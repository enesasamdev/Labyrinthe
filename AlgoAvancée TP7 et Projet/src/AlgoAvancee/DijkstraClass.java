package AlgoAvancee;

//Par Sylvain Lobry, pour le cours "IF05X040 Algorithmique avancée"
//de l'Université de Paris, 11/2020
//Repris et modifié par Enes ASAM, étudiant en L3 informatique.

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Cette classe permet de représenter l'algorithme de Dijkstra
 */
public class DijkstraClass {
	
	
	/**
	 * Méthode Dijkstra : l’algorithme de Dijkstra cherche à assigner la plus courte distance depuis le point de départ à
     * tous les sommets d’un graphe, en utilisant une approche gloutonne : on part ainsi du point de départ
     * (qui a une distance nulle depuis le point de départ), et ses voisins se voient attribuer la distance entre le
     * point de départ et eux-mêmes comme distance temporaire. On considère alors le nœud courant (ici, le
     * point de départ) comme visité et on ne le visite plus.
     * On repète cette procédure en choisissant comme nœud courant celui qui a la plus petite distance
     * temporaire. On regarde pour ses voisins si la distance en passant par le nœud courant (donc distance
     * temporaire plus distance du nœud courant au voisin) est plus petite que la distance temporaire. Si tel estle cas, on mets à jour la distance temporaire, et on enregistre le nœud courant comme nœud parent du
     * voisin dans le chemin.
     * On repète cette procédure jusqu’à avoir visité le nœud d’arrivé, où on peut alors dérouler le chemin.
	 * 
	 * @param graph		le graphe représentant la carte
	 * @param start	    un entier représentant la case de départ (entier unique correspondant à la case obtenue dans le sens de la lecture)
	 * @param end	    un entier représentant la case d'arrivée (entier unique correspondant à la case obtenue dans le sens de la lecture)
	 * @param numberV 	le nombre de cases dans la carte
	 * @param board	    l'affichage
	 * @return une liste d'entiers correspondant au chemin.
	 */
	public static LinkedList<Integer> Dijkstra(Graph graph, int start, int end, int numberV, Board board){
		graph.vertexlist.get(start).timeFromSource = 0;
		int number_tries = 0;
		
		HashSet<Integer> to_visit = new HashSet<Integer>();
		for(Vertex vertex : graph.vertexlist) //pour chaque sommet dans le graphe
			to_visit.add(vertex.num); //on ajoute le numero du sommet dans la liste
		
		while(to_visit.contains(end)){
			int min_v = 0; //on créé une variable min_v correspondant au sommet minimale trouvé
			double distance_min = Double.POSITIVE_INFINITY; //on initialise à infini sa distance pour aller à au sommet source
			for(Integer vertexNum : to_visit ) { //pour chaque numero de sommet dans la liste 
				if(graph.vertexlist.get(vertexNum).timeFromSource <= distance_min) { //si la distance est plus inférieure ou égale 
						min_v = vertexNum; //on associe le sommet en question à min_v
						distance_min = graph.vertexlist.get(vertexNum).timeFromSource; //on associe sa distance à la variable qui permettra de vérifier
				}
			}	
			to_visit.remove(min_v); 
			number_tries += 1;

			for (int i = 0 ; i < graph.vertexlist.get(min_v).adjacencylist.size() ; i++) {
				if(to_visit.contains(graph.vertexlist.get(min_v).adjacencylist.get(i).destination)) { //si la liste possède le voisin du sommet minimale
					int to_try = graph.vertexlist.get(min_v).adjacencylist.get(i).destination;
					if(((graph.vertexlist.get(min_v).timeFromSource+graph.vertexlist.get(min_v).adjacencylist.get(i).weight) < (graph.vertexlist.get(to_try).timeFromSource))){ //si la distance en passant par le sommet est inférieure à la distance temporaire
						graph.vertexlist.get(to_try).timeFromSource = (graph.vertexlist.get(min_v).timeFromSource+graph.vertexlist.get(min_v).adjacencylist.get(i).weight); //on associe la distance temporaire par la distance obtenu en passant par le sommet
						graph.vertexlist.get(to_try).prev = graph.vertexlist.get(min_v); //le sommet parent devient le sommet minimale
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
		System.out.println("Done! Using Dijkstra:");
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
}