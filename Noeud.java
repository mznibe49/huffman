public class Noeud {

    private char c;
    private int freq;
    private Noeud fgauche, fdroit;

    public Noeud(char c, int freq){
	this.c = c;
	this.freq = freq;
    }


    public Noeud(int freq, Noeud g, Noeud d){
	this.freq = freq;
	this.fgauche = g;
	this.fdroit = d;
    }

    public int getFreq(){ return this.freq; }

    public char getChar(){ return this.c; }

    public Noeud getNG(){ return this.fgauche; }

    public Noeud getND(){ return this.fdroit; }

    public boolean estFeuille(){
	return (this.fgauche == null && this.fdroit == null);	
    }

    public void code_bin(String[] st, String s){
	if(!this.estFeuille()){
	    if(this.fdroit == null){
		this.fgauche.code_bin(st,s+0);
	    } else if(this.fgauche == null){
		this.fdroit.code_bin(st,s+1);
	    } else {
		this.fgauche.code_bin(st,s+'0');
		this.fdroit.code_bin(st,s+'1');
	    }
	} else {
	    st[this.c] = s;
	}
    }
	
    public StringBuffer Decodage(StringBuffer text, Noeud n){
	StringBuffer s=new StringBuffer("");
	Noeud actuel=n;
	for(int i=0; i<text.length(); i++){
	    char car= text.charAt(i);
	    if(car == '0')
		actuel=actuel.fgauche;
	    else
		actuel=actuel.fdroit;
	    if(actuel.estFeuille()){
		s.append(actuel.getChar());
		actuel=n;
	    }
	}
	return s;
    }
}
