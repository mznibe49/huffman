public class Huff {

    private char a;
    private String res;

    //le char a est sa representation apres sa definition dans l'arbre
    public Huff(char a, String res){
	this.a = a;
	this.res = res;
    }

    public char getAlph(){ return a; }
    public String getCode(){ return res; }

    
}
