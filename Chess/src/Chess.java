/**
 * Created by chengqian on 11/8/16.
 */
import java.util.LinkedList;
import java.util.Scanner;

//This program plays chess game
public class Chess {
    private final char EMPTY = ' ';     //empty slot
    private final int MIN = 0;          //min level
    private final int MAX = 1;          //max level
    private final int LIMIT = 5;        //depth limit
    private final char COMPUTER_ROCK = 'R';
    private final char COMPUTER_KING = 'K';
    private final char COMPUTER_BISHOP = 'B';
    private final char PLAYER_ROCK = 'r';
    private final char PLAYER_KING = 'k';
    private final char PLAYER_BISHOP = 'b';

    //Board class (inner class)
    public class Board
    {
        private char[][] array;         //board array

        //Constructor of Board class
        private Board(int size)
        {
            array = new char[size][size];   //create array

            for (int i = 0; i < size; i++)  //fill with empty slots
                for (int j = 0; j < size; j++)
                    array[i][j] = EMPTY;

            array[0][0] = COMPUTER_ROCK;
            array[0][1] = COMPUTER_KING;
            array[0][3] = COMPUTER_BISHOP;
            array[0][4] = COMPUTER_ROCK;
            array[0][5] = COMPUTER_BISHOP;

            array[5][0] = PLAYER_BISHOP;
            array[5][1] = PLAYER_ROCK;
            array[5][2] = PLAYER_ROCK;
            array[5][3] = PLAYER_KING;
            array[5][5] = PLAYER_BISHOP;
        }
    }

    private Board board;        //game board
    private int size;           //size of board

    //Constructor of chess class
    public Chess(int size)
    {
        this.board = new Board(size);   //create game board
        this.size = size;               //assign board size
    }

