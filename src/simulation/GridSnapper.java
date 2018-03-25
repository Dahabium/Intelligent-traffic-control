package simulation;

import java.util.ArrayList;

public class GridSnapper {

    ArrayList<double[]> grid = new ArrayList<>();

    public GridSnapper()
    {
        for(int i = 0; i<10; i++)
        {
            for(int j = 0; j<10; j++)
            {
                double x = j*51 + 46.5;
                double y = i*51 + 126.5;
                double[] xy = new double[4];
                xy[0] = x;
                xy[1] = y;
                xy[2] = j;
                xy[3] = i;
                grid.add(xy);
            }
        }
    }
    public double[] getGridXY(double x, double y)
    {
        double[] closest = new double[4];
        double minimum = 9999999;
        for(int i = 0; i<grid.size(); i++)
        {
            double diff = Math.abs(grid.get(i)[0]-x) + Math.abs(grid.get(i)[1]-y);

            if(diff<minimum)
            {
                minimum = diff;
                closest = grid.get(i);
            }
        }

        return closest;

    }

}
