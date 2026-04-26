import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

class TextoQ09 {
    public static boolean igual(String a, String b) {
        boolean resp = true;
        int i = 0;
        if (a.length() != b.length()) { resp = false; }
        while (i < a.length() && resp) { if (a.charAt(i) != b.charAt(i)) { resp = false; } i++; }
        return resp;
    }
    public static int paraInt(String s) {
        int i = 0;
        int valor = 0;
        int sinal = 1;
        if (s.length() > 0 && s.charAt(0) == '-') { sinal = -1; i = 1; }
        while (i < s.length()) { if (s.charAt(i) >= '0' && s.charAt(i) <= '9') { valor = valor * 10 + (s.charAt(i) - '0'); } i++; }
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
        return parteInteira + ((double)parteDecimal / fator);
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

class DataQ09 {
    private int ano, mes, dia;
    public DataQ09(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
    public int getAno() { return ano; }
    public int getMes() { return mes; }
    public int getDia() { return dia; }
    public static DataQ09 parse(String s) { return new DataQ09((s.charAt(0) - '0') * 1000 + (s.charAt(1) - '0') * 100 + (s.charAt(2) - '0') * 10 + (s.charAt(3) - '0'), (s.charAt(5) - '0') * 10 + (s.charAt(6) - '0'), (s.charAt(8) - '0') * 10 + (s.charAt(9) - '0')); }
    public String formatar() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
}
class HoraQ09 {
    private int h, m;
    public HoraQ09(int h, int m) { this.h = h; this.m = m; }
    public static HoraQ09 parse(String s) { return new HoraQ09((s.charAt(0) - '0') * 10 + (s.charAt(1) - '0'), (s.charAt(3) - '0') * 10 + (s.charAt(4) - '0')); }
    public String formatar() { return String.format("%02d:%02d", h, m); }
}
class RestauranteQ09 {
    private int id, capacidade, faixa;
    private String nome, cidade;
    private double avaliacao;
    private String[] tipos;
    private HoraQ09 ha, hf;
    private DataQ09 da;
    private boolean aberto;
    public RestauranteQ09(int id, String nome, String cidade, int capacidade, double avaliacao, String[] tipos, int faixa, HoraQ09 ha, HoraQ09 hf, DataQ09 da, boolean aberto) {
        this.id = id; this.nome = nome; this.cidade = cidade; this.capacidade = capacidade; this.avaliacao = avaliacao; this.tipos = tipos; this.faixa = faixa; this.ha = ha; this.hf = hf; this.da = da; this.aberto = aberto;
    }
    public int getId() { return id; }
    public int getCapacidade() { return capacidade; }
    public DataQ09 getDataAbertura() { return da; }
    public static RestauranteQ09 parse(String s) {
        String[] c = TextoQ09.separar(s, ',');
        String horario = c[7];
        int p = TextoQ09.posChar(horario, '-');
        return new RestauranteQ09(TextoQ09.paraInt(c[0]), c[1], c[2], TextoQ09.paraInt(c[3]), TextoQ09.paraDouble(c[4]),
            TextoQ09.separar(c[5], ';'), c[6].length(), HoraQ09.parse(TextoQ09.faixa(horario, 0, p)), HoraQ09.parse(TextoQ09.faixa(horario, p + 1, horario.length())),
            DataQ09.parse(c[8]), TextoQ09.igual(c[9], "true"));
    }
    private static String faixaTxt(int f) { String x = "$"; if (f == 2) { x = "$$"; } else if (f == 3) { x = "$$$"; } else if (f == 4) { x = "$$$$"; } return x; }
    public String formatar() {
        String tiposTexto = "[";
        int i = 0;
        while (i < tipos.length) {
            if (i > 0) { tiposTexto = tiposTexto + ","; }
            tiposTexto = tiposTexto + tipos[i];
            i++;
        }
        tiposTexto = tiposTexto + "]";
        return "[" + id + " ## " + nome + " ## " + cidade + " ## " + capacidade + " ## "
                + String.format(Locale.US, "%.1f", avaliacao) + " ## " + tiposTexto + " ## " + faixaTxt(faixa)
                + " ## " + ha.formatar() + "-" + hf.formatar() + " ## " + da.formatar() + " ## " + aberto + "]";
    }
}
class ColecaoQ09 {
    private RestauranteQ09[] v;
    private int n;
    private int contar(String p) throws FileNotFoundException {
        int t = 0; Scanner sc = new Scanner(new File(p)); if (sc.hasNextLine()) { sc.nextLine(); }
        while (sc.hasNextLine()) { if (sc.nextLine().length() > 0) { t++; } } sc.close(); return t;
    }
    public void ler(String p) throws FileNotFoundException {
        n = contar(p); v = new RestauranteQ09[n]; Scanner sc = new Scanner(new File(p)); if (sc.hasNextLine()) { sc.nextLine(); }
        int i = 0; while (sc.hasNextLine() && i < n) { String l = sc.nextLine(); if (l.length() > 0) { v[i] = RestauranteQ09.parse(l); i++; } } sc.close();
    }
    public static ColecaoQ09 lerCsv() throws FileNotFoundException {
        ColecaoQ09 c = new ColecaoQ09(); if (new File("/tmp/restaurantes.csv").exists()) { c.ler("/tmp/restaurantes.csv"); } else { c.ler("C:/tmp/restaurantes.csv"); } return c;
    }
    public RestauranteQ09 buscar(int id) {
        RestauranteQ09 r = null; int i = 0; while (i < n && r == null) { if (v[i].getId() == id) { r = v[i]; } i++; } return r;
    }
}

public class Questao09 {
    private static int cmpData(RestauranteQ09 a, RestauranteQ09 b) {
        int c = a.getDataAbertura().getAno() - b.getDataAbertura().getAno();
        if (c == 0) { c = a.getDataAbertura().getMes() - b.getDataAbertura().getMes(); }
        if (c == 0) { c = a.getDataAbertura().getDia() - b.getDataAbertura().getDia(); }
        return c;
    }
    private static int maiorFilho(RestauranteQ09[] v, int i, int tam) {
        int f = 2 * i + 1;
        if (f + 1 < tam && cmpData(v[f + 1], v[f]) > 0) { f = f + 1; }
        return f;
    }
    private static void swap(RestauranteQ09[] v, int a, int b) { RestauranteQ09 t = v[a]; v[a] = v[b]; v[b] = t; }
    private static void construirHeap(RestauranteQ09[] v, int tam) {
        int i = tam / 2 - 1;
        while (i >= 0) {
            int pai = i;
            int fim = 0;
            while (2 * pai + 1 < tam && fim == 0) {
                int f = maiorFilho(v, pai, tam);
                if (cmpData(v[f], v[pai]) > 0) { swap(v, pai, f); pai = f; } else { fim = 1; }
            }
            i--;
        }
    }
    private static void heapsort(RestauranteQ09[] v, int n) {
        construirHeap(v, n);
        int tam = n;
        while (tam > 1) {
            swap(v, 0, tam - 1);
            tam--;
            int pai = 0;
            int fim = 0;
            while (2 * pai + 1 < tam && fim == 0) {
                int f = maiorFilho(v, pai, tam);
                if (cmpData(v[f], v[pai]) > 0) { swap(v, pai, f); pai = f; } else { fim = 1; }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        ColecaoQ09 base = ColecaoQ09.lerCsv();
        RestauranteQ09[] sel = new RestauranteQ09[500];
        int n = 0; Scanner sc = new Scanner(System.in); boolean fim = false;
        while (sc.hasNextInt() && !fim) { int id = sc.nextInt(); if (id == -1) { fim = true; } else { sel[n] = base.buscar(id); n++; } }
        sc.close();
        heapsort(sel, n);
        int i = 0; while (i < n) { System.out.println(sel[i].formatar()); i++; }
    }
}
