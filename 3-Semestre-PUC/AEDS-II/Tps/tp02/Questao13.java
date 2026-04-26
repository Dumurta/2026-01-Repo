import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

class TextoQ13 {
    public static boolean igual(String a, String b) {
        boolean resp = true;
        int i = 0;
        if (a.length() != b.length()) { resp = false; }
        while (i < a.length() && resp) { if (a.charAt(i) != b.charAt(i)) { resp = false; } i++; }
        return resp;
    }
    public static boolean comecaCom(String s, String p) {
        boolean resp = true;
        int i = 0;
        if (s.length() < p.length()) { resp = false; }
        while (i < p.length() && resp) { if (s.charAt(i) != p.charAt(i)) { resp = false; } i++; }
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

class DataQ13 {
    private int ano, mes, dia;
    public DataQ13(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
    public int getAno() { return ano; }
    public static DataQ13 parse(String s) { return new DataQ13((s.charAt(0) - '0') * 1000 + (s.charAt(1) - '0') * 100 + (s.charAt(2) - '0') * 10 + (s.charAt(3) - '0'), (s.charAt(5) - '0') * 10 + (s.charAt(6) - '0'), (s.charAt(8) - '0') * 10 + (s.charAt(9) - '0')); }
    public String formatar() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
}
class HoraQ13 {
    private int h, m;
    public HoraQ13(int h, int m) { this.h = h; this.m = m; }
    public static HoraQ13 parse(String s) { return new HoraQ13((s.charAt(0) - '0') * 10 + (s.charAt(1) - '0'), (s.charAt(3) - '0') * 10 + (s.charAt(4) - '0')); }
    public String formatar() { return String.format("%02d:%02d", h, m); }
}
class RestauranteQ13 {
    private int id, capacidade, faixa;
    private String nome, cidade;
    private double avaliacao;
    private String[] tipos;
    private HoraQ13 ha, hf;
    private DataQ13 da;
    private boolean aberto;
    public RestauranteQ13(int id, String nome, String cidade, int capacidade, double avaliacao, String[] tipos, int faixa, HoraQ13 ha, HoraQ13 hf, DataQ13 da, boolean aberto) {
        this.id = id; this.nome = nome; this.cidade = cidade; this.capacidade = capacidade; this.avaliacao = avaliacao; this.tipos = tipos; this.faixa = faixa; this.ha = ha; this.hf = hf; this.da = da; this.aberto = aberto;
    }
    public int getId() { return id; }
    public String getNome() { return nome; }
    public DataQ13 getDataAbertura() { return da; }
    public static RestauranteQ13 parse(String s) {
        String[] c = TextoQ13.separar(s, ',');
        String horario = c[7];
        int p = TextoQ13.posChar(horario, '-');
        return new RestauranteQ13(TextoQ13.paraInt(c[0]), c[1], c[2], TextoQ13.paraInt(c[3]), TextoQ13.paraDouble(c[4]),
            TextoQ13.separar(c[5], ';'), c[6].length(), HoraQ13.parse(TextoQ13.faixa(horario, 0, p)), HoraQ13.parse(TextoQ13.faixa(horario, p + 1, horario.length())),
            DataQ13.parse(c[8]), TextoQ13.igual(c[9], "true"));
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
class ColecaoQ13 {
    private RestauranteQ13[] v;
    private int n;
    private int contar(String p) throws FileNotFoundException {
        int t = 0; Scanner sc = new Scanner(new File(p)); if (sc.hasNextLine()) { sc.nextLine(); }
        while (sc.hasNextLine()) { if (sc.nextLine().length() > 0) { t++; } } sc.close(); return t;
    }
    public void ler(String p) throws FileNotFoundException {
        n = contar(p); v = new RestauranteQ13[n]; Scanner sc = new Scanner(new File(p)); if (sc.hasNextLine()) { sc.nextLine(); }
        int i = 0; while (sc.hasNextLine() && i < n) { String l = sc.nextLine(); if (l.length() > 0) { v[i] = RestauranteQ13.parse(l); i++; } } sc.close();
    }
    public static ColecaoQ13 lerCsv() throws FileNotFoundException {
        ColecaoQ13 c = new ColecaoQ13(); if (new File("/tmp/restaurantes.csv").exists()) { c.ler("/tmp/restaurantes.csv"); } else { c.ler("C:/tmp/restaurantes.csv"); } return c;
    }
    public RestauranteQ13 buscar(int id) {
        RestauranteQ13 r = null; int i = 0; while (i < n && r == null) { if (v[i].getId() == id) { r = v[i]; } i++; } return r;
    }
}

class FilaCircularQ13 {
    private RestauranteQ13[] v;
    private int primeiro;
    private int ultimo;
    private int somaAnos;
    public FilaCircularQ13(int capacidadeElementos) {
        v = new RestauranteQ13[capacidadeElementos + 1];
        primeiro = 0;
        ultimo = 0;
        somaAnos = 0;
    }
    private int prox(int i) { return (i + 1) % v.length; }
    public boolean cheia() { return prox(ultimo) == primeiro; }
    public boolean vazia() { return primeiro == ultimo; }
    public int tamanho() {
        int t = ultimo - primeiro;
        if (t < 0) { t = t + v.length; }
        return t;
    }
    public RestauranteQ13 remover() {
        RestauranteQ13 r = null;
        if (!vazia()) {
            r = v[primeiro];
            somaAnos = somaAnos - r.getDataAbertura().getAno();
            primeiro = prox(primeiro);
        }
        return r;
    }
    public void inserir(RestauranteQ13 r) {
        if (cheia()) {
            RestauranteQ13 removidoAuto = remover();
            System.out.println("(R)" + removidoAuto.getNome());
        }
        v[ultimo] = r;
        ultimo = prox(ultimo);
        somaAnos = somaAnos + r.getDataAbertura().getAno();
        int media = 0;
        int t = tamanho();
        if (t > 0) { media = (int)Math.round((double)somaAnos / t); }
        System.out.println("(I)" + media);
    }
    public void imprimir() {
        int i = primeiro;
        while (i != ultimo) {
            System.out.println(v[i].formatar());
            i = prox(i);
        }
    }
}

public class Questao13 {
    public static void main(String[] args) throws Exception {
        ColecaoQ13 base = ColecaoQ13.lerCsv();
        FilaCircularQ13 fila = new FilaCircularQ13(5);
        Scanner sc = new Scanner(System.in);

        boolean fimIds = false;
        while (sc.hasNextLine() && !fimIds) {
            String linha = sc.nextLine();
            if (TextoQ13.igual(linha, "-1")) {
                fimIds = true;
            } else {
                int id = TextoQ13.paraInt(linha);
                fila.inserir(base.buscar(id));
            }
        }

        int ops = TextoQ13.paraInt(sc.nextLine());
        int i = 0;
        while (i < ops && sc.hasNextLine()) {
            String cmd = sc.nextLine();
            if (TextoQ13.comecaCom(cmd, "I ")) {
                int id = TextoQ13.paraInt(TextoQ13.faixa(cmd, 2, cmd.length()));
                fila.inserir(base.buscar(id));
            } else if (TextoQ13.igual(cmd, "R")) {
                RestauranteQ13 r = fila.remover();
                System.out.println("(R)" + r.getNome());
            }
            i++;
        }
        sc.close();
        fila.imprimir();
    }
}
