

public class DisjointSet {

	private Vertex[] v;
	public DisjointSet(int vertice) {
		v = new Vertex[vertice];
		for (int i = 0; i < vertice; i++) {
			v[i] = new Vertex();
			v[i].id = i;
		}
	}
	public int union(int a, int b){
		Vertex repA = v[find(a)];
		Vertex repB = v[find(b)];

		repB.parent = repA;
		return repA.id;
	}
	public int find(int a){
		Vertex n = v[a];
		int salto = 0;
		while(n.parent != null){
			n = n.parent;
			salto++;
		}
		if(salto > 1)repair(a, n.id);
		return n.id;
	}
	private void repair(int a, int id) {
		Vertex atual = v[a];
		while(atual.id != id){
			Vertex tmp = atual.parent;
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
