package Logic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import Objects.Entity;

public class ScreenPane extends JPanel{
    
    ArrayList<ArrayList<Entity>> entities = new ArrayList<>();
    //prueba

    public ScreenPane(int layers){
        super();

        for(int x = 0; x <= layers+1; x++){
            entities.add(new ArrayList<Entity>());
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for(ArrayList<Entity> e: entities){

            for(Entity entity: e){
                entity.paint(g.create());
            }

        }
        
        //---------------for proofs----------------
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.setColor(new Color(1,1,1,0.3f));
        g2d.drawLine(0,300, 800,300);
        g2d.drawLine(400,0, 400,600);
        //------------------------------------------
    }

    public void startAnimations(){

        for(ArrayList<Entity> e: entities){

            for(Entity entity: e){    
                for(Animation anima: entity.getAnimations()){
                    anima.startAnimation();
                }
            }

        }

    }

    public void addEntity(Entity e){
        entities.get(e.getLayer()).add(e);

    }

    public void removeEntity(Entity e){
        entities.get(e.getLayer()).remove(e);
    }

    public void insertEntity(Entity e, int index){

        entities.get(e.getLayer()).add(index, e);

    }

}
