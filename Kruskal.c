#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <math.h>
#define TRUE 1
#define FALSE 0

void kruskal();

typedef struct
{
	int weight;
	int visited;
} Matriz;
//struct que define a matriz de adjacencia


typedef struct
{
	int weight;
	int u;
	int v;
} Aresta;
//struct que armazena a lista de arestas

int tempoKruskal[10];
int n;//quantidade de vertices
Matriz grafo[3000][3000];


int gera(int iter)
{

	int i, j;

	//RANDONIZA UM GRAFO
	for(i = 0; i < n; i++)
		for(j = 0; j < n; j++)
		{
			grafo[i][j].weight = rand() % 20;
			if(grafo[i][j].weight < 1)grafo[i][j].weight = 9999; //se for menor que 1, considera-se que nao ha aresta (custo infinito)
			grafo[j][i].weight = grafo[i][j].weight;
			grafo[i][j].visited = FALSE;
			grafo[j][i].visited = FALSE;

		}
	//PRINTFS OCULTOS PARA AGILIZAR A EXECUÇÃO
	printf("\nMATRIZ DE ADJACENCIA %d\n", iter + 1);
	for(i = 0; i < n; i++)
	{
		printf("\n");
		for(j = 0; j < n; j++)
			if(grafo[i][j].weight == 9999)
				printf("%3d ", 0);
			else
				printf("%3d ", grafo[i][j].weight);
	}
	//getch();
	kruskal(iter);
}

/* Metodo find&union para encontrar ciclos num grafo*/
int find(int set[], int i)
{
	if (set[i] == -1) //encontra a raiz
		return i;
	else
		return find(set, set[i]);
}


void Union(int set[], int u, int v)
{
	int uset = find(set, u);
	int vset = find(set, v);
	set[uset] = vset;
}


//funcao de comparacao, usada na funcao qsort
int myComp(const void* a, const void* b)
{
	Aresta *a1 = (Aresta *)a;
	Aresta *b1 = (Aresta *)b;
	if (a1->weight > b1->weight)
		return 1;
	else
		return -1;
}


void kruskal(int iter)
{
	int inicio = clock();
	int i, j, k = 0, x, y, set[n], cost = 0, ne = 0, pesoTotal = 0;
	Matriz arvore[n][n];
	Aresta edge[90000];

	//armazena as arestas do grafo
	for(i = 0; i < n; i++)
	{
		for (j = 0; j < n; j++)
		{
			arvore[i][j].weight = 0;
			if(grafo[i][j].visited != TRUE)
			{
				edge[k].weight = grafo[i][j].weight;
				edge[k].u = i;
				edge[k++].v = j;
				grafo[j][i].visited = TRUE;
			}
		}
	}
	//ordena as arestas usando QUICK SORT
	qsort(edge, k, sizeof(Aresta), myComp);

	//seta todos os pais como -1
	memset(set, -1, n * sizeof(set[0]));

	i = 0;
	//while numero de arestas < numero de vertices (a arvore tera n-1 arestas)
	printf("\n\nArestas da arvore geradora minima %d:", iter);
	while (ne < n - 1)
	{
		x = find(set, edge[i].u);
		y = find(set, edge[i].v);
		if (x != y) //se nao formar ciclo
		{

			cost = cost + edge[i].weight;
			arvore[x][y].weight = edge[i].weight;
			arvore[y][x].weight = edge[i].weight;
			Union(set, x, y);
			printf("\nAresta %d: V%d --- V%d   PESO=%d", ne + 1, edge[i].u + 1, edge[i].v + 1, edge[i].weight);
			pesoTotal += edge[i].weight;
			ne++;//incrementa a quantidade de arestas
		}
		i++;
	}
	printf("\nCusto minimo = %d\n", pesoTotal);
	//printf("\n\nCusto minimo = %d",cost);
	tempoKruskal[iter] = clock() - inicio;
	getch();

}


void escreve()
{
	int total = 0;
	FILE *fp;
	fp = fopen("kruskalQuickSort.txt", "w");   		/* Arquivo ASCII, para escrita */
	if(!fp)
	{
		printf( "Erro na abertura do arquivo");
		exit(0);
	}
	fprintf(fp, "Kruskal: ");
	int iter;
	for(iter = 0; iter < 10; iter++)
	{
		total += tempoKruskal[iter];
		fprintf(fp, "%d ", tempoKruskal[iter]);
	}
	fprintf(fp, "\nSoma Kruskal: %d", total);



}

int main()
{
	srand((unsigned int)time(NULL));
	int choice;

	printf("\nQuantidade de nos: ");
	scanf("%d", &n);
	printf("\n1. Para entrar com uma matriz\n2. Para gerar 10 grafos aleatorios\n");
	scanf("%d", &choice);
	if(choice == 1)
	{
		int i, j;
		printf("\nEntrar com a matriz de adjacencia:\n");
		for(i = 0; i < n; i++)
		{
			for (j = 0; j < n; j++)
			{
				scanf("%d", &grafo[i][j].weight);
				if(grafo[i][j].weight == 0) grafo[i][j].weight = 9999;
				grafo[i][j].visited = FALSE;
			}
		}
	}
	int iter;
	for(iter = 0; iter < 10; iter++)
	{
		if(choice == 2)gera(iter);
		else
		{
			kruskal(0);
			break;
		}
	}
	escreve();
	return 0;
}

