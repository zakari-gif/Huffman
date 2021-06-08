
public class alphabet {
    int frequence;
    char label;
    String codeBinaire;

    public alphabet(int frequence, Character label){
        this.frequence = frequence;
        this.label = label;
    }

	public int getFrequence() {
		return frequence;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	public char getLabel() {
		return label;
	}

	public void setLabel(char label) {
		this.label = label;
	}

	public String getCodeBinaire() {
		return codeBinaire;
	}

	public void setCodeBinaire(String codeBinaire) {
		this.codeBinaire = codeBinaire;
	}
	public node getfilsdroit(node node) {
	    return node.filsdroit;
	}

    
}
