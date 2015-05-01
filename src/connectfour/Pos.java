
package connectfour;

/**
 *
 * @author patrick
 */
public class Pos {
    public int x; 
    public int y;
    
    public Pos(int x, int y){
        this.x= x;
        this.y = y;
    }
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
   /* public Pos change(int dx, int dy){
        return new Pos(x+dx, y+dy);
    }
    */
    public void change (int x, int y){
        this.x += x; 
        this.y += y;
    }
    public String toString(){
        return "("+x+", "+y+")";
    }
}
