#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdlib.h>

double hola( double a, int b);

int main() {

    double mealCost = 0, totalCost = 0, tip2 = 0, tax2 = 0;
    int tip = 0, tax = 0;
    
    scanf("%lf", &mealCost);
    scanf("%d", &tip);
    tip2 = hola(mealCost, tip);
    printf("tip es %.2lf\n", tip2);
    scanf("%d", &tax);
    tax2 = hola(mealCost, tax);
    printf("tax es %.2lf\n", tax2);
    
    totalCost = mealCost + tip2 + tax2;
    printf("The total meal cost is %.0lf dollars", totalCost);
  
    return 0;
}
double hola( double a, int b) {
	double value = 0;
	value = a * ((double)b / 100);
	return value;
}
