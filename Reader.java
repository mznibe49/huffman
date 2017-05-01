import java.io.*;
import java.util.*;

public class Reader {

    private File f;

    public Reader(File f) {
	this.f = f;
    }

    // une fnction qui renvoie un tab contenant le nb d'occurence de chaque
    // lettre du fichier
    public String lire() throws IOException {
	Scanner in = new Scanner(this.f);
	StringBuffer s = new StringBuffer("");
	int cpt = 0;
	while (in.hasNextLine()) {
	    s.append(in.nextLine());
	    s.append("\n");	
	}
	//System.out.print(s);
	String newS = s.substring(0,s.length()-1);
	//System.out.println(newS);
	return newS; 
    }

    public char[] tri(String s) {
	char[] tab = s.toCharArray();
	Arrays.sort(tab);
	return tab;
    }

    public Noeud[] ordre(String s1) {
	char[] c = tri(s1);
	int [] occ = new int [c.length];
	ArrayList<Character> lettres = new ArrayList<Character>();

	for(int i=0; i<c.length;i++) //Si la lettre n'a jamais été rencontré, on ajoute la lettre
	    if(lettres.size()==0) lettres.add(c[0]);
	    else if(!lettres.contains(c[i])) lettres.add(c[i]);
	for(int i=0; i<lettres.size(); i++)
	    for(int j=0; j<c.length;j++)
		if(lettres.get(i).equals(c[j])) occ[i]++;

	Noeud []n = new Noeud[lettres.size()];

	for (int k=0; k<n.length; k++){
	    n[k]=new Noeud(lettres.get(k), occ[k]);
	}
	// ensuite je range par ordre decroissant les occurences
	for (int k = n.length; k >= 1; k--) {
	    for (int j = 0; j < k - 1; j++) {
		if (n[j + 1].getFreq() <  n[j].getFreq()) {
		    Noeud t = n[j + 1];
		    n[j + 1] = n[j];
		    n[j] = t;
		}
	    }
	}
	// on renvoie une tab de feuille trier par frequence
	return n;
    }


    public Noeud min_list(ArrayList<Noeud> list){
	Noeud min = list.get(0);
	for(int i = 1; i<list.size(); i++){
	    if( list.get(i).getFreq() < min.getFreq() ){
		min = list.get(i);
		i = 1;
	    }
	}
	return min;
    }

    public void delete(ArrayList<Noeud> list ,Noeud n){
	for(int i = 0; i<list.size(); i++){
	    if(list.get(i) == n){
		list.remove(i);
	    }
	}
    }

    public Noeud create_Tree(Noeud [] tab){
	// on met les element du tab dans une liste
	ArrayList<Noeud> list = new ArrayList<Noeud>();
	for(int i = tab.length-1; i>=0; i--){
	    list.add(tab[i]);
	    //System.out.println(tab[i]);f
	}
	Noeud tmp = null;
	if(list.size() == 1)
	    return new Noeud(1,list.get(0),null);
	while(list.size() > 1){
	    // on prend les 2 plus ptit de la list
	    // on creer un nouveau noeud qui contient les 2 plus ptit
	    // on supprime les 2 plus ptit de la list
	    // on ajoute le nouveau noeud a la list
	    // on refait cette action jusqu'a ce qu'on un seul noeud
	    // qui contient tout les autre Noeud !!

	    Noeud min1 = min_list(list);
	    delete(list,min1);
	    Noeud min2 = min_list(list);
	    delete(list,min2);
	    
	    //on a plusieurs cas : le cas ou on a feuillexfeuille, le cas ou on a feuillexnoeud et le cas ou on a noeudxnoeud
	    //cas 1 : on a feuille x feuille 
	    if((min1.estFeuille()==true) && (min2.estFeuille()==true))
		if(min1.getFreq()==min2.getFreq())
		    if((int)min1.getChar()<(int)min2.getChar())
			tmp = new Noeud(min1.getFreq()+min2.getFreq(),min1,min2);
		    else
			tmp = new Noeud(min1.getFreq()+min2.getFreq(),min2,min1);		    
		else
		    tmp = new Noeud(min1.getFreq()+min2.getFreq(),min1,min2);		
	    //cas 2 : on a feuille x noeud
	    else if(min1.estFeuille()==true && min2.estFeuille()==false || min2.estFeuille()==true && min1.estFeuille()==false)
		if(min1.estFeuille()==true)
		    tmp = new Noeud(min1.getFreq()+min2.getFreq(),min2,min1);
		else
		    tmp = new Noeud(min1.getFreq()+min2.getFreq(),min1,min2);
	    //cas 3 : on a noeud x noeud
	    else  
		tmp = new Noeud(min1.getFreq()+min2.getFreq(),min1,min2);        	   
	    list.add(tmp);
	}
	// on renvoie la racine qui contient l arbre entiere
	return tmp;
    }

