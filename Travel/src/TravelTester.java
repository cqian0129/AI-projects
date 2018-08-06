/**
 * Created by chengqian on 10/15/16.
 */
public class TravelTester
{
    //main method to test travelling salesman solver
    public static void main(String[] args)
    {
        //edges of specific graph, 5 vertices, vertices numbered from 0
        /*
        int[][] edges = {{3, 4, 7},
                         {2, 1, 6},
                         {0, 3, 9},
                         {2, 4, 4},
                         {4, 0, 10},
                         {2, 3, 5},
                         {1, 3, 3},
                         {0, 1, 2},

        };
*/
        int[][] edges = {{6, 8, 10},
                         {2, 4, 24},
                         {4, 5, 28},
                         {1, 2, 25},
                         {3, 4, 23},
                         {2, 8, 27},
                         {3, 7, 33},
                         {5, 7, 32},
                         {3, 5, 19},
                         {2, 7, 31},
                        {1, 3, 31},
                        {4, 7, 17},
                        {7, 8, 19},
                        {1, 4, 18},
                        {4, 8, 14},
                        {1, 5, 36},
                        {3, 8, 15},
                        {6, 7, 30},
                        {1, 6, 27},
                        {3, 6, 20},
                        {2, 5, 35},
                        {1, 7, 12},
                        {5, 6, 36},
                        {1, 8, 20},
                        {2, 3, 14},
                        {4, 6, 22},
                        {2, 6, 18},
                        {5, 8, 20},

        };

        /*
        int[][] edges = {{4, 13, 20},
                        {10, 18, 35},
                        {2, 6, 15},
                        {17, 19, 22},
                        {1, 5, 34},
                        {3, 12, 18},
                        {2, 20, 26},
                        {13, 20, 37},
                        {3, 19, 18},
                        {2, 8, 14},
                        {5, 14, 12},
                        {8, 15, 23},
                        {6, 9, 37},
                        {3, 14, 27},
                        {7, 11, 31},
                        {8, 11, 26},
                        {4, 18, 14},
                        {9, 14, 12},
                        {1, 4, 27},
                        {10, 15, 35},
                        {1, 16, 32},
                        {7, 12, 16},
                        {9, 19, 38},
                        {11, 17, 17},
                        {7, 16, 20},
                        {5, 13, 20},
                        {6, 15, 10},


        };
*/
        //find a travelling salesman cycly
        Travel t = new Travel(8, edges);


        long startTime = System.currentTimeMillis();


        t.solve();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        //System.out.println("time: " + totalTime);
    }
}
