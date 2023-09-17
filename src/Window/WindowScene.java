package Window;

//-------------Window Design-------------
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import java.awt.Dimension;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
//----------------------------------------

//----------Entities and Drawing----
import Logic.Animationa;
import Logic.Jump;
import Logic.ScreenPane;
import Logic.Sprint;
import Logic.SprintYoshi;
import Objects.Entity;
//----------------------------------

//----------Events------------------
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//---------------------------------

/**
 * Window where the development of a loop of entities 
 * is implemented, rendering of these, operations, 
 * a whole handling of operations and processes.
 * 
 * @see WindowScene(int, int)
 */
public final class WindowScene extends JFrame implements Runnable, KeyListener{
    
    private final int WIDTH;
    private final int HEIGHT;
    /**
     * Create a new window with preferred height and width.
     * 
     * This is the primal Constructor of this class, it only needs two parameters.
     * @param width     number of pixels on the x-axis
     * @param height    number of pixels on the y-axis
     */
    public WindowScene(int width, int height){

        this.WIDTH = width;
        this.HEIGHT = height;

        setSize(new Dimension(WIDTH,HEIGHT));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();


        /*-------------------------------------------------------------------------
         *  Differentiator for undecorated mode below this [Incomming experimental]
         *///----------------------------------------------------------------------
        
        setUndecorated(false); // Switchable

        /*
         * 
         * [JPanel]{background} This background allows the good management 
         * of the other components, since this is the last one located in 
         * the background.
         * 
         * This section is customizable for a greater personalization graphical experience.
         */
        JPanel background = new JPanel();
        background.setSize(800,600);
        background.setPreferredSize(new Dimension(800,600));
        background.setBackground(new Color(102, 153, 153));
        
        /*
         * This is complex in Visual Studio Code, but in other IDEs it's more 
         * possible because there are more graphical components intuitive(NetBeans).
         */

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.CENTER)
                .addComponent(background,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.CENTER)
                .addComponent(background,GroupLayout.PREFERRED_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE));
        
        //---------------------------------------------------------------

        /* Should always be at the END*/
        setVisible(true);
    }

    //-----------------------------------------------
    private static Thread thread;
    /**
     * This method is for thread initialization.
     * In it should always go the creation and start of execution of the Threads.
     */
    private void startThreah(){

        thread = new Thread(this,"Loop");
        thread.start();

    }
    //-----------------------------------------------

    Entity prueba;
    Entity prueba2;
    Entity prueba4;
    Entity yoshi;

    Animationa animation1;
    Animationa animation2;
    Animationa animation3;
    Animationa animation4;

    Sprint sprint;
    SprintYoshi sprintyoshi;
    Jump jump;

    // Graficas SEMI OFICIALES
    private Windoww windoww2;
    private Windoww windoww3;
    //-----------------------

    ScreenPane screen;
    //Mas pruebas

    Entity carrot;
    Entity arrow;

    /**
     * This is where the components of WindowScene begin.
     * Components such as: JPanel, Events, Interfaces, 
     * Graphic Objects among others.
     * 
     */
    private void initComponents(){

        carrot = new Entity(500, 500, 100,"src/Game/Carrot.png");
        carrot.setName("carrot");
        carrot.addColissionWith('B');
        animation2 = new Animationa(carrot);
        animation2.movementAnimation(5,0,9999,false,true);
        animation2.setValueK(300);

        arrow = new Entity(400, 450, 50,"src/Game/Arrow.png");
        arrow.setName("arrow");
        arrow.addColissionWith('C');
        animation3 = new Animationa(arrow);
        animation3.movementAnimation(5,1,20,false,true);
        animation2.setValueK(200);

        prueba = new Entity(WIDTH/2,HEIGHT/2, 1000,"src/Game/feather.png");
        prueba.setName("feather");
        animation4 = new Animationa(prueba);
        animation4.movementAnimation(3,0,9999,true,true);


        prueba4 = new Entity(400,300,500,"src/Game/feather.png");
        prueba4.setName("I'm");
        
        sprint = new Sprint(prueba4);
        jump = new Jump(prueba4);

        prueba4.addAnimation(sprint);
        prueba4.addAnimation(jump);
        // animation1 = new Animationa(prueba4);
        

        prueba.addColissionWith('A');
        prueba4.addColissionWith('A');
        prueba4.addColissionWith('B');
        prueba4.addColissionWith('C');
        carrot.addColissionWith('B');
        arrow.addColissionWith('B');
        // carrot.addColissionWith('C');

        prueba4.colissionEntity.add(prueba);
        prueba4.colissionEntity.add(carrot);
        prueba4.colissionEntity.add(arrow);
        arrow.colissionEntity.add(carrot);

        //yoshi
        yoshi = new Entity(100, 300-160,200,"src/Game/yoshi/yoshi00.png");
        yoshi.setName("Yoshi!");

        sprintyoshi = new SprintYoshi(yoshi);
        yoshi.addAnimation(sprintyoshi);

        yoshi.setLayer(4);
        

        this.addKeyListener(this);

        screen = new ScreenPane(5);

        prueba.setLayer(1);
        prueba4.setLayer(1);

        screen.addEntity(prueba4);
        screen.insertEntity(prueba, 1);
        
        //-- Prueba
        carrot.setLayer(3);
        screen.addEntity(carrot);
        arrow.setLayer(0);
        screen.addEntity(arrow);

        screen.addEntity(yoshi);

        screen.setSize(this.getSize());
        screen.setOpaque(false);

        this.add(screen);

        windoww2 = new Windoww(getLocation(),0);
        windoww3 = new Windoww(getLocation(),300);
        
        startThreah();

    }

    /**
     * Update is the Core process of this window.
     * Updates per second are used and implement 
     * the methods that must be executed in this 
     * particular time, used mainly for the detection 
     * and updating of data.
     * 
     * Example: in this case in 1 second there are 60 updates.
     */
    private void update(){

        updateKeys();
        keyToDo();
        
        ups2++;
        ups++;

        // animation1.startAnimation();
        animation2.startAnimation();
        animation3.startAnimation();
        animation4.startAnimation();
        // prueba.animations(x++);
        
        // prueba4.thereColissionWith();
        screen.startAnimations();
        screen.repaint();
        // prueba4.animations(x);
        
        
    }

    //-------------------- Control de actualizaciones -----------
    private static int ups;
    private static int fps = 0;
    //------------- Extra para testear----------------
    private static int fps2 = 0;
    private static int ups2 = 0;
    //------------------------------------------------
    private boolean gameOver = false;
    //-------------------------------------------------------------

    @Override

    /**
     * It interprets the main loop of the window in the 
     * runnable interface, where the updates per second 
     * that there will be in update are controlled as well 
     * as in the rendering.
     */
    public void run() {
        
        /*
         *  It manages a main Loop that will be the motor of the animations, frames, updates, etc.
         *  
         */

        final int NANO_SECONDS = 1000000000;
        final byte UPDATE_PER_SECONDS = 60;
        final double NANO_SEC_PER_UPDATES = NANO_SECONDS / UPDATE_PER_SECONDS;

        double delta_time = 0;

        long time_past = System.nanoTime();

        long refer_fps = System.nanoTime();
        long refer_fps2 = System.nanoTime(); //Añadido para testear Grafica.


        /* Loop primary */
        while(!gameOver){

            long current_time = System.nanoTime();
            
            delta_time += (double)(current_time - time_past)/NANO_SEC_PER_UPDATES;

            time_past = current_time;

            /* Delta time implementation for process compatibility */
            if(delta_time >= 1){
                
                update();
                delta_time--;
            }

            fps++;

            /* Show FPS and UPS in the viewport*///-------------------

            fps2++;
            if(current_time - refer_fps2 >= (double)NANO_SECONDS/4){

                refer_fps2 = current_time;

                windoww2.setGraphicScreen(ups2);
                windoww3.setGraphicScreen(fps2);
                ups2 = 0;
                fps2 = 0;
            }
            //----------------------------------------------------------

            /* Resets FPS AND UPS every second. 
             * Show in the title these. 
             */
            if(current_time - refer_fps >= NANO_SECONDS){

                refer_fps = current_time;
                setTitle(" UPS: " + ups + "   FPS: " + fps);

                ups = 0;
                fps = 0;
            }

        }

    }

    public static void main(String[] args){

        new WindowScene(800,600);
        
    }

    private boolean[] keys = new boolean[120];

    public static boolean left;
    public static boolean right;
    public static boolean down;
    public static boolean up;
    public static boolean space;
    
    public static boolean keyD;
    public static boolean keyA;


    public static int lastKey;

    /**
     * Basic management for the keys.
     * If you want to add a new key, you must 
     * equal the key to the position of the array.
     * <blockquote><pre></pre></blockquote><p>
     * {@code Example:} create the new variable static or public, 
     *          don't care and set it equal 
     *          to the Array keys
     * <blockquote><pre>
     *     public static boolean space;
     *     space = keys[KetEvent.VK_SPACE];.
     * </pre></blockquote><p>
     *  
     * This will detect this key and mark it if it is active in update();
     */

    private void updateKeys(){
        left = keys[KeyEvent.VK_J];
        right = keys[KeyEvent.VK_L];
        down = keys[KeyEvent.VK_K];
        up = keys[KeyEvent.VK_I];
        space = keys[KeyEvent.VK_SPACE];

        keyD = keys[KeyEvent.VK_D];
        keyA = keys[KeyEvent.VK_A];

    }

    /**
     * Contains the management that each update 
     * will be done with the keys that are pressed
     * 
     */

    public void keyToDo(){

        double x = 0;
        double y = 0;

        int vel = 4;
        
        // sprint.startAnimation();
        // jump.startAnimation();
        if(up){
            y-=vel;
        }
        if(right){
            x+=vel;
            lastKey = KeyEvent.VK_RIGHT;
        }
        if(left){
            x-=vel;
            lastKey = KeyEvent.VK_LEFT;
        }
        if(down){
            y+=vel;
        }

        if(space){
            if(!jump.isActive()){
                jump.setActive();
            }
        }

        double x2 = 0;
        double y2 = 0;

        if(keyD){
            x2+=5;
        }
        if(keyA){
            x2-=5;
        }

        double normal = Math.sqrt((x*x) + (y*y));

        prueba4.move((int)(((double)x/normal)*vel),(int)(((double)y/normal)*vel));

        yoshi.move((int)x2,0);

    }

    
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

}
