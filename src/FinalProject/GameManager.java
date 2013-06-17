package FinalProject;
import javax.imageio.ImageIO; 
import javax.swing.*; 

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage; 
import java.io.IOException; 
import java.net.*; 
import FinalProject.OnePlayer; 
/** 
 *  
 */
  
/** 
 * @authors Sally Hunsberger, Ted Papaiaoannou, Ryan Ottney
 * 
 */
public class GameManager extends JFrame { 
	protected JMenuItem onePlayer = new JMenuItem("1");
	protected JMenuItem twoPlayers = new JMenuItem("2");
  
    /** 
     * @param args 
     */
    private static final long serialVersionUID = 1L; 
      
    public GameManager() { 
        super("Choose Mode"); 
//        OnePlayer panel = new OnePlayer(); 
		JMenuBar bar = new JMenuBar();
		JMenu mode = new JMenu("Mode");
		
		onePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OnePlayer game1 = new OnePlayer();
				
			}			
		});
		
		twoPlayers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Two-player mode");
			}			
		});
		
		mode.add(onePlayer);
		mode.add(twoPlayers);
		bar.add(mode);
		
//        try { 
//            URL url = new URL("http://upload.wikimedia.org/wikipedia/commons/e/e9/Sombrero_Galaxy_in_infrared_light_(Hubble_Space_Telescope_and_Spitzer_Space_Telescope).jpg"); 
//            BufferedImage image = ImageIO.read(url); 
//        } catch(MalformedURLException e) { 
//            // here is where we handle MalformedURLException (from URL constructor) 
//            JOptionPane.showMessageDialog(this, "MalformedURLException"); 
//        } catch(IOException ioe) { 
//            // here is where we handle IOExceptions (from ImageIO.read(...)) 
//            JOptionPane.showMessageDialog(this, "IOException"); 
//        } 
          
        getContentPane().add(bar, BorderLayout.NORTH); 
        pack(); 
    } 
      
    public static void main(String[] args) { 
        // TODO Auto-generated method stub 
        GameManager demo = new GameManager(); 
        demo.setVisible(true); 
    } 
  
}