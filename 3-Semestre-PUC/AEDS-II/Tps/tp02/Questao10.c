#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_LINHA_CSV 1024
#define MAX_TEXTO 256
#define MAX_TIPOS_COZINHA 10
#define MAX_REGISTROS 600
#define PATH_CSV_LINUX "/tmp/restaurantes.csv"
#define PATH_CSV_WINDOWS "C:/tmp/restaurantes.csv"

int texto_tamanho(char* s) { int i = 0; while (s[i] != '\0') { i++; } return i; }
void texto_copiar(char* d, char* o) { int i = 0; while (o[i] != '\0') { d[i] = o[i]; i++; } d[i] = '\0'; }
void texto_concatenar(char* d, char* o) { int i = 0; int j = texto_tamanho(d); while (o[i] != '\0') { d[j] = o[i]; i++; j++; } d[j] = '\0'; }

typedef struct { int ano, mes, dia; } Data;
typedef struct { int hora, minuto; } Hora;
typedef struct {
    int id, capacidade, quantidade_tipos, faixa_preco;
    char nome[MAX_TEXTO], cidade[MAX_TEXTO];
    double avaliacao;
    char tipos_cozinha[MAX_TIPOS_COZINHA][MAX_TEXTO];
    Hora horario_abertura, horario_fechamento;
    Data data_abertura;
    bool aberto;
} Restaurante;
typedef struct { int tamanho; Restaurante** restaurantes; } ColecaoRestaurantes;

