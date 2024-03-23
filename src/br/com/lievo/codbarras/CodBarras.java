package br.com.lievo.codbarras;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;
import java.awt.event.KeyAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
//0000209-51.2008.822.0017
public class CodBarras {    
	
	private Icon icon = new ImageIcon("codBarras.jpg");
	private JLabel label = new JLabel(icon);
	private Clipboard clipboard;
	private JFrame frame;
	private JFormattedTextField maskTF;
	private JButton btnGerar, btnCopiar, btnSair;
	private int xPosn, yPosn, xScr, yScr;
		
	private void montaFormulario(){
	try {
		frame = new JFrame("Código de Barras SAP");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(CodBarras.class.getResource("Settings.png")));
		frame.getContentPane().setLayout(null);
        frame.setSize(350, 200);
    	frame.setResizable(false);
    	frame.setLocationRelativeTo(null);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setUndecorated(true);
    	frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
		        xPosn = e.getX(); 
		        yPosn = e.getY(); 	
			}
		});
		frame.getContentPane().addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e){ 
				xScr += (e.getX() - xPosn);
				yScr += (e.getY() - yPosn);
			    frame.setLocation(xScr, yScr);
			}    
		});		
		
		clipboard = frame.getToolkit().getSystemClipboard();		
		Border borda = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
		
		MaskFormatter mask;
		mask = new MaskFormatter("#######-##.####.#.##.####");
		mask.setPlaceholderCharacter('_');		
		
		maskTF = new JFormattedTextField(mask);
		maskTF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		maskTF.setBounds(22, 32, 193, 20);
		maskTF.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				//maskTF.requestFocus();  				
				//maskTF.setSelectionStart(0);
				//maskTF.setSelectionEnd(maskTF.getText().length());
                maskTF.setText(maskTF.getText());  
				maskTF.selectAll();
			}
		});
		maskTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()== KeyEvent.VK_ENTER)
					maskTF.transferFocus();			
			}
		});
        frame.getContentPane().add(maskTF);
        
        label.setBounds(21, 91, 195, 50);		
		label.setTransferHandler(new ImageSelection());
		label.setPreferredSize(new Dimension(195, 50));
		frame.getContentPane().add(label);
		
		btnGerar = new JButton("Gerar código");
		btnGerar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnGerar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));				
			}
			@Override
			public void mouseExited(MouseEvent e) {;
				btnGerar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
			}
		});
		btnGerar.setBackground(Color.BLACK);
    	btnGerar.setForeground(Color.WHITE);
    	btnGerar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
	    btnGerar.setBounds(234, 31, 103, 23);
	    btnGerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){	
				try{
                if(VerificaDig.validaNum(maskTF.getText())){
                	label.setIcon(null);
                	label.setIcon(Intercalado2de5.criarImagem(VerificaDig.nCompleto(maskTF.getText()), maskTF.getText()));
                	btnCopiar.requestFocus();
                }else{
                	label.setIcon(null);
                	JOptionPane.showMessageDialog(null,"PROCESSO INVÁLIDO");   
                	maskTF.requestFocus();
                }
				}catch(Exception nIncorreto){
					JOptionPane.showMessageDialog(null, "Este número, não é um número de processo válido!");
					maskTF.requestFocus();
				}
			}
		});
		btnGerar.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER)
					btnGerar.doClick();
			}
			public void keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){}
		});
	    frame.getContentPane().add(btnGerar);
	    
		btnCopiar = new JButton("Copiar");
		btnCopiar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnCopiar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));				
			}
			@Override
			public void mouseExited(MouseEvent e) {;
				btnCopiar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
			}
		});
		btnCopiar.setBackground(Color.BLACK);
    	btnCopiar.setForeground(Color.WHITE);   
    	btnCopiar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		btnCopiar.setBounds(234, 64, 103, 23);
		btnCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					TransferHandler handler = label.getTransferHandler();				
					handler.exportToClipboard(label, clipboard, TransferHandler.COPY);
					maskTF.requestFocus();
				}catch(Exception copia){
					JOptionPane.showMessageDialog(null, "Não há dados para Copiar. Primeiro tente converter um número!");
				}
			}
		});
		btnCopiar.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER){
					btnCopiar.doClick();
				}
			}
			public void keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){}
		});
        frame.getContentPane().add(btnCopiar);
        
		btnSair = new JButton("Sair");
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnSair.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));				
			}
			@Override
			public void mouseExited(MouseEvent e) {;
				btnSair.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
			}
		});
    	btnSair.setBackground(Color.BLACK);
    	btnSair.setForeground(Color.WHITE);
    	btnSair.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnSair.setBounds(234, 149, 103, 23);
        btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.exit(0);
			}
		});
		btnSair.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER)
					btnSair.doClick();				
			}
			public void keyReleased(KeyEvent e){}
			public void keyTyped(KeyEvent e){}
		});
        frame.getContentPane().add(btnSair);
        
        JLabel barraTitulo = new JLabel();
    	barraTitulo.setBounds(0, 0, 350, 21);
    	barraTitulo.setBorder(borda);    	
        barraTitulo.setForeground(Color.WHITE);
        barraTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        barraTitulo.setText("      Código de Barras - Processos");
        frame.getContentPane().add(barraTitulo);
        
    	JLabel statusbar = new JLabel();
    	statusbar.setBounds(0, 183, 350, 17);
    	statusbar.setBorder(borda);    	
    	statusbar.setForeground(Color.LIGHT_GRAY);
    	statusbar.setFont(new Font(null, Font.BOLD, 11)); 
    	statusbar.setText("  by   Willian Oliveira - COINF - Ji-Paraná/RO");
    	frame.getContentPane().add(statusbar);
    	
    	JLabel label_1 = new JLabel("New label");
    	label_1.setBounds(5, 0, 18, 23);
    	label_1.setIcon(new ImageIcon(CodBarras.class.getResource("Settings16.png")));
    	frame.getContentPane().add(label_1);
    	    	
    	frame.setVisible(true);    	
	}catch (ParseException ex) {}
	}	public static void main(String args[]){
		new CodBarras().montaFormulario();
	}
}