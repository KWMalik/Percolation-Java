import java.util.ArrayList;
import java.util.Random;

public class Percolation {
    public boolean[][] grid;
    private int nos;
    public QuickUnionPathCompression connections;
    private Random randomGenerator;
    private ArrayList<Point> al;
   
    
    public Percolation(int N)              // create N-by-N grid, with all sites blocked
    {
        grid = new boolean[N][N];
        nos = N;
        connections = new QuickUnionPathCompression(N * N);
        al = new ArrayList<Point>();
        randomGenerator = new Random();
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Point p = new Point(i + 1, j + 1);
                grid[i][j] = false;
                al.add(p);
            }
        }
        //System.out.println(al);
    }
    public void open (int i, int j)         // open site (row i, column j) if it is not already
    {
           grid[i - 1][j - 1] = true;
           // (i - 1, j - 1) is the site I just opened
           //System.out.format("Opened (%d, %d)\n", i - 1, j - 1);
           if (i - 2 >= 0 && isOpen(i - 1, j)) { // left
               //System.out.format("Left: Connecting %d to %d\n", (j - 1) * nos + i - 1, (j - 1) * nos + i - 2);
               connections.union((j - 1) * nos + i - 1, (j - 1) * nos + i - 2);
           }
           if (i < nos && isOpen(i + 1, j)) // right
           {
                       //System.out.format("Right: Connecting %d to %d\n", (j - 1) * nos + i - 1, (j - 1) * nos + i);
                       connections.union((j - 1) * nos + i - 1, (j - 1) * nos + i);
           }
           if (j - 2 >= 0 && isOpen(i, j - 1)) // up
           {
               //System.out.format("Up: Connecting %d to %d\n", (j - 2) * nos + i - 1, (j - 1) * nos + i - 1);
               connections.union((j - 2) * nos + i - 1, (j - 1) * nos + i - 1); 
           }
           if (j < nos && isOpen(i, j + 1)) // down
           {
                   //System.out.format("Connecting %d to %d\n", (j - 1) * nos + i - 1, j * nos + i - 1);
                   connections.union((j - 1) * nos + i - 1, j * nos + i - 1);
           }
    }
    
    public boolean isOpen(int i, int j)    // is site (row i, column j) open?
    {
        return grid[i - 1][j - 1];
    }
    
    public boolean isFull(int i, int j)    // is site (row i, column j) full?
    {
        return grid[i - 1][j - 1] == false;
    }
    
    public boolean percolates() {
        for (int i = (nos * (nos - 1)); i < (nos * nos); i++) {
            for (int i2 = 0; i2 < nos; i2++) {
                if (connections.connected(i, i2)) return true;
            }
        }
        return false;
    }
    
    public void openRandom()
    {
        int index = randomGenerator.nextInt(al.size());
        Point point = al.get(index);
        al.remove(index);
        open(point.get_x(), point.get_y());
        //System.out.println(al.size());
    }
    
    public static void main(String[] args) {
        int N = 10;
        Percolation p = new Percolation(N);
        for (int j = 0; j <= N - 1; j++) {
            for (int i = 0; i <= N - 1; i++) {
                 System.out.print(p.grid[i][j]);
                 System.out.print("\t");
            }
            System.out.print("\n");
        }
    }
}