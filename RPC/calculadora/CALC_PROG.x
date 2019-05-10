struct doublepair {
    double a;
    double b;
};

program CALC_PROG {
    version CALC_VERS {
        double sumar(doublepair) = 1;
        double multiplicar(doublepair) = 2;
        double dividir(doublepair) = 3;
        double porcentaje(doublepair) = 4;
        double raizCuadrada(double) = 5;
        double potencia(doublepair) = 6;
        double reciproco(double) = 7;
    } = 1;
} = 0x31314141;
