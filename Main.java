import java.io.*;
import java.util.*;

public class Main {

    public static void main(String [] args) throws IOException{
	Chrono.Go_Chrono();
	//stem.out.println(args[0]);
	System.out.println("Tapez le nom du fichier a compresser : ");
	Scanner sc = new Scanner(System.in);
	String str = sc.nextLine();
	String [] ext = str.split("\\.");
	boolean b = false;
	if (ext[1].equals("txt"))
	    b = true;
	File f = new File(str);
	Reader reader = new Reader(f,b);

	System.out.println("TOT");
	reader.writeInFileBit();
	System.out.println(":=>");
	BitInputStream input= new BitInputStream(new FileInputStream("code.txt"));
	File f2 = new File ("code.txt");
	Reader reader2 = new Reader (f2);
	input.decompression(reader2, input);

	Chrono.Stop_Chrono();
    }

}
