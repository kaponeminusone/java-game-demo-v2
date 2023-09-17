package Logic;
//---------Colaider figure---------
import java.awt.Polygon;
//---------------------------------

/**
 * Creates a Polygon type object with a collision 
 * detection feature with another Polygon.
 * it also allows to move it gradually and 
 * exactly, also to rotate it.
 * 
 * @implNote    This class should not be modified 
 *              unless you know what you are doing.
 * @implSpec    It is possible to inherit from this 
 *              class and create a new way to use the 
 *              qualities of this class
 */
public class Colaider{

    /* Core Atributte */
    private Polygon colaider;

    /* Collaider dimension */
    protected int width;
    protected int height;

    /* Base coordinates of the polygon size */
    protected int[] baseX;
    protected int[] baseY;

    /* Used for translational positioning  */
    private int relativeX;
    private int relativeY;

    /* Rotation angle (in facts)*/
    protected double rotation_angle;

    /**
     * Create a rectangular polygon with the width and 
     * height and the reduction of this.
     * 
     * @param width         width size
     * @param height        height size
     * @param reduction     value of reduction - 1 == Default
     *                       and - value < 1  is the reduction
     * @see createColaider(double)
     */
    public Colaider(int width, int height, double reduction){ 
        this.height = height;
        this.width = width;  
        createColaider(reduction);
    }

    /**
     * Creates a polygon which is customizable.
     * it can have any number of sides, you just
     * have to pass the relationship between the
     * x-axis and the axis in Arrays[], no reduction.
     * 
     * Arrays must have the same number of elements.
     * 
     * @param xpoints  Array of x-axis
     * @param ypoints  Array of y-axis
     * @param npoints  Array size 
     */
    public Colaider(int[] xpoints, int[] ypoints, int npoints){   
        
        colaider = new Polygon(xpoints,ypoints,npoints);
        baseX = xpoints;
        baseY = ypoints;
    }

    /**
     * This method is in charge of creating the reduced
     *  rectangular polygon if desired, this is the method
     *  that must be overridden if a better management of
     *  the creation of the polygon with N sides is desired.
     * 
     * @param reduction     the reduction value < 1;
     * 
     * @implNote    remember that if you override 
     *              this method you must allocate 
     *              the baseXY arrays and allocate
     *              to the collaider creation.
     */

    private void createColaider(double reduction){

        int longPoint = (int)(width*(1-reduction));
        int sizePoint = (int)(height*(1-reduction));

        /* Create the size with center at (0,0) for more adaptability */
        baseX = new int[]{-longPoint,-longPoint,+longPoint,+longPoint};
        baseY = new int[]{-sizePoint,+sizePoint,+sizePoint,-sizePoint};

        colaider = new Polygon(baseX,baseY,4);
        
    }

