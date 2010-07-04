package ballsim.org;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.commons.math.geometry.Vector3D;



/**
 * @author 
 *
 * Don't want any graphics in this project, but sometimes needed to
 * understand and debug problems.
 * 
 */
public class Plotter extends JPanel {

 	private static final long serialVersionUID = -5923733218322952123L;

 	int w,h,r;
 	double scale;
 	List<Event> events = new ArrayList<Event>();
 	Graphics2D g2d;

 	public void generateTestEventsSlide()
 	{
		Event slide = Utilities.getSliding(Vector3D.PLUS_I.scalarMultiply(30),Vector3D.PLUS_I.scalarMultiply(60));
		Event roll = slide.rollingEventFromSliding();
		Event stationary = roll.stationaryEventFromRolling();
		events.add(slide);
		events.add(roll);
		events.add(stationary);		
 	}

 	public void generateTestEventsRoll()
 	{
		Event roll = Utilities.getRolling(new Vector3D(30,10,0));
		Event stationary = roll.stationaryEventFromRolling();
		for(double dt = 0; dt < stationary.t*0.9; dt += stationary.t/5.0)
		{
			events.add(roll.advanceDelta(dt));
		}
		events.add(stationary);		
 	}

 	public void generateTestEvents()
 	{
 		events.clear();
 		generateTestEventsRoll();
 		generateTestEventsSlide();
 	}
 	
 	public void plotEventList(List<Event> events)
 	{
 		for(Event e : events)
 		{
 			plotEvent(e);
 		}
 	}

 	public int scaledX(double x)
 	{
 		return (int)(x/scale) + w/2;
 	}
 	public int scaledY(double y)
 	{
 		return (int)(y/scale) + h/2;
 	}
 	
 	public void plotEvent(Event e)
 	{
        int x = scaledX(e.pos.getX());
        int y = scaledY(e.pos.getY());
        
        
        if (e.state == State.Sliding)
            g2d.setColor(Color.blue);

        if (e.state == State.Rolling)
        	g2d.setColor(Color.red);

        if (e.state == State.Stationary)
        	g2d.setColor(Color.black);

        g2d.drawOval(x-r/2, y-r/2, r, r); 	
        g2d.drawChars(e.state.toString().toCharArray(), 0, 4, x+r, y);

        int xvel = scaledX(e.pos.getX() + e.vel.getX()/10);
        int yvel = scaledY(e.pos.getY() + e.vel.getY()/10);

        int xavel = scaledX(e.pos.getX() + e.angularVel.getX()/10);
        int yavel = scaledY(e.pos.getY() + e.angularVel.getY()/10);

        g2d.drawLine(x, y, xvel, yvel);
        g2d.drawLine(x, y, xavel, yavel);
 	}
 	
 	public void scaleToFit()
 	{
 		double  minx = -Ball.R,
 				maxx = Ball.R,
 				miny = -Ball.R,
 				maxy = Ball.R;
 		
 		for(Event e : events)
 		{
 			System.out.println(e);
 			if (minx > e.pos.getX())
 				minx = e.pos.getX();

 			if (maxx < e.pos.getX())
 				maxx = e.pos.getX();

 			if (miny > e.pos.getY())
 				miny = e.pos.getY();

 			if (maxy < e.pos.getY())
 				maxy = e.pos.getY();
 		} 		
 		
 		double scalex = (maxx-minx) / w;
 		double scaley = (maxy-miny) / h;
 		
 		System.out.println(scalex + "," + scaley);
 		
 		scale = (scalex+scaley)*1.3; 		
 		if (scale==0)
 			scale = 0.05;
 		
        r = (int) (Ball.R/scale);

 	}
 	
	public void paintComponent(Graphics g) 
	{
            super.paintComponent(g); 

            g2d = (Graphics2D) g;

            g2d.setColor(Color.blue);
            Dimension size = getSize();
            Insets insets = getInsets();

            w =  size.width - insets.left - insets.right;
            h =  size.height - insets.top - insets.bottom;
 
            generateTestEvents();
            scaleToFit();
            plotEventList(events);
    		
    }

    public static void main(String[] args) 
    {
        Plotter points = new Plotter();
        JFrame frame = new JFrame("Test plot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(points);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}