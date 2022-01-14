package AlgoAvancee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;


/**
 * Classe Labyrinthe permettant de résoudre le problème du labyrinthe à l'aide de ces diverses méthodes.
 * Cette classe possède également une méthode main() qui sera à executer lors du test de la classe.
 * De plus cette classe redéfinit les méthodes distance() et AStar() (pour AStar, l'affichage de board est retiré).
 * 
 * Dans un labyrinthe, il existe plusieurs types de symboles : 
 * "." pour un sommet étant libre
 * "D" pour un sommet représentant le début
 * "S" pour un sommet représentant la sortie
 * "F" pour un sommet représentant le feu
 * "#" pour un sommet représentant un mur
 * 
 * Les différentes directions dans lesquelles le prisonnier peut aller sont :
 * "T" pour se rendre en haut
 * "B" pour se rendre en bas
 * "L" pour se rendre à gauche
 * "R" pour se rendre à droite
 * 
 * @author Enes ASAM
 */
public class Labyrinthe {

	/**
	 * La méthode A* : l’algorithme A* (à pronnoncer A star ou A étoile) peut être vu comme une extension de l’algorithme
     * de Dijkstra. La différence se fait dans le choix du nœud à explorer. Au lieu de choisir celui ayant la plus
     * petite distance temporaire, on sélectionne en fonction de la somme entre la distance temporaire et une
     * heuristique (ici, une distance estimée au nœud d’arriver) à définir.
	 * 
	 * @param graph	  Le graphe représentant la carte.
	 * @param start   Un entier représentant la case de départ (entier unique correspondant à la case obtenue dans le sens de la lecture).
	 * @param end     Un entier représentant la case d'arrivée (entier unique correspondant à la case obtenue dans le sens de la lecture).
	 * @param ncols   Le nombre de colonnes dans la carte.
	 * @param numberV Le nombre de cases dans la carte.
	 * 
	 * @return une liste d'entiers correspondant au chemin.
	 */
	public static ArrayList<Vertex> AStar(Graph graph, int start, int end, int ncols, int numberV) {
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		graph.vertexlist.get(start).timeFromSource = 0;
		HashSet<Integer> to_visit = new HashSet<Integer>();
		for (Vertex vertex : graph.vertexlist)
			to_visit.add(vertex.num);
		int i = 0;
		for (Vertex vertex : graph.vertexlist) {
			vertex.heuristic = distance(i % ncols, i / ncols, end % ncols, end / ncols);
			i++;
		}
		while (to_visit.contains(end)) {
			int min_v = 0;
			double distance_min = Double.POSITIVE_INFINITY;
			for (Integer v : to_visit) {
				if ((graph.vertexlist.get(v).timeFromSource + graph.vertexlist.get(v).heuristic) <= distance_min) {
					min_v = v;
					distance_min = graph.vertexlist.get(v).timeFromSource + graph.vertexlist.get(v).heuristic;
				}
			}
			path.add(graph.vertexlist.get(min_v));
			to_visit.remove(min_v);
			for (i = 0; i < graph.vertexlist.get(min_v).adjacencylist.size(); i++) {
				if (to_visit.contains(graph.vertexlist.get(min_v).adjacencylist.get(i).destination)) {
					int to_try = graph.vertexlist.get(min_v).adjacencylist.get(i).destination;
					boolean can_move = seRendreADestination(graph.vertexlist, to_try);
					if (can_move) {
						if (((graph.vertexlist.get(min_v).timeFromSource + graph.vertexlist.get(min_v).adjacencylist.get(i).weight) < (graph.vertexlist.get(to_try).timeFromSource))) {
							graph.vertexlist.get(to_try).timeFromSource = (graph.vertexlist.get(min_v).timeFromSource+ graph.vertexlist.get(min_v).adjacencylist.get(i).weight);
							graph.vertexlist.get(to_try).prev = graph.vertexlist.get(min_v);
						}
					}
				}
			}
		}
		return path;
	}

	
	/**
	 * Méthode static utile seulement à l'algorithme A* permettant de calculer la distance entre deux points.
	 * 
	 * @param xa L'abscisse du premier point.
	 * @param xb L'ordonnée du premier point.
	 * @param ya L'abscisse du second point.
	 * @param yb L'ordonnée du second point.
	 * 
	 * @return La distance entre les deux points.
	 */
	public static double distance(int xa, int ya, int xb, int yb) {
		double x = Math.pow((xb-xa), 2);
		double y = Math.pow((yb-ya), 2);
		return (Math.sqrt(x+y));
	}
	
	
	/**
	 * Méthode permettant de vérifier si le prisonnier peut se rendre à un sommet de destination
	 * 
	 * @param destination Le numero du sommet de destination.
	 * @param lsSommets   La liste des sommets de la carte.
	 * @param nbSommets   Le nombre de sommets dans carte.
	 * 
	 * @return true si et seulement si le symbole de destination vaut 'S' ou '.'.
	 */
	public static boolean seRendreADestination(ArrayList<Vertex> lsSommets, int destination) {
		boolean possible = false;
		if(destination < lsSommets.size())
			possible = (lsSommets.get(destination).symbole == 'S' || lsSommets.get(destination).symbole == '.');
		return possible;
	}

	
	/**
	 * Méthode permettant de savoir le chemin que le prisonnier doit emprunter pour se rendre à destination.
	 * 
	 * @param graph	  le graphe représentant la carte.
	 * @param start   un entier représentant la case de départ (entier unique correspondant à la case obtenue dans le sens de la lecture).
	 * @param end     un entier représentant la case d'arrivée (entier unique correspondant à la case obtenue dans le sens de la lecture).
	 * @param ncols   le nombre de colonnes dans la carte.
	 * @param numberV le nombre de cases dans la carte.
	 * 
	 * @return La liste des directions pour le chemin à effectuer pour se rendre à destination
	 */
	public static ArrayList<Character> cheminAPrendre(Graph graph, int start, int end, int ncols, int numberV) {
		ArrayList<Character> cheminAPrendre = new ArrayList<Character>();
		ArrayList<Vertex> chemin = AStar(graph, start, end, ncols, numberV);
		int direction;
		for (int i=0; i < (chemin.size()-1); i++) {
			direction = chemin.get(i+1).num - chemin.get(i).num;
			if (direction == 1) {
				cheminAPrendre.add('R');
			}else if (direction == -1) {
				cheminAPrendre.add('L');
			}else if (direction == ncols) {
				cheminAPrendre.add('B');
			}else if (direction == (-1 * ncols)) {
				cheminAPrendre.add('T');
			}else {
				return cheminAPrendre;
			}
		}
		return cheminAPrendre;
	}
	
	
	/**
	 * Méthode permettant de savoir si le prochain mouvement du prisonnier lui permettra
	 * de s'échapper du labyrinthe.
	 * 
	 * @param lsSommets  La liste des sommets de la carte.
	 * @param start      La position actuelle où se situe le feu dans la liste des sommets.
	 * @param nlignes    Le nombre de lignes dans la carte.
	 * @param ncols      Le nombre de colonnes dans la carte.
	 * 
	 * @return true si et seulement si le prochain mouvement du prisonnier le placera sur la case 'S'.
	 */
	public static boolean nextVictoire(ArrayList<Vertex> lsSommets, int start, int nblignes, int nbcols) {
		int posI = lsSommets.get(start).i;
		int posJ = lsSommets.get(start).j;
		
		boolean c1 = ((posI != 0) && (lsSommets.get(start - nbcols).symbole == 'S'));
		boolean c2 = ((posI != (nblignes - 1)) && (lsSommets.get(start + nbcols).symbole == 'S'));
		boolean c3 = ((posJ != 0) && ((lsSommets.get(start - 1).symbole == 'S')));
		boolean c4 = ((posJ != (nbcols - 1)) && ((lsSommets.get(start + 1).symbole == 'S')));
		boolean victoire = (c1 || c2 || c3 || c4);
		
		return victoire;
	}
	
	
	/**
	 * Méthode permettant au prisonnier de faire un mouvement. Cette méthode vérifie aussi 
	 * si le prisonnier s'échappe du labyrinthe.
	 * 
	 * @param mvt        La direction vers laquelle le mouvement sera fait.
	 * @param lsSommets  La liste des sommets de la carte.
	 * @param nblignes    Le nombre de lignes dans la carte.
	 * @param nbcols      Le nombre de colonnes dans la carte.
	 * 
	 * @return true si et seulement si le prisonnier s'échappe du labyrinthe.
	 */
	public static boolean mouvement(ArrayList<Vertex> lsSommets, char mvt, int nblignes, int nbcols) {
		int posD = 0;
		for (int i = 0; i < lsSommets.size(); i++)
			if (lsSommets.get(i).symbole == 'D')
				posD = i;
		
		if (nextVictoire(lsSommets, posD, nblignes, nbcols))
			return true;
			
		lsSommets.get(posD).symbole = 'L';
		if (mvt == 'T') {
			lsSommets.get(posD-nbcols).symbole = 'D';
		}else if (mvt == 'B') {
			lsSommets.get(posD+nbcols).symbole = 'D';
		}else if (mvt == 'L') {
			lsSommets.get(posD - 1).symbole = 'D';
	    }else if (mvt == 'R') {
		lsSommets.get(posD + 1).symbole = 'D';
		}
		return false;
	}

	
	/**
	 * Méthode permettant de propager le feu dans toutes les directions tour à tour,
	 * dans le cas où l'une des cases autour du feu est un mur, cette case ne deviendra 
	 * pas une case feu. Les futurs cases feu deviendront réellement des cases feu au tour d'après,
	 * c'est pourquoi nous les appelons dans un premier temps 'x'.
	 * De plus la partie est finit si le prisonnier se trouve sur une case feu ou si le feu
	 * se propage sur la case de sortie avant que le prisonnier soit sortie.
	 * 
	 * @param lsSommets   La liste des sommets de la carte.
	 * @param start       La position où se situe le feu dans la liste des sommets.
	 * @param nblignes    Le nombre de lignes dans la carte.
	 * @param nbcols      Le nombre de colonnes dans la carte.
	 * 
	 * @return true si et seulement si le prisonnier a perdu.
	 */
	public static boolean propagationFeu(ArrayList<Vertex> lsSommets, int start, int nblignes, int nbcols) {
		int posI = lsSommets.get(start).i;
		int posJ = lsSommets.get(start).j;

		if (posI != 0) {
			if (lsSommets.get(start-nbcols).symbole == '.') {
				lsSommets.get(start-nbcols).symbole = 'x';
			}else if (lsSommets.get(start-nbcols).symbole == 'S') {
				return true;
			}
		}
		if (posI != (nblignes-1)) {
			if (lsSommets.get(start+nbcols).symbole == '.') {
				lsSommets.get(start+nbcols).symbole = 'x';
			}else if (lsSommets.get(start+nbcols).symbole == 'S') {
				return true;
			}
		}
		if (posJ != 0) {
			if (lsSommets.get(start-1).symbole == '.') {
				lsSommets.get(start-1).symbole = 'x';
			}else if (lsSommets.get(start-1).symbole == 'S') {
				return true;
			}
		}
		if (posJ != (nbcols-1)) {
			if (lsSommets.get(start+1).symbole == '.') {
				lsSommets.get(start+1).symbole = 'x';
			}else if (lsSommets.get(start+1).symbole == 'S') {
				return true;
			}
		}
		return false;
	}

	
	/**
	 * Méthode permettant de vérifier si le prisonnier peut s'échapper du labyrinthe.
	 * Cette méthode retourne un caractère étant 'Y' si il peut s'échapper, 'N' sinon.
	 * 
	 * @param graph	    Le graphe représentant la carte.
	 * @param start     Un entier représentant la case de départ (entier unique correspondant à la case obtenue dans le sens de la lecture).
	 * @param end       Un entier représentant la case d'arrivée (entier unique correspondant à la case obtenue dans le sens de la lecture).
	 * @param nblignes  Le nombre de lignes dans la carte.
	 * @param nbcols    Le nombre de colonnes dans la carte.
	 * @param numberV   Le nombre de cases dans la carte.
	 * 
	 * @return 'Y' si le prisonnier peut s'échapper, 'N' sinon.
	 */
	public static char echappe(Graph graph, int start, int end, int nblignes, int nbcols) {
		ArrayList<Character> cheminAPrendre = cheminAPrendre(graph, start, end, nbcols, nblignes * nbcols);
		for(int i = 0; i<cheminAPrendre.size(); i++) {
			for (int j = 0; j < graph.vertexlist.size(); j++) {
				if (graph.vertexlist.get(j).symbole == 'x') {
					graph.vertexlist.get(j).symbole = 'F';
				}
			}
			for (int j = 0; j < graph.vertexlist.size(); j++) {
				if (graph.vertexlist.get(j).symbole == 'F') {
					if (propagationFeu(graph.vertexlist, j, nblignes, nbcols)) {
						return 'N';
					}
				}
			}
			if (mouvement(graph.vertexlist, cheminAPrendre.get(i), nblignes, nbcols)) {
				return 'Y';
			}
		}
		return 'N';
	}

	
	/**
	 * Méthode main dans laquelle s'effectuera les tests et dans laquelle il y aura des
	 * interections avec l'utilisateur, en effet l'utilisateur devra entrer le nombre d'instance
	 * de carte, puis il devra entrer pour chaque instance le nombre de ligne "espace" nombre de colonne,
	 * puis il entrera la valeur de chaque ligne, et ainsi de suite pour chaque instance.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Character> res = new ArrayList<Character>();
		int nbInstances = Integer.parseInt(sc.nextLine());
		char[][] map = null;
		int n = 0, m = 0;
		for (int i = 0; i < nbInstances; i++) {
			StringTokenizer t = new StringTokenizer(sc.nextLine(), " ");
			
			if(t.hasMoreTokens()) {
				n = Integer.parseInt(t.nextToken());
			}else {
				System.out.println("Erreur d'entrée");
				System.exit(1);
			}
			if(t.hasMoreTokens()) {
				m = Integer.parseInt(t.nextToken());
			}else {
				System.out.println("Erreur d'entrée");
				System.exit(1);
			}
			map = new char[n][m];
			
			for (int j = 0; j < n; j++) {
				String c = sc.nextLine();
				for (int k = 0; k < m; k++)
					map[j][k] = c.charAt(k);
			}
			
			Graph graph = new Graph();
			int d = 0, f = 0;
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < m; k++) {
					graph.addVertex(map[j][k], 1, j, k);
					if (map[j][k] == 'D')
						d = j * m + k;
					if (map[j][k] == 'S')
						f = j * m + k;
				}
			}
			for (int ligne = 0; ligne < n; ligne++) {
				for (int col = 0; col < m; col++) {
					int source = ligne * m + col;
					int dest;
					double weight;
					for (int j = -1; j <= 1; j++) {
						for (int k = -1; k <= 1; k++) {
							if ((j != 0) || (k != 0)) {
								if (((ligne + j) >= 0) && ((ligne + j) < n) && ((col + k) >= 0) && ((col + k) < m)) {
									dest = (ligne + j) * m + col + k;
									weight = 1;
									if ((Math.abs(source - dest) == 1) || (Math.abs(source - dest) == m))
										graph.addEgde(source, dest, weight);
								}
							}
						}
					}
				}
			}
			res.add(echappe(graph, d, f, n, m));
		}
		for (Character c : res)
			System.out.println(c);
		sc.close();
	}
}