    /**
     *  Method capable of verifying if two polygons
     *  with N sides are colliding with each other.
     * 
     * <blockquote><pre></pre></blockquote><p>
     *  {@code step 1}: it is detected if any point of each
     *  polygon is inside the other, 
     * 
     * <blockquote><pre></pre></blockquote><p>
     *  {@code step 2}: if it is not possible in this way,
     *  the slopes of each are obtained pair of 
     *  adjacent vertices, that means the equation 
     *  of each edge, 
     * 
     * <blockquote><pre></pre></blockquote><p>
     *  {@code step 3}: different conditions are applied
     *  for each particular case, it returns true if it 
     *  found an intersection point in the limits of each 
     *  segment.
     * 
     * @param target    polygon object
     * @return          returns true or false 
     *                  according to each case
     * @implNote        in the method there is a 
     *                  more detailed explanation
     */
    public boolean isColission(Polygon target){

        /* Step 1: *///--------------------------------
        int[] targetX = target.xpoints;
        int[] targetY = target.ypoints;

        for(int pair = 0; pair < target.npoints; pair++){

            if(colaider.contains(targetX[pair],targetY[pair])){
                return true;
            }
        }

        int[] colaiderX = colaider.xpoints;
        int[] colaiderY = colaider.ypoints;

        for(int pair = 0; pair < colaider.npoints; pair++){

            if(target.contains(colaiderX[pair],colaiderY[pair])){

                return true;
            }
        }
        //-----------------------------------------------

        /* Step 2 and 3: *///----------------------------------
        double[] c2 = new double[target.npoints];
        double[] m2 = new double[target.npoints];

        for(int pair = 0; pair < target.npoints; pair++){

            double x1 = targetX[pair];
            double y1 = targetY[pair];

            double x2 = targetX[(pair+1)%target.npoints];
            double y2 = targetY[(pair+1)%target.npoints];

            m2[pair] = (y2-y1)/(x2-x1);

            c2[pair] =-(m2[pair]*x1) + y1;
            
        }

        for(int pair = 0; pair < colaider.npoints; pair++){

            double x1 = colaiderX[pair];
            double y1 = colaiderY[pair];

            double x2 = colaiderX[(pair+1)%colaider.npoints];
            double y2 = colaiderY[(pair+1)%colaider.npoints];

            double m1 = (y2-y1)/(x2-x1);

            double c1 =-(m1*x1) + y1;
            
            // System.out.println("\n PAIR[" + pair +"] SIDE: (" + x1 + "," + y1 + "," + ") (" + x2 + "," + y2 + ")");

            if(x1 > x2){
                double aux = x1;
                x1 = x2;
                x2 = aux;
            }
            if(y1 > y2){
                double aux = y1;
                y1 = y2;
                y2 = aux;
            }

            for(int compare = 0; compare < target.npoints; compare++){

                double t1 = targetX[compare];
                double t2 = targetX[(compare+1)%target.npoints];

                double r1 = targetY[compare];
                double r2 = targetY[(compare+1)%target.npoints];

                // System.out.println(" COMPARE[" + compare +"] SIDE: [" + t1 + "," + r1 + "] [" + t2 + "," + r2 + "]");
                // System.out.println(" m1: " + m1);
                // System.out.println(" m2: " + m2[compare]);

                if(t1 > t2){
                    double aux = t1;
                    t1 = t2;
                    t2 = aux;
                }
                if(r1 > r2){
                    double aux = r1;
                    r1 = r2;
                    r2 = aux;
                }
                

                double a0 = 0;
                double a1 = 0;
                double b0 = 0;
                double b1 = 0;

                double x = 0;
                double y = 0;

                /*                              PROCESS
                 *
                 * Everything is possible due to the calculation of the slopes.
                 * 
                 * Case 1:
                 * If the slope in both is 0 and Infinite or the same as saying that
                 * they are the same, it is impossible for these two 
                 * segments to be touching each other, that is, they contain 
                 * them in one way or another.
                 * 
                 * Case 2:
                 * if the slope is 0 in the target and in the object it is 1, it means
                 *  that perpendicular to me it is possible that they will intersect.
                 * 
                 * Case 3:
                 * If they are oblique and not parallel, it means that at some point 
                 * they also intersect.
                 * 
                 * After this, the possibilities of oblique and straight lines parallel 
                 * to the axes are combined.
                 * 
                 * And finally, we look at whether the points resulting from 
                 * combining the equations or defining X and Y in other terms 
                 * belong to the same range of the target segment or object.
                 * 
                 * Note: 
                 *      I insert a return in each case,
                 *      so that if it is true, it does not
                 *       continue evaluating until the end.
                 */

                if(m1 == 0 && m2[compare] == 0 || Double.isInfinite(m1) && Double.isInfinite(m2[compare])){
                    // System.out.println("DODGE");
                    continue;
                }

                if(!Double.isInfinite(m1) && m1 != 0){ // ORANGE STAR

                    // System.out.print("ORANGE");

                    if(Double.isInfinite(m2[compare])){
                        // System.out.println(" [PINK]");
                        x = t1;
                        y = (x*m1) + c1;

                        a0 = x1;
                        a1 = x2;

                        b0 = r1;
                        b1 = r2;

                        if(a0 <= x && x<= a1 && b0 <= y && y <= b1){

                            // System.out.println("+ [" + a0 + " < " + x + " < " + a1 + "] [" + b0 + " < " + y + " < " + b1 + "]");
                            return true;
                        }else{
                            continue;
                        }

                    }

                    if(m2[compare] == 0){
                        // System.out.println(" [YELLOW]");

                        y = r1;
                        x = (y - c1)/m1;

                        a0 = t1;
                        a1 = t2;

                        b0 = y1;
                        b1 = y2;

                        if(a0 <= x && x<= a1 && b0 <= y && y <= b1){

                            // System.out.println("+ [" + a0 + " < " + x + " < " + a1 + "] [" + b0 + " < " + y + " < " + b1 + "]");
                            return true;
                        }else{
                            continue;
                        }

                    }

                    if(!Double.isInfinite(m2[compare]) && m2[compare] != 0){
                        // System.out.println(" [CYAN]");
                        
                        x = (c2[compare] - c1)/(m1-m2[compare]);
                        y = (x*m1) + c1;

                        a0 = x1;
                        a1 = x2;

                        b0 = r1;
                        b1 = r2;

                        if(a0 <= x && x<= a1 && b0 <= y && y <= b1){

                            // System.out.println("+ [" + a0 + " < " + x + " < " + a1 + "] [" + b0 + " < " + y + " < " + b1 + "]");
                            return true;
                        }else{
                            continue;
                        }
                    }
                    
                }

                if(m1 == 0){ // BROWN STAR

                    // System.out.print("BROWN");

                    if(Double.isInfinite(m2[compare])){
                        // System.out.println(" [PINK]");
                        y = y1;
                        x = t1;

                        a0 = x1;
                        a1 = x2;

                        b0 = r1;
                        b1 = r2;

                        if(a0 <= x && x<= a1 && b0 <= y && y <= b1){

                            // System.out.println("+ [" + a0 + " < " + x + " < " + a1 + "] [" + b0 + " < " + y + " < " + b1 + "]");
                            return true;
                        }else{
                            continue;
                        }
                    }

                    if(!Double.isInfinite(m2[compare]) && m2[compare] != 0){
                        // System.out.println(" [CYAN]");
                        y = y1;
                        x = (y-c2[compare])/m2[compare];

                        a0 = x1;
                        a1 = x2;

                        b0 = r1;
                        b1 = r2;

                        if(a0 <= x && x<= a1 && b0 <= y && y <= b1){

                            // System.out.println("+ [" + a0 + " < " + x + " < " + a1 + "] [" + b0 + " < " + y + " < " + b1 + "]");
                            return true;
                        }else{
                            continue;
                        }

                    }
                    
                }

                if(Double.isInfinite(m1)){ // PURPLE STAR

                    // System.out.print("PURPLE");

                    if(m2[compare] == 0){
                        // System.out.println(" [YELLOW]");
                        x = x1;
                        y = r1;

                        a0 = t1;
                        a1 = t2;

                        b0 = y1;
                        b1 = y2;

                        if(a0 <= x && x<= a1 && b0 <= y && y <= b1){

                            // System.out.println("+ [" + a0 + " < " + x + " < " + a1 + "] [" + b0 + " < " + y + " < " + b1 + "]");
                            return true;
                        }else{
                            continue;
                        }
                    }

                    if(!Double.isInfinite(m2[compare]) && m2[compare] != 0){
                        // System.out.println(" [CYAN]");
                        x = x1;
                        y = (m2[compare]*x) + c2[compare];

                        a0 = t1;
                        a1 = t2;

                        b0 = y1;
                        b1 = y2;
                        
                        if(a0 <= x && x<= a1 && b0 <= y && y <= b1){

                            // System.out.println("+ [" + a0 + " < " + x + " < " + a1 + "] [" + b0 + " < " + y + " < " + b1 + "]");
                            return true;
                        }else{
                            continue;
                        }
                    }
                    
                }

                

            }
            
        }
        return false;
        //----------------------------------------------
    }

