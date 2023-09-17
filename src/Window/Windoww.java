package Window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.BasicStroke;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Windoww extends JFrame{
    
    private int WIDTH = 300;
    private int HEIGHT = 300;
    private Polygon p = new Polygon();

    int[] graphic = new int[60];
    Queue<Integer> tail = new LinkedList<Integer>();
    
    JPanel a = new JPanel(){
        
        @Override
        public void paint(Graphics g){
            super.paint(g);

            Graphics2D g2d = (Graphics2D)g.create();
            g2d.setStroke(new BasicStroke(2.0f));
            double promedio = 0;
            int max = 0;
            int min = 9999999;
            int xmax = 0;
            int xmin = 0;
            int maxvalue = 0;
            int minvalue = 0;

            Integer[] c = new Integer[60];

            c = tail.toArray(c);
            
            for(int x = 0; x < 60; x++){
                promedio+= c[x];
                
            }
            
            promedio/=60;
            
            for(int x = 0; x < 59; x++){
                int valueof = (int)((double)(150*(double)c[x])/promedio);
                int valueof2 = (int)((double)(150*(double)c[x+1])/promedio);
                g2d.drawLine(x*5,300-valueof,(x+1)*5,300-valueof2);
                
              

                if(c[x] > max){
                    max = c[x];
                    
                    xmax = x*5;
                }
                if(c[x] < min){
                    min = c[x];
                    
                    xmin = x*5;
                }
            }
            
            int maxC = (int)((double)(150*(double)max)/promedio);
            int minC = (int)((double)(150*(double)min)/promedio);
            
            g.setColor(Color.gray);
            g.drawLine(0,300-maxC,300,300-maxC);
            g.drawLine(0,300-minC,300, 300-minC);
            
            g.drawString("" + (max*4),xmax-10,300-maxC-20);
            g.drawString("" + (min*4),xmin-10,300-minC+20);
            
            g.setColor(new Color(57, 172, 115));
            g.drawLine(0,150,300, 150);
            // g.drawPolygon(p);
        }

    };

    public Windoww(Point point, int offy){

        // this.a = a;
        
        for(int x = 0; x < 60; x++){
            tail.offer(60/4);
        }

        setSize(new Dimension(WIDTH,HEIGHT));
        setLocation((int)point.getX()-300,(int)point.getY()+offy);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        initComponents();

        setVisible(true);
    }


    public void setGraphicScreen(int value){

        tail.poll();
        tail.offer(value);
        a.repaint();

    }

    private void initComponents() {
        add(a);
    }

}
