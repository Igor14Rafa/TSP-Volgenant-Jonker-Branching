import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BranchAndBound {

	private Arvore busca;
	private ArrayList<Aresta> grafo;
	private No titular;
	Kruskal k = new Kruskal();
	private int quantVertices;
	private int quantPodas;
	private int quantNoAnalisados;

	public BranchAndBound(List<Aresta> a, int numVertices) {
		this.setQuantVertices(numVertices);
		grafo = new ArrayList<Aresta>();
		grafo.addAll(a);
		this.busca = new Arvore(OneTree(a, numVertices), numVertices);// formac�o da raiz

		System.out.println("Custo da primeira arvore gerada = " + busca.getRaiz().getCusto());
		this.titular = null;
		quantPodas=0;
		quantNoAnalisados=0;
	}

	public No branch() {
		No aux, inter;
		ArrayList<No> fronteira = new ArrayList<No>();
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
					ArrayList<Aresta> livres = new ArrayList<Aresta>();
					Aresta arestaAux = null;
					Collections.sort(aux.getArv());
					for (i = 0; i < aux.getTamArv(); i++) {
						if (aux.getArv().get(i).getAnt() == verticeAux || 
							aux.getArv().get(i).getProx() == verticeAux) {
							arestaAux = new Aresta(aux.getArv().get(i).getProx(), 
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
						inter = particao1(aux, livres);
						quantNoAnalisados++;
						if(inter != null)
							fronteira.add(inter);
						break;
					case 2:
						inter = particao1(aux, livres);
						quantNoAnalisados++;
						if(inter != null)
							fronteira.add(inter);
						inter = particao2(aux, livres);
						quantNoAnalisados++;
						if(inter != null)
							fronteira.add(inter);

						inter = particao3(aux, livres,verticeAux);
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
						ArrayList<Aresta> livres = new ArrayList<Aresta>();
						Aresta arestaAux = null;
						Collections.sort(aux.getArv());
						for (i = 0; i < aux.getTamArv(); i++) {
							if (aux.getArv().get(i).getAnt() == verticeAux
									|| aux.getArv().get(i).getProx() == verticeAux) {
								arestaAux = new Aresta(aux.getArv().get(i)
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
							inter = particao1(aux, livres);
							quantNoAnalisados++;
							if(inter != null)
								fronteira.add(inter);
							break;
						case 2:
							inter = particao1(aux, livres);
							quantNoAnalisados++;
							if(inter != null)
								fronteira.add(inter);
							inter = particao2(aux, livres);
							quantNoAnalisados++;
							if(inter != null)
								fronteira.add(inter);
							inter = particao3(aux, livres,verticeAux);
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

	private No particao3(No a, ArrayList<Aresta> livres, int vertice) {
		No result;
		No aux = new No(a.getArv(), a.getTamArv());

		aux.getIncluidas().addAll(a.getIncluidas());
		aux.getExcluidas().addAll(a.getExcluidas());
		Aresta a1 = new Aresta(livres.get(0).getProx(), livres.get(0).getAnt(),
				livres.get(0).getPeso());
		Aresta a2 = new Aresta(livres.get(1).getAnt(), livres.get(1).getProx(),
				livres.get(1).getPeso());


		aux.getIncluidas().add(a2);
		aux.getIncluidas().add(a1);
		result = oneTreeParticao3(aux.getIncluidas(), aux.getExcluidas(), aux, vertice);
		if (result == null) {
			return null;
		} else {
			result.setExcluidas(aux.getExcluidas());
			result.setIncluidas(aux.getIncluidas());
			result.setPai(a);
			return result;
		}

	}

	private No oneTreeParticao3(ArrayList<Aresta> inc, ArrayList<Aresta> e,
			No no, int vertice) {
		No result;
		int i, j;
		ArrayList<Aresta> grafoAux = new ArrayList<Aresta>();
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
		result = OneTreeParticoes3(grafoAux, quantVertices, inc, no);

		return result;
	}

	public No OneTreeParticoes3(List<Aresta> a, int num_de_vertices,ArrayList<Aresta> ins, No no) {
		int x,restante=0;
		List<Aresta> guard = new ArrayList<Aresta>();// guarda as arestas
		// ligadas a 1
		List<Aresta> onetree = new ArrayList<Aresta>();

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
		List<Aresta> result = k.kruskalAlgorithm(onetree, num_de_vertices,num_de_vertices-restante);// Retorna
		// a
		// árvore
		// geradora
		// da
		// One-tree
		List<Aresta> result1 = k.kruskalAlgorithm(guard, num_de_vertices);

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

		No n = new No((ArrayList<Aresta>) result, num_de_vertices);
		if (n.getTamArv() != quantVertices - 1 || n.verificaGrau0() == true) {
			return null;
		}
		n.CalculoCusto();

		return n;
	}




	private No particao2(No a, ArrayList<Aresta> livres) {
		No result;
		No aux = new No(a.getArv(), a.getTamArv());

		aux.getIncluidas().addAll(a.getIncluidas());
		aux.getExcluidas().addAll(a.getExcluidas());
		Aresta a1 = new Aresta(livres.get(0).getProx(), livres.get(0).getAnt(),
				livres.get(0).getPeso());
		Aresta a2 = new Aresta(livres.get(1).getAnt(), livres.get(1).getProx(),
				livres.get(1).getPeso());


		aux.getExcluidas().add(a2);
		aux.getIncluidas().add(a1);

		result = oneTreeParticao2(aux.getIncluidas(), aux.getExcluidas(), aux);
		if (result == null) {
			return null;
		} else {
			result.setExcluidas(aux.getExcluidas());
			result.setIncluidas(aux.getIncluidas());
			result.setPai(a);
			return result;
		}

	}

	private No oneTreeParticao2(ArrayList<Aresta> inc, ArrayList<Aresta> exc,
			No no) {
		No result;
		int i, j;
		ArrayList<Aresta> grafoAux = new ArrayList<Aresta>();
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

		result = OneTreeParticoes2(grafoAux, quantVertices, inc, no);

		return result;
	}

	private No particao1(No a, ArrayList<Aresta> livres) {

		No result;
		No aux = new No(a.getArv(), a.getTamArv());

		aux.getIncluidas().addAll(a.getIncluidas());
		aux.getExcluidas().addAll(a.getExcluidas());
		Aresta a1 = new Aresta(livres.get(0).getProx(), livres.get(0).getAnt(),
				livres.get(0).getPeso());
		aux.getExcluidas().add(a1);
		result = oneTreeParticao1(aux.getIncluidas(), aux.getExcluidas(), aux);
		if (result.verificaExcluidas()) {
			return null;
		} else {
			result.setExcluidas(aux.getExcluidas());
			result.setIncluidas(aux.getIncluidas());
			result.setPai(a);
			return result;
		}

	}

	private No oneTreeParticao1(ArrayList<Aresta> inc, ArrayList<Aresta> e,
			No no) {
		No result;
		int i, j;
		ArrayList<Aresta> grafoAux = new ArrayList<Aresta>();
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

		result = OneTreeParticoes(grafoAux, quantVertices, inc, no);

		return result;
	}

	public No OneTreeParticoes2(List<Aresta> a, int num_de_vertices,
			ArrayList<Aresta> ins, No no) {
		int x,restante=0;
		List<Aresta> guard = new ArrayList<Aresta>();// guarda as arestas ligadas a 1
		List<Aresta> onetree = new ArrayList<Aresta>();

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
		List<Aresta> result = k.kruskalAlgorithm(onetree, num_de_vertices,num_de_vertices-restante);
		List<Aresta> result1 = k.kruskalAlgorithm(guard, num_de_vertices);

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

		No n = new No((ArrayList<Aresta>) result, num_de_vertices);
		if (n.getTamArv() != quantVertices - 1 || n.verificaGrau0() == true) {
			return null;
		}
		n.CalculoCusto();

		// Checar

		return n;
	}

	public BranchAndBound() {

	}

	public No OneTreeParticoes(List<Aresta> a, int num_de_vertices,
			ArrayList<Aresta> ins, No no) {
		List<Aresta> guard = new ArrayList<Aresta>();// guarda as arestas ligadas a 1
		List<Aresta> onetree = new ArrayList<Aresta>();

		for (int i = 1; i < a.size(); i++) {

			if ((a.get(i).getAnt() == 1) || (a.get(i).getProx() == 1)) {
				guard.add(a.get(i)); // Arestas ligadas ao v�rtice 1
			}

			else
				onetree.add(a.get(i));
		}

		List<Aresta> result = k.kruskalAlgorithm(onetree, num_de_vertices);// Retorna a �rvore geradora da One-tree
		List<Aresta> result1 = k.kruskalAlgorithm(guard, num_de_vertices);

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

		No n = new No((ArrayList<Aresta>) result, num_de_vertices);
		n.CalculoCusto();

		return n;
	}

	private boolean verificaArestas(List<Aresta> a, Aresta x) {
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
	public No OneTree(List<Aresta> a, int num_de_vertices) {

		List<Aresta> guard = new ArrayList<Aresta>();// guarda as arestas ligadas a 1
		List<Aresta> onetree = new ArrayList<Aresta>();

		for (int i = 1; i < a.size(); i++) {

			if ((a.get(i).getAnt() == 1) || (a.get(i).getProx() == 1)) {
				guard.add(a.get(i)); // Arestas ligadas ao v�rtice 1
			}

			else
				onetree.add(a.get(i));
		}

		List<Aresta> result = k.kruskalAlgorithm(onetree, num_de_vertices);// Retorna a �rvore geradora da One-tree
		List<Aresta> result1 = k.kruskalAlgorithm(guard, num_de_vertices);

		// Enquanto o n�mero de arestas for igual ao n�mero de v�rtices(O que
		// caracteriza um ciclo)
		for (int h = 0; h < num_de_vertices; h++) {
			if (result.size() < num_de_vertices - 1) {
				result.add(result1.get(h));
				// Adiciono as menores arestas ligadas ao v�rtice 1 na �rvore
			}
		}

		No n = new No((ArrayList<Aresta>) result, num_de_vertices);
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
	public Arvore getBusca() {
		return busca;
	}

	public void setBusca(Arvore busca) {
		this.busca = busca;
	}

	public No getTitular() {
		return titular;
	}

	public void setTitular(No titular) {
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