    /**
     * moves the collaider to the exact position entered.
     * @param x position in the x-axis
     * @param y position in the y-axis
     */
    public void moveTo(int x, int y){  //Se mueve a la posicion exacta
        colaider = new Polygon(baseX,baseY,colaider.npoints);
        colaider.translate(x, y);
        relativeX = x;
        relativeY = y;
    }

    /**
     * move the collaider to the position direction gradually.
     * @param x increases position in the x-direction
     * @param y increases position in the y-direction
     */
    public void moveToGradual(int x, int y){ //Posicion donde solo se le suma cierta cantidad
        colaider.translate(x, y);
        relativeX += x;
        relativeY += y;
    }

    /**
     * move and rotate the collaider to the 
     * position in the direction gradually, 
     * but the angle is exact.
     * 
     * this method is used for animations.
     * 
     * @param angle     angle value in degrees
     * @param deltax    increases position in the x-direction
     * @param deltay    increases position in the y-direction
     * 
     */
    public void rotateAndMove(double angle, int deltax, int deltay){

        relativeX+=deltax;
        relativeY+=deltay;

        int N = colaider.npoints;

        int[] newX = new int[N];
        int[] newY = new int[N];
        
        double radiant = Math.toRadians(angle);
        rotation_angle = radiant;

        for(int index = 0; index < N; index++){

            newX[index] = (int) Math.round((Math.cos(radiant)*baseX[index]) - (Math.sin(radiant)*baseY[index]));
            newY[index] = (int) Math.round((Math.sin(radiant)*baseX[index]) + (Math.cos(radiant)*baseY[index]));

        }

        colaider = new Polygon(newX,newY,N);
        colaider.translate(relativeX,relativeY);
    }

    /* returns the polygon object */
    public Polygon getColaider(){
        return colaider;
    }
    

}
