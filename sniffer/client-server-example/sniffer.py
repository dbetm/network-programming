import os
import socket
from struct import unpack # check: https://docs.python.org/3/library/struct.html
from functools import lru_cache
from pprint import pprint



"""For best match with hardware and network realities, the value of bufsize 
should be a relatively small power of 2, for example, 4096."""
DEFAULT_BUFFER_SIZE = 65565 #4096
EXTENDED_ETHERNET_HEADER_LENGTH = 14
IPV4_ETHER_TYPE = 8
IP_PROTOCOL_TYPES_MAP = {
    1: "ICMP", 2: "IGMP", 5: "ST", 6:"TCP", 17:"UDP", 27: "RDP", 56: "TLSP"
}
NETWORK_INTERFACE = "wlp2s0"


def eth_addr_to_hex(address: str):
    """Convert a string of 6 characters of ethernet address into a dash separated hex string."""
    b = '%.2x:%.2x:%.2x:%.2x:%.2x:%.2x' % (
        int(address[0]), int(address[1]), int(address[2]), int(address[3]), int(address[4]), int(address[5])
    )
    
    return b


def get_headers_from_ethernet_frame(ethernet_frame: bytes) -> tuple:
    """Get source MAC address, destination MAC address and protocol type (EtherType).
    Check Exthernet II frame at: https://www.geeksforgeeks.org/ethernet-frame-format/
    """
    dest_mac, source_mac, protocol_type = unpack(
        "!6s6sH", ethernet_frame[:EXTENDED_ETHERNET_HEADER_LENGTH]
    )

    """ntohs converts 16-bit positive integers from network to host byte order. On machines where the host byte order is the same as network byte order, this is a no-operation; 
    otherwise, it performs a 2-byte swap operation."""
    protocol_type = socket.ntohs(protocol_type)

    # parse destination and source MAC addresses
    dest_mac = eth_addr_to_hex(dest_mac)
    source_mac = eth_addr_to_hex(source_mac)

    return (dest_mac, source_mac, protocol_type)


def get_ipv4_protocol_metadata(ethernet_frame: bytes):
    """Extract related metadata of IPv4 protocol. Check this:
    https://www.geeksforgeeks.org/introduction-and-ipv4-datagram-header/
    https://en.wikipedia.org/wiki/IPv4
    """
    ip_headers = ethernet_frame[EXTENDED_ETHERNET_HEADER_LENGTH:20 + EXTENDED_ETHERNET_HEADER_LENGTH]
    # now unpack some of them :)
    """
    1 byte: Destination Service Access Point, B - integer
    1 byte: Source Service Access Point, B - integer
    1 byte: Control field, H - integer
    # IPv4 Datagram:
    ...
    1 byte: IP protocol, B - integer 
    ...
    4 bytes: Source IPv4 address, 4s - bytes
    4 bytes: Destination address, 4s - bytes
    """
    ip_headers_tuple = unpack("!BBHHHBBH4s4s", ip_headers)

    protocol = ip_headers_tuple[6]
    # inet_ntoa converts an IP address, which is in 32-bit packed format to the popular 
    # human readable dotted-quad string format.
    source_address = socket.inet_ntoa(ip_headers_tuple[8])
    destination_address = socket.inet_ntoa(ip_headers_tuple[9])

    return (protocol, source_address, destination_address)


@lru_cache(maxsize=50)
def resolve_host_name(ip_address: str) -> str:
    try:
        hostname, _, _ = socket.gethostbyaddr(ip_address)
    except:
        hostname = "unknown"

    return hostname



def enable_promiscous_mode(interface: str) -> None:
    cmd = f"sudo ip link set dev {interface} promisc on"
    try:
        os.system(cmd)
    except Exception as e:
        print(f"Failed to enable promiscous mode on {interface}")
        raise e

def disable_promiscous_mode(interface: str) -> None:
    cmd = f"sudo ip link set dev {interface} promisc off"
    try:
        os.system(cmd)
    except Exception as e:
        print(f"Failed to disable promiscous mode on {interface}")


def sniff():
    """RAW Sockets: This element provides a way to bypass the whole network stack traversal 
    of a packet and deliver it directly to an application.

    In linux socket.ntohs(0x0003) tells capture everything including ethernet frames. 
    To capture TCP, UDP, or ICMP only, instead of socket.ntohs(0x0003) you will 
    write socket.IPPROTO_TCP, socket.IPPROTO_UDP and socket.IPPROTO_ICMP respectively.

    In Windows you can use: socket.htons(0x0800), check: 
    https://en.wikipedia.org/wiki/EtherType
    """
    ip_source_dest_mapping = dict()
    enable_promiscous_mode(interface=NETWORK_INTERFACE)

    with socket.socket(socket.AF_PACKET, socket.SOCK_RAW, socket.ntohs(3)) as conn:
        conn.bind((NETWORK_INTERFACE, 0))

        for i in range(200):
            packet = conn.recvfrom(DEFAULT_BUFFER_SIZE)
            ethernet_frame_raw_bytes, address = packet

            dest_mac, src_mac, protocol_eth_type = get_headers_from_ethernet_frame(
                ethernet_frame_raw_bytes
            )
            #print(i+1, "Dirección MAC de origen: ", src_mac)

            # wait for a moment, 3 seconds
            # time.sleep(1)

            # Checar si es del protocolo de internet IPv4, 8, aunque en realidad no es necesario
            # dada la forma de construir el socket socket.ntohs(0x0003)
            if protocol_eth_type == IPV4_ETHER_TYPE:
                ip_protocol_metadata = get_ipv4_protocol_metadata(ethernet_frame_raw_bytes)
                protocol, source_ip, dest_ip = ip_protocol_metadata


                if protocol in IP_PROTOCOL_TYPES_MAP:
                    origin_hostname = resolve_host_name(source_ip)
                    dest_hostname = resolve_host_name(dest_ip)

                    print(
                        i+1,
                        f"[IP de origen]: {source_ip} hostname: {origin_hostname}, "
                        f"[IP destino]: {dest_ip} hostname: {dest_hostname}, "
                        f"[IP protocolo]: {IP_PROTOCOL_TYPES_MAP[protocol]}"
                    )

                    key_ = f"{source_ip} {origin_hostname}"

                    if not key_ in ip_source_dest_mapping:
                        ip_source_dest_mapping[key_] = set()
                    ip_source_dest_mapping[key_].add(f"{dest_ip} {dest_hostname}")

    print("\nIP Mapping Source and Dest", "*"*20)
    pprint(ip_source_dest_mapping)

    disable_promiscous_mode(NETWORK_INTERFACE)

    # terminar la conexión del socket (dejar de cachar paquetes en la LAN)
    # conn.close() # al usar un context manager ya no es necesario, y es más seguro


if __name__ == '__main__':
    sniff()