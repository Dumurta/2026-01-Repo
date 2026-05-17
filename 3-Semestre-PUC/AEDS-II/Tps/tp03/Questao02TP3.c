#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX_LINHA_CSV 1024
#define MAX_TEXTO 256
#define MAX_TIPOS_COZINHA 10
#define MAX_REGISTROS 600
#define PATH_CSV "tmp/restaurantes.csv"

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

bool igual(char *a, char *b)
{
    bool resp = true;
    int i = 0;
    if (texto_tamanho(a) != texto_tamanho(b))
    {
        resp = false;
    }
    while (i < texto_tamanho(a) && resp)
    {
        if (a[i] != b[i])
        {
            resp = false;
        }
        i++;
    }
    return resp;
}

int para_int(char *s)
{
    int i = 0;
    int valor = 0;
    int sinal = 1;
    if (texto_tamanho(s) > 0 && s[0] == '-')
    {
        sinal = -1;
        i = 1;
    }
    while (i < texto_tamanho(s))
    {
        if (s[i] >= '0' && s[i] <= '9')
        {
            valor = valor * 10 + (s[i] - '0');
        }
        i++;
    }
    return valor * sinal;
}

double para_double(char *s)
{
    int i = 0;
    int parte_inteira = 0;
    int parte_decimal = 0;
    int fator = 1;
    bool decimal = false;
    while (i < texto_tamanho(s))
    {
        char c = s[i];
        if (c == '.')
        {
            decimal = true;
        }
        else if (c >= '0' && c <= '9')
        {
            if (!decimal)
            {
                parte_inteira = parte_inteira * 10 + (c - '0');
            }
            else
            {
                parte_decimal = parte_decimal * 10 + (c - '0');
                fator = fator * 10;
            }
        }
        i++;
    }
    return parte_inteira + ((double)parte_decimal / fator);
}

void separar(char *s, char sep, char resultado[][MAX_TEXTO], int *total)
{
    int i = 0;
    int idx = 0;
    int j = 0;
    char atual[MAX_TEXTO];
    while (i < texto_tamanho(s))
    {
        if (s[i] == sep)
        {
            atual[j] = '\0';
            texto_copiar(resultado[idx], atual);
            idx++;
            j = 0;
        }
        else
        {
            atual[j] = s[i];
            j++;
        }
        i++;
    }
    atual[j] = '\0';
    texto_copiar(resultado[idx], atual);
    idx++;
    *total = idx;
}

int pos_char(char *s, char alvo)
{
    int i = 0;
    int pos = -1;
    while (i < texto_tamanho(s) && pos == -1)
    {
        if (s[i] == alvo)
        {
            pos = i;
        }
        i++;
    }
    return pos;
}

void faixa(char *s, int ini, int fim, char *resp)
{
    int i = ini;
    int j = 0;
    while (i < fim)
    {
        resp[j] = s[i];
        i++;
        j++;
    }
    resp[j] = '\0';
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
    int t = 0;
    int i = 0;
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
    q++;
    r->quantidade_tipos = q;
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
                if (l[j] == '\n')
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

long comparacoes = 0;
long movimentacoes = 0;

void insercao_parcial(Restaurante **v, int n, int k)
{
    int limite = k;
    if (n < k)
    {
        limite = n;
    }
    int i = 1;
    while (i < limite)
    {
        Restaurante *chave = v[i];
        int j = i - 1;
        while (j >= 0 && strcmp(v[j]->cidade, chave->cidade) > 0)
        {
            comparacoes++;
            v[j + 1] = v[j];
            movimentacoes++;
            j--;
        }
        comparacoes++;
        v[j + 1] = chave;
        i++;
    }
    i = limite;
    while (i < n)
    {
        if (strcmp(v[i]->cidade, v[limite - 1]->cidade) < 0)
        {
            comparacoes++;
            Restaurante *chave = v[i];
            int j = limite - 1;
while (j >= 0 && strcmp(v[j]->cidade, chave->cidade) > 0)
{
    comparacoes++;
    if (j + 1 < limite) {
        v[j + 1] = v[j];
        movimentacoes++;
    }
    j--;
}
v[j + 1] = chave;
movimentacoes++;
        }
        else
        {
            comparacoes++;
        }
        i++;
    }
}

int main()
{
    ColecaoRestaurantes *c = ler_csv();
    Restaurante *sel[MAX_REGISTROS];
    int n = 0, id = 0;
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
    insercao_parcial(sel, n, 10);
    clock_t fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;

    int i = 0;
    while (i < n)
    {
        char b[MAX_LINHA_CSV];
        formatar_restaurante(sel[i], b);
        printf("%s\n", b);
        i++;
    }

    liberar_colecao(c);

    FILE *log = fopen("884985_insercao_parcial.txt", "w");
    fprintf(log, "884985\t%ld\t%ld\t%.2f\n", comparacoes, movimentacoes, tempo);
    fclose(log);

    return 0;
}