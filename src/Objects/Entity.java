package Objects;

//----------- Images import -------
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
//---------------------------------

//------------ Render -------------
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
//---------------------------------

//------------ Colisisions --------
import Logic.Colaider;
import java.util.ArrayList;
import java.awt.Polygon;
//---------------------------------

//----------- Animations -------
import Logic.Animation;
//------------------------------
/**
 * Class designed for inheritance and modification for the creation of various entities.
 * Entity is for drawing, moving, rotating, getting collision, and customizing.
 * @see ScreenPane
 * @see Animation
 * @see Colaider
 */

public class Entity{

    private Image sprite;
    private double x;
    private double y;

    /* is the width and height modified by the view percentage*/
    private double newWidth = 0;
    private double newHeight = 0;

    /* Identifier by name */
    private String name = "NONE";

    /**
     * Create an Entity at the xy position in the 
     * {@code ScreenPane} with a scaling of its image.
     * It is possible to create the entity and then 
     * modify its values ​​with the methods included 
     * in this class. 
     * <blockquote><pre></pre></blockquote><p>
     * For greater control and differentiation, a new class 
     * inherited from this should be made.
     * 
     * @param x       x-axis position
     * @param y       y-axis posittion
     * @param view    percentage in which the image 
     *                is seen, that is, how big it is.
     *                value > 0, 100 is the default
     * @param path    image path
     */
    public Entity(double x, double y,double view,String path){
        newWidth = 0;
        newHeight = 0;

        try {

            BufferedImage load_image = ImageIO.read(new File(path));
            //----------------------------------------------------------
            // Redimencionar de manera facil para mejor vista de carga.
            newWidth = (load_image.getWidth()*(view/100));
            newHeight = (load_image.getHeight()*(view/100));
            sprite = load_image.getScaledInstance((int)newWidth,(int)newHeight,Image.SCALE_DEFAULT);
            //----------------------------------------------------------

        } catch (IOException e){
            System.out.println(" Can't load image + [" + path + "]");
        }

        this.x = x;
        this.y = y;

        initEntity();
    }

    /**
     *  Private method of the class that acts as 
     *  an initialization intermediary in it.
     */
    private void initEntity(){

        createPolygon();

    }

    //----------- Collision Management etc.. ---------

    private Colaider colaider;

    /**
     * Create a collaider which will have rectangular
     *  dimensions depending on the height and width 
     *  of the image
     */
    private void createPolygon(){

        colaider = new Colaider((int)newHeight/2,(int)newWidth/2,0.2);
        colaider.moveTo((int)x,(int)y);

    }
    
    /* SOME TESTING *///-----------------------
 
    private Color changeColor(int x){

        Color color = Color.BLACK;

        switch(x){

            case 0: color = Color.BLUE;
            break;
            case 1: color = Color.GREEN;
            break;
            case 2: color = Color.RED;
            break;
            case 3: color = Color.YELLOW;
            break;

        }

        return color;
    }

    private Color changeColor(char x){

        Color color = Color.BLACK;

        switch(x){

            case 'A': color = Color.MAGENTA;
            break;
            case 'B': color = Color.ORANGE;
            break;
            case 'C': color = Color.LIGHT_GRAY;
            break;
            case 'D': color = Color.PINK;
            break;

        }

        return color;
    }

    private StringBuffer colissionWith = new StringBuffer();

    /**
     * Add a new index that is a collision character,
     *  that is, collision differentiator.
     * 
     * <blockquote><pre>
     * Entity sora = new Entity...;
     *Entity donald = new Entity...;
     * 
     *     sora.addColissionWith('X');
     *     donald.addColissionWith('X');
     *  
     * </pre></blockquote><p>
     * after that
     * <blockquote><pre>
     * sora.colissionEntity.add(donald);
     * </pre></blockquote><p>
     * 
     * sora is then able to confirm if donald is colliding with him.
     * 
     * @param with  group identifier character
     * @see ArrayList<Entity> colissionEntity
     * @see thereColissionWith()
     */

    public void addColissionWith(char with){
        colissionWith.append(with);
    }
    
    /**
     * {@code ArrayList} type attribute to add the different 
     * entities which will confirm the collision if 
     * it is in a group already added
     */
    public ArrayList<Entity> colissionEntity = new ArrayList<Entity>();

