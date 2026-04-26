#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_LINHA_CSV 1024
#define MAX_TEXTO 256
#define MAX_TIPOS_COZINHA 10
#define MAX_REGISTROS 600
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

void texto_concatenar(char *d, char *o)
{
    int i = 0, j = 0;
    while (d[j] != '\0')
        j++;
    while (o[i] != '\0')
    {
        d[j] = o[i];
        i++;
        j++;
    }
    d[j] = '\0';
}

Data parse_data(char *s)
{
    Data d;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

void formatar_data(Data *d, char *b) { sprintf(b, "%02d/%02d/%04d", d->dia, d->mes, d->ano); }

Hora parse_hora(char *s)
{
    Hora h;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

void formatar_hora(Hora *h, char *b) { sprintf(b, "%02d:%02d", h->hora, h->minuto); }

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
            r->tipos_cozinha[q][j] = s[i];
            j++;
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
    r->faixa_preco = 0;
    for (int i = 0; f[i] != '\0'; i++)
        if (f[i] == '$')
            r->faixa_preco++;
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
    char ts[MAX_TEXTO] = "[", fs[10] = "", ha[10], hf[10], d[20];
    for (int i = 0; i < r->quantidade_tipos; i++)
    {
        if (i > 0)
            texto_concatenar(ts, ", ");
        texto_concatenar(ts, r->tipos_cozinha[i]);
    }
    texto_concatenar(ts, "]");
    for (int i = 0; i < r->faixa_preco; i++)
        fs[i] = '$';
    fs[r->faixa_preco] = '\0';
    formatar_hora(&r->horario_abertura, ha);
    formatar_hora(&r->horario_fechamento, hf);
    formatar_data(&r->data_abertura, d);
    sprintf(b, "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]",
            r->id, r->nome, r->cidade, r->capacidade, r->avaliacao, ts, fs, ha, hf, d, r->aberto ? "true" : "false");
}

void selecao(Restaurante **v, int n, int *c, int *m)
{
    for (int i = 0; i < n - 1; i++)
    {
        int menor = i;
        for (int j = i + 1; j < n; j++)
        {
            (*c)++;
            if (strcmp(v[j]->nome, v[menor]->nome) < 0)
                menor = j;
        }
        Restaurante *t = v[i];
        v[i] = v[menor];
        v[menor] = t;
        (*m) += 3;
    }
}

int main()
{
    int comp = 0, mov = 0;
    clock_t t_inicio = clock();
    FILE *arq = fopen(PATH_CSV_LINUX, "r");
    if (!arq)
        arq = fopen("C:/tmp/restaurantes.csv", "r");

    Restaurante *base[1000];
    int total_base = 0;
    char linha[MAX_LINHA_CSV];
    if (arq)
    {
        fgets(linha, sizeof(linha), arq);
        while (fgets(linha, sizeof(linha), arq))
            base[total_base++] = parse_restaurante(linha);
        fclose(arq);
    }

    Restaurante *sel[MAX_REGISTROS];
    int n = 0, id;
    while (scanf("%d", &id) == 1 && id != -1)
    {
        for (int i = 0; i < total_base; i++)
            if (base[i]->id == id)
            {
                sel[n++] = base[i];
                break;
            }
    }

    selecao(sel, n, &comp, &mov);

    for (int i = 0; i < n; i++)
    {
        char out[MAX_LINHA_CSV];
        formatar_restaurante(sel[i], out);
        printf("%s\n", out);
    }

    clock_t t_fim = clock();
    double tempo = ((double)(t_fim - t_inicio)) / CLOCKS_PER_SEC;
    FILE *log = fopen("884985_selecao.txt", "w");
    fprintf(log, "884985\t%d\t%d\t%f", comp, mov, tempo);
    fclose(log);
    return 0;
}