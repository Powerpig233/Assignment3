import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.util.Random;
import java.awt.Rectangle;
import java.sql.SQLException;


public class GameEngine extends JPanel {
    private final int FPS = 240;
    private final int player_MOVEMENT = 1;

    private boolean paused = false;
    private Image background;
    public int levelNum = 1;
    public int point = 0;
    private Level level;
    private Dragon dragon;
    private Player player;
    private BlackBoard blackboard;
    private Timer newFrameTimer;
    

    public GameEngine()
    {
        super();
        background = new ImageIcon("image/bg.jpg").getImage();
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
              /**
     * Decide what to do when pressed left
     * @param ae
     */
            @Override
            
            public void actionPerformed(ActionEvent ae) {
                player.setVelx(-player_MOVEMENT);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
                  /**
     * Decide what to do when pressed right
     * @param ae
     */
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.setVelx(player_MOVEMENT);
                
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
                  /**
     * Decide what to do when pressed down
     * @param ae
     */
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.setVely(player_MOVEMENT);
               
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
                  /**
     * Decide what to do when pressed up
     * @param ae
     */
            @Override
            public void actionPerformed(ActionEvent ae) {
                player.setVely(-player_MOVEMENT);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
                  /**
     * Decide what to do when pressed Esc
     * @param ae
     */
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });
        restart();
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener(this));
        newFrameTimer.start();
    }

    public void restart() {
        try {
            level = new Level("data/level" + levelNum + ".txt");
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Image playerImage = new ImageIcon("image/player.png").getImage();
        player = new Player(0, 700, 40, 40, playerImage);
        //Image dragonImage = new ImageIcon("image/dragon.png").getImage();
        GenerateDragon(level);
        blackboard = new BlackBoard();
        blackboard.modify(player);
        //dragon = new Dragon(0,40,40,40, dragonImage);
    }

    private Dragon GenerateDragon(Level level) {
        Random r = new Random();
        boolean isfound = false;
        while (!isfound) {
            int x = r.nextInt(741);
            int y = r.nextInt(321) + 40;
            boolean tmp = true;
            for (Wall wall : level.walls) {

                Rectangle rect = new Rectangle(x, y, 40, 40);
                Rectangle otherRect = new Rectangle(wall.x, wall.y, wall.width, wall.height);
                if (rect.intersects(otherRect)) {
                    tmp = false;
                }

            }
            if(tmp == true)
            {
                Image dragonImage = new ImageIcon("image/dragon.jpg").getImage();
                dragon = new Dragon(x,y,40,40,dragonImage);
                isfound = true;
                return dragon;
            }
            
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 800, 800, null);
        level.draw(grphcs);
        player.draw(grphcs);
        dragon.draw(grphcs);
        blackboard.modify(player);
        blackboard.draw(grphcs);

    }

    class NewFrameListener implements ActionListener {
        public JPanel panel;

        NewFrameListener(JPanel panel) {
            this.panel = panel;
        }


        @Override
        
        public void actionPerformed(ActionEvent ae) {
            if (!paused) {
                dragon.move();
                if (dragon.collides(player)) {
                   String name = JOptionPane.showInputDialog("You are caught, you got " + (point) + " points\n      Please give your name:","Player1");
                    if(name != null){
                    try{
                           database db = new database();
                            db.insertScore(name,point);
                            }catch (SQLException ex) {
                                Logger.getLogger(database.class.getName());
                             }
                            point = 0;
                            levelNum = 1;
                            restart();
                }
                }
                if(player.win())
                {
                    point = point + 1;
                    JOptionPane.showMessageDialog(panel, "You won this level, you got " + point + " points",
                            "Congrats", JOptionPane.PLAIN_MESSAGE);
                            if(levelNum<=9)
                            {
                                levelNum =  levelNum + 1;
                            }else
                            {
                                levelNum = 1;
                            }
                            restart();
                }
                
                if (level.collides(dragon)) {
                    dragon.ChangeDirection(level);
                }

                if (level.collides(player)) {
                    player.setVelx(0);
                    player.setVely(0);
                }

                player.move();
            }

            repaint();
        }

    }

   
         
}
