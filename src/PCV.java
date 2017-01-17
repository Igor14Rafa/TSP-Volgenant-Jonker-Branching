
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;
import java.awt.Dimension;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileReader;

import javax.swing.JTextField;

import java.awt.Component;

public class PCV extends javax.swing.JFrame {
	long tinicial, tfinal;
	double tempo;
	int aux, aux2, aux3, aux5, aux6, num = 0;
	int i, j, m, n;
	List<Edge> a = new ArrayList<Edge>();
	Random gera = new Random();
	Prim referencia = new Prim();
	Kruskal k = new Kruskal();
	Prim buscar = new Prim();
	int matriz_adjacencia[][];
	int num_de_vertices = 0; 
	static int contador = 0; 

	public PCV() {
		getContentPane().setBackground(new Color(153, 153, 153));
		setPreferredSize(new Dimension(800, 510));
		getContentPane().setPreferredSize(new Dimension(800, 400));
		getContentPane().setMaximumSize(new Dimension(2147483647, 2147483494));
		setMaximumSize(new Dimension(2147483406, 2147483647));
		setTitle("Trabalho Pesquisa Operacional");
		JOptionPane.showMessageDialog(null,"Primeiro insira o n° de vértices\nDepois escolha entre gerar grafo aleatório\nou ler do arquivo");
		initComponents();
	}

