import java.util.Scanner;
class Cesar{
 public static String Cifrar(String s1, int chave){
    	String s = new String();
    	for(int i = 0; i < s1.length(); i++){
        	char c = s1.charAt(i);
        	if(c == ' '){
				s+= '#';
			}else if(c >= 128 && c <= 255){
				s += c;
			}else{
				s += (char) (c + chave);
			}
    	}
    	return s;
	}

	public static void main(String[] args) {
	    	Scanner sc = new Scanner(System.in);
			int chave = 3;
			String s = sc.nextLine();
		while( s.length() != 3 
			  || s.charAt(0) != 'F' 
			  || s.charAt(1) != 'I' 
			  || s.charAt(2) != 'M') {
				System.out.println(Cifrar(s, chave));
				s = sc.nextLine();
    		}
		sc.close();
	}
}
