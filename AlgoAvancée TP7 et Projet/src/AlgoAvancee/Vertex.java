package AlgoAvancee;

//Par Sylvain Lobry, pour le cours "IF05X040 Algorithmique avancée"
//de l'Université de Paris, 11/2020
//Repris et modifié par Enes ASAM, étudiant en L3 informatique.

import java.util.LinkedList;

/**
 * Classe permettant de représenter un sommet.
 */
public class Vertex {
	
	/**
	 * Attribut de type double correspondant au temps por parcourir le sommet.
	 */
	double indivTime;
	
	/**
	 * Attribut de type double correspondant au temps pour aller au sommet source.
	 */
	double timeFromSource;
	
	/**
	 * Attribut de type double correspondant au temps heuristic.
	 */
	double heuristic;
	
	/**
	 * Attribut de type Vertex correspondant au sommet parent.
	 */
	Vertex prev;
	
	/**
	 * Attribut de type LinkedList correspondant à une liste chaînée de l'adjacence du sommet comportant des arrêtes.
	 */
	LinkedList<Edge> adjacencylist;
	
	/**
	 * Attribut de type int correspondant au numéro du sommet.
	 */
	int num;
	
	/**
	 * Attribut de type char permettant de représenté un sommet par un symbole.
	 * Il existe différent type de symboles : "." pour un sommet étant libre
	 *                                        "D" pour un sommet représentant le début
	 *                                        "S" pour un sommet représentant la sortie
	 *                                        "F" pour un sommet représentant le feu
	 *                                        "#" pour un sommet représentant un mur
	 * (Attribut ajouté pour le projet Labyrinthe)
	 */
	char symbole;
	
	/**
	 * Attribut de type int correspondant à la ligne où est situé le sommet.
	 * (Attribut ajouté pour le projet Labyrinthe)
	 */
	int i;
	
	/**
	 * Attribut de type int correspondant à la colonne où est situé le sommet.
	 * (Attribut ajouté pour le projet Labyrinthe)
	 */
	int j;
	
	/**
	 * Constructeur permettant de créer un sommet à partir de son numéro.
	 * 
	 * @param num Le numéro du sommet.
	 */
	public Vertex(int num) {
		this.indivTime = Double.POSITIVE_INFINITY;
		this.timeFromSource = Double.POSITIVE_INFINITY;
		this.heuristic = -1;
		this.prev = null;
		this.adjacencylist = new LinkedList<Edge>();
		this.num = num;
	}
	
	
	/**
	 * Constructeur permettant de créer un sommet à partir de son
	 * numéro, symbole, sa ligne et sa colonne.
	 * (Constructeur ajouté pour le projet Labyrinthe)
	 * 
	 * @param num       Le numéro du sommet.
     * @param symbole	Le symbole du sommet.
	 * @param i         La ligne où est situé le sommet dans le graphe.
	 * @param j         La colonne où est situé le sommet dans le graphe.
	 */
	public Vertex(int num, char symbole, int i, int j) {
		this.indivTime = Double.POSITIVE_INFINITY;
		this.timeFromSource = Double.POSITIVE_INFINITY;
		this.heuristic = -1;
		this.prev = null;
		this.adjacencylist = new LinkedList<Edge>();
		this.num = num;
		this.symbole = symbole;
		this.i = i;
		this.j = j;
	}
	
	/**
	 * Méthode permettant d'ajouter un voisin au sommet.
	 * 
	 * @param e L'arête liée au voisin.
	 */
	public void addNeighbor(Edge e) {
		this.adjacencylist.addFirst(e);
	}
	
}