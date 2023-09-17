package Logic;

import Objects.Entity;
import Window.WindowScene;
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Jump extends Animation{

    Image mario04;
    Image mario004;
    Image mario00;
    Image mario000;

    public Jump(Entity entity){
        this.entity = entity;

        int dim = 80;

        try {
            
            mario04 = ImageIO.read(new File("src/Game/mario/mario04.png")).getScaledInstance(dim,dim,Image.SCALE_DEFAULT);
            mario004 = ImageIO.read(new File("src/Game/mario/mario004.png")).getScaledInstance(dim,dim,Image.SCALE_DEFAULT);
            mario00 = ImageIO.read(new File("src/Game/mario/mario00.png")).getScaledInstance(dim,dim,Image.SCALE_DEFAULT);
            mario000 = ImageIO.read(new File("src/Game/mario/mario000.png")).getScaledInstance(dim,dim,Image.SCALE_DEFAULT);
            
            movementAnimation(0.5,0,10000, true,false);
        } catch (Exception e) {
            
        }
       
    }

    @Override
    protected double equationX(double time) {
        return 0;
    }

    public void setActive(){
        active = true;
        repetitionDown = 0;
    }
    public boolean isActive(){
        return active;
    }


    int state = 0;

    @Override
    protected double equationY(double time) {
        
        double x = time;
        double h = (ups*period)/2;
        double k = 150; 
        
        double movementY = -k/Math.pow(h,2)*Math.pow(x, 2) + (2*k*x)/h;
        deltaYMovement(movementY);

        entity.getColaiderClass().rotateAndMove(0,0,-deltaY);
        entity.setX(entity.getX());
        entity.setY(entity.getY() -deltaY);
        
        if(WindowScene.lastKey == KeyEvent.VK_LEFT){
            entity.setImage(mario004);
        }else if(WindowScene.lastKey == KeyEvent.VK_RIGHT){
            entity.setImage(mario04);
        }

        deltaY(movementY);
        
        if(time >= 60*period){
            
            if(WindowScene.left){
                entity.setImage(mario000);
            }else if(WindowScene.right){
                entity.setImage(mario00);
            }

            active = false;
        }

        return 0;
    }
    
}
