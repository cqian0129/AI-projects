/**
 * Created by chengqian on 12/14/16.
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class Vac {

    public static void main(String[] args) throws IOException{
        int norows = 335642;
        int nocols = 5;

        Object[][] input;
        double dist = 0;
        Random rand = new Random();
        Object[][] V1 = new Object[norows][4];
        double[] ERROR = new double[10];
        for (int i = 0; i < 10; i++)
            ERROR[i] = 0.0;

        double[][] changeRate = {
            {86.37, 8.42, 1.67, 0.73, 2.80},
            {52.29, 24.82, 8.74, 3.55, 10.60},
            {22.99, 25.25, 16.54, 7.67, 27.55},
            {7.09, 10.24, 11.94, 14.50, 56.24},
            {4.76, 1.18, 0.60, 0.49, 92.97},
        };


        //read file
        input = new Object[norows][nocols];
        Scanner inFile = new Scanner(new File("Workbook1.txt"));

        for (int i = 0; i < norows; i++) {

                input[i][0] = inFile.nextInt();
                input[i][1] = inFile.nextDouble();
                input[i][2] = inFile.nextDouble();
                input[i][3] = inFile.nextInt();
                input[i][4] = inFile.nextInt();

        }



        inFile.close();

        for (int i = 0; i < norows; i++){
            V1[i][0] = i+1;
            //System.out.println(i);
            V1[i][1] = (int)input[i][3];
            V1[i][2] = (double)input[i][1];
            V1[i][3] = (double)input[i][2];

        }


        for (int i = 0; i < norows; i++) {
            if (i<=100){
                List list1 = new ArrayList();

                for (int j = 0; j < (i + 100); j++) {

                    dist = Math.sqrt(Math.pow(((double)input[i][1] - (double)input[j][1]), 2) + Math.pow(((double)input[i][2] - (double)input[j][2]), 2));

                    if (dist <= 200) {
                        list1.add(input[j][3]);


                    }
                }


                int index = rand.nextInt(list1.size());

                //System.out.println((int)list1.get(index)-1);
                double newRate = (changeRate[(int)input[i][3]-1][(int)list1.get(index)-1])/100;

                double compareRate = rand.nextDouble();

                if (compareRate < newRate)
                    V1[i][1] = (int)list1.get(index);

            }
            if (i>100&&i<(norows-100)) {
                List list2 = new ArrayList();

                for (int j = (i - 100); j < (i + 100); j++) {
                    dist = Math.sqrt(Math.pow(((double)input[i][1] - (double)input[j][1]), 2) + Math.pow(((double)input[i][2] - (double)input[j][2]), 2));
                    if (dist <= 200) {
                        list2.add(input[j][3]);



                    }
                }
                int index = rand.nextInt(list2.size());

                double newRate = (changeRate[(int)input[i][3]-1][(int)list2.get(index)-1])/100;

                double compareRate = rand.nextDouble();

                if (compareRate < newRate)
                    V1[i][1] = (int)list2.get(index);
            }
            if (i>=(norows-100)) {
                List list3 = new ArrayList();

                for (int j = (i - 100); j < norows; j++) {
                    dist = Math.sqrt(Math.pow(((double)input[i][1] - (double)input[j][1]), 2) + Math.pow(((double)input[i][2] - (double)input[j][2]), 2));
                    if (dist <= 200) {

                        list3.add(input[j][3]);



                    }
                }
                int index = rand.nextInt(list3.size());

                double newRate = (changeRate[(int)input[i][3]-1][(int)list3.get(index)-1])/100;

                double compareRate = rand.nextDouble();

                if (compareRate < newRate)
                    V1[i][1] = (int)list3.get(index);
            }



        }

        List list = new ArrayList();
        list.add(V1);
        int iterations = 30;
        int[][] a = new int[norows][iterations];

        for (int i = 0; i < iterations-1; i++){
            Object[][] item = (Object[][])list.get(i);
            list.add(convert(item));
        }

        for (int i = 0; i < iterations; i++){
            Object[][] item = (Object[][])list.get(i);

            for (int j = 0; j < norows; j++){
                a[j][i] = (int)item[j][1];
            }
        }


        for (int j = 1; j < iterations; j++){
            int sum = 0;
            for (int i = 0; i < norows; i++){
            sum += (a[i][j]-(int)input[i][4])*(a[i][j]-(int)input[i][4]);
            }
            System.out.println(sum);
        }



        PrintWriter outFile = new PrintWriter(new FileWriter("V1"));



        for (int i = 0; i < norows; i++){
            for (int j = 0; j < iterations; j++){
                outFile.print(a[i][j] + " ");
            }
            outFile.println();
        }
        outFile.close();

    }

    public static Object[][] convert(Object[][] V){
        int rows = V.length;
        int cols = V[0].length;
        Object[][] CT = new Object[rows][cols];
        double dist;
        Random rand = new Random();

        for (int i = 0; i < rows; i++){
            CT[i][0] = i+1;
            CT[i][1] = V[i][1];
            CT[i][2] = V[i][2];
            CT[i][3] = V[i][3];
        }

        double[][] changeRate = {
                {86.37, 8.42, 1.67, 0.73, 2.80},
                {52.29, 24.82, 8.74, 3.55, 10.60},
                {22.99, 25.25, 16.54, 7.67, 27.55},
                {7.09, 10.24, 11.94, 14.50, 56.24},
                {4.76, 1.18, 0.60, 0.49, 92.97},
        };

        for (int i = 0; i < rows; i++) {
            if (i <= 100) {
                List list1 = new ArrayList();

                for (int j = 0; j < (i + 100); j++) {
                    //System.out.println("i:" + i);
                    //System.out.println("j:" + j);
                    dist = Math.sqrt(Math.pow(((double)V[i][2] - (double)V[j][2]), 2) + Math.pow(((double)V[i][3] - (double)V[j][3]), 2));

                    if (dist <= 200) {
                        list1.add(V[j][1]);


                    }
                }


                int index = rand.nextInt(list1.size());

                //System.out.println((int)list1.get(index)-1);
                double newRate = (changeRate[(int)V[i][1] - 1][(int)list1.get(index) - 1]) / 100;

                double compareRate = rand.nextDouble();

                if (compareRate < newRate)
                    CT[i][1] = (int)list1.get(index);

            }
            if (i > 100 && i < (rows - 100)) {
                List list2 = new ArrayList();

                for (int j = (i - 100); j < (i + 100); j++) {
                    dist = Math.sqrt(Math.pow(((double)V[i][2] - (double)V[j][2]), 2) + Math.pow(((double)V[i][3] - (double)V[j][3]), 2));
                    if (dist <= 200) {
                        list2.add(V[j][1]);


                    }
                }
                int index = rand.nextInt(list2.size());

                double newRate = (changeRate[(int)V[i][1] - 1][(int)list2.get(index) - 1]) / 100;

                double compareRate = rand.nextDouble();

                if (compareRate < newRate)
                    CT[i][1] = (int)list2.get(index);
            }
            if (i >= (rows - 100)) {
                List list3 = new ArrayList();

                for (int j = (i - 100); j < rows; j++) {
                    dist = Math.sqrt(Math.pow(((double)V[i][2] - (double)V[j][2]), 2) + Math.pow(((double)V[i][3] - (double)V[j][3]), 2));
                    if (dist <= 200) {

                        list3.add(V[j][1]);


                    }
                }
                int index = rand.nextInt(list3.size());

                double newRate = (changeRate[(int)V[i][1] - 1][(int)list3.get(index) - 1]) / 100;

                double compareRate = rand.nextDouble();

                if (compareRate < newRate)
                    CT[i][1] = (int)list3.get(index);
            }

        }

        return CT;

    }
}
