#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int tamanho(char string[]){
    int i = 0;
    while(string[i] != '\0'){
        i = i + 1;
    }
    return i;
}

int verificaAna(char string[]){
    int retorno = 1;
    int i = 0;
    int j = tamanho(string) - 1;//nao achei um jeito melhor de fazer a remocao do \0 e \n 
    
    if (string[j] == '\n') j--;
    if(tamanho(string) > 1){
        while(i < j && string[i] != ' ' && string[j] != ' '){
            if(string[i] != string[j]){
                retorno = 0;
            }
            i++;
            j--;
        }
    }
    return retorno;
}

int main(){
    char s1[100] = " ";
    while(fgets(s1,100,stdin) != NULL){
        if(verificaAna(s1) == 1){
            printf("SIM");
        }
        else{
            printf("NAO");
        }
        printf("\n");
    }
    return 0;
}
