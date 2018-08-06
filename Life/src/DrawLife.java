import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;

//Program draws an array of two types of opinions
public class DrawLife extends JFrame
{
    private final int EMPTY = 0;			//type one opinion
    private final int OCCUPIED = 1;		//type two opinion

    private int[][] array;				//array of opinions
    private int size;					//size of array

    /*******************************************************************************************/

    //Constructor of DrawOpinion class
    public DrawLife(int[][] array, int size)
    {
        setSize(5*size, 5*size);		//set window size to 5*size

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//standard window settings

        setVisible(true);

        this.array = array;				//set array that is drawn

        this.size = size;				//set array size
    }

    /*******************************************************************************************/

    //Method paints window
    public void paint(Graphics g)
    {
        //go thru array
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
            {
                if (array[i][j] == EMPTY)		//draw type one opinion as red
                {
                    g.setColor(Color.BLACK);
                    g.fillRect(5*j, 5*i, 5, 5);
                }
                else							//draw type two opinion as blue
                {
                    g.setColor(Color.RED);
                    g.fillRect(5*j, 5*i, 5, 5);
                }
            }
    }
}
