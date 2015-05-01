
package connectfour;

import java.io.IOException;
import java.util.*;
//import static connectfour.ConnectFour.gb;

/**
 *
 * @author patrick
 */
public class ComputerPlayer {
    private int score, alpha, beta;
    private boolean gameOver= false;
    private Chip player= new Chip(2);
    private Chip otherPlayer= new Chip(1);
    private int me=2, opponent=1;
    private final int COLUMNS, ROWS;
    //private Chip[][] board;
    public GameBoard gb;
    
    public ComputerPlayer(GameBoard gb, Chip player){
        //this.gb= gb;
        this.player.name="Computer";
        this.COLUMNS= gb.COLUMNS;
        this.ROWS= gb.ROWS;
        this.gb= new GameBoard(ROWS, COLUMNS);
        //this.board=new Chip[ROWS][COLUMNS];
        for (int row =0; row< ROWS; row++){
                for (int column= 0; column < COLUMNS; column++){
                    this.gb.board[row][column] = new Chip(gb.board[row][column].getPlayer());
                }
        }
    }
    
    public void update(){
        for (int row =0; row< ROWS; row++){
                for (int column= 0; column < COLUMNS; column++){
                    this.gb.board[row][column].setPlayer(ConnectFour.gb.board[row][column].getPlayer());
                }
        }
        //System.out.println("\nplayer at real board pos(0,0):"+gb.board[0][5].player);
        //System.out.println("player at fake board pos(0,0):"+GB.board[0][5].getPlayer());
        //System.out.println("real board: \n"+ gb.toString());
        //System.out.println("FAKE board: \n"+ GB.toString());
    }
    /*
    public void update(GameBoard gameBoard){
        for (int row =0; row< ROWS; row++){
                for (int column= 0; column < COLUMNS; column++){
                    gb.board[row][column].setPlayer(gameBoard.board[row][column].getPlayer());
                }
        }
        update();
    }
    */
    
    public void getBoard(GameBoard gb){
        for (int row =0; row< ROWS; row++){
                for (int column= 0; column < COLUMNS; column++){
                    this.gb.board[row][column] = new Chip(gb.board[row][column].getPlayer());
                }
        }
        //System.out.println(this.GB.toString());
    }
    public void printBoard(){
        System.out.println(gb);
    }
    
    public int[] minimax (int depth, int player) throws IOException{            //bestMove={score, index}
        int[] bestMove=new int[2];
        //bestMove[0]= (player == me) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int score=0;
        if (evaluateBoard()[0]>=1000||evaluateBoard()[1]>=1000 || depth<=0 || gb.isFull()){    // add if clause to check if board is full
            if (gb.isFull()){
                bestMove[0]= 0;
                return bestMove;
            }
            int[] playerScores=evaluateBoard();
                bestMove[0]= playerScores[1]-playerScores[0];
                return bestMove;
        }
        ArrayList<Integer> children= new ArrayList<Integer>();
        for (int column=0; column<COLUMNS; column++){
            if (gb.canPlay(column)) children.add(column);
        }
        bestMove[1]=children.get(0);
        if (player== me){
            bestMove[0]= Integer.MIN_VALUE;
            for (int column :children){
                gb.play(column, this.player, 0);
                score= minimax(depth-1, opponent)[0];
                if (score> bestMove[0]){
                    bestMove[0]= score;
                    bestMove[1]= column;
                    //System.out.println("Maximizing player's move found: score="+score+" column="+ column+ " depth= "+depth);
                    //printBoard();
                }
                gb.clear(column);
            }
            return bestMove;
        }
        else {
            bestMove[0]= Integer.MAX_VALUE;
            for (int column : children){
                gb.play(column, this.otherPlayer, 0);
                score= minimax(depth-1, me)[0];
                if (score< bestMove[0]){
                    bestMove[0]= score;
                    bestMove[1]= column;
                    //System.out.println("Minimizing player's move found: score="+score+" column="+column+ " depth="+depth);
                    //printBoard();
                }
                gb.clear(column);
            }
            return bestMove;
        }
        // location of alternate Minimax algorithm (broken)
    }
    
    public int[] alphaBeta (int depth, int alpha, int beta, int player) throws IOException{
        int bestMove[]= new int[2];
        if (evaluateBoard()[0]>=900||evaluateBoard()[1]>=900 || depth<=0 || gb.isFull()){
            if (gb.isFull()){
                bestMove[0]= 0;
                return bestMove;
            }
            int[] playerScores=evaluateBoard();
            bestMove[0]= playerScores[1]-playerScores[0];
            return bestMove;
        }
        ArrayList<Integer> children= new ArrayList<Integer>();
        for (int column=0; column<COLUMNS; column++){
            if (gb.canPlay(column)) children.add(column);
        }
        bestMove[1]=children.get(0); 
        int[] value= new int[2]; value[1]= children.get(0);
        if (player==me){
            value[0]= Integer.MIN_VALUE;
            for (int column : children){
                gb.play(column, this.player, 0);
                if (value[0] < alphaBeta(depth-1, alpha, beta, opponent)[0]){
                    value[0]= alphaBeta(depth-1, alpha, beta, opponent)[0];
                    value[1]= column;
                    //System.out.println("Maximizing player's move found: score="+value[0]+" column="+ column+ " depth= "+depth+" alpha="+alpha+" beta="+beta);
                    //printBoard();
                }
                alpha = Math.max(alpha, value[0]);
                gb.clear(column);
                if (beta<=alpha)
                    break;
            }
            return value;
        }
        else{
            value[0]= Integer.MAX_VALUE;
            for (int column : children){
                gb.play(column, this.otherPlayer, 0);
                if (value[0]> alphaBeta(depth-1, alpha, beta, me)[0]){
                    value[0]=alphaBeta(depth-1, alpha, beta, me)[0];
                    value[1]= column;
                    //System.out.println("Minimizing player's move found: score="+value[0]+" column="+ column+ " depth= "+depth+" alpha="+alpha+" beta="+beta);
                    //printBoard();
                }
                beta = Math.min(beta, value[0]);
                gb.clear(column);
                if (beta<=alpha)
                    break;
            }
            return value;
        }
    }
    
