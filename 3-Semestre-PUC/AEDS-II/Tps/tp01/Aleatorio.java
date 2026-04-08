import java.util.Scanner;
import java.util.Random;

class Aleatorio{
	public static String trocador(String s1, Random gerador){

		//sorteio das letras a 1 vai ser a letra que sera trocada e a 2 e' a substituta

		char letra1 = ((char)('a'+(Math.abs(gerador.nextInt())%26)));
		char letra2 = ((char)('a'+(Math.abs(gerador.nextInt())%26)));
		
		String s2 = "";
		for(int i = 0; i < s1.length(); i++){
			if(s1.charAt(i) == letra1){
				s2 += letra2;
			}else{
				s2 += s1.charAt(i);
			}
		}
		return s2;
	}	

	public static void main(String[] args) {
        	Scanner sc = new Scanner(System.in);
		Random gerador = new Random();
        	gerador.setSeed(4);
        	String s = sc.nextLine();
		while (!(s.length() == 3 && s.charAt(0)== 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M')) {
           	 	String resultado = trocador(s, gerador);
            		System.out.println(resultado);
            		s = sc.nextLine();
        	}
        	sc.close();
    	}
}
