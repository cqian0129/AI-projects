import java.io.*;
import java.util.*;

/**
 * Created by chengqian on 11/22/16.
 */
public class TravelGTester {
    public static void main(String[] args) throws IOException {
        int numberVertices = 10;
        int[][] matrix = new int[numberVertices][numberVertices];

        Scanner inFile = new Scanner(new File("inputfile"));

        int a = 0;
        int b = 0;
        int c = 0;

        for (int i = 0; i < 45; i++)
        {
            a = inFile.nextInt();
            b = inFile.nextInt();
            c = inFile.nextInt();

            matrix[a-1][b-1] = c;
            matrix[b-1][a-1] = c;
            //System.out.println(c + "   ");
        }
/*

        for (int i = 0; i < numberVertices; i++)
            for (int j = 0; j < numberVertices; j++)
                System.out.println(i + " " + j + " " + matrix[i][j]);
*/
        TravelG t = new TravelG(matrix, numberVertices);

        t.setParameters(2000, 10, 1000, 0.8, 0.01, 4329);

        t.solve();
    }
}
