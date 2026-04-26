import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ColecaoRestaurantes {
    private static final String PATH_CSV = "/tmp/restaurantes.csv";
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
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
        while (sc.hasNextLine()) {
            String linha = sc.nextLine().trim();
            if (!linha.isEmpty()) {
                numeroDeRegistros++;
            }
        }
        sc.close();
        return numeroDeRegistros;
    }

    public void lerCsv(String path) throws FileNotFoundException {
        tamanho = obterNumeroDeRegistrosCsv(path);
        restaurantes = new Restaurante[tamanho];

        Scanner sc = new Scanner(new File(path));
        if (sc.hasNextLine()) {
            sc.nextLine();
        }

        int indice = 0;
        while (sc.hasNextLine() && indice < tamanho) {
            String linha = sc.nextLine().trim();
            if (!linha.isEmpty()) {
                restaurantes[indice] = Restaurante.parseRestaurante(linha);
                indice++;
            }
        }
        sc.close();
    }

    public static ColecaoRestaurantes lerCsv() throws FileNotFoundException {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes();
        colecao.lerCsv(PATH_CSV);
        return colecao;
    }
}
