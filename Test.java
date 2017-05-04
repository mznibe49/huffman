import java.util.*;

class Test {

    public static void main(String [] args){
	//String str = "test.txt";
	//String str = "bonjour,ceci est un bon exemple de découpage d\'un string avec split,génial,no ?";
	Scanner sc = new Scanner(System.in);
	String str = sc.nextLine();
	String [] res = str.split("\\.");
	System.out.println(res[0]);
	System.out.println(res[1]);
	
	//for( int i = str.length()-1; i > 
	//System.out.println(str.charAt(str.length()-1));
	//System.out.println(1996-2017);
	/*String [] res = str.split(".");
	//System.out.println(res.length);
	for ( int i=0; i<res.length; i++){
	    System.out.println(res[i]);
	    //System.out.println(1);
	    }*/
	/*System.out.println(res[0]);	
	System.out.println(res[1]);
	/*Noeud n7 = new Noeud('s',2);
	Noeud n6 = new Noeud('P',0);
	Noeud n5 = new Noeud('I',1);
	Noeud n4 = new Noeud(1,n6,n7);
	Noeud n3 = new Noeud(3,n4,n5);
	Noeud n2 = new Noeud('m',3);
	Noeud n1 = new Noeud(4,n2,n3);
	n1.afficheInfixe();
	System.out.println();
	Noeud max = n1.maxInf(n7);*/
	//System.out.println(max.getFreq()+" "+max.getChar());
    }

}
