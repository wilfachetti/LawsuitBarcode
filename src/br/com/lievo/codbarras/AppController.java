package br.com.lievo.codbarras;

import javax.swing.TransferHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppController {
    private AppView appView;

    public AppController(AppView appView) {
        this.appView = appView;
    }

    public void laucher() {
        appView.montaFormulario();	

        appView.getBtnGerar().addActionListener(new ActionListener() {
				@Override
                public void actionPerformed(ActionEvent e){	
                    try{
						if(VerificaDig.validaNum(appView.getMaskTF().getText())){
							appView.getImageCodBar().setIcon(null);
							appView.getImageCodBar().setIcon(Intercalado2de5.criarImagem(VerificaDig.nCompleto(appView.getMaskTF().getText()), appView.getMaskTF().getText()));
							appView.getBtnCopiar().requestFocus();
						} else {
							appView.getImageCodBar().setIcon(null);
							appView.showMessage("PROCESSO INVÁLIDO");   
							appView.getMaskTF().requestFocus();
						}
                    } catch(Exception nIncorreto){
                        appView.showMessage("Este número, não é um número de processo válido!");
                        appView.getMaskTF().requestFocus();
                    }
                }

            });

		appView.getBtnCopiar().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try{
                        TransferHandler handler = appView.getMaskTF().getTransferHandler();				
                        handler.exportToClipboard(appView.getMaskTF(), appView.getClipboard(), TransferHandler.COPY);
                        appView.getMaskTF().requestFocus();
                    }catch(Exception copia){
                        appView.showMessage("Não há dados para Copiar. Primeiro, tente converter um número!");
                    }
                }
		});

    }
         
}
