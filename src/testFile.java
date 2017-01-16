import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;


public class testFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] matriz = new int[4][4];
		Scanner scanner;
		Vector<Integer> arrayValores = new Vector<Integer>();
		String[] test = {};
		int i = 0, j = 0;
		try {
			scanner = new Scanner(new FileReader("grafo.txt")).useDelimiter("\\n").useDelimiter(" ");
			while(scanner.hasNextLine()){
				String aux = scanner.nextLine().toString();
				test = aux.split(" ");
				for(int k = 0; k < test.length; k++){
					arrayValores.add(Integer.parseInt(test[k]));
				}
					
			}
			scanner.close();		
			int itValores = 0;
			for(i = 0; i < 4; i++){
				for(j = 0; j < 4; j++){
					matriz[i][j] = arrayValores.elementAt(itValores);
					itValores++;
					System.out.println(matriz[i][j]);
				}
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}	
	}
}
