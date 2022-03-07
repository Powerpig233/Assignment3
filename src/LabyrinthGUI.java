import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class LabyrinthGUI {
    private JFrame frame;
    private GameEngine gameArea;

    public LabyrinthGUI() {
        frame = new JFrame("Labyrinth");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        gameArea = new GameEngine();
        frame.getContentPane().add(gameArea);
        frame.setPreferredSize(new Dimension(800, 800));
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        JMenuItem newMenu = new JMenuItem("New");
        gameMenu.add(newMenu);
        newMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                gameArea.levelNum = 1;
                gameArea.point = 0;
                gameArea.restart();

            }
        });
        JMenuItem Ranking = new JMenuItem("Ranking");
        gameMenu.add(Ranking);
       
        Ranking.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
            try{
            database db = new database();
            ArrayList<data> Points = db.getHighScores();
            String content = "                Ranking   \n";
            for(int i=0 ;i < Points.size();i++)
            {
                if(i<10){
                content += String.valueOf(i+1)+". Name: " + Points.get(i).name + "  Point:  "+ Points.get(i).point +"\n";
                }
            }
             JOptionPane.showMessageDialog(gameArea, content,
                            "Ranking", JOptionPane.PLAIN_MESSAGE);
            
            }
            catch(SQLException ex)
            {
                Logger.getLogger(database.class.getName());
            }
            
            }
            }); 
            
        
        
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    
}
}
