#include <stdio.h>
#include <stdlib.h>

char* CifrarREC(char* s1,char* s2, int i){
    if(s1[i] != '\0'){
        char c = s1[i];
        if(c >= 128 && c <= 255){
            s2[i] = c;
        }else{
            s2[i] = (char) (c + 3);
        }
        CifrarREC(s1, s2, i + 1);
    }else s2[i] = '\0';

    return s2;
   
}

int main(){
    char* string;
    string = (char*) malloc(100 * sizeof(char));
    char* string2 = (char*) malloc(100 * sizeof(char));
    fgets(string, 100, stdin);
    while(!(string[0] == 'F' && string[1] == 'I' && string[2] == 'M')){
        string2 = CifrarREC(string,string2,0 );
        for(int i = 0; string2[i] != '\0'; i++){
            printf("%c", string2[i]);
        }
        printf("\n");
        fgets(string, 100, stdin);
    }
    return 0;
}
