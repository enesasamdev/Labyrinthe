package AlgoAvancee;

//Par Sylvain Lobry, pour le cours "IF05X040 Algorithmique avancée"
//de l'Université de Paris, 11/2020
//Repris et modifié par Enes ASAM, étudiant en L3 informatique.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;

//Classe principale. C'est ici que vous devez faire les modifications
public class App {

	// Méthode principale
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // ajt
		// Lecture de la carte et création du graphe
		try {
			// TODO: obtenir le fichier qui décrit la carte
			File myObj = new File(args[0]); // mettre le nom du fichier peut etre args[0]
			Scanner myReader = new Scanner(myObj);
			String data = "";
			// On ignore les deux premières lignes
			for (int i = 0; i < 3; i++)
				data = myReader.nextLine();

			// Lecture du nombre de lignes
			int nlines = Integer.parseInt(data.split("=")[1]);
			// Et du nombre de colonnes
			data = myReader.nextLine();
			int ncols = Integer.parseInt(data.split("=")[1]);

			// Initialisation du graphe
			Graph graph = new Graph();

			HashMap<String, Integer> groundTypes = new HashMap<String, Integer>();
			HashMap<Integer, String> groundColor = new HashMap<Integer, String>();
			data = myReader.nextLine();
			data = myReader.nextLine();
			
			// Lire les différents types de cases
			while (!data.equals("==Graph==")) {
				String name = data.split("=")[0];
				int time = Integer.parseInt(data.split("=")[1]);
				data = myReader.nextLine();
				String color = data;
				groundTypes.put(name, time);
				groundColor.put(time, color);
				data = myReader.nextLine();
			}

			// On ajoute les sommets dans le graphe (avec le bon type)
			for (int line = 0; line < nlines; line++) {
				data = myReader.nextLine();
				for (int col = 0; col < ncols; col++)
					graph.addVertex(groundTypes.get(String.valueOf(data.charAt(col))));
			}

			// TODO: ajouter les arrêtes
			for (int line = 0; line < nlines; line++) {
				for (int col = 0; col < ncols; col++) {
					int source = line * ncols + col;
					int dest;
					double weight;
					if (line > 0) {
						if (col > 0) {
							dest = (line - 1) * ncols + col - 1;
							weight = ((graph.vertexlist.get(source).indivTime + graph.vertexlist.get(dest).indivTime) / 2);
							// le poids de l'arrête vaut le temps pour parcourir la case source et de destination (horizontalement ou verticalement), le total divisé par 2.
							graph.addEgde(source, dest, weight);
						}
					}
				}
			}

			// On obtient les noeuds de départ et d'arrivé
			data = myReader.nextLine();
			data = myReader.nextLine();
			int startV = Integer.parseInt(data.split("=")[1].split(",")[0]) * ncols + Integer.parseInt(data.split("=")[1].split(",")[1]);
			data = myReader.nextLine();
			int endV = Integer.parseInt(data.split("=")[1].split(",")[0]) * ncols + Integer.parseInt(data.split("=")[1].split(",")[1]);

			myReader.close();

			// A changer pour avoir un affichage plus ou moins grand
			int pixelSize = 10;
			Board board = new Board(graph, pixelSize, ncols, nlines, groundColor, startV, endV);
			drawBoard(board, nlines, ncols, pixelSize);
			board.repaint();

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("stop");
			}

			LinkedList<Integer> path;
			// TODO: laisser le choix entre Dijkstra et A*
			int algo; // correspond au choix que l'utilisateur fera
			do { // tant que le choix est différent de 1 (pour Dijkstra) et 2 (pour A*)
				System.out.println("Vous voulez appliquer l'algorithme de Dijkstra (1) ou A* (2) ?");
				algo = sc.nextInt(); // on recupère le choix de l'utilisateur
			} while ((algo < 1) && (algo > 2));
			if (algo == 1) { // si le choix est 1
				path = DijkstraClass.Dijkstra(graph, startV, endV, nlines * ncols, board); // on applique l'algorithme de Dijkstra
			} else { // si le choix est 2
				path = AStarClass.AStar(graph, startV, endV, ncols, nlines * ncols, board); // on applique l'algorithme A*
			}

			// Écriture du chemin dans un fichier de sortie
			try {
				File file = new File("/users/licence/il09004/AlgoA/grapheTest/out.txt");
				if (!file.exists()) {
					file.createNewFile();
					System.out.println("Fichier créé.");
				}else {
					System.out.println("Fichier déjà existant ! Le chemin ne peut donc pas être enregistré");
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);

				for (int i : path) {
					bw.write(String.valueOf(i));
					bw.write('\n');
				}
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	// Initialise l'affichage
	private static void drawBoard(Board board, int nlines, int ncols, int pixelSize) {
		JFrame window = new JFrame("Plus court chemin");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, ncols * pixelSize + 20, nlines * pixelSize + 40);
		window.getContentPane().add(board);
		window.setVisible(true);
	}
}