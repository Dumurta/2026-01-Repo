import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

class TextoQ07 {
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

class DataQ07 {
    private int ano, mes, dia;
    public DataQ07(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
    public static DataQ07 parseData(String s) { return new DataQ07((s.charAt(0) - '0') * 1000 + (s.charAt(1) - '0') * 100 + (s.charAt(2) - '0') * 10 + (s.charAt(3) - '0'), (s.charAt(5) - '0') * 10 + (s.charAt(6) - '0'), (s.charAt(8) - '0') * 10 + (s.charAt(9) - '0')); }
    public String formatar() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
}
class HoraQ07 {
    private int h, m;
    public HoraQ07(int h, int m) { this.h = h; this.m = m; }
    public static HoraQ07 parseHora(String s) { return new HoraQ07((s.charAt(0) - '0') * 10 + (s.charAt(1) - '0'), (s.charAt(3) - '0') * 10 + (s.charAt(4) - '0')); }
    public String formatar() { return String.format("%02d:%02d", h, m); }
}
class RestauranteQ07 {
    private int id, capacidade, faixaPreco;
    private String nome, cidade;
    private double avaliacao;
    private String[] tipos;
    private HoraQ07 ha, hf;
    private DataQ07 da;
    private boolean aberto;
    public RestauranteQ07(int id, String nome, String cidade, int capacidade, double avaliacao, String[] tipos, int faixaPreco, HoraQ07 ha, HoraQ07 hf, DataQ07 da, boolean aberto) {
        this.id = id; this.nome = nome; this.cidade = cidade; this.capacidade = capacidade; this.avaliacao = avaliacao; this.tipos = tipos; this.faixaPreco = faixaPreco; this.ha = ha; this.hf = hf; this.da = da; this.aberto = aberto;
    }
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCidade() { return cidade; }
    public static RestauranteQ07 parse(String s) {
        String[] c = TextoQ07.separar(s, ',');
        String horario = c[7];
        int p = TextoQ07.posChar(horario, '-');
        return new RestauranteQ07(TextoQ07.paraInt(c[0]), c[1], c[2], TextoQ07.paraInt(c[3]),
            TextoQ07.paraDouble(c[4]), TextoQ07.separar(c[5], ';'), c[6].length(), HoraQ07.parseHora(TextoQ07.faixa(horario, 0, p)),
            HoraQ07.parseHora(TextoQ07.faixa(horario, p + 1, horario.length())), DataQ07.parseData(c[8]), TextoQ07.igual(c[9], "true"));
    }
    private static String faixa(int f) { String x = "$"; if (f == 2) { x = "$$"; } else if (f == 3) { x = "$$$"; } else if (f == 4) { x = "$$$$"; } return x; }
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
                + String.format(Locale.US, "%.1f", avaliacao) + " ## " + tiposTexto + " ## " + faixa(faixaPreco)
                + " ## " + ha.formatar() + "-" + hf.formatar() + " ## " + da.formatar() + " ## " + aberto + "]";
    }
}
class ColecaoQ07 {
    private RestauranteQ07[] v;
    private int n;
    private int contar(String path) throws FileNotFoundException {
        int t = 0; Scanner sc = new Scanner(new File(path)); if (sc.hasNextLine()) { sc.nextLine(); }
        while (sc.hasNextLine()) { if (sc.nextLine().length() > 0) { t++; } } sc.close(); return t;
    }
    public void ler(String path) throws FileNotFoundException {
        n = contar(path); v = new RestauranteQ07[n]; Scanner sc = new Scanner(new File(path)); if (sc.hasNextLine()) { sc.nextLine(); }
        int i = 0; while (sc.hasNextLine() && i < n) { String l = sc.nextLine(); if (l.length() > 0) { v[i] = RestauranteQ07.parse(l); i++; } } sc.close();
    }
    public static ColecaoQ07 lerCsv() throws FileNotFoundException {
        ColecaoQ07 c = new ColecaoQ07(); if (new File("/tmp/restaurantes.csv").exists()) { c.ler("/tmp/restaurantes.csv"); } else { c.ler("C:/tmp/restaurantes.csv"); } return c;
    }
    public RestauranteQ07 buscar(int id) {
        RestauranteQ07 r = null; int i = 0; while (i < n && r == null) { if (v[i].getId() == id) { r = v[i]; } i++; } return r;
    }
}

public class Questao07 {
    private static int cmp(RestauranteQ07 a, RestauranteQ07 b) {
        int c = a.getCidade().compareTo(b.getCidade());
        if (c == 0) { c = a.getNome().compareTo(b.getNome()); }
        return c;
    }
    private static void merge(RestauranteQ07[] v, RestauranteQ07[] aux, int l, int m, int r) {
        int i = l; int j = m + 1; int k = l;
        while (i <= m && j <= r) {
            if (cmp(v[i], v[j]) <= 0) { aux[k] = v[i]; i++; } else { aux[k] = v[j]; j++; }
            k++;
        }
        while (i <= m) { aux[k] = v[i]; i++; k++; }
        while (j <= r) { aux[k] = v[j]; j++; k++; }
        i = l;
        while (i <= r) { v[i] = aux[i]; i++; }
    }
    private static void ms(RestauranteQ07[] v, RestauranteQ07[] aux, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            ms(v, aux, l, m);
            ms(v, aux, m + 1, r);
            merge(v, aux, l, m, r);
        }
    }
    public static void main(String[] args) throws Exception {
        ColecaoQ07 base = ColecaoQ07.lerCsv();
        RestauranteQ07[] sel = new RestauranteQ07[500];
        int n = 0; Scanner sc = new Scanner(System.in); boolean fim = false;
        while (sc.hasNextInt() && !fim) { int id = sc.nextInt(); if (id == -1) { fim = true; } else { sel[n] = base.buscar(id); n++; } }
        sc.close();
        RestauranteQ07[] aux = new RestauranteQ07[n];
        if (n > 0) { ms(sel, aux, 0, n - 1); }
        int i = 0; while (i < n) { System.out.println(sel[i].formatar()); i++; }
    }
}