    //Method plays game
    public void play()
    {
        while(true)                     //computer and player take turns
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

            if (computerWin(board))         //if computer wins game over
            {
                System.out.println("Computer wins");
                break;
            }
            if (draw(board))                //if draw game over
            {
                System.out.println("Draw");
            }
        }
    }

    //Method performs player move
    private Board playerMove(Board board)
    {
        System.out.println("Player please move b, r, k pieces!");
        System.out.print("Player move: ");     //prompt player

        Scanner scanner = new Scanner(System.in);   //read move
        int i = scanner.nextInt();
        int j = scanner.nextInt();
        int m = scanner.nextInt();
        int n = scanner.nextInt();

        if (check(board, i, j, m, n)) {
            board.array[m][n] = board.array[i][j];

            board.array[i][j] = EMPTY;
        }

        displayBoard(board);

        return board;
    }

    //Method determines computer move
    private Board computerMove(Board board)
    {
        LinkedList<Board> childrenRock = generate(board, COMPUTER_ROCK);
        LinkedList<Board> childrenBishop = generate(board, COMPUTER_BISHOP);
        LinkedList<Board> childrenKing = generate(board, COMPUTER_KING);

        LinkedList<Board> children = new LinkedList<Board>();
        children.addAll(childrenRock);
        children.addAll(childrenBishop);
        children.addAll(childrenKing);

        int maxIndex = 0;
        int maxValue = minmax(children.get(0), MIN, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);

        for (int i = 1; i < children.size(); i++)
        {
            int currentValue = minmax(children.get(i), MIN, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (currentValue > maxValue)
            {
                maxIndex = i;
                maxValue = currentValue;
            }
        }

        Board result = children.get(maxIndex);

        System.out.println("Computer move:");

        displayBoard(result);

        return result;
    }

    //Method computes minmax value of a board
    private int minmax(Board board, int level, int depth, int alpha, int beta)
    {
        if (computerWin(board) || playerWin(board) || draw(board) || depth >= LIMIT)
            return evaluate(board);

        else if (level == MAX)
        {
            int maxValue = Integer.MIN_VALUE;

            LinkedList<Board> childrenRock = generate(board, COMPUTER_ROCK);
            LinkedList<Board> childrenBishop = generate(board, COMPUTER_BISHOP);
            LinkedList<Board> childrenKing = generate(board, COMPUTER_KING);

            LinkedList<Board> children = new LinkedList<Board>();
            children.addAll(childrenRock);
            children.addAll(childrenBishop);
            children.addAll(childrenKing);


            for (int i = 0; i < children.size(); i++){
                int currentValue = minmax(children.get(i), MIN, depth + 1, alpha, beta);

                if (currentValue > maxValue)
                    maxValue = currentValue;

                if (maxValue >= beta)
                    return maxValue;

                if (maxValue > alpha)
                    alpha = maxValue;
            }

            return maxValue;
        }
        else
        {
            int minValue = Integer.MAX_VALUE;

            LinkedList<Board> childrenRock = generate(board, PLAYER_ROCK);
            LinkedList<Board> childrenBishop = generate(board, PLAYER_BISHOP);
            LinkedList<Board> childrenKing = generate(board, PLAYER_KING);

            LinkedList<Board> children = new LinkedList<Board>();
            children.addAll(childrenRock);
            children.addAll(childrenBishop);
            children.addAll(childrenKing);

            for (int i = 0; i < children.size(); i++){
                int currentValue = minmax(children.get(i), MAX, depth + 1, alpha, beta);

                if (currentValue < minValue)
                    minValue = currentValue;

                if (minValue <= alpha)
                    return minValue;

                if (minValue < beta)
                    beta = minValue;
            }

            return minValue;
        }
    }

    private LinkedList<Board> generate(Board board, char symbol)
    {
        LinkedList<Board> children = new LinkedList<Board>();


        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board.array[i][j] == symbol){
                    for (int m = 0; m < size; m++)
                        for (int n = 0; n < size; n++)
                            if (check(board, i, j, m, n)){
                                Board child = copy(board);                    //put the symbol and
                                child.array[m][n] = child.array[i][j];        //create child board
                                child.array[i][j] = EMPTY;
                                children.addLast(child);
                            }
                }


        return children;
    }

    private boolean contain(Board board, char symbol)
    {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board.array[i][j] == symbol)
                    return true;

        return false;
    }

    private boolean check(Board board, int i, int j, int m, int n){
        if (board.array[i][j] == COMPUTER_BISHOP)
            if (board.array[m][n] != COMPUTER_BISHOP && board.array[m][n] != COMPUTER_KING && board.array[m][n] != COMPUTER_ROCK)
                if ((i-m == j-n && (i-m == 1 || i-m == -1)) || (i+j == m+n && (i-m == 1 || i-m == -1)))
                    return true;

        if (board.array[i][j] == PLAYER_BISHOP)
            if (board.array[m][n] != PLAYER_KING && board.array[m][n] != PLAYER_BISHOP && board.array[m][n] != PLAYER_ROCK)
                if ((i-m == j-n && (i-m == 1 || i-m == -1)) || (i+j == m+n && (i-m == 1 || i-m == -1)))
                    return true;

        if (board.array[i][j] == COMPUTER_ROCK)
            if (board.array[m][n] != COMPUTER_BISHOP && board.array[m][n] != COMPUTER_KING && board.array[m][n] != COMPUTER_ROCK)
                if ((i == m && (n-j == 1 || n-j == -1)) || (j == n && (i-m == 1 || i-m == -1)))
                    return true;

        if (board.array[i][j] == PLAYER_ROCK)
            if (board.array[m][n] != PLAYER_ROCK && board.array[m][n] != PLAYER_BISHOP && board.array[m][n] != PLAYER_KING)
                if ((i == m && (n-j == 1 || n-j == -1)) || (j == n && (i-m == 1 || i-m == -1)))
                    return true;

        if (board.array[i][j] == COMPUTER_KING)
            if (board.array[m][n] != COMPUTER_BISHOP && board.array[m][n] != COMPUTER_KING && board.array[m][n] != COMPUTER_ROCK)
                if (((i-m == j-n && (i-m == 1 || i-m == -1)) || (i+j == m+n && (i-m == 1 || i-m == -1))) || ((i == m && (n-j == 1 || n-j == -1)) || (j == n && (i-m == 1 || i-m == -1))))
                    return true;

        if (board.array[i][j] == PLAYER_KING)
            if (board.array[m][n] != PLAYER_KING && board.array[m][n] != PLAYER_BISHOP && board.array[m][n] != PLAYER_ROCK)
                if (((i-m == j-n && (i-m == 1 || i-m == -1)) || (i+j == m+n && (i-m == 1 || i-m == -1))) || ((i == m && (n-j == 1 || n-j == -1)) || (j == n && (i-m == 1 || i-m == -1))))
                    return true;

        return false;
    }

    private boolean computerWin(Board board){
        return !contain(board, PLAYER_KING);
    }

    private boolean playerWin(Board board){
        return !contain(board, COMPUTER_KING);
    }

    private boolean draw(Board board){
        return false;
    }

    private Board copy(Board board){
        Board result = new Board(size);

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result.array[i][j] = board.array[i][j];

        return result;
    }

    public void displayBoard(Board board){
        //System.out.println("------------");
        for (int i = 0; i < size; i++)
        {
            System.out.println("------------");
            for (int j = 0; j< size; j++)
                System.out.print(board.array[i][j] + "|");
            System.out.println();
        }
    }

    //Method evaluates a board
    private int evaluate(Board board){
        if (computerWin(board))
            return 4*size;
        else if (playerWin(board))
            return -4*size;
        else if (draw(board))
            return 3*size;
        else
            return count(board, COMPUTER_KING) + count(board, COMPUTER_BISHOP) + count(board, COMPUTER_ROCK) - count(board, PLAYER_KING) - count(board, PLAYER_BISHOP) - count(board, PLAYER_ROCK);
    }

    //Method counts possible ways a symbol can win
    private int count(Board board, char symbol){
        int answer = 0;

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board.array[i][j] == symbol) {
                    for (int m = 0; m < size; m++)
                        for (int n = 0; n < size; n++)
                            if (check(board, i, j, m, n) && (board.array[m][n] == COMPUTER_KING || board.array[m][n] == PLAYER_KING)) {
                                answer += 10;
                            }
                            else if (check(board, i, j, m, n) && (board.array[m][n] == COMPUTER_BISHOP || board.array[m][n] == PLAYER_BISHOP)) {
                                answer += 2;
                            }
                            else if (check(board, i, j, m, n) && (board.array[m][n] == COMPUTER_ROCK || board.array[m][n] == PLAYER_ROCK)) {
                                answer += 2;
                            }
                }

        return answer;
    }
}
