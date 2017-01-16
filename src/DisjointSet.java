

public class DisjointSet {

	private Vertice[] v;
	public DisjointSet(int vertice) {
		v = new Vertice[vertice];
		for (int i = 0; i < vertice; i++) {
			v[i] = new Vertice();
			v[i].id = i;
		}
	}
	public int union(int a, int b){
		Vertice repA = v[find(a)];
		Vertice repB = v[find(b)];

		repB.parent = repA;
		return repA.id;
	}
	public int find(int a){
		Vertice n = v[a];
		int salto = 0;
		while(n.parent != null){
			n = n.parent;
			salto++;
		}
		if(salto > 1)repair(a, n.id);
		return n.id;
	}
	private void repair(int a, int id) {
		Vertice atual = v[a];
		while(atual.id != id){
			Vertice tmp = atual.parent;
			atual.parent = v[id];
			atual = tmp;
		}
	}
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < v.length; i++) {
			builder.append(find(i) + "");
		}
		return builder.toString();
	}
}
