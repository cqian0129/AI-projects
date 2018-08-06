import java.io.*;

/********************************************************************************/

//Program tests Bayes classifier
public class BayesTester {
	//main method
	public static void main(String[] args) throws IOException
	{
		//construct bayes classifier
		Bayes b = new Bayes();
		
		//load training data
		b.loadTrainingData("trainingfile");
		
		//compute probailities
		b.computeProbability();
		
		//classify data
		b.classifyData("testfile", "classifiedfile");
	}
}

/********************************************************************************/

