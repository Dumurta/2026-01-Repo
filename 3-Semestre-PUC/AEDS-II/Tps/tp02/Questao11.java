import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

class TextoQ11 {
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

class DataQ11 {
    private int ano, mes, dia;
    public DataQ11(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
    public static DataQ11 parse(String s) { return new DataQ11((s.charAt(0) - '0') * 1000 + (s.charAt(1) - '0') * 100 + (s.charAt(2) - '0') * 10 + (s.charAt(3) - '0'), (s.charAt(5) - '0') * 10 + (s.charAt(6) - '0'), (s.charAt(8) - '0') * 10 + (s.charAt(9) - '0')); }
    public String formatar() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
}
class HoraQ11 {
    private int h, m;
    public HoraQ11(int h, int m) { this.h = h; this.m = m; }
    public static HoraQ11 parse(String s) { return new HoraQ11((s.charAt(0) - '0') * 10 + (s.charAt(1) - '0'), (s.charAt(3) - '0') * 10 + (s.charAt(4) - '0')); }
    public String formatar() { return String.format("%02d:%02d", h, m); }
}
class RestauranteQ11 {
    private int id, capacidade, faixa;
    private String nome, cidade;
    private double avaliacao;
    private String[] tipos;
    private HoraQ11 ha, hf;
    private DataQ11 da;
    private boolean aberto;
    public RestauranteQ11(int id, String nome, String cidade, int capacidade, double avaliacao, String[] tipos, int faixa, HoraQ11 ha, HoraQ11 hf, DataQ11 da, boolean aberto) {
        this.id = id; this.nome = nome; this.cidade = cidade; this.capacidade = capacidade; this.avaliacao = avaliacao; this.tipos = tipos; this.faixa = faixa; this.ha = ha; this.hf = hf; this.da = da; this.aberto = aberto;
    }
    public int getId() { return id; }
    public String getNome() { return nome; }
    public static RestauranteQ11 parse(String s) {
        String[] c = TextoQ11.separar(s, ',');
        String horario = c[7];
        int p = TextoQ11.posChar(horario, '-');
        return new RestauranteQ11(TextoQ11.paraInt(c[0]), c[1], c[2], TextoQ11.paraInt(c[3]), TextoQ11.paraDouble(c[4]),
            TextoQ11.separar(c[5], ';'), c[6].length(), HoraQ11.parse(TextoQ11.faixa(horario, 0, p)), HoraQ11.parse(TextoQ11.faixa(horario, p + 1, horario.length())),
            DataQ11.parse(c[8]), TextoQ11.igual(c[9], "true"));
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
class ColecaoQ11 {
    private RestauranteQ11[] v;
    private int n;
    private int contar(String p) throws FileNotFoundException {
        int t = 0; Scanner sc = new Scanner(new File(p)); if (sc.hasNextLine()) { sc.nextLine(); }
        while (sc.hasNextLine()) { if (sc.nextLine().length() > 0) { t++; } } sc.close(); return t;
    }
    public void ler(String p) throws FileNotFoundException {
        n = contar(p); v = new RestauranteQ11[n]; Scanner sc = new Scanner(new File(p)); if (sc.hasNextLine()) { sc.nextLine(); }
        int i = 0; while (sc.hasNextLine() && i < n) { String l = sc.nextLine(); if (l.length() > 0) { v[i] = RestauranteQ11.parse(l); i++; } } sc.close();
    }
    public static ColecaoQ11 lerCsv() throws FileNotFoundException {
        ColecaoQ11 c = new ColecaoQ11(); if (new File("/tmp/restaurantes.csv").exists()) { c.ler("/tmp/restaurantes.csv"); } else { c.ler("C:/tmp/restaurantes.csv"); } return c;
    }
    public RestauranteQ11 buscar(int id) {
        RestauranteQ11 r = null; int i = 0; while (i < n && r == null) { if (v[i].getId() == id) { r = v[i]; } i++; } return r;
    }
}

class ListaSeqQ11 {
    private RestauranteQ11[] v;
    private int n;
    public ListaSeqQ11(int cap) { v = new RestauranteQ11[cap]; n = 0; }
    public void inserirInicio(RestauranteQ11 x) {
        int i = n;
        while (i > 0) { v[i] = v[i - 1]; i--; }
        v[0] = x; n++;
    }
    public void inserirFim(RestauranteQ11 x) { v[n] = x; n++; }
    public void inserir(RestauranteQ11 x, int pos) {
        int i = n;
        while (i > pos) { v[i] = v[i - 1]; i--; }
        v[pos] = x; n++;
    }
    public RestauranteQ11 removerInicio() {
        RestauranteQ11 x = v[0];
        int i = 0;
        while (i < n - 1) { v[i] = v[i + 1]; i++; }
        n--;
        return x;
    }
    public RestauranteQ11 removerFim() { n--; return v[n]; }
    public RestauranteQ11 remover(int pos) {
        RestauranteQ11 x = v[pos];
        int i = pos;
        while (i < n - 1) { v[i] = v[i + 1]; i++; }
        n--;
        return x;
    }
    public int tamanho() { return n; }
    public RestauranteQ11 get(int i) { return v[i]; }
}

public class Questao11 {
    public static void main(String[] args) throws Exception {
        ColecaoQ11 base = ColecaoQ11.lerCsv();
        ListaSeqQ11 lista = new ListaSeqQ11(1000);
        Scanner sc = new Scanner(System.in);

        boolean fimIds = false;
        while (sc.hasNextLine() && !fimIds) {
            String linha = sc.nextLine();
            if (TextoQ11.igual(linha, "-1")) {
                fimIds = true;
            } else {
                int id = TextoQ11.paraInt(linha);
                lista.inserirFim(base.buscar(id));
            }
        }

        int operacoes = TextoQ11.paraInt(sc.nextLine());
        int i = 0;
        while (i < operacoes && sc.hasNextLine()) {
            String cmd = sc.nextLine();
            if (TextoQ11.comecaCom(cmd, "II ")) {
                int id = TextoQ11.paraInt(TextoQ11.faixa(cmd, 3, cmd.length()));
                lista.inserirInicio(base.buscar(id));
            } else if (TextoQ11.comecaCom(cmd, "IF ")) {
                int id = TextoQ11.paraInt(TextoQ11.faixa(cmd, 3, cmd.length()));
                lista.inserirFim(base.buscar(id));
            } else if (TextoQ11.comecaCom(cmd, "I* ")) {
                String[] p = TextoQ11.separar(cmd, ' ');
                int pos = TextoQ11.paraInt(p[1]);
                int id = TextoQ11.paraInt(p[2]);
                lista.inserir(base.buscar(id), pos);
            } else if (TextoQ11.igual(cmd, "RI")) {
                RestauranteQ11 r = lista.removerInicio();
                System.out.println("(R)" + r.getNome());
            } else if (TextoQ11.igual(cmd, "RF")) {
                RestauranteQ11 r = lista.removerFim();
                System.out.println("(R)" + r.getNome());
            } else if (TextoQ11.comecaCom(cmd, "R* ")) {
                int pos = TextoQ11.paraInt(TextoQ11.faixa(cmd, 3, cmd.length()));
                RestauranteQ11 r = lista.remover(pos);
                System.out.println("(R)" + r.getNome());
            }
            i++;
        }
        sc.close();

        i = 0;
        while (i < lista.tamanho()) {
            System.out.println(lista.get(i).formatar());
            i++;
        }
    }
}
