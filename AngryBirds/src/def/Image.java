package def;
/*	Image objects are invisible panes for displaying gif or jpeg images 
    Author:  David Riley -- Nov, 2004 */
    
/*	class invariant
    	getWidth() >= 0  AND  getHeight() >= 0  */

import java.awt.Toolkit.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.JComponent;

public class Image extends JComponent implements java.awt.image.ImageObserver {
	private java.awt.Image content;
    private int bestWidth, bestHeight;

// Constructor methods
	/**	post:   an empty image view is instantiated
	 *          and  getX() == 0   and  getY() == 0   
	 *          and  getWidth() == 10  and  getHeight() == 10   */
	public Image()  {
		super();
		setBounds(0, 0, 10, 10);
	} 

	/**	pre:    w >= 0   and   h >= 0
     *  post:   an empty image view is instantiated
	 *          and  getX() == x   and  getY() == y   
	 *          and  getWidth() == w  and  getHeight() == h   */
	public Image(int x, int y, int w, int h)  {
		super();
		setBounds(x, y, w, h);
	} 


	/**	pre:    w >= 0   and   h >= 0
     *  post:   getX() == x   and  getY() == y   
	 *          and  getWidth() == w  and  getHeight() == h
	 *          and  Upon repaint the image displayed will be given by the 
     *               file with complete pathname specified as s 
	 *		         (Note that the file should be a gif, jpeg or png)  */
	public Image(int x, int y, int w, int h, String s)  {
		super();
		setBounds(x, y, w, h);
        setImage(s);
	} 
    
	/**  post:  getX() == x   and  getY() == y   
	 *          and  getWidth() and getHeight() are assigned by the file
	 *          and  Upon repaint the image displayed will be given by the 
     *               file with complete pathname specified as s 
	 *		         (Note that the file should be a gif, jpeg or png)  */
	public Image(int x, int y, String s)  {
		super();
        setImage(s);
		setBounds(x, y, bestWidth, bestHeight);
	} 
    

// Class Methods   

	/**	post:   Upon repaint the image displayed will be given by the 
     *          file with complete pathname specified as s 
	 *		    (Note that the file should be a gif, jpeg or png)  
     *          and bestWidth and bestHeight are assigned by the file
     */
	public void setImage(String s)  {
        java.net.URL url = getClass().getResource(s);  // for applets
        if (url == null)   {
            url = getClass().getResource("/"+s);
            if (url == null)
                try {  // for applications
                    content = ImageIO.read(new File(s));
                    bestWidth = content.getWidth(this);
                    bestHeight = content.getHeight(this);
                } catch(IOException ioe) {
                    ioe.printStackTrace();
                }
            else {
                content = getToolkit().getImage(url);
                bestWidth = content.getWidth(this);
                bestHeight = content.getHeight(this);
            }
        } else {
            content = getToolkit().getImage(url);
            bestWidth = content.getWidth(this);
            bestHeight = content.getHeight(this);
        }
        flush();

	}
    
    public void flush() {
        if (content != null) content.flush();
    }


	public void paint(Graphics g)  {
		g.drawImage(content, 0, 0, getWidth(), getHeight(), this);
	} 

}
