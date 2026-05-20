import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

class Texto12 {
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

class Data12 {
    private int ano, mes, dia;
    public Data12(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
    public int getAno() { return ano; } public int getMes() { return mes; } public int getDia() { return dia; }
    public static Data12 parseData(String s) {
        int ano = (s.charAt(0)-'0')*1000+(s.charAt(1)-'0')*100+(s.charAt(2)-'0')*10+(s.charAt(3)-'0');
        int mes = (s.charAt(5)-'0')*10+(s.charAt(6)-'0'); int dia = (s.charAt(8)-'0')*10+(s.charAt(9)-'0');
        return new Data12(ano, mes, dia);
    }
    public String formatar() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
}

class Hora12 {
    private int hora, minuto;
    public Hora12(int hora, int minuto) { this.hora = hora; this.minuto = minuto; }
    public int getHora() { return hora; } public int getMinuto() { return minuto; }
    public static Hora12 parseHora(String s) { int hora = (s.charAt(0)-'0')*10+(s.charAt(1)-'0'); int minuto = (s.charAt(3)-'0')*10+(s.charAt(4)-'0'); return new Hora12(hora, minuto); }
    public String formatar() { return String.format("%02d:%02d", hora, minuto); }
}

class Restaurante12 {
    private int id, capacidade, faixaPreco; private String nome, cidade; private double avaliacao;
    private String[] tiposCozinha; private Hora12 horarioAbertura, horarioFechamento; private Data12 dataAbertura; private boolean aberto;
    public Restaurante12(int id, String nome, String cidade, int capacidade, double avaliacao, String[] tiposCozinha, int faixaPreco, Hora12 horarioAbertura, Hora12 horarioFechamento, Data12 dataAbertura, boolean aberto) {
        this.id = id; this.nome = nome; this.cidade = cidade; this.capacidade = capacidade; this.avaliacao = avaliacao;
        this.tiposCozinha = tiposCozinha; this.faixaPreco = faixaPreco; this.horarioAbertura = horarioAbertura; this.horarioFechamento = horarioFechamento; this.dataAbertura = dataAbertura; this.aberto = aberto;
    }
    public int getId() { return id; } public String getNome() { return nome; } public String getCidade() { return cidade; }
    public int getCapacidade() { return capacidade; } public double getAvaliacao() { return avaliacao; }
    public String[] getTiposCozinha() { return tiposCozinha; } public int getFaixaPreco() { return faixaPreco; }
    public Hora12 getHorarioAbertura() { return horarioAbertura; } public Hora12 getHorarioFechamento() { return horarioFechamento; }
    public Data12 getDataAbertura() { return dataAbertura; } public boolean isAberto() { return aberto; }
    public static Restaurante12 parseRestaurante(String s) {
        String[] campos = Texto12.separar(s, ','); int id = Texto12.paraInt(campos[0]); String nome = campos[1]; String cidade = campos[2];
        int capacidade = Texto12.paraInt(campos[3]); double avaliacao = Texto12.paraDouble(campos[4]); String[] tiposCozinha = Texto12.separar(campos[5], ';');
        int faixaPreco = campos[6].length(); String horario = campos[7]; int dashPos = Texto12.posChar(horario, '-');
        Hora12 horarioAbertura = Hora12.parseHora(Texto12.faixa(horario, 0, dashPos)); Hora12 horarioFechamento = Hora12.parseHora(Texto12.faixa(horario, dashPos + 1, horario.length()));
        Data12 dataAbertura = Data12.parseData(campos[8]); boolean aberto = Texto12.igual(campos[9], "true");
        return new Restaurante12(id, nome, cidade, capacidade, avaliacao, tiposCozinha, faixaPreco, horarioAbertura, horarioFechamento, dataAbertura, aberto);
    }
    private static String faixaPrecoParaString(int faixa) { String t = "$"; if (faixa == 2) { t = "$$"; } else if (faixa == 3) { t = "$$$"; } else if (faixa == 4) { t = "$$$$"; } return t; }
    public String formatar() {
        String tipos = "["; int i = 0;
        while (i < tiposCozinha.length) { if (i > 0) { tipos = tipos + ","; } tipos = tipos + tiposCozinha[i]; i++; }
        tipos = tipos + "]";
        return "[" + id + " ## " + nome + " ## " + cidade + " ## " + capacidade + " ## " + String.format(Locale.US, "%.1f", avaliacao) + " ## " + tipos + " ## " + faixaPrecoParaString(faixaPreco) + " ## " + horarioAbertura.formatar() + "-" + horarioFechamento.formatar() + " ## " + dataAbertura.formatar() + " ## " + aberto + "]";
    }
}

class ColecaoRestaurantes12 {
    private static final String pathCsv = "/tmp/restaurantes.csv";
    private int tamanho; private Restaurante12[] restaurantes;
    public ColecaoRestaurantes12() { this.tamanho = 0; this.restaurantes = new Restaurante12[0]; }
    public int getTamanho() { return tamanho; } public Restaurante12[] getRestaurantes() { return restaurantes; }
    private int obterNumeroDeRegistrosCsv(String path) throws FileNotFoundException {
        int n = 0; Scanner sc = new Scanner(new File(path)); if (sc.hasNextLine()) { sc.nextLine(); }
        while (sc.hasNextLine()) { String l = sc.nextLine(); if (l.length() > 0) { n++; } } sc.close(); return n;
    }
    public void lerCsv(String path) throws FileNotFoundException {
        tamanho = obterNumeroDeRegistrosCsv(path); restaurantes = new Restaurante12[tamanho];
        Scanner sc = new Scanner(new File(path)); if (sc.hasNextLine()) { sc.nextLine(); } int idx = 0;
        while (sc.hasNextLine() && idx < tamanho) { String l = sc.nextLine(); if (l.length() > 0) { restaurantes[idx] = Restaurante12.parseRestaurante(l); idx++; } } sc.close();
    }
    public static ColecaoRestaurantes12 lerCsv() throws FileNotFoundException { ColecaoRestaurantes12 c = new ColecaoRestaurantes12(); c.lerCsv(pathCsv); return c; }
}

class NoArvore12 {
    Restaurante12 restaurante; NoArvore12 esq; NoArvore12 dir;
    public NoArvore12(Restaurante12 r) { this.restaurante = r; this.esq = null; this.dir = null; }
}

class Arvore12 {
    private NoArvore12 raiz;
    static long comparacoes = 0;

