/**
 * Created by chengqian on 11/4/16.
 */
public class SlidingATester {
    //main method to test sliding board solver
    public static void main(String[] args)
    {
        //initial board

/*        char[][] initial = {
                {'5', '7', '1'},
                {'2', ' ', '8'},
                {'4', '6', '3'}
        };

        char[][] initial = {
                {'2', '7', ' ', '8'},
                {'10', '4', '14', '5'},
                {'1', '13', '11', '15'},
                {'12', '6', '9', '3'}

        };*/

        int[][] initial = {
                {2, 7, 0, 8},
                {10, 4, 14, 5},
                {1, 13, 11, 15},
                {12, 6, 9, 3}

        };



        //final board

/*        char[][] goal = {
                {'1', '4', '8'},
                {'5', ' ', '2'},
                {'6', '3', '7'}
        };

        char[][] goal = {
                {'7', '13', '14', '8'},
                {'2', '1', '5', '9'},
                {'10', '11', '4', ' '},
                {'12', '6', '3', '15'},

        };*/

        int[][] goal = {
                {7, 13, 14, 8},
                {2, 1, 5, 9},
                {10, 11, 4, 0},
                {12, 6, 3, 15}

        };

        //solve sliding puzzle
        SlidingA s = new SlidingA(initial, goal, 4);
        s.solve();
    }
}
