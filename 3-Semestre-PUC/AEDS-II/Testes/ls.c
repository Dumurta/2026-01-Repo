#include <stdio.h>
#include <stdbool.h>
bool compararStrings(char s1[], char s2[])
{
    bool teste = false;
    int i = 0;
    while (s1[i] == s2[i] && s1[i] != '\0' && s2[i] != '\0')
    {
        i++;
    }
    if (s1[i] == s2[i])
    {
        teste = true;
    }
    return teste;
}
int comprimento(char s[])

{
    int i = 0;
    while (s[i] != '\0' && s[i] != '\n')
    {
        i++;
    }
    return i;
}

char toLower(char s)
{
    char c = s;
    if (c >= 'A' && c <= 'Z')
    {
        c = (char)(c + 32);
    }
    return c;
}
/*
 *funcao para testar se string e' composta somente por vogais, caso algum caractere da string nao seja vogal
 *a funcao altera a variavel booleana pra false
 */

bool testeVogais(char string[])
{
    bool test = true;
    int tamanho = comprimento(string);
    char c;
    int i = 0;
    while (i < tamanho && test)
    {
        c = string[i];
        c = toLower(c);
        /*
         *Se C for um caractere considerado especial na tabela UNICODE
         *Ele retorna false
         */
        if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u')
        {
            test = false;
        }
        i++;
    }

    return test;
}

bool testeConso(char string[])
{
    bool test = true;
    int tamanho = comprimento(string);
    char c;
    int i = 0;
    while (i < tamanho && test)
    {
        c = string[i];
        c = toLower(c);
        /**
         * se o caractere C nao for uma vogal, ele e' consoante
         */
        // Se nao for letra (a-z), teste = false
        if (!(c >= 'a' && c <= 'z'))
        {
            test = false;
        }
        if ((c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'))
        {
            test = false;
        }
        i++;
    }

    return test;
}
bool testeNum(char string[])
{
    bool test = true;
    int tamanho = comprimento(string);
    char c;
    int i = 0;
    while (i < tamanho && test)
    {
        c = string[i];
        /**
         * Caso o C com o valor inteiro convertido da string
         * segundo a tabela ASCII, nao contemple de 0 a 9
         * retorna false.
         */
        if (!(c >= '0' && c <= '9'))
        {
            test = false;
        }
        i++;
    }

    return test;
}

bool testeReal(char string[])
{
    bool teste = false;
    int tamanho = comprimento(string);
    int i = 0;
    int pontos = 0;
    while (i < tamanho)
    {
        char c = string[i];
        /**
         * Caso a string nao possua apenas numeros de 0 a 9
         * e nao tenha ate 1 ponto ou virgula
         * e' false
         */
        if (c == '.' || c == ',')
        {
            pontos++;
        }
        else if (!(c >= '0' && c <= '9'))
        {
            teste = false;
        }
        else if (pontos == 1)
        {
            teste = true;
        }
        i++;
    }
    return teste;
}

int main()
{
    char string[1000];
    bool fimEncontrado = false;

    while (!fimEncontrado && fgets(string, 1000, stdin) != NULL)
    {

        int len = comprimento(string);
        if (len > 0 && string[len - 1] == '\n')
        {
            string[len - 1] = '\0';
        }
        if (comprimento(string) >= 3 && string[0] == 'F' && string[1] == 'I' && string[2] == 'M')
        {
            fimEncontrado = true;
        }
        else
        {
            bool x1 = testeVogais(string);
            bool x2 = testeConso(string);
            bool x3 = testeNum(string);
            bool x4 = testeReal(string);

            printf("%s ", x1 ? "SIM" : "NAO");
            printf("%s ", x2 ? "SIM" : "NAO");
            printf("%s ", x3 ? "SIM" : "NAO");
            printf("%s\n", x4 ? "SIM" : "NAO");
        }
    }
    return 0;
}