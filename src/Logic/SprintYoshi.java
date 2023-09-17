package Logic;

import java.io.File;


import javax.imageio.ImageIO;

import Objects.Entity;

import java.io.File;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Window.WindowScene;

public class SprintYoshi extends Animation{

    public static Image yoshi00;
    public static Image yoshi01;
    public static Image yoshi02;
    public static Image yoshi03;
    public static Image yoshi04;

    public SprintYoshi(Entity entity){
        this.entity = entity;
        movementAnimation(100,0,100,true,true);
        
        int dim = 160;

        try {
            yoshi00 = ImageIO.read(new File("src/Game/yoshi/yoshi00.png")).getScaledInstance(dim,dim,Image.SCALE_DEFAULT);
            yoshi01 = ImageIO.read(new File("src/Game/yoshi/yoshi01.png")).getScaledInstance(dim,dim,Image.SCALE_DEFAULT);
            yoshi02 = ImageIO.read(new File("src/Game/yoshi/yoshi02.png")).getScaledInstance(dim,dim,Image.SCALE_DEFAULT);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    int state = 0;
    Image lastImage;
    @Override
    protected double equationX(double time) {
        
        if(WindowScene.keyD){
            
            if(state == 0){
                lastImage = yoshi00;
                entity.setImage(yoshi01);
            }

            if(time >= 6){

                if(state == 0 || state == 1){

                    state = 2;
                    entity.setImage(yoshi02);

                }else if(state == 2){

                    state = 3;
                    entity.setImage(yoshi01);
                }else if(state == 3){
                    state = 1;
                    entity.setImage(yoshi00);

                }

                cont = 0;
            }
            
        }else if(WindowScene.keyA){
            
            if(state == 0){
                // lastImage = yoshi02;
                // entity.setImage(yoshi03);
            }

            if(time >= 10){

                if(state == 0 || state == 1){

                    state = 2;
                    // entity.setImage(mario02);

                }else if(state == 2){

                    state = 1;
                    // entity.setImage(mario03);
                }

                cont = 0;
            }
            
            

        }else{
            
            entity.setImage(lastImage);
            cont = 0;
            state = 0;

            return 0;
        }

        if(WindowScene.keyD && WindowScene.keyA){
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
