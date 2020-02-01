#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/* Prototipos de funciones */
unsigned int menu();
unsigned int validar();
// emisor y receptor
char* leerTrama(int);
char* calcularResiduo(char *, char *);
char* quitarCerosInicio(char *);
char* obtenerSubstring(char *, unsigned int, unsigned int);
char* dividir(char *, char *);

int main(int argc, char const *argv[]) {
    unsigned int opcion;

    opcion = menu();

    switch (opcion) {
        // Primer polinomio
        case 1 ... 3: {
            unsigned int len = 0;
            char *generador;
            // Declaración del residuo a calcular en el emisor.
            char *residuo;
            // Declaración del residuo a calcular en el receptor.
            char *residuoReceptor;
            char *trama;
            char *secuencia;
            char *mensajeRecibido;

            trama = leerTrama(0);

            // Asignación el polinomio generador en forma binaria y
            // concatenación de parte binaria para algoritmo CRC
            if(opcion == 1) {
                generador = (char *)malloc(4 * sizeof(char));
                strcat(generador, "1101");
                // Redimensionamos *secuencia
                secuencia = (char *)malloc(strlen(trama) + strlen(generador));
                strcpy(secuencia, trama);
                strcat(secuencia, "000");
            }
            else if(opcion == 2) {
                generador = (char *)malloc(13 * sizeof(char));
                strcat(generador, "1100000001111");
                secuencia = (char *)malloc(strlen(trama) + strlen(generador));
                strcpy(secuencia, trama);
                strcat(secuencia, "000000000000");
            }
            else {
                generador = (char *)malloc(17 * sizeof(char));
                strcat(generador, "10001000000100001");
                secuencia = (char *)malloc(strlen(trama) + strlen(generador));
                strcpy(secuencia, trama);
                strcat(secuencia, "0000000000000000");
            }

            len = strlen(secuencia);

            if(len < strlen(generador)) {
                printf("Inconsistencia, generador con longitud mayor a la secuencia\n");
                break;
            }

            // printf("Trama: %s\nSecuencia: %s\nGenerador: %s\n", trama, secuencia, generador);

            // resolvemos la división en el emisor para obtener el residuo
            residuo = calcularResiduo(secuencia, generador);

            // En el lado del receptor se debe comprobar la integridad de los datos
            mensajeRecibido = (char *)malloc(strlen(secuencia));
            strcpy(mensajeRecibido, leerTrama(1));
            strcat(mensajeRecibido, strrchr(residuo, '0'));
            printf("=> Mensaje recibido: %s\n", mensajeRecibido);
            residuoReceptor = calcularResiduo(mensajeRecibido, generador);
            printf("=> El residuo en el receptor es: %s\n", residuoReceptor);
            // Para determinar si la integridad de los datos no se corrompió,
            // entonces el residuo de la divisón polinomial en el receptor debe
            // ser 0000...., "cero".
            if(strrchr(residuoReceptor, '1') == NULL) printf("\nYay! Mensaje correcto\n");
            else printf("Alerta: Mensaje incorrecto.\n");

            //Liberamos memoria
            if(trama != NULL) free(trama);
            // printf("Hola mundo 1\n");
            // getchar();
            if(residuo != NULL) free(residuo);
            if(generador != NULL) free(generador);
            if(secuencia != NULL) free(secuencia);
            break;
        }
        // Salir
        case 4:
            printf("¡Adiós!\n");
            break;
        default:
            printf("Opción no válida\n");
    }
    return 0;
}

unsigned int menu() {
    system("clear");
    printf("\tElija un polinomio generador: \n");
    printf("1) CRC-3: x^3 + x^2 + 1 => 1101 (forma binaria)\n");
    printf("2) CRC-12: x^12 + x^11 + x^3 + x^2 + x^1 + 1 => 1100000001111\n");
    printf("3) CRC CCITT V41: x^16 + x^12 + x^5 + 1 => 10001000000100001\n");
    printf("4) Salir\n");
    return validar();
}

