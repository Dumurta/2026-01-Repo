class Celula{
    private int elemento;
    private Celular prox;
    Celula(int x){
        this.elemento = x;
        prox = null;
    }
}



class Pilha{
    private Celula topo;
    Pilha(){
        topo = null;
    }

    public void inserir(int x){
        Celula tmp = new Celula(x);
        tmp.prox = topo;
        topo = tmp;
        tmp = null;
    }

    public int remover() throws Exception {
        if(topo == null){
            throw new Exception("ERRO!");
        }
            int elemento = topo.elemento;
            Celula tmp = topo;
            topo = topo.prox;
            tmp.prox = null;
            tmp = null
        return elemento;
    }
