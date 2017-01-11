#include <stdio.h>
#include <conio.h>
#include <time.h>
#include <math.h>
#include <stdlib.h>
#include <stdbool.h>

int i, j, n;
int visited[3000] = {0}, min, mingrafo = 0, grafo[3000][3000];
unsigned int soma = 0;
int fim, tempoPrim[10];

int gera();
void prim();
void escreve();

void main()
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
			for(j = 0; j < n; j++)
			{
				scanf("%d", &grafo[i][j]);
				if(grafo[i][j] == 0)
					grafo[i][j] = 999;
			}
	}

	int iter;
	for(iter = 0; iter < 10; iter++)
	{
		if(choice == 2)gera(iter);
		else
		{
			prim(0);
			break;
		}
	}
	escreve();
}


int gera(int k)
{

	//GERA O GRAFO ALEATORIO
	for(i = 0; i < n; i++)
		for(j = 0; j < n; j++)
		{
			grafo[i][j] = rand() % 20;
			if(grafo[i][j] < 1)grafo[i][j] = 9999; //se for menor que 400, considera-se que nao ha aresta (custo infinito)
			grafo[j][i] = grafo[i][j];
		}

	//PRINTS OCULTOS PARA AGILIZAR A EXECUCAO
	printf("\nMATRIZ DE ADJACENCIA %d\n", k + 1);
	for(i = 0; i < n; i++)
	{
		printf("\n");
		for(j = 0; j < n; j++)
			if(grafo[i][j] == 9999) printf("0 ");
			else printf("%d ", grafo[i][j]);
	}
	printf("%\n");

	prim(k);
}
//encontra o vertice com menor valor de chave dentre os vertices ainda nao incluidos
int chaveMinima(int chave[], bool arvore[])
{
	int min = 9999, min_index;
	int v;
	for (v = 0; v < n; v++)
		if (arvore[v] == false && chave[v] < min)
			min = chave[v], min_index = v;

	return min_index;
}

void prim(int iter)
{
	int inicio = clock();
	int visited[n]; //armazena a arvore construida(vertices visitados)
	int chave[n]; //valores chave para pegar a aresta de menor custo
	bool arvore[n]; //vertices ainda nao incluidos na arvore

	int i;
	for(i = 0; i < n; i++)
	{
		chave[i] = 9999; //todas as chaves sao infinitas inicialmente
		arvore[i] = false; //nenhum vertice visitado
	}
	chave[0] = 0; //primeiro vertice a ser pego: [vertice 1]
	visited[0] = -1; //primeiro vertice eh a raiz

	int count; //a arvore vai ter n vertices, e n-1 arestas
	for(count = 0; count < (n); count++)
	{
		//pega um vertice ainda nao visitado cuja aresta eh minima
		int u = chaveMinima(chave, arvore);

		//coloca o vertice como visitado
		arvore[u] = true;

		int v;
		for(v = 0; v < n; v++)
		{
			//se for adjacente e ainda nao estiver incluido, e for menor que a chave, atualize a chave
			if (grafo[u][v] && arvore[v] == false && grafo[u][v] <  chave[v])
				visited[v]  = u, chave[v] = grafo[u][v];
		}
	}
	int custoMinimo = 0;
	printf("\nPeso das arestas da arvore geradora minima %d:\n", iter + 1);
	for (i = 1; i < n; i++)
	{
		custoMinimo += grafo[i][visited[i]];
		printf("%d - %d    %d \n", visited[i] + 1, i + 1, grafo[i][visited[i]]);
	}

	printf("\n Custo minimo = %d\n", custoMinimo);
	tempoPrim[iter] = clock() - inicio;

	getch();

	escreve();
}

void escreve()
{
	int total = 0;
	FILE *fp;
	fp = fopen("primNovo.txt", "w");   		/* Arquivo ASCII, para escrita */
	if(!fp)
	{
		printf( "Erro na abertura do arquivo");
		exit(0);
	}
	fprintf(fp, "Prim: ");
	for(i = 0; i < 10; i++)
	{
		total += tempoPrim[i];
		fprintf(fp, "%d ", tempoPrim[i]);
	}
	fprintf(fp, "\nSoma Prim: %d", total);
}
