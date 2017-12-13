package def;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** ThreeButtons and ThreeButtonFrame supplier Classes
 * Author: David D. Riley
 * Date: April, 2004
 *
 *  ThreeButtons supports button events for the ThreeButtonFrame class.
 *  This class is designed to be inherited and its methods overridden.
 */
public abstract class ThreeButtons  {  

    /** The method below is an event handler for button clicks on
     *  the LEFT button of an object of type A3ButtonWindow 
     */
    public abstract void pathAction();

    /** The method below is an event handler for button clicks on
     *  the MID button of an object of type A3ButtonWindow 
     */
    public abstract void birdAction();

    /** The method below is an event handler for button clicks on
     *  the RIGHT button of an object of type A3ButtonWindow 
     */
    public abstract void clearAction();

    /** The class below provides a JFrame that includes three JButtons (left, mid and right).
     *  The event handling of these three buttons will be performed by the leftAction
     *  midAction and rightAction methods of the subclass of ThreeButtons. 
     */
    protected class ThreeButtonFrame extends JFrame implements ActionListener
    {
        private JButton pathButton, birdButton, clearButton ;

        private JTextField velocity;
        private JTextField timeStep;
        private JLabel velLabel;
        private JLabel timeLabel;

        public ThreeButtonFrame(String s, int width, int height) 
        {
            super(s);
            setBounds(100, 100, width, height);
            setVisible(true);

            Container pane = getContentPane();
            pane.setLayout(null);

            pathButton = new JButton(" Project Path ");
            pathButton.setBounds(50, 530, 200, 30);
            pathButton.addActionListener(this);
            pane.add(pathButton, 0);

            birdButton = new JButton(" Birds Fly ");
            birdButton.setBounds(300, 530, 200, 30);
            birdButton.addActionListener(this);
            pane.add(birdButton, 0);

            clearButton = new JButton(" Clear & Reset ");
            clearButton.setBounds(550, 530, 200, 30);
            clearButton.addActionListener(this);
            pane.add(clearButton, 0);

            // set velocity 
            velLabel = new JLabel("Horizontal Velocity : ");
            velLabel.setBounds(0, 0, 150, 40);
            velLabel.setLocation( 60, 480 );
            pane.add( velLabel );
            
            velocity = new JTextField("", 2);
            velocity.setText("20");
            velocity.setBounds( 0, 0, 100, 40 );
            velocity.setLocation( 190, 480 );
            pane.add( velocity );
            
            timeLabel = new JLabel("Time step : ");
            timeLabel.setBounds(0, 0, 150, 40);
            timeLabel.setLocation( 350, 480 );
            pane.add( timeLabel );            
            
            timeStep = new JTextField("", 2);
            timeStep.setText("1.0");
            timeStep.setBounds( 0, 0, 100, 40 );
            timeStep.setLocation( 420, 480 );
            pane.add( timeStep );
            

            pane.repaint();
        }

        /** Event Handler
         *  This method is called whenever any of the three
         *  buttons is clicked   
         */
        public void actionPerformed(ActionEvent e)  
        {
            if (e.getSource() == pathButton)
                pathAction();
            else if (e.getSource() == birdButton)
                birdAction();
            else if (e.getSource() == clearButton)
                clearAction();      
        }

        public int getVelocity()
        {
            int vel = Integer.parseInt( velocity.getText() );
            if( vel >= 0 && vel <= 90 )
            {
                return vel;
            }
            else
            {
                velocity.setText("0");
                return 0;
            }
        }
        public double getTimeStep()
        {
            double ts = Double.parseDouble( timeStep.getText() );
            if( ts < 0.0 || ts > 2.5 )
            {
                timeStep.setText("1.0");
                return 1.0;
            }
            else
            {
                return ts;
            }
        }
    }
}