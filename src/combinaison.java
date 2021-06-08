import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class combinaison {	
    String nom_du_fichier_compresser;
    String nom_du_fichier_contenant_les_frequences;
    Scanner scan = new Scanner(System.in);
	combinaison(){
	}
	public void decoding_file()throws IOException{
	 
	    System.out.println("saisir le nom_du_fichier_compresser"); 
	    nom_du_fichier_compresser= scan.nextLine();
	    System.out.println("saisir le nom_du_fichier_contenant_les_frequences"); 
	    nom_du_fichier_contenant_les_frequences= scan.nextLine();
	    
		File fichier_compresser = new File(nom_du_fichier_compresser);
		File fichier_frequence = new File(nom_du_fichier_contenant_les_frequences);
	
        ArrayList<alphabet> listLettre = new ArrayList<alphabet>();
        ArrayList<node> listNode = new ArrayList<node>();
        ArrayList<node> tree = new ArrayList<node>();
        decoding decodage = new decoding();
        listLettre=decodage.creationlettre(fichier_frequence);
        listNode =decodage.init_node(listLettre);
        decodage.sortNodes(listNode);
        
        tree = decodage.creer_arbre(listNode);
        decodage.parcourir_arbre(tree.get(tree.size()-1),null);
        
        String bits = decodage.ascii_binaire(fichier_compresser);
        String texte_dezipper=decodage.binaire_texte(bits,listLettre);
        System.out.println(texte_dezipper);
        decodage.Ecrire_dans_un_fichier(texte_dezipper);
        
        scan.close();
    }
}
