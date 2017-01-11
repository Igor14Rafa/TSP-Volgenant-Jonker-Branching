#include <stdio.h>
#include <conio.h>
#include <time.h>
#include <math.h>
#include <stdlib.h>
#include <stdbool.h>

#define TAM_MAX 3000 //Número máximo de nós do grafo

int i, j, n; // n => Número de nós; i,j => Iteradores 
int visited[TAM_MAX] = {0}, min, mingrafo = 0, grafo[TAM_MAX][TAM_MAX];
unsigned int soma = 0;
int fim, tempoPrim[10];

void leArq();
void gera();
void prim();
void escreveArq();

void main()
{
	srand((unsigned int)time(NULL));

	int choice;
	printf("\nQuantidade de nos: ");
	scanf("%d", &n);
	printf("\n1. Para entrar com uma matriz");
	printf("\n2. Para gerar 10 grafos aleatorios");
	printf("\n3.Ler matriz do arquivo\n-> ");
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
	
	if(choice == 3){
		leArq();
		prim(0);
		return;
    }

	int iter;
	for(iter = 0; iter < 10; iter++)
	{
		if(choice == 2)
			gera(iter);
		else
		{
			prim(0);
			break;
		}
	}
	escreveArq();
}


void gera(int k)
{

	//GERA O GRAFO ALEATÓRIO
	for(i = 0; i < n; i++)
		for(j = 0; j < n; j++)
		{
			grafo[i][j] = rand() % 20;
			if(grafo[i][j] < 1)grafo[i][j] = 9999; //se for menor que 400, considera-se que não há aresta (custo infinito)
			grafo[j][i] = grafo[i][j];
		}

	//PRINTS OCULTOS PARA AGILIZAR A EXECUCÃO
	printf("\nMATRIZ DE ADJACENCIA %d\n", k + 1);
	for(i = 0; i < n; i++)
	{
		printf("\n");
		for(j = 0; j < n; j++)
			if(grafo[i][j] == 9999) printf("0 ");
			else printf("%d ", grafo[i][j]);
	}
	printf("\n");

	prim(k);
}
//encontra o vértice com menor valor de chave dentre os vértices ainda não incluídos
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
	int visited[n]; //armazena a árvore construída(vértices visitados)
	int chave[n]; //valores chave para pegar a aresta de menor custo
	bool arvore[n]; //vértices ainda não incluídos na árvore

	int i;
	for(i = 0; i < n; i++)
	{
		chave[i] = 9999; //todas as chaves são infinitas inicialmente
		arvore[i] = false; //nenhum vertice visitado
	}
	chave[0] = 0; //primeiro vértice a ser pego: [vertice 1]
	visited[0] = -1; //primeiro vértice é a raiz

	int count; //a árvore vai ter n vértices, e n-1 arestas
	for(count = 0; count < (n); count++)
	{
		//pega um vértice ainda não visitado cuja aresta é mínima
		int u = chaveMinima(chave, arvore);

		//coloca o vértice como visitado
		arvore[u] = true;

		int v;
		for(v = 0; v < n; v++)
		{
			//se for adjacente e ainda nao estiver incluído, e for menor que a chave, atualize a chave
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

	escreveArq();
}

void escreveArq()
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

//Lê matriz de adjacência do grafo
void leArq(){
	FILE *entrada_grafo;
	entrada_grafo = fopen("Matriz_grafo.txt", "r");
	if(!entrada_grafo){
		printf("Erro na abertura do arquivo Matriz_grafo");
		exit(1);
	}
	
	for(i = 0; i < n; i++)
		for(j = 0; j < n; j++)
			fscanf(entrada_grafo, "%d", &grafo[i][j]);
		
	fclose(entrada_grafo);	
}
