import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Scanner;

class Texto11 {
    public static boolean igual(String a, String b) {
        boolean resp = true; int i = 0;
        if (a.length() != b.length()) { resp = false; }
        while (i < a.length() && resp) { if (a.charAt(i) != b.charAt(i)) { resp = false; } i++; }
        return resp;
    }
    public static int paraInt(String s) {
        int i = 0, valor = 0, sinal = 1;
        if (s.length() > 0 && s.charAt(0) == '-') { sinal = -1; i = 1; }
        while (i < s.length()) { if (s.charAt(i) >= '0' && s.charAt(i) <= '9') { valor = valor * 10 + (s.charAt(i) - '0'); } i++; }
        return valor * sinal;
    }
    public static double paraDouble(String s) {
        int i = 0, parteInteira = 0, parteDecimal = 0, fator = 1; boolean decimal = false;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == '.') { decimal = true; }
            else if (c >= '0' && c <= '9') { if (!decimal) { parteInteira = parteInteira * 10 + (c - '0'); } else { parteDecimal = parteDecimal * 10 + (c - '0'); fator = fator * 10; } }
            i++;
        }
        return parteInteira + ((double) parteDecimal / fator);
    }
    public static int contarPartes(String s, char sep) { int i = 0, total = 1; while (i < s.length()) { if (s.charAt(i) == sep) { total++; } i++; } return total; }
    public static String[] separar(String s, char sep) {
        int total = contarPartes(s, sep); String[] partes = new String[total]; int i = 0, idx = 0; String atual = "";
        while (i < s.length()) { if (s.charAt(i) == sep) { partes[idx] = atual; idx++; atual = ""; } else { atual = atual + s.charAt(i); } i++; }
        partes[idx] = atual; return partes;
    }
    public static int posChar(String s, char alvo) { int i = 0, pos = -1; while (i < s.length() && pos == -1) { if (s.charAt(i) == alvo) { pos = i; } i++; } return pos; }
    public static String faixa(String s, int ini, int fim) { String resp = ""; int i = ini; while (i < fim) { resp = resp + s.charAt(i); i++; } return resp; }
}

