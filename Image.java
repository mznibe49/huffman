

import java.io.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;


public class Image {

	public Image(int width, int height, ArrayList<Color> c) throws IOException {
		//BufferedImage image = ImageIO.read(f);
		//int width = image.getWidth()/2;
		//int height = image.getHeight()/2;
		try {
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			int a =0;
			for (int i=0; i<width; i++){
				for (int j =0; j<height; j++){
					img.setRGB(i,j,c.get(a).getRGB());
					a++;		    
				}
			}
			Graphics2D g2d = (Graphics2D)img.getGraphics();          
			g2d.dispose();                 
			ImageIO.write(img, "bmp", new File("compress.bmp"));
		}
		catch(IOException e){
			System.out.println(e);
		}       
	}



}
