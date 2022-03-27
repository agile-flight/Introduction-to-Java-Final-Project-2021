import java.awt.*;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent; 
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import java.util.Random;

class Draw {
    Graphics g;
    Draw(Graphics g) {
        this.g = g;
    };
}
public class CopyOfGunjan {
    
    JFrame frame;
    DrawPanel drawPanel;
    
    public static void main(String... args) {
        new CopyOfGunjan().go();
    }

    private void go() {
        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(515, 539));
        frame.setBackground(Color.BLACK);
        frame.pack();
        drawPanel = new DrawPanel();

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setResizable(true);
        frame.setLocationByPlatform(true);

        frame.setVisible(true);
        
    }
    
    //Global Variables go here
    //Your global variables go here
    int[] player = {72,220};
    int[] oldPlayer = {72,220};
    double[] speed = {0,0};
    double gravity = 0.3;
    double jumpVelocity = -8 ;
    boolean isGrounded = false;
    int t = 0;
    int stageNum = 0;
    int deaths = 0;
    boolean frozen = false;
    boolean infinite = false;
    
    Color mint = new Color(55, 255, 98);
    Color lightMint = new Color(151, 240, 179);
    Color darkMint = new Color(44, 150, 77);
    Color darkOrange = new Color(255, 144, 54);
    
    int[][] platforms3d = getPlatforms();
    int[][] enemies = getEnemies(stageNum);
    
    String l1 = "This game contains stages with randomly generated platforms.";
    String l2 = "To get to the next stage, go off of the right side of the game.";
    String l3 = "If you fall, you will die and go back to first stage.";
    String l4 = "Get to stage 5 to win.";
    String l5 = "The orange blocks are lethal.";
    String l6 = "Almost there!";
 
    //booleans for user input
    public boolean UpButton = false;
    public boolean DownButton = false;
    public boolean RightButton = false;
    public boolean LeftButton = false;
    public boolean IButton = false;
    public boolean OButton = false;
    public boolean PButton = false;
    boolean SpaceButton = false;
    boolean mouseClicked = false;
    public int[] mouse = {0,0};
    
    class DrawPanel extends JPanel {
        private static final long serialVersionUID = 1L;     

        public DrawPanel() {
            KeyListener listener = new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    //System.out.println("keyPressed=" + KeyEvent.getKeyText(e.getKeyCode()));
                    int key = e.getKeyCode();
                    
                    if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W ) {
                        UpButton = true;
                        DownButton = false;
                    } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                        UpButton = false;
                        DownButton = true;
                    }
                    if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                        RightButton = true;
                        LeftButton = false;
                    } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                        RightButton = false;
                        LeftButton = true;
                    }
                    if (key == KeyEvent.VK_SPACE) {
                        SpaceButton = true;
                    }
                    if (key == KeyEvent.VK_I) {
                        IButton = true;
                    }
                    if (key == KeyEvent.VK_O) {
                        OButton = true;
                    }
                    if(infinite){
                        if (key == KeyEvent.VK_P) {
                            PButton = true;
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    int key = e.getKeyCode();
                    
                    if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                        UpButton = false;
                    } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                        DownButton = false;
                    }
                    if (key == KeyEvent.VK_RIGHT|| key == KeyEvent.VK_D) {
                        RightButton = false;
                    } else if (key == KeyEvent.VK_LEFT|| key == KeyEvent.VK_A) {
                        LeftButton = false;
                    }
                    if (key == KeyEvent.VK_SPACE) {
                        SpaceButton = false;
                    }
                    
                    if (key == KeyEvent.VK_I) {
                        IButton = false;
                    }
                    if (key == KeyEvent.VK_O) {
                        OButton = false;
                    }

                }
            };
            addKeyListener(listener);
            setFocusable(true);
            
            
            MouseListener mListener = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    mouseClicked = true;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                     mouseClicked = false;
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                     
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                     
                }
 
            };
            addMouseListener(mListener);
            setFocusable(true);
        }
        
        public void paintComponent(Graphics g) {
            Random r2 = new Random();
            //This code redraw the scene.  Don't change this
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            frame.repaint();
            //This code finds the mouse location
            Point p = MouseInfo.getPointerInfo().getLocation();
            SwingUtilities.convertPointFromScreen(p, frame);
            mouse[0] = p.x;
            mouse[1] = p.y-30;
            if(RightButton)
            {
                if((player[0]+10) < 500){
                    if(!(frozen)){                        
                        speed[0]++;
                    }
                }
                else{
                    player[0] = 72;
                    player[1] = 220;
                    stageNum++;
                    platforms3d = getPlatforms();
                    enemies = getEnemies(stageNum);
                    for(int i = 0; i < 100; i++){
                        speed[0] = 0;
                        try{
                            Thread.sleep(2);
                        } catch(InterruptedException e) {
                         Thread.currentThread().interrupt();
                        }
                    }
                }
            }
            else if(LeftButton)
            {
                if((player[0] > 0) && !(frozen)){
                    speed[0]--;
                }          
            }
            speed[0] *= 0.89; 
            if((player[0] - 20) < 0){
                speed[0] = 0;
                if(RightButton){
                    player[0]++;
                }
            }
            player[0] += (int)speed[0];
            if(Math.abs(speed[0]) < 0.89){
                speed[0] = 0;
            }
            //Jump for the player
            if((SpaceButton || UpButton)&& isGrounded)
            {
                speed[1] = jumpVelocity;
                isGrounded = false;
            } 
            if(isGrounded)
            {
                speed[1] = 0.9;
            }
            else
            {
                speed[1] += gravity;
            }
            if(speed[1] > 10){
                speed[1] *= 0.9;
            }
            if(!(frozen)){
                player[1] += speed[1];
            }
            if((player[1] + 10) > 500){
                player[0] = 72;
                player[1] = 220;
                speed[0] = 0;
                deaths++;
                if(!(infinite)){
                    stageNum = 1;
                }
                platforms3d = getPlatforms();
                enemies = getEnemies(stageNum);
                try{
                    Thread.sleep(100);
                } catch(InterruptedException e) {
                   Thread.currentThread().interrupt();
                }
            }
            //draw the player.
            g.setColor(Color.RED);
            g.fillOval(player[0]-10,player[1]-10,20,20);
            for(int i = 0; i<enemies.length;i++)
            {
                if(collisionDetection(g,(player[0]-10), (player[1]-10), 20,
                                    20, enemies[i][0], enemies[i][1], 
                                    enemies[i][2], enemies[i][3])){
                    player[0] = 72; 
                    player[1] = 220;
                    speed[0] = 0; 
                    deaths++;
                    if(!(infinite)){
                        stageNum = 1;
                    }
                    platforms3d = getPlatforms();
                    enemies = getEnemies(stageNum);
                    try{
                        Thread.sleep(100);
                    } catch(InterruptedException e) {
                       Thread.currentThread().interrupt();
                    }
                }
            } 
            for(int i = 0; i<platforms3d.length;i++)
            {
                g.setColor(mint);
                g.fillRect(platforms3d[i][0],platforms3d[i][1],
                platforms3d[i][2],platforms3d[i][3]);
                g.setColor(lightMint);
                g.drawRect(platforms3d[i][0],platforms3d[i][1], 
                platforms3d[i][2],platforms3d[i][3]);
                g.setColor(darkMint);
                g.drawLine((platforms3d[i][0] + platforms3d[i][2]),
                            (platforms3d[i][1] + platforms3d[i][3]),
                            platforms3d[i][0], platforms3d[i][1]);
                g.drawLine((platforms3d[i][0] + platforms3d[i][2]),
                            platforms3d[i][1], platforms3d[i][0],
                           (platforms3d[i][1] + platforms3d[i][3]));
            }
            for(int i = 0; i<enemies.length;i++)
            {
                g.setColor(Color.ORANGE);
                g.fillRect(enemies[i][0],enemies[i][1],
                enemies[i][2],enemies[i][3]);
                g.setColor(darkOrange);
                g.fillOval(enemies[i][0] + 5,enemies[i][1] + 5,
                enemies[i][2] - 10,enemies[i][3] - 10);
                g.drawRect(enemies[i][0],enemies[i][1],
                enemies[i][2],enemies[i][3]);
            }
            //platform landing detection
            if(!isGrounded)
                for(int i = 0; i<platforms3d.length;i++)
                {
                    if(oldPlayer[1]<platforms3d[i][1] && 
                        player[1]>=platforms3d[i][1] && 
                        player[0]>platforms3d[i][0] && 
                        player[0]<platforms3d[i][0]+platforms3d[i][2])
                    {
                        player[1] = platforms3d[i][1]-10;
                        isGrounded = true;
                        t = i;
                    }
                }
            if(isGrounded)
                
                if(player[0] < platforms3d[t][0]|| 
                    player[0] > (platforms3d[t][0] + platforms3d[t][2]))
                {
                    isGrounded = false;
                    t = -1;
                }   
            //End of loop clean up and storage
            oldPlayer[0] = player[0];
            oldPlayer[1] = player[1];
            //This section displays indicators to show when buttons are pushed
            HUD(g,mouse,UpButton,DownButton,RightButton,LeftButton,mouseClicked);
            if(stageNum == 0){
                g.drawString(l1, 5,15);
                g.drawString(l2, 5,30);
                g.drawString(l3, 5,45);
                g.drawString(l4, 5,75);
                g.drawString(l5, 5,60);
            }
            else if(stageNum == 4){
                if(!(infinite)){
                    g.drawString(l6, 5,15);
                }
            }
            
            
            /** 
            * 
            * 
            * TO CHANGE AMOUNT OF STAGES TO WIN TO N AMOUNT OF STAGES,
            * CHANGE HE CONDITIONAL OF THE BOTTOM IF LOOP TO 
            * if(stageNum == N){.
            * 
            * 
            */
           
            if(!(infinite)){
                if(stageNum == 5){
                    endGame(g, deaths, infinite, stageNum);
                    frozen = true;
                    if(IButton){
                        stageNum = 1;
                        deaths = 0;
                        platforms3d = getPlatforms();
                        enemies = getEnemies(stageNum);
                        player[0] = 72; 
                        player[1] = 220;
                    }
                    if(OButton){
                        stageNum = 1;
                        deaths = 0;
                        platforms3d = getPlatforms();
                        enemies = getEnemies(stageNum);
                        player[0] = 72; 
                        player[1] = 220;
                        infinite = true;
                        frozen = false;
                    }
                }
                else{
                    frozen = false;
                }
            }
            else if(PButton){
                    endGame(g, deaths, infinite, stageNum);
                    frozen = true;
                
            }
        }
    }
    public void HUD(Graphics g, int[] mouse, boolean UpButton,boolean DownButton,boolean RightButton,
                            boolean LeftButton,boolean mouseClicked)
    {
            g.setColor(Color.GRAY);
            g.fillRect(35,440,20,20);
            g.fillRect(40,460,20,20);
            g.fillRect(20,460,20,20);
            g.fillRect(60,460,20,20);
            g.fillRect(20,485,60,10);
            g.fillOval(100,480,15,15);
            g.setColor(mint);
            g.drawRect(35,440,20,20);
            g.drawRect(40,460,20,20);
            g.drawRect(20,460,20,20);
            g.drawRect(60,460,20,20);
            g.drawRect(20,485,60,10);
            g.drawOval(100,480,15,15);
            g.setColor(Color.YELLOW);
            if(UpButton)
                g.fillRect(35,440,20,20);
            if(DownButton)
                g.fillRect(40,460,20,20);
            if(LeftButton)
                g.fillRect(20,460,20,20);
            if(RightButton)
                g.fillRect(60,460,20,20);
            if(SpaceButton)
                g.fillRect(20,485,60,10);
            if(mouseClicked)
                g.fillOval(100,480,15,15);
            String display4 = "";
            String info = "";
            if(infinite){
                display4 = "Score: " + stageNum;
                g.drawString(display4,440, 490);
                info = "Press P to exit";
                g.drawString(info,320, 490);
            }
            else{
                display4 = "Stage Number: " + stageNum;
                g.drawString(display4,400, 490);
            }
    }
    public static int[][] getPlatforms(){
        Random r = new Random();
        int[][] platformCombine = new int[4][4];
        int[] platform1 = new int[4];
        int[] platform2 = new int[4];
        int[] platform3 = new int[4];
        int[] platform4 = new int[4];
        int randNum = 0;
        int times = 1;
        platform1[0] = 50;
        platform1[1] = 250;
        platform1[2] = 60;
        platform1[3] = 10;
        platform2[0] = platform1[0] + r.nextInt(100)+60;
        if((platform1[0] - 100) < 0){
            platform2[1] = platform1[1] + r.nextInt(100);
        }
        else if((platform1[1] + 100) > 500){
            platform2[1] = platform1[1] - r.nextInt(100);
        }
        else{
            randNum = r.nextInt(3);
            if(randNum == 1){
                times = -1;
            }
            else{
                times = 1;
            }
            platform2[1] = platform1[1] + times * r.nextInt(100);
        }
        platform2[2] = 60;
        platform2[3] = 10;
        platform3[0] = platform2[0] + r.nextInt(100)+60;
        if((platform2[0] - 100) < 0){
            platform3[1] = platform2[1] + r.nextInt(100);
        }
        else if((platform1[1] + 100) > 500){
            platform3[1] = platform2[1] - r.nextInt(100);
        }
        else{
            randNum = r.nextInt(3);
            if(randNum == 1){
                times = 1;
            }
            else{
                times = -1;
            }
            platform3[1] = platform2[1] + times * r.nextInt(100);
        }
        platform3[2] = 60;
        platform3[3] = 10;
        platform4[0] = platform3[0] + r.nextInt(100)+60;
        if((platform1[3] - 100) < 0){
            platform4[1] = platform3[1] + r.nextInt(100);
        }
        else if((platform1[1] + 100) > 500){
            platform2[1] = platform1[1] - r.nextInt(100);
        }
        else{
            randNum = r.nextInt(3);
            if(randNum == 1){
                times = 1;
            }
            else{
                times = -1;
            }
            platform4[1] = platform3[1] + times * r.nextInt(100);
            }
        platform4[2] = 60;
        platform4[3] = 10;
        for(int i = 0; i < platformCombine.length; i++){
            for(int y = 0; y < platformCombine[i].length; y++){
                if(i == 0){
                    platformCombine[i][y] = platform1[y];
                }
                else if(i == 1){
                    platformCombine[i][y] = platform2[y];
                }
                else if(i == 2){
                    platformCombine[i][y] = platform3[y];
                }
                else{
                    platformCombine[i][y] = platform4[y];
                }
            }
        }
        return platformCombine;
    }
    public static void endGame(Graphics g, int deaths, boolean infinite, 
                                int score){
        if(!(infinite)){
            String l7 = "You Win!"; 
            String l8 = "";
            if(!(infinite)){
                if(deaths > 0){
                    l8 = "Deaths: " + deaths;
                }
                else{
                    l8 = "Deathless!";
                }
            }
            String l9 = "Press I key to play again.";
            String l10 = "Press O key to play";
            String l11 = "infinite mode.";
            g.setColor(Color.BLACK);
            g.fillRect(0,0,600,600);
            g.setColor(Color.WHITE);
            g.drawRect(150,200,200,100);
            g.setColor(Color.YELLOW);
            g.drawString(l7, 160, 220);
            g.drawString(l8, 160, 245);
            g.drawString(l9, 160, 260);
            g.drawString(l10, 160, 275);
            g.drawString(l11, 160, 290);
        }
        else{
            String l7 = "Ending Game"; 
            String l8 = "";
            String l9 = "Score " + score;
            if(deaths > 0){
                l8 = "Deaths: " + deaths;
            }
            else{
                l8 = "Deathless!";
            }
            g.setColor(Color.BLACK);
            g.fillRect(0,0,500,500);
            g.setColor(Color.WHITE);
            g.drawRect(150,200,200,100);
            g.setColor(Color.YELLOW);
            g.drawString(l7, 160, 220);
            g.drawString(l8, 160, 245);
            g.drawString(l9, 160, 260);
        }
    }
    public static int[][] getEnemies(int stageNumber){
        Random r3 = new Random();        
        int[][] enemyCombine = new int[6][4];
        int[] e1 = new int[4];
        int[] e2 = new int[4];
        int[] e3 = new int[4];
        int[] e4 = new int[4];
        int[] e5 = new int[5];
        int[] e6 = new int[6];
        e1[0] = r3.nextInt(380)+100; //Yes I know this code is sloppy
        e1[1] = r3.nextInt(300);
        e1[2] = 20;
        e1[3] = 20;
        e2[0] = r3.nextInt(380)+100;
        e2[1] = r3.nextInt(300);
        e2[2] = 20;
        e2[3] = 20;
        e3[0] = r3.nextInt(380)+100;
        e3[1] = r3.nextInt(300);
        e3[2] = 20;
        e3[3] = 20;
        e4[0] = r3.nextInt(380)+100;
        e4[1] = r3.nextInt(300);
        e4[2] = 20;
        e4[3] = 20;
        e5[0] = r3.nextInt(380)+100;
        e5[1] = r3.nextInt(300);
        e5[2] = 20;
        e5[3] = 20;
        e6[0] = r3.nextInt(380)+100;
        e6[1] = r3.nextInt(300);
        e6[2] = 20;
        e6[3] = 20;        
        for(int i = 0; i < stageNumber+2; i++){
            for(int y = 0; y < enemyCombine[i].length; y++){
                if(i == 0){
                    enemyCombine[i][y] = e1[y];
                }
                else if(i == 1){
                    enemyCombine[i][y] = e2[y];
                }
                else if(i == 2){
                    enemyCombine[i][y] = e3[y];
                }
                else if(i == 3){
                    enemyCombine[i][y] = e4[y];
                }
                else if(i == 4){
                    enemyCombine[i][y] = e5[y];
                }
                else if(i == 5){
                    enemyCombine[i][y] = e6[y];
                }                
            }
        }
        return enemyCombine;
    }
    public static boolean collisionDetection(Graphics g,int x1,int y1, int w1, int h1,
                                            int x2, int y2, int w2, int h2){
        if(x1 < x2 + w2 &&
           x1 + w1 > x2 &&
           y1 + h1 > y2 &&
           y1 < y2 + h2){
               return true;
        }
        return false;
    }
}
