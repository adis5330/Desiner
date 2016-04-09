
package Controllers;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class RectangleController extends JPanel{
    
    Point startDrag, endDrag;
    ArrayList<Rectangle> shapes = new ArrayList<>();
    private boolean paint = true;
    
    public RectangleController() {
        
        this.setPreferredSize(new Dimension(1000,600));
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.red));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(getPaint()){
                    startDrag = new Point(e.getX(), e.getY());
                    endDrag = startDrag;//TODO
                    System.out.println("Click press");
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(getPaint()){
                    Rectangle r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
                    shapes.add(r);
                    startDrag = null;
                    endDrag = null;
                    repaint();
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(getPaint()){
                    endDrag = new Point(e.getX(), e.getY());
                    repaint();
                }
            }
        });
    }
    
    private Rectangle makeRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }  
    
    private void paintBackground(Graphics2D g2){
        g2.setPaint(Color.LIGHT_GRAY);
        for (int i = 0; i < getSize().width; i += 10) {
            Shape line = new Line2D.Float(i, 0, i, getSize().height);
            g2.draw(line);
        }

        for (int i = 0; i < getSize().height; i += 10) {
            Shape line = new Line2D.Float(0, i, getSize().width, i);
            g2.draw(line);
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintBackground(g2);
        int colorIndex = 0;
        g2.setStroke(new BasicStroke(2));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
        for (Shape s : shapes) {
            g2.setPaint(Color.BLACK);
            g2.draw(s);
        }
        if (startDrag != null && endDrag != null) {
            g2.setPaint(Color.LIGHT_GRAY);
            Shape r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
            g2.draw(r);
        }
    }
    
    public boolean getPaint(){
        return this.paint;
    }
    
    public void setPaint(boolean paint){
        this.paint = paint;
    }
    
    public ArrayList<Rectangle> getShapes(){
        return shapes;
    }
}
