/**
 * Created by chengqian on 11/7/16.
 */
import java.util.LinkedList;
import java.util.Scanner;

public class NewGame {
    private final char EMPTY = ' ';     //empty slot
    private final char COMPUTER = 'X';  //computer
    private final char PLAYER = 'O';    //player
    private final int MIN = 0;          //min level
    private final int MAX = 1;          //max level
    private final int LIMIT = 5;        //depth limit

    //Board class (inner class)
    private class Board
    {
        private char[][] array;         //board array

        //Constructor of Board class
        private Board(int size)
        {
            array = new char[size][size];   //create array

            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++)
                    array[i][j] = EMPTY;
        }

        private int takePlayerValue()
        {
            int playerValue = 0;

            //2m
            //check each row
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size-1; j++){
                    if (array[i][j] == PLAYER && array[i][j+1] == PLAYER)
                        playerValue += 2;
                }
            //check each column
            for (int j = 0; j < size; j++)
                for (int i = 0; i < size-1; i++){
                    if (array[i][j] == PLAYER && array[i+1][j] == PLAYER)
                        playerValue += 2;
                }

            //3n
            //check each row
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size-2; j++){
                    if (array[i][j] == PLAYER && array[i][j+1] == PLAYER && array[i][j+2] == PLAYER)
                        playerValue += 3;
                }
            //check each column
            for (int j = 0; j < size; j++)
                for (int i = 0; i < size-2; i++){
                    if (array[i][j] == PLAYER && array[i+1][j] == PLAYER && array[i+2][j] == PLAYER)
                        playerValue += 3;
                }

            return playerValue;
        }

        private int takeComputerValue()
        {
            int computerValue = 0;

            //2m
            //check each row
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size-1; j++){
                    if (array[i][j] == COMPUTER && array[i][j+1] == COMPUTER)
                        computerValue += 2;
                }
            //check each column
            for (int j = 0; j < size; j++)
                for (int i = 0; i < size-1; i++){
                    if (array[i][j] == COMPUTER && array[i+1][j] == COMPUTER)
                        computerValue += 2;
                }

            //3n
            //check each row
            for (int i = 0; i < size; i++)
                for (int j = 0; j < size-2; j++){
                    if (array[i][j] == COMPUTER && array[i][j+1] == COMPUTER && array[i][j+2] == COMPUTER)
                        computerValue += 3;
                }
            //check each column
            for (int j = 0; j < size; j++)
                for (int i = 0; i < size-2; i++){
                    if (array[i][j] == COMPUTER && array[i+1][j] == COMPUTER && array[i+2][j] == COMPUTER)
                        computerValue += 3;
                }

            return computerValue;
        }
    }

    private Board board;                //game board
    private int size;                   //size of board

    //Constructor of NewGame class
    public NewGame(int size)
    {
        this.board = new Board(size);       //create game board
        this.size = size;                   //assign board size
    }

    //Method plays game
    public void play()
    {
        while(true)                         //computer and player take turns
        {
            board = playerMove(board);      //player move

            if (playerWin(board))           //if player wins game over
            {
                System.out.println("Player wins");
                break;
            }
            if (draw(board))                //if draw game over
            {
                System.out.println("Draw");
                break;
            }

            board = computerMove(board);    //computer move

            if(computerWin(board))          //if computer wins game over
            {
                System.out.println("Computer wins");
                break;
            }

            if(draw(board))                 //if draw game over
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

        Scanner scanner = new Scanner(System.in);       //reak move
        int i = scanner.nextInt();
        int j = scanner.nextInt();

        board.array[i][j] = PLAYER;             //place player symbol

        displayBoard(board);                    //display board

        return board;                           //return updated board
    }

    //Method determines computer move
    private Board computerMove(Board board)
    {
        LinkedList<Board> children = generate(board, COMPUTER); //generate children of board


        int maxIndex = 0;
        int maxValue = minmax(children.get(0), MIN, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);

        for (int i = 1; i< children.size(); i++)    //find the child with largest minmax value
        {
            int currentValue = minmax(children.get(i), MIN, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (currentValue > maxValue)
            {
                maxIndex = i;
                maxValue = currentValue;
            }
        }

        Board result = children.get(maxIndex);      //choose the child as next move

        System.out.println("Computer move: ");

        displayBoard(result);                       //print next move

        return result;                              //print updated board
    }

    //Method computes minmax value of a board
    private int minmax(Board board, int level, int depth, int alpha, int beta)
    {
        if (computerWin(board) || playerWin(board) || draw(board) || depth >= LIMIT)    //if board is win for computer
            return evaluate(board);    //if board leaf

        else if (level == MAX)          //if board is at max level
        {
            int maxValue = Integer.MIN_VALUE;

            LinkedList<Board> children = generate(board, COMPUTER);
                                        //generate children of board
            for (int i = 0; i < children.size(); i++){
                int currentValue = minmax(children.get(i), MIN, depth + 1, alpha, beta);
                                        //find maximum of minmax values of children
                if (currentValue > maxValue)
                    maxValue = currentValue;
                                        //if maximum exceeds beta stop
                if (maxValue >= beta)
                    return maxValue;
                                        //if maximum exceeds alpha update alpha
                if (maxValue >= alpha)
                    alpha = maxValue;
            }

            return maxValue;            //return maximum value
        }
        else
        {
            int minValue = Integer.MAX_VALUE;
            LinkedList<Board> children = generate(board, PLAYER);
                                        //generate children of board
            for (int i = 0; i < children.size(); i++){
                int currentValue = minmax(children.get(i), MAX, depth + 1, alpha, beta);
                                        //find minimum of minmax values of children
                if (currentValue < minValue)
                    minValue = currentValue;
                                        //if minimum is less than alpha stop
                if (minValue <= alpha)
                    return minValue;
                                        //if minimum is less than beta update beta
                if (minValue < beta)
                    beta = minValue;    //return minimum value
            }

            return minValue;            //return minimum value
        }
    }

    private LinkedList<Board> generate(Board board, char symbol)
    {
        LinkedList<Board> children = new LinkedList<Board>();
                                        //empty list of children
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)  //go thru board
                if (board.array[i][j] == EMPTY)
                {
                    Board child = copy(board);
                    child.array[i][j] = symbol;
                    children.addLast(child);
                }
        return children;                //return list of children
    }

    private boolean computerWin(Board board)
    {
        if (full(board))
            if (board.takeComputerValue() > board.takePlayerValue())
                return true;

        return false;
    }

    private boolean playerWin(Board board)
    {
        if (full(board))
            if (board.takePlayerValue() > board.takeComputerValue())
                return true;

        return false;
    }

    private boolean draw(Board board)
    {
        if (full(board))
            if (board.takePlayerValue() == board.takeComputerValue())
                return true;

        return false;
    }

    private boolean full(Board board)
    {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board.array[i][j] == EMPTY)
                    return false;

        return true;
    }

    //Method evaluates a board
    private int evaluate(Board board)
    {
        if (computerWin(board))
            return 4*size;
        else if (playerWin(board))
            return -4*size;
        else if (draw(board))
            return 3*size;
        else
            return count(board, COMPUTER) - count(board, PLAYER);
    }

    //Method counts possible scores a symbol can get
    private int count(Board board, char symbol)
    {
        int answer = 0;

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board.array[i][j] == EMPTY)
                    board.array[i][j] = symbol;

        if (symbol == COMPUTER)
            answer = board.takeComputerValue();
        else if (symbol == PLAYER)
            answer = board.takePlayerValue();

        return answer;
    }

    //Method makes copy of a board
    private Board copy(Board board)
    {
        Board result = new Board(size);     //create empty board

        for (int i = 0; i < size; i++)      //copy contents
            for (int j = 0; j < size; j++)
                result.array[i][j] = board.array[i][j];

        return result;
    }

    //Method displays a board
    private void displayBoard(Board board)
    {
        int humanScore = 0;
        int computerScore = 0;

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
                System.out.print(board.array[i][j]);
            System.out.println();
        }

        humanScore = board.takePlayerValue();
        computerScore = board.takeComputerValue();

        System.out.println("Score of human player: " + humanScore);
        System.out.println("Score of computer: " + computerScore);
    }
}
