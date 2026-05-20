#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Celula{
    char nome[50];
    int forca;
    struct Celula* prox;
}Celula;

typedef struct Fila{
    Celula* primeiro;
    Celula* ultimo;
}Fila;

void fila_iniciar(Fila* f){
    f->primeiro = (Celula*) malloc(sizeof(Celula));
    f->ultimo = f->primeiro;
}

void fila_inserir(Fila* f, char* nome, int forca){
    Celula* tmp = (Celula*) malloc(sizeof(Celula));
    strcpy(tmp->nome, nome);
    tmp->forca = forca;
    f->ultimo->prox = tmp;
    f->ultimo = tmp;
}

Celula* fila_remover(Fila* f){
    Celula* tmp = NULL;
    if(f->primeiro == f->ultimo){
        errx(1, "Erro!");
    }else{
        tmp = f->primeiro->prox;
        f->primeiro->prox = tmp->prox;
    }
    if(f->primeiro->prox == NULL){
        f->ultimo = f->primeiro;
    }
    return tmp;
}
    

