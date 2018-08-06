/**
 * Created by chengqian on 11/6/16.
 */
import java.util.LinkedList;
import java.util.Scanner;

//This program plays tic-tac game
public class TicTac {
    private final char EMPTY = ' ';     //empty slot
    private final char COMPUTER = 'X';  //computer
    private final char PLAYER = 'O';    //player
    private final int MIN = 0;          //min level
    private final int MAX = 1;          //max level

    //Board class (inner class)
    private class Board
    {
        private char[][] array;         //board array

        //Constructor of Board class
        private Board(int size)
        {
            array = new char[size][size];   //create array

            for (int i = 0; i < size; i++)  //fill with empty slots
                for (int j = 0; j < size; j++)
                    array[i][j] = EMPTY;
        }
    }

    private Board board;                //game board
    private int size;                   //size of board

    //Constructor of TicTac class
    public TicTac(int size)
    {
        this.board = new Board(size);   //create game board
        this.size = size;               //assign board size
    }

    //Method plays game
    public void play()
    {
        while (true)                            //computer and player take turns
        {
            board = playerMove(board);          //player move

            if (playerWin(board))               //if player wins game over
            {
                System.out.println("Player wins");
                break;
            }
            if (draw(board))                    //if draw game over
            {
                System.out.println("Draw");
                break;
            }

            board = computerMove(board);        //computer move

            if (computerWin(board))             //if computer wins game over
            {
                System.out.println("Computer wins");
                break;
            }

            if (draw(board))                    //if draw game over
            {
                System.out.println("Draw");
                break;
            }
        }
    }

    //Method performs player move
    private Board playerMove(Board board)
    {
        System.out.print("Player move: ");      //prompt player

        Scanner scanner = new Scanner(System.in);       //read move
        int i = scanner.nextInt();
        int j = scanner.nextInt();

        board.array[i][j] = PLAYER;             //place player symbol

        displayBoard(board);                    //display board

        return board;                           //return updated board
    }

    //Method determines computer move
    private Board computerMove(Board board)
    {                                                           //generate children of board
        LinkedList<Board> children = generate(board, COMPUTER);

        int maxIndex = 0;
        int maxValue = minmax(children.get(0), MIN);
                                                            //find the child with largest minmax value
        for (int i = 1; i < children.size(); i++)
        {
            int currentValue = minmax(children.get(i), MIN);
            if (currentValue > maxValue)
            {
                maxIndex = i;
                maxValue = currentValue;
            }
        }

        Board result = children.get(maxIndex);              //choose the child as next move

        System.out.println("Computer move:");

        displayBoard(result);                               //print next move

        return result;                                      //print updated board
    }

    //Method computes minmax value of a board
    private int minmax(Board board, int level)
    {
        if (computerWin(board))             //if board is win for computer
            return 1;
        else if (playerWin(board))          //if board is win for player
            return -1;
        else if (draw(board))               //if board is draw
            return 0;
        else
        {
            if (level == MAX)               //if board is at max level
            {
                LinkedList<Board> children = generate(board, COMPUTER);
                                            //generate children of board
                int maxValue = minmax(children.get(0), MIN);
                                            //find maximum of minmax value of children
                for (int i = 0; i < children.size(); i++)
                {
                    int currentValue = minmax(children.get(i), MIN);
                    if (currentValue > maxValue)
                        maxValue = currentValue;
                }

                return maxValue;    //return maximum minmax value
            }
            else                    //if board is at min level
            {
                LinkedList<Board> children = generate(board, PLAYER);
                                    //generate children of board
                int minValue = minmax(children.get(0), MAX);
                                    //find minimum of minmax values of children
                for (int i = 1; i < children.size(); i++)
                {
                    int currentValue = minmax(children.get(i), MAX);

                    if (currentValue < minValue)
                        minValue = currentValue;
                }

                return minValue;        //return minimum minmax value
            }
        }
    }

    //Method generates children of board using a symbol
    private LinkedList<Board> generate(Board board, char symbol)
    {
        LinkedList<Board> children = new LinkedList<Board>();
                                        //empty list of children
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)  //go thru board
                if (board.array[i][j] == EMPTY)
                {                                       //if slot is empty
                    Board child = copy(board);          //put the symbol and
                    child.array[i][j] = symbol;         //create child board
                    children.addLast(child);
                }

        return children;                //return list of children
    }

    //Method checks whether computer wins
    private boolean computerWin(Board board)
    {
        return check(board, COMPUTER);      //check computer wins somewhere in a board
    }

    //Method checks whether player wins
    private boolean playerWin(Board board)
    {
        return check(board, PLAYER);        //check player wins somewhere in board
    }

    //Method checks whether board is draw
    private boolean draw(Board board)
    {                                       //check board is full and
        return full(board) && !computerWin(board) && !playerWin(board);
    }                                       //neither computer nor player wins

    //Method checks whether row, column, or diagonal is occupied by a symbol
    private boolean check(Board board, char symbol)
    {
        for (int i = 0; i < size; i++)      //check each row
            if (checkRow(board, i, symbol))
                return true;

        for (int i = 0; i < size; i++)      //check each column
            if (checkColumn(board, i, symbol))
                    return true;

        if (checkLeftDiagonal(board, symbol))   //check left diagonal
            return true;

        if (checkRightDiagonal(board, symbol))  //check right diagonal
            return true;

        return false;
    }

    //Method checks whether a row is occupied by a symbol
    private boolean checkRow(Board board, int i, char symbol)
    {
        for (int j = 0; j < size; j++)
            if (board.array[i][j] != symbol)
                return false;

        return true;
    }

    //Method checks whether a column is occupied by a symbol
    private boolean checkColumn(Board board, int i, char symbol)
    {
        for (int j = 0; j < size; j++)
            if (board.array[j][i] != symbol)
                return  false;

        return true;
    }

    //Method checks whether left diagonal is occupied a symbol
    private boolean checkLeftDiagonal(Board board, char symbol)
    {
        for (int i = 0; i < size; i++)
            if (board.array[i][i] != symbol)
                return false;

        return true;
    }

    //Method checks whether right diagonal is occupied by a symbol
    private boolean checkRightDiagonal(Board board, char symbol)
    {
        for (int i = 0; i < size; i++)
            if (board.array[i][size-1-i] != symbol)
                return false;

        return true;
    }

    //Method checks whether a board is full
    private boolean full(Board board)
    {
        for (int i = 0; i < size; i++)
            for (int j= 0; j < size; j++)
                if (board.array[i][j] == EMPTY)
                    return false;

        return true;
    }

    //Method makes copy of a board
    private Board copy(Board board)
    {
        Board result = new Board(size);     //create empty board

        for (int i = 0; i < size; i++)      //copy contents
            for (int j = 0; j < size; j++)
                result.array[i][j] = board.array[i][j];

        return result;                      //return copy
    }

    //Method displays a board
    private void displayBoard(Board board)
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
                System.out.print(board.array[i][j]);
            System.out.println();
        }
    }
}
