// Aquí definimos la interfaz de nuestro módulo
program DATE_PROG {
    version DATE_VERS {
        long BIN_DATE(void) = 1;    /*Procedimiento #1*/
        string STR_DATE(long) = 2;  /*Procedimiento #2*/
    } = 1; /*Versión #1*/
} = 0x31234567; /*Número de programa*/
