
package connectfour;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Patrick Mancuso
 */
public class ConnectFour {
    public final int COLUMNS;
    public final int ROWS;
    public String p1, p2;
    public static GameBoard gb;
    public Chip player1 = new Chip(1);
    public Chip player2 = new Chip(2);
    public Chip winner =player2;
    public boolean computer= false; 
    public String response= "yes";
    public ComputerPlayer computerPlayer;
    
    public ConnectFour(GameBoard gb){
        COLUMNS = gb.getColumns();
        ROWS = gb.getRows();
        this.gb= gb;
    }
    public static GameBoard getGameBoard(){
        return gb;
    }
    
    public void start() throws IOException{             // allow player1 name to have a space
        Scanner scr = new Scanner(System.in);
        System.out.println("player1 please enter your name:");
        player1.name= scr.next();
        System.out.println("player2 please enter your name: or 'Computer' to play computer");
        player2.name= scr.next();
        if (player2.name.equalsIgnoreCase("computer")){
            computer = true;
            computerPlayer = new ComputerPlayer(gb, player2);
            System.out.println(player2.name+" "+ player2.getPlayer());
        }
        System.out.println("Let the game begin!!");
        int m;
        while (response.equalsIgnoreCase("yes")){
            System.out.println(gb.toString());
            while (! gb.gameOver){
                System.out.println("===========================================================");
                System.out.println(player1.name+ ", please enter a column(0, 1, 2, 3, 4, 5, 6):");
                while(!gb.canPlay(m= scr.nextInt()))
                    System.out.println("you cant go there, try again.");
                gb.play(m, player1, 1);
                if (gb.gameOver){
                    winner =player1;
                            
                    break;
                }
                if (computer){
                    System.out.println("Please wait while the computer calculates its next move...");
                    computerPlayer.update();
                    //int[] move= computerPlayer.minimax(5, 2);
                    int[] move= computerPlayer.alphaBeta(6, Integer.MIN_VALUE, Integer.MAX_VALUE, 2);
                    gb.play(move[1], player2, 1);
                }
                else{
                    Chip tempP= player1;
                    player1= player2;
                    player2= tempP;
                }
            }
            System.out.println("Congradulations "+winner.name+", You've won!");
            System.out.println("play again? (yes, no) ");
            response= scr.next();
            gb= new GameBoard(ROWS, COLUMNS);
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        GameBoard gb = new GameBoard(6, 7);
        Chip player1 = new Chip(1);
        Chip player2 = new Chip(2);
        ConnectFour c4 = new ConnectFour(gb);
        c4.start();
        /*
        ComputerPlayer cpu= new ComputerPlayer(gb, player2);
        gb.play(0, player1, 1);
        cpu.update();
        System.out.println(cpu.evaluateBoard()[0]);
        System.out.println(cpu.evaluateBoard()[1]);
        gb.play(0, player2, 1);
        cpu.update();
        System.out.println(cpu.evaluateBoard()[0]);
        System.out.println(cpu.evaluateBoard()[1]);
        gb.play(0, player2, 1);
        cpu.update();
        System.out.println(cpu.evaluateBoard()[0]);
        System.out.println(cpu.evaluateBoard()[1]);
        gb.play(0, player1, 1);
        cpu.update();
        System.out.println(cpu.evaluateBoard()[0]);
        System.out.println(cpu.evaluateBoard()[1]);
        gb.play(2, player2, 1);
        cpu.update();
        System.out.println(cpu.evaluateBoard()[0]);
        System.out.println(cpu.evaluateBoard()[1]);
        gb.play(3, player1, 1);
        cpu.update();
        //System.out.println("===============\n"+cpu.checkLine(new Pos(2,5), -1, 0, 1));
        //System.out.println("================\n"+cpu.computeScore(2, 1));
        System.out.println(cpu.evaluateBoard()[0]);
        System.out.println(cpu.evaluateBoard()[1]);
        */
    }
    
}
/*
{ maxIndex= index; break;}
                            for (int c=0; c<COLUMNS; c++){
                                if (enemyScore< computerPlayer.computeScore(c, 1))
                                    enemyScore= computerPlayer.computeScore(c, 1);
                            }
                            if (score-enemyScore> maxScore){
                                maxScore= score-enemyScore;
                                maxIndex=index;
                            }
*/