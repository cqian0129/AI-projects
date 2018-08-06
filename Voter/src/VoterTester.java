public class VoterTester {
	public static void main(String[] args)
	{
		//create a voter object
		Voter v = new Voter(100, 1000, 0.6, 46235);
				
		//run simulation
		v.run();
	}
}
