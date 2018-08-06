/**
 * Created by chengqian on 11/4/16.
 */
import java.util.LinkedList;

//This program solves travelling slasman problem
//using greedy heuristic
public class Greedy {
    //Path class (inner class)
    private class Path
    {
        private LinkedList<Integer> list;           //vertices in path
        private int cost;                           //cost of path

        //Constructor of path class
        private Path()
        {
            list = new LinkedList<Integer>();       //empty list of vertices
            cost = 0;                               //cost is 0;
        }

        //Copy constructor
        private Path(Path other)
        {
            list = new LinkedList<Integer>();       //empty list of vertices

            for (int i = 0; i < other.list.size(); i++)     //copy vertices to list
                list.addLast(other.list.get(i));

            cost = other.cost;                      //copy cost
        }

        //Method adds vertex to path
        private void add(int vertex, int weight)
        {
            list.addLast(vertex);                   //add vertex at the end
            cost += weight;                         //increment cost
        }

        //Method finds last vertex of path
        private int last()
        {
            return list.getLast();                  //return cost
        }

        //Method finds length of path
        private int cost()
        {
            return cost;                            //return cost
        }

        //Method finds length of path
        private int size()
        {
            return list.size();                     //return length
        }

        //Method decides whether path contains a given vertex
        private boolean contains(int vertex)
        {
            for (int i = 0; i< list.size(); i++)    //compare vertex with
                if (list.get(i) == vertex)              //vertices of path
                    return true;

            return false;
        }

        //Method displays path and its cost
        private void display()
        {
            for (int i = 0; i < list.size(); i++)       //print path
                System.out.print(list.get(i) + 1 + " ");
            System.out.println(": " + cost);            //print cost
        }
    }

    private int size;                                   //number of vertices of graph
    private int[][] matrix;                             //adjacency matrix of graph
    private int initial;                                //starting/ending vertex

    public long startTime;
    public long endTime;
    public long totalTime;

    //Constructor of Greedy class
    public Greedy(int vertices, int[][] edges)
    {
        size = vertices;                                //assign vertices

        matrix = new int[size][size];                   //initialize adjacency matrix
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = 0;

        for (int i = 0; i < edges.length; i++)
        {
            int u = edges[i][0]-1;                        //place weights in adjacency
            int v = edges[i][1]-1;                        //matrix using edges
            int weight = edges[i][2];
            matrix[u][v] = weight;
            matrix[v][u] = weight;
        }

        initial = edges[0][0]-1;                          //pick a starting/ending vertex
    }

    //Method finds shortest cycle
    public void solve()
    {
        long startTime = System.currentTimeMillis();


        Path shortestPath = null;                       //initialize shortest cycle
        int minimumCost = Integer.MAX_VALUE;            //and minimum cost

        LinkedList<Path> list = new LinkedList<Path>();
                                                        //list of paths
        Path path = new Path();
        path.add(initial, 0);                           //create initial path and
        list.addFirst(path);                            //add to list

        while (!list.isEmpty())                         //while list has paths
        {
            path = list.removeFirst();                  //remove first path

            if (complete(path))                         //if path is cycle
            {
                if (path.cost() < minimumCost)          //if cost is less than minimum
                {
                    minimumCost = path.cost();          //update minimum
                    shortestPath = path;                //update cycle
                    System.out.println("find one");
                    break;

                }
            }
            else                                        //if path is not complete
            {
                LinkedList<Path> children = generate(path);
                                                        //generate children of path
                if (children.size()!=0)                   //if there are children
                {

                    //System.out.println(children.size());
                    int best = selectBest(children);    //find best child

                    list.addFirst(children.get(best));  //add child to list
                    //list.get(0).display();
                }
            }
        }

        if (shortestPath == null)                       //if there is no cycle
            System.out.println("no solution");          //then there is no solution
        else
            shortestPath.display();                     //otherwise display shortest
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
                                                        //children list
        int lastVertex = path.last();                   //find last vertex of path

        for (int i = 0; i < size; i++)                  //go thru all vertices
        {
            if (matrix[lastVertex][i] != 0)             //if vertex is neighbor
            {
                if (i == initial)                       //if vertex is initial vertex
                {
                    if (path.size() == size)            //if path has size vertices
                    {
                        Path child = new Path(path);        //create copy of path

                        child.add(i, matrix[lastVertex][i]);
                                                            //add vertex to path
                        children.addLast(child);            //add extended path to
                    }                                       //children list
                }
                else                                        //if vertex is not initial vertex
                {
                    if (!path.contains(i))                  //if vertex is not in path
                    {
                        Path child = new Path(path);        //create copy of path

                        child.add(i, matrix[lastVertex][i]);
                                                            //add extended path to
                        children.addLast(child);            //children list
                    }
                }
            }
        }

        return children;                                    //return children list
    }

    //Method locates the path with minimum cost in a list of paths
    private int selectBest(LinkedList<Path> list)
    {

        int minValue = list.get(0).cost;                    //initialize minimum
        int minIndex = 0;                                   //value and location



        for (int i =0; i < list.size(); i++)
        {
            int value = list.get(i).cost;
            if (value < minValue)                           //updates minimums if
            {                                               //board with smaller
                minValue = value;                           //heuristic value is found
                minIndex = i;
            }
        }

        return minIndex;                                    //return minimum location
    }

    //Method decides whether a path is complete
    boolean complete(Path path)
    {
        return path.size() == size + 1;     //check path has size+1 number of vertices
    }



}
