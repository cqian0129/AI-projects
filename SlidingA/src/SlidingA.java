/**
 * Created by chengqian on 11/4/16.
 */
import java.util.LinkedList;

//This program solves sliding puzzle using A* algorithm
public class SlidingA {
    //Board class (inner class)
    private class Board
    {
        private int[][] array;             //board array
        private int gvalue;                 //path cost
        private int hvalue;                 //heuristic value
        private int fvalue;                 //gvalue plus hvalue
        private Board parent;               //parent board

        //Constructor of board class
        private Board(int[][] array, int size)
        {
            this.array = new int[size][size];      //create board array

            for (int i = 0; i < size; i++)          //copy given array
                for (int j = 0; j < size; j++)
                    this.array[i][j] = array[i][j];

            this.gvalue = 0;                        //path cost is zero
            this.hvalue = heuristic(this);          //heuristic value of board
            this.fvalue = gvalue + hvalue;          //gvalue plus hvalue
            //this.fvalue = hvalue;                   //f = h
            //this.fvalue = gvalue;                     //f = g

            this.parent = null;                     //no parent
        }
    }

    private Board initial;                          //initial board
    private Board goal;                             //final board
    private int size;                               //board sie

    public long startTime;
    public long endTime;
    public long totalTime;

    //Constructor of SlidingA class
    public SlidingA(int[][] initial, int[][] goal, int size)
    {
        this.initial = new Board(initial, size);    //create initial board
        this.goal = new Board(goal, size);          //create final board
        this.size = size;
    }

    //Method solves sliding puzzle
    public void solve()
    {
        long startTime = System.currentTimeMillis();


        LinkedList<Board> openList = new LinkedList<Board>();   //open list
        LinkedList<Board> closedList = new LinkedList<Board>(); //closed list

        openList.addFirst(initial);     //add initial board to open list

        while (!openList.isEmpty())     //while open list has more boards
        {
            int best = selectBest(openList);    //select best board

            Board board = openList.remove(best);    //remove board

            closedList.addLast(board);          //add board to closed list
            //System.out.println("-----------------");
            //System.out.println(board.fvalue);
            //displayBoard(board);
            //System.out.println("-----------------");
            if (complete(board))                //if board is goal
            {
                displayPath(board);             //display path to goal

                long endTime   = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                System.out.println("startTime: " + startTime);

                System.out.println("endTime: " + endTime);

                System.out.println("totalTime: " + totalTime);

                return;                         //stop search
            }
            else
            {
                LinkedList<Board> children = generate(board);   //create children
                for (int i = 0; i < children.size(); i++)
                {                                       //for each child
                    Board child = children.get(i);
                                                        //if it is not in closed list

                    if (!exists(child, closedList))
                    {
                        if (!exists(child, openList))   //if it is not in open list add to open list
                            openList.addLast(child);
                        else                                //if it is already in open list
                        {
                            int index = find(child, openList);
                            if (child.fvalue < openList.get(index).fvalue)
                            {                               //if fvalue of new
                                openList.remove(index);     //copy is less than
                                openList.addLast(child);    //fvalue of old copy
                            }                               //replace old with
                        }                                   //new copy
                    }
                }
            }
        }

        System.out.println("no solution");                   //no solution if there are no boards in open list
    }

    //Method creates children of a board
    private LinkedList<Board> generate(Board board)
    {
        int i = 0, j = 0;
        boolean found = false;

        for (i = 0; i < size; i++)          //find location of empty slot of board
        {
            for (j = 0; j < size; j++)
                if (board.array[i][j] == 0)
                {
                    found = true;
                    break;
                }

                if (found)
                    break;
        }

        boolean north, south, east, west;       //decide whether empty slot
        north = i == 0 ? false : true;          //has N, S, E, W neighbors
        south = i == size-1 ? false : true;
        east = j == size-1 ? false: true;
        west = j == 0 ? false : true;

        LinkedList<Board> children = new LinkedList<Board>();   //list of children

        if (north) children.addLast(createChild(board, i, j, 'N'));     //add N, S, E, W children if they exist
        if (south) children.addLast(createChild(board, i, j, 'S'));
        if (east) children.addLast(createChild(board, i, j, 'E'));
        if (west) children.addLast(createChild(board, i, j, 'W'));

        return children;                        //return children
    }

    //Method creates a child of a board by swapping empty slot in a given direction
    private Board createChild(Board board, int i, int j, char direction)
    {
        Board child = copy(board);              //create copy of board

        if (direction == 'N')                   //swap empty slot to north
        {
            child.array[i][j] = child.array[i-1][j];
            child.array[i-1][j] = 0;
        }
        else if (direction == 'S')				//swap empty slot to south
        {
            child.array[i][j] = child.array[i+1][j];
            child.array[i+1][j] = 0;
        }
        else if (direction == 'E')				//swap empty slot to east
        {
            child.array[i][j] = child.array[i][j+1];
            child.array[i][j+1] = 0;
        }
        else                                    //swap empty slot to west
        {
            child.array[i][j] = child.array[i][j-1];
            child.array[i][j-1] = 0;
        }

        child.gvalue = board.gvalue + 1;        //parent path cost plus one

        child.hvalue = heuristic(child);        //heuristic value of child

        //child.fvalue = child.gvalue + child.hvalue; //gvalue plus hvalue
        child.fvalue = child.hvalue;            //f = h
        //child.fvalue = child.gvalue;              //f = g

        child.parent = board;                   //assign parent ot child

        return child;                           //return child
    }

    //Method computes heuristic value of board based misplaced values
    private int heuristic(Board board)
    {
        int value = 0;                          //initial heuristic value

        for (int i = 0; i < size; i++)          //go thru board and
            for (int j = 0; j < size; j++)      //count misplaced values
                if (board.array[i][j] != goal.array[i][j])
                    value += 1;

        return value;                           //return heuristic value
    }


    //Method locates the board with minimum fvalue in a list of boards
    private int selectBest(LinkedList<Board> list)
    {
        int minValue = list.get(0).fvalue;      //initialize minimum
        int minIndex = 0;                       //value and location

        for (int i = 0; i < list.size(); i++)
        {
            int value = list.get(i).fvalue;
            if (value < minValue)               //updates minimums if
            {                                   //board with smaller
                minValue = value;               //heuristic value is found
            }
        }

        return minIndex;                        //return minimum location
    }

    //Method creates copy of a board
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

    //Method finds location of a board in a list
    private int find(Board board, LinkedList<Board> list)
    {
        for (int i = 0; i < list.size(); i++)       //compare board with each
            if (identical(board, list.get(i)))      //element of list
                return i;

        return -1;
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
            list.addFirst(pointer);  	//add boards to beginning of list

            pointer = pointer.parent;	//keep going back
        }

        for (int i = 0; i < list.size(); i++)
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
