package br.com.lievo.codbarras;

// Example number: 0000000-01.0000.000.0000
public class Application {	
	public static void main(String args[]){
		AppController appController = new AppController(new AppView());
		appController.laucher();		   
	}
}