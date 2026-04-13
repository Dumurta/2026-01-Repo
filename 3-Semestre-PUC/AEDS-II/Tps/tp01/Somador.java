import java.util.Scanner;

class Somador {

    public static int somador(int numero) {
        int resultado = 0;
        if (numero > 0) {
            resultado = (numero % 10) + somador(numero / 10);
        }
        return resultado;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int numero = sc.nextInt();
            System.out.println(somador(numero));
        }
        sc.close();
    }
}
