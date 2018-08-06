/**
 * Created by chengqian on 10/15/16.
 */
import java.util.LinkedList;

public class Queens
{
    //Board class (inner class)
    public class Board
    {
        private char[][] array;		//array
        private int rows;			//filled rows

        //Constructor of board class
        private Board(int size)
        {
            array = new char[size][size];	//create array

            for (int i = 0; i < size; i++)	//initialize array
                for (int j = 0; j < size; j++)	//to blanks
                    array[i][j] = ' ';

            rows = 0;		//zero filled rows
        }
    }

    private int size;		//size of board

    //Constructor of queens class
    public Queens(int size)
    {
        this.size = size;		//assign size
    }

    //Method solves queens problem
    public void solve()
    {
        LinkedList<Board> list = new LinkedList<Board>(); //list of boards

        Board board = new Board(size);		//create empty board and add to list
        list.addFirst(board);

        while (!list.isEmpty())		//while list has boards
        {
            board = list.removeFirst();  //remove first board

            if (complete(board))	//if board is a solution
            {
                display(board);		//display board and stop
                return;
            }
            else					//if board is not a solution
            {
                LinkedList<Board> children = generate(board);
                //generate children
                for (int i = 0; i < children.size(); i++)		//add children at
                    list.addFirst(children.get(i));				//beginning of list
            }
        }

        System.out.println("no solution");  			//if there are no boards in list
        //then there is no solution
    }

    //Method generates children of a board
    private LinkedList<Board> generate(Board board)
    {
        LinkedList<Board> children = new LinkedList<Board>();	//children list

        for (int i = 0; i < size; i++)		//generate children
        {
            Board child = copy(board);		//create copy of parent

            child.array[child.rows][i] = 'Q';	//put queen next row

            child.rows += 1;		//increment filled rows

            if (check(child, child.rows-1, i))	//if the added queen does not
                children.addLast(child);		//cause conflict add board to
        }										//children list

        return children;						//return list of children
    }

    //Method checks whether queen at a given location causes conflict
    private boolean check(Board board, int x, int y)
    {
        for (int i= 0; i < size; i++)		//go thru all locations of board
            for (int j = 0; j < size; j++)
            {
                if (board.array[i][j] == ' ')	//if empty location ignore
                    ;
                else if (x == i && y == j)		//if same location ignore
                    ;
                else if (x == i || y == j || x+y == i+j || x-y == i-j)
                    return false;				//queen in the row, column,
                //or diagonal causes conflict
            }

        return true;							//no conflicts
    }

    //Method checks whether board is complete
    private boolean complete(Board board)
    {
        //check number filled rows equals board size
        return (board.rows == size);
    }

    //Method makes copy of board
    private Board copy(Board board)
    {
        Board result = new Board(size);		//create empty board

        for (int i = 0; i< size; i++)		//copy given board to empty board
            for (int j = 0; j < size; j++)
                result.array[i][j] = board.array[i][j];

        result.rows = board.rows;			//copy filled rows

        return result;						//return copy
    }

    //Method displays board
    private void display(Board board)
    {
        for (int j = 0; j < size; j++)		//top horizontal line
            System.out.print("--");

        System.out.println();

        for (int i = 0; i < size; i++)		//for every row
        {
            System.out.print("|");			//first vertical line

            for (int j = 0; j < size; j++)	//every slot and vertical line
                System.out.print(board.array[i][j] + "|");

            System.out.println();			//next line

            for (int j = 0; j< size; j++)	//horizontal line
                System.out.print("--");

            System.out.println(); 			//next line
        }
    }
}
