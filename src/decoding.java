import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;


public class decoding implements Comparator<alphabet> {


	//Créer un arbre à partir de la liste de nœud avec le codeBinaire de la lettre (Alphabet) associée au nœud
	
	public ArrayList<node> creer_arbre(ArrayList<node> liste_node){
		
	    ArrayList<node> arbre = new ArrayList<node>();
	    int minimum;
	    int min1;
	    int min2;
	    node minimum_node1;
	    node minimum_node2;
	    while(liste_node.size()>1){
	        min1=-1;
	        min2=-1;
	        minimum_node2=null;
	        minimum_node1=null;
	        minimum=Integer.MAX_VALUE;
	        //Rechercher Alphabet ayant la plus petite fréquence
	        
	        for(int j = 0; j<liste_node.size();j++){
	            if(liste_node.get(j).getLettre().getFrequence()<minimum){
	                minimum=liste_node.get(j).getLettre().getFrequence();
	                min1=j;
	            }
	        }
	        
	        
	        //Recherchez la lettre (Alphabet) avec la 2ème plus petite fréquence
	        
	        minimum=Integer.MAX_VALUE;
	        for(int i = 0; i<liste_node.size();i++){
	            if(liste_node.get(i).getLettre().getFrequence()<minimum && i!=min1){
	                minimum=liste_node.get(i).getLettre().getFrequence();
	                min2=i;
	            }
	        }
	
	        minimum_node1 = liste_node.get(min1);
	        minimum_node2 = liste_node.get(min2);
	        
	        //ajouter un noeud a la liste des noeuds compose d'alphabet et ayant les 2 plus petites frequences
	        int minfreq1 = minimum_node1.getLettre().getFrequence();
	        int minfreq2 = minimum_node2.getLettre().getFrequence();
	        
	        arbre.add(liste_node.get(min1));
	        arbre.add(liste_node.get(min2));
	        
	        liste_node.add(0,new node(new alphabet(minfreq1+minfreq2,Character.MIN_VALUE),minimum_node1,minimum_node2));
	        
	        //...supprimer les deux noeuds ayant les 2 plus petites frequences
	        liste_node.remove(minimum_node1);
	        liste_node.remove(minimum_node2);
	
	
	    }
	    arbre.add(liste_node.get(0));
	    return arbre;
	}
	
	//prenez un nœud de départ et traversez l'arbre avec le chemin
	public void parcourir_arbre(node node, String chemin){
		
	    if(node.getfilsgauche()==null  && node.Alphabet.getfilsdroit(node) == null){
	        node.getLettre().setCodeBinaire(chemin);
	        System.out.println("label : "+node.getLettre().getLabel()+" chemin -> "+node.getLettre().getCodeBinaire());
	        return;
	    }
	    if(chemin==null){
	        parcourir_arbre(node.Alphabet.getfilsdroit(node),"1");
	        parcourir_arbre(node.getfilsgauche(),"0");
	    }else{
	        parcourir_arbre(node.Alphabet.getfilsdroit(node),chemin+"1");
	        parcourir_arbre(node.getfilsgauche(),chemin+"0");
	    }
	
	}



    //Écrivez le texte décodé dans un fichier 
	
