/**
 * Created by chengqian on 11/8/16.
 */
public class ChessTester {
    public static void main(String[] args)
    {
        char[][] array = new char[6][6];

        Chess game = new Chess(6);

        array = new char[6][6];   //create array

        for (int i = 0; i < 6; i++)  //fill with empty slots
            for (int j = 0; j < 6; j++)
                array[i][j] = ' ';

        array[0][0] = 'R';
        array[0][1] = 'K';
        array[0][3] = 'B';
        array[0][4] = 'R';
        array[0][5] = 'B';

        array[5][0] = 'b';
        array[5][1] = 'r';
        array[5][2] = 'r';
        array[5][3] = 'k';
        array[5][5] = 'b';

        for (int i = 0; i < 6; i++)
        {
            System.out.println("------------");
            for (int j = 0; j< 6; j++)
                System.out.print(array[i][j] + "|");
            System.out.println();
        }

        game.play();
    }
}
