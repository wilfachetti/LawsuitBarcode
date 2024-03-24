package br.com.lievo.codbarras;



// 0000209-51.2008.822.0017
// 0000000-01.0000.000.0000
public class Application {    
	
	public static void main(String args[]){
		AppController appController = new AppController(new AppView());
		appController.laucher();		   
	}
}