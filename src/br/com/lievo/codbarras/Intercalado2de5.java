package br.com.lievo.codbarras;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class Intercalado2de5 {
	private static String gerarCodigo(String lawsuitNumber){
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
	    String codigoFinal;
	    int counter1, counter2;

	    codigoFinal = "0000";

	    for(counter1 = 0; counter1 < (lawsuitNumber.length()/2); counter1++){ 
	    	int firstNumber = Integer.parseInt(lawsuitNumber.substring(counter1 * 2, counter1 * 2 + 1));
	    	int secondNumber = Integer.parseInt(lawsuitNumber.substring(counter1 * 2 + 1, counter1 * 2 + 2));
	        
			for(counter2 = 0; counter2 <= 4; counter2++){
	        	codigoFinal = codigoFinal + digito[firstNumber].charAt(counter2) + digito[secondNumber].charAt(counter2);
	        }
	    }

	    codigoFinal += "100";

	    return codigoFinal;
	}
		      
	public static ImageIcon criarImagem(String lawsuitNumber, String texto) {  
		int width = 193, height = 39;
		int counter1 = 0, insertPosition = 0, widthBar = 0; 

		String numeracaoCodBarras = gerarCodigo(lawsuitNumber);
		        
		BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB );  
		Graphics graphics = buffer.createGraphics();  
		graphics.setColor( Color.WHITE );  
		graphics.fillRect( 0, 0, width, height );
		
		insertPosition = 2;
		
		for(counter1 = 0; counter1 < numeracaoCodBarras.length(); counter1++){		           
			int n = Integer.parseInt(numeracaoCodBarras.substring(counter1, counter1 + 1));
			widthBar = n * 2 + 1;
		        	
			if((counter1 % 2) == 0){
				graphics.setColor( Color.BLACK ); 
		        graphics.fillRect(insertPosition, 2, insertPosition + widthBar, 35);
		    }
			if((counter1 % 2) == 1){
		    	graphics.setColor( Color.WHITE ); 
		        graphics.fillRect(insertPosition, 2, insertPosition + widthBar, 35); 
		    }
		    insertPosition += widthBar;
		}

		graphics.setColor(Color.WHITE); 
		graphics.fillRect(191, 2, 192, 35);
		graphics.setFont(new Font("Arial", Font.PLAIN, 11));  		
    	graphics.fillRect(27, 27, 140, 15);                 	
    	graphics.setColor(Color.BLACK); 
    	graphics.drawString(texto, 30, 37);

		return new ImageIcon(buffer);  
	}
}