Data parse_data(char* s) { Data d; d.ano = 0; d.mes = 0; d.dia = 0; sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia); return d; }
void formatar_data(Data* d, char* b) { sprintf(b, "%02d/%02d/%04d", d->dia, d->mes, d->ano); }
Hora parse_hora(char* s) { Hora h; h.hora = 0; h.minuto = 0; sscanf(s, "%d:%d", &h.hora, &h.minuto); return h; }
void formatar_hora(Hora* h, char* b) { sprintf(b, "%02d:%02d", h->hora, h->minuto); }
int contar_faixa_preco(char* f) { int t = 0; int i = 0; while (f[i] != '\0') { if (f[i] == '$') { t++; } i++; } return t; }
void copiar_tipos(Restaurante* r, char* s) { int i = 0; int j = 0; int q = 0; while (s[i] != '\0' && q < MAX_TIPOS_COZINHA) { if (s[i] == ';') { r->tipos_cozinha[q][j] = '\0'; q++; j = 0; } else if (j < MAX_TEXTO - 1) { r->tipos_cozinha[q][j] = s[i]; j++; } i++; } r->tipos_cozinha[q][j] = '\0'; q++; r->quantidade_tipos = q; }
Restaurante* parse_restaurante(char* s) {
    Restaurante* r = (Restaurante*)malloc(sizeof(Restaurante)); char tipos[MAX_TEXTO], faixa[10], horario[20], data[20], aberto[10]; int h1 = 0, m1 = 0, h2 = 0, m2 = 0;
    sscanf(s, "%d,%255[^,],%255[^,],%d,%lf,%255[^,],%9[^,],%19[^,],%19[^,],%9s", &r->id, r->nome, r->cidade, &r->capacidade, &r->avaliacao, tipos, faixa, horario, data, aberto);
    copiar_tipos(r, tipos); r->faixa_preco = contar_faixa_preco(faixa);
    sscanf(horario, "%d:%d-%d:%d", &h1, &m1, &h2, &m2); r->horario_abertura.hora = h1; r->horario_abertura.minuto = m1; r->horario_fechamento.hora = h2; r->horario_fechamento.minuto = m2;
    r->data_abertura = parse_data(data); r->aberto = strcmp(aberto, "true") == 0; return r;
}
void faixa_para_txt(int f, char* b) { texto_copiar(b, "$"); if (f == 2) { texto_copiar(b, "$$"); } else if (f == 3) { texto_copiar(b, "$$$"); } else if (f == 4) { texto_copiar(b, "$$$$"); } }
void formatar_tipos(Restaurante* r, char* b) { int i = 0; texto_copiar(b, "["); while (i < r->quantidade_tipos) { if (i > 0) { texto_concatenar(b, ","); } texto_concatenar(b, r->tipos_cozinha[i]); i++; } texto_concatenar(b, "]"); }
void formatar_restaurante(Restaurante* r, char* b) { char t[MAX_TEXTO], f[5], ha[6], hf[6], d[11]; formatar_tipos(r, t); faixa_para_txt(r->faixa_preco, f); formatar_hora(&r->horario_abertura, ha); formatar_hora(&r->horario_fechamento, hf); formatar_data(&r->data_abertura, d); sprintf(b, "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]", r->id, r->nome, r->cidade, r->capacidade, r->avaliacao, t, f, ha, hf, d, r->aberto ? "true" : "false"); }
int contar_registros(char* p) { int n = 0; char l[MAX_LINHA_CSV]; FILE* a = fopen(p, "r"); if (a != NULL) { if (fgets(l, sizeof(l), a) != NULL) {} while (fgets(l, sizeof(l), a) != NULL) { if (l[0] != '\0' && l[0] != '\n') { n++; } } fclose(a); } return n; }
void ler_csv_colecao(ColecaoRestaurantes* c, char* p) { c->tamanho = contar_registros(p); c->restaurantes = (Restaurante**)malloc(sizeof(Restaurante*) * c->tamanho); char l[MAX_LINHA_CSV]; FILE* a = fopen(p, "r"); int i = 0; if (a != NULL) { if (fgets(l, sizeof(l), a) != NULL) {} while (fgets(l, sizeof(l), a) != NULL && i < c->tamanho) { int j = 0; while (l[j] != '\0') { if (l[j] == '\n') { l[j] = '\0'; } j++; } if (l[0] != '\0') { c->restaurantes[i] = parse_restaurante(l); i++; } } fclose(a); } }
ColecaoRestaurantes* ler_csv() { ColecaoRestaurantes* c = (ColecaoRestaurantes*)malloc(sizeof(ColecaoRestaurantes)); FILE* a = fopen(PATH_CSV_LINUX, "r"); if (a != NULL) { fclose(a); ler_csv_colecao(c, (char*)PATH_CSV_LINUX); } else { ler_csv_colecao(c, (char*)PATH_CSV_WINDOWS); } return c; }
Restaurante* buscar_por_id(ColecaoRestaurantes* c, int id) { Restaurante* r = NULL; int i = 0; while (i < c->tamanho && r == NULL) { if (c->restaurantes[i]->id == id) { r = c->restaurantes[i]; } i++; } return r; }
void liberar_colecao(ColecaoRestaurantes* c) { int i = 0; while (i < c->tamanho) { free(c->restaurantes[i]); i++; } free(c->restaurantes); free(c); }

void counting_sort_capacidade(Restaurante** v, int n) {
    int max = 0; int i = 0;
    while (i < n) { if (v[i]->capacidade > max) { max = v[i]->capacidade; } i++; }
    int* cont = (int*)calloc(max + 1, sizeof(int));
    Restaurante** out = (Restaurante**)malloc(sizeof(Restaurante*) * n);
    i = 0; while (i < n) { cont[v[i]->capacidade]++; i++; }
    i = 1; while (i <= max) { cont[i] += cont[i - 1]; i++; }
    i = n - 1; while (i >= 0) { out[cont[v[i]->capacidade] - 1] = v[i]; cont[v[i]->capacidade]--; i--; }
    i = 0; while (i < n) { v[i] = out[i]; i++; }
    free(cont); free(out);
}

int main() {
    ColecaoRestaurantes* c = ler_csv();
    Restaurante* sel[MAX_REGISTROS];
    int n = 0, id = 0; bool fim = false;
    while (scanf("%d", &id) == 1 && !fim) { if (id == -1) { fim = true; } else { sel[n] = buscar_por_id(c, id); n++; } }
    counting_sort_capacidade(sel, n);
    int i = 0; while (i < n) { char b[MAX_LINHA_CSV]; formatar_restaurante(sel[i], b); printf("%s\n", b); i++; }
    liberar_colecao(c);
    return 0;
}
