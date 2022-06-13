import edu.princeton.cs.algs4.MinPQ;
import java.util.Deque;
import java.util.LinkedList;
public class Solver {
	private int moves;
	private Deque<Board> solution;
	private Boolean solved;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
    	if(initial == null)
    	{
    		throw new IllegalArgumentException();
    	}
    	moves = 0;
    	solution = new LinkedList<Board>();
    	
    
        MinPQ<SearchNode> queue = new MinPQ<SearchNode>();
        queue.insert(new SearchNode(initial, null, moves));
        
        MinPQ<SearchNode> queueT = new MinPQ<SearchNode>();
        Board twin = initial.twin();
        queueT.insert(new SearchNode(twin, null, moves));
        
        while(true)
        	{
        		SearchNode delt = queue.delMin();
        		SearchNode delt1 = queueT.delMin();
 
        		if(delt.current.isGoal())
        		{
        			solution.push(delt.current);
        			moves = delt.moves;
        			solved = true;
        			while(delt.previous!=null)
        			{
        				solution.push(delt.previous.current);
        				delt = delt.previous;
        			}
        			break;
        		}
        		
        		if(delt1.current.isGoal()|| delt.moves>100)
        		{
        			solved = false;
        			break;
        		}
        		
   
        		for(Board b : delt.current.neighbors())
        		{
        			if(delt.previous == null || !b.equals(delt.previous.current))
        			{
        				queue.insert(new SearchNode(b,delt,delt.moves+1));
        			}
        		}
        		
        		for(Board b : delt1.current.neighbors())
        		{
        			if(delt1.previous == null || !b.equals(delt1.previous.current))
        			{
        				queueT.insert(new SearchNode(b,delt1,delt1.moves+1));
        			}
        		}
        		
        	}	
    	
    	
    }
    
    private class SearchNode implements Comparable<SearchNode>
    {

		public Board current;
		public SearchNode previous;
		public int moves;
		private int priority;
		
		
		public SearchNode(Board curr, SearchNode prev, int m)
		{
			current = curr;
			previous = prev;
			moves = m;
			priority = moves + current.manhattan();
		}
	
		public int compareTo(SearchNode compare) {
			
			int currManDist = priority;
			int compManDist = compare.priority;
			
			if(currManDist>compManDist)
			{
				return 1;
			}
			if(currManDist<compManDist)
			{
				return -1;
			}
			return 0;
			
		
		}
    	
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable()
    {
    	return solved;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()
    {
    	if(isSolvable())
    	{
    		return moves;
    	}
    	else
    	{
    		return -1;
    	}
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()
    {
    	if(isSolvable())
    	{
    		return solution;
    	}
    	else
    	{
    		return null;
    	}
    }
    // test client (see below) 
    public static void main(String[] args)
    {
    	int[][] board1 = new int[3][3];
    	board1[0][0] = 2;
    	board1[0][1] = 0;
    	board1[0][2] = 8;
    	
    	board1[1][0] = 1;
    	board1[1][1] = 3;
    	board1[1][2] = 5;
    	
    	board1[2][0] = 4;
    	board1[2][1] = 6;
    	board1[2][2] = 7;
    	
    	
    	
    	
    	Board tell = new Board(board1);
    	Solver test = new Solver(tell);
    	System.out.println(test.moves());
    	System.out.println(test.solution());
    	
    	/**int[][] board1 = new int[2][2];
    	board1[0][0] = 1;
    	board1[0][1] = 0;
    	board1[1][0] = 3;
    	board1[1][1] = 2;
    	
    	Board board2 = new Board(board1);
    	Solver test = new Solver(board2);
    	System.out.println(test.moves());
    	System.out.println(test.solution());**/
    	
    	
    	
    }

}