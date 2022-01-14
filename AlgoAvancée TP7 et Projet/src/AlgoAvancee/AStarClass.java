package AlgoAvancee;

//Par Sylvain Lobry, pour le cours "IF05X040 Algorithmique avancée"
//de l'Université de Paris, 11/2020
//Repris et modifié par Enes ASAM, étudiant en L3 informatique.

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Cette classe permet de représenter l'algorithme A* (A star)
 */
public class AStarClass {
	/**
	 * La méthode A* : l’algorithme A* (à pronnoncer A star ou A étoile) peut être vu comme une extension de l’algorithme
     * de Dijkstra. La différence se fait dans le choix du nœud à explorer. Au lieu de choisir celui ayant la plus
     * petite distance temporaire, on sélectionne en fonction de la somme entre la distance temporaire et une
     * heuristique (ici, une distance estimée au nœud d’arriver) à définir.
	 * 
	 * @param graph	  le graphe représentant la carte.
	 * @param start   un entier représentant la case de départ (entier unique correspondant à la case obtenue dans le sens de la lecture).
	 * @param end     un entier représentant la case d'arrivée (entier unique correspondant à la case obtenue dans le sens de la lecture).
	 * @param ncols   le nombre de colonnes dans la carte.
	 * @param numberV le nombre de cases dans la carte.
	 * @param board   l'affichage.
	 * 
	 * @return une liste d'entiers correspondant au chemin.
	 */
	public static LinkedList<Integer> AStar(Graph graph, int start, int end, int ncols, int numberV, Board board){
		graph.vertexlist.get(start).timeFromSource = 0;
		int number_tries = 0;
		
		HashSet<Integer> to_visit = new HashSet<Integer>();
		for(Vertex vertex : graph.vertexlist) //pour chaque sommet dans le graphe
		    to_visit.add(vertex.num); //on ajoute le numero du sommet dans la liste
		
		int i = 0; //variable initialisé à 0 utile pour calculer la distance heuristique
		for(Vertex v : graph.vertexlist) { //pour chaque sommet dans le graphe
			v.heuristic = distance(i%ncols, i/ncols, end%ncols, end/ncols); //on calcule sa distance heuristique
			i++; //on passe au sommet d'après dans le graphe
		}
		
		while (to_visit.contains(end)){
			int min_v = 0; //on créé une variable min_v correspondant au sommet minimale trouvé
			double distance_min = Double.POSITIVE_INFINITY; //on initialise à infini sa distance pour aller à au sommet source
			for(Integer v : to_visit ) {  //pour chaque numero de sommet dans la liste 
				if((graph.vertexlist.get(v).timeFromSource + graph.vertexlist.get(v).heuristic ) <= distance_min ) { //si la distance heuristique du sommet est inférieur ou égale 
					min_v = v; //on associe le sommet en question à min_v
					distance_min = graph.vertexlist.get(v).timeFromSource + graph.vertexlist.get(v).heuristic; //on associe sa distance à la variable qui permettra de vérifier
				}
			}			
			to_visit.remove(min_v); 
			number_tries += 1;
			
			for (i = 0 ; i < graph.vertexlist.get(min_v).adjacencylist.size() ; i++){
				if(to_visit.contains(graph.vertexlist.get(min_v).adjacencylist.get(i).destination)) { //si la liste possède le voisin du sommet minimale
					int to_try = graph.vertexlist.get(min_v).adjacencylist.get(i).destination;
					if(((graph.vertexlist.get(min_v).timeFromSource + graph.vertexlist.get(min_v).adjacencylist.get(i).weight )  < (graph.vertexlist.get(to_try).timeFromSource))) { //si la distance en passant par le sommet est inférieure à la distance temporaire
						graph.vertexlist.get(to_try).timeFromSource = (graph.vertexlist.get(min_v).timeFromSource + graph.vertexlist.get(min_v).adjacencylist.get(i).weight); //on associe la distance temporaire par la distance obtenu en passant par le sommet
						graph.vertexlist.get(to_try).prev =  graph.vertexlist.get(min_v); //le sommet parent devient le sommet minimale
					}
				}
			}

			try {
	    	    board.update(graph, min_v); 
	    	    Thread.sleep(10);
	    	} catch(InterruptedException e) {
	    	    System.out.println("stop");
	    	}  
		}
		
		System.out.println("Done! Using A*:");
		System.out.println("	Number of nodes explored: " + number_tries);
		System.out.println("	Total time of the path: " + graph.vertexlist.get(end).timeFromSource);
		LinkedList<Integer> path=new LinkedList<Integer>();
		path.addFirst(end);
		
		Vertex sommetParent = graph.vertexlist.get(end).prev; //on créé une variable de type Vertex qui correspond au sommet parent de end
		while(sommetParent != null) { //tant qu'il possède un sommet parent 
			path.addFirst(sommetParent.num); //on ajoute dans la liste du chemin le numéro du sommet parent
			sommetParent = graph.vertexlist.get(sommetParent.num).prev; //le sommet parent devient le sommet précédent
		}
		
		board.addPath(graph, path);
		return path;
	}
	
	
	/**
	 * Méthode static utile seulement à l'algorithme A* permettant de calculer la distance entre deux points.
	 * 
	 * @param xa L'abscisse du premier point
	 * @param xb L'ordonnée du premier point
	 * @param ya L'abscisse du second point
	 * @param yb L'ordonnée du second point
	 * @return La distance entre les deux points
	 */
	private static double distance(int xa, int xb, int ya, int yb) {
		double x = Math.pow((xb-xa), 2);
		double y = Math.pow((yb-ya), 2);
		return (Math.sqrt(x+y));
   }
}