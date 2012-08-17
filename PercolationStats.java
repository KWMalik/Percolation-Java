public class PercolationStats {
   private double[] counts;
   private int no_of_times;
    
   public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
   {
       counts = new double[T];
       no_of_times = T;
       for (int i = 0; i < T; i++) {
           Percolation p = new Percolation(N);
           int count;
           for (count = 1; p.percolates() == false; count++) {
               p.openRandom();           
           }
           counts[i] = (double)count / (double)(N * N);
       }
   }
   public double mean()                     // sample mean of percolation threshold
   {
       double sum = 0;
       for (int i = 0; i < no_of_times; i++) {
           sum += counts[i];
       }
       return (double)(sum / no_of_times);
   }
   public double stddev()                   // sample standard deviation of percolation threshold
   {
       double sum = 0;
       double mean = mean();
       for (int i = 0; i < no_of_times; i++) {
           sum += (counts[i] - mean) * (counts[i] - mean);           
       }
       return (double)Math.sqrt(sum / (no_of_times - 1));
   }
   
   public static void main(String[] args)   // test client, described below
   {
       int N = Integer.parseInt(args[0]);
       int T = Integer.parseInt(args[1]);
       PercolationStats ps = new PercolationStats(N, T);
       double mean = ps.mean();
       double stddev = ps.stddev();
       double a = mean - ((double)1.96*stddev/Math.sqrt(T));
       double b = mean + ((double)1.96*stddev/Math.sqrt(T));
       System.out.println(a);
       System.out.format("mean\t\t\t= %f\n", mean);
       System.out.format("stddev\t\t\t= %f\n", stddev);
       System.out.format("95%% confidence interval\t= %f, %f\n", a, b);
   }
}