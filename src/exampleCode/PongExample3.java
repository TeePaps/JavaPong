package exampleCode;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class PongExample3 extends JFrame{
	private static final long serialVersionUID = 1L;
	protected JPanel panel = new JPanel();
	protected NewOnePlayerPong game = new NewOnePlayerPong();
	int xsize = 800;
	int ysize = 600;
	
	public PongExample3(){
		super("RTS_Pong");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(xsize, ysize));
		setMaximumSize(new Dimension(xsize, ysize));
		setBounds(new Rectangle(50, 50, xsize, ysize));
		setResizable(false);
		panel.setLayout(new BorderLayout());
		panel.add(game, BorderLayout.CENTER);
		setContentPane(panel);
		
		this.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				formKeyPressed(e);
			}
			public void keyReleased(KeyEvent e){
				formKeyReleased(e);
			}
		});
		
		pack();
	} //end constructor
	
	protected void formKeyPressed(KeyEvent e){
		game.KeyPressed(e);
	}
	
	protected void formKeyReleased(KeyEvent e){
		game.keyReleased(e);
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				PongExample3 thisClass = new PongExample3();
				thisClass.setVisible(true);
			}
		});
	} //end main
} //end class