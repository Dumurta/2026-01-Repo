import java.util.Scanner;

class Celula {
    int valor;
    Celula direita;
    Celula abaixo;
    public Celula(int valor) { this.valor = valor; this.direita = null; this.abaixo = null; }
}

class Matriz {
    private Celula inicio;
    private int linhas;
    private int colunas;

    public Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        Celula[] anterior = new Celula[colunas];
        int i = 0;
        while (i < linhas) {
            Celula inicioLinha = null;
            Celula ultimaLinha = null;
            int j = 0;
            while (j < colunas) {
                Celula nova = new Celula(0);
                if (j == 0) { inicioLinha = nova; }
                else { ultimaLinha.direita = nova; }
                if (i == 0) { anterior[j] = nova; }
                else { anterior[j].abaixo = nova; anterior[j] = nova; }
                ultimaLinha = nova;
                j++;
            }
            if (i == 0) { inicio = inicioLinha; }
            i++;
        }
    }

    private Celula getCelula(int lin, int col) {
        Celula atual = inicio; int i = 0;
        while (i < lin) { atual = atual.abaixo; i++; }
        int j = 0;
        while (j < col) { atual = atual.direita; j++; }
        return atual;
    }

    public void set(int lin, int col, int val) { getCelula(lin, col).valor = val; }
    public int get(int lin, int col) { return getCelula(lin, col).valor; }

    public Matriz somar(Matriz m) {
        Matriz res = new Matriz(linhas, colunas);
        int i = 0;
        while (i < linhas) {
            int j = 0;
            while (j < colunas) { res.set(i, j, get(i, j) + m.get(i, j)); j++; }
            i++;
        }
        return res;
    }

    public Matriz multiplicar(Matriz m) {
        Matriz res = new Matriz(linhas, m.colunas);
        int i = 0;
        while (i < linhas) {
            int j = 0;
            while (j < m.colunas) {
                int soma = 0; int k = 0;
                while (k < colunas) { soma = soma + get(i, k) * m.get(k, j); k++; }
                res.set(i, j, soma);
                j++;
            }
            i++;
        }
        return res;
    }

    public void mostrarDiagonalPrincipal() {
        int i = 0; boolean primeiro = true;
        while (i < linhas && i < colunas) {
            if (!primeiro) { System.out.print(" "); }
            System.out.print(get(i, i));
            primeiro = false; i++;
        }
        System.out.println();
    }

    public void mostrarDiagonalSecundaria() {
        int i = 0; boolean primeiro = true;
        while (i < linhas && i < colunas) {
            if (!primeiro) { System.out.print(" "); }
            System.out.print(get(i, colunas - 1 - i));
            primeiro = false; i++;
        }
        System.out.println();
    }

    public void mostrar() {
        int i = 0;
        while (i < linhas) {
            int j = 0;
            while (j < colunas) {
                if (j > 0) { System.out.print(" "); }
                System.out.print(get(i, j));
                j++;
            }
            System.out.println(); i++;
        }
    }
}

public class Questao09TP3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int casos = sc.nextInt();
        int c = 0;
        while (c < casos) {
            int l1 = sc.nextInt(); int c1 = sc.nextInt();
            Matriz m1 = new Matriz(l1, c1);
            Matriz m2 = new Matriz(l1, c1);
            int i = 0;
            while (i < l1) { int j = 0; while (j < c1) { m1.set(i, j, sc.nextInt()); j++; } i++; }
            i = 0;
            while (i < l1) { int j = 0; while (j < c1) { m2.set(i, j, sc.nextInt()); j++; } i++; }
            m1.mostrarDiagonalPrincipal();
            m2.mostrarDiagonalSecundaria();
            m1.somar(m2).mostrar();
            m1.multiplicar(m2).mostrar();
            c++;
        }
        sc.close();
    }
}
