import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

class Texto6 {
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

class Data6 {
    private int ano, mes, dia;
    public Data6(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
    public int getAno() { return ano; } public int getMes() { return mes; } public int getDia() { return dia; }
    public static Data6 parseData(String s) {
        int ano = (s.charAt(0)-'0')*1000+(s.charAt(1)-'0')*100+(s.charAt(2)-'0')*10+(s.charAt(3)-'0');
        int mes = (s.charAt(5)-'0')*10+(s.charAt(6)-'0'); int dia = (s.charAt(8)-'0')*10+(s.charAt(9)-'0');
        return new Data6(ano, mes, dia);
    }
    public String formatar() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
}

class Hora6 {
    private int hora, minuto;
    public Hora6(int hora, int minuto) { this.hora = hora; this.minuto = minuto; }
    public int getHora() { return hora; } public int getMinuto() { return minuto; }
    public static Hora6 parseHora(String s) { int hora = (s.charAt(0)-'0')*10+(s.charAt(1)-'0'); int minuto = (s.charAt(3)-'0')*10+(s.charAt(4)-'0'); return new Hora6(hora, minuto); }
    public String formatar() { return String.format("%02d:%02d", hora, minuto); }
}

class Restaurante6 {
    private int id, capacidade, faixaPreco; private String nome, cidade; private double avaliacao;
    private String[] tiposCozinha; private Hora6 horarioAbertura, horarioFechamento; private Data6 dataAbertura; private boolean aberto;
    public Restaurante6(int id, String nome, String cidade, int capacidade, double avaliacao, String[] tiposCozinha, int faixaPreco, Hora6 horarioAbertura, Hora6 horarioFechamento, Data6 dataAbertura, boolean aberto) {
        this.id = id; this.nome = nome; this.cidade = cidade; this.capacidade = capacidade; this.avaliacao = avaliacao;
        this.tiposCozinha = tiposCozinha; this.faixaPreco = faixaPreco; this.horarioAbertura = horarioAbertura; this.horarioFechamento = horarioFechamento; this.dataAbertura = dataAbertura; this.aberto = aberto;
    }
    public int getId() { return id; } public String getNome() { return nome; } public String getCidade() { return cidade; }
    public int getCapacidade() { return capacidade; } public double getAvaliacao() { return avaliacao; }
    public String[] getTiposCozinha() { return tiposCozinha; } public int getFaixaPreco() { return faixaPreco; }
    public Hora6 getHorarioAbertura() { return horarioAbertura; } public Hora6 getHorarioFechamento() { return horarioFechamento; }
    public Data6 getDataAbertura() { return dataAbertura; } public boolean isAberto() { return aberto; }
    public static Restaurante6 parseRestaurante(String s) {
        String[] campos = Texto6.separar(s, ','); int id = Texto6.paraInt(campos[0]); String nome = campos[1]; String cidade = campos[2];
        int capacidade = Texto6.paraInt(campos[3]); double avaliacao = Texto6.paraDouble(campos[4]); String[] tiposCozinha = Texto6.separar(campos[5], ';');
        int faixaPreco = campos[6].length(); String horario = campos[7]; int dashPos = Texto6.posChar(horario, '-');
        Hora6 horarioAbertura = Hora6.parseHora(Texto6.faixa(horario, 0, dashPos)); Hora6 horarioFechamento = Hora6.parseHora(Texto6.faixa(horario, dashPos + 1, horario.length()));
        Data6 dataAbertura = Data6.parseData(campos[8]); boolean aberto = Texto6.igual(campos[9], "true");
        return new Restaurante6(id, nome, cidade, capacidade, avaliacao, tiposCozinha, faixaPreco, horarioAbertura, horarioFechamento, dataAbertura, aberto);
    }
    private static String faixaPrecoParaString(int faixa) { String t = "$"; if (faixa == 2) { t = "$$"; } else if (faixa == 3) { t = "$$$"; } else if (faixa == 4) { t = "$$$$"; } return t; }
    public String formatar() {
        String tipos = "["; int i = 0;
        while (i < tiposCozinha.length) { if (i > 0) { tipos = tipos + ","; } tipos = tipos + tiposCozinha[i]; i++; }
        tipos = tipos + "]";
        return "[" + id + " ## " + nome + " ## " + cidade + " ## " + capacidade + " ## " + String.format(Locale.US, "%.1f", avaliacao) + " ## " + tipos + " ## " + faixaPrecoParaString(faixaPreco) + " ## " + horarioAbertura.formatar() + "-" + horarioFechamento.formatar() + " ## " + dataAbertura.formatar() + " ## " + aberto + "]";
    }
}

class ColecaoRestaurantes6 {
    private static final String pathCsv = "/tmp/restaurantes.csv";
    private int tamanho; private Restaurante6[] restaurantes;
    public ColecaoRestaurantes6() { this.tamanho = 0; this.restaurantes = new Restaurante6[0]; }
    public int getTamanho() { return tamanho; } public Restaurante6[] getRestaurantes() { return restaurantes; }
    private int obterNumeroDeRegistrosCsv(String path) throws FileNotFoundException {
        int n = 0; Scanner sc = new Scanner(new File(path)); if (sc.hasNextLine()) { sc.nextLine(); }
        while (sc.hasNextLine()) { String l = sc.nextLine(); if (l.length() > 0) { n++; } } sc.close(); return n;
    }
    public void lerCsv(String path) throws FileNotFoundException {
        tamanho = obterNumeroDeRegistrosCsv(path); restaurantes = new Restaurante6[tamanho];
        Scanner sc = new Scanner(new File(path)); if (sc.hasNextLine()) { sc.nextLine(); } int idx = 0;
        while (sc.hasNextLine() && idx < tamanho) { String l = sc.nextLine(); if (l.length() > 0) { restaurantes[idx] = Restaurante6.parseRestaurante(l); idx++; } } sc.close();
    }
    public static ColecaoRestaurantes6 lerCsv() throws FileNotFoundException { ColecaoRestaurantes6 c = new ColecaoRestaurantes6(); c.lerCsv(pathCsv); return c; }
}

class No6 {
    Restaurante6 restaurante; No6 prox;
    public No6(Restaurante6 r) { this.restaurante = r; this.prox = null; }
}

class Pilha6 {
    private No6 topo; private int tamanho;
    public Pilha6() { this.topo = null; this.tamanho = 0; }
    public void inserir(Restaurante6 r) { No6 novo = new No6(r); novo.prox = topo; topo = novo; tamanho++; }
    public Restaurante6 remover() {
        Restaurante6 r = null;
        if (tamanho > 0) { r = topo.restaurante; topo = topo.prox; tamanho--; }
        return r;
    }
    public int getTamanho() { return tamanho; }
    public void mostrar() {
        No6 atual = topo;
        while (atual != null) { System.out.println(atual.restaurante.formatar()); atual = atual.prox; }
    }
}

public class Questao06TP3 {
    public static void main(String[] args) throws Exception {
        ColecaoRestaurantes6 colecao = ColecaoRestaurantes6.lerCsv();
        Restaurante6[] todos = colecao.getRestaurantes();
        Pilha6 pilha = new Pilha6();
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        while (id != -1) {
            int i = 0; boolean encontrado = false;
            while (i < colecao.getTamanho() && !encontrado) {
                if (todos[i].getId() == id) { pilha.inserir(todos[i]); encontrado = true; }
                i++;
            }
            id = sc.nextInt();
        }
        int n = sc.nextInt();
        int i = 0;
        while (i < n) {
            String cmd = sc.next();
            if (Texto6.igual(cmd, "I")) {
                int rid = sc.nextInt(); int j = 0; boolean encontrado = false;
                while (j < colecao.getTamanho() && !encontrado) {
                    if (todos[j].getId() == rid) { pilha.inserir(todos[j]); encontrado = true; }
                    j++;
                }
            } else if (Texto6.igual(cmd, "R")) {
                Restaurante6 r = pilha.remover();
                if (r != null) { System.out.println("(R)" + r.getNome()); }
            }
            i++;
        }
        pilha.mostrar();
        sc.close();
    }
}