    public void affiche(ArrayList<Huff> list){
	for( int i = 0; i<list.size(); i++){
	    System.out.println(list.get(i).getAlph() + " "+ list.get(i).getCode());
	}
    }

    public ArrayList<Huff> make_list(String [] st){
	ArrayList<Huff> list = new ArrayList<Huff>();
	for( int i = 0; i<st.length; i++){
	    if(st[i] != null)
		list.add(new Huff((char)i,st[i]));
	}
	return list;
    }

     // convertion d'un entier entre 0 et 9 a un char '0','1',..,'9'
    public char intToChar(int a){
	String s = ""+a;
	return s.charAt(0);
    }

    // convertion d'un int a un String qui contient sa representation binaire
    // ex : un 7 devient "111"
    public StringBuffer intToByte(int a){
        ArrayList<Integer> list = new ArrayList<Integer>();
	while( a != 0 ){
	    int x = a%2;
	    list.add(x);
	    a /= 2;
	}
	StringBuffer b = new StringBuffer("");
	for(int i = list.size()-1; i>=0; i--){
	    char tmp = intToChar(list.get(i));
	    b.append(tmp);		     
	}
	return b;
    }


    // on ecrite la taille du tableau sous la forme binaire sur 1oc
    public void writeTailleTabInFile(BitOutputStream x, int taille) throws IOException {
	//System.out.println("dd : "+taille);
	int z = 0;
	StringBuffer res = intToByte(taille);
	
	//System.out.println(res);
	while(z<8-res.length()){
	    x.writeBit(0);
	    z++;
	}
	for(int i = 0; i<res.length(); i++){
	    if(res.charAt(i) == '1')
		x.writeBit(1);
	    else
		x.writeBit(0);
	}
    }

    // le res ici represente la definition d'un char dans l'arbre
    // on lecrite sur 4oc
    public void writeFreqInFile(BitOutputStream x, int freq) throws IOException {
	StringBuffer res = intToByte(freq); // "111" pour freq = 5
	int z = 0;
	while( z < 32-res.length() ){
	    x.writeBit(0);
	    z++;
	}
	for(int i = 0; i<res.length(); i++){
	    if(res.charAt(i) == '1')
		x.writeBit(1);
	    else
		x.writeBit(0);    	
	}
    }

    //on ecrit le char sur 1oc
    public void writeCharInFile(BitOutputStream x, char c) throws IOException {
	byte z = (byte)(c);
	int za = (int)(c);
	//StringBuffer res = intToByte(x);
	writeTailleTabInFile(x,za);
	//x.write(z);
    }

    public void writeEncodageInFile(BitOutputStream x, String s) throws IOException {
	//System.out.println(":"+s+":");
	for(int i = 0; i<s.length(); i++){
	    if(s.charAt(i) == '1')
		x.writeBit(1);
	    else
		x.writeBit(0);
	}
    }

    public int exist(String res, ArrayList<Huff> l){
	//System.out.println(" "+res+" ");
	for(int i = 0; i<l.size(); i++){
	    if(l.get(i).getCode().equals(res))
	       return i;
	}
	return -1;
    }

    // 1oc pour la taille du tableau de frequence
    // 1oc pour la representation ascii du char
    // 4oc pour la frequence du char && cme ça si jamais c trop lours bah ça surportte bien
    public void writeEnteteInFile(BitOutputStream x, Noeud [] tab) throws IOException {
	 writeTailleTabInFile(x,tab.length);
	 for(int i =0; i<tab.length; i++){
	     writeCharInFile(x,tab[i].getChar());
	     writeFreqInFile(x,tab[i].getFreq());
	 }
    }	 
    
    public void writeInFileBit() throws IOException {
	BitOutputStream x = new BitOutputStream(new FileOutputStream("code.txt"));
	//String code = coder(texte,st);
	String texte = lire();
	//System.out.println(texte);
	//System.out.println(texte.length());
	Noeud [] tab = ordre(texte);
	/*for(int i = 0; i<tab.length; i++){
	    System.out.println(tab[i].getChar()+" "+tab[i].getFreq());
	    }*/
	Noeud n = create_Tree(tab);
	String [] st = new String [256];
	n.code_bin(st,"");
	System.out.println();
	ArrayList<Huff> list = new ArrayList<Huff>();

	for(int i = 0; i<st.length; i++){
	    if(st[i] != null){
		//System.out.println((char)i+" "+st[i]);
		list.add(new Huff((char)i,st[i]));
	    }    
	}
	System.out.println();
	writeEnteteInFile(x,tab);


	for(int i = 0; i<texte.length(); i++){
	    //on affiche le texte
	    //System.out.print(st[(int)(texte.charAt(i))]);
	    //on ecrit le texte
	    int indice = exist(st[(int)(texte.charAt(i))],list);
	    String tmp = list.get(indice).getCode();
	    writeEncodageInFile(x,tmp);
	}

	System.out.println();
	x.close();
    }

    public void ReadInFileBit() throws IOException {
	
    }
    
}
