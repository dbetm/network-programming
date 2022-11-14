import socket
from struct import unpack # check: https://docs.python.org/3/library/struct.html
import time


"""For best match with hardware and network realities, the value of bufsize 
should be a relatively small power of 2, for example, 4096."""
DEFAULT_BUFFER_SIZE = 4096
EXTENDED_ETHERNET_HEADER_LENGTH = 14
IPV4_ETHER_TYPE = 8
IP_PROTOCOL_TYPES_MAP = {6:"TCP", 17:"UDP"}


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


def sniff():
    """RAW Sockets: This element provides a way to bypass the whole network stack traversal 
    of a packet and deliver it directly to an application.

    In linux socket.ntohs(0x0003) tells capture everything including ethernet frames. 
    To capture TCP, UDP, or ICMP only, instead of socket.ntohs(0x0003) you will 
    write socket.IPPROTO_TCP, socket.IPPROTO_UDP and socket.IPPROTO_ICMP respectively.

    In Windows you can use: socket.htons(0x0800), check: 
    https://en.wikipedia.org/wiki/EtherType
    """
    conn = socket.socket(socket.AF_PACKET, socket.SOCK_RAW, socket.ntohs(0x0003))

    for i in range(10):
        packet = conn.recvfrom(DEFAULT_BUFFER_SIZE)
        ethernet_frame_raw_bytes, address = packet

        dest_mac, src_mac, protocol_eth_type = get_headers_from_ethernet_frame(
            ethernet_frame_raw_bytes
        )
        print(i+1, "Dirección MAC de origen: ", src_mac)

        # wait for a moment, 3 seconds
        time.sleep(3)

        # Checar si es del protocolo de internet IPv4, 8, aunque en realidad no es necesario
        # dada la forma de construir el socket socket.ntohs(0x0003)
        if protocol_eth_type == IPV4_ETHER_TYPE:
            ip_protocol_metadata = get_ipv4_protocol_metadata(ethernet_frame_raw_bytes)
            protocol, source_ip, dest_ip = ip_protocol_metadata

            if protocol in IP_PROTOCOL_TYPES_MAP:
                print(
                    i+1,
                    f"[IP de origen]: {source_ip}, "
                    f"[IP destino]: {dest_ip}, "
                    f"[IP protocolo]: {IP_PROTOCOL_TYPES_MAP[protocol]}"
                )

    # terminar la conexión del socket (dejar de cachar paquetes en la LAN)
    conn.close()


if __name__ == '__main__':
    sniff()