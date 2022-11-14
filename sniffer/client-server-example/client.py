import socket

HOST = "192.168.1.138"  # The server's hostname or IP address
PORT = 65432  # The port used by the server

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    x = input("Escribe el mensaje a enviar: ")
    s.sendall(bytes(x, "utf-8"))
    data = s.recv(1024)

print(f"Received {data!r}")