/** 
 *  
 */
package FinalProject;
  
import java.awt.*; 

import java.awt.event.KeyEvent; 
import java.sql.SQLException;
import java.util.Random;
  
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel; 
  
/** 
 * @authors Ryan Ottney, Sally Hunsberger, Ted Papaioannou 
 * 
 */
public class OnePlayerExample extends JPanel implements Runnable {  
    
    //set screen size
    int xsize = 800; int ysize = 600;
    //set object sizes
	int paddleWidth = 20;
	int paddleLength = 100;
	int ballSize = 20;

	//initialize positions
	private int ballX = ballSize, ballY = ysize/2, 
				paddleX = xsize - paddleWidth, paddleY = ysize/2;

	//define speed of movement
	int right = 10; 
	int left = -10;
	int up = 10;
	int down = -10;
	double step = Math.sqrt(left*left + up*up);
	double dx=right, dy=0; //use to move ball at different angles
	int width, height;
	int hits, misses;
	boolean playerFlagUp, playerFlagDown; //use to move paddle
	boolean playing = false;
	boolean gameOver = false;
	boolean leftRight = true;
	boolean upDown = false;
	Random rand = new Random();

	boolean clearScreen = true;
	int score = 0;

	// difficulty
	public static int difficulty = 1;
    //*** end of definitions ***

    public OnePlayerExample() { 
        super();
        this.setPreferredSize(new Dimension(xsize, ysize)); 

        Thread t = new Thread(this);
		t.start();
    }
    
    private static final long serialVersionUID = 1L; 
    
    public void paintComponent(Graphics _g) { //overriding parent component implementations
    	super.paintComponent(_g); 
    	Graphics2D g = (Graphics2D)_g;
        
        setOpaque(false);
        if (!clearScreen) {
        	setOpaque(false);
        	g.setColor(Color.black);
        	g.fillRect(ballX, ballY, ballSize, ballSize);

        	g.fillRect(paddleX, paddleY, paddleWidth, paddleLength);

        	g.drawString("Number of Hits: "+hits, xsize/2 - 100, 20);
        	g.drawString("Number of Misses: "+misses, xsize/2 + 100, 20);

        	if(gameOver)
			g.drawString("GAME OVER", xsize/2, 60);
        }
    } 
    
    public void start(){
    	if (gameOver) {
    		Reset();
    	}
    	if (difficulty > 2) {
    		paddleLength = 60;
    	}
        playing = true;
        clearScreen = false;
    }
    
    public void stop(){
        playing = false;
        clearScreen = true;
        repaint();
    }
    
    protected void moveBall() {
		if (dx>0 && ballX>=xsize-ballSize) { //ball at player end of court
			dx = left;
			misses++;
		} else if (dx<0 && ballX<=0){ //ball at server end of court
			dx = right;
			// select up or down
			if (rand.nextBoolean()) {
				dy = up;
			}
			else {
				dy = down;
			}
		}

		if (dy>0 && ballY>=ysize-ballSize) {
			dy = down;
		} else if (dy<0 && ballY<=0) {
			dy = up;
		}

		// increase ball speed for difficulty "medium" and "hard"
		if (difficulty > 1) {
			dx = dx * 1.4;
			dy = dy * 1.4;
		}

		ballX+=dx; ballY+=dy;
		positionBall(ballX,ballY);

		// reset ball speed (each time through) for difficulty "medium" and "hard"
		if (difficulty > 1) {
			dx = dx / 1.4;
			dy = dy / 1.4;
		}
}

protected void positionBall(int nx, int ny) {
	ballX = nx;
	ballY = ny;
	this.width=this.getWidth();
	this.height=this.getHeight();
	repaint();
}


protected void movePaddle() {
	if(playerFlagUp == true && paddleY>=0)
		paddleY+=(down * 2);
	if(playerFlagDown == true && paddleY<=(this.getHeight()-paddleLength))
		paddleY+=(up * 2);
	positionPaddle(paddleX, paddleY);
}

protected void positionPaddle(int x, int y) {
	this.paddleX = x;
	this.paddleY = y;
	repaint();
}

protected  void checkForHit(){
	if (ballX>=(paddleX-ballSize)) { //check for a hit
		if (ballY>=paddleY-ballSize+1 && ballY<=paddleY+paddleLength-1) {
			hits++;
			dx=left;
			dy=0; // this is a direct hit
			int paddleCenter = paddleY + paddleLength/2;
			int ballCenter = ballY + ballSize/2;
			int offset = ballCenter - paddleCenter;
			int delta = paddleLength/10;
			//set dx and dy dependent on region of paddle hit
			if (offset < 0) {
				if (Math.abs(offset) > 4*delta) {
					dx = -step * Math.sin(Math.toRadians(30.0));
					dy = -Math.sqrt(step*step - dx*dx);
				} else if (Math.abs(offset) > 2*delta) {
					dx = -step * Math.sin(Math.toRadians(60.0));
					dy = -Math.sqrt(step*step - dx*dx);
				}
			} else if (offset > 0) {
				if (Math.abs(offset) > 4*delta) {
					dx = -step * Math.sin(Math.toRadians(150.0));
					dy = Math.sqrt(step*step - dx*dx);
				} else if (Math.abs(offset) > 2*delta) {
					dx = -step * Math.sin(Math.toRadians(120.0));
					dy = Math.sqrt(step*step - dx*dx);
				}
			}
			//System.out.format("\noffset is %d", offset);
			//System.out.format("\ndx is %f, dy is %f", dx, dy); 

		}
	}	
}

public void KeyPressed(KeyEvent e){
	switch(e.getKeyCode()){
	case KeyEvent.VK_UP:
		playerFlagUp = true;
		break;
	case KeyEvent.VK_DOWN:
		playerFlagDown = true;
		break;

	}
}

public void keyReleased(KeyEvent e){
	switch(e.getKeyCode()){
	case KeyEvent.VK_UP:
		playerFlagUp = false;
		break;
	case KeyEvent.VK_DOWN:
		playerFlagDown = false;
		break;
	}
}

public void Reset() {
	right = 10; 
	left = -10;
	up = 10;
	down = -10;
	step = Math.sqrt(left*left + up*up);
	dx=right;
	dy=0;
	hits = 0;
	misses = 0;
	playing = false;
	gameOver = false;
	leftRight = true;
	upDown = false;
	ballX = ballSize;
	ballY = ysize/2;
	paddleX = xsize - paddleWidth;
	paddleY = ysize/2;
}

protected void highScore() throws ClassNotFoundException, SQLException {
	JFrame frame = new JFrame("Game Over");
    // prompt the user to enter their initials
    String name = JOptionPane.showInputDialog(frame, "Enter your initials");
    
    // update high score list with and score
    MySQLConnector.addNewHighScore(name, score);
}
  
    @Override
    public void run() { 
        while(true) { 
	        if (playing && !gameOver){
	        	try {
					Thread.sleep(1000/30); //sleep for 1/30 second

					//if (!gameOver) {
						moveBall();
						movePaddle();
						if (dx>0) checkForHit();

						if(misses == 3){
							gameOver = true;
							score = hits * difficulty;
							highScore();
							repaint();
							playing = false;
						}
					//}
				} catch (InterruptedException ie) {	
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


	        } else {
	        	Thread.yield();
	        }	
        } 
    }    
}
