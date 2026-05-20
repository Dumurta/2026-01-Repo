import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Scanner;

class Texto3 {
    public static boolean igual(String a, String b) {
        boolean resp = true;
        int i = 0;
        if (a.length() != b.length()) { resp = false; }
        while (i < a.length() && resp) {
            if (a.charAt(i) != b.charAt(i)) { resp = false; }
            i++;
        }
        return resp;
    }
    public static int paraInt(String s) {
        int i = 0;
        int valor = 0;
        int sinal = 1;
        if (s.length() > 0 && s.charAt(0) == '-') { sinal = -1; i = 1; }
        while (i < s.length()) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') { valor = valor * 10 + (s.charAt(i) - '0'); }
            i++;
        }
        return valor * sinal;
    }
    public static double paraDouble(String s) {
        int i = 0;
        int parteInteira = 0;
        int parteDecimal = 0;
        int fator = 1;
        boolean decimal = false;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == '.') { decimal = true; }
            else if (c >= '0' && c <= '9') {
                if (!decimal) { parteInteira = parteInteira * 10 + (c - '0'); }
                else { parteDecimal = parteDecimal * 10 + (c - '0'); fator = fator * 10; }
            }
            i++;
        }
        return parteInteira + ((double) parteDecimal / fator);
    }
    public static int contarPartes(String s, char sep) {
        int i = 0;
        int total = 1;
        while (i < s.length()) { if (s.charAt(i) == sep) { total++; } i++; }
        return total;
    }
    public static String[] separar(String s, char sep) {
        int total = contarPartes(s, sep);
        String[] partes = new String[total];
        int i = 0;
        int idx = 0;
        String atual = "";
        while (i < s.length()) {
            if (s.charAt(i) == sep) { partes[idx] = atual; idx++; atual = ""; }
            else { atual = atual + s.charAt(i); }
            i++;
        }
        partes[idx] = atual;
        return partes;
    }
    public static int posChar(String s, char alvo) {
        int i = 0;
        int pos = -1;
        while (i < s.length() && pos == -1) { if (s.charAt(i) == alvo) { pos = i; } i++; }
        return pos;
    }
    public static String faixa(String s, int ini, int fim) {
        String resp = "";
        int i = ini;
        while (i < fim) { resp = resp + s.charAt(i); i++; }
        return resp;
    }
}

class Data3 {
    private int ano, mes, dia;
    public Data3(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
    public int getAno() { return ano; }
    public int getMes() { return mes; }
    public int getDia() { return dia; }
    public static Data3 parseData(String s) {
        int ano = (s.charAt(0)-'0')*1000+(s.charAt(1)-'0')*100+(s.charAt(2)-'0')*10+(s.charAt(3)-'0');
        int mes = (s.charAt(5)-'0')*10+(s.charAt(6)-'0');
        int dia = (s.charAt(8)-'0')*10+(s.charAt(9)-'0');
        return new Data3(ano, mes, dia);
    }
    public String formatar() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
}

class Hora3 {
    private int hora, minuto;
    public Hora3(int hora, int minuto) { this.hora = hora; this.minuto = minuto; }
    public int getHora() { return hora; }
    public int getMinuto() { return minuto; }
    public static Hora3 parseHora(String s) {
        int hora = (s.charAt(0)-'0')*10+(s.charAt(1)-'0');
        int minuto = (s.charAt(3)-'0')*10+(s.charAt(4)-'0');
        return new Hora3(hora, minuto);
    }
    public String formatar() { return String.format("%02d:%02d", hora, minuto); }
}

class Restaurante3 {
    private int id, capacidade, faixaPreco;
    private String nome, cidade;
    private double avaliacao;
    private String[] tiposCozinha;
    private Hora3 horarioAbertura, horarioFechamento;
    private Data3 dataAbertura;
    private boolean aberto;

    public Restaurante3(int id, String nome, String cidade, int capacidade,
                        double avaliacao, String[] tiposCozinha, int faixaPreco,
                        Hora3 horarioAbertura, Hora3 horarioFechamento,
                        Data3 dataAbertura, boolean aberto) {
        this.id = id; this.nome = nome; this.cidade = cidade;
        this.capacidade = capacidade; this.avaliacao = avaliacao;
        this.tiposCozinha = tiposCozinha; this.faixaPreco = faixaPreco;
        this.horarioAbertura = horarioAbertura; this.horarioFechamento = horarioFechamento;
        this.dataAbertura = dataAbertura; this.aberto = aberto;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCidade() { return cidade; }
    public int getCapacidade() { return capacidade; }
    public double getAvaliacao() { return avaliacao; }
    public String[] getTiposCozinha() { return tiposCozinha; }
    public int getFaixaPreco() { return faixaPreco; }
    public Hora3 getHorarioAbertura() { return horarioAbertura; }
    public Hora3 getHorarioFechamento() { return horarioFechamento; }
    public Data3 getDataAbertura() { return dataAbertura; }
    public boolean isAberto() { return aberto; }

    public static Restaurante3 parseRestaurante(String s) {
        String[] campos = Texto3.separar(s, ',');
        int id = Texto3.paraInt(campos[0]);
        String nome = campos[1];
        String cidade = campos[2];
        int capacidade = Texto3.paraInt(campos[3]);
        double avaliacao = Texto3.paraDouble(campos[4]);
        String[] tiposCozinha = Texto3.separar(campos[5], ';');
        int faixaPreco = campos[6].length();
        String horario = campos[7];
        int dashPos = Texto3.posChar(horario, '-');
        Hora3 horarioAbertura = Hora3.parseHora(Texto3.faixa(horario, 0, dashPos));
        Hora3 horarioFechamento = Hora3.parseHora(Texto3.faixa(horario, dashPos + 1, horario.length()));
        Data3 dataAbertura = Data3.parseData(campos[8]);
        boolean aberto = Texto3.igual(campos[9], "true");
        return new Restaurante3(id, nome, cidade, capacidade, avaliacao,
                tiposCozinha, faixaPreco, horarioAbertura, horarioFechamento, dataAbertura, aberto);
    }

    private static String faixaPrecoParaString(int faixa) {
        String t = "$";
        if (faixa == 2) { t = "$$"; } else if (faixa == 3) { t = "$$$"; } else if (faixa == 4) { t = "$$$$"; }
        return t;
    }

    public String formatar() {
        String tipos = "[";
        int i = 0;
        while (i < tiposCozinha.length) {
            if (i > 0) { tipos = tipos + ","; }
            tipos = tipos + tiposCozinha[i];
            i++;
        }
        tipos = tipos + "]";
        return "[" + id + " ## " + nome + " ## " + cidade + " ## " + capacidade + " ## "
                + String.format(Locale.US, "%.1f", avaliacao) + " ## " + tipos + " ## "
                + faixaPrecoParaString(faixaPreco) + " ## " + horarioAbertura.formatar() + "-"
                + horarioFechamento.formatar() + " ## " + dataAbertura.formatar() + " ## " + aberto + "]";
    }
}

class ColecaoRestaurantes3 {
    private static final String pathCsv = "/tmp/restaurantes.csv";
    private int tamanho;
    private Restaurante3[] restaurantes;

