import java.util.Scanner;

class Inversor {

    public static char toLower(char c) {
        if (c >= 'A' && c <= 'Z') {
            c = (char)(c + 32);
        }
        return c;
    }

    // Constrói a string invertida recursivamente
    public static String inversor(String s, int i) {
        String resp = "";
        if (i >= 0) {
            resp = s.charAt(i) + inversor(s, i - 1);
        }
        return resp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while (!(s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')) {
            System.out.println(inversor(s, s.length() - 1));
            s = sc.nextLine();
        }
        sc.close();
    }
}
