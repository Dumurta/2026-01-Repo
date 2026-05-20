#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX_TEXTO 256
#define MAX_TIPOS_COZINHA 10
#define MAX_LINHA_CSV 1024
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

typedef struct No
{
    Restaurante *restaurante;
    struct No *prox;
} No;
typedef struct
{
    No *inicio;
    No *fim;
    int tamanho;
} Lista;

Data parse_data(char *s)
{
    Data d;
    d.ano = 0;
    d.mes = 0;
    d.dia = 0;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}
void formatar_data(Data *d, char *b) { sprintf(b, "%02d/%02d/%04d", d->dia, d->mes, d->ano); }
Hora parse_hora(char *s)
{
    Hora h;
    h.hora = 0;
    h.minuto = 0;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}
void formatar_hora(Hora *h, char *b) { sprintf(b, "%02d:%02d", h->hora, h->minuto); }
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
    sscanf(s, "%d,%255[^,],%255[^,],%d,%lf,%255[^,],%9[^,],%19[^,],%19[^,],%9s", &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao, tipos, faixa, horario, data, aberto);
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
    sprintf(b, "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]", r->id, r->nome, r->cidade, r->capacidade, r->avaliacao, t, f, ha, hf, d, r->aberto ? "true" : "false");
}

typedef struct
{
    int tamanho;
    Restaurante **restaurantes;
} ColecaoRestaurantes;

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
ColecaoRestaurantes *ler_csv()
{
    ColecaoRestaurantes *c = (ColecaoRestaurantes *)malloc(sizeof(ColecaoRestaurantes));
    c->tamanho = contar_registros(PATH_CSV);
    c->restaurantes = (Restaurante **)malloc(sizeof(Restaurante *) * c->tamanho);
    char l[MAX_LINHA_CSV];
    FILE *a = fopen(PATH_CSV, "r");
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

Lista *criar_lista()
{
    Lista *l = (Lista *)malloc(sizeof(Lista));
    l->inicio = NULL;
    l->fim = NULL;
    l->tamanho = 0;
    return l;
}
void inserir_fim_lista(Lista *l, Restaurante *r)
{
    No *novo = (No *)malloc(sizeof(No));
    novo->restaurante = r;
    novo->prox = NULL;
    if (l->tamanho == 0)
    {
        l->inicio = novo;
        l->fim = novo;
    }
    else
    {
        l->fim->prox = novo;
        l->fim = novo;
    }
    l->tamanho++;
}

long comparacoes = 0;
long movimentacoes = 0;

void selecao_lista(Lista *l)
{
    No *i = l->inicio;
    while (i != NULL)
    {
        No *min = i;
        No *j = i->prox;
        while (j != NULL)
        {
            comparacoes++;
            if (strcmp(j->restaurante->nome, min->restaurante->nome) < 0)
            {
                min = j;
            }
            j = j->prox;
        }
        if (min != i)
        {
            Restaurante *tmp = i->restaurante;
            i->restaurante = min->restaurante;
            min->restaurante = tmp;
            movimentacoes += 3;
        }
        i = i->prox;
    }
}

int main()
{
    ColecaoRestaurantes *c = ler_csv();
    Lista *l = criar_lista();
    int id = 0;
    scanf("%d", &id);
    while (id != -1)
    {
        Restaurante *r = buscar_por_id(c, id);
        if (r != NULL)
        {
            inserir_fim_lista(l, r);
        }
        scanf("%d", &id);
    }
    clock_t inicio = clock();
    selecao_lista(l);
    clock_t fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;
    No *atual = l->inicio;
    while (atual != NULL)
    {
        char b[MAX_LINHA_CSV];
        formatar_restaurante(atual->restaurante, b);
        printf("%s\n", b);
        atual = atual->prox;
    }
    FILE *log = fopen("884985_selecao_flexivel.txt", "w");
    fprintf(log, "884985\t%ld\t%ld\t%.2f\n", comparacoes, movimentacoes, tempo);
    fclose(log);
    return 0;
}
