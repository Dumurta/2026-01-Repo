import java.util.Scanner;

class Ls{
    public static boolean engual(String s1, String s2){
        boolean resp = true;
        for(int i = 0; i < s1.length(); i++){
            if(s1.charAt(i) != s2.charAt(i)){
                resp = false;
            }
        }
        return resp;
    }//end engual (o nome e' proposital)
    public static boolean vogais(String s1){
        boolean resp = true;
        for(int i = 0; i < s1.length(); i++){
            if(!(s1.charAt(i) == 'a' || s1.charAt(i) == 'e' || s1.charAt(i) == 'i' ||
            s1.charAt(i) == 'o' || s1.charAt(i) == 'u')){
                resp = false;
                i = s1.length();
            }
        }
        return resp;
    }//end vogais
    public static boolean conso(String s1){
        boolean resp = true;
        for(int i = 0; i < s1.length(); i++){
            char c = s1.charAt(i);
            if(!((c >= 'a' && c <= 'z') && !(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'))){
                resp = false;
            }
        }
        return resp;
    }//end conso
    public static boolean numero(String s1){
        boolean resp = true;
        for(int i = 0; i < s1.length(); i++){
            if(!(s1.charAt(i) >= '0' && s1.charAt(i) <= '9')) resp = false;
        }
        return resp;
    }//end numero
    public static boolean numeroReal(String s1){
        boolean separador = false;
        for(int i = 0; i < s1.length(); i++){
            char c = s1.charAt(i);
            if(c == '.' || c == ','){
                separador = true;
            } else if(!(c >= '0' && c <= '9')){
                separador = false;
                i = s1.length();
            }
        }
        return separador;
    }//end numeroReal
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!(s.length() == 3 && engual(s, "FIM"))){
            System.out.print(vogais(s) ? "SIM" : "NAO");
            System.out.print(" ");
            System.out.print(conso(s) ? "SIM" : "NAO");
            System.out.print(" ");
            System.out.print(numero(s) ? "SIM" : "NAO");
            System.out.print(" ");
            System.out.println(numeroReal(s) ? "SIM" : "NAO");
            s = sc.nextLine();
        }
        sc.close();
    }// end main











}
