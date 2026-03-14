import java.util.Scanner;
public static String Cifrar(String s1, int chave){
    String s = new String();
    for(int i = 0; i < s1.length(); i++){
        char c = s1.charAt(i);
        if((c > 'A' && c > 'Z' ) || (c > 'a' && c < 'z') ){
        s += (char) (c + chave);
        }else{
            s += c;
        }
    }
    return s;
}

public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int chave = sc.nextInt();
    String s = sc.next();
    System.out.println(Cifrar(s, chave));
    sc.close();
}