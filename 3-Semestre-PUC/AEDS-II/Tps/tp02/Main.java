import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Restaurante[] restaurantes = colecao.getRestaurantes();

        Scanner sc = new Scanner(System.in);
        boolean encerrarLeitura = false;
        while (sc.hasNextInt() && !encerrarLeitura) {
            int id = sc.nextInt();
            if (id == -1) {
                encerrarLeitura = true;
            } else {
                boolean encontrado = false;
                int i = 0;
                while (i < colecao.getTamanho() && !encontrado) {
                    if (restaurantes[i].getId() == id) {
                        System.out.println(restaurantes[i].formatar());
                        encontrado = true;
                    }
                    i++;
                }
            }
        }
        sc.close();
    }
}
