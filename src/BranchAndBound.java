import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BranchAndBound {

	private Tree busca;
	private ArrayList<Edge> grafo;
	private Node titular;
	Kruskal k = new Kruskal();
	private int quantVertices;
	private int quantPodas;
	private int quantNoAnalisados;

	public BranchAndBound(List<Edge> a, int numVertices) {
		this.setQuantVertices(numVertices);
		grafo = new ArrayList<Edge>();
		grafo.addAll(a);
		this.busca = new Tree(OneTree(a, numVertices), numVertices);// formac�o da raiz

		System.out.println("Custo da primeira arvore gerada = " + busca.getRaiz().getCusto());
		this.titular = null;
		quantPodas=0;
		quantNoAnalisados=0;
	}

	public Node branch() {
		Node aux, inter;
		ArrayList<Node> fronteira = new ArrayList<Node>();
		int flagTitular = 0, verticeAux, i;
		fronteira.add(busca.getRaiz());
		while (fronteira.size() > 0) {
			aux = fronteira.get(0);
			fronteira.remove(0);
			if (flagTitular == 0) {// n�o h� titular

				if (aux.verificaGrau2()) {
					titular = aux;
					flagTitular = 1;
				} else {
					verticeAux = aux.verificaGrau();
					ArrayList<Edge> livres = new ArrayList<Edge>();
					Edge arestaAux = null;
					Collections.sort(aux.getArv());
					for (i = 0; i < aux.getTamArv(); i++) {
						if (aux.getArv().get(i).getAnt() == verticeAux || 
							aux.getArv().get(i).getProx() == verticeAux) {
							arestaAux = new Edge(aux.getArv().get(i).getProx(), 
												   aux.getArv().get(i).getAnt(), 
												   aux.getArv().get(i).getPeso());
							if (!aux.verificaIncluidas(arestaAux)) {
								livres.add(arestaAux);
								if (livres.size() == 2)
									break;
							}
						}
					}

					switch (livres.size()) {
					case 1:
						inter = geraNo1(aux, livres);
						quantNoAnalisados++;
						if(inter != null)
							fronteira.add(inter);
						break;
					case 2:
						inter = geraNo1(aux, livres);
						quantNoAnalisados++;
						if(inter != null)
							fronteira.add(inter);
						inter = geraNo2(aux, livres);
						quantNoAnalisados++;
						if(inter != null)
							fronteira.add(inter);

						inter = geraNo3(aux, livres,verticeAux);
						quantNoAnalisados++;
						if(inter != null)
							fronteira.add(inter);
						break;
					default:
					}
				}

			} else {

				if (aux.getCusto() >= titular.getCusto()) {// poda: nao descer� na �rvore quando um n� apresentar custo >= ao titular
					quantPodas++;
					continue;
				} else {
					verticeAux = aux.verificaGrau();
					if (aux.verificaGrau2()) {
						System.out
						.println("Nova Titular, peso da titular anterior: "
								+ titular.getCusto());
						titular = aux;
						quantPodas++;
					} else {
						ArrayList<Edge> livres = new ArrayList<Edge>();
						Edge arestaAux = null;
						Collections.sort(aux.getArv());
						for (i = 0; i < aux.getTamArv(); i++) {
							if (aux.getArv().get(i).getAnt() == verticeAux
									|| aux.getArv().get(i).getProx() == verticeAux) {
								arestaAux = new Edge(aux.getArv().get(i)
										.getProx(), aux.getArv().get(i)
										.getAnt(), aux.getArv().get(i)
										.getPeso());
								if (!aux.verificaIncluidas(arestaAux)) {
									livres.add(arestaAux);
									if (livres.size() == 2)
										break;
								}
							}
						}

						switch (livres.size()) {
						case 1:
							inter = geraNo1(aux, livres);
							quantNoAnalisados++;
							if(inter != null)
								fronteira.add(inter);
							break;
						case 2:
							inter = geraNo1(aux, livres);
							quantNoAnalisados++;
							if(inter != null)
								fronteira.add(inter);
							inter = geraNo2(aux, livres);
							quantNoAnalisados++;
							if(inter != null)
								fronteira.add(inter);
							inter = geraNo3(aux, livres,verticeAux);
							quantNoAnalisados++;
							if(inter != null)
								fronteira.add(inter);
							break;
						default:
						}
						
					}
				}
			}

		}
		if (flagTitular == 0) {
			System.out.println("Nao existe solucao viavel pra este problema");
		} else {
			System.out.println("Solucao titular");
			System.out.println("Origem : Destino = Peso");
			for (int t = 0; t < titular.getTamArv(); t++) {
				System.out.println(titular.getArv().get(t).getAnt() + "\t:\t"
						+ titular.getArv().get(t).getProx() + "\t=\t"
						+ titular.getArv().get(t).getPeso());
				System.out.println("origem :"
						+ titular.getArv().get(t).getAnt());
				System.out.println("destino = "
						+ titular.getArv().get(t).getProx());
				System.out
				.println("-------------------------------------------------------------------------------------------------");
			}
			System.out.println("Custo da titular: "+ titular.getCusto());
			System.out.println("Quantidade de podas: "+ quantPodas);
			System.out.println("Quantidade de nos gerados: "+ quantNoAnalisados);
		}
		return titular;
	}

	private Node geraNo3(Node a, ArrayList<Edge> livres, int vertice) {
		Node result;
		Node aux = new Node(a.getArv(), a.getTamArv());

		aux.getIncluidas().addAll(a.getIncluidas());
		aux.getExcluidas().addAll(a.getExcluidas());
		Edge a1 = new Edge(livres.get(0).getProx(), livres.get(0).getAnt(),
				livres.get(0).getPeso());
		Edge a2 = new Edge(livres.get(1).getAnt(), livres.get(1).getProx(),
				livres.get(1).getPeso());


		aux.getIncluidas().add(a2);
		aux.getIncluidas().add(a1);
		result = oneTreeNo3(aux.getIncluidas(), aux.getExcluidas(), aux, vertice);
		if (result == null) {
			return null;
		} else {
			result.setExcluidas(aux.getExcluidas());
			result.setIncluidas(aux.getIncluidas());
			result.setPai(a);
			return result;
		}

	}

	private Node oneTreeNo3(ArrayList<Edge> inc, ArrayList<Edge> e,
			Node node, int vertice) {
		Node result;
		int i, j;
		ArrayList<Edge> grafoAux = new ArrayList<Edge>();
		grafoAux.addAll(grafo);
		// removendo as arestas do grafo original
		for (i = 0; i < e.size(); i++) {
			for (int q = 0; q < 2; q++) {
				for (j = 0; j < grafoAux.size(); j++) {
					if ((e.get(i).getAnt() == grafoAux.get(j).getAnt() || e
							.get(i).getAnt() == grafoAux.get(j).getProx())
							&& (e.get(i).getProx() == grafoAux.get(j).getAnt() || e
							.get(i).getProx() == grafoAux.get(j)
							.getProx())) {
						grafoAux.remove(j);
						break;
					}
				}
			}
		}

		for (i = 0; i < inc.size(); i++) {
			for (int q = 0; q < 2; q++) {
				for (j = 0; j < grafoAux.size(); j++) {
					if ((inc.get(i).getAnt() == grafoAux.get(j).getAnt() || inc
							.get(i).getAnt() == grafoAux.get(j).getProx())
							&& (inc.get(i).getProx() == grafoAux.get(j)
							.getAnt() || inc.get(i).getProx() == grafoAux
							.get(j).getProx())) {
						grafoAux.remove(j);
						break;
					}
				}
			}
		}

		for(i=0; i< grafoAux.size();i++){
			if(grafoAux.get(i).getAnt() == vertice || grafoAux.get(i).getProx() ==vertice){
				grafoAux.remove(i);
				i--;
			}
		}
		result = oneTreeParticaoNo3(grafoAux, quantVertices, inc, node);

		return result;
	}

	public Node oneTreeParticaoNo3(List<Edge> a, int num_de_vertices,ArrayList<Edge> ins, Node node) {
		int x,restante=0;
		List<Edge> guard = new ArrayList<Edge>();// guarda as arestas
		// ligadas a 1
		List<Edge> onetree = new ArrayList<Edge>();

		for (int i = 1; i < a.size(); i++) {

			if ((a.get(i).getAnt() == 1) || (a.get(i).getProx() == 1)) {
				guard.add(a.get(i)); // Arestas ligadas ao vértice 1
			}

			else
				onetree.add(a.get(i));
		}

		for(x=0; x<ins.size();x++){
			if(ins.get(x).getAnt() != 1 && ins.get(x).getProx() != 1 ){
				restante++;
			}
		}
		List<Edge> result = k.kruskalAlgorithm(onetree, num_de_vertices,num_de_vertices-restante);// Retorna
		// a
		// árvore
		// geradora
		// da
		// One-tree
		List<Edge> result1 = k.kruskalAlgorithm(guard, num_de_vertices);

		for (int j = 0; j < ins.size(); j++) {
			result.add(ins.get(j));
		}

		for (int h = 0; h < result1.size(); h++) {
			if (result.size() < num_de_vertices - 1) {
				if (!verificaArestas(result, result1.get(h))) {
					result.add(result1.get(h));
				} else {
					continue;

				}
				// Adiciono as menores arestas ligadas ao vértice 1 na árvore
			}
		}

		Node n = new Node((ArrayList<Edge>) result, num_de_vertices);
		if (n.getTamArv() != quantVertices - 1 || n.verificaGrau0() == true) {
			return null;
		}
		n.CalculoCusto();

		return n;
	}




	private Node geraNo2(Node a, ArrayList<Edge> livres) {
		Node result;
		Node aux = new Node(a.getArv(), a.getTamArv());

		aux.getIncluidas().addAll(a.getIncluidas());
		aux.getExcluidas().addAll(a.getExcluidas());
		Edge a1 = new Edge(livres.get(0).getProx(), livres.get(0).getAnt(),
				livres.get(0).getPeso());
		Edge a2 = new Edge(livres.get(1).getAnt(), livres.get(1).getProx(),
				livres.get(1).getPeso());


		aux.getExcluidas().add(a2);
		aux.getIncluidas().add(a1);

		result = oneTreeNo2(aux.getIncluidas(), aux.getExcluidas(), aux);
		if (result == null) {
			return null;
		} else {
			result.setExcluidas(aux.getExcluidas());
			result.setIncluidas(aux.getIncluidas());
			result.setPai(a);
			return result;
		}

	}

	private Node oneTreeNo2(ArrayList<Edge> inc, ArrayList<Edge> exc,
			Node node) {
		Node result;
		int i, j;
		ArrayList<Edge> grafoAux = new ArrayList<Edge>();
		grafoAux.addAll(grafo);
		// removendo as arestas do grafo orignal
		for (i = 0; i < exc.size(); i++) {
			for (int q = 0; q < 2; q++) {
				for (j = 0; j < grafoAux.size(); j++) {
					if ((exc.get(i).getAnt() == grafoAux.get(j).getAnt() || 
							exc.get(i).getAnt() == grafoAux.get(j).getProx()) && 
							(exc.get(i).getProx() == grafoAux.get(j).getAnt() || 
							exc.get(i).getProx() == grafoAux.get(j).getProx())) {
						grafoAux.remove(j);
						break;
					}
				}
			}
		}

		for (i = 0; i < inc.size(); i++) {
			for (int q = 0; q < 2; q++) {
				for (j = 0; j < grafoAux.size(); j++) {
					if ((inc.get(i).getAnt() == grafoAux.get(j).getAnt() || 
							inc.get(i).getAnt() == grafoAux.get(j).getProx()) && 
							(inc.get(i).getProx() == grafoAux.get(j).getAnt() || 
							inc.get(i).getProx() == grafoAux.get(j).getProx())) {
						grafoAux.remove(j);
						break;
					}
				}
			}
		}

		result = oneTreeParticaoNo2(grafoAux, quantVertices, inc, node);

		return result;
	}

	public Node oneTreeParticaoNo2(List<Edge> a, int num_de_vertices,
			ArrayList<Edge> ins, Node node) {
		int x,restante=0;
		List<Edge> guard = new ArrayList<Edge>();// guarda as arestas ligadas a 1
		List<Edge> onetree = new ArrayList<Edge>();

		for (int i = 1; i < a.size(); i++) {

			if ((a.get(i).getAnt() == 1) || (a.get(i).getProx() == 1)) {
				guard.add(a.get(i)); // Arestas ligadas ao v�rtice 1
			}

			else
				onetree.add(a.get(i));
		}

		for(x=0; x<ins.size();x++){
			if(ins.get(x).getAnt() != 1 && ins.get(x).getProx() != 1 ){
				restante++;
			}
		}
		// Retorna a �rvore geradora da One-tree e armazena e result
		List<Edge> result = k.kruskalAlgorithm(onetree, num_de_vertices,num_de_vertices-restante);
		List<Edge> result1 = k.kruskalAlgorithm(guard, num_de_vertices);

		for (int j = 0; j < ins.size(); j++) {
			result.add(ins.get(j));
		}

		for (int h = 0; h < result1.size(); h++) {
			if (result.size() < num_de_vertices - 1) {
				if (!verificaArestas(result, result1.get(h))) {
					result.add(result1.get(h));
				} else {
					continue;

				}
				// Adiciono as menores arestas ligadas ao vértice 1 na árvore
			}
		}

		Node n = new Node((ArrayList<Edge>) result, num_de_vertices);
		if (n.getTamArv() != quantVertices - 1 || n.verificaGrau0() == true) {
			return null;
		}
		n.CalculoCusto();

		// Checar

		return n;
	}

	private Node geraNo1(Node a, ArrayList<Edge> livres) {

		Node result;
		Node aux = new Node(a.getArv(), a.getTamArv());

		aux.getIncluidas().addAll(a.getIncluidas());
		aux.getExcluidas().addAll(a.getExcluidas());
		Edge a1 = new Edge(livres.get(0).getProx(), livres.get(0).getAnt(),
				livres.get(0).getPeso());
		aux.getExcluidas().add(a1);
		result = oneTreeNo1(aux.getIncluidas(), aux.getExcluidas(), aux);
		if (result.verificaExcluidas()) {
			return null;
		} else {
			result.setExcluidas(aux.getExcluidas());
			result.setIncluidas(aux.getIncluidas());
			result.setPai(a);
			return result;
		}

	}

	private Node oneTreeNo1(ArrayList<Edge> inc, ArrayList<Edge> e,
			Node node) {
		Node result;
		int i, j;
		ArrayList<Edge> grafoAux = new ArrayList<Edge>();
		grafoAux.addAll(grafo);
		// removendo as arestas do grafo orignal
		for (i = 0; i < e.size(); i++) {
			for (int q = 0; q < 2; q++) {
				for (j = 0; j < grafoAux.size(); j++) {
					if ((e.get(i).getAnt() == grafoAux.get(j).getAnt() || e
							.get(i).getAnt() == grafoAux.get(j).getProx())
							&& (e.get(i).getProx() == grafoAux.get(j).getAnt() || e
							.get(i).getProx() == grafoAux.get(j)
							.getProx())) {
						grafoAux.remove(j);
						break;
					}
				}
			}
		}

		result = OneTreeParticoes(grafoAux, quantVertices, inc, node);

		return result;
	}


	public BranchAndBound() {

	}

	public Node OneTreeParticoes(List<Edge> a, int num_de_vertices,
			ArrayList<Edge> ins, Node node) {
		List<Edge> guard = new ArrayList<Edge>();// guarda as arestas ligadas a 1
		List<Edge> onetree = new ArrayList<Edge>();

		for (int i = 1; i < a.size(); i++) {

			if ((a.get(i).getAnt() == 1) || (a.get(i).getProx() == 1)) {
				guard.add(a.get(i)); // Arestas ligadas ao v�rtice 1
			}

			else
				onetree.add(a.get(i));
		}

		List<Edge> result = k.kruskalAlgorithm(onetree, num_de_vertices);// Retorna a �rvore geradora da One-tree
		List<Edge> result1 = k.kruskalAlgorithm(guard, num_de_vertices);

		for (int h = 0; h < result1.size(); h++) {
			if (result.size() < num_de_vertices - 1) {
				if (!verificaArestas(result, result1.get(h))) {
					result.add(result1.get(h));
				} else {
					continue;

				}
				// Adiciono as menores arestas ligadas ao v�rtice 1 na �rvore
			}
		}

		Node n = new Node((ArrayList<Edge>) result, num_de_vertices);
		n.CalculoCusto();

		return n;
	}

	private boolean verificaArestas(List<Edge> a, Edge x) {
		int i;
		for (i = 0; i < a.size(); i++) {
			if ((x.getAnt() == a.get(i).getAnt() || x.getAnt() == a.get(i)
					.getProx())
					&& (x.getProx() == a.get(i).getAnt() || x.getProx() == a
					.get(i).getProx())) {
				return true;
			}
		}
		return false;
	}

	// Primeira itera��o
	public Node OneTree(List<Edge> a, int num_de_vertices) {

		List<Edge> guard = new ArrayList<Edge>();// guarda as arestas ligadas a 1
		List<Edge> onetree = new ArrayList<Edge>();

		for (int i = 1; i < a.size(); i++) {

			if ((a.get(i).getAnt() == 1) || (a.get(i).getProx() == 1)) {
				guard.add(a.get(i)); // Arestas ligadas ao v�rtice 1
			}

			else
				onetree.add(a.get(i));
		}

		List<Edge> result = k.kruskalAlgorithm(onetree, num_de_vertices);// Retorna a �rvore geradora da One-tree
		List<Edge> result1 = k.kruskalAlgorithm(guard, num_de_vertices);

		// Enquanto o n�mero de arestas for igual ao n�mero de v�rtices(O que
		// caracteriza um ciclo)
		for (int h = 0; h < num_de_vertices; h++) {
			if (result.size() < num_de_vertices - 1) {
				result.add(result1.get(h));
				// Adiciono as menores arestas ligadas ao v�rtice 1 na �rvore
			}
		}

		Node n = new Node((ArrayList<Edge>) result, num_de_vertices);
		n.CalculoCusto();

		// Checar
		System.out.println("Origem : Destino = Peso");
		for (int t = 0; t < result.size(); t++) {
			System.out.println(result.get(t).getAnt() + "\t:\t"
					+ result.get(t).getProx() + "\t=\t"
					+ result.get(t).getPeso());
			System.out.println("origem :" + result.get(t).getAnt());
			System.out.println("destino = " + result.get(t).getProx());
			System.out.println("--------------------------------------------------------------------");
		}

		return n;
	}

	//Getters and Setters 
	public Tree getBusca() {
		return busca;
	}

	public void setBusca(Tree busca) {
		this.busca = busca;
	}

	public Node getTitular() {
		return titular;
	}

	public void setTitular(Node titular) {
		this.titular = titular;
	}

	public int getQuantVertices() {
		return quantVertices;
	}

	public void setQuantVertices(int quantVertices) {
		this.quantVertices = quantVertices;
	}
	public int quantpodas() {
		return quantPodas;
	}
	public int quatnos(){
		return quantNoAnalisados;
	}
	public int custo(){
		return titular.getCusto();
	}
}