    public int[] evaluateBoard() throws IOException{
        int[] playerScores= {0, 0};             //index 0, 1 are players 1, 2 respectively
        int player, tempScore;
        for (int c =0; c<COLUMNS; c++){
            for (int r=0; r<ROWS; r++ ){
                player=gb.board[r][c].getPlayer();
                if (player!=0){
                    tempScore=check(new Pos(r,c) , player);
                    if (tempScore>playerScores[player-1]){
                        //System.out.println("pos= "+new Pos(r,c)+" player="+player+" score ="+tempScore);
                        playerScores[player-1]=tempScore;}
                }
            }
        }
        //playerScores[0]*=-1;
        return playerScores;
    }
    public int computeScore(int column, int player) throws IOException{
        //if (depth<=0) 
        //update();
        int myScore=0;
        if (column <0 || column > COLUMNS) return -1;
            for (int row = ROWS-1; row>= 0; row--){
                if (!gb.canPlay(column)) continue;
                if ( gb.board[row][column].getPlayer()== 0){
                    //if (player==2)
                       // GB.board[row][column]= new Chip(player); 
                    return myScore= check(new Pos(row+1, column), player);
                }
            }
        return myScore;
    }
    
    public int check(Pos p, int player){
        //update();
        int maxScore=0, lineScore;
        for (int i=-1; i <2; i++){
            for (int k=-1; k<2; k++){
                if (!(i==0 &&k==0)){
                    lineScore=checkLine(p, i, k, player);
                    if (lineScore> maxScore) {
                        maxScore= lineScore;
                    }
                }
            }
        }
        //System.out.println("MaxScore= "+maxScore+" index="+p.getY());
        return maxScore;
    } 
    
    public int checkLine(Pos p, int dx, int dy, int player){ 
            int score=0;
            int numRow= 1; int posRow=1;
            Pos p2= new Pos (p.x, p.y);
            Pos p1= new Pos (p.x, p.y);
           
            p1.change(dx, dy);
            try{
            while (player == gb.board[p1.x][p1.y].player || gb.board[p1.x][p1.y].player==0){
                if (numRow%10 >= 4) return numRow;
                if (posRow>=4)break;
                if (player == gb.board[p1.x][p1.y].player ){
                    //System.out.println("\n found one! at "+p1.x+" "+p1.y+"  posRow="+posRow+" numRow="+numRow+ "current pos ="+ p.toString());
                    numRow*=10; 
                    posRow++;}
                else{
                    posRow++;
                }
                p1.change(dx, dy);
            }
           
            } catch (IndexOutOfBoundsException e) {}
            //System.out.println("\n position= "+p+"  posRow="+ posRow+ "  numRow="+ numRow+ "  dx, dy="+dx+", "+dy);
            dx*=-1; dy*=-1;
             p2.change(dx, dy);
            try{
            while (player == gb.board[p2.x][p2.y].player || gb.board[p2.x][p2.y].player==0){
                if (numRow%10>=4) return numRow; 
                if (posRow>=4)break;
                if (player == gb.board[p2.x][p2.y].player){
                    //System.out.println("\n found one! at "+p2.x+" "+p2.y+"  posRow="+posRow+" numRow="+numRow+ "current pos ="+ p.toString());
                    numRow*=10; 
                    posRow++;}
                else{
                    posRow++;
                }
                p2.change(dx,dy);
            }
           
            } catch (IndexOutOfBoundsException e) {}
            
            score=numRow;
            if (posRow<4) score=0;
            //System.out.println("dx="+dx+" dy="+dy+" posRow="+ posRow+"                              score="+score );
            return score;
        }
}
 
//alternate minimax algorithm (broken)
/*
        else{
            ArrayList<Integer> children= new ArrayList<Integer>();
            for (int column=0; column<COLUMNS; column++){
               if (gb.canPlay(column)) children.add(column);
            }
            for (int column : children){
                gb.play(column,new Chip(player), 0);
                if (player==me){
                    score =minimax(depth-1, opponent);
                    if (score[0]>bestMove[0]){
                        bestMove[0]= score[0];
                        bestMove[1]= column;
                        System.out.println("found one at depth="+depth+" score="+bestMove[0]+" column="+bestMove[1]+" player="+me);
                        System.out.println(gb);
                    }
                }
                else {
                    score =minimax(depth-1, me);
                    if (score[0]<bestMove[0]){
                        bestMove[0]= score[0];
                        bestMove[1]= column;
                        System.out.println("found one at depth="+depth+" score="+bestMove[0]+" column="+bestMove[1]+" player="+opponent);
                        System.out.println(gb);
                    }
                }
                gb.clear(column);
            }
        }
        return bestMove;
        */