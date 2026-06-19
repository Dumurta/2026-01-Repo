import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

class Texto {
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

class Data {
    private int ano, mes, dia;
    public Data(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
    public static Data parseData(String s) {
        int ano = (s.charAt(0)-'0')*1000+(s.charAt(1)-'0')*100+(s.charAt(2)-'0')*10+(s.charAt(3)-'0');
        int mes = (s.charAt(5)-'0')*10+(s.charAt(6)-'0'); int dia = (s.charAt(8)-'0')*10+(s.charAt(9)-'0');
        return new Data(ano, mes, dia);
    }
    public String formatar() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
}

class Hora {
    private int hora, minuto;
    public Hora(int hora, int minuto) { this.hora = hora; this.minuto = minuto; }
    public static Hora parseHora(String s) { int hora = (s.charAt(0)-'0')*10+(s.charAt(1)-'0'); int minuto = (s.charAt(3)-'0')*10+(s.charAt(4)-'0'); return new Hora(hora, minuto); }
    public String formatar() { return String.format("%02d:%02d", hora, minuto); }
}

class Restaurante {
    private int id, capacidade, faixaPreco; private String nome, cidade; private double avaliacao;
    private String[] tiposCozinha; private Hora horarioAbertura, horarioFechamento; private Data dataAbertura; private boolean aberto;
    public Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao, String[] tiposCozinha, int faixaPreco, Hora horarioAbertura, Hora horarioFechamento, Data dataAbertura, boolean aberto) {
        this.id = id; this.nome = nome; this.cidade = cidade; this.capacidade = capacidade; this.avaliacao = avaliacao;
        this.tiposCozinha = tiposCozinha; this.faixaPreco = faixaPreco; this.horarioAbertura = horarioAbertura; this.horarioFechamento = horarioFechamento; this.dataAbertura = dataAbertura; this.aberto = aberto;
    }
    public int getId() { return id; } public String getNome() { return nome; } public String getCidade() { return cidade; }
    public int getCapacidade() { return capacidade; } public double getAvaliacao() { return avaliacao; }
    public String[] getTiposCozinha() { return tiposCozinha; } public int getFaixaPreco() { return faixaPreco; }
    public Hora getHorarioAbertura() { return horarioAbertura; } public Hora getHorarioFechamento() { return horarioFechamento; }
    public Data getDataAbertura() { return dataAbertura; } public boolean isAberto() { return aberto; }
    public static Restaurante parseRestaurante(String s) {
        String[] campos = Texto.separar(s, ','); int id = Texto.paraInt(campos[0]); String nome = campos[1]; String cidade = campos[2];
        int capacidade = Texto.paraInt(campos[3]); double avaliacao = Texto.paraDouble(campos[4]); String[] tiposCozinha = Texto.separar(campos[5], ';');
        int faixaPreco = campos[6].length(); String horario = campos[7]; int dashPos = Texto.posChar(horario, '-');
        Hora horarioAbertura = Hora.parseHora(Texto.faixa(horario, 0, dashPos)); Hora horarioFechamento = Hora.parseHora(Texto.faixa(horario, dashPos + 1, horario.length()));
        Data dataAbertura = Data.parseData(campos[8]); boolean aberto = Texto.igual(campos[9], "true");
        return new Restaurante(id, nome, cidade, capacidade, avaliacao, tiposCozinha, faixaPreco, horarioAbertura, horarioFechamento, dataAbertura, aberto);
    }
    private static String faixaPrecoParaString(int faixa) { String t = "$"; if (faixa == 2) { t = "$$"; } else if (faixa == 3) { t = "$$$"; } else if (faixa == 4) { t = "$$$$"; } return t; }
    public String formatar() {
        String tipos = "["; int i = 0;
        while (i < tiposCozinha.length) { if (i > 0) { tipos = tipos + ","; } tipos = tipos + tiposCozinha[i]; i++; }
        tipos = tipos + "]";
        return "[" + id + " ## " + nome + " ## " + cidade + " ## " + capacidade + " ## " + String.format(Locale.US, "%.1f", avaliacao) + " ## " + tipos + " ## " + faixaPrecoParaString(faixaPreco) + " ## " + horarioAbertura.formatar() + "-" + horarioFechamento.formatar() + " ## " + dataAbertura.formatar() + " ## " + aberto + "]";
    }
}

class ColecaoRestaurantes {
    private static final String pathCsv = "/tmp/restaurantes.csv";
    private int tamanho; private Restaurante[] restaurantes;
    public ColecaoRestaurantes() { this.tamanho = 0; this.restaurantes = new Restaurante[0]; }
    public int getTamanho() { return tamanho; } public Restaurante[] getRestaurantes() { return restaurantes; }
    private int obterNumeroDeRegistrosCsv(String path) throws FileNotFoundException {
        int n = 0; Scanner sc = new Scanner(new File(path)); if (sc.hasNextLine()) { sc.nextLine(); }
        while (sc.hasNextLine()) { String l = sc.nextLine(); if (l.length() > 0) { n++; } } sc.close(); return n;
    }
    public void lerCsv(String path) throws FileNotFoundException {
        tamanho = obterNumeroDeRegistrosCsv(path); restaurantes = new Restaurante[tamanho];
        Scanner sc = new Scanner(new File(path)); if (sc.hasNextLine()) { sc.nextLine(); } int idx = 0;
        while (sc.hasNextLine() && idx < tamanho) { String l = sc.nextLine(); if (l.length() > 0) { restaurantes[idx] = Restaurante.parseRestaurante(l); idx++; } } sc.close();
    }
    public static ColecaoRestaurantes lerCsv() throws FileNotFoundException { ColecaoRestaurantes c = new ColecaoRestaurantes(); c.lerCsv(pathCsv); return c; }
}

class No {
    Restaurante restaurante; No esq, dir; int altura;
    No(Restaurante r) { restaurante = r; esq = null; dir = null; altura = 1; }
}

class AVL {
    private No raiz;
    static long comparacoes = 0;
    private int h(No n) { return n == null ? 0 : n.altura; }
    private int max(int a, int b) { return a > b ? a : b; }
    private No rotDir(No y) { No x = y.esq; No t = x.dir; x.dir = y; y.esq = t; y.altura = 1+max(h(y.esq),h(y.dir)); x.altura = 1+max(h(x.esq),h(x.dir)); return x; }
    private No rotEsq(No x) { No y = x.dir; No t = y.esq; y.esq = x; x.dir = t; x.altura = 1+max(h(x.esq),h(x.dir)); y.altura = 1+max(h(y.esq),h(y.dir)); return y; }
    public void inserir(Restaurante r) { raiz = ins(raiz, r); }
    private No ins(No n, Restaurante r) {
        if (n == null) return new No(r);
        int c = r.getNome().compareTo(n.restaurante.getNome());
        if (c < 0) n.esq = ins(n.esq, r); else if (c > 0) n.dir = ins(n.dir, r); else return n;
        n.altura = 1 + max(h(n.esq), h(n.dir));
        int bal = h(n.esq) - h(n.dir);
        if (bal > 1 && r.getNome().compareTo(n.esq.restaurante.getNome()) < 0) return rotDir(n);
        if (bal < -1 && r.getNome().compareTo(n.dir.restaurante.getNome()) > 0) return rotEsq(n);
        if (bal > 1 && r.getNome().compareTo(n.esq.restaurante.getNome()) > 0) { n.esq = rotEsq(n.esq); return rotDir(n); }
        if (bal < -1 && r.getNome().compareTo(n.dir.restaurante.getNome()) < 0) { n.dir = rotDir(n.dir); return rotEsq(n); }
        return n;
    }
    public String pesquisar(String nome) { return pesquisar(raiz, nome, "raiz"); }
    private String pesquisar(No n, String nome, String caminho) {
        String resp = caminho + " NAO";
        if (n != null) {
            comparacoes++;
            int c = nome.compareTo(n.restaurante.getNome());
            if (c == 0) { resp = caminho + " SIM\n" + n.restaurante.formatar(); }
            else if (c < 0) { resp = pesquisar(n.esq, nome, caminho + " esq"); }
            else { resp = pesquisar(n.dir, nome, caminho + " dir"); }
        }
        return resp;
    }
    public void emOrdem() { emOrdem(raiz); }
    private void emOrdem(No n) { if (n != null) { emOrdem(n.esq); System.out.println(n.restaurante.formatar()); emOrdem(n.dir); } }
}

public class Questao01tp04 {
    public static void main(String[] args) throws Exception {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Restaurante[] todos = colecao.getRestaurantes();
        AVL arvore = new AVL();
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        while (id != -1) {
            int i = 0; boolean enc = false;
            while (i < colecao.getTamanho() && !enc) { if (todos[i].getId() == id) { arvore.inserir(todos[i]); enc = true; } i++; }
            id = sc.nextInt();
        }
        long inicio = System.currentTimeMillis();
        String linha = sc.nextLine();
        while (sc.hasNextLine()) {
            linha = sc.nextLine();
            if (!Texto.igual(linha, "FIM")) { System.out.println(arvore.pesquisar(linha)); }
        }
        long fim = System.currentTimeMillis();
        arvore.emOrdem();
        sc.close();
        java.io.FileWriter fw = new java.io.FileWriter("884985_arvore_avl.txt");
        fw.write("884985\t" + AVL.comparacoes + "\t" + String.format(Locale.US, "%.2f", (fim - inicio) / 1000.0) + "\n");
        fw.close();
    }
}
