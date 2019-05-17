#include <unistd.h>
#include <cstdio>
#include <cstdlib>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string>

#define PORT 8080

using namespace std;

int main(int argc, char const *argv[]) {
    int server_fd, new_socket, valread;
    struct sockaddr_in address;
    int opt = 1;
    // Longitud de la direccción
    int addrlen = sizeof(address);
    // Definimos un buffer
    char buffer[1024] = {0};
    string mensaje = "Hello from server";
    // Creando el descriptor de socket del archivo
    if((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
        perror("socket failed");
        exit(EXIT_FAILURE);
    }
    // Indicar que se va usar el puerto 8080
    if(setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT, &opt, sizeof(opt))) {
        perror("setsocketopt");
        exit(EXIT_FAILURE);
    }
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(PORT);
    // Binding
    if(bind(server_fd, (struct sockaddr*)&address, sizeof(address)) < 0) {
        perror("bind failed");
        exit(EXIT_FAILURE);
    }
    // Poner a escuchar el servidor
    if(listen(server_fd, 3) < 0) {
        perror("listen");
        exit(EXIT_FAILURE);
    }
    // Aceptar un nuevo cliente
    if((new_socket = accept(server_fd, (struct sockaddr*)&address, (socklen_t*)&addrlen)) < 0) {
        perror("accept");
        exit(EXIT_FAILURE);
    }
    // leer el mensaje entrante
    valread = read(new_socket, buffer, 1024);
    printf(">> %s\n", buffer);
    // Enviamos un mensaje de respuesta
    send(new_socket, mensaje.c_str(), mensaje.size(), 0);
    printf("The message has been sent\n");
    return 0;
}
