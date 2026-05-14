#include <stdio.h>
#include <stdlib.h>


int somador(int numero){
    int resultado = 0;
    while(numero > 0){
        resultado = resultado + (numero % 10);
        numero = numero / 10;
    }
    return resultado;
}


int main(){
    int numero;
    
    while(scanf("%d", &numero) == 1 && numero != 0){
        int resultado = somador(numero);
        printf("%d\n", resultado);
    }
    
    return 0;
}
