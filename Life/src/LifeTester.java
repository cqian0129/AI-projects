/**
 * Created by chengqian on 12/20/16.
 */
public class LifeTester {
    public static void main(String[] args)
    {
        //create a voter object
        Life l = new Life(100, 1000, 0.5, 46235);

        //run simulation
        l.run();
    }
}