class Data11 {
    private int ano, mes, dia;
    public Data11(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
    public int getAno() { return ano; } public int getMes() { return mes; } public int getDia() { return dia; }
    public static Data11 parseData(String s) {
        int ano = (s.charAt(0)-'0')*1000+(s.charAt(1)-'0')*100+(s.charAt(2)-'0')*10+(s.charAt(3)-'0');
        int mes = (s.charAt(5)-'0')*10+(s.charAt(6)-'0'); int dia = (s.charAt(8)-'0')*10+(s.charAt(9)-'0');
        return new Data11(ano, mes, dia);
    }
    public String formatar() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
}

class Hora11 {
    private int hora, minuto;
    public Hora11(int hora, int minuto) { this.hora = hora; this.minuto = minuto; }
    public int getHora() { return hora; } public int getMinuto() { return minuto; }
    public static Hora11 parseHora(String s) { int hora = (s.charAt(0)-'0')*10+(s.charAt(1)-'0'); int minuto = (s.charAt(3)-'0')*10+(s.charAt(4)-'0'); return new Hora11(hora, minuto); }
    public String formatar() { return String.format("%02d:%02d", hora, minuto); }
}

class Restaurante11 {
    private int id, capacidade, faixaPreco; private String nome, cidade; private double avaliacao;
    private String[] tiposCozinha; private Hora11 horarioAbertura, horarioFechamento; private Data11 dataAbertura; private boolean aberto;
    public Restaurante11(int id, String nome, String cidade, int capacidade, double avaliacao, String[] tiposCozinha, int faixaPreco, Hora11 horarioAbertura, Hora11 horarioFechamento, Data11 dataAbertura, boolean aberto) {
        this.id = id; this.nome = nome; this.cidade = cidade; this.capacidade = capacidade; this.avaliacao = avaliacao;
        this.tiposCozinha = tiposCozinha; this.faixaPreco = faixaPreco; this.horarioAbertura = horarioAbertura; this.horarioFechamento = horarioFechamento; this.dataAbertura = dataAbertura; this.aberto = aberto;
    }
    public int getId() { return id; } public String getNome() { return nome; } public String getCidade() { return cidade; }
    public int getCapacidade() { return capacidade; } public double getAvaliacao() { return avaliacao; }
    public String[] getTiposCozinha() { return tiposCozinha; } public int getFaixaPreco() { return faixaPreco; }
    public Hora11 getHorarioAbertura() { return horarioAbertura; } public Hora11 getHorarioFechamento() { return horarioFechamento; }
    public Data11 getDataAbertura() { return dataAbertura; } public boolean isAberto() { return aberto; }
    public static Restaurante11 parseRestaurante(String s) {
        String[] campos = Texto11.separar(s, ','); int id = Texto11.paraInt(campos[0]); String nome = campos[1]; String cidade = campos[2];
        int capacidade = Texto11.paraInt(campos[3]); double avaliacao = Texto11.paraDouble(campos[4]); String[] tiposCozinha = Texto11.separar(campos[5], ';');
        int faixaPreco = campos[6].length(); String horario = campos[7]; int dashPos = Texto11.posChar(horario, '-');
        Hora11 horarioAbertura = Hora11.parseHora(Texto11.faixa(horario, 0, dashPos)); Hora11 horarioFechamento = Hora11.parseHora(Texto11.faixa(horario, dashPos + 1, horario.length()));
        Data11 dataAbertura = Data11.parseData(campos[8]); boolean aberto = Texto11.igual(campos[9], "true");
        return new Restaurante11(id, nome, cidade, capacidade, avaliacao, tiposCozinha, faixaPreco, horarioAbertura, horarioFechamento, dataAbertura, aberto);
    }
    private static String faixaPrecoParaString(int faixa) { String t = "$"; if (faixa == 2) { t = "$$"; } else if (faixa == 3) { t = "$$$"; } else if (faixa == 4) { t = "$$$$"; } return t; }
    public String formatar() {
        String tipos = "["; int i = 0;
        while (i < tiposCozinha.length) { if (i > 0) { tipos = tipos + ","; } tipos = tipos + tiposCozinha[i]; i++; }
        tipos = tipos + "]";
        return "[" + id + " ## " + nome + " ## " + cidade + " ## " + capacidade + " ## " + String.format(Locale.US, "%.1f", avaliacao) + " ## " + tipos + " ## " + faixaPrecoParaString(faixaPreco) + " ## " + horarioAbertura.formatar() + "-" + horarioFechamento.formatar() + " ## " + dataAbertura.formatar() + " ## " + aberto + "]";
    }
}

class ColecaoRestaurantes11 {
    private static final String pathCsv = "/tmp/restaurantes.csv";
    private int tamanho; private Restaurante11[] restaurantes;
    public ColecaoRestaurantes11() { this.tamanho = 0; this.restaurantes = new Restaurante11[0]; }
    public int getTamanho() { return tamanho; } public Restaurante11[] getRestaurantes() { return restaurantes; }
    private int obterNumeroDeRegistrosCsv(String path) throws FileNotFoundException {
        int n = 0; Scanner sc = new Scanner(new File(path)); if (sc.hasNextLine()) { sc.nextLine(); }
        while (sc.hasNextLine()) { String l = sc.nextLine(); if (l.length() > 0) { n++; } } sc.close(); return n;
    }
    public void lerCsv(String path) throws FileNotFoundException {
        tamanho = obterNumeroDeRegistrosCsv(path); restaurantes = new Restaurante11[tamanho];
        Scanner sc = new Scanner(new File(path)); if (sc.hasNextLine()) { sc.nextLine(); } int idx = 0;
        while (sc.hasNextLine() && idx < tamanho) { String l = sc.nextLine(); if (l.length() > 0) { restaurantes[idx] = Restaurante11.parseRestaurante(l); idx++; } } sc.close();
    }
    public static ColecaoRestaurantes11 lerCsv() throws FileNotFoundException { ColecaoRestaurantes11 c = new ColecaoRestaurantes11(); c.lerCsv(pathCsv); return c; }
}

class No11 {
    Restaurante11 restaurante; No11 prox; No11 ant;
    public No11(Restaurante11 r) { this.restaurante = r; this.prox = null; this.ant = null; }
}

class ListaDupla11 {
    No11 inicio; No11 fim; int tamanho;
    public ListaDupla11() { this.inicio = null; this.fim = null; this.tamanho = 0; }
    public void inserirFim(Restaurante11 r) {
        No11 novo = new No11(r); novo.ant = fim;
        if (tamanho > 0) { fim.prox = novo; } else { inicio = novo; }
        fim = novo; tamanho++;
    }
}

public class Questao11TP3 {
    static long comparacoes = 0;
    static long movimentacoes = 0;

