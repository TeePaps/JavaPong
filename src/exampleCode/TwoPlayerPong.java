package exampleCode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class TwoPlayerPong extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	private int ballX = 100, ballY = 110, rock1X = 10, rock1Y = 100, rock2X =230, rock2Y = 100;
	Thread hilo;
	int right = 5;
	int left = -5;
	int up = 5;
	int down = -5;
	int width, height;
	int GamePlayer1 = 0, GamePlayer2 = 0;
	boolean player1FlagUp, player1FlagDown, player2FlagUp, player2FlagDown;
	boolean playing, gameOver;
	
	public TwoPlayerPong(){
		playing = true;
		hilo = new Thread(this);
		hilo.start();
	}
	public void paintComponent(Graphics gc){
		setOpaque(false);
		super.paintComponent(gc);
		gc.setColor(Color.black);
		gc.fillRect(ballX, ballY, 10, 10);
		
		gc.fillRect(rock1X, rock1Y, 10, 30);
		gc.fillRect(rock2X, rock2Y, 10, 30);
		
		gc.drawString("Score Player 1:"+GamePlayer1, 25,10);
		gc.drawString("Score Player 2:"+GamePlayer2, 125, 10);
		
		if(gameOver)
			gc.drawString("Game Over", 100,125);
	}
	public void positionBall(int nx, int ny)
	{
		ballX = nx;
		ballY = ny;
		this.width=this.getWidth();
		this.height=this.getHeight();
		repaint();
	}
	public void KeyPressed(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_W:
			player1FlagUp =true;
			break;
		case KeyEvent.VK_S:
			player1FlagDown = true;
			break;
		case KeyEvent.VK_UP:
			player2FlagUp = true;
			break;
		case KeyEvent.VK_DOWN:
			player2FlagDown = true;
			break;
			
		}
	}
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_W:
			player1FlagUp = false;
			break;
		case KeyEvent.VK_S:
			player1FlagDown = false;
			break;
		case KeyEvent.VK_UP:
			player2FlagUp = false;
			break;
		case KeyEvent.VK_DOWN:
			player2FlagDown = false;
			break;
		}
	}
	public void moverPlayer1(){
		if(player1FlagUp == true && rock1Y>=0)
			rock1Y+=down;
		if(player1FlagDown == true && rock1Y<=(this.getHeight()-25))
			rock1Y+=up;
		positionPlayer1(rock1X, rock1Y);
	}
	public void moverPlayer2(){
		if(player2FlagUp == true && rock2Y>=0)
			rock2Y+=down;
		if(player2FlagDown == true && rock2Y<=(this.getHeight()-25))
			rock2Y+=up;
		positionPlayer2(rock2X, rock2Y);
	}
	public void positionPlayer1(int x, int y){
		this.rock1X = x;
		this.rock1Y = y;
		repaint();
	}
	public void positionPlayer2(int x, int y){
		this.rock2X = x;
		this.rock2Y = y;
		repaint();
	}
	public void run(){
		boolean leftRight = false;
		boolean upDown = false;
		
		while(true){
			if(playing){
				if(leftRight){
					ballX+=right;
					if(ballX>=(width - 8))
						leftRight = false;
				}
				else{
					ballX += left;
					if(ballX<= 0)
						leftRight = true;
				}
				if(upDown){
					ballY+=up;
					if(ballY>=(height -8))
						upDown = false;
					
				}
				else{
					ballY+=down;
					if(ballY<=0)
						upDown = true;
				}
				positionBall(ballX,ballY);
				try{
					Thread.sleep(50);
				}catch(InterruptedException ex){
					
				}
				moverPlayer1();
				moverPlayer2();
				if(ballX>=(getWidth() - 8))
					GamePlayer1++;
				if(ballX == 0)
					GamePlayer2++;
				if(GamePlayer1 == 6 || GamePlayer2 == 6){
					playing =false;
					gameOver = true;
				}
				if(ballX <= (rock1X+10) && ballY>=rock1Y&& ballY<=(rock1Y+25))
					leftRight = true;
				if(ballX>=(rock2X-5)&& ballY>=rock2Y && ballY<=(rock2Y+25))
					leftRight = false;
			}
		}
	}
}
