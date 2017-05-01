import java.io.*;
import java.util.*;

public class Main {

    public static void main(String [] args) throws IOException{
	Chrono.Go_Chrono();
	//System.out.println(args[0].getClass().getName());
	
	File f = new File("test.txt");
	Reader reader = new Reader(f);


	reader.writeInFileBit();
	System.out.println(":=>");
	BitInputStream input= new BitInputStream(new FileInputStream("code.txt"));
	File f2 = new File ("code.txt");
	Reader reader2 = new Reader (f2);
	input.decompression(reader2, input);

	Chrono.Stop_Chrono();
    }

}
