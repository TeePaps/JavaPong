/**
 * 
 */
package FinalProject;

import java.awt.Graphics2D;

/**
 * @author ted
 *
 */
public class Paddle {
	
	private int Width = 20;
	private int Length = 100;
	private int ScreenWidth, ScreenHeight;
	private int X;
	private int Y;
	private int velY;
	private int DirY;
	
	public Paddle(int screenWidth, int screenHeight, int player) {
		ScreenWidth = screenWidth;
		ScreenHeight = screenHeight;
		if (player == 1) {
			X = ScreenWidth - Width;
			Y = ScreenHeight/2;
		} else {
			X = 0;
			Y = ScreenHeight/2;
		}
		
		setPaddleVel(10);
	}
	
	public void drawPaddle(Graphics2D g) {
		//g.fillRect(X, Y, Width, Length);
		g.fillRoundRect(X, Y, Width, Length, 7, 7);
	}
	
	public void setPaddleVel(int vel) {
		velY = vel;
	}
	
	protected void movePaddle(boolean playerFlagUp, boolean playerFlagDown){
		if(playerFlagUp == true && Y>=0)
			Y-=velY;
		if(playerFlagDown == true && Y<=(ScreenHeight-Length))
			Y+=velY;
		positionPaddle(X, Y);
	}

	protected void positionPaddle(int newX, int newY){
		X = newX;
		Y = newY;
	}

	public int getY() {
		return Y;
	}
	
	public int getX() {
		return X;
	}

	public int getLength() {
		return Length;
	}

	public int getWidth() {
		return Width;
	}
	
	public void setLength(int length) {
		Length = length;
	}
}
