import java.io.IOException;
import java.util.Scanner;
/**
 * IMPORTANT: 
 *      O nome da classe deve ser "Main" para que a sua solução execute
 *      Class name must be "Main" for your solution to execute
 *      El nombre de la clase debe ser "Main" para que su solución ejecutar
 */
class No{
    String str;
    No esq, dir;
    No(){
        str = "";
        esq = null;
        dir = null;
    }
    No(String s){
        str = s;
        esq = null;
        dir = null;
    }
}
class ABP{
    No raiz;
    ABP(){
        raiz = null;
    }
    public void inserir(String s){
        raiz = inserir(s, raiz);
    }
    private No inserir(String s, No no){
        if(no == null){
            no = new No(s);
        }else{
            if(s.compareTo(no.str) < 0){
                no.esq = inserir(s, no.esq);
            }else if(s.compareTo(no.str) > 0){
                 no.dir = inserir(s, no.dir);
            }
        }
        return no;
    }
    public void caminhar(){
        if(raiz != null){
            caminhar(raiz);
        }
    }
    private void caminhar(No no){
        if(no != null){
            caminhar(no.esq);
            System.out.println(no.str);
            caminhar(no.dir);
        }
    }
    
}
public class 1215bc{
 
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        ABP Ar = new ABP();
        while(sc.hasNext()){
            String s1 = "";
            for(int i = 0; i < s.length(); i++){
                if((s.charAt(i) >= 'a' && s.charAt(i) <= 'z'))
                {
                    s1 += s.charAt(i);
                }else if((s.charAt(i) >= 'A' &&s.charAt(i) <= 'Z'))
                {
                    s1 += (char) (s.charAt(i) - 32);
                }else{
                    if(s1.length() >= 1)
                    {
                        Ar.inserir(s1);
                    }
                    s1 = "";
                }
            }
            if(s1.length() >= 1)
            {
                Ar.inserir(s1);
            }
            s = sc.next();
        }
        Ar.caminhar();
        sc.close();
    }
 
}