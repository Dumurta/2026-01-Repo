import java.util.Scanner;

class No{
	int elemento;
	No esq, dir;
	public No(int x){
		elemento = x;
		esq = null;
		dir = null;
	}
}

class ArvoreBP{
	No raiz;

	ArvoreBP(){
		this.raiz = null;
	}
	public void inserir(int x){
		 raiz = inserir(x, raiz);
	}
	private No inserir(int x, No i){
		if(i == null){
			i = new No(x);
		}
		else if(x > i.elemento){
			inserir(x, i.dir);
		}else if(x <= i.elemento){
			inserir(x, i.esq);
		}else{
			//valor duplicado
		}

		return i;
	}
	
	public boolean pesquisar(int x){
		return pesquisar(x, raiz);
	}
	private boolean pesquisar(int x, No i){
		boolean resultado = false;
		if(i != null){
			System.out.println(i.elemento);
			if(x < i.elemento){
				pesquisar(x, i.esq);
			}else if(x > i.elemento){
				pesquisar(x, i.dir);
			}else if(x == i.elemento){
				resultado = true;
			}
		}
		return resultado;
	}

	public void caminhaPre(){
		if(raiz != null){caminhaPre(raiz);}
		else{ System.out.println("V");}
	}

	private void caminhaPre(No i){
		if(i != null){
			System.out.println(i.elemento + " ");
			caminhaPre(i.esq);
			caminhaPre(i.dir);
		}
	}

	public void caminhaPos(){
		if(raiz != null){
			caminhaPos(raiz);
		}
		else{System.out.println("V");}
	}

	private void caminhaPos(No i){
		if(i != null){
			caminhaPos(i.esq);
			caminhaPos(i.dir);
			System.out.println(i.elemento + " ");
		}
	}

	public void caminhaEM(){
		if(raiz != null){
			caminhaEM(raiz);
		}else{
		       	System.out.println("V");
		}
	}

	private void caminhaEM(No i){
		if( i != null){
			caminhaEM(i.esq);
			System.out.println(i.elemento + " " );
			caminhaEM(i.dir);
		}
	}

}

class LabArvoreBinaria{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		String op;
		int elemento;
		ArvoreBP arvore = new ArvoreBP();
		while(sc.hasNext()){
			op = sc.next();
			if(op.compareTo("I") == 0){
				elemento = sc.nextInt();
				arvore.inserir(elemento);
			}else if(op.compareTo("P") == 0){
				elemento = sc.nextInt();
				if(arvore.pesquisar(elemento)){
					System.out.println("S");
				}else{
					System.out.println("N");
				}
			}else if(op.compareTo("PRE") == 0){
				arvore.caminhaPre();
			}else if(op.compareTo("POS") == 0){
                                arvore.caminhaPos();
                        }else if(op.compareTo("EM") == 0){
                                arvore.caminhaEM();
                        }
		}	
		sc.close();
	}
}	

