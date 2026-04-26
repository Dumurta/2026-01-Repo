#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_LINHA_CSV 1024
#define MAX_TEXTO 256
#define MAX_TIPOS_COZINHA 10
#define PATH_CSV_LINUX "/tmp/restaurantes.csv"

int texto_tamanho(char* s) {
    int i = 0;
    while (s[i] != '\0') {
        i++;
    }
    return i;
}

void texto_copiar(char* destino, char* origem) {
    int i = 0;
    while (origem[i] != '\0') {
        destino[i] = origem[i];
        i++;
    }
    destino[i] = '\0';
}

void texto_concatenar(char* destino, char* origem) {
    int i = 0;
    int j = texto_tamanho(destino);
    while (origem[i] != '\0') {
        destino[j] = origem[i];
        i++;
        j++;
    }
    destino[j] = '\0';
}

typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

typedef struct {
    int hora;
    int minuto;
} Hora;

typedef struct {
    int id;
    char nome[MAX_TEXTO];
    char cidade[MAX_TEXTO];
    int capacidade;
    double avaliacao;
    char tipos_cozinha[MAX_TIPOS_COZINHA][MAX_TEXTO];
    int quantidade_tipos;
    int faixa_preco;
    Hora horario_abertura;
    Hora horario_fechamento;
    Data data_abertura;
    bool aberto;
} Restaurante;

typedef struct {
    int tamanho;
    Restaurante** restaurantes;
} ColecaoRestaurantes;

Data parse_data(char* s) {
    Data data;
    data.ano = 0;
    data.mes = 0;
    data.dia = 0;
    sscanf(s, "%d-%d-%d", &data.ano, &data.mes, &data.dia);
    return data;
}

void formatar_data(Data* data, char* buffer) {
    sprintf(buffer, "%02d/%02d/%04d", data->dia, data->mes, data->ano);
}

Hora parse_hora(char* s) {
    Hora hora;
    hora.hora = 0;
    hora.minuto = 0;
    sscanf(s, "%d:%d", &hora.hora, &hora.minuto);
    return hora;
}

void formatar_hora(Hora* hora, char* buffer) {
    sprintf(buffer, "%02d:%02d", hora->hora, hora->minuto);
}

int contar_faixa_preco(char* faixa_preco) {
    int tamanho = 0;
    int i = 0;
    while (faixa_preco[i] != '\0') {
        if (faixa_preco[i] == '$') {
            tamanho++;
        }
        i++;
    }
    return tamanho;
}

void copiar_tipos_cozinha(Restaurante* restaurante, char* tipos_str) {
    int i = 0;
    int j = 0;
    int quantidade = 0;
    while (tipos_str[i] != '\0' && quantidade < MAX_TIPOS_COZINHA) {
        if (tipos_str[i] == ';') {
            restaurante->tipos_cozinha[quantidade][j] = '\0';
            quantidade++;
            j = 0;
        } else {
            if (j < MAX_TEXTO - 1) {
                restaurante->tipos_cozinha[quantidade][j] = tipos_str[i];
                j++;
            }
        }
        i++;
    }
    restaurante->tipos_cozinha[quantidade][j] = '\0';
    quantidade++;
    restaurante->quantidade_tipos = quantidade;
}

Restaurante* parse_restaurante(char* s) {
    Restaurante* restaurante = (Restaurante*)malloc(sizeof(Restaurante));
    char tipos_str[MAX_TEXTO];
    char faixa_str[10];
    char horario_str[20];
    char data_str[20];
    char aberto_str[10];
    int hora_abertura = 0;
    int minuto_abertura = 0;
    int hora_fechamento = 0;
    int minuto_fechamento = 0;

    sscanf(
        s,
        "%d,%255[^,],%255[^,],%d,%lf,%255[^,],%9[^,],%19[^,],%19[^,],%9s",
        &restaurante->id,
        restaurante->nome,
        restaurante->cidade,
        &restaurante->capacidade,
        &restaurante->avaliacao,
        tipos_str,
        faixa_str,
        horario_str,
        data_str,
        aberto_str
    );

    copiar_tipos_cozinha(restaurante, tipos_str);
    restaurante->faixa_preco = contar_faixa_preco(faixa_str);

    sscanf(horario_str, "%d:%d-%d:%d", &hora_abertura, &minuto_abertura, &hora_fechamento, &minuto_fechamento);
    restaurante->horario_abertura = parse_hora(horario_str);
    restaurante->horario_fechamento.hora = hora_fechamento;
    restaurante->horario_fechamento.minuto = minuto_fechamento;
    restaurante->data_abertura = parse_data(data_str);
    restaurante->aberto = strcmp(aberto_str, "true") == 0;

    return restaurante;
}

