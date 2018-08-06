/**
 * Created by chengqian on 11/11/16.
 */
import java.io.*;

//program tests neural network
public class NeuralNetworkTester {
    //main method
    public static void main(String[] args) throws IOException
    {
        //construct neural network
        NeuralNetwork network = new NeuralNetwork();

        //load training data
        network.loadTrainingData("trainingfile");


        //set parameters of network
        network.setParameters(5, 1000000, 48422222, 0.1);
        //network.setParameters(4, 1000000, 45390000, 0.9);
        
        network.standard();
        
        //train network
        network.train();
        network.validate("validationfile");

        //test network
        network.testData("inputfile", "outputfile");
    }
}
