package exampleCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongExample3 extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private TwoPlayerPong panel = null;
	private TwoPlayerPong getPanel(){
		if(panel == null){
			panel = new TwoPlayerPong();
		}
		return panel;
		
	}

public PongExample3(){
	super();
	initialize();
	this.addKeyListener(new KeyAdapter(){
		public void keyPressed(KeyEvent e){
			formKeyPressed(e);
		}
		public void keyReleased(KeyEvent e){
			formKeyReleased(e);
		}
	});
	}
	private void formKeyPressed(KeyEvent e){
		panel.KeyPressed(e);
	}
	private void formKeyReleased(KeyEvent e){
		panel.keyReleased(e);
	}
	private void initialize(){
		this.setResizable(false);
		this.setBounds(new Rectangle(400, 400, 400, 400));
		this.setMaximumSize(new Dimension(400, 400));
		this.setMinimumSize(new Dimension(400, 400));
		this.setContentPane(getJContentPane());
		this.setTitle("RTS_Pong");
	}
	private JPanel getJContentPane(){
		if(jContentPane == null){
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				PongExample3 thisClass = new PongExample3();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}
}
