package FinalProject;

import javax.swing.*; 

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import FinalProject.*; 

/** 
 * @authors Sally Hunsberger, Ted Papaiaoannou, Ryan Ottney
 * 
 */
public class GameManager extends JFrame { 
	private static final long serialVersionUID = 1L; 
	
	protected TwoPlayer game1 = new TwoPlayer();
	protected TwoPlayer game2; 
	protected int numPlayers;
	
    public GameManager() { 
        //super("Choose Mode"); 
        super("RTS_Pong");
        
        numPlayers = 1;
		
		//set up menu
        JMenuBar bar = new JMenuBar();
		
		JMenu mode = new JMenu("Mode");
		JMenuItem onePlayerMode = new JMenuItem("1");
		JMenuItem twoPlayerMode = new JMenuItem("2");
		onePlayerMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPlayers = 1;
			}			
		});
		twoPlayerMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numPlayers = 2;
			}			
		});
		mode.add(onePlayerMode);
		mode.add(twoPlayerMode);
		bar.add(mode);
		
		JMenu game = new JMenu("Game");
		JMenuItem startGame = new JMenuItem("start");
		JMenuItem stopGame = new JMenuItem("stop");
		
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numPlayers == 1){
					game1.start();
				} else {
					game2 = new TwoPlayer();
					game2.start();
				}
			}			
		});
		
		stopGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numPlayers == 1){
					game1.stop();
				} else {
					game2 = new TwoPlayer();
					game2.stop();
				}
			}			
		});
		game.add(startGame);
		game.add(stopGame);
		bar.add(game);
		setJMenuBar(bar);
		
		this.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				formKeyPressed(e);
			}
			public void keyReleased(KeyEvent e){
				formKeyReleased(e);
			}
		});
		
        getContentPane().add(game1, BorderLayout.CENTER);
        pack(); 
    }  //end of constructor 
    
    protected void formKeyPressed(KeyEvent e){
		game1.KeyPressed(e);
	}
	
	protected void formKeyReleased(KeyEvent e){
		game1.keyReleased(e);
	}
      
    public static void main(String[] args) {      
       
		GameManager gm = new GameManager();
		gm.setVisible(true);
			
    } //end main 
  
}
