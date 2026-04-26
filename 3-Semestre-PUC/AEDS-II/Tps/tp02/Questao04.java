import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

class TextoQ04 {
    public static boolean igual(String a, String b) {
        boolean resp = true;
        int i = 0;
        if (a.length() != b.length()) {
            resp = false;
        } else {
            while (i < a.length() && resp) {
                if (a.charAt(i) != b.charAt(i)) {
                    resp = false;
                }
                i++;
            }
        }
        return resp;
    }
    public static int paraInt(String s) {
        int i = 0;
        int valor = 0;
        int sinal = 1;
        if (s.length() > 0 && s.charAt(0) == '-') {
            sinal = -1;
            i = 1;
        }
        while (i < s.length()) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                valor = valor * 10 + (s.charAt(i) - '0');
            }
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
            if (c == '.') {
                decimal = true;
            } else if (c >= '0' && c <= '9') {
                if (!decimal) {
                    parteInteira = parteInteira * 10 + (c - '0');
                } else {
                    parteDecimal = parteDecimal * 10 + (c - '0');
                    fator = fator * 10;
                }
            }
            i++;
        }
        return parteInteira + ((double)parteDecimal / fator);
    }
    public static int contarPartes(String s, char sep) {
        int i = 0;
        int total = 1;
        while (i < s.length()) {
            if (s.charAt(i) == sep) { total++; }
            i++;
        }
        return total;
    }
    public static String[] separar(String s, char sep) {
        int total = contarPartes(s, sep);
        String[] partes = new String[total];
        int i = 0;
        int idx = 0;
        String atual = "";
        while (i < s.length()) {
            if (s.charAt(i) == sep) {
                partes[idx] = atual;
                idx++;
                atual = "";
            } else {
                atual = atual + s.charAt(i);
            }
            i++;
        }
        partes[idx] = atual;
        return partes;
    }
    public static int posChar(String s, char alvo) {
        int i = 0;
        int pos = -1;
        while (i < s.length() && pos == -1) {
            if (s.charAt(i) == alvo) { pos = i; }
            i++;
        }
        return pos;
    }
    public static String faixa(String s, int ini, int fim) {
        String resp = "";
        int i = ini;
        while (i < fim) {
            resp = resp + s.charAt(i);
            i++;
        }
        return resp;
    }
}

class DataQ04 {
    private int ano;
    private int mes;
    private int dia;
    public DataQ04(int ano, int mes, int dia) { this.ano = ano; this.mes = mes; this.dia = dia; }
    public int getAno() { return ano; }
    public static DataQ04 parseData(String s) {
        int ano = (s.charAt(0) - '0') * 1000 + (s.charAt(1) - '0') * 100 + (s.charAt(2) - '0') * 10 + (s.charAt(3) - '0');
        int mes = (s.charAt(5) - '0') * 10 + (s.charAt(6) - '0');
        int dia = (s.charAt(8) - '0') * 10 + (s.charAt(9) - '0');
        return new DataQ04(ano, mes, dia);
    }
    public String formatar() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
}

class HoraQ04 {
    private int hora;
    private int minuto;
    public HoraQ04(int hora, int minuto) { this.hora = hora; this.minuto = minuto; }
    public static HoraQ04 parseHora(String s) {
        int hora = (s.charAt(0) - '0') * 10 + (s.charAt(1) - '0');
        int minuto = (s.charAt(3) - '0') * 10 + (s.charAt(4) - '0');
        return new HoraQ04(hora, minuto);
    }
    public String formatar() { return String.format("%02d:%02d", hora, minuto); }
}

class RestauranteQ04 {
    private int id;
    private String nome;
    private String cidade;
    private int capacidade;
    private double avaliacao;
    private String[] tiposCozinha;
    private int faixaPreco;
    private HoraQ04 horarioAbertura;
    private HoraQ04 horarioFechamento;
    private DataQ04 dataAbertura;
    private boolean aberto;

