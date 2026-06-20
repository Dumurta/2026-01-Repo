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
        i++;
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
    int i = 0, j = texto_tamanho(d);
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
int contar_faixa_preco(char *f)
{
    int t = 0, i = 0;
    while (f[i] != '\0')
    {
        if (f[i] == '$')
            t++;
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
           &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao, tipos, faixa, horario, data, aberto);
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
        texto_copiar(b, "$$");
    else if (f == 3)
        texto_copiar(b, "$$$");
    else if (f == 4)
        texto_copiar(b, "$$$$");
}
void formatar_tipos(Restaurante *r, char *b)
{
    int i = 0;
    texto_copiar(b, "[");
    while (i < r->quantidade_tipos)
    {
        if (i > 0)
            texto_concatenar(b, ",");
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
            r->id, r->nome, r->cidade, r->capacidade, r->avaliacao, t, f, ha, hf, d, r->aberto ? "true" : "false");
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
                n++;
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
                    l[j] = '\0';
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
    int i = 0;
    Restaurante *r = NULL;
    while (i < c->tamanho && r == NULL)
    {
        if (c->restaurantes[i]->id == id)
            r = c->restaurantes[i];
        i++;
    }
    return r;
}

/* ---- Arvore Bicolor (Red-Black) ---- */
typedef enum
{
    VERMELHO,
    PRETO
} Cor;
typedef struct NoRB
{
    Restaurante *restaurante;
    struct NoRB *esq, *dir, *pai;
    Cor cor;
} NoRB;

NoRB *NULO_RB;
long comparacoes = 0;

void inicializar_nulo()
{
    NULO_RB = (NoRB *)malloc(sizeof(NoRB));
    NULO_RB->cor = PRETO;
    NULO_RB->esq = NULO_RB->dir = NULO_RB->pai = NULL;
    NULO_RB->restaurante = NULL;
}


/* Rotacao top-down: usa ponteiros pai da estrutura para corretude */
static void rotacionar_td(NoRB **raiz, NoRB *i)
{
    NoRB *pai = i->pai;
    NoRB *avo = pai->pai;
    NoRB *bisavo = avo->pai;
    NoRB *novo;
    if (avo->dir == pai)
    {
        if (pai->dir == i)
        {
            /* RR: rotacao simples esquerda em avo */
            avo->dir = pai->esq;
            if (pai->esq != NULO_RB) pai->esq->pai = avo;
            pai->esq = avo;
            avo->pai = pai;
            novo = pai;
        }
        else
        {
            /* RL: rotacao dupla (dir em pai, esq em avo) */
            pai->esq = i->dir;
            if (i->dir != NULO_RB) i->dir->pai = pai;
            i->dir = pai;
            pai->pai = i;
            avo->dir = i->esq;
            if (i->esq != NULO_RB) i->esq->pai = avo;
            i->esq = avo;
            avo->pai = i;
            novo = i;
        }
    }
    else
    {
        if (pai->esq == i)
        {
            /* LL: rotacao simples direita em avo */
            avo->esq = pai->dir;
            if (pai->dir != NULO_RB) pai->dir->pai = avo;
            pai->dir = avo;
            avo->pai = pai;
            novo = pai;
        }
        else
        {
            /* LR: rotacao dupla (esq em pai, dir em avo) */
            pai->dir = i->esq;
            if (i->esq != NULO_RB) i->esq->pai = pai;
            i->esq = pai;
            pai->pai = i;
            avo->esq = i->dir;
            if (i->dir != NULO_RB) i->dir->pai = avo;
            i->dir = avo;
            avo->pai = i;
            novo = i;
        }
    }
    /* Apos rotacao: novo nao-colorido (preto), filhos coloridos (vermelhos) */
    novo->cor = PRETO;
    if (novo->esq != NULO_RB) novo->esq->cor = VERMELHO;
    if (novo->dir != NULO_RB) novo->dir->cor = VERMELHO;
    novo->pai = bisavo;
    if (bisavo == NULO_RB)
        *raiz = novo;
    else if (bisavo->esq == avo)
        bisavo->esq = novo;
    else
        bisavo->dir = novo;
}

