/** 
 *  
 */
package FinalProject;

import java.awt.*;

import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;

/**
 * @authors Ted Papaioannou, Ryan Ottney, Sally Hunsberger
 * 
 */
public class TwoPlayer extends JPanel implements Runnable {

	// set screen size
	int xsize = 800;
	int ysize = 600;
	// set object sizes
	int ballSize = 20;

	// initialize positions
	private int ballX = ballSize, ballY = ysize / 2;

	// define speed of movement
	final int right = 10;
	final int left = -10;
	int up = 10;
	int down = -10;
	double step = Math.sqrt(left * left + up * up);
	double dx = right, dy = 0; // use to move ball at different angles
	int width, height;
	int hits1, hits2;
	boolean player1FlagUp, player1FlagDown, player2FlagUp, player2FlagDown;
	boolean playing = false, gameOver = false;
	boolean leftRight = true;
	boolean upDown = false;
	Random rand = new Random();

	boolean clearScreen = true;
	
	public static int difficulty = 1;

	Paddle playerOne, playerTwo;

	// *** end of definitions ***

	public TwoPlayer() {
		super();
		this.setPreferredSize(new Dimension(xsize, ysize));

		playerOne = new Paddle(xsize, ysize, 1);
		playerTwo = new Paddle(xsize, ysize, 2);

		Thread t = new Thread(this);
		t.start();
	}

	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics _g) { // overriding parent component
												// implementations
		super.paintComponent(_g);
		Graphics2D g = (Graphics2D) _g;

		setOpaque(false);
		if (!clearScreen) {
			g.setColor(Color.black);
			//g.fillRect(ballX, ballY, ballSize, ballSize);
			g.fillOval(ballX, ballY, ballSize, ballSize);

			playerOne.drawPaddle(g);
			playerTwo.drawPaddle(g);

			g.drawString("Player 1: " + hits1, xsize / 2 - 100, 20);
			g.drawString("Player 2: " + hits2, xsize / 2 + 100, 20);

			if (gameOver)
				g.drawString("GAME OVER", xsize / 2, 60);
	    }
	}

	public void start() {
		if (gameOver) {
    		Reset();
    	}
		if (difficulty > 2) {
    		playerOne.setLength(60);
    		playerTwo.setLength(60);
    	} else {
    		playerOne.setLength(100);
    		playerTwo.setLength(100);
    	}
		
		playing = true;
		clearScreen = false;
	}

	public void stop() {
		playing = false;
		clearScreen = true;
		repaint();
	}

	protected void moveBall() {
		if (dx > 0 && ballX >= xsize - ballSize) { // ball at player end of
													// court
			dx = left;
		} else if (dx < 0 && ballX <= 0) { // ball at server end of court
			dx = right;
			// select up or down
			if (rand.nextBoolean()) {
				dy = up;
			} else {
				dy = down;
			}
		}

		if (dy > 0 && ballY >= ysize - ballSize) {
			dy = down;
		} else if (dy < 0 && ballY <= 0) {
			dy = up;
		}
		
		if (difficulty > 1) {
			dx = dx * 1.4;
			dy = dy * 1.4;
		}

		ballX += dx;
		ballY += dy;
		positionBall(ballX, ballY);
		
		if (difficulty > 1) {
			dx = dx / 1.4;
			dy = dy / 1.4;
		}
	}

	protected void positionBall(int nx, int ny) {
		ballX = nx;
		ballY = ny;
		this.width = this.getWidth();
		this.height = this.getHeight();
		repaint();
	}

	protected void checkForHitLeft(Paddle paddle) {
		if (ballX <= (paddle.getX() + ballSize)) {
			if (ballY >= paddle.getY() - ballSize + 1
					&& ballY <= paddle.getY() + paddle.getLength() - 1) {
				hits1++;
				dx = right;
				dy = 0; // this is a direct hit
				int paddleCenter = paddle.getY() + paddle.getLength() / 2;
				int ballCenter = ballY + ballSize / 2;
				double offset = ballCenter - paddleCenter;
				double halfPaddle = paddle.getLength()/2;
				//set dx and dy dependent on region of paddle hit
				double angleDeg = 90.0 + 60.0 * (offset/halfPaddle);
				dx = +step * Math.sin(Math.toRadians(angleDeg));
				if (offset < 0) {
					dy = -Math.sqrt(step*step - dx*dx);
				} else if (offset > 0) {
					dy = Math.sqrt(step*step - dx*dx);
				}
			}
		}
	}

	protected void checkForHitRight(Paddle paddle) {
		if (ballX >= (paddle.getX() - ballSize)) {
			if (ballY >= paddle.getY() - ballSize + 1
					&& ballY <= paddle.getY() + paddle.getLength() - 1) {
				hits2++;
				
				dx = left;
				dy = 0; // this is a direct hit
				int paddleCenter = paddle.getY() + paddle.getLength() / 2;
				int ballCenter = ballY + ballSize / 2;
				double offset = ballCenter - paddleCenter;
				double halfPaddle = paddle.getLength()/2;
				//set dx and dy dependent on region of paddle hit
				double angleDeg = 90.0 + 60.0 * (offset/halfPaddle);
				dx = -step * Math.sin(Math.toRadians(angleDeg));
				if (offset < 0) {
					dy = -Math.sqrt(step*step - dx*dx);
				} else if (offset > 0) {
					dy = Math.sqrt(step*step - dx*dx);
				}
			}
		}
	}

	public void KeyPressed(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_W:
			player2FlagUp =true;
			break;
		case KeyEvent.VK_S:
			player2FlagDown = true;
			break;
		case KeyEvent.VK_UP:
			player1FlagUp = true;
			break;
		case KeyEvent.VK_DOWN:
			player1FlagDown = true;
			break;

		}
	}
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_W:
			player2FlagUp = false;
			break;
		case KeyEvent.VK_S:
			player2FlagDown = false;
			break;
		case KeyEvent.VK_UP:
			player1FlagUp = false;
			break;
		case KeyEvent.VK_DOWN:
			player1FlagDown = false;
			break;
		}
	}

	public void Reset() {
		step = Math.sqrt(left*left + up*up);
		dx=right;
		dy=0;
		hits1 = 0;
		hits2 = 0;
		playing = false;
		gameOver = false;
		leftRight = true;
		upDown = false;
		ballX = ballSize;
		ballY = ysize/2;
	}

	@Override
	public void run() {
		while (true) {
			if (playing && !gameOver) {
				try {
					Thread.sleep(1000 / 30); // sleep for 1/30 second
					moveBall();
					try {
						playerOne.movePaddle(player1FlagUp, player1FlagDown);
						playerTwo.movePaddle(player2FlagUp, player2FlagDown);
						repaint();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (dx > 0) {
						checkForHitRight(playerOne);
					} else if (dx < 0) {
						checkForHitLeft(playerTwo);
					}

					if(hits1 == 11 || hits2 == 11){
						gameOver = true;
						repaint();
						playing = false;
					}
				} catch (InterruptedException ie) {
				}

			} else {
				Thread.yield();
			}

		}
	}
}