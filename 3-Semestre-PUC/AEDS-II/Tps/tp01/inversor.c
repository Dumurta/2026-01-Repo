#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

char *umInversor(char string[])
{
    int tamanho = strlen(string);
    char *resposta = malloc((tamanho + 1) * sizeof(char));
    if (tamanho > 1)
    {
        int j = tamanho - 1;
        for (int i = 0; i < tamanho; i++)
        {
            resposta[i] = string[j];
            j--;
        }
    }
    return resposta;
}

int main()
{
    char string[1000];
    bool fimEncontrado = false;
    while (!fimEncontrado && fgets(string, 1000, stdin))
    {
        int len = strlen(string);
        if (len > 0 && string[len - 1] == '\n')
        {
            string[len - 1] = '\0';
        }
        if (strlen(string) && string[0] == 'F' && string[1] == 'I' && string[2] == 'M')
        {
            fimEncontrado = true;
        }
        else
        {
            char *resposta = umInversor(string);
            printf("%s", resposta);
            printf("\n");
        }
    }
    return 0;
}