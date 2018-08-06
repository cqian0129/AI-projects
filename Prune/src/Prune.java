/**
 * Created by chengqian on 10/21/16.
 */
import java.util.LinkedList;

public class Prune {
    //Path class (inner class)
    private class Path
    {
        private LinkedList<Integer> list;       //vertices in path
        private int cost;                       //cost of path

        //Constructor of path class
        private Path()
        {
            list = new LinkedList<Integer>();       //empty list of vertices
            cost = 0;
        }

        //copy constructor
        private Path(Path other)
        {
            list = new LinkedList<Integer>();       //empty list of vertices

            for (int i = 0; i < other.list.size(); i++)     //copy vertices to list
                list.addLast(other.list.get(i));

            cost = other.cost;          //copy cost
        }

        //Method adds vertex to path
        private void add(int vertex, int weight)
        {
            list.addLast(vertex);               //add vertex at the end
            cost += weight;                     //increment cost
        }

        //Method finds last vertex of path
        private int last()
        {
            return list.getLast();
        }

        //Method finds cost of path
        private int cost()
        {
            return cost;
        }

        //Method fidns length of path
        private int size()
        {
            return list.size();
        }

        //Method decides whether path contains a given vertex
        private boolean contains(int vertex)
        {
            for (int i = 0; i < list.size(); i++)           //compare vertex with
                if (list.get(i) == vertex)                  //vertices of path
                    return true;

            return false;
        }

        //Method displays path and its cost
        private void display()
        {
            for (int i = 0; i < list.size(); i++)           //print path
                System.out.print(list.get(i) + 1 + " ");
            System.out.println(": " + cost);            //print cost
        }
    }

    private int size;                   //number of vertices of graph
    private int[][] matrix;             //adjacency matrix of graph
    private int initial;                //starting/ending vertex

    public long startTime;
    public long endTime;
    public long totalTime;

    //Constructor of Prune class
    public Prune(int vertices, int[][] edges)
    {
        size = vertices;                //assign vertices

        matrix = new int[size][size];   //initialize adjacency matrix
        for (int i = 0; i< size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = 0;

        for (int i = 0; i < edges.length; i++)
        {

            int u = edges[i][0]-1;            //place weights in adjacency
            int v = edges[i][1]-1;            //matrix using edges
            int weight = edges[i][2];
            matrix[u][v] = weight;
            matrix[v][u] = weight;
        }

        initial = edges[0][0]-1;              //pick a starting/ending vertex
    }

    //Method finds shortest cycle
    public void solve()
    {
        long startTime = System.currentTimeMillis();


        Path shortestPath = null;           //initialize shortest cycle
        int minimumCost = Integer.MAX_VALUE;//and minimum cost

        LinkedList<Path> list = new LinkedList<Path>();
                                                //list of paths
        Path path = new Path();
        path.add(initial, 0);                   //create initial path adn

        System.out.println(initial);



        list.addFirst(path);                    //add to list

        while (!list.isEmpty())                 //while list has paths
        {
            path = list.removeFirst();          //remove first path

            //``````````````````````````````````````````````



            if (complete(path))
            {
                if (path.cost() < minimumCost)  //if cost is less than minimum
                {
                    minimumCost = path.cost();  //update minimum
                    shortestPath = path;        //update path

                    System.out.println("-----------");
                    System.out.println(list.size());
                    shortestPath.display();

                }
            }
            else                                //if path is not complete
            {
                if (path.cost() < minimumCost)  //if partial path cost is less than minimum cycle cost
                {
                    LinkedList<Path> children = generate(path);     //generate children of path

                    for (int i = 0; i < children.size(); i++) {
                        list.addFirst(children.get(i));
                    }

                }                                                   //add children to beginning of list
            }
        }

        if (shortestPath == null)                   //if there is no cycle
        {
            System.out.println("no solution");      //then there is no solution
        }
        else
            shortestPath.display();                 //otherwise display shortest
                                                    //cycle

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("startTime: " + startTime);

        System.out.println("endTime: " + endTime);

        System.out.println("totalTime: " + totalTime);

    }

    //Method generates children of path
    private LinkedList<Path> generate(Path path)
    {
        LinkedList<Path> children = new LinkedList<Path>();
                                                        //children lsit
        int lastVertex = path.last();                   //find last vertex of path

        for (int i = 0; i< size; i++)                   //go throu all vertices
        {

            if (matrix[lastVertex][i] != 0)             //if vertex is neighbor
            {
                if (i == initial)                       //if vertex is initial vertex
                {
                    if (path.size() == size)            //if path has size vertices
                    {
                        Path child = new Path(path);    //create copy of path

                        child.add(i, matrix[lastVertex][i]);    //add extended path to children list

                        children.addLast(child);
                    }
                }
                else                                    //if vertex is not initial vertex
                {
                    if (!path.contains(i))              //if vertex is not in path
                    {
                        Path child = new Path(path);        //create copy of path

                        child.add(i, matrix[lastVertex][i]);
                                                            //add extended path to
                        children.addLast(child);            //children list
                    }
                }
            }
        }

        return children;                //return children list
    }

    //Method decides whether a path is complete
    boolean complete(Path path)
    {
        return path.size() == size + 1;     //check path has size+1 number of vertices
    }
}