#include <stdio.h>
#include <stdlib.h>
int tamanho(char[] string){
    int i = 0;
    while(string[i] != '\0'){
        i = i + 1;
    }
    return i;
}

int verficaAna(char[] string){
    int retorno = 1;
    int i = 0;
    int j = tamanho(string);
    while(i < j && string[i] != ' '){
        if(string[i] != string[j]){
            retorno = 0;
        }
    }
    return retorno;
}

int main(){
    char[] s1 = "amor roma";
    if(verificarAna(s1) == 1){
        printf("%s", "SIM");
    }
    else{
        printf("%s", "NAO");
    }
    return 0;
}
