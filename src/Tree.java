import java.util.ArrayList;


public class Tree {
	private Node raiz;

	public Tree(ArrayList<Edge> matriz, int tam){
		raiz = new Node(matriz,tam);
		raiz.setFilhos(new ArrayList<Node>());
		raiz.setPai(null);
	}

	public Tree(Node raiz, int tam){
		this.raiz= raiz;
		this.raiz.setFilhos(new ArrayList<Node>());
		this.raiz.setPai(null);

	}


	/**
	 * Adciona filho a raiz

	 */
	public void AddFilhoARaiz(Node node){
		raiz.addFilho(node);
	}

	/**


	 */
	public void AddFilhoANo(Node pai, Node filho){
		pai.addFilho(filho);
	}

	public Node getRaiz() {
		return raiz;
	}

}
