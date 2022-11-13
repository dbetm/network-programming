import socket

s = socket.socket(socket.AF_INET, socket.SOCK_RAW, socket.IPPROTO_TCP)

while(True):
    obj = s.recvfrom(65565)
    print(obj)
