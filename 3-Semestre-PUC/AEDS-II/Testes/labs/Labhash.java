class TabelaHashComReserva{
    String[] tabela;
    int m; 
    int r;
    int nr;
    TabelaHashComReserva(int m1, int r1){
        this.tabela = new String[m1+r1];
        this.m = m1;
        this.r = r1;
        this.nr = 0;
    }
    private int hash(String chave){
        int hash = 0;
        int soma = 0;
        for(int i = 0; i < chave.length(); i++){
            soma += (int) chave.charAt(i);
        }
        hash = soma % m;
        return hash;
    }
    private boolean isPosicaoLivre(int pos){
        boolean resultado = true;
        if(tabela[pos] != null){
            resultado = false;
        }
        return resultado;
    }
    public void inserir(String chave){
        int pos = hash(chave);
        if(isPosicaoLivre(pos)){
            tabela[pos] = chave;
        }else{
            for(int i = m; i < m+r; i++){
                if(isPosicaoLivre(i)){
                    tabela[i] = chave;
                    i = r+1;
                }
            }
        }
    }
    public String pesquisar(String chave){
        String s = "";
        int pos = hash(chave);
        if(tabela[pos] != null){
            if(chave.compareTo(tabela[pos]) == 0){
                s = tabela[pos];
            }else{
                for(int i = m; i < m+r;i++){
                    if(chave.compareTo(tabela[i]) == 0){
                        s = tabela[i];
                        i = r+1;
                    }
                }
            }
        }
        return s;
    }
    public String remover(String chave){
        String s = "";
        int pos = hash(chave);
        if(tabela[pos] != null){
            if(chave.compareTo(tabela[pos]) == 0){
                s = tabela[pos];
                tabela[pos] = null;
            }else{
                for(int i = m; i < m+r;i++){
                    if(chave.compareTo(tabela[i]) == 0){
                        s = tabela[i];
                        tabela[i] = null;
                        i = r+1;
                    }
                }
            }
        }
        return s;
    }
}