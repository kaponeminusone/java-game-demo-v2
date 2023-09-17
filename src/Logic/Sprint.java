package Logic;

import java.io.File;


import javax.imageio.ImageIO;

import Objects.Entity;

import java.io.File;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Window.WindowScene;

public class Sprint extends Animation{

    public static Image mario00;
    public static Image mario01;
    public static Image mario02;
    public static Image mario03;
    public static Image mario04;

    public Sprint(Entity entity){
        this.entity = entity;
        movementAnimation(100,0,100,true,true);
        
        int dim = 80;

        try {
            mario00 = ImageIO.read(new File("src/Game/mario/mario00.png")).getScaledInstance(dim,dim,Image.SCALE_DEFAULT);
            mario01 = ImageIO.read(new File("src/Game/mario/mario01.png")).getScaledInstance(dim,dim,Image.SCALE_DEFAULT);
            mario02 = ImageIO.read(new File("src/Game/mario/mario000.png")).getScaledInstance(dim,dim,Image.SCALE_DEFAULT);
            mario03 = ImageIO.read(new File("src/Game/mario/mario001.png")).getScaledInstance(dim,dim,Image.SCALE_DEFAULT);
            mario04 = ImageIO.read(new File("src/Game/mario/mario04.png")).getScaledInstance(dim,dim,Image.SCALE_DEFAULT);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    int state = 0;
    Image lastImage;
    @Override
    protected double equationX(double time) {
        
        if(WindowScene.right){
            
            if(state == 0){
                lastImage = mario00;
                entity.setImage(mario01);
            }

            if(time >= 10){

                if(state == 0 || state == 1){

                    state = 2;
                    entity.setImage(mario00);

                }else if(state == 2){

                    state = 1;
                    entity.setImage(mario01);
                }

                cont = 0;
            }
            
        }else if(WindowScene.left){
            
            if(state == 0){
                lastImage = mario02;
                entity.setImage(mario03);
            }

            if(time >= 10){

                if(state == 0 || state == 1){

                    state = 2;
                    entity.setImage(mario02);

                }else if(state == 2){

                    state = 1;
                    entity.setImage(mario03);
                }

                cont = 0;
            }
            
            

        }else{
            
            entity.setImage(lastImage);
            cont = 0;
            state = 0;

            return 0;
        }

        if(WindowScene.right && WindowScene.left){
            entity.setImage(lastImage);
            state = 0;
            cont = 0;
        }

        return 0;
    }

    @Override
    protected double equationY(double time) {
        // TODO Auto-generated method stub
        return 0;
    }

    
    
}
