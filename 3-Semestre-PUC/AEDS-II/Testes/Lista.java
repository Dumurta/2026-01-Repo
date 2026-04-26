class Lista{
	private int[] array;
	private int n;
	
	public Lista(int tamanho){
		array = new int[tamanho];
		n = 0;
	}
	
	//Inserir Fim O(1)
	public void InserirFim(int x) throws Exception{
		if(n >= array.length) throw new Exception("ERRO: LISTA CHEIA");
		array[n] = x;
		n++;
	}
	
	public void inserirInicio(intx) throws Exception{
		if(n >= array.length) throw new Exception("ERRO: LISTA CHEIA");
		//pra inserir um elemento no inicio da minha lista preciso fazer o
		//shift dos elementos a partir de n=