    public void Ecrire_dans_un_fichier(String str){
        PrintWriter writer = null;
        try {
            File file = new File("textedecoding.txt");
            writer = new PrintWriter(file, "ISO-8859-1");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        writer.print(str);
        writer.close();
    }

    //Convertir la chaîne binaire en texte lisible
    
    public String binaire_texte(String str, ArrayList<alphabet> List_alphabet){
        String templist="";
        String str2="";
        int size = compte_alphabet(List_alphabet); 
        int tempSize=0;
        for(int i = 0; i < str.length(); i++)//lis la string de bits
        {
            templist+=str.charAt(i);
            for (int j = 0; j < List_alphabet.size(); j++) {
                if(templist.equals(List_alphabet.get(j).getCodeBinaire())&&tempSize<size){
                    str.substring(0,templist.length()-1);//remove the already used huffman code
                    str2=str2+List_alphabet.get(j).getLabel();
                    templist="";
                    tempSize++;
                }
            }
        }
        return str2;
    }


    // Lisez le fichier de fréquences pour créer une liste d'alphabet
    
    public ArrayList<alphabet> creationlettre(File fichier_frequence){
        ArrayList<alphabet> liste_alphabet = new ArrayList<alphabet>();
        
        if (!(fichier_frequence.isFile() && fichier_frequence.canRead())) {
            System.out.println(fichier_frequence.getName() + "ne peut pas etre lit");
            System.out.println("Reessayez:");
            return null;
        }
        if (!fichier_frequence.exists()) {
            System.out.println(fichier_frequence.getName() + " n'existe pas");
            System.out.println("Reessayez:");
            return null;
        }
        
        try {
            FileInputStream fils = new FileInputStream(fichier_frequence);
            char aucours;
            char avant='~';
            String str;
            boolean bol= true;
            boolean bool1 = true;
            boolean bool2 = false;
            boolean declancheur = false;
            boolean reg;
            boolean reg1;
            boolean reg2;
            char T_Label = '~';
            String Frequence ="";
            while (fils.available() > 0) {
                aucours = (char) fils.read();
                
                str = String.valueOf(aucours);
                reg = Pattern.matches("( )", str);
                reg1 = Pattern.matches("\\n", String.valueOf(avant));
                reg2 = Pattern.matches("\\r", String.valueOf(avant));
                if (declancheur){//attend la premier it�ration du while
                    if(bool2){
                        if(Pattern.matches("[0-9]+", String.valueOf(aucours))){
                            Frequence=Frequence+aucours;
                        }else{

                            liste_alphabet.add(new alphabet(Integer.parseInt(Frequence),T_Label));
                            bool2=false;
                            Frequence="";
                        }
                    }
                    if(!reg1 && !reg2 && reg && !bool2){//si l'on est au niveau d'un espace entre un char et une fr�quence
                        T_Label=avant;
                        bool2=true;
                    }
                    if(bool1){//Si le character \r n'a pas encore �t� lu
                        if(reg && reg2 && !bool2) {
                            T_Label=avant;
                            bool2 = true;
                            bool1=false;
                        }
                    }
                    if(bol){//Si le character \n n'a pas encore �t� lu
                        if(reg && reg1 && !bool2) {
                            T_Label=avant;
                            bool2 = true;
                            bol = false;
                        }
                    }
                  
                }
                avant = aucours;
                declancheur = true;
            }
            fils.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return liste_alphabet;
    }
    
    //// Créer une liste de noeud à partir de la liste de l'alphabet
   	public ArrayList<node> init_node (ArrayList<alphabet> list_alphabet){
   	    ArrayList<node> node_List = new ArrayList<node>();
   	    
   	    for(int i=0; i<list_alphabet.size();i++){
   	    	node_List.add(new node(list_alphabet.get(i),null,null));
   	    }
   	    return node_List;
   	}
   	
   	
   	 //Trier tous les nœuds de la liste des nœuds par fréquence
     	public void sortNodes(ArrayList<node> listeNode){
     	    listeNode.sort(Comparator.comparing((node n1) -> n1.getLettre().getFrequence()).thenComparing(n1 -> n1.getLettre().getLabel()));
     	}
    
//compteur du nombre de caractere dans un texte
    
    public int compte_alphabet(ArrayList<alphabet>l){
        int size=0;
        for (int i = 0; i < l.size(); i++) {
            size+=l.get(i).getFrequence();
        }
        return size;
    }

    //convertir le texte ascii en une chaîne binaire
    
    public String ascii_binaire(File file) throws IOException{
    	if (!(file.isFile() && file.canRead())) {
            System.out.println(file.getName() + " ne peut pas etre lit");
            System.out.println("Reessayez:");
            return null;
        }
        if (!file.exists()) {
            System.out.println(file.getName() + " n'existe pas");
            System.out.println("Reessayez:");
            return null;
       
        }
        byte[] fichier_binaire = Files.readAllBytes(file.toPath());
        StringBuilder binaire = new StringBuilder();
        String str;
        for (byte b : fichier_binaire)
        {
            int val = b;
            for (int j = 0; j < 8; j++)
            {
                binaire.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        str = binaire.toString();
        return str;
    }
    
   

    @Override
    public int compare(alphabet a, alphabet aa) {
        return 0;
    }
}

