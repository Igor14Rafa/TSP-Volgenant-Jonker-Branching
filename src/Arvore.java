import java.util.ArrayList;


public class Arvore {
	private No raiz;

	public Arvore(ArrayList<Aresta> matriz, int tam){
		raiz = new No(matriz,tam);
		raiz.setFilhos(new ArrayList<No>());
		raiz.setPai(null);
	}

	public Arvore(No raiz, int tam){
		this.raiz= raiz;
		this.raiz.setFilhos(new ArrayList<No>());
		this.raiz.setPai(null);

	}


	/**
	 * Adciona filho a raiz

	 */
	public void AddFilhoARaiz(No no){
		raiz.addFilho(no);
	}

	/**


	 */
	public void AddFilhoANo(No pai, No filho){
		pai.addFilho(filho);
	}

	public No getRaiz() {
		return raiz;
	}

}
