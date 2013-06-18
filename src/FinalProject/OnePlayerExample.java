/** 
 *  
 */
package FinalProject;
  
import java.awt.*; 

import java.awt.event.KeyEvent; 
import java.util.Random;
  
import javax.swing.JPanel; 
  
/** 
 * @author rto5021 
 * 
 */
public class OnePlayerExample extends JPanel implements Runnable {  
    
    //set screen size
    int xsize = 800; int ysize = 600;
    //set object sizes
	int paddleWidth = 20; int paddleLength = 100;
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
	boolean playing = false, gameOver;
	boolean leftRight = true;
	boolean upDown = false;
	Random rand = new Random();
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
		g.setColor(Color.black);
		g.fillRect(ballX, ballY, ballSize, ballSize);
		
		g.fillRect(paddleX, paddleY, paddleWidth, paddleLength);
		
		g.drawString("Number of Hits: "+hits, xsize/2 - 100, 20);
		g.drawString("Number of Misses: "+misses, xsize/2 + 100, 20);
		
		if(gameOver)
			g.drawString("GAME OVER", xsize/2, 60);
    } 
    
    public void start(){
         playing = true;
    }
    
    public void stop(){
        playing = false;
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
		
		ballX+=dx; ballY+=dy;
		positionBall(ballX,ballY);
}

protected void positionBall(int nx, int ny)
{
	ballX = nx;
	ballY = ny;
	this.width=this.getWidth();
	this.height=this.getHeight();
	repaint();
}


protected void movePaddle(){
	if(playerFlagUp == true && paddleY>=0)
		paddleY+=down;
	if(playerFlagDown == true && paddleY<=(this.getHeight()-paddleLength))
		paddleY+=up;
	positionPaddle(paddleX, paddleY);
}

protected void positionPaddle(int x, int y){
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
  
    @Override
    public void run() { 
        while(true) { 
	        if (playing){
	        	try {
					Thread.sleep(1000/30); //sleep for 1/30 second
					moveBall();
					movePaddle();
					
					if (dx>0) checkForHit();
						
					if(hits == 11 || misses == 11){
						playing =false;
						gameOver = true;
						repaint();
					}	
				} catch (InterruptedException ie) {	
				}
				
				
	        } else {
	        	Thread.yield();
	        }
			
        } 
    }    
}
