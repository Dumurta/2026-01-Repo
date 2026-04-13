import java.util.Scanner;
public class ValidaSenha{
public static boolean temMaiuscula(String s){
    boolean retorno = false;
    int i = 0;
    while(i < s.length()){
        if(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') retorno = true;
        i++;
    }
    return retorno;
}

public static boolean temMinuscula(String s){
    boolean retorno = false;
    int i = 0;
    while(i < s.length()){
        if(s.charAt(i) >= 'a' && s.charAt(i) <= 'z') retorno = true;
        i++;
    }
    return retorno;
}

public static boolean temNumero(String s){
    boolean retorno = false;
    int i = 0;
    while(i < s.length()){
        if(s.charAt(i) >= '0' && s.charAt(i) <= '9') retorno = true;
        i++;
    }
    return retorno;
}

public static boolean temEspecial(String s){
    boolean retorno = false;
    int i = 0;
    while(i < s.length()){
        char c = s.charAt(i);
        if(!(c >= 'A' && c <= 'Z') &&
           !(c >= 'a' && c <= 'z') &&
           !(c >= '0' && c <= '9') &&
           c != ' '){
            retorno = true;
        }
        i++;
    }
    return retorno;
}

public static boolean validaSenha(String s){
    boolean retorno = false;
    if(s.length() >= 8 &&
       temMaiuscula(s) &&
       temMinuscula(s) &&
       temNumero(s)    &&
       temEspecial(s)){
        retorno = true;
    }
    return retorno;
}


public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    String Senha = sc.next();
    while(!(Senha.charAt(0) == 'F' && Senha.charAt(1) == 'I' && Senha.charAt(2) == 'M')){
        if(validaSenha(Senha) == true){ System.out.println("SIM");}
        else{System.out.println("NAO");}
        Senha = sc.next();
    }
    sc.close();

}
}