    public Arvore12() { this.raiz = null; }

    public void inserir(Restaurante12 r) { raiz = inserir(raiz, r); }
    private NoArvore12 inserir(NoArvore12 no, Restaurante12 r) {
        NoArvore12 resp = no;
        if (no == null) { resp = new NoArvore12(r); }
        else {
            int cmp = r.getNome().compareTo(no.restaurante.getNome());
            if (cmp < 0) { no.esq = inserir(no.esq, r); }
            else if (cmp > 0) { no.dir = inserir(no.dir, r); }
        }
        return resp;
    }

    public String pesquisar(String nome) { return pesquisar(raiz, nome, "raiz"); }
    private String pesquisar(NoArvore12 no, String nome, String caminho) {
        String resp = caminho + " NAO";
        if (no != null) {
            comparacoes++;
            int cmp = nome.compareTo(no.restaurante.getNome());
            if (cmp == 0) { resp = caminho + " SIM"; }
            else if (cmp < 0) { resp = pesquisar(no.esq, nome, caminho + " esq"); }
            else { resp = pesquisar(no.dir, nome, caminho + " dir"); }
        }
        return resp;
    }

    public void emOrdem() { emOrdem(raiz); }
    private void emOrdem(NoArvore12 no) {
        if (no != null) {
            emOrdem(no.esq);
            System.out.println(no.restaurante.formatar());
            emOrdem(no.dir);
        }
    }
}

public class Questao12TP3 {
    public static void main(String[] args) throws Exception {
        ColecaoRestaurantes12 colecao = ColecaoRestaurantes12.lerCsv();
        Restaurante12[] todos = colecao.getRestaurantes();
        Arvore12 arvore = new Arvore12();
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        while (id != -1) {
            int i = 0; boolean encontrado = false;
            while (i < colecao.getTamanho() && !encontrado) {
                if (todos[i].getId() == id) { arvore.inserir(todos[i]); encontrado = true; }
                i++;
            }
            id = sc.nextInt();
        }
        long inicio = System.currentTimeMillis();
        String linha = sc.nextLine();
        while (sc.hasNextLine()) {
            linha = sc.nextLine();
            if (!Texto12.igual(linha, "FIM")) {
                System.out.println(arvore.pesquisar(linha));
            }
        }
        long fim = System.currentTimeMillis();
        double tempo = (fim - inicio) / 1000.0;
        arvore.emOrdem();
        sc.close();
        java.io.FileWriter fw = new java.io.FileWriter("884985_arvore_binaria.txt");
        fw.write("884985\t" + Arvore12.comparacoes + "\t" + String.format(Locale.US, "%.2f", tempo) + "\n");
        fw.close();
    }
}