    public ColecaoRestaurantes3() { this.tamanho = 0; this.restaurantes = new Restaurante3[0]; }
    public int getTamanho() { return tamanho; }
    public Restaurante3[] getRestaurantes() { return restaurantes; }

    private int obterNumeroDeRegistrosCsv(String path) throws FileNotFoundException {
        int n = 0;
        Scanner sc = new Scanner(new File(path));
        if (sc.hasNextLine()) { sc.nextLine(); }
        while (sc.hasNextLine()) { String l = sc.nextLine(); if (l.length() > 0) { n++; } }
        sc.close();
        return n;
    }

    public void lerCsv(String path) throws FileNotFoundException {
        tamanho = obterNumeroDeRegistrosCsv(path);
        restaurantes = new Restaurante3[tamanho];
        Scanner sc = new Scanner(new File(path));
        if (sc.hasNextLine()) { sc.nextLine(); }
        int idx = 0;
        while (sc.hasNextLine() && idx < tamanho) {
            String l = sc.nextLine();
            if (l.length() > 0) { restaurantes[idx] = Restaurante3.parseRestaurante(l); idx++; }
        }
        sc.close();
    }

    public static ColecaoRestaurantes3 lerCsv() throws FileNotFoundException {
        ColecaoRestaurantes3 c = new ColecaoRestaurantes3();
        c.lerCsv(pathCsv);
        return c;
    }
}

public class Questao03TP3 {

    static long comparacoes = 0;
    static long movimentacoes = 0;

    public static int comparar(Restaurante3 a, Restaurante3 b) {
        int resp = 0;
        if (a.getAvaliacao() < b.getAvaliacao()) { resp = -1; }
        else if (a.getAvaliacao() > b.getAvaliacao()) { resp = 1; }
        else { resp = a.getNome().compareTo(b.getNome()); }
        return resp;
    }

    private static int[] particionar(Restaurante3[] v, int esq, int dir) {
        Restaurante3 pivo = v[(esq + dir) / 2];
        int i = esq;
        int j = dir;
        while (i <= j) {
            while (comparar(v[i], pivo) < 0) { comparacoes++; i++; }
            while (comparar(v[j], pivo) > 0) { comparacoes++; j--; }
            if (i <= j) {
                Restaurante3 temp = v[i];
                v[i] = v[j];
                v[j] = temp;
                movimentacoes += 3;
                i++;
                j--;
            }
        }
        return new int[]{i, j};
    }

    public static void quicksortParcial(Restaurante3[] v,int n, int k) {
        quicksortParcialAux(v, 0, n - 1, k);
    }

    private static void quicksortParcialAux(Restaurante3[] v, int esq, int dir, int k) {
        if (esq < dir) {
            int[] limites = particionar(v, esq, dir);
            int i = limites[0];
            int j = limites[1];
            if (esq < j) { quicksortParcialAux(v, esq, j, k); }
            if (i < dir && i < k) { quicksortParcialAux(v, i, dir, k); }
        }
    }

    public static void main(String[] args) throws Exception {
        ColecaoRestaurantes3 colecao = ColecaoRestaurantes3.lerCsv();
        Restaurante3[] todos = colecao.getRestaurantes();

        Scanner sc = new Scanner(System.in);
        Restaurante3[] sel = new Restaurante3[colecao.getTamanho()];
        int n = 0;
        int id = sc.nextInt();
        while (id != -1) {
            int i = 0;
            boolean encontrado = false;
            while (i < colecao.getTamanho() && !encontrado) {
                if (todos[i].getId() == id) { sel[n] = todos[i]; n++; encontrado = true; }
                i++;
            }
            id = sc.nextInt();
        }
        sc.close();

        long inicio = System.currentTimeMillis();
        quicksortParcial(sel, n, 10);
        long fim = System.currentTimeMillis();
        double tempo = (fim - inicio) / 1000.0;

        int i = 0;
        while (i < n) {
            System.out.println(sel[i].formatar());
            i++;
        }

        FileWriter fw = new FileWriter("884985_quicksort_parcial.txt");
        fw.write("884985\t" + comparacoes + "\t" + movimentacoes + "\t" + String.format(Locale.US, "%.2f", tempo) + "\n");
        fw.close();
    }
}