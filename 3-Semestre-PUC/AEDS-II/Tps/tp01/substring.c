#include <stdio.h>
#include <stdlib.h>
int tamanho(char s[]){
    int i = 0;
    while(s[i] != '\0') i++;
    return i;
}


int substringSemRepeticao(char s[]){
    int freq[256] = {0};
    int i = 0, j = 0;
    int maior = 0, atual = 0;
    int tam = tamanho(s);

    while(j < tam && s[j] != '\n'){
        while(freq[(int)s[j]] > 0){
            freq[(int)s[i]]--;
            i++;
            atual--;
        }
        
        freq[(int)s[j]]++;
        atual++;
        j++;

        if(atual > maior) maior = atual;
    }
    return maior;
}

int main(){
    char s[100];
    fgets(s, 100, stdin);
    while(!(s[0] == 'F' && s[1] == 'I' && s[2] == 'M')){
        printf("%d\n", substringSemRepeticao(s));
        fgets(s, 100, stdin);
    }
    return 0;
}