unsigned int validar() {
    int num, ok, ch;

    do {
		printf("==> ");
		fflush(stdout);
		if ((ok = scanf("%d", &num)) == EOF)
		return EXIT_FAILURE;

		if ((ch = getchar()) != '\n') {
    		ok = 0;
    		printf("Vuelva a intentarlo: ");
    		while ((ch = getchar()) != EOF && ch != '\n');
		}
	} while (!ok);

	return num;
}

// Función que permite una lectura dinámica
char* leerTrama(int op) {
    char *secuencia = 0;
    unsigned int secuenciaSize = 0;
    if(op == 0) printf("Emisor => Escriba la trama de bits: ");
    else printf("Receptor => Escriba la trama de bits: ");

    while(1) {
        // se redimensiona
        secuencia = (char *)realloc(secuencia, secuenciaSize + 1);
        // Buenas prácticas de programación
        if(secuencia == NULL) {
            printf("Error al intentar asignar memoria\n");
            exit(EXIT_FAILURE);
        }

        int c = getchar();
        // si C es igual al final de línea, entonces deja de leer
        if(c == EOF || c == '\n') {
            secuencia[secuenciaSize] = 0;
            break;
        }
        // Si hay un carácter diferente de 1 o 0
        if((char)c != '1' && (char)c != '0') {
            printf("Carácter %c ignorado\n", (char)c);
            continue;
        }
        secuencia[secuenciaSize] = (char)c;
        secuenciaSize++;
    }
    return secuencia;
}

char* calcularResiduo(char *secuencia, char *generador) {
    char *residuo = 0;
    char *subcadena = (char *)malloc(strlen(generador) * sizeof(char));
    unsigned int s, index = 0, len;

    secuencia = quitarCerosInicio(secuencia);
    len = strlen(secuencia);
    // se obtiene la primer subcadena
    subcadena = obtenerSubstring(secuencia, strlen(generador), index);
    index += strlen(generador);

    while (index < len) {
        subcadena = dividir(subcadena, generador);
        subcadena = quitarCerosInicio(subcadena);
        s = strlen(generador) - strlen(subcadena);
        // Concatenamos la subcadena obtenida
        strcat(subcadena, obtenerSubstring(secuencia, s, index));
        index += s;
        //printf("index vale %d\n", index);
        //printf("La subcadena es: %s\n", subcadena);
    }
    residuo = dividir(subcadena, generador);
    return residuo;
}

char* quitarCerosInicio(char *secuencia) {
    int zeros = 0;
    char *resultado = 0;
    for(unsigned int i = 0; i < strlen(secuencia); i++) {
        if(secuencia[i] == '1') break;
        zeros++;
    }
    // Creamos el arreglo con una dimensión igual al de la secuencia original
    // menos el número de ceros al inicio.
    resultado = (char *)realloc(resultado, strlen(secuencia) - zeros);

    for(unsigned int i = zeros; i < strlen(secuencia); i++)
        resultado[i - zeros] = secuencia[i];

    return resultado;
}

// Obtiene la subcadena correspondiente para concatenar y poder hacer la división
char* obtenerSubstring(char *secuencia, unsigned int len, unsigned int index) {
    char *substr = (char *)malloc(len * sizeof(char));
    for (unsigned int i = index; i < (len + index); i++)
        substr[i - index] = secuencia[i];

    //printf("\nRetorno es: %s\n", substr);
    return substr;
}

// Hace la división, en realidad es suma exclusiva (^) XOR bit a bit
char* dividir(char *substr, char *generador) {
    char* resultado = (char *)malloc(strlen(generador) * sizeof(char));
    for(unsigned int i = 0; i < strlen(substr); i++) {
        if((substr[i] == '1' && generador[i] == '1') ||
        (substr[i] == '0' && generador[i] == '0'))
            resultado[i] = '0';
        else resultado[i] = '1';
    }

    return resultado;
}
