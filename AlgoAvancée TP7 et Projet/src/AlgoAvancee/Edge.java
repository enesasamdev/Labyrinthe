package AlgoAvancee;

//Par Sylvain Lobry, pour le cours "IF05X040 Algorithmique avancée"
//de l'Université de Paris, 11/2020
//Repris et modifié par Enes ASAM, étudiant en L3 informatique.


/**
 * Classe permettant de représenter une arrête.
 */
public class Edge {
	
	/**
	 * Attribut de type int correspondant au numero du sommet source de l'arrête
	 */
	int source;
	
	/**
	 * Attribut de type int correspondant au numero du sommet de destination de l'arrête
	 */
    int destination;
    
    /**
     * Attribut de type double correspondant au poids de l'arrête
     */
    double weight;

    
    /**
     * Constructeur permettant de créer une arrête à partir du numéro de son sommet source,
     * sommet de destination et de son poids.
     */
    public Edge(int source, int destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    
}