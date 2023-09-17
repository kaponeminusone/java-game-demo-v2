package Logic;

//--- Target Entity -----------
import Objects.Entity;
//-----------------------------

/**
 * Abstract class to generate an animation by modifying/overwriting 
 * the methods equationX and equationY, contains modifiers of time, 
 * period, activation, repetitions. To run correctly this class must 
 * be subject to a primary update per second.
 * @see movementAnimation(double, double, int, boolean, boolean)
 * @see startAnimation()
 * 
 */
public abstract class Animation {
    
    //------------Primary parameters-----------------
    protected boolean active = false;

    protected double period; 
    protected double timeActivation;

    protected int repetitions;
    protected int repetitionDown = 0;
    //-----------------------------------------------

    /**
     * This method contains all the function for 
     * the movement in the X-axis mainly
     * 
     * @param time  period active time update counter.
     * @return      It can return a value to be used in 
     *              another method of movement or another way
     */
    protected abstract double equationX(double time);

    /**
     * This method contains all the function for 
     * the movement in the Y-axis mainly
     * 
     * @param time  period active time update counter.
     * @return      It can return a value to be used in 
     *              another method of movement or another way
     */

    protected abstract double equationY(double time);

    protected boolean startWith = false;

    /**
     * Entity to which the animation changes 
     * will be applied, sprite among others.
     */
    protected Entity entity = null;

    /** 
     * Method for the main initialization of the animations 
     * that inherit from the class, it is modifiable since 
     * more attributes can be added. 
     * 
     * <blockquote><pre></pre></blockquote><p>
     * All attributes of this class method are inherited.
     * 
     * @param period            Period of time in which the action is executed - period > 0
     * @param timeActivation    Delay before the action is executed again - value >= 0
     * @param repetitions       Number of repetitions for each completed period - value > 0
     * @param startWith         Whether the animation should start immediately 
     *                          or wait for the activation time per period
     * @param active            animation on or off
     * 
     */

    public void movementAnimation(double period, double timeActivation, int repetitions, boolean startWith, boolean active){

        this.period = period;
        this.timeActivation = timeActivation;
        this.repetitions = repetitions;
        this.active = active;
        this.startWith = startWith;
        
    }

    //--------------------------------------------------------
    protected static final int ups = 60;
    private final double NANO_SECONDS = 1000000000;

    private double timerStarted = 0;

    private int contador = 0; // Variable not important
    protected int cont = 0;

    private double timePeriod = 0;
    //---------------------------------------------------------

    /**
     *  The initialization of the animation begins and this 
     *  class is minimally manipulable for the proper 
     *  functioning of the animation, implementing in 
     *  these the waiting times, active periods, repetitions
     *  and unique actions.
     * 
     */

    public void startAnimation(){
        /* hasty leave */
        if(repetitionDown == repetitions || !active){
            return;
        }

        /* Starts the timer for the animation execution. */
        if(timerStarted == 0){
            timerStarted = System.nanoTime();
        }

        /* Condition to execute after a certain time */
        if((System.nanoTime() - timerStarted)/NANO_SECONDS >= timeActivation || startWith == true){
            
            if(timePeriod == 0){
                contador++;
                System.out.println("[" + contador + "] + "  + entity.getName());
                
                timePeriod = System.nanoTime();
            }

            if((System.nanoTime() - timePeriod)/NANO_SECONDS <= period && period != 0){
                
                /* Run the animation 60*period times */
                if(cont > period*ups){
                    return;
                }

                /* cont is a "ups timer" */

                equationX(cont);
                equationY(cont);
                cont++; 

            }else{

                /* the variables are reset to zero for a new activation time*/

                cont = 0;

                startWith = false;
                repetitionDown++;
                timePeriod = 0;
                timerStarted = 0;
            }

            /* Unique execution */ 
            if(period == 0){
                
                equationX(0);
                equationY(0);
            }

        }

    }


    /**
     * Stop the animation.
     */
    public void stopAnimation(){
        active = false;
        repetitionDown = 0;
    }

    //--------------------------------------------------------

    /* Variables and Methods not relevant */

    protected int deltaY = 0;
    protected void deltaY(double y){
        deltaY = (int)y;
    }

    protected void deltaYMovement(double y){
        deltaY = (int)y - deltaY;
    }

    protected int deltaX = 0;
    protected void deltaX(double x){
        deltaX = (int)x;
    }
    protected void deltaXMovement(double x){
        deltaX = (int)x - deltaY;
    }
    
    

}
