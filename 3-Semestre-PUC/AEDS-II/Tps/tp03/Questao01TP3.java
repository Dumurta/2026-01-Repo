import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Scanner;

class Texto {
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

    public static int comparar(String a, String b) {
        return a.compareTo(b);
    }
}

class Data {
    private int ano;
    private int mes;
    private int dia;

    public Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public int getAno() { return ano; }
    public int getMes() { return mes; }
    public int getDia() { return dia; }

    public static Data parseData(String s) {
        int ano = (s.charAt(0) - '0') * 1000 + (s.charAt(1) - '0') * 100 + (s.charAt(2) - '0') * 10 + (s.charAt(3) - '0');
        int mes = (s.charAt(5) - '0') * 10 + (s.charAt(6) - '0');
        int dia = (s.charAt(8) - '0') * 10 + (s.charAt(9) - '0');
        return new Data(ano, mes, dia);
    }

    public String formatar() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}

class Hora {
    private int hora;
    private int minuto;

    public Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public int getHora() { return hora; }
    public int getMinuto() { return minuto; }

    public static Hora parseHora(String s) {
        int hora = (s.charAt(0) - '0') * 10 + (s.charAt(1) - '0');
        int minuto = (s.charAt(3) - '0') * 10 + (s.charAt(4) - '0');
        return new Hora(hora, minuto);
    }

    public String formatar() {
        return String.format("%02d:%02d", hora, minuto);
    }
}

class Restaurante {
    private int id;
    private String nome;
    private String cidade;
    private int capacidade;
    private double avaliacao;
    private String[] tiposCozinha;
    private int faixaPreco;
    private Hora horarioAbertura;
    private Hora horarioFechamento;
    private Data dataAbertura;
    private boolean aberto;

    public Restaurante(int id, String nome, String cidade, int capacidade,
                       double avaliacao, String[] tiposCozinha, int faixaPreco,
                       Hora horarioAbertura, Hora horarioFechamento,
                       Data dataAbertura, boolean aberto) {
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
    public String getNome() { return nome; }
    public String getCidade() { return cidade; }
    public int getCapacidade() { return capacidade; }
    public double getAvaliacao() { return avaliacao; }
    public String[] getTiposCozinha() { return tiposCozinha; }
    public int getFaixaPreco() { return faixaPreco; }
    public Hora getHorarioAbertura() { return horarioAbertura; }
    public Hora getHorarioFechamento() { return horarioFechamento; }
    public Data getDataAbertura() { return dataAbertura; }
    public boolean isAberto() { return aberto; }

    public static Restaurante parseRestaurante(String s) {
        String[] campos = Texto.separar(s, ',');
        int id = Texto.paraInt(campos[0]);
        String nome = campos[1];
        String cidade = campos[2];
        int capacidade = Texto.paraInt(campos[3]);
        double avaliacao = Texto.paraDouble(campos[4]);
        String[] tiposCozinha = Texto.separar(campos[5], ';');
        int faixaPreco = campos[6].length();

        String horario = campos[7];
        int dashPos = Texto.posChar(horario, '-');
        Hora horarioAbertura = Hora.parseHora(Texto.faixa(horario, 0, dashPos));
        Hora horarioFechamento = Hora.parseHora(Texto.faixa(horario, dashPos + 1, horario.length()));

        Data dataAbertura = Data.parseData(campos[8]);
        boolean aberto = Texto.igual(campos[9], "true");

        return new Restaurante(id, nome, cidade, capacidade, avaliacao,
                tiposCozinha, faixaPreco, horarioAbertura, horarioFechamento,
                dataAbertura, aberto);
    }

    private static String faixaPrecoParaString(int faixa) {
        String faixaTexto = "$";
        if (faixa == 2) { faixaTexto = "$$"; }
        else if (faixa == 3) { faixaTexto = "$$$"; }
        else if (faixa == 4) { faixaTexto = "$$$$"; }
        return faixaTexto;
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

class ColecaoRestaurantes {
    private static final String pathCsv = "/tmp/restaurantes.csv";
    private int tamanho;
    private Restaurante[] restaurantes;

    public ColecaoRestaurantes() {
        this.tamanho = 0;
        this.restaurantes = new Restaurante[0];
    }

    public int getTamanho() { return tamanho; }
    public Restaurante[] getRestaurantes() { return restaurantes; }

    private int obterNumeroDeRegistrosCsv(String path) throws FileNotFoundException {
        int numeroDeRegistros = 0;
        Scanner sc = new Scanner(new File(path));
        if (sc.hasNextLine()) { sc.nextLine(); }
        while (sc.hasNextLine()) {
            String linha = sc.nextLine();
            if (linha.length() > 0) { numeroDeRegistros++; }
        }
        sc.close();
        return numeroDeRegistros;
    }

    public void lerCsv(String path) throws FileNotFoundException {
        tamanho = obterNumeroDeRegistrosCsv(path);
        restaurantes = new Restaurante[tamanho];
        Scanner sc = new Scanner(new File(path));
        if (sc.hasNextLine()) { sc.nextLine(); }
        int indice = 0;
        while (sc.hasNextLine() && indice < tamanho) {
            String linha = sc.nextLine();
            if (linha.length() > 0) {
                restaurantes[indice] = Restaurante.parseRestaurante(linha);
                indice++;
            }
        }
        sc.close();
    }

    public static ColecaoRestaurantes lerCsv() throws FileNotFoundException {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes();
        colecao.lerCsv(pathCsv);
        return colecao;
    }
}

public class Questao01TP3 {

    static long comparacoes = 0;
    static long movimentacoes = 0;

    public static void selecaoParcial(Restaurante[] v, int n, int k) {
        int limite = k;
        if (n < k) { limite = n; }
        int i = 0;
        while (i < limite) {
            int minIdx = i;
            int j = i + 1;
            while (j < n) {
                comparacoes++;
                if (v[j].getNome().compareTo(v[minIdx].getNome()) < 0) {
                    minIdx = j;
                }
                j++;
            }
            if (minIdx != i) {
                Restaurante temp = v[i];
                v[i] = v[minIdx];
                v[minIdx] = temp;
                movimentacoes += 3;
            }
            i++;
        }
    }

    public static void main(String[] args) throws Exception {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Restaurante[] todos = colecao.getRestaurantes();

        Scanner sc = new Scanner(System.in);
        Restaurante[] selecionados = new Restaurante[colecao.getTamanho()];
        int n = 0;
        int id = sc.nextInt();
        while (id != -1) {
            int i = 0;
            boolean encontrado = false;
            while (i < colecao.getTamanho() && !encontrado) {
                if (todos[i].getId() == id) {
                    selecionados[n] = todos[i];
                    n++;
                    encontrado = true;
                }
                i++;
            }
            id = sc.nextInt();
        }
        sc.close();

        long inicio = System.currentTimeMillis();
        selecaoParcial(selecionados, n, 10);
        long fim = System.currentTimeMillis();
        double tempo = (fim - inicio) / 1000.0;

        int i = 0;
        while (i < n) {
            System.out.println(selecionados[i].formatar());
            i++;
        }

        FileWriter fw = new FileWriter("884985_selecao_parcial.txt");
        fw.write("884985\t" + comparacoes + "\t" + movimentacoes + "\t" + String.format(Locale.US, "%.2f", tempo) + "\n");
        fw.close();
    }
}