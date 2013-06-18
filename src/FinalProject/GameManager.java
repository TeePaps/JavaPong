 
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
	
	protected OnePlayerExample game1 = new OnePlayerExample();
	//protected TwoPlayer game2; 
	protected int numPlayers = 1;
	
    public GameManager() { 
        //super("Choose Mode"); 
        super("RTS_Pong");
		
		//set up menu
        JMenuBar bar = new JMenuBar();
		
		JMenu players = new JMenu("Players");
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
		players.add(onePlayerMode);
		players.add(twoPlayerMode);
		bar.add(players);
		
		JMenu game = new JMenu("Game");
		JMenuItem startGame = new JMenuItem("Start");
		JMenuItem stopGame = new JMenuItem("Stop");
		
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numPlayers == 1){
					game1.start();
				} else {
					//game2 = new TwoPlayer();
					//game2.start();
				}				
			}			
		});
		
		stopGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numPlayers == 1){
					game1.stop();
					
				} else {
					//game2 = new TwoPlayer();
					//game2.stop();
				}
			}			
		});
		game.add(startGame);
		game.add(stopGame);
		bar.add(game);
		setJMenuBar(bar);
		
		JMenu mode = new JMenu("Mode");
		
		JMenuItem easy = new JMenuItem("Easy (default)");
		easy.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				OnePlayerExample.difficulty = 1;
			}
		});
		
		JMenuItem medium = new JMenuItem("Medium");
		medium.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				OnePlayerExample.difficulty = 2;
			}
		});
		
		JMenuItem hard = new JMenuItem("Hard");
		hard.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				OnePlayerExample.difficulty = 3;
			}
		});
		
		mode.add(easy);
		mode.add(medium);
		mode.add(hard);
		bar.add(mode);
		
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