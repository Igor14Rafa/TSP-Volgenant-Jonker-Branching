

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {

	public List<Aresta> kruskalAlgorithm(List<Aresta> a, int count){
		DisjointSet ds = new DisjointSet(count);
		List<Aresta> spanningTree = new ArrayList<Aresta>();
		Collections.sort(a);
		int i = 0;
		while(i != a.size() && spanningTree.size() != count - 1){
			Aresta e = a.get(i);
			if(ds.find(e.getAnt()) != ds.find(e.getProx())){
				spanningTree.add(e);
				ds.union(e.getAnt(), e.getProx());
			}
			i++;
		}
		return spanningTree;

	}


	public List<Aresta> kruskalAlgorithm(List<Aresta> a, int count, int restante){
		DisjointSet ds = new DisjointSet(count);
		List<Aresta> spanningTree = new ArrayList<Aresta>();
		Collections.sort(a);
		int i = 0;

		while(i != a.size() && spanningTree.size() != restante - 3){
			Aresta e = a.get(i);
			if(ds.find(e.getAnt()) != ds.find(e.getProx())){
				spanningTree.add(e);
				ds.union(e.getAnt(), e.getProx());
			}
			i++;
		}
		return spanningTree;

	}
	public ArrayList<Aresta> kruskalAlgorithm(ArrayList<Aresta> a, int count){
		DisjointSet ds = new DisjointSet(count);
		ArrayList<Aresta> spanningTree = new ArrayList<Aresta>();
		Collections.sort(a);
		int i = 0;
		while(i != a.size() && spanningTree.size() != count - 1){
			Aresta e = a.get(i);
			if(ds.find(e.getAnt()) != ds.find(e.getProx())){
				spanningTree.add(e);
				ds.union(e.getAnt(), e.getProx());
			}
			i++;
		}
		return spanningTree;

	}

	public List<Aresta> kruskalAlgorithm(List<Aresta> a, int count, ArrayList<Aresta> incluido){
		DisjointSet ds = new DisjointSet(count);
		ArrayList<Aresta> spanningTree = new ArrayList<Aresta>();
		Collections.sort(a);
		int j;

		for(j=0;j<incluido.size();j++){
			spanningTree.add(incluido.get(j));
		}
		int i = 0;

		while(i != a.size() && spanningTree.size() != count - 1){
			Aresta e = a.get(i);
			if(ds.find(e.getAnt()) != ds.find(e.getProx())){
				spanningTree.add(e);
				ds.union(e.getAnt(), e.getProx());
			}
			i++;
		}
		return spanningTree;

	}
}
