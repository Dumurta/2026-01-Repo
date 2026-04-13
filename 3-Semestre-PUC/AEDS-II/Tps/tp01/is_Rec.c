#include <stdio.h>
#include <stdlib.h>

char toLower(char c){
    if(c >= 'A' && c <= 'Z'){
        c = c + 32;
    }
    return c;
}

int tamanho(char* s, int i){
    if(s[i] != '\0'){
        i = tamanho(s, i + 1);
    }
    return i;
}


int engual(char* s1, char* s2,int i, int resp){
    if(s1[i] != '\0'){
        if(s1[i] != s2[i]){
            resp = 0;
        }
        resp = engual(s1,s2,i + 1, resp);
    }
    return resp;
}

int vogais(char* s1, int i, int resp){
    if(s1[i] != '\0'&& resp == 1){
        char c = toLower(s1[i]);
        if(!(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')){
            resp = 0;
        }
        resp = vogais(s1, i + 1, resp);
    }
    return resp;
}


int conso(char* s1, int i, int resp){
    if(s1[i] != '\0'&& resp == 1){
        char c = toLower(s1[i]);
        if(!((c >= 'a' && c <= 'z') && !(c == 'a' || c == 'e' || c == 'i' || c == 'o' | c == 'u'))){
            resp = 0;
        }
        resp = conso(s1, i + 1, resp);
    }
    return resp;
}

int numero(char* s1, int i, int resp){
    if(s1[i] != '\0' && resp == 1){
        if(!(s1[i] >= '0' && s1[i] <= '9')) {
            resp = 0;
        }
        resp = numero(s1, i + 1, resp);
    }
    return resp;
}

int numeroReal(char* s1, int i, int resp){
    if(s1[i] != '\0'){
        char c = s1[i];
        if(c == '.' || c == ','){
            resp = 1;
        } else if(!(c >= '0' && c <= '9')){
            resp = -1;
        }
        if(resp != -1){
            resp = numeroReal(s1, i + 1, resp);
        }
    }
    if(resp == -1){
        resp = 0;
    }
    return resp;
}


int main(){
    char* string = malloc(100 * sizeof(char));
    int i = 0;
    scanf(" %s", string);
    while(!(tamanho(string, 0) == 3 && engual(string, "FIM", 0, 1))){
        printf(vogais(string, 0, 1) ? "SIM " : "NAO ");
        
        printf(conso(string,0,1) ? "SIM " : "NAO ");
       
        printf(numero(string,0,1) ? "SIM " : "NAO ");

        printf(numeroReal(string,0,0) ? "SIM " : "NAO ");
        printf("\n");
        scanf(" %s", string);
    }
    return 0;
}

