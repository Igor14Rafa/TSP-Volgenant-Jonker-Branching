import java.util.ArrayList;


public class No {
	private int arvore[][];//guarda arvore geradora
	private ArrayList<Aresta> arv;
	private int quantVertices;
	private No pai;
	private ArrayList<No> filhos;
	private int profundidade;
	private int custo;
	private ArrayList<Aresta> incluidas;
	private ArrayList<Aresta> excluidas;

	public No(ArrayList<Aresta> a, int tam){
		quantVertices = tam;
		arv = new ArrayList<Aresta>();
		arv.addAll(a);
		pai = null;
		incluidas = new ArrayList<Aresta>();
		excluidas = new ArrayList<Aresta>();
		custo =0;
	}

	public void CalculoCusto(){
		custo=0;
		for (int i = 0; i < arv.size(); i++) { 
			custo = custo + arv.get(i).getPeso();

		} 		

	}

	/**
	 * Verifica o grau de cada vertice na arvore
	 * @return 0, se todos os vertices da arvore possuirem grau 2! ( solucao viavel p/ caixeiro qd isso acontecer)
	 * otherwise, ira retornar o primeiro vertice com grau > 2.
	 */
	public int verificaGrau(){
		int i,j, count,tam;
		tam = arv.size();
		for(i=1; i<= tam; i++){
			count = 0;
			for(j=0;j<tam;j++){
				if( (arv.get(j).getAnt() == i) || (arv.get(j).getProx() == i) ){
					count++;
					if(count > 2)
						return i;
				}
			}
		}
		return 0;
	}

	public boolean verificaGrau1(){
		int i,j, count,tam;
		tam = arv.size();
		for(i=1; i<= tam; i++){
			count = 0;
			for(j=0;j<tam;j++){
				if( (arv.get(j).getAnt() == i) || (arv.get(j).getProx() == i) ){
					count++;
					//if(count > 2)
					//	return i;
				}
			}
			if(count<2)
				return true;
		}
		return false;
	}

	public boolean verificaGrau0(){
		int i,j, count,tam;
		tam = arv.size();
		for(i=1; i<= tam; i++){
			count = 0;
			for(j=0;j<tam;j++){
				if( (arv.get(j).getAnt() == i) || (arv.get(j).getProx() == i) ){
					count++;
				}
			}
			if(count==0){
				return true;
			}
		}
		return false;
	}

	public boolean verificaGrau2(){
		int i,j, count,tam;
		tam = arv.size();
		for(i=1; i<= tam; i++){
			count = 0;
			for(j=0;j<tam;j++){
				if( (arv.get(j).getAnt() == i) || (arv.get(j).getProx() == i) ){
					count++;
				}
			}
			if(count!=2){
				return false;
			}
		}
		return true;
	}


	public int compareTo(No arg0) {
		if(this.getCusto() > arg0.getCusto()){
			return 1;
		}
		else if(this.getCusto() < arg0.getCusto()){
			return -1;
		}
		else{
			return 0;
		}
	}

	public boolean verificaIncluidas(Aresta a){
		int i;
		for(i=0;i<incluidas.size();i++){
			if( (a.getAnt()==incluidas.get(i).getAnt()||a.getAnt()==incluidas.get(i).getProx()) && (a.getProx()==incluidas.get(i).getAnt()||a.getProx()==incluidas.get(i).getProx()) ){
				return true;
			}
		}
		return false;
	}

	public boolean verificaExcluidas(){
		int i, j,sum;
		for(j=1; j<=quantVertices;j++){
			sum = 0;
			for(i=0; i<excluidas.size();i++){
				if(excluidas.get(i).getAnt() == j || excluidas.get(i).getProx()==j){
					sum++;
				}
			}
			if(sum > (quantVertices/2))
				return true;
		}

		return false;
	}

	public boolean verificaInseridas(){
		int i, j,sum;
		for(j=1; j<=quantVertices;j++){
			sum = 0;
			for(i=0; i<incluidas.size();i++){
				if(incluidas.get(i).getAnt() == j || incluidas.get(i).getProx()==j){
					sum++;
				}
			}
			if(sum > 2)
				return true;
		}

		return false;
	}

	public boolean verificaArv(Aresta a){
		int i;
		for(i=0;i<arv.size();i++){
			if( (a.getAnt()==arv.get(i).getAnt()||a.getAnt()==arv.get(i).getProx()) && (a.getProx()==arv.get(i).getAnt()||a.getProx()==arv.get(i).getProx()) ){
				return true;
			}
		}
		return false;
	}

	public void addFilho(No no){
		no.setPai(this);
		filhos.add(no);
	}

	public int[][] getArvore() {
		return arvore;
	}
	public void setArvore(int arvore[][]) {
		this.arvore = arvore;
	}
	public No getPai() {
		return pai;
	}
	public void setPai(No pai) {
		this.pai = pai;
	}
	public ArrayList<No> getFilhos() {
		return filhos;
	}
	public void setFilhos(ArrayList<No> filhos) {
		this.filhos = filhos;
	}
	public int getProfundidade() {
		return profundidade;
	}
	public void setProfundidade(int profundidade) {
		this.profundidade = profundidade;
	}

	public int getCusto() {
		return custo;
	}

	public void setCusto(int custo) {
		this.custo = custo;
	}

	public int getQuantVertices() {
		return quantVertices;
	}
	public ArrayList<Aresta> getArv(){
		return arv;
	}
	public void setQuantVertices(int quantVertices) {
		this.quantVertices = quantVertices;
	}
	public ArrayList<Aresta> getIncluidas() {
		return incluidas;
	}
	public void setIncluidas(ArrayList<Aresta> incluidas) {
		this.incluidas = incluidas;
	}
	public ArrayList<Aresta> getExcluidas() {
		return excluidas;
	}
	public void setExcluidas(ArrayList<Aresta> excluidas) {
		this.excluidas = excluidas;
	}

	public int getTamArv() {
		return arv.size();
	}

	public void setTamArv() {
		arv.size();
	}
}
