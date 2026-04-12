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
    char s1[100], s2[100];
    fgets(s1, 100, stdin);
    while(!(s1[0] == 'F' && s1[1] == 'I' && s1[2] == 'M')){
        fgets(s2, 100, stdin);
        if(verificaAna(s1, s2) == 1)
            printf("SIM\n");
        else
            printf("NAO\n");
        fgets(s1, 100, stdin);
    }
    return 0;
}
