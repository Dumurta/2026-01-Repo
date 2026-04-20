#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int verificaAna(char s1[], char s2[]){
    int retorno = 1;
    int freq[26] = {0};
    int i = 0;
    char c;

    while(s1[i] != '\0'){
        if(s1[i] != ' ' && s1[i] != '\n'){
            c = s1[i];
            if(c >= 'A' && c <= 'Z') c = c + 32;
            freq[c - 'a']++;
        }
        i++;
    }

    i = 0;
    while(s2[i] != '\0'){
        if(s2[i] != ' ' && s2[i] != '\n'){
            c = s2[i];
            if(c >= 'A' && c <= 'Z') c = c + 32;
            freq[c - 'a']--;
        }
        i++;
    }

    i = 0;
    while(i < 26){
        if(freq[i] != 0) retorno = 0;
        i++;
    }

    return retorno;
}

int main(){
    char linha[100], s1[50], s2[50];
    fgets(linha, 100, stdin);
    while(!(linha[0] == 'F' && linha[1] == 'I' && linha[2] == 'M')){
         sscanf(linha, "%s %s", s1, s2);
        if(verificaAna(s1, s2) == 1)
            printf("SIM\n");
        else
            printf("NAO\n");
        fgets(linha, 100, stdin);
    }
    return 0;
}
