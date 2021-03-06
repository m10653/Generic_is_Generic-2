package com.idttracker.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.idttracker.util.Config;
import com.idttracker.util.SmartScroller;
/**
 * 
 * @author Michael Combs
 *
 */
public class ConsoleWindow{
	private JFrame window;
	private JMenuBar menuBar;
	private JTextPane textArea;
	private JScrollPane scrollPane;
	private JTextField input;
	private DefaultStyledDocument document;
	private Style info, warning, error, send;
	private int parnum = 0;
	private boolean gui = Boolean.parseBoolean(Config.read("ConsoleGUI"));
	private int maxLines = Integer.parseInt(Config.read("MaxLineBuffer"));
	public ConsoleWindow() {
		if(gui){
			
			window = new JFrame("Package Tracking Console");
			window .setIconImage(new ImageIcon("Icon.png").getImage());
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			window.setSize((int)(screenSize.getWidth()/4),(int) (screenSize.getHeight() /1.5));
			window.setLocationRelativeTo(null);
			window.getContentPane().setLayout(new BorderLayout());
		
		
			menuBar = new JMenuBar();
			menuBar.add("test", new JLabel("Testing"));
			window.setJMenuBar(menuBar);
		
			StyleContext sc = new StyleContext();
			document = new DefaultStyledDocument(sc);
			
		
			//Styles
			
			send = sc.addStyle("(Send)", null);
			send.addAttribute(StyleConstants.Foreground, Color.CYAN);
			
			info = sc.addStyle("(Info)", null);
			info.addAttribute(StyleConstants.Foreground, Color.WHITE);
		
			warning = sc.addStyle("(Warning)", null);
			warning.addAttribute(StyleConstants.Foreground, Color.YELLOW);
		
			error = sc.addStyle("(Error)", null);
			error.addAttribute(StyleConstants.Foreground, Color.RED);
			error.addAttribute(StyleConstants.Bold, true);
	
			textArea = new JTextPane(document);
			textArea.setBackground(new Color(50, 50, 50));
			textArea.setEditable(true);
			textArea.setBorder(null);
			textArea.setForeground(Color.WHITE);
			
			scrollPane = new JScrollPane(textArea);
			new SmartScroller(scrollPane);
			
			window.getContentPane().add(scrollPane);
		
			input = new JTextField();
			input.setBackground(new Color(50,50,50));
			input.setForeground(Color.WHITE);
			input.setCaretColor(Color.WHITE);
			input.addActionListener(new test());
			window.getContentPane().add(input, BorderLayout.SOUTH);
		
			window.setVisible(true);
			
		}
	
	}
	/**
	 * 
	 * @param c
	 */
	public void setInputTextColor(Color c){
		send.addAttribute(StyleConstants.Foreground, c);
	}
	/**
	 * 
	 * @param c
	 */
	public void setInfoTextColor(Color c){
		info.addAttribute(StyleConstants.Foreground, c);
	}
	/**
	 * 
	 * @param c
	 */
	public void setWarningTextColor(Color c){
		warning.addAttribute(StyleConstants.Foreground, c);
	}
	/**
	 * 
	 * @param c
	 */
	public void setErrorTextColor(Color c){
		error.addAttribute(StyleConstants.Foreground, c);
	}
	/**
	 * 
	 * @param str
	 * @param severity
	 */
	public void addLine(String str,int severity){
		Style style = null;
		str = str + "\n";
		switch(severity){
		case 0:
			style = send;
			break;
		case 1:
			style = info;
			break;
		case 2:
			style = warning;
			break;
		case 3:
			style = error;
			break;
		}
		try {
			clean();
			document.insertString(document.getLength(),str,null);
			document.setParagraphAttributes(parnum, str.length(), style, false);
			parnum+= str.length();
		} catch (Exception e) {
			Console.sendError("Unkown Error :" + e.toString());
		}
	}
	
	private void clean(){
		Element root = document.getDefaultRootElement();
		while(root.getElementCount() > maxLines){
			try {
				
				parnum-= document.getText(0,root.getElement(0).getEndOffset()).length();
				document.remove(0,root.getElement(0).getEndOffset());
				
			} catch (BadLocationException e) {
				Console.sendError("Unkown Error :" + e.toString());
			}
		}
	}
	
	public class test implements ActionListener{
		

		public void actionPerformed(ActionEvent e) {
			if(!e.getActionCommand().equals("")){
				Console.sendInput(e.getActionCommand());
				ComandHandler.processComand(e.getActionCommand());
				input.setText("");
			}
		}
		
	}

}
