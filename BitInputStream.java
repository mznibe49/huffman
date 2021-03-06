import java.io.*;
import java.util.LinkedList;
import java.awt.image.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;

/**
 * Classe permettant la lecture dans un flot bit à bit
 * @author Forax the best of us
 * @version 1.0
 */
public class BitInputStream extends FilterInputStream {  
    int bits;
    int mask;

    public BitInputStream(InputStream in) {
	super(in);
    }
    /**
     * Reads the next bit from this input stream. The value is returned as an
     * int in the range 0 to 1. If no bit is available because the end of the stream
     * has been reached, the value -1 is returned.
     * This method blocks until input data is available, the end of the stream
     * is detected, or an exception is thrown. 
     */
    public int readBit() throws IOException {
	int bits;
	int mask=this.mask;

	if (mask==0) {
	    bits=read();
	    if (bits==-1)
		return -1;

	    this.bits=bits;
	    mask=0x80;
	}
	else {
	    bits=this.bits;
	}

	if ((bits & mask)==0) {
	    this.mask=mask>>1;
	    return 0;
	}
	else {
	    this.mask=mask>>1;
	    return 1;
	}
    }

    public int[] readBit16(int nombre ) throws IOException{
	int[] bits = new int[nombre];
	for(int i=0; i<bits.length;i++)
	    bits[i] = -1;

	int j = 0;
	int tmp=-1;
	for(int i = 0;i < bits.length; i++){
	    tmp = readBit();
	    if(tmp < 0){
		break;
	    }
	    else{
		bits[j] = tmp;
		j++;
	    }
	}
	return bits;
    }

    public LinkedList<Integer> bits(int [] bits){
	LinkedList<Integer> tmp = new LinkedList<Integer>();
	for(int i=0; i< bits.length;i++){
	    if(bits[i] == 1){
		tmp.add(bits[i]);
		for(i=i+1; i<bits.length; i++){
		    tmp.add(bits[i]);
		}
	    }
	}
	return tmp;
    }


    public int convertInDecimal(LinkedList<Integer> binaires){
	int n = binaires.size();
	int p = n -1;
	int res = 0;
	for(int val : binaires){
	    res =  (int)(res+ val*Math.pow(2, p));
	    p--;
	}
	return res;
    }

    public static char intToChar(int n){
	return (char) n;
    }

    public char conv(int x){
	if (x==1) return '1';
	else return '0';
    }

    public void print(Noeud [] tab){
	for (int i =0; i<tab.length; i++){
	    System.out.println(i+" "+tab[i].getChar()+" "+tab[i].getFreq());
	}
    }

    public ArrayList<Color> make_Color(String texte){
	ArrayList<Color> l = new ArrayList<Color>();
	for(int i = 0; i<texte.length(); i+=3){
	    int red = (int)texte.charAt(i);
	    int green = (int)texte.charAt(i+1);
	    int blue = (int)texte.charAt(i+2);
	    Color c = new Color(red,green,blue);
	    l.add(c);
	}
	return l;
    }

    public void decompression(Reader r, BitInputStream input) throws IOException {
	//String t ="";
	int bool = convertInDecimal(bits(readBit16(8)));
	System.out.println(bool);
	//boolean b = false;
	//if (bool == 1) b = true;
	int width = 0;
	int height = 0;
	if(bool == 0){ // c une image sin c un txt
	    width = convertInDecimal(bits(readBit16(16)));
	    height = convertInDecimal(bits(readBit16(16)));
	}	
	int taille = convertInDecimal(bits(readBit16(16)));
	//System.out.println(width+" "+height+" "+taille);
	Noeud [] tab = new Noeud [taille];
	int j; //frequence
	char lettre; //char
	int cpt=0;
	System.out.println("\nbla\n");
	//return;
	for (int i =0; i<taille; i++){
	    lettre = intToChar(convertInDecimal(bits(readBit16(8))));
	    j = convertInDecimal(bits(readBit16(32)));
	    tab [i]= new Noeud (lettre, j);
	    cpt = cpt +j;
	    System.out.println(lettre+" "+j+" "+cpt);
	}
	//print(tab);
	Noeud racine = r.create_Tree(tab);
	StringBuffer texte = new StringBuffer("");
	int lu = input.readBit();
	while(lu >= 0){
	    char ss = conv(lu);
	    texte.append(ss);// = texte + lu;
	    lu = input.readBit();
	}
	StringBuffer textes = racine.Decodage(texte, racine);
	String end = "";
	if (cpt != textes.length()){
	    end = textes.substring(0, cpt);
	} else {
	    end = textes.substring(0,textes.length());
	}
	if(bool == 0){
	    ArrayList<Color> list = make_Color(end);		
	    new Image(width,height,list);
	} else {
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("decode.txt")));
	    out.write(end);
	    out.close();
	}
	//System.out.println(end);

	/*	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("decode.txt")));
		out.write(end);
		out.close();*/
    }

}
