
package connectfour;

/**
 *
 * @author patrick
 */
public class Chip {
    public int player= 0;         // 0= no player; 1= player1; 2=player2;
    public String name;
    
    public Chip(int player){
        this.player= player;
    }
    public Chip(){
        this.player=0;
    }
    
    public int getPlayer() {
        return player; 
    }
    
    public void setPlayer(int player){
        this.player = player;
    }
}