/* Insercao top-down: fragmenta nos-4 durante a descida */
static void inserir_rec(NoRB **raiz, Restaurante *r, NoRB *bisavo, NoRB *avo, NoRB *pai, NoRB *cur)
{
    if (cur == NULO_RB)
    {
        /* Posicao encontrada: inserir como folha colorida (vermelha) */
        NoRB *novo = (NoRB *)malloc(sizeof(NoRB));
        novo->restaurante = r;
        novo->cor = VERMELHO;
        novo->esq = novo->dir = NULO_RB;
        novo->pai = pai;
        if (pai == NULO_RB)
            *raiz = novo;
        else if (strcmp(r->nome, pai->restaurante->nome) < 0)
            pai->esq = novo;
        else
            pai->dir = novo;
        /* Se pai e colorido (vermelho) -> rotacionar */
        if (pai != NULO_RB && pai->cor == VERMELHO)
            rotacionar_td(raiz, novo);
        return;
    }
    /* Verificar no-4 e fragmentar antes de descer */
    if (cur->esq->cor == VERMELHO && cur->dir->cor == VERMELHO)
    {
        cur->cor = VERMELHO;
        cur->esq->cor = PRETO;
        cur->dir->cor = PRETO;
        /* Se pai e colorido (vermelho) -> rotacionar */
        if (pai != NULO_RB && pai->cor == VERMELHO)
            rotacionar_td(raiz, cur);
    }
    /* Descer recursivamente */
    int cmp = strcmp(r->nome, cur->restaurante->nome);
    if (cmp < 0)
        inserir_rec(raiz, r, avo, pai, cur, cur->esq);
    else if (cmp > 0)
        inserir_rec(raiz, r, avo, pai, cur, cur->dir);
    /* cmp == 0: duplicata, ignorar */
}

void inserir(NoRB **raiz, Restaurante *r)
{
    if (*raiz == NULO_RB)
    {
        NoRB *novo = (NoRB *)malloc(sizeof(NoRB));
        novo->restaurante = r;
        novo->cor = PRETO;
        novo->esq = novo->dir = novo->pai = NULO_RB;
        *raiz = novo;
        return;
    }
    inserir_rec(raiz, r, NULO_RB, NULO_RB, NULO_RB, *raiz);
    (*raiz)->cor = PRETO;
}
void pesquisar(NoRB *no, char *nome, char *caminho)
{
    if (no == NULO_RB)
    {
        printf("%s NAO\n", caminho);
    }
    else
    {
        comparacoes++;
        int cmp = strcmp(nome, no->restaurante->nome);
        if (cmp == 0)
        {
            printf("%s SIM\n", caminho);
        }
        else if (cmp < 0)
        {
            char nc[MAX_LINHA_CSV];
            sprintf(nc, "%s esq", caminho);
            pesquisar(no->esq, nome, nc);
        }
        else
        {
            char nc[MAX_LINHA_CSV];
            sprintf(nc, "%s dir", caminho);
            pesquisar(no->dir, nome, nc);
        }
    }
}
void em_ordem(NoRB *no)
{
    if (no != NULO_RB)
    {
        em_ordem(no->esq);
        char b[MAX_LINHA_CSV];
        formatar_restaurante(no->restaurante, b);
        printf("%s\n", b);
        em_ordem(no->dir);
    }
}

int main()
{
    inicializar_nulo();
    ColecaoRestaurantes *c = ler_csv();
    NoRB *raiz = NULO_RB;
    int id;
    scanf("%d", &id);
    while (id != -1)
    {
        Restaurante *r = buscar_por_id(c, id);
        if (r != NULL)
            inserir(&raiz, r);
        scanf("%d", &id);
    }
    clock_t inicio = clock();
    char linha[MAX_LINHA_CSV];
    fgets(linha, sizeof(linha), stdin);
    while (fgets(linha, sizeof(linha), stdin) != NULL)
    {
        int j = 0;
        while (linha[j] != '\0')
        {
            if (linha[j] == '\n' || linha[j] == '\r')
                linha[j] = '\0';
            j++;
        }
        if (strcmp(linha, "FIM") != 0 && texto_tamanho(linha) > 0)
            pesquisar(raiz, linha, "raiz");
    }
    clock_t fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;
    em_ordem(raiz);
    FILE *log = fopen("884985_arvore_bicolor.txt", "w");
    fprintf(log, "884985\t%ld\t%.2f\n", comparacoes, tempo);
    fclose(log);
    return 0;
}
