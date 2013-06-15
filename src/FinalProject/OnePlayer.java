/** 
 *  
 */
package FinalProject;
  
import java.awt.*; 
import java.awt.image.BufferedImage; 
import java.io.IOException; 
import java.net.MalformedURLException; 
import java.net.URL; 
  
import javax.imageio.ImageIO; 
import javax.swing.JOptionPane; 
import javax.swing.JPanel; 
  
/** 
 * @author rto5021 
 * 
 */
public class OnePlayer extends JPanel implements Runnable { 
    protected BufferedImage image; 
    protected int x; 
      
    public OnePlayer() { 
        super(); 
        System.out.println("Made it here");
        this.setPreferredSize(new Dimension(640, 510)); 
        try { 
            URL url = new URL("http://upload.wikimedia.org/wikipedia/commons/e/e9/Sombrero_Galaxy_in_infrared_light_(Hubble_Space_Telescope_and_Spitzer_Space_Telescope).jpg"); 
            image = ImageIO.read(url); 
        } catch(MalformedURLException e) { 
            // here is where we handle MalformedURLException (from URL constructor) 
            JOptionPane.showMessageDialog(this, "MalformedURLException"); 
        } catch(IOException ioe) { 
            // here is where we handle IOExceptions (from ImageIO.read(...)) 
            JOptionPane.showMessageDialog(this, "IOException"); 
        } 
        x = 0; 
        Thread t = new Thread(this); 
        t.start(); 
        System.out.println("somehow made it here");
    } 
  
    /** 
     *  
     */
    private static final long serialVersionUID = 1L; 
      
    public void paintComponent(Graphics _g) { // overriding parent component implementations 
        super.paintComponent(_g); 
        Graphics2D g = (Graphics2D)_g; 
          
        // Draw Text 
        g.drawString("This is my custom Pong Game!", 10, 20); 
        g.drawImage(image, x, 30, 640, 480, null); 
    } 
  
    @Override
    public void run() { 
        while(true) { 
            try { 
                Thread.sleep(1000/30); // sleep for 1/30 second 
                x += 1; 
                if (x > 640) x = 0; 
                repaint(); 
            } catch (InterruptedException ie) { 
                  
            } 
        } 
    }    
}