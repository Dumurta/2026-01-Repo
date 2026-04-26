#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_LINHA 1024
#define MAX_TEXTO 256
#define MAX_REGISTROS 1000
#define PATH_CSV_LINUX "/tmp/restaurantes.csv"

typedef int bol;
#define true 1
#define false 0

typedef struct
{
    int id;
    char nome[MAX_TEXTO];
} Restaurante;

typedef struct
{
    int tamanho;
    Restaurante **restaurantes;
} Colecao;

void texto_copiar(char *d, char *o)
{
    int i = 0;
    while (o[i] != '\0')
    {
        d[i] = o[i];
        i++;
    }
    d[i] = '\0';
}

int contar_registros(char *p)
{
    int n = 0;
    char l[MAX_LINHA];
    FILE *arq = fopen(p, "r");
    if (arq != NULL)
    {
        if (fgets(l, sizeof(l), arq) != NULL)
        {
        }
        while (fgets(l, sizeof(l), arq) != NULL)
        {
            if (l[0] != '\0' && l[0] != '\n')
                n++;
        }
        fclose(arq);
    }
    return n;
}

void ler_csv(Colecao *c, char *p)
{
    c->tamanho = contar_registros(p);
    c->restaurantes = (Restaurante **)malloc(sizeof(Restaurante *) * c->tamanho);
    FILE *arq = fopen(p, "r");
    char l[MAX_LINHA];
    int i = 0;
    if (arq != NULL)
    {
        if (fgets(l, sizeof(l), arq) != NULL)
        {
        }
        while (fgets(l, sizeof(l), arq) != NULL && i < c->tamanho)
        {
            int id = 0;
            char nome_lido[MAX_TEXTO];
            sscanf(l, "%d,%255[^,\n]", &id, nome_lido);

            c->restaurantes[i] = (Restaurante *)malloc(sizeof(Restaurante));
            c->restaurantes[i]->id = id;
            texto_copiar(c->restaurantes[i]->nome, nome_lido);
            i++;
        }
        fclose(arq);
    }
}

Colecao carregar()
{
    Colecao c;
    FILE *a = fopen(PATH_CSV_LINUX, "r");
    if (a != NULL)
    {
        fclose(a);
        ler_csv(&c, (char *)PATH_CSV_LINUX);
    }
    else
    {
        ler_csv(&c, "C:/tmp/restaurantes.csv");
    }
    return c;
}

char *buscar_nome_por_id(Colecao *c, int id)
{
    int i = 0;
    while (i < c->tamanho)
    {
        if (c->restaurantes[i]->id == id)
        {
            return c->restaurantes[i]->nome;
        }
        i++;
    }
    return NULL;
}

void selecao(char array[][MAX_TEXTO], int n)
{
    for (int i = 0; i < (n - 1); i++)
    {
        int menor = i;
        for (int j = (i + 1); j < n; j++)
        {
            if (strcmp(array[j], array[menor]) < 0)
                menor = j;
        }
        char temp[MAX_TEXTO];
        texto_copiar(temp, array[menor]);
        texto_copiar(array[menor], array[i]);
        texto_copiar(array[i], temp);
    }
}

bol busca_binaria(char nomes[][MAX_TEXTO], int n, char *alvo, int *comp)
{
    int esq = 0;
    int dir = n - 1;
    while (esq <= dir)
    {
        int m = (esq + dir) / 2;
        (*comp)++;
        int c = strcmp(nomes[m], alvo);
        if (c == 0)
            return true;
        else if (c < 0)
            esq = m + 1;
        else
            dir = m - 1;
    }
    return false;
}

int main()
{
    int comparacoes = 0;
    clock_t inicio = clock();

    Colecao c = carregar();
    char nomes_sel[MAX_REGISTROS][MAX_TEXTO];
    int n = 0;
    int id = 0;
    bol fim_ids = false;

    while (scanf("%d", &id) == 1 && !fim_ids)
    {
        if (id == -1)
        {
            fim_ids = true;
        }
        else
        {
            char *nome = buscar_nome_por_id(&c, id);
            if (nome != NULL)
            {
                texto_copiar(nomes_sel[n], nome);
                n++;
            }
        }
    }

    selecao(nomes_sel, n);

    char linha[MAX_TEXTO];
    if (fgets(linha, sizeof(linha), stdin) != NULL)
    {
    }

    bol fim_busca = false;
    while (!fim_busca && fgets(linha, sizeof(linha), stdin) != NULL)
    {
        int t = 0;
        while (linha[t] != '\0')
            t++;
        if (t > 0 && linha[t - 1] == '\n')
            linha[t - 1] = '\0';
        if (t > 0 && linha[t - 1] == '\r')
            linha[t - 1] = '\0';

        if (strcmp(linha, "FIM") == 0)
        {
            fim_busca = true;
        }
        else
        {
            if (busca_binaria(nomes_sel, n, linha, &comparacoes))
            {
                printf("SIM\n");
            }
            else
            {
                printf("NAO\n");
            }
        }
    }

    int i = 0;
    while (i < c.tamanho)
    {
        free(c.restaurantes[i]);
        i++;
    }
    free(c.restaurantes);

    clock_t tempo_fim = clock();
    double tempo = ((double)(tempo_fim - inicio)) / CLOCKS_PER_SEC;

    FILE *log = fopen("884985_binaria.txt", "w");
    if (log != NULL)
    {
        fprintf(log, "884985\t%d\t%f", comparacoes, tempo);
        fclose(log);
    }

    return 0;
}