	private void initComponents() {

		tituloPCV = new javax.swing.JLabel();
		tituloPCV.setAlignmentX(Component.CENTER_ALIGNMENT);
		grafoRandom = new javax.swing.JButton();
		branchAndBound = new javax.swing.JButton();
		inserirNumVertices = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		textMatrixGrafo = new javax.swing.JTextArea();
		jScrollPane2 = new javax.swing.JScrollPane();
		textResultado = new javax.swing.JTextArea();
		textResultado.setFont(new Font("Arial", Font.PLAIN, 13));
		jSeparator2 = new javax.swing.JSeparator();
		inserirValorArestas = new javax.swing.JButton();
		lblResultado = new javax.swing.JLabel();
		lblResultado.setFont(new Font("Tahoma", Font.PLAIN, 16));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setBackground(new java.awt.Color(153, 153, 255));

		tituloPCV.setFont(new Font("Arial", Font.PLAIN, 25)); 
		tituloPCV.setText("Branch and Bound - PCV - Parti\u00E7\u00E3o de Volgenant-Jonker ");
		tituloPCV.setMaximumSize(new java.awt.Dimension(275, 15));

		grafoRandom.setFont(new java.awt.Font("Tekton Pro Ext", 0, 18)); 
		grafoRandom.setText("Gerar Grafo Aleat\u00F3rio");
		grafoRandom.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		branchAndBound.setFont(new java.awt.Font("Tekton Pro Ext", 0, 18)); 
		branchAndBound.setText("Branch and Bound");
		branchAndBound.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});

		inserirNumVertices.setFont(new java.awt.Font("Tekton Pro Ext", 0, 18)); 
		inserirNumVertices.setText("Inserir N\u00FAmero de V\u00E9rtices");
		inserirNumVertices.setToolTipText("");
		inserirNumVertices.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});

		textMatrixGrafo.setColumns(20);
		textMatrixGrafo.setFont(new Font("Arial", Font.PLAIN, 14)); 
		textMatrixGrafo.setRows(5);
		jScrollPane1.setViewportView(textMatrixGrafo);

		textResultado.setColumns(20);
		textResultado.setRows(5);
		jScrollPane2.setViewportView(textResultado);

		inserirValorArestas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		inserirValorArestas.setText("Inserir Valor das Arestas");
		inserirValorArestas.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		lblResultado.setText("Resultado");
		lblResultado.setToolTipText("");

		JLabel lblMatrizDeAdjacencia = new JLabel();
		lblMatrizDeAdjacencia.setToolTipText("");
		lblMatrizDeAdjacencia.setText("Matriz de adjac\u00EAncia do grafo");
		lblMatrizDeAdjacencia.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JButton lerArquivo = new JButton();
		lerArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				lerArquivoActionPerfomed(evt);
			}
		});
		lerArquivo.setText("Ler Grafo do Arquivo");
		lerArquivo.setFont(new Font("Dialog", Font.PLAIN, 18));
		
		textNumVertices = new JTextField();
		textNumVertices.setColumns(10);
		textNumVertices.setText(String.valueOf(num_de_vertices));
		
		JLabel lblNumVertices = new JLabel();
		lblNumVertices.setToolTipText("");
		lblNumVertices.setText("N\u00FAm. V\u00E9rtices");
		lblNumVertices.setFont(new Font("Tahoma", Font.PLAIN, 16));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(inserirValorArestas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(inserirNumVertices, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(27)
							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(branchAndBound, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(grafoRandom, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)))
						.addGroup(layout.createSequentialGroup()
							.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 502, GroupLayout.PREFERRED_SIZE)
							.addGap(18))
						.addGroup(layout.createSequentialGroup()
							.addComponent(lblMatrizDeAdjacencia, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
							.addGap(155)))
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup()
							.addGap(81)
							.addComponent(lblResultado))
						.addGroup(layout.createSequentialGroup()
							.addGap(18)
							.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(layout.createSequentialGroup()
									.addComponent(lblNumVertices, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textNumVertices, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(lerArquivo, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addComponent(jSeparator2, GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap(77, Short.MAX_VALUE)
					.addComponent(tituloPCV, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(68))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(12)
					.addComponent(tituloPCV, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
							.addComponent(grafoRandom, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(textNumVertices, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNumVertices, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(inserirNumVertices, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(inserirValorArestas)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
							.addComponent(branchAndBound)
							.addComponent(lerArquivo, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMatrizDeAdjacencia, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblResultado))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE)
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		getContentPane().setLayout(layout);

		pack();
	}
		
	//Gera grafo aleat�rio
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		matriz_adjacencia = new int[num_de_vertices + 1][num_de_vertices + 1];
		System.out.print("V�RTICES: ");
		for (i = 1; i <= num_de_vertices; i++) {
			System.out.print(i + " ");
		}
		System.out.println("\n");
		for (i = 1; i <= num_de_vertices; i++) {
			for (j = 1; j <= num_de_vertices; j++) {
				if (i == j) {
					matriz_adjacencia[i][j] = 999;
				} else {
					aux = gera.nextInt(50);
					while (aux == 0) {
						aux = gera.nextInt(50);
					}
					matriz_adjacencia[i][j] = aux;
					matriz_adjacencia[j][i] = aux;
				}
			}
		}
		textMatrixGrafo.setText("");
		for (int m = 1; m <= num_de_vertices; m++)
			textMatrixGrafo.append("\t" + m);
		textMatrixGrafo.append("\n\n");
		for (int n = 1; n <= num_de_vertices; n++){
			textMatrixGrafo.append(n + "\t");
			for (int l = 1; l <= num_de_vertices; l++){
				textMatrixGrafo.append(matriz_adjacencia[n][l] + "\t");
				if(l == num_de_vertices){
					textMatrixGrafo.append("\n");
				}
				textMatrixGrafo.append("");
			}
		}

		JOptionPane.showMessageDialog(null,"Grafo aleat�rio gerado com sucesso");
	}
	
	//Executa o branch and bound
	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {

		for (int y = 1; y <= num_de_vertices; y++) {
			for (int x = 1; x <= num_de_vertices; x++) {
				if (matriz_adjacencia[y][x] > 0 && matriz_adjacencia[y][x] < 999) {
					Edge e1 = new Edge(y, x, matriz_adjacencia[y][x]);
					a.add(e1);
				}	
			}
		}

		BranchAndBound b = new BranchAndBound(a, num_de_vertices + 1);
		long tempoInicio = System.currentTimeMillis();
		Node k = b.branch();

		textResultado.setText("");
		textResultado.append("Arestas da solu��o �tima: \n");
		for (int t = 0; t < k.getTamArv(); t++)
			textResultado.append(k.getArv().get(t).getAnt() + "  :  " + k.getArv().get(t).getProx() + "  =  " + k.getArv().get(t).getPeso()+"\n");
		
		textResultado.append("\n\n");
		textResultado.append("Info:\n");
		textResultado.append("Custo da Solu��o �tima: " + k.getCusto()+"\n");
		textResultado.append("N�mero de podas: "+ b.quantpodas()+"\n");
		textResultado.append("N�mero de n�s gerados: "+ b.quatnos()+"\n");
		textResultado.append("Tempo decorrido: "+(System.currentTimeMillis() - tempoInicio)+" ms");
	}

	//Inser��o do n�mero de v�rtices
	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
		String s = null;

		s = JOptionPane.showInputDialog("Inserir n�mero de v�rtices", "N�mero de v�rtices (Exemplo: 4)");
		num_de_vertices = Integer.parseInt(s);

		textNumVertices.setText(String.valueOf(num_de_vertices));

	}
	
	//Ler grafo do arquivo
	private void lerArquivoActionPerfomed(ActionEvent evt) {
		matriz_adjacencia = new int[num_de_vertices + 1][num_de_vertices + 1];
		String arquivo  = JOptionPane.showInputDialog("Informe o nome do arquivo (deve estar na mesma pasta do projeto)","Nome do arquivo (Ex: grafo.txt)");
		Scanner scanner;
		Vector<Integer> array = new Vector<Integer>();
		String[] tempString = {};
		int i = 0, j = 0;
		try {
			scanner = new Scanner(new FileReader(arquivo)).useDelimiter("\\n").useDelimiter(" ");
			while(scanner.hasNextLine()){
				String auxString = scanner.nextLine().toString();
				tempString = auxString.split(" ");
				for(int k = 0; k < tempString.length; k++){
					array.add(Integer.parseInt(tempString[k]));
				}
			}
			scanner.close();

			int itValores = 0;
			for(i = 1; i <= num_de_vertices; i++){
				for(j = 1; j <= num_de_vertices; j++){
					matriz_adjacencia[i][j] = array.elementAt(itValores);
					itValores++;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		textMatrixGrafo.setText("");
		for(int m = 1; m <= num_de_vertices; m++)
			textMatrixGrafo.append("\t" + m);
		textMatrixGrafo.append("\n\n");
		for(int n = 1; n <= num_de_vertices; n++){
			textMatrixGrafo.append(n + "\t");
			for(int l = 1; l <= num_de_vertices; l++){
				textMatrixGrafo.append(matriz_adjacencia[n][l] + "\t");
				if(l == num_de_vertices){
					textMatrixGrafo.append("\n");
				}
				textMatrixGrafo.append("");
			}
		}

		JOptionPane.showMessageDialog(null,"Grafo gerado com sucesso");
	}

	//Inser��o manual dos valores dos v�rtices
	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		matriz_adjacencia = new int[num_de_vertices + 1][num_de_vertices + 1];
		int i = 1;            
		int j = 1;

		while((i*j)  < (num_de_vertices * num_de_vertices)){  
			if(i == j){
				matriz_adjacencia[i][j] = 999;
				j++;
			}

			if(j <= num_de_vertices){
				String valor = "";  
				valor = JOptionPane.showInputDialog("Informe o valor da aresta :"+"["+i+"]"+"["+j+"]","Valor da aresta (Ex: 5)");  
				matriz_adjacencia[i][j] = Integer.parseInt(valor);   
				j++; 
			}else{
				j = 1;
				i++;
			}
		}
		matriz_adjacencia[num_de_vertices ][num_de_vertices] = 999;
		textMatrixGrafo.setText("");
		
		for (int m = 1; m <= num_de_vertices; m++)
			textMatrixGrafo.append("\t" + m);
		textMatrixGrafo.append("\n");
		for (int n = 1; n <= num_de_vertices; n++){
			textMatrixGrafo.append(n + "\t");
			for (int l = 1; l <= num_de_vertices; l++){
				textMatrixGrafo.append(matriz_adjacencia[n][l] + "\t");
				if(l == num_de_vertices){
					textMatrixGrafo.append("\n");
				}
				textMatrixGrafo.append("");
			}
		}
	}

	public static void main(String args[]) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(PCV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(PCV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(PCV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(PCV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PCV().setVisible(true);
			}
		});
	}

	private javax.swing.JButton grafoRandom;
	private javax.swing.JButton inserirValorArestas;
	private javax.swing.JButton branchAndBound;
	private javax.swing.JButton inserirNumVertices;
	private javax.swing.JLabel tituloPCV;
	private javax.swing.JLabel lblResultado;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JTextArea textMatrixGrafo;
	private javax.swing.JTextArea textResultado;
	private JTextField textNumVertices;
}
