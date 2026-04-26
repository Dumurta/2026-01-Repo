#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINHA_CSV 1024
#define MAX_TEXTO 256
#define MAX_TIPOS_COZINHA 10
#define MAX_REGISTROS 1000
#define PATH_CSV_LINUX "/tmp/restaurantes.csv"

typedef int bol;
#define true 1
#define false 0

typedef struct
{
    int ano, mes, dia;
} Data;
typedef struct
{
    int hora, minuto;
} Hora;

typedef struct
{
    int id, capacidade, quantidade_tipos, faixa_preco;
    char nome[MAX_TEXTO], cidade[MAX_TEXTO];
    double avaliacao;
    char tipos_cozinha[MAX_TIPOS_COZINHA][MAX_TEXTO];
    Hora horario_abertura, horario_fechamento;
    Data data_abertura;
    bol aberto;
} Restaurante;

typedef struct
{
    int tamanho;
    Restaurante **restaurantes;
} ColecaoRestaurantes;

Data parse_data(char *s)
{
    Data d = {0, 0, 0};
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

void formatar_data(Data *d, char *b) { sprintf(b, "%02d/%02d/%04d", d->dia, d->mes, d->ano); }

Hora parse_hora(char *s)
{
    Hora h = {0, 0};
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

void formatar_hora(Hora *h, char *b) { sprintf(b, "%02d:%02d", h->hora, h->minuto); }

int contar_faixa(char *s)
{
    int t = 0;
    for (int i = 0; s[i] != '\0'; i++)
        if (s[i] == '$')
            t++;
    return t;
}

void copiar_tipos(Restaurante *r, char *s)
{
    int i = 0, j = 0, q = 0;
    while (s[i] != '\0' && q < MAX_TIPOS_COZINHA)
    {
        if (s[i] == ';')
        {
            r->tipos_cozinha[q][j] = '\0';
            q++;
            j = 0;
        }
        else
        {
            r->tipos_cozinha[q][j++] = s[i];
        }
        i++;
    }
    r->tipos_cozinha[q][j] = '\0';
    r->quantidade_tipos = q + 1;
}

Restaurante *parse_restaurante(char *s)
{
    Restaurante *r = (Restaurante *)malloc(sizeof(Restaurante));
    char t[MAX_TEXTO], f[10], hr[20], dt[20], ab[10];
    sscanf(s, "%d,%255[^,],%255[^,],%d,%lf,%255[^,],%9[^,],%19[^,],%19[^,],%9s",
           &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao, t, f, hr, dt, ab);
    copiar_tipos(r, t);
    r->faixa_preco = contar_faixa(f);
    int h1, m1, h2, m2;
    sscanf(hr, "%d:%d-%d:%d", &h1, &m1, &h2, &m2);
    r->horario_abertura.hora = h1;
    r->horario_abertura.minuto = m1;
    r->horario_fechamento.hora = h2;
    r->horario_fechamento.minuto = m2;
    r->data_abertura = parse_data(dt);
    r->aberto = (strcmp(ab, "true") == 0);
    return r;
}

void formatar_restaurante(Restaurante *r, char *b)
{
    char ts[MAX_TEXTO] = "[", fs[5], ha[6], hf[6], d[11];
    for (int i = 0; i < r->quantidade_tipos; i++)
    {
        if (i > 0)
            strcat(ts, ", ");
        strcat(ts, r->tipos_cozinha[i]);
    }
    strcat(ts, "]");
    if (r->faixa_preco == 1)
        strcpy(fs, "$");
    else if (r->faixa_preco == 2)
        strcpy(fs, "$$");
    else if (r->faixa_preco == 3)
        strcpy(fs, "$$$");
    else
        strcpy(fs, "$$$$");
    formatar_hora(&r->horario_abertura, ha);
    formatar_hora(&r->horario_fechamento, hf);
    formatar_data(&r->data_abertura, d);
    sprintf(b, "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]",
            r->id, r->nome, r->cidade, r->capacidade, r->avaliacao, ts, fs, ha, hf, d, r->aberto ? "true" : "false");
}

int main()
{
    char l[MAX_LINHA_CSV];
    FILE *a = fopen(PATH_CSV_LINUX, "r");
    if (!a)
        a = fopen("C:/tmp/restaurantes.csv", "r");
    Restaurante *db[MAX_REGISTROS];
    int total = 0;
    if (a)
    {
        fgets(l, sizeof(l), a);
        while (fgets(l, sizeof(l), a))
            db[total++] = parse_restaurante(l);
        fclose(a);
    }
    int id;
    bol encerrar = false;
    while (scanf("%d", &id) == 1 && !encerrar)
    {
        if (id == -1)
            encerrar = true;
        else
        {
            for (int i = 0; i < total; i++)
            {
                if (db[i]->id == id)
                {
                    char out[MAX_LINHA_CSV];
                    formatar_restaurante(db[i], out);
                    printf("%s\n", out);
                }
            }
        }
    }
    return 0;
}