import java.util.Scanner;


public class Pilha{
	public static int[] empilharPush(int[] pilha, int x){
		int i = 0;
	       	while(pilha[i] >= 0) //caminha ate achar o vazio
		{	
			i++;
		}
		pilha[i] = x;
		return pilha;
	}
	public static int add(int[] pilha){
	       	int i = 0;
       	       	while(pilha[i] >= 0 && pilha[i] <= 9){
			i++;
	 	}
		int add = pilha[i] + pilha[i-1];
 		return add;		
	}

	public static int sub(int[] pilha){
                int i = 0;
                while(pilha[i] >= 0 && pilha[i] <= 9){
                        i++;
                }
                int sub = pilha[i] - pilha[i-1];
                return sub;
        }

	public static int mul(int[] pilha){
                int i = 0;
                while(pilha[i] >= 0 && pilha[i] <= 9){
                        i++;
                }
                int mul = pilha[i] * pilha[i-1];
                return mul;
        }

	public static int div(int[] pilha){
                int i = 0;
                while(pilha[i] >= 0 && pilha[i] <= 9){
                        i++;
                }
                int div = pilha[i] / pilha[i-1];
                return div;
        }
	public static int mul(int[] pilha){
                int i = 0;
                while(pilha[i] >= 0 && pilha[i] <= 9){
                        i++;
                }
                int out = pilha[i];
                return out;
        }




	publis static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] pilha = new Int[n];
		char op = sc.nextChar();
		int val = sc.nextInt();
		if(op == 'p'){
			System.out.println(empilharPush(pilha, val));
		}else if(op == 'a'){
			System.out.println(add(pilha));	
		}else if(op == 's'){
			System.out.println(sub(pilha));
		}else if(op == 'm'){
			System.out.println(mul(pilha));
		}else if(op == 'd'){
			System.out.println(div(pilha));
		}else if(op == 'o'){
			System.out.println(out(pilha));
		}
		

	}







}
