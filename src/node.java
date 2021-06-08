public class node {
    node filsgauche;
    node filsdroit;
    alphabet Alphabet;

    public node(alphabet lettre,node left, node right){
        this.filsgauche = left;
        this.filsdroit  = right;
        this.Alphabet = lettre;
    }
    
    public alphabet getLettre() {
        return Alphabet;
    }

    public node getfilsgauche() {
        return filsgauche;
    }
    public node getfilsdroit() {
        return filsdroit;
    }
}
