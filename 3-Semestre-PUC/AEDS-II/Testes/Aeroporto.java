import java.util.Scanner;

class Celula{
    private String nome;
    private int forca;
    private Celula prox;
    public Celula(){
        this.nome = "";
        this.forca = 0;
        this.prox = null;
    }
    public Celula(String nome, int forca){
        this.nome = nome;
        this.forca = forca;
        this.prox = null;
    }


    public String getNome(){
        return this.nome;
    }
    public int getForca(){
        return this.forca;
    }

}

class Fila{
    private Celula primeiro;
    private Celula ultimo;
    public Fila(){
        this.primeiro = new Celula();
        this.ultimo = primeiro;
    }

    public void inserir(String nome, int forca){
        Celula tmp = new Celula(nome,forca);
        ultimo.prox = tmp;
        ultimo = tmp;
    }

    public Celula remover(){
        Celula i = null;
        if(primeiro != ultimo){
            i = primeiro.prox;
            primeiro.prox = i.prox;   
        }
        if(primeiro.prox == null){
            ultimo = primeiro;
        }
        return i;
    }
   
     public void imprimirPrimeiro(){
        if(primeiro != ultimo){
            System.out.println(primeiro.prox.getNome() + " " + primeiro.prox.getForca());
        }else{
            System.out.println("VAZIA");
        }
    }

    public int tamanho(){
        int i = 0;
        Celula x = primeiro.prox;
        while(x != null){
            i = i + 1;
            x = x.prox;
        }
        return i;
    }
}

class Aeroporto{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String op;
        String nome;
        int forca;
        Fila ff = new Fila();
        while(sc.hasNext()){
            op = sc.next();
            if(op.compareTo("E") == 0){
                nome = sc.next();
                forca = sc.nextInt();
                ff.inserir(nome,forca);
            }else if(op.compareTo("D") == 0){
                Celula i = ff.remover();
                if(i != null){ System.out.println(i.getNome() + " " + i.getForca());}
                else{System.out.println("VAZIA");}
            }else if(op.compareTo("F") == 0){
                ff.imprimirPrimeiro();
            }else if(op.compareTo("T") == 0){
                int i = ff.tamanho();
                System.out.println(i);
            }
        }
        sc.close();
    }
}
        

                
