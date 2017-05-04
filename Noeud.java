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

    public void afficheInfixe(){
	if(this.fgauche != null){
	    this.fgauche.afficheInfixe();
	}
	System.out.print(this.c + " ");
	if(this.fdroit != null)
	    this.fdroit.afficheInfixe();
    }
  
    public Noeud maxInf(Noeud ref){
	if(this.fgauche != null){
	    System.out.println("Test FG : " +this.getFreq());
	    if(this.getFreq() < ref.getFreq()){
		System.out.println("lllllll");
		return((this.getFreq() < this.fgauche.maxInf(ref).getFreq())?this.fgauche:this);
	    }else{
		return (this.fgauche.maxInf(ref));
	    }
	    
	}
	if(this.fdroit != null){
	    System.out.println("Test FD : " +this.getFreq());
	    if(this.getFreq() < ref.getFreq()){
		System.out.println("kkkkkkk");
		return((this.getFreq() < this.fdroit.maxInf(ref) .getFreq())?this.fdroit:this);
	    }else{
		return this.fdroit.maxInf(ref);
	    }
	    
	}
	return this;
    }
    /*public Noeud maxInf(Noeud racine, Noeud tmp){
	//Noeud res = null;
       	if(racine.getFreq() < tmp.getFreq()){
	    return racine;
	    }
	
	if(racine.fgauche != null){
	    //if(racine.fgauche.getFreq() < tmp.getFreq() ){
	    return racine.fgauche.maxInf(racine.fgauche,tmp);
		//}
	}
	if(racine.fdroit != null){
	    //if(racine.fdroit.getFreq() < tmp.getFreq()){
	    return racine.fdroit.maxInf(racine.fdroit,tmp);
		//}
		}
	
	
	return racine;
	
    }*/
    
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
