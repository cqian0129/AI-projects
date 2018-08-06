/**
 * Created by chengqian on 12/15/16.
 */
public class MatrixVacTester {
    public static void main(String[] args){

        MatrixVac m = new MatrixVac(200, 1000, 0.63, 0.07, 0.03, 0.01, 0.26, 46235);
        //MatrixVac m = new MatrixVac(100, 1000, 0.3, 0.1, 0.1, 0.1, 0.4, 46235);

    //run simulation
        m.run();
    }
}