    public static int comparar(Restaurante11 a, Restaurante11 b) {
        int resp = 0;
        if (a.getAvaliacao() < b.getAvaliacao()) { resp = -1; }
        else if (a.getAvaliacao() > b.getAvaliacao()) { resp = 1; }
        else { resp = a.getNome().compareTo(b.getNome()); }
        return resp;
    }

    private static No11 encontrarMeio(No11 esq, No11 dir) {
        No11 meio = esq;
        No11 tmp = esq;
        while (tmp != dir && tmp.prox != dir) { meio = meio.prox; tmp = tmp.prox.prox; }
        return meio;
    }

    private static No11 particionar(No11 esq, No11 dir) {
        Restaurante11 pivo = encontrarMeio(esq, dir).restaurante;
        No11 i = esq;
        No11 j = dir;
        while (i != j) {
            while (comparar(i.restaurante, pivo) < 0 && i != j) { comparacoes++; i = i.prox; }
            while (comparar(j.restaurante, pivo) > 0 && i != j) { comparacoes++; j = j.ant; }
            if (i != j) {
                Restaurante11 t = i.restaurante; i.restaurante = j.restaurante; j.restaurante = t;
                movimentacoes += 3;
                if (i.prox != j) { i = i.prox; j = j.ant; }
                else { i = j; }
            }
        }
        comparacoes++;
        if (comparar(i.restaurante, pivo) > 0 && i != esq) { i = i.ant; }
        return i;
    }

    public static void quicksort(ListaDupla11 lista) {
        quicksortAux(lista, lista.inicio, lista.fim);
    }

    private static void quicksortAux(ListaDupla11 lista, No11 esq, No11 dir) {
        if (esq != null && dir != null && esq != dir && dir.prox != esq) {
            No11 p = particionar(esq, dir);
            if (p != esq) { quicksortAux(lista, esq, p); }
            if (p.prox != null && p.prox != dir.prox) { quicksortAux(lista, p.prox, dir); }
        }
    }

    public static void main(String[] args) throws Exception {
        ColecaoRestaurantes11 colecao = ColecaoRestaurantes11.lerCsv();
        Restaurante11[] todos = colecao.getRestaurantes();
        ListaDupla11 lista = new ListaDupla11();
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        while (id != -1) {
            int i = 0; boolean encontrado = false;
            while (i < colecao.getTamanho() && !encontrado) {
                if (todos[i].getId() == id) { lista.inserirFim(todos[i]); encontrado = true; }
                i++;
            }
            id = sc.nextInt();
        }
        sc.close();
        long inicio = System.currentTimeMillis();
        quicksort(lista);
        long fim = System.currentTimeMillis();
        double tempo = (fim - inicio) / 1000.0;
        No11 atual = lista.inicio;
        while (atual != null) { System.out.println(atual.restaurante.formatar()); atual = atual.prox; }
        FileWriter fw = new FileWriter("884985_quicksort_flexivel.txt");
        fw.write("884985\t" + comparacoes + "\t" + movimentacoes + "\t" + String.format(Locale.US, "%.2f", tempo) + "\n");
        fw.close();
    }
}
