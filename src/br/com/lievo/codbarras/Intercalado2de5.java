package br.com.lievo.codbarras;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Intercalado2de5 {
	private static String geraCodigo(String codigo){
	    String[] digito = {	"00110",//0 
	    					"10001",//1
	    					"01001",//2
	    					"11000",//3
	    					"00101",//4 
	    					"10100",//5 
	    					"01100",//6
	    					"00011",//7
	    					"10010",//8 
	    					"01010"};//9
	    String s;
	    int i, j;
	    s = "0000";
	    for(i = 0; i <(codigo.length()/2); i++){ 
	    	int n = Integer.parseInt(codigo.substring(i*2,i*2+1));
	    	int n2 = Integer.parseInt(codigo.substring(i*2+1,i*2+2));
	        for(j = 0; j<=4; j++){
	        	s = s + digito[n].charAt(j) + digito[n2].charAt(j);
	        }
	    }
	    s += "100";
	    return s;
	}
		      
	public static ImageIcon criarImagem(String nCodigo, String texto) {  
		int width=193, height=39, i, x, t;  
		String s = geraCodigo(nCodigo);
		        
		BufferedImage buffer = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );  
		Graphics g = buffer.createGraphics();  
		g.setColor( Color.WHITE );  
		g.fillRect( 0, 0, width, height );
		x = 2;
		
		for(i = 0; i < s.length(); i++){		           
			int n = Integer.parseInt(s.substring(i,i+1));
			t = n * 2 + 1;
		        	
			if((i%2)==0){
				g.setColor( Color.BLACK ); 
		        g.fillRect(x, 2, x+t, 35);
		    }
			if((i%2)==1){
		    	g.setColor( Color.WHITE ); 
		        g.fillRect(x, 2, x+t, 35); 
		    }
		    x += t;
		}
		g.setColor( Color.WHITE ); 
		g.fillRect(191, 2, 192, 35);
		g.setFont(new Font("Arial", Font.PLAIN, 11));  		
    	g.fillRect(27, 27, 140, 15);                 	
    	g.setColor(Color.BLACK); 
    	g.drawString(texto, 30, 37);
		return new ImageIcon( buffer );  
	}
}	    
		    /** private String encodeValue(String s) {  
		    	#     if ((s.length() == 0) || ((s.length() % 2) != 0)) {  
		    	#         return "";  
		    	#     }  
		    	#   
		    	#     char[] c = s.toCharArray();  
		    	#     StringBuffer encoded = new StringBuffer();  
		    	#                   
		    	#     if (autoQuietZones) encoded.append("!");  
		    	#     if (autoStartStopMarks) encoded.append(startPattern);  
		    	#   
		    	#     for (int i = 0; i < c.length; i += 2) {  
		    	#         if (!Character.isDigit(c[i]) || !Character.isDigit(c[i + 1]))  
		    	#             return "";  
		    	#   
		    	#         String d1 = I25Pattern[c[i] - 48];  
		    	#         String d2 = I25Pattern[c[i + 1] - 48];  
		    	#   
		    	#         for (int n = 0; n <= 4; n++) {  
		    	#             encoded.append(d1.charAt(n));  
		    	#             encoded.append(d2.charAt(n));  
		    	#         }  
		    	#     }  
		    	#                   
		    	#     if (autoStartStopMarks) encoded.append(stopPattern);  
		    	#     if (autoQuietZones) encoded.append("!");  
		    	#      
		    	#     return encoded.toString();  
		    	# }**/ 