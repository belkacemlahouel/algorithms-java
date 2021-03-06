import java.util.LinkedList;

public class Board {

	private final int[][] blocks;
	private final int n;
	
	public Board(int[][] _blocks) {
		n = _blocks.length;
		blocks = new int[n][n];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				blocks[i][j] = _blocks[i][j];
			}
		}
	}
	
	public int dimension() {
		return n;
	}
	
	public int hamming() {
		int k = 1;
		int dist = 0;
		
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (blocks[i][j] != 0 && blocks[i][j] != k)
					++dist;
				++k;
			}
		}
		
		return dist;
	}
	
	public int manhattan() {
		int k = 1;
		int dist = 0;
		
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (blocks[i][j] != 0 && blocks[i][j] != k) {
					int dx = (blocks[i][j] - 1)/n;
                    int dy = (blocks[i][j] - 1) - dx*n;
                    dist += Math.abs(i - dx) + Math.abs(j - dy);
					
					//int tmp = Math.abs(k - blocks[i][j]);
					//dist += tmp/n + tmp%n;
				}
				++k;
			}
		}
		
		return dist;
	}
	
	@Override
	public String toString() {
		String str = "" + n + "\n";
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				str += String.format("%2d ", blocks[i][j]);
			}
			if (i < n-1) str += "\n";
		}
		return str;
	}
	
	public boolean isGoal() {
		int k = 1;
		
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (blocks[i][j] != k && blocks[i][j] != 0) {
					return false;
				}
				++k;
			}
		}
		
		return true;
	}
	
	public Board twin() {
		Board newboard = new Board(blocks);
		if (newboard.blocks[0][0] > 0 && newboard.blocks[0][1] > 0)
			newboard.swap(0, 0, 0, 1);
		else if (newboard.blocks[0][0] == 0)
			newboard.swap(1, 0, 0, 1);
		else
			newboard.swap(0, 0, 1, 0);
		return newboard;
	}
	
	private void swap(int i1, int j1, int i2, int j2) {
		int tmp = blocks[i1][j1];
		blocks[i1][j1] = blocks[i2][j2];
		blocks[i2][j2] = tmp;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
            return true;
		
        if (obj == null)
            return false;
        
        if (obj.getClass() != getClass())
        	return obj.toString() == toString();
		
		Board that = (Board) obj;
		
		if (that.n != n)
			return false;
		
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j)
				if (blocks[i][j] != that.blocks[i][j])
					return false;
		}
		
		return true;
	}
	
    public Iterable<Board> neighbors() {
    	LinkedList<Board> neighs = new LinkedList<>();
    	int i = 0, j = 0;
    	
    	for (; i < n; ++i) {
    		boolean brk = false;
    		
    		for (j = 0; j < n; ++j) {
    			if (blocks[i][j] == 0) {
    				brk = true;
    				break;
    			}
    		}
    		
    		if (brk) break;
    	}
    	
    	if (j < n-1) {
    		Board tmp = new Board(blocks);
    		tmp.swap(i, j, i, j+1);
    		neighs.add(tmp);
    	}
    	
    	if (j > 0) {
    		Board tmp = new Board(blocks);
    		tmp.swap(i, j, i, j-1);
    		neighs.add(tmp);
    	}
    	
    	if (i < n-1) {
    		Board tmp = new Board(blocks);
    		tmp.swap(i+1, j, i, j);
    		neighs.add(tmp);
    	}
    	
    	if (i > 0) {
    		Board tmp = new Board(blocks);
    		tmp.swap(i-1, j, i, j);
    		neighs.add(tmp);
    	}
    	
    	return neighs;
    }
}
