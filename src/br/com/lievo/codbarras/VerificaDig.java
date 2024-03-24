package br.com.lievo.codbarras;

public class VerificaDig {	
	private static boolean validaMod97(long NNNNNNN, long DD,long AAAA, long JTR, long OOOO) {
		String valor1 = "";
		String valor2 = "";
		String valor3 = "";
		
		long resto1 = 0;
		long resto2 = 0;

		valor1 = preencheZeros(NNNNNNN, 7);
		resto1 = Long.parseLong(valor1) % 97;
		valor2 = preencheZeros(resto1, 2) + preencheZeros(AAAA, 4) + preencheZeros(JTR, 3);
		resto2 = Long.parseLong(valor2) % 97;
		valor3 = preencheZeros(resto2, 2) + preencheZeros(OOOO, 4) + preencheZeros(DD, 2);
		
		return ((Long.parseLong(valor3) % 97) == 1);
	}

	private static String preencheZeros(long numero, int quantidade){
		String temp = String.valueOf(numero);
		String retorno = "";
		if (quantidade < temp.length())
			return temp;
		else {
			for (int i = 0; i < (quantidade - temp.length()); i++)
				retorno = "0" + retorno;
			return retorno + temp;
		}
	}
	
	public static boolean validaNum(String numero){
		long NNNNNNN, DD, AAAA, JTR, OOOO;
		NNNNNNN = Long.parseLong(numero.substring(0, 7));
		DD = Long.parseLong(numero.substring(8, 10));
		AAAA = Long.parseLong(numero.substring(11, 15));
		JTR = Long.parseLong(numero.substring(16, 17) + numero.substring(18, 20));
		OOOO = Long.parseLong(numero.substring(21, 25));
		return validaMod97(NNNNNNN, DD, AAAA, JTR, OOOO);
	}	
	
	public static String nCompleto(String numero){
		numero = numero.replace("-" , "");
		numero = numero.replace("." , "");
		return numero;		
	}
}
