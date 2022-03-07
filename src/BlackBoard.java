import java.awt.Image;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class BlackBoard {
    private final int BLACK_WIDTH = 40;
    private final int BLACK_HEIGHT = 40;
    private final int WIDTH = 800;
    private final int HEIGHT = 800;
    public ArrayList<Black> blacks;

    public BlackBoard() {
        blacks = new ArrayList<>();
        Image image = new ImageIcon("image/black.png").getImage();
        for (int i = 0; i < WIDTH / BLACK_WIDTH; i++) {
            for (int j = 0; j < HEIGHT / BLACK_HEIGHT; j++) {
                blacks.add(new Black(i * BLACK_WIDTH, j * BLACK_HEIGHT, BLACK_WIDTH, BLACK_HEIGHT, image));
            }
        }
    }

    public void draw(Graphics g) {
        for (Black black : blacks) {
           if(!black.exposed)
           {
                black.draw(g);
           }
        }
    }

    public void modify(Player player) {

        for (Black black : blacks) {

            Rectangle rect = new Rectangle(player.x - 3 * player.width, player.y - 3 * player.height, 7 * player.width,
                    7 * player.height);
            Rectangle otherRect = new Rectangle(black.x, black.y, black.width, black.height);
            if (rect.intersects(otherRect)) {
                black.exposed = true;
            }else
            {
                black.exposed = false;
            }

        }

    }
}
