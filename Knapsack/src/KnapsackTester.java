/**
 * Created by chengqian on 11/14/16.
 */
import java.io.*;
import java.util.*;

public class KnapsackTester {
    //main method
    public static void main(String[] args) throws IOException {
        //knapsack data
        int numberItems = 50;
        int maximumWeight = 500;
        int[][] table = new int[numberItems][3];

        Scanner inFile = new Scanner(new File("inputfile"));

        for (int i = 0; i < numberItems; i++){
            table[i][0] = inFile.nextInt();
            table[i][1] = inFile.nextInt();
            table[i][2] = inFile.nextInt();
        }
/*
        for (int i = 0; i < numberItems; i++) {
            System.out.print(table[i][0]);
            System.out.print(table[i][1]);
            System.out.print(table[i][2]);
            System.out.println();
        }
*/

        //create knapsack solver
        Knapsack k = new Knapsack(table, numberItems, maximumWeight);

        //set parameters of genetic algorithm
        k.setParameters(500, 50, 1000, 0.8, 0.01, 4329);

        //find optimal solution
        k.solve();
    }
}

/***********************************************************************/

