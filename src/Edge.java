

public class Edge implements Comparable<Edge> {

	private int prox;
	private int ant;
	private int peso;

	public Edge(int prox, int ant, int peso) {
		super();
		this.prox = prox;
		this.ant = ant;
		this.peso = peso;
	}

	public int getProx() {
		return prox;
	}

	public void setProx(int prox) {
		this.prox = prox;
	}

	public int getAnt() {
		return ant;
	}

	public void setAnt(int ant) {
		this.ant = ant;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public int compareTo(Edge arg0) {
		if(this.getPeso() > arg0.getPeso()){
			return 1;
		}
		else if(this.getPeso() < arg0.getPeso()){
			return -1;
		}
		else{
			return 0;
		}
	}


}
