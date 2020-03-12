import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF uf;
    private int openSites;
    private int side;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }
        side = n;
        openSites = 0;
        grid = new int[n][n];
        uf = new WeightedQuickUnionUF((n * n) + 2);


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > side || row < 1 || col < 1 || col > side) {
            throw new IllegalArgumentException("row,col out of bounds");
        }


        if (grid[row - 1][col - 1] != 1) {
            grid[row - 1][col - 1] = 1;
            openSites++;
            if (row == 1) {
                uf.union(0, (row - 1) * side + col);
            }
            if (row == side) {
                uf.union((side * side) + 1, (row - 1) * side + col);
            }
            try {
                if (isOpen(row - 1, col)) uf.union(((row - 1) * side + col), ((row - 2) * side + col));
            } catch (IllegalArgumentException e) {
            }

            try {
                if (isOpen(row + 1, col)) uf.union(((row - 1) * side + col), (row * side) + col);
            } catch (IllegalArgumentException e) {
            }

            try {
                if (isOpen(row, col - 1)) uf.union(((row - 1) * side + col), (row - 1) * side + (col - 1));
            } catch (IllegalArgumentException e) {
            }

            try {
                if (isOpen(row, col + 1)) uf.union(((row - 1) * side + col), (row - 1) * side + (col + 1));
            } catch (IllegalArgumentException e) {
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > side || row < 1 || col < 1 || col > side) {
            throw new IllegalArgumentException("row,col out of bounds");
        }
        return grid[row - 1][col - 1] == 1;

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > side || row < 1 || col < 1 || col > side) {
            throw new IllegalArgumentException("row,col out of bounds");
        }
        return uf.connected(0, ((row - 1) * side + col));

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, side * side + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        Percolation test = new Percolation(n);
        for (int i = 0; i < t; i++) {

            while (!StdIn.isEmpty()) {
                int row = StdIn.readInt();
                int col = StdIn.readInt();
                test.open(row, col);
                System.out.println(test.isFull(row, col));
                System.out.println(test.isOpen(row, col));
                System.out.println(test.percolates());


            }

        }
        System.out.println(test.numberOfOpenSites());


    }

}