    /**
     * detects if there is a collision with the entity 
     * if it is in the same group and executes a function
     *  passing it which group it collided with.
     * 
     * @return returns character '\0' 
     *         - nothing for now (no avaliable)
     * @see actionForColission()
     * @see addColissionWith(char)
     * @see ArrayList<Entity> colissionEntity
     */
    public char thereColissionWith(){

        char with = '\0';

        int len = colissionWith.length();

        for(Entity i: colissionEntity){

            for(int x = 0; x < len; x++){

                if(i.colissionWith.indexOf(String.valueOf(colissionWith.charAt(x))) != -1){
                
                    if(isColission(i.getColaider())){
                        /* Available for changes */
                        actionForColission(colissionWith.charAt(x));
                    }
                }
            }

        }

        return with;
    }
    
    private void actionForColission(char c){

        colorColission = changeColor(c);
    }
        
    //--------------------------------------------------
    
    
    /* On-Screen Graphics *///--------------------------
    
    /* variable to test, shows the Colaider with different colors */
    boolean showColission = false;  
    Color colorColission;
    
    private int layer = 0;
    /**
     * Each entity can override this method to be drawn in its own way.
     * @param gb  requeried graphic component
     */
    public void paint(Graphics gb){

        Graphics2D g = (Graphics2D)gb;
        g.setStroke(new BasicStroke(2f));

        if(showColission == true){

            int N = colaider.getColaider().npoints;
            int[] xpoints = colaider.getColaider().xpoints;
            int[] ypoints = colaider.getColaider().ypoints;

            for(int x = 0; x < N ; x++){

                int x0 = xpoints[x%N];
                int y0 = ypoints[x%N];
    
                int x1 = xpoints[(x+1)%N];
                int y1 = ypoints[(x+1)%N];
               
                g.setColor(changeColor(x%4));
                
                g.drawLine(x0, y0, x1, y1);
    
            }

        }else{
            colorColission = Color.BLACK;
            thereColissionWith();
            g.setColor(colorColission);
            g.drawPolygon(colaider.getColaider());
        }

        drawImage(g);

    }

    
    /**
     * Each entity can override this method to be drawn in its own way.
     * @param g requeried graphic component
     */
    public void drawImage(Graphics2D g){
        g.rotate(Math.toRadians(this.angle),x,y);
        g.drawImage(this.getImage(),(int)(x-(newWidth/2)),(int)(y-(newWidth/2)), null);
    }

    //----------------------------------------------------

    /* Rotations *///--------------------------------------------

    private double angle = 0;

    /**
     * basic method of gradual position.
     * @param x increases position in the x-direction
     * @param y increases position in the y-direction
     */
    public void move(int x, int y){
        
        colaider.moveToGradual(x, y);

        //--Test to disable movement in case of collision-
        // thereColissionWith();

        // if(with == 'A'){
        //     with = '\0';
        //     colaider.moveToGradual(-x, -y);
        //     return;
        // }
        // ------------------------------------------------

        this.x+=x;
        this.y+=y;
    }

    //------------------------------------------------------

    /**
     * Call the {@code Colaider} method to detect collisions.
     * @param target Polugon for detect
     * @return if there is a colission
     * @see Colaider.isColission(Polygon)
     */
    public boolean isColission(Polygon target){
        return colaider.isColission(target);
    }


    private ArrayList<Animation> animations = new ArrayList<Animation>();

    public void addAnimation(Animation anima){
        this.animations.add(anima);
    }
    public void removeAnimation(Animation anima){
        try {
            this.animations.remove(anima);
        } catch (Exception e) {
            System.out.println("Missing Object Animation");
        }
    }

    public ArrayList<Animation> getAnimations(){
        return this.animations;
    }
    
    

    /**
     * Returns the layer number.
     * @return layer value - value >= 0
     */
    public int getLayer(){
        return this.layer;
    }
    
    /**
     * Assigns the layer on which the entity will be drawn.
     * @param layer layer - value >= 0
     */
    public void setLayer(int layer){
        this.layer = layer;
    }

    /**
     * Assigns the angle of rotation that the image has
     * @param angle angle in degrees
     */
    public void setAngle(double angle){
        this.angle = angle;
    }

    /**
     * returns the collaider object
     * @return {@code Colaider} object
     */
    public Colaider getColaiderClass(){
        return colaider;
    }

    /**
     * returns the collaider Polygon
     * @return  {@code Polygon} object
     */
    public Polygon getColaider(){
        return colaider.getColaider();
    }

    // ---- PROOFS, NOTTHING OFICIAL-----------------------

    public Image getImage(){
        return sprite;
    }

    public void setImage(Image sprite){
        this.sprite = sprite;
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    //----------------------------------------------

    
}
