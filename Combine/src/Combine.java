import java.io.*;
import java.util.*;
/**
 * Created by chengqian on 12/12/16.
 */
public class Combine {

    private static int size = 3;
    private static Object[] tmp12 = new Object[size];
    private static Object[] tmp34 = new Object[size*4];
    //main method
    public static void main(String[] args) throws IOException
    {


        for (int i = 0; i < size; i++){
            int j = i + 4;
            Scanner inFile = new Scanner(new File("OG_087" + j + "_unblur_bin2_tmp1_tmp2.txt"));
            tmp12[i] = inFile.next();
            inFile.close();
        }

        for (int u = 0; u < size*4; u+=4){
            int k = (u/4) + 4;
            Scanner inFile = new Scanner(new File("OG_087" + k + "_unblur_bin2_tmp3_tmp4.txt"));
            tmp34[u] = inFile.next();
            tmp34[u+1] = inFile.next();
            tmp34[u+2] = inFile.next();
            tmp34[u+3] = inFile.next();
            inFile.close();
        }

        int w = 0;
        for (int m = 0; m < size; m++) {
            int n = m + 4;

            PrintWriter outFile = new PrintWriter(new FileWriter("OG_087" + n + "_unblur_bin2_tmp.txt"));
            outFile.print(tmp12[m] + " ");

            outFile.print(tmp34[w] + " ");
            outFile.print(tmp34[w+1] + " ");
            outFile.print(tmp34[w+2] + " ");
            outFile.print(tmp34[w+3] + " ");
            w += 4;

            outFile.close();
        }
    }
}
