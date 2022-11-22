from scapy.all import *

def handler(packet):
    summary = packet.summary()
    
    print(summary)


sniff(prn=handler, count=500)