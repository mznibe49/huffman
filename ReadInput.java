import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadInput {
	
    public static void main(String[] args) throws IOException {
	BitInputStream input= new BitInputStream(
						 new FileInputStream("code.txt"));
	int lu = input.readBit();
	int cpt = 1;
	while(lu >= 0){
	    if (cpt == 8){
		System.out.print(lu+"|");
		cpt = 0;
	    }else{
		System.out.print(lu);
	    }
	    cpt++;
	    lu = input.readBit(); // 0|0|0|0|0|0|0|1|0|0|0|0|0|0|0|1
	}
	
    }

}
