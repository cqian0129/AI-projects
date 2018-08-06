/**
 * Created by chengqian on 11/6/16.
 */
import java.util.LinkedList;

//This programfinds shortest path using A* algorithm
public class ShortestA {
    //Node class (inner class)
    private class Node {
        private int id;         //vertex id
        private int gvalue;     //path cost
        private int hvalue;     //heuristic value
        private int fvalue;     //gvalue plus hvalue
        private Node parent;    //parent of vertex

        //Constructor of node class
        private Node(int id)
        {
            this.id = id;       //assign vertex id
            this.gvalue = 0;    //path cost is zero
            this.hvalue = heuristic(this);  //heuristic value of node
            this.fvalue = gvalue + hvalue;  //gvalue plus hvalue
            this.parent = null;         //no parent
        }
    }

    private int[][] matrix;     //adjacency amtrix of graph
    private int size;           //number of vertices
    private Node initial;       //initial node
    private Node goal;          //goal node

    //Constructor of ShortestA class
    public ShortestA(int vertices, int[][] edges, int initial, int goal)
    {
        size = vertices;            //assign vertices

        matrix = new int[size][size];       //initialize adjacency matrix
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = 0;

        for (int i = 0; i < edges.length; i++)      //place weights in adjacency
        {                                           //matrix using edges
            int u = edges[i][0];
            int v = edges[i][1];
            matrix[u][v] = matrix [v][u] = edges[i][2];
        }

        this.initial = new Node(initial);       //create initial node
        this.goal = new Node(goal);             //create goal node
    }

    //Method finds shortest path
    public void solve()
    {
        LinkedList<Node> openList = new LinkedList<Node>(); //open list
        LinkedList<Node> closedList = new LinkedList<Node>();   //closed list

        openList.addFirst(initial);         //add initial node to open list

        while(!openList.isEmpty())          //while open list has more nodes
        {
            int best = selectBest(openList);    //select best node

            Node node = openList.remove(best);  //remove node

            closedList.addLast(node);           //add node to closed list

            if (complete(node))
            {
                displayPath(node);              //display path ot goal
                return;                         //stop search
            }
            else
            {
                LinkedList<Node> children = generate(node);
                                                        //create children
                for (int i = 0; i < children.size(); i++)
                {                                       //for each child
                    Node child = children.get(i);

                    if(!exists(child, closedList))      //if it is not in closed list
                    {
                        if (!exists(child, openList))   //if it is not in open list
                            openList.addLast(child);
                        else                            //if it is already in open list
                        {
                            int index = find(child, openList);
                            if (child.fvalue < openList.get(index).fvalue)  //if fvalue of new copy is less than
                            {                                               //fvalue of old copy replace old with new copy
                                openList.remove(index);
                                openList.addLast(child);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("no solution");      //no solution if there are no nodes in open list
    }

    //Method creates children of node
    private LinkedList<Node> generate(Node node)
    {
        LinkedList<Node> children = new LinkedList<Node>();     //list of children

        for (int i = 0; i < size; i++)                  //go thru adjacency matrix
            if (matrix[node.id][i] != 0)                //and determine neighbors
            {
                Node child = new Node(i);               //create node for neighbor

                                                        //parent path cost plus edge
                child.gvalue = node.gvalue + matrix[node.id][i];
                                                        //heuristic value of child
                child.hvalue = heuristic(child);
                                                        //gvalue plus hvalue
                child.fvalue = child.gvalue + child.hvalue;

                child.parent = node;            //assign parent ot child

                children.addLast(child);        //add to children list
            }

        return children;                        //return children list
    }

    //Method computes heuristic value of node
    private int heuristic(Node node)
    {
        return 0;
    }

    //Method locates the node with minimum fvalue in a list of nodes
    private int selectBest(LinkedList<Node> list)
    {
        int minValue = list.get(0).fvalue;              //initialize minimum

        int minIndex = 0;           //value and location

        for (int i = 0; i < list.size(); i++)
        {
            int value = list.get(i).fvalue;
            if (value < minValue)           //updatess minimums if node with smaller
            {                               //node with smaller
                minValue = value;           //heuristic value is found
                minIndex = i;
            }
        }

        return minIndex;                    //return minimum locaiton
    }

    //Method decides whether a ndoe is goal
    private boolean complete(Node node)
    {
        return identical(node, goal);           //compare node with goal
    }

    //Method decides whether a node is in a list
    private boolean exists(Node node; LinkedList<Node> list)
    {
        for (int i = 0; i < list.size(); i++)       //compare node with each
            if (identical(node, list.get(i)))       //element of list
                return true;

        return false;
    }

    //Method finds location of a node in a list
    private int find(Node node, LinkedList<Node> list)
    {
        for (int i = 0; i < list.size(); i++)       //compare node with each
            if (identical(node, list.get(i)))           //element of list
                return i;

        return -1;
    }

    //Method decides whether two nodes are identical
    private boolean identical(Node p, Node q)
    {
        return p.id == q.id;                     //compare vertex id of nodes
    }

    //Method displays path from initial to current node
    private void displayPath(Node node)
    {
        LinkedList<Node> list = new LinkedList<Node>();

        Node pointer = node;        //start at current node

        while (pointer != null)     //go back towards initial node
        {
            list.addFirst(pointer);     //add nodes to list

            pointer = pointer.parent;       //keep going back
        }

        for (int i = 0; i < list.size(); i++)
            displayNode(list.get(i));
                                            //print path cost

        System.out.println(": " + list.getLast().gvalue);
    }

    //Method displays node


}
