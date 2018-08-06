/**
 * Created by chengqian on 12/13/16.
 */
public class MajorityTester {
    public static void main(String[] args)
    {
        //create a voter object
        Majority m = new Majority(100, 1000, 0.5, 46235);

        //run simulation
        m.run();
    }
}
