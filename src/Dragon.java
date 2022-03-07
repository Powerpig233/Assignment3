import java.awt.Image;
import java.util.Random;
import java.awt.Rectangle;
public class Dragon extends Sprite {
    private double velx =1 ;
    private double vely ;
    private boolean WhetherX = true;
    public String tendDir = "x";
    public double tendValue =0; 

    public Dragon(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
        
    }

    public void moveX() {
        if ((velx < 0 && x > 0) || (velx > 0 && x + width <= 780)) {
            x += velx;
        }else
        {
            ChangeDirection();
        }
    }

    public void moveY() {
        if ((vely < 0 && y > 0) || (vely > 0 && y + height <= 740)) {
            y += vely;
        }else
        {
            ChangeDirection();
        }
    }

    public void ChangeDirection(Level level)
    {
        Random r = new Random();
        int n = r.nextInt(4); 
        if(n == 0)
        {
            boolean Canbe = true; 
            Rectangle rect = new Rectangle(x , y -1 , width, height);
            for(Wall wall : level.walls)
            {
                Rectangle otherRect = new Rectangle(wall.x, wall.y, wall.width, wall.height);
                if (rect.intersects(otherRect)) {
                    Canbe = false;
                }
            }
            if(Canbe){
            vely = -1;
            tendValue = vely;
            tendDir = "y";
            WhetherX = false;
            }else
            {
                ChangeDirection(level);
            }
        }else if(n==1)
        {
            boolean Canbe = true;
            Rectangle rect = new Rectangle(x , y + 1 , width, height);
            for(Wall wall : level.walls)
            {
                Rectangle otherRect = new Rectangle(wall.x, wall.y, wall.width, wall.height);
                if (rect.intersects(otherRect)) {
                    Canbe = false;
                }
            }
            if(Canbe){
            vely = 1 ;
            tendValue = vely;
            tendDir = "y";
            WhetherX = false;
            }else
            {
                ChangeDirection(level);
            }
            
        }else if(n==2)
        {
            boolean Canbe = true;
            Rectangle rect = new Rectangle(x-1 , y , width, height);
            for(Wall wall : level.walls)
            {
                Rectangle otherRect = new Rectangle(wall.x, wall.y, wall.width, wall.height);
                if (rect.intersects(otherRect)) {
                    Canbe = false;
                }
            }
            if(Canbe){
            velx = -1;
            tendValue = velx;
            tendDir ="x";
            WhetherX = true;
            }else
            {
                ChangeDirection(level);
            }
            
        }else
        {
            boolean Canbe = true;
            Rectangle rect = new Rectangle(x+1 , y , width, height);
            for(Wall wall : level.walls)
            {
                Rectangle otherRect = new Rectangle(wall.x, wall.y, wall.width, wall.height);
                if (rect.intersects(otherRect)) {
                    Canbe = false;
                }
            }
            if(Canbe){
            velx = 1;
            tendValue = velx;
            tendDir ="x";
            WhetherX = true;
            }else
            {
                ChangeDirection(level);
            }
        }
        
    }

    public void ChangeDirection()
    {
        
        Random r = new Random();
        int n = r.nextInt(4);
        if(n == 0)
        {
            vely = -1;
            tendValue = vely;
            tendDir = "y";
            WhetherX = false;
            
        }else if(n==1)
        {
           
            vely = 1 ;
            tendValue = vely;
            tendDir = "y";
            WhetherX = false;
           
            
        }else if(n==2)
        {
            velx = -1;
            tendValue = velx;
            tendDir ="x";
            WhetherX = true;
            
        }else
        {
            velx = 1;
            tendValue = velx;
            tendDir ="x";
            WhetherX = true;
           
        }
    }

    public void move()
    {
        if(WhetherX == true)
        {
            moveX();
        }else
        {
            moveY();
        }
    }


    @Override
    public boolean collides(Sprite other) {
        if(tendDir.equals("x"))
        {
            Rectangle rect = new Rectangle(x + (int)velx , y, width, height);
            Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);        
            return rect.intersects(otherRect);
        }else
        {
            Rectangle rect = new Rectangle(x , y+ (int) vely, width, height);
            Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);        
            return rect.intersects(otherRect);
        }
        
    }
}
