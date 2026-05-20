#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX_LINHA_CSV 1024
#define MAX_TEXTO 256
#define MAX_TIPOS_COZINHA 10
#define MAX_REGISTROS 600
#define PATH_CSV "/tmp/restaurantes.csv"

int texto_tamanho(char *s)
{
    int i = 0;
    while (s[i] != '\0')
    {
        i++;
    }
    return i;
}

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
    int i = 0;
    int j = texto_tamanho(d);
    while (o[i] != '\0')
    {
        d[j] = o[i];
        i++;
        j++;
    }
    d[j] = '\0';
}

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
    bool aberto;
} Restaurante;

typedef struct
{
    int tamanho;
    Restaurante **restaurantes;
} ColecaoRestaurantes;

Data parse_data(char *s)
{
    Data d;
    d.ano = 0;
    d.mes = 0;
    d.dia = 0;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

void formatar_data(Data *d, char *b)
{
    sprintf(b, "%02d/%02d/%04d", d->dia, d->mes, d->ano);
}

Hora parse_hora(char *s)
{
    Hora h;
    h.hora = 0;
    h.minuto = 0;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

void formatar_hora(Hora *h, char *b)
{
    sprintf(b, "%02d:%02d", h->hora, h->minuto);
}

int contar_faixa_preco(char *f)
{
    int t = 0, i = 0;
    while (f[i] != '\0')
    {
        if (f[i] == '$')
        {
            t++;
        }
        i++;
    }
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
        else if (j < MAX_TEXTO - 1)
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
    char tipos[MAX_TEXTO], faixa[10], horario[20], data[20], aberto[10];
    int h1 = 0, m1 = 0, h2 = 0, m2 = 0;
    sscanf(s, "%d,%255[^,],%255[^,],%d,%lf,%255[^,],%9[^,],%19[^,],%19[^,],%9s",
           &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao,
           tipos, faixa, horario, data, aberto);
    copiar_tipos(r, tipos);
    r->faixa_preco = contar_faixa_preco(faixa);
    sscanf(horario, "%d:%d-%d:%d", &h1, &m1, &h2, &m2);
    r->horario_abertura.hora = h1;
    r->horario_abertura.minuto = m1;
    r->horario_fechamento.hora = h2;
    r->horario_fechamento.minuto = m2;
    r->data_abertura = parse_data(data);
    r->aberto = strcmp(aberto, "true") == 0;
    return r;
}

void faixa_para_txt(int f, char *b)
{
    texto_copiar(b, "$");
    if (f == 2)
    {
        texto_copiar(b, "$$");
    }
    else if (f == 3)
    {
        texto_copiar(b, "$$$");
    }
    else if (f == 4)
    {
        texto_copiar(b, "$$$$");
    }
}

void formatar_tipos(Restaurante *r, char *b)
{
    int i = 0;
    texto_copiar(b, "[");
    while (i < r->quantidade_tipos)
    {
        if (i > 0)
        {
            texto_concatenar(b, ",");
        }
        texto_concatenar(b, r->tipos_cozinha[i]);
        i++;
    }
    texto_concatenar(b, "]");
}

void formatar_restaurante(Restaurante *r, char *b)
{
    char t[MAX_TEXTO], f[5], ha[6], hf[6], d[11];
    formatar_tipos(r, t);
    faixa_para_txt(r->faixa_preco, f);
    formatar_hora(&r->horario_abertura, ha);
    formatar_hora(&r->horario_fechamento, hf);
    formatar_data(&r->data_abertura, d);
    sprintf(b, "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]",
            r->id, r->nome, r->cidade, r->capacidade, r->avaliacao,
            t, f, ha, hf, d, r->aberto ? "true" : "false");
}

int contar_registros(char *p)
{
    int n = 0;
    char l[MAX_LINHA_CSV];
    FILE *a = fopen(p, "r");
    if (a != NULL)
    {
        fgets(l, sizeof(l), a);
        while (fgets(l, sizeof(l), a) != NULL)
        {
            if (l[0] != '\0' && l[0] != '\n')
            {
                n++;
            }
        }
        fclose(a);
    }
    return n;
}

void ler_csv_colecao(ColecaoRestaurantes *c, char *p)
{
    c->tamanho = contar_registros(p);
    c->restaurantes = (Restaurante **)malloc(sizeof(Restaurante *) * c->tamanho);
    char l[MAX_LINHA_CSV];
    FILE *a = fopen(p, "r");
    int i = 0;
    if (a != NULL)
    {
        fgets(l, sizeof(l), a);
        while (fgets(l, sizeof(l), a) != NULL && i < c->tamanho)
        {
            int j = 0;
            while (l[j] != '\0')
            {
                if (l[j] == '\n' || l[j] == '\r')
                {
                    l[j] = '\0';
                }
                j++;
            }
            if (l[0] != '\0')
            {
                c->restaurantes[i] = parse_restaurante(l);
                i++;
            }
        }
        fclose(a);
    }
}

ColecaoRestaurantes *ler_csv()
{
    ColecaoRestaurantes *c = (ColecaoRestaurantes *)malloc(sizeof(ColecaoRestaurantes));
    ler_csv_colecao(c, PATH_CSV);
    return c;
}

Restaurante *buscar_por_id(ColecaoRestaurantes *c, int id)
{
    Restaurante *r = NULL;
    int i = 0;
    while (i < c->tamanho && r == NULL)
    {
        if (c->restaurantes[i]->id == id)
        {
            r = c->restaurantes[i];
        }
        i++;
    }
    return r;
}

void liberar_colecao(ColecaoRestaurantes *c)
{
    int i = 0;
    while (i < c->tamanho)
    {
        free(c->restaurantes[i]);
        i++;
    }
    free(c->restaurantes);
    free(c);
}

int comparar_data(Data *a, Data *b)
{
    int resp = 0;
    if (a->ano != b->ano)
    {
        resp = a->ano - b->ano;
    }
    else if (a->mes != b->mes)
    {
        resp = a->mes - b->mes;
    }
    else
    {
        resp = a->dia - b->dia;
    }
    return resp;
}

int comparar_restaurante(Restaurante *a, Restaurante *b)
{
    int resp = comparar_data(&a->data_abertura, &b->data_abertura);
    if (resp == 0)
    {
        resp = strcmp(a->nome, b->nome);
    }
    return resp;
}

static void heapify_aux(Restaurante **v, int n, int i, long *comp, long *mov)
{
    int maior = i;
    int esq = 2 * i + 1;
    int dir = 2 * i + 2;

    if (esq < n)
    {
        *comp += 1;
        if (comparar_restaurante(v[esq], v[maior]) > 0)
        {
            maior = esq;
        }
    }
    if (dir < n)
    {
        *comp += 1;
        if (comparar_restaurante(v[dir], v[maior]) > 0)
        {
            maior = dir;
        }
    }
    if (maior != i)
    {
        Restaurante *temp = v[i];
        v[i] = v[maior];
        v[maior] = temp;
        *mov += 3;
        heapify_aux(v, n, maior, comp, mov);
    }
}

void heapify(Restaurante **v, int n, int i, long *comp, long *mov)
{
    heapify_aux(v, n, i, comp, mov);
}

void heapsort_parcial(Restaurante **v, int n, int k, long *comp, long *mov)
{
    int limite = k;
    if (n < k)
    {
        limite = n;
    }

    int i = limite / 2 - 1;
    while (i >= 0)
    {
        heapify(v, limite, i, comp, mov);
        i--;
    }

    i = limite;
    while (i < n)
    {
        *comp += 1;
        if (comparar_restaurante(v[i], v[0]) < 0)
        {
            Restaurante *temp = v[i];
            v[i] = v[0];
            v[0] = temp;
            *mov += 3;
            heapify(v, limite, 0, comp, mov);
        }
        i++;
    }

    i = limite - 1;
    while (i > 0)
    {
        Restaurante *temp = v[0];
        v[0] = v[i];
        v[i] = temp;
        *mov += 3;
        heapify(v, i, 0, comp, mov);
        i--;
    }
}

int main()
{
    ColecaoRestaurantes *c = ler_csv();
    Restaurante *sel[MAX_REGISTROS];
    int n = 0, id = 0;
    long comp = 0, mov = 0;

    while (scanf("%d", &id) == 1 && id != -1)
    {
        Restaurante *r = buscar_por_id(c, id);
        if (r != NULL)
        {
            sel[n] = r;
            n++;
        }
    }

    clock_t inicio = clock();
    heapsort_parcial(sel, n, 10, &comp, &mov);
    clock_t fim_clock = clock();
    double tempo = ((double)(fim_clock - inicio)) / CLOCKS_PER_SEC;

    int i = 0;
    while (i < n)
    {
        char b[MAX_LINHA_CSV];
        formatar_restaurante(sel[i], b);
        printf("%s\n", b);
        i++;
    }

    liberar_colecao(c);

    FILE *log = fopen("884985_heapsort_parcial.txt", "w");
    fprintf(log, "884985\t%ld\t%ld\t%.2f\n", comp, mov, tempo);
    fclose(log);

    return 0;
}