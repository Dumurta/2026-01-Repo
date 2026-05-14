#include <stdio.h>
#include <stdlib.h>

int* codificador(char* senha){
	int* senhaCod = malloc(20 * sizeof(int));
	for(int i = 0; senha[i] != '\0'; i++){
		if((senha[i] >= 'a' && senha[i] <= 'z' )||(senha[i] >= 'A' && senha[i] <= 'Z' )){
			if(senha[i] == 'a' || senha[i] == 'k' || senha[i] == 'u' || senha[i] == 'G' || senha[i] == 'Q'){
				senhaCod[i] = 0;
			}else if(senha[i] == 'I' || senha[i] == 'b' || senha[i] == 'S' || senha [i] == 'l' || senha[i] == 'v'){
				senhaCod[i] = 1;
			}else if(senha[i] == 'E' || senha[i] == 'O' || senha[i] == 'Y' || senha [i] == 'c' 
				|| senha[i] == 'm' || senha[i] == 'w'){
	                        senhaCod[i] = 2;
	       		}else if(senha[i] == 'F' || senha[i] == 'P' || senha[i] == 'Z' || senha [i] == 'd'
				|| senha[i] == 'n' || senha[i] == 'x'){
                       		senhaCod[i] = 3;	                
			}else if(senha[i] == 'J' || senha[i] == 'T' || senha[i] == 'e' || senha [i] == 'o' || senha[i] == 'y'){
                        	senhaCod[i] = 4;
       	        	}else if(senha[i] == 'D' || senha[i] == 'N' || senha[i] == 'X' || senha [i] == 'F' || 
				senha[i] == 'p' || senha[i] == 'z'){
                        	senhaCod[i] = 5;
               		}else if(senha[i] == 'A' || senha[i] == 'K' || senha[i] == 'U' || senha [i] == 'q' || senha[i] == 'g'){
                        	senhaCod[i] = 6;
                	}else if(senha[i] == 'C' || senha[i] == 'M' || senha[i] == 'W' || senha [i] == 'h' || senha[i] == 'r'){
                        	senhaCod[i] = 7;
                	}else if(senha[i] == 'B' || senha[i] == 'L' || senha[i] == 'V' || senha [i] == 'i' || senha[i] == 's'){
                        	senhaCod[i] = 8;
                	}else if(senha[i] == 'H' || senha[i] == 'R' || senha[i] == 'j' || senha [i] == 't'){
	                       	 senhaCod[i] = 9;
                	}
	 	}else{
			i++;
		}
	}
	return senhaCod;
}

int main(){
	char* senha;
	senha = malloc(30 * sizeof(char));
	fgets(senha, 30, stdin);
	int* senhaCod = codificador(senha);
	for(int i = 0; senhaCod[i] != '\0'; i++){
		printf("%d", senhaCod[i]);
	}
	printf("\n");
	return 0;
	/*
	 * OBS: NAO CONSIGO FAZER A LEITURA COMPLETA, NAO IDENTIFIQUEI O PQ, ELE LE A PRIMEIRA LETRA E JA MANDA DIRETO PRO METODO Q RETORNA
	 * SOMENTE O PRIMEIRO TERMO CODIFICADO, NO CASO ELE LE APENAS ATE ONDE NAO TEM ESPACO, SE TIVER ELE PARA*/
}