void faixa_preco_para_string(int faixa_preco, char* buffer) {
    texto_copiar(buffer, "$");
    if (faixa_preco == 2) {
        texto_copiar(buffer, "$$");
    } else if (faixa_preco == 3) {
        texto_copiar(buffer, "$$$");
    } else if (faixa_preco == 4) {
        texto_copiar(buffer, "$$$$");
    }
}

void formatar_tipos_cozinha(Restaurante* restaurante, char* buffer) {
    int i = 0;
    texto_copiar(buffer, "[");
    while (i < restaurante->quantidade_tipos) {
        if (i > 0) {
            texto_concatenar(buffer, ",");
        }
        texto_concatenar(buffer, restaurante->tipos_cozinha[i]);
        i++;
    }
    texto_concatenar(buffer, "]");
}

void formatar_restaurante(Restaurante* restaurante, char* buffer) {
    char tipos_buffer[MAX_TEXTO];
    char faixa_preco_buffer[5];
    char hora_abertura_buffer[6];
    char hora_fechamento_buffer[6];
    char data_buffer[11];

    formatar_tipos_cozinha(restaurante, tipos_buffer);
    faixa_preco_para_string(restaurante->faixa_preco, faixa_preco_buffer);
    formatar_hora(&restaurante->horario_abertura, hora_abertura_buffer);
    formatar_hora(&restaurante->horario_fechamento, hora_fechamento_buffer);
    formatar_data(&restaurante->data_abertura, data_buffer);

    sprintf(
        buffer,
        "[%d ## %s ## %s ## %d ## %.1f ## %s ## %s ## %s-%s ## %s ## %s]",
        restaurante->id,
        restaurante->nome,
        restaurante->cidade,
        restaurante->capacidade,
        restaurante->avaliacao,
        tipos_buffer,
        faixa_preco_buffer,
        hora_abertura_buffer,
        hora_fechamento_buffer,
        data_buffer,
        restaurante->aberto ? "true" : "false"
    );
}

int obter_numero_de_registros_csv(char* path) {
    int numero_de_registros = 0;
    char linha[MAX_LINHA_CSV];
    FILE* arquivo = fopen(path, "r");

    if (arquivo != NULL) {
        if (fgets(linha, sizeof(linha), arquivo) != NULL) {
        }
        while (fgets(linha, sizeof(linha), arquivo) != NULL) {
            if (linha[0] != '\0' && linha[0] != '\n') {
                numero_de_registros++;
            }
        }
        fclose(arquivo);
    }

    return numero_de_registros;
}

void ler_csv_colecao(ColecaoRestaurantes* colecao, char* path) {
    int indice = 0;
    char linha[MAX_LINHA_CSV];
    FILE* arquivo = NULL;

    colecao->tamanho = obter_numero_de_registros_csv(path);
    colecao->restaurantes = (Restaurante**)malloc(sizeof(Restaurante*) * colecao->tamanho);

    arquivo = fopen(path, "r");
    if (arquivo != NULL) {
        if (fgets(linha, sizeof(linha), arquivo) != NULL) {
        }
        while (fgets(linha, sizeof(linha), arquivo) != NULL && indice < colecao->tamanho) {
            int i = 0;
            while (linha[i] != '\0') {
                if (linha[i] == '\n') {
                    linha[i] = '\0';
                }
                i++;
            }
            if (linha[0] != '\0') {
                colecao->restaurantes[indice] = parse_restaurante(linha);
                indice++;
            }
        }
        fclose(arquivo);
    }
}

ColecaoRestaurantes* ler_csv() {
    ColecaoRestaurantes* colecao = (ColecaoRestaurantes*)malloc(sizeof(ColecaoRestaurantes));
    FILE* arquivo = fopen(PATH_CSV_LINUX, "r");

    if (arquivo != NULL) {
        fclose(arquivo);
        ler_csv_colecao(colecao, (char*)PATH_CSV_LINUX);
    } else {
        ler_csv_colecao(colecao, "C:/tmp/restaurantes.csv");
    }

    return colecao;
}

void liberar_colecao(ColecaoRestaurantes* colecao) {
    int i = 0;
    while (i < colecao->tamanho) {
        free(colecao->restaurantes[i]);
        i++;
    }
    free(colecao->restaurantes);
    free(colecao);
}

int main() {
    ColecaoRestaurantes* colecao = ler_csv();
    int id = 0;
    bool encerrar = false;

    while (scanf("%d", &id) == 1 && !encerrar) {
        if (id == -1) {
            encerrar = true;
        } else {
            int i = 0;
            bool encontrado = false;
            while (i < colecao->tamanho && !encontrado) {
                if (colecao->restaurantes[i]->id == id) {
                    char buffer_saida[MAX_LINHA_CSV];
                    formatar_restaurante(colecao->restaurantes[i], buffer_saida);
                    printf("%s\n", buffer_saida);
                    encontrado = true;
                }
                i++;
            }
        }
    }

    liberar_colecao(colecao);
    return 0;
}
