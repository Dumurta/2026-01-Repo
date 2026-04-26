#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_LINHA 1024
#define MAX_TEXTO 256
#define MAX_REGISTROS 600
#define PATH_CSV_LINUX "/tmp/restaurantes.csv"
#define PATH_CSV_WINDOWS "C:/tmp/restaurantes.csv"

int texto_tamanho(char* s) { int i = 0; while (s[i] != '\0') { i++; } return i; }
void texto_copiar(char* d, char* o) { int i = 0; while (o[i] != '\0') { d[i] = o[i]; i++; } d[i] = '\0'; }

typedef struct { int id; char nome[MAX_TEXTO]; } Restaurante;
typedef struct { int tamanho; Restaurante* restaurantes; } Colecao;

int contar_registros(char* p) {
    int n = 0; char l[MAX_LINHA]; FILE* arq = fopen(p, "r");
    if (arq != NULL) { if (fgets(l, sizeof(l), arq) != NULL) {} while (fgets(l, sizeof(l), arq) != NULL) { if (l[0] != '\0' && l[0] != '\n') { n++; } } fclose(arq); }
    return n;
}
void ler_csv(Colecao* c, char* p) {
    c->tamanho = contar_registros(p); c->restaurantes = (Restaurante*)malloc(sizeof(Restaurante) * c->tamanho);
    FILE* arq = fopen(p, "r"); char l[MAX_LINHA]; int i = 0;
    if (arq != NULL) {
        if (fgets(l, sizeof(l), arq) != NULL) {}
        while (fgets(l, sizeof(l), arq) != NULL && i < c->tamanho) {
            int id = 0;
            sscanf(l, "%d,%255[^,\n]", &id, c->restaurantes[i].nome);
            c->restaurantes[i].id = id;
            i++;
        }
        fclose(arq);
    }
}
Colecao carregar() {
    Colecao c; FILE* a = fopen(PATH_CSV_LINUX, "r");
    if (a != NULL) { fclose(a); ler_csv(&c, (char*)PATH_CSV_LINUX); } else { ler_csv(&c, (char*)PATH_CSV_WINDOWS); }
    return c;
}
char* buscar_nome_por_id(Colecao* c, int id) {
    char* nome = NULL; int i = 0;
    while (i < c->tamanho && nome == NULL) { if (c->restaurantes[i].id == id) { nome = c->restaurantes[i].nome; } i++; }
    return nome;
}
void insertion_sort(char nomes[][MAX_TEXTO], int n) {
    int i = 1;
    while (i < n) {
        char tmp[MAX_TEXTO]; texto_copiar(tmp, nomes[i]);
        int j = i - 1;
        while (j >= 0 && strcmp(nomes[j], tmp) > 0) { texto_copiar(nomes[j + 1], nomes[j]); j--; }
        texto_copiar(nomes[j + 1], tmp); i++;
    }
}
bool busca_binaria(char nomes[][MAX_TEXTO], int n, char* alvo) {
    bool achou = false; int esq = 0; int dir = n - 1;
    while (esq <= dir && !achou) {
        int m = (esq + dir) / 2; int c = strcmp(nomes[m], alvo);
        if (c == 0) { achou = true; } else if (c < 0) { esq = m + 1; } else { dir = m - 1; }
    }
    return achou;
}
int main() {
    Colecao c = carregar();
    char nomes_sel[MAX_REGISTROS][MAX_TEXTO]; int n = 0; int id = 0; bool fim_ids = false;
    while (scanf("%d", &id) == 1 && !fim_ids) {
        if (id == -1) { fim_ids = true; }
        else {
            char* nome = buscar_nome_por_id(&c, id);
            if (nome != NULL) { texto_copiar(nomes_sel[n], nome); n++; }
        }
    }
    insertion_sort(nomes_sel, n);

    char linha[MAX_TEXTO];
    if (fgets(linha, sizeof(linha), stdin) != NULL) {}
    bool fim = false;
    while (!fim && fgets(linha, sizeof(linha), stdin) != NULL) {
        int t = texto_tamanho(linha);
        if (t > 0 && linha[t - 1] == '\n') { linha[t - 1] = '\0'; }
        if (strcmp(linha, "FIM") == 0) { fim = true; }
        else { if (busca_binaria(nomes_sel, n, linha)) { printf("SIM\n"); } else { printf("NAO\n"); } }
    }
    free(c.restaurantes);
    return 0;
}
