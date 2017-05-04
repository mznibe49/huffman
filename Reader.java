import java.awt.image.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;

public class Reader  {

    private File f;
    private boolean b; // true = texte - false = image 

    public Reader(File f, boolean b){
	this.f = f;
	this.b = b;
    }

    public Reader(File f){
	this.f = f;
    }

    public ArrayList<Color> lireImg() throws IOException {

	BufferedImage img = ImageIO.read(this.f);
	int width = img.getWidth();
	int height = img.getHeight();

	System.out.println(width+" "+height);

	ArrayList<Color> l = new ArrayList<Color>();

	for ( int i = 0; i<width; i++){
	    for (int j = 0; j<height; j++){
		l.add(new Color(img.getRGB(i,j)));
	    }
	}

	return l;

    }


    public String imgTotexte(ArrayList<Color> list){
	StringBuffer str = new StringBuffer("");
	for(  int i=0; i< list.size(); i++){
	    str.append((char)list.get(i).getRed());
	    str.append((char)list.get(i).getGreen());
	    str.append((char)list.get(i).getBlue());
	    //System.out.println(list.get(i).getRed()+" "+list.get(i).getGreen()+" "+list.get(i).getBlue());
	}
	String end = str.substring(0,str.length());
	//	System.out.println(end);
	return end;
    }

    public String lire() throws IOException {
	Scanner in = new Scanner(this.f);
	StringBuffer s = new StringBuffer("");
	int cpt = 0;
	while(in.hasNextLine()){
	    s.append(in.nextLine());
	    s.append("\n");
	}
	String newS = s.substring(0,s.length()-1);
	return newS;
    }

    public char[] tri(String s){
	//String s = str.substring(0,str.length());
	char [] tab = s.toCharArray();
	Arrays.sort(tab);
	return tab;
    }

    public Noeud[] ordre(String s1) {
	//StringBuffer s1 = new StringBuffer(s3);
	char[] c = tri(s1);
	//System.out.println(c.length);
	int [] occ = new int [c.length];
	ArrayList<Character> lettres = new ArrayList<Character>();

	for(int i=0; i<c.length;i++){ //Si la lettre n'a jamais été rencontré, on ajoute la lettre
	    if(!lettres.contains(c[i]))
		lettres.add(c[i]);
	}
	//System.out.println(lettres.size());
	for(int i=0; i<lettres.size(); i++)
	    for(int j=0; j<c.length;j++)
		if(lettres.get(i).equals(c[j]))
		    occ[i]++;

	Noeud []n = new Noeud[lettres.size()];
	System.out.println("\nLa taille de la tab de Noeud est : "+n.length+'\n');
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


    // on ecrite la taille du tableau sous la forme binaire sur 2oc
    public void writeTailleTabInFile(BitOutputStream x, int taille) throws IOException {
	//System.out.println("dd : "+taille);
	int z = 0;
	StringBuffer res = intToByte(taille);

	//System.out.println(res);
	while(z<16-res.length()){
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

    public void writeAsciiInFile(BitOutputStream x,int taille) throws IOException {
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

    //on ecrit le char sur 1oc
    public void writeCharInFile(BitOutputStream x, char c) throws IOException {
	byte z = (byte)(c);
	int za = (int)(c);
	//StringBuffer res = intToByte(x);
	writeAsciiInFile(x,za);
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

    // 2oc pour la taille du tableau de frequence
    // 1oc pour la representation ascii du char
    // 4oc pour la frequence du char && cme ça si jamais c trop lours bah ça surportte bien
    public void writeEnteteInFile(BitOutputStream x, Noeud [] tab) throws IOException {
	writeTailleTabInFile(x,tab.length);
	for(int i =0; i<tab.length; i++){
	    writeCharInFile(x,tab[i].getChar());
	    writeFreqInFile(x,tab[i].getFreq());
	}

    }

    //2oc pour largeur/hauteur
    public void writeTinFile(BitOutputStream x, int freq) throws IOException {
	StringBuffer res = intToByte(freq); // "111" pour freq = 5
	int z = 0;
	while( z < 16-res.length() ){
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

    // 2oc pr hauteur 2oc pr largeur
    public void writeResolutionInFile(BitOutputStream x, int width, int height) throws IOException {
	//StringBuffer wid = intToByte(width);
	//StringBuffer hei = intToByte(height);	
	//System.out.println(wid+" "+hei);

	writeTinFile(x,width);
	writeTinFile(x,height);

    }

    public void writeBInFile(BitOutputStream x) throws IOException {
	//int z = 0;
	if(this.b == false ){ // image
	    for(int i = 0; i<8; i++){
		x.writeBit(0);
	    }
	} else {
	    for(int i = 0; i<7; i++){
		x.writeBit(0);
	    }
	    x.writeBit(1);
	}
    }

    public void writeInFileBit() throws IOException {
	BitOutputStream x = new BitOutputStream(new FileOutputStream("code.txt"));
	System.out.println("TITS");
	String texte = "";
	ArrayList<Color> l = null;
	if(this.b == false){ // image
	    l = lireImg();
	    texte = imgTotexte(l);
	} else {
	    texte = lire();
	}
	System.out.println("Taille de texte : "+texte.length());
	Noeud [] tab = ordre(texte);
	//System.out.println(tab.length);
		
	for(int i=0; i<tab.length;i++){
	    System.out.println(tab[i].getChar()+" "+tab[i].getFreq());
	}

	Noeud n = create_Tree(tab);
	String [] st = new String [256];
	n.code_bin(st,"");
	System.out.println();

	ArrayList<Huff> list = new ArrayList<Huff>();

	for(int i = 0; i<st.length; i++){
	    if(st[i] != null){
		list.add(new Huff((char)i,st[i]));
		System.out.println(i+" "+(char)i+"  "+st[i]);
	    }    
	}

	System.out.println();

	int width = 0;
	int height = 0;
	if(this.b == false){
	    BufferedImage img = ImageIO.read(this.f);
	    width = img.getWidth();
	    height = img.getHeight();
	    System.out.println(width+" "+height+" "+tab.length);
	}

	//System.out.println(this.);
	// on ecrit le boolean qui represente l'extention
	/*if(this.b == false)
	    writeBInFile(x,0);
	else
	writeBInFile(x,1);*/
	writeBInFile(x);
	if(this.b == false)
	    writeResolutionInFile(x,width,height);
	writeEnteteInFile(x,tab);

	System.out.println();

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

}



