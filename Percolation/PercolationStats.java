
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    private double[] est;
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   {
       if(n <= 0 || trials <= 0)
           throw new IllegalArgumentException("invalid input");
       est = new double[trials];
       for( int k = 0;k<trials;k++)
       {
           Percolation perc = new Percolation(n);
           double count = 0;
           while( !perc.percolates())
           {
               int i = StdRandom.uniform(1,n+1);
               int j = StdRandom.uniform(1,n+1);
               if(perc.isOpen(i,j)) continue;
               perc.open(i,j);
               ++count;
           }
           est[k] = count / (n*n);
       }
       mean = StdStats.mean(est);
       stddev = StdStats.stddev(est);
       confidenceLo = mean - (1.96*stddev)/(Math.sqrt(trials));
       confidenceHi = mean + (1.96*stddev)/(Math.sqrt(trials));
   }
   public double mean()                          // sample mean of percolation threshold
   {
       return mean;
   }
   public double stddev()                        // sample standard deviation of percolation threshold
   {
       return stddev;
   }
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   {
       return confidenceLo;
   }
   public double confidenceHi()                  // high endpoint of 95% confidence intervale
   {
       return confidenceHi;
   }

   public static void main(String[] args)        // test client (described below)
   {
       int n = Integer.parseInt(args[0]), trials = Integer.parseInt(args[1]);
       PercolationStats stats = new PercolationStats(n,trials);
       StdOut.println("mean =                      "+stats.mean());
       StdOut.println("stddev =                    "+stats.stddev());
       StdOut.println("95% confidence interval = [" + stats.confidenceLo() +", "+stats.confidenceHi()+"]");
   }
}