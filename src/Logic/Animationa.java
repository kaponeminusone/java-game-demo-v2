package Logic;

import Objects.Entity;

public class Animationa extends Animation {

    public Animationa(Entity entity){
        this.entity = entity;
        movementAnimation(1,3,20, true,true);
    }

    private double valueK = 150;
    public void setValueK(double value){
        valueK = value;
    }

    @Override
    protected double equationX(double time) {
        return 0;
    }

    int state = 0;
    
    @Override
    protected double equationY(double time) {

        double x = time;
        double h = (ups*period)/2;
        double k = valueK;

        double movementY = -k/Math.pow(h,2)*Math.pow(x, 2) + (2*k*x)/h;
        deltaYMovement(movementY);

        double slope = (-2*k*x)/Math.pow(h, 2) + (2*k)/h; 
        double angle = Math.toDegrees(Math.atan(slope));

        switch(state){

            case 0: angle*=-1; 
            break;
            case 1: angle*=1;
            break;
        }

        //MEJORAR----------------------------

        if(x >= ups*period){

            switch(state){

                case 0: 
                    state = 1;

                    if(timeActivation == 0){
                        break;
                    }
                    angle = 90;
                    entity.setAngle(angle);
                break;
                case 1: 
                    state = 0;
                    if(timeActivation == 0){
                        break;
                    }
                    angle = -90;
                    entity.setAngle(angle);
                break;

            }

            // System.out.println("- " + x + "[" + angle+ "]");
        }

        //Hacer una funcion mejor. si o si
        // if(state == 0 && x >= ups*period){
            
        //     angle = 90;
        //     entity.angle = Math.atan(90);
        //     state = 1;
        // }else if(state == 1 && x >= ups*period){
            
        //     angle = -90;
        //     entity.angle = -Math.atan(90);
        //     state = 0;
        // }
        //-------------------------------
        entity.getColaiderClass().rotateAndMove(angle,0,-deltaY);
        entity.setAngle(angle);
        entity.setX(entity.getX());
        entity.setY(entity.getY() -deltaY);

        // delta = -(int)movementY;

        if(x >= ups*period){
            // System.out.println("[Morado][X]:      ("+ entity.getX() +","+ entity.getY() +")");
        }

        deltaY(movementY);
        return 0;
    }
    
}
