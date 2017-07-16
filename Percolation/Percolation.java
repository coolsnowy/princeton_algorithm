/****************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:  java Percolation input20.txt
 *  Dependencies: In.java StdOut.java WeightedQuickUnionUF.java
 *
 *  The Percolation System Model.
 *
 ****************************************************************************/



import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private int N; // number of columns plus 1
    private WeightedQuickUnionUF uf; // union-find data structure
    // open[i][j] = the condition of site at row i, column j:
    // 0 - Blocked site   1 - Open site    2 - Open site connected to the bottom
    private byte[][] open;
    
    /**
     * Create n-by-n grid, with all sites blocked
     * @throws java.lang.IllegalArgumentException if N <= 0
     * @param N number of row and columns
     */ 
    
    // n = N-1,N-1-by-N-1,0 or N represent virtual site
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("invalid input");
        this.N = n+1;
        uf = new WeightedQuickUnionUF((n+1)*(n+1));
        open = new byte[n+1][n+1];
    }
    
    // validate that i and j are valid indices
    private void validate(int row, int col) {
        if (row <= 0 || row >= N) 
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (col <= 0 || col >= N) 
            throw new IndexOutOfBoundsException("column index j out of bounds");
    }
    
    // union operation for both UF instances
    private void union(int row, int col) {
        uf.union(row, col);
    }
    
      public boolean isOpen(int row,int col)
    {
        validate(row,col);
        return open[row][col] > 0;
      }
    /**
     * Open site (row <tt>i</tt>, column <tt>j</tt>) if it is not open already
     * @param i the integer representing the row index
     * @param j the integer representing the column index
     * @throws java.lang.IndexOutOfBoundsException 
     *     unless both 0 < i <= N and 0 < j <= N
     */ 
    public void open(int row, int col) {
        validate(row,col);
        if (isOpen(row , col)) // has been opened, no operation
            return;
        open[row][col] = 1;
        
        if(row == N-1) // last row, N represent virtual site
            open[row][col] = 2;
        if(row == 1)
        {
            uf.union(0,row*N + col);
            if(open[row][col] == 2)
                open[0][0] = 2; // 1-by-1 grid corner case
        }
        
        if(col +1 < N && isOpen(row , col + 1 ))
        {
            int q = uf.find(row * N + col);
            int p = uf.find(row * N + col + 1);
            union( row * N + col , row * N + col + 1);
            if(open[p / N][p % N] == 2 || open[q / N][q % N] == 2)
            {
                int t = uf.find(row * N + col);
                open[t / N][t % N] = 2;
            }
        }
        if(col -1 > 0 && isOpen(row , col - 1))
        {
            int q = uf.find(row * N + col);
            int p = uf.find(row * N + col - 1);
            union( row * N + col , row * N + col - 1);
            if(open[p / N][p % N] == 2 || open[q / N][q % N] == 2)
            {
                int t = uf.find(row * N + col);
                open[t / N][t % N] = 2;
            }
        }
        
       if(row -1 > 0 && isOpen(row - 1 , col))
       {
            int q = uf.find(row * N + col);
            int p = uf.find((row - 1)* N + col );
            union( row * N + col , (row - 1) * N + col);
            if(open[p / N][p % N] == 2 || open[q / N][q % N] == 2)
            {
                int t = uf.find(row * N + col);
                open[t / N][t % N] = 2;
            }
       }
       if(row +1 < N && isOpen(row + 1 , col))
       {
            int q = uf.find(row * N + col);
            int p = uf.find((row + 1)* N + col );
            union( row * N + col , (row + 1) * N + col);
            if(open[p / N][p % N] == 2 || open[q / N][q % N] == 2)
            {
                int t = uf.find(row * N + col);
                open[t / N][t % N] = 2;
            }
       }
    }
  
    public boolean isFull(int row, int col) 
    {
        validate(row , col);
        return open[row][col] > 0 && uf.connected(0 , row * N + col);
    }
    public     int numberOfOpenSites()
    {
        int count = 0;
        for(int i = 1;i<N;i++)
        {
            for(int j= 1;j<N;j++)
            {
                if(isOpen(i,j))
                       count ++;
            }
        }
        return count;

    }
    
    public boolean percolates()              // does the system percolate?
    {
        int root = uf.find(0);
        return open[root / N][root % N] == 2;
    }
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        int N = in.readInt();
        Percolation perc = new Percolation(N);
        boolean percolates = false;
        while( !in.isEmpty())
        {
            int i = in.readInt();
            int j = in.readInt();       
            perc.open(i,j);
            percolates = perc.percolates();
            if(percolates) break;
        }
       
        if(percolates)
            StdOut.println("percolates");
        else
            StdOut.println("does not percolate");
        StdOut.println(perc.numberOfOpenSites() + "open sites");
    }
}
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
       