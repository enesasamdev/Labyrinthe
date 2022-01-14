package AlgoAvancee;

//Par Sylvain Lobry, pour le cours "IF05X040 Algorithmique avancée"
//de l'Université de Paris, 11/2020
//Repris et modifié par Enes ASAM, étudiant en L3 informatique.

import java.util.ArrayList;

/**
 * Cette classe permet la représentation d'un graphe.
 */
public class Graph {
	
	/**
	 * Attribut de type ArrayList correspondant à la liste des sommets présents dans le graphe.
	 */
	ArrayList<Vertex> vertexlist;
	
	/**
	 * Attribut de type int correspondant au numéro du prochain noeud dans le graphe
	 * (il est initialisé à 0)
	 */
    int num_v = 0;
    
    /**
     * Contructeur permettant de construire un graphe, 
     * ce constructeur va seulement créer la liste de sommets (vide).
     */
    public Graph() {
        vertexlist = new ArrayList<Vertex>();
    }

    /**
     * Méthode permettant d'ajouter un sommet dans le graphe à partir du temps pour le parcourir.
     * 
     * @param indivTime Le temps pour parcourir un sommet
     */
    public void addVertex(double indivTime){
    	Vertex v = new Vertex(num_v);
    	v.indivTime = indivTime;
    	vertexlist.add(v);
    	num_v = num_v + 1;
    }
    
    /**
	 * Une fonction qui ajoute un nouveau sommet au graphe à partir de son symbole, temps, sa ligne et sa colonne.
	 * (Cette fonction est utilisé seulement pour le projet Labyrinthe)
	 * 
     * @param symbole	Le symbole du sommet.
     * @param indivTime Le temps pour parcourir le sommet.
	 * @param i         La ligne où est situé le sommet dans le graphe.
	 * @param j         La colonne où est situé le sommet dans le graphe.
	 */
    public void addVertex(char symbole, double indivTime, int i, int j) {
    	Vertex v = new Vertex(num_v, symbole, i, j);
    	v.indivTime = indivTime;
    	vertexlist.add(v);
    	num_v = num_v + 1;
    }
    
    /**
     * Méthode permettant d'ajouter une arrête dans le graphe
     * à partir du noeud source, de destination et de son poids.
     */
    public void addEgde(int source, int destination, double weight) {
        Edge edge = new Edge(source, destination, weight);
        vertexlist.get(source).addNeighbor(edge);
    }
}