    public RestauranteQ04(int id, String nome, String cidade, int capacidade, double avaliacao, String[] tiposCozinha,
                          int faixaPreco, HoraQ04 horarioAbertura, HoraQ04 horarioFechamento, DataQ04 dataAbertura, boolean aberto) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.avaliacao = avaliacao;
        this.tiposCozinha = tiposCozinha;
        this.faixaPreco = faixaPreco;
        this.horarioAbertura = horarioAbertura;
        this.horarioFechamento = horarioFechamento;
        this.dataAbertura = dataAbertura;
        this.aberto = aberto;
    }
    public int getId() { return id; }
    public String getCidade() { return cidade; }
    public static RestauranteQ04 parseRestaurante(String s) {
        String[] c = TextoQ04.separar(s, ',');
        String horario = c[7];
        int p = TextoQ04.posChar(horario, '-');
        String h1 = TextoQ04.faixa(horario, 0, p);
        String h2 = TextoQ04.faixa(horario, p + 1, horario.length());
        return new RestauranteQ04(
            TextoQ04.paraInt(c[0]),
            c[1],
            c[2],
            TextoQ04.paraInt(c[3]),
            TextoQ04.paraDouble(c[4]),
            TextoQ04.separar(c[5], ';'),
            c[6].length(),
            HoraQ04.parseHora(h1),
            HoraQ04.parseHora(h2),
            DataQ04.parseData(c[8]),
            TextoQ04.igual(c[9], "true")
        );
    }
    private static String faixaPrecoParaString(int faixa) {
        String txt = "$";
        if (faixa == 2) { txt = "$$"; } else if (faixa == 3) { txt = "$$$"; } else if (faixa == 4) { txt = "$$$$"; }
        return txt;
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

class ColecaoQ04 {
    private RestauranteQ04[] restaurantes;
    private int tamanho;
    private int contar(String path) throws FileNotFoundException {
        int total = 0;
        Scanner sc = new Scanner(new File(path));
        if (sc.hasNextLine()) { sc.nextLine(); }
        while (sc.hasNextLine()) { if (sc.nextLine().length() > 0) { total++; } }
        sc.close();
        return total;
    }
    public void lerCsv(String path) throws FileNotFoundException {
        tamanho = contar(path);
        restaurantes = new RestauranteQ04[tamanho];
        Scanner sc = new Scanner(new File(path));
        if (sc.hasNextLine()) { sc.nextLine(); }
        int i = 0;
        while (sc.hasNextLine() && i < tamanho) {
            String linha = sc.nextLine();
            if (linha.length() > 0) { restaurantes[i] = RestauranteQ04.parseRestaurante(linha); i++; }
        }
        sc.close();
    }
    public static ColecaoQ04 lerCsv() throws FileNotFoundException {
        ColecaoQ04 c = new ColecaoQ04();
        File linux = new File("/tmp/restaurantes.csv");
        if (linux.exists()) { c.lerCsv("/tmp/restaurantes.csv"); } else { c.lerCsv("C:/tmp/restaurantes.csv"); }
        return c;
    }
    public RestauranteQ04 buscarPorId(int id) {
        RestauranteQ04 resp = null;
        int i = 0;
        while (i < tamanho && resp == null) {
            if (restaurantes[i].getId() == id) { resp = restaurantes[i]; }
            i++;
        }
        return resp;
    }
}

public class Questao04 {
    private static void insertionSortCidade(RestauranteQ04[] v, int n) {
        int i = 1;
        while (i < n) {
            RestauranteQ04 tmp = v[i];
            int j = i - 1;
            while (j >= 0 && v[j].getCidade().compareTo(tmp.getCidade()) > 0) {
                v[j + 1] = v[j];
                j--;
            }
            v[j + 1] = tmp;
            i++;
        }
    }
    public static void main(String[] args) throws Exception {
        ColecaoQ04 base = ColecaoQ04.lerCsv();
        RestauranteQ04[] sel = new RestauranteQ04[500];
        int n = 0;
        Scanner sc = new Scanner(System.in);
        boolean fim = false;
        while (sc.hasNextInt() && !fim) {
            int id = sc.nextInt();
            if (id == -1) { fim = true; } else { sel[n] = base.buscarPorId(id); n++; }
        }
        sc.close();
        insertionSortCidade(sel, n);
        int i = 0;
        while (i < n) { System.out.println(sel[i].formatar()); i++; }
    }
}
