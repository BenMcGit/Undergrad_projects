package def;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/** Rectangle Supplier Class 
 * Author: David D. Riley
 * Date: April, 2004
 */
public class Rectangle extends JComponent  {

    /** post:   getX() == x  and  getY() == y
     *          and  getWidth() == w  and getHeight() == h
     *          and  getBackground() == Color.black
     */
	public Rectangle(int x, int y, int w, int h)  {
        super();
		setBounds(x, y, w, h);
        setBackground(Color.black);
	}

    /** post:   this method draws a filled Rectangle
     *          and  the upper left corner is (getX(), getY()) 
     *          and  the rectangle's dimensions are getWidth() and getHeight()
     *          and  the rectangle's color is getBackground()
     */
    public void paint(Graphics g)  {
       // ((Graphics2D)g).setClip( new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight()) );
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth()-1, getHeight()-1);
        paintChildren(g);
   }

}