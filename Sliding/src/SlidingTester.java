//Tester program for sliding board solver
public class SlidingTester
{
    //main method to test sliding board solver
    public static void main(String[] args)
    {
        //initial board

        char[][] initial = {
                {'2', '8', '3'},
                {'1', '6', '4'},
                {'7', ' ', '5'}
        };


        //final board

        char[][] goal = {
                {'1', '2', '3'},
                {'8', ' ', '4'},
                {'7', '6', '5'}
        };

        //solve sliding puzzle
        Sliding s = new Sliding(initial, goal, 3);
        s.solve();
    }
}
