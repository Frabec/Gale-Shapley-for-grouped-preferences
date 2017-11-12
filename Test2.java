import java.util.LinkedList;

public class Test2 {
	
public static void main(String[] args) {
	int n=3;
	StableMatching s = new StableMatching();
	int[][] matching=s.constructStableMatching(new int[0], new int [0],new int[0][0],new int [0][0]);
	affiche(n, matching);
}

	public static void affiche (int n, int[][] matrix){
		for (int i=0;i<n;i++){
			for (int j=0;j<n;j++){
				System.out.print(matrix[i][j]+ " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public static void affiche (int n, int[] array){
		for (int i = 0; i <n; i++) {
			System.out.print(array[i]+ " ");
		}
		
		System.out.println("");
	}
	
	public static void affiche (LinkedList<Integer> liste){
		for (int i : liste){
			System.out.print(i+" ");
		}
		System.out.println("");
	}
	}