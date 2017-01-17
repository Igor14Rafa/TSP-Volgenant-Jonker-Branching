

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal {

	public List<Edge> kruskalAlgorithm(List<Edge> a, int count){
		DisjointSet ds = new DisjointSet(count);
		List<Edge> spanningTree = new ArrayList<Edge>();
		Collections.sort(a);
		int i = 0;
		while(i != a.size() && spanningTree.size() != count - 1){
			Edge e = a.get(i);
			if(ds.find(e.getAnt()) != ds.find(e.getProx())){
				spanningTree.add(e);
				ds.union(e.getAnt(), e.getProx());
			}
			i++;
		}
		return spanningTree;

	}


	public List<Edge> kruskalAlgorithm(List<Edge> a, int count, int restante){
		DisjointSet ds = new DisjointSet(count);
		List<Edge> spanningTree = new ArrayList<Edge>();
		Collections.sort(a);
		int i = 0;

		while(i != a.size() && spanningTree.size() != restante - 3){
			Edge e = a.get(i);
			if(ds.find(e.getAnt()) != ds.find(e.getProx())){
				spanningTree.add(e);
				ds.union(e.getAnt(), e.getProx());
			}
			i++;
		}
		return spanningTree;

	}
	public ArrayList<Edge> kruskalAlgorithm(ArrayList<Edge> a, int count){
		DisjointSet ds = new DisjointSet(count);
		ArrayList<Edge> spanningTree = new ArrayList<Edge>();
		Collections.sort(a);
		int i = 0;
		while(i != a.size() && spanningTree.size() != count - 1){
			Edge e = a.get(i);
			if(ds.find(e.getAnt()) != ds.find(e.getProx())){
				spanningTree.add(e);
				ds.union(e.getAnt(), e.getProx());
			}
			i++;
		}
		return spanningTree;

	}

	public List<Edge> kruskalAlgorithm(List<Edge> a, int count, ArrayList<Edge> incluido){
		DisjointSet ds = new DisjointSet(count);
		ArrayList<Edge> spanningTree = new ArrayList<Edge>();
		Collections.sort(a);
		int j;

		for(j=0;j<incluido.size();j++){
			spanningTree.add(incluido.get(j));
		}
		int i = 0;

		while(i != a.size() && spanningTree.size() != count - 1){
			Edge e = a.get(i);
			if(ds.find(e.getAnt()) != ds.find(e.getProx())){
				spanningTree.add(e);
				ds.union(e.getAnt(), e.getProx());
			}
			i++;
		}
		return spanningTree;

	}
}
