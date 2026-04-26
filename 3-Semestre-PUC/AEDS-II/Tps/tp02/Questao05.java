import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class TextoQ05 {
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
            valor = valor * 10 + (s.charAt(i) - '0');
            i++;
        }
        return valor * sinal;
    }

    public static void separarDoisCamposCsv(String s, String[] campos) {
        int i = 0;
        String c0 = "";
        String c1 = "";
        while (i < s.length() && s.charAt(i) != ',') {
            c0 = c0 + s.charAt(i);
            i++;
        }
        i++;
        while (i < s.length() && s.charAt(i) != ',') {
            c1 = c1 + s.charAt(i);
            i++;
        }
        campos[0] = c0;
        campos[1] = c1;
    }
}

class RestauranteQ05 {
    private int id;
    private String nome;
    public RestauranteQ05(int id, String nome) { this.id = id; this.nome = nome; }
    public int getId() { return id; }
    public String getNome() { return nome; }
    public static RestauranteQ05 parseRestaurante(String s) {
        String[] campos = new String[2];
        TextoQ05.separarDoisCamposCsv(s, campos);
        return new RestauranteQ05(TextoQ05.paraInt(campos[0]), campos[1]);
    }
}

class ColecaoQ05 {
    private RestauranteQ05[] restaurantes;
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
        restaurantes = new RestauranteQ05[tamanho];
        Scanner sc = new Scanner(new File(path));
        if (sc.hasNextLine()) { sc.nextLine(); }
        int i = 0;
        while (sc.hasNextLine() && i < tamanho) {
            String linha = sc.nextLine();
            if (linha.length() > 0) { restaurantes[i] = RestauranteQ05.parseRestaurante(linha); i++; }
        }
        sc.close();
    }
    public static ColecaoQ05 lerCsv() throws FileNotFoundException {
        ColecaoQ05 c = new ColecaoQ05();
        File linux = new File("/tmp/restaurantes.csv");
        if (linux.exists()) { c.lerCsv("/tmp/restaurantes.csv"); } else { c.lerCsv("C:/tmp/restaurantes.csv"); }
        return c;
    }
    public RestauranteQ05 buscarPorId(int id) {
        RestauranteQ05 resp = null;
        int i = 0;
        while (i < tamanho && resp == null) {
            if (restaurantes[i].getId() == id) { resp = restaurantes[i]; }
            i++;
        }
        return resp;
    }
}

public class Questao05 {
    public static void main(String[] args) throws Exception {
        ColecaoQ05 base = ColecaoQ05.lerCsv();
        RestauranteQ05[] sel = new RestauranteQ05[500];
        int n = 0;
        Scanner sc = new Scanner(System.in);
        boolean fimIds = false;
        while (sc.hasNextLine() && !fimIds) {
            String linha = sc.nextLine();
            if (TextoQ05.igual(linha, "-1")) {
                fimIds = true;
            } else {
                int id = TextoQ05.paraInt(linha);
                sel[n] = base.buscarPorId(id);
                n++;
            }
        }

        boolean fimNomes = false;
        while (sc.hasNextLine() && !fimNomes) {
            String nomeBusca = sc.nextLine();
            if (TextoQ05.igual(nomeBusca, "FIM")) {
                fimNomes = true;
            } else {
                boolean achou = false;
                int i = 0;
                while (i < n && !achou) {
                    if (TextoQ05.igual(sel[i].getNome(), nomeBusca)) { achou = true; }
                    i++;
                }
                if (achou) { System.out.println("SIM"); } else { System.out.println("NAO"); }
            }
        }
        sc.close();
    }
}
