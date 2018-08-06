import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;

/**
 * Created by chengqian on 12/15/16.
 */
public class DrawOpinion extends JFrame {
    private final int GOOD = 1;			//type one opinion
    private final int FAIR = 2;		//type two opinion
    private final int POOR = 3;
    private final int DEMO = 4;
    private final int Vac = 5;

    private int[][] array;				//array of opinions
    private int size;					//size of array

    /*******************************************************************************************/

    //Constructor of DrawOpinion class
    public DrawOpinion(int[][] array, int size)
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
                if (array[i][j] == GOOD)		//draw type one opinion as red
                {
                    g.setColor(Color.BLUE);
                    g.fillRect(5*j, 5*i, 5, 5);
                }
                else if (array[i][j] == FAIR)							//draw type two opinion as blue
                {
                    g.setColor(Color.GREEN);
                    g.fillRect(5*j, 5*i, 5, 5);
                }
                else if (array[i][j] == POOR)
                {
                    g.setColor(Color.YELLOW);
                    g.fillRect(5*j, 5*i, 5, 5);
                }
                else if (array[i][j] == DEMO)
                {
                    g.setColor(Color.ORANGE);
                    g.fillRect(5*j, 5*i, 5, 5);
                }
                else
                {
                    g.setColor(Color.RED);
                    g.fillRect(5*j, 5*i, 5, 5);
                }
            }
    }
}
