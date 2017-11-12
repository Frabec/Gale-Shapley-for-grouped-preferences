import java.util.HashMap;
import java.util.LinkedList;

public class StableMatching implements StableMatchingInterface {

	@Override
	public int[][] constructStableMatching(int[] menGroupCount, int[] womenGroupCount, int[][] menPrefs,
			int[][] womenPrefs) {
		int n = 0;
		int mgcLength=menGroupCount.length; // menGroupCount length
		int wgcLength=womenGroupCount.length;// womenGroupCount length
		for (int i = 0; i < menGroupCount.length; i++) {
			n += menGroupCount[i];
		}
		if (n==0){
			return (new int[0][0]);
		}
		int[][] globalMenPrefs = buildGlobalPreference(n, menGroupCount, menPrefs);
		int[][] globalWomenPrefs = buildGlobalPreference(n, womenGroupCount, womenPrefs);
		int[][] stableMatching = basicGS(n, globalMenPrefs, globalWomenPrefs);
		int[][] groupMatching = new int[mgcLength][wgcLength];
		int[] cumulatedMenGroupCount = new int[mgcLength]; // somme
																		// cummulées
		int[] cumulatedWomenGroupCount = new int[wgcLength];
		// initialisation somme cumulées
		cumulatedMenGroupCount[0] = menGroupCount[0];
		cumulatedWomenGroupCount[0] = womenGroupCount[0];
		for (int i = 1; i<mgcLength; i++) {
			cumulatedMenGroupCount[i] = cumulatedMenGroupCount[i - 1] + menGroupCount[i];
		}
		for (int i = 1; i < wgcLength; i++) {
			cumulatedWomenGroupCount[i] = cumulatedWomenGroupCount[i - 1] + womenGroupCount[i];
		}
		for (int i = 0; i < mgcLength; i++) {
			for (int j = 0; j < wgcLength; j++) {
				for (int k = (i==0)? 0: cumulatedMenGroupCount[i-1]; k < cumulatedMenGroupCount[i]; k++) {
					for (int l = (j==0)?0 : cumulatedWomenGroupCount[j-1]; l < cumulatedWomenGroupCount[j]; l++) {
						if(stableMatching[k][l]==1){
							groupMatching[i][j]=1;
							break;
						}
					}
				}
			}
		}
		return (groupMatching);

	}

	// Algorithme de Gale Shapley pour les individus.
	public static int[][] basicGS(int n, int[][] menPrefs, int[][] womenPrefs) {
		LinkedList<Integer> unengagedMen = new LinkedList<Integer>(); // Liste
																		// des
																		// hommes
																		// non
																		// mariés
		HashMap<Integer, LinkedList<Integer>> preferredWoman = new HashMap<Integer, LinkedList<Integer>>(); // Stockage
																											// des
																											// femmes
																											// non
																											// demandées
																											// par
																											// ordre
																											// de
																											// préférence
		int[] engagedwomen = new int[n]; // -1 si célib numéro de partenaire
											// sinon
		int[][] reversedWomenPrefs = reversePref(n, womenPrefs); // tableau de
																	// préférence
																	// de femmes
																	// inversé;
		int[][] stableMatching = new int[n][n];
		LinkedList<Integer> temp = new LinkedList<Integer>();
		int man=0;
		int woman=0;
		int partner=0;
		
		// initialisation
		for (int i = 0; i < n; i++) {
			unengagedMen.add(i);
			temp = new LinkedList<Integer>();
			engagedwomen[i] = -1;
			for (int j = 0; j < n; j++) {
				temp.add(menPrefs[i][j]);
			}
			preferredWoman.put(i, temp);
		}

		// coeur de GS

		while (!unengagedMen.isEmpty()) {
			man = unengagedMen.remove();
			woman = preferredWoman.get(man).remove();
			if (engagedwomen[woman] == -1) {
				stableMatching[man][woman] = 1;
				engagedwomen[woman] = man;
			} else {
				partner = engagedwomen[woman];
				if (reversedWomenPrefs[woman][man] < reversedWomenPrefs[woman][partner]) {
					stableMatching[partner][woman] = 0;
					stableMatching[man][woman] = 1;
					engagedwomen[woman] = man;
					unengagedMen.add(partner);
				} else {
					unengagedMen.add(man);
				}

			}
			 
		}
		return stableMatching;

	}

	// transforme les groupes en individus.
	public static int[][] buildGlobalPreference(int n, int[] GroupCount, int[][] Prefs) {
		int[][] globalPrefs = new int[n][n];
		int indice = 0;
		for (int i = 0; i < GroupCount.length; i++) {
			for (int k = 0; k < GroupCount[i]; k++) {
				globalPrefs[indice] = Prefs[i];
				indice++;
			}
		}
		return globalPrefs;
	}

	public static int[][] reversePref(int n, int[][] womenPrefs) {
		int[][] reversedWomenPrefs = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				reversedWomenPrefs[i][womenPrefs[i][j]] = j;
			}
		}
		return reversedWomenPrefs;
	}
}
