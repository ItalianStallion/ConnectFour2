
package connectfour;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 *
 * @author patrick
 */
public class GameBoard{
        public final int COLUMNS; 
        public final int ROWS;
        public Chip[][] board;
        public boolean gameOver = false;
        private File f= new File("GameBoard.txt");
        public int lastChipPlayed;
        
        public GameBoard( int rows, int columns){
            this.COLUMNS = columns;
            this.ROWS = rows;
            board= new Chip[ROWS][COLUMNS];
            for (int row =0; row< ROWS; row++){
                for (int column= 0; column < COLUMNS; column++){
                    board[row][column] = new Chip(0);
                }
            }
        }
        
        public GameBoard( String b){
            Scanner scr = new Scanner(b);
            this.ROWS= scr.nextInt();
            this.COLUMNS= scr.nextInt();
            for (int row =0; row< ROWS; row++){
                scr.next();
                for (int column= 0; column < COLUMNS; column++){
                    board[row][column]= new Chip(scr.nextInt());
                }
                scr.next();
            }
            scr.close();
        }
        public File saveBoard() throws IOException{
           // File f= new File(file);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(ROWS);
            pw.println(COLUMNS);
            pw.println(toString());
            pw.close();
            return f;
        }
        public String loadBoard() throws IOException{
            FileReader fr = new FileReader(f);
            Scanner scr= new Scanner(fr);
            String gameBoardString= "";
            while (scr.hasNext())
                gameBoardString+= scr.next()+" ";
            return gameBoardString;
        }
        
        public int getColumns(){
            return COLUMNS;
        }
        public int getRows(){ 
            return ROWS;
        }
         public boolean canPlay(int column){
            if (column <0 || column >= COLUMNS) return false;
            for (int row = ROWS-1; row>= 0; row--){
                if ( board[row][column].getPlayer()== 0){
                    return true;
                }
            }
            return false;
         }
        public int play(int column, Chip player, int print){
            if (column <0 || column >= COLUMNS) return 1;
            for (int row = ROWS-1; row>= 0; row--){
                if ( board[row][column].getPlayer()== 0){
                    lastChipPlayed=row;
                    board[row][column]= player; 
                    if (check(new Pos(row, column)))
                        gameOver= true;
                    if (print==1)
                        System.out.println(toString());
                    return 0;
                }
            }
            return 2;
        }
        /*
        this method changes the last chip played in @param column to a blank chip
        */
        public int clear(int column){
            
            if (column <0 || column >= COLUMNS) return 1;
            for (int row = 0; row< ROWS; row++){
                if ( board[row][column].getPlayer()!= 0){
                    board[row][column]= new Chip();
                    return 0;
                }
            }
            return 2;
                    
           // board[lastChipPlayed][column]= new Chip();
           //         return 0;
        }
        
        public boolean isFull(){
            for (int column=0; column<COLUMNS; column++){
                if (board[0][column].getPlayer()==0)
                    return false;
            }
            return true;
        }
        
        public boolean check(Pos p){
            int row =p.getX();
            int column= p.getY();
            int player= board[row][column].getPlayer();
            for (int i =-1; i< 2; i++)
                for (int k =-1; k< 1; k++)
                    if (!(i==0 && k==0)) 
                        if (checkLine(p, i, k, player)>=4 && player != 0) return true; 
            return false;
        } 
        
        public int checkLine(Pos p, int dx, int dy, int player){ 
            int numRow= 1;
            Pos p2= new Pos (p.x, p.y);
            Pos p1= new Pos (p.x, p.y);
            p2.change(-1*dx, -1*dy);
            try{
            while (player == board[p2.x][p2.y].getPlayer()){
                numRow++;
                if (numRow>= 4) return numRow; 
                p2.change(-1*dx, -1*dy); 
            }} catch (IndexOutOfBoundsException e) {}
            
            p1.change(dx, dy);
            try{
            while (player == board[p1.x][p1.y].getPlayer()){
                numRow++;
                if (numRow>= 4) return numRow; 
                p1.change(dx, dy);
            }} catch (IndexOutOfBoundsException e) {}
            return numRow;
        }
        
        public String toString(){
            StringBuilder sb = new StringBuilder();
            for (int i =0; i< ROWS; i++){
                sb.append("|");
                for (Chip c: board[i]){
                    sb.append(" ");
                    if (c.getPlayer()==0) sb.append("0");
                    if (c.getPlayer()==1) sb.append("1");
                    if (c.getPlayer()==2) sb.append("2"); 
                }
                sb.append(" |\n");
            }
            return sb.toString();
        }
    }
