import java.io.*;

//Program performs k-means clustering
public class KmeansTester
{
	//main method
	public static void main(String[] args) throws IOException
	{
		//create clustering object
		Kmeans k = new Kmeans();

		//load data records
		k.load("inputfile");

		//set parameters
		k.setParameters(3, 4539);

		//perform clustering
		k.cluster();

		//display records and clusters
		k.display("outputfile");
	}
}