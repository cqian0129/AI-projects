import java.util.LinkedList;

//This program solves sliding puzzle
public class Sliding
{
    //Board class (inner class)
    private class Board
    {
        private char[][] array;		//board array
        private int size;			//board size
        private Board parent;		//parent board

        //Constructor of board class
        private Board(char[][] array, int size)
        {
            this.array = new char[size][size];	//create board array

            for (int i = 0; i < size; i++)		//copy given array
                for (int j = 0; j < size; j++)
                    this.array[i][j] = array[i][j];

            this.size =size;					//assign size

            this.parent = null;					//no parent
        }
    }

    private Board initial;			//initial board
    private Board goal;				//final board
    private int size; 				//board size

    //Constructor of sliding class
    public Sliding(char[][] initial, char[][] goal, int size)
    {
        this.initial = new Board(initial, size); 	//create initial board
        this.goal = new Board(goal, size);			//create final board
        this.size = size;
    }

    //Method solves sliding puzzle
    public void solve()
    {
        LinkedList<Board> openList = new LinkedList<Board>();	//open list
        LinkedList<Board> closedList = new LinkedList<Board>();	//closed list

        openList.addFirst(initial);		//add initial board to open list

        while (!openList.isEmpty())		//while open list has more boards
        {
            Board board = openList.removeFirst(); 	//remove first board

            closedList.addFirst(board);  			//add board to closed list

            if (complete(board))
            {
                displayPath(board);					//display path to goal
                return;								//stop search
            }
            else
            {
                LinkedList<Board> children = generate(board);//create children

                //int children_length = children.size();

                for (int i = 0; i < children.size(); i++)
                {

                    Board child = children.get(i);	//for each child

                    if(!exists(child, openList) && !exists(child, closedList))
                        openList.addLast(child); 		//if it is not in open or closed list then add to end of open list
                }
            }
        }

        System.out.println("no solution"); 				//no solution if there are no boards in open list
    }

    //Method creates children of a board
    private LinkedList<Board> generate(Board board)
    {
        int i = 0, j = 0;
        boolean found = false;

        for (i = 0; i < size; i++)				//find location of empty slot of board
        {
            for (j = 0; j < size; j++)
                if (board.array[i][j] == ' ')
                {
                    found = true;
                    break;
                }

            if (found)
                break;
        }

        boolean north, south, east, west;		//decide whether empty slot has N, S, E, W neighbors
        north = i == 0 ? false : true;
        south = i == size-1 ? false : true;
        east = j == size-1 ? false : true;
        west = j == 0 ? false : true;

        LinkedList<Board> children = new LinkedList<Board>(); //list of children

        if (north) children.addLast(createChild(board, i, j, 'N')); //add N, S, E, W children if they exist
        if (south) children.addLast(createChild(board, i, j, 'S'));
        if (east) children.addLast(createChild(board, i, j, 'E'));
        if (west) children.addLast(createChild(board, i, j, 'W'));

        return children;			//return children
    }

    //Method creates a child of a board by swapping empty slot in a given direction
    private Board createChild(Board board, int i, int j, char direction)
    {
        Board child = copy(board);				//create copy of board

        if (direction == 'N') 					//swap empty slot to north
        {
            child.array[i][j] = child.array[i-1][j];
            child.array[i-1][j] = ' ';
        }
        else if (direction == 'S')				//swap empty slot to south
        {
            child.array[i][j] = child.array[i+1][j];
            child.array[i+1][j] = ' ';
        }
        else if (direction == 'E')				//swap empty slot to east
        {
            child.array[i][j] = child.array[i][j+1];
            child.array[i][j+1] = ' ';
        }
        else                                    //swap empty slot to west
        {
            child.array[i][j] = child.array[i][j-1];
            child.array[i][j-1] = ' ';
        }
        child.parent = board;			//assign parent to child

        return child;					//return child
    }

    //method creates copy of a board
    private Board copy(Board board)
    {
        return new Board(board.array, size);
    }

    //Method decides whether a board is complete
    private boolean complete(Board board)
    {
        return identical(board, goal);		//compare board with goal
    }

    //Method decides whether a board exists in a list
    private boolean exists(Board board, LinkedList<Board> list)
    {
        for (int i = 0; i < list.size(); i++)		//compare board with each element of list
            if (identical(board, list.get(i)))
                return true;

        return false;
    }

    //Method decides whether two boards are identical
    private boolean identical(Board p, Board q)
    {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (p.array[i][j] != q.array[i][j])
                    return false;		//if there is a mismatch then false

        return true;					//otherwise true
    }

    //Method displays path from initial to current board
    private void displayPath(Board board)
    {
        LinkedList<Board> list = new LinkedList<Board>();

        Board pointer = board;			//start at current board

        while (pointer != null)			//go back towards initial board
        {
            list.addLast(pointer);  	//add boards to list

            pointer = pointer.parent;	//keep moving back
        }

        for (int i = list.size()-1; i >= 0; i--)
            displayBoard(list.get(i));
    }

    //Method displays board
    private void displayBoard(Board board)
    {
        for (int i = 0; i < size; i++)
        {
            for (int j= 0; j < size; j++)
                System.out.print(board.array[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
}
