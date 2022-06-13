import java.util.ArrayList;
public class Board {

	private int[][] board;
	private int dimension;
	private int row0;
	private int col0;
	
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        
    	board = new int[tiles.length][tiles.length];
    	dimension = board.length;
    	for(int i =0; i<tiles.length; i++)
    	{
    		for(int j=0; j<tiles.length; j++)
    		{
    			if(tiles[i][j]==0)
    			{
    				row0 = i;
    				col0 = j;
    			}
    			board[i][j] = tiles[i][j];
    		}
    	}
    }
                                           
    // string representation of this board
    public String toString()
    {
    	String total =dimension + "\n";
    	for(int i =0; i<dimension; i++)
    	{
    		for(int j=0; j<dimension; j++)
    		{
    			total += String.format("%2d ",board[i][j]);
    		}
    		
    		total += "\n";
    	}
    	return total;
    }

    // board dimension n
    public int dimension()
    {
    	return dimension;
    }

    // number of tiles out of place
    public int hamming()
    {
    	int counter =0;
    	for(int i =0; i<dimension; i++)
    	{
    		for(int j=0; j<dimension; j++)
    		{
    		
    			if(board[i][j]!=0 && board[i][j] != ((i*dimension)+j)+1)
    			{
    				counter++;
    			}
    		}
    		
    	}
    	return counter;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
    	int dist =0;
    	for(int i =0; i<dimension; i++)
    	{
    		for(int j=0; j<dimension; j++)
    		{
			// you dont need to calculate dist for zero cause its not a tile so we just skip it
    			if(board[i][j] !=0 && board[i][j] != ((i*dimension)+j)+1) 
    			{
    				int corRow = (board[i][j]-1)/dimension;
    				int corCol =  (board[i][j]-1) - (corRow*dimension);
    				
    				dist += Math.abs(i-corRow);
    				dist += Math.abs(j-corCol);
    			}
    		}
    		
    	}
    	return dist;
    	
    }

    // is this board the goal board?
    public boolean isGoal()
    {
    	if(hamming()!=0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
    	if(y!= null && y.toString().equals(this.toString()))
    	{
    			return true;
    	}
    	else
    	{
    			return false;
    	}
		
    }

    
    // all neighboring boards
    public Iterable<Board> neighbors()
    {
    	ArrayList<Board> neighbors = new ArrayList<Board>();
    		
    	if(row0 == 0)
    	{
    		if(col0 == 0)
    		{
    			neighbors.add(exchanged(row0,col0,row0,col0+1));
    			neighbors.add(exchanged(row0,col0,row0+1,col0));
    		}
    		else if (col0 == dimension-1)
    		{
    			neighbors.add(exchanged(row0,col0,row0,col0-1));
    			neighbors.add(exchanged(row0,col0,row0+1,col0));
    		}
    		else
    		{
    			neighbors.add(exchanged(row0,col0,row0,col0-1));
    			neighbors.add(exchanged(row0,col0,row0,col0+1));
    			neighbors.add(exchanged(row0,col0,row0+1,col0));
    		}
    	}
    	else if(row0 == dimension-1)
    	{
    		if(col0 == 0)
    		{
    			neighbors.add(exchanged(row0,col0,row0,col0+1));
    			neighbors.add(exchanged(row0,col0,row0-1,col0));
    		}
    		else if (col0 == dimension-1)
    		{
    			neighbors.add(exchanged(row0,col0,row0,col0-1));
    			neighbors.add(exchanged(row0,col0,row0-1,col0));
    		}
    		else
    		{
    			neighbors.add(exchanged(row0,col0,row0,col0-1));
    			neighbors.add(exchanged(row0,col0,row0,col0+1));
    			neighbors.add(exchanged(row0,col0,row0-1,col0));
    		}
    	}
    	else
    	{
    		if(col0 == 0)
    		{
    			neighbors.add(exchanged(row0,col0,row0,col0+1));
    			neighbors.add(exchanged(row0,col0,row0+1,col0));
    			neighbors.add(exchanged(row0,col0,row0-1,col0));
    		}
    		else if (col0 == dimension-1)
    		{
    			neighbors.add(exchanged(row0,col0,row0,col0-1));
    			neighbors.add(exchanged(row0,col0,row0+1,col0));
    			neighbors.add(exchanged(row0,col0,row0-1,col0));
    		}
    		else
    		{
    			neighbors.add(exchanged(row0,col0,row0,col0-1));
    			neighbors.add(exchanged(row0,col0,row0,col0+1));
    			neighbors.add(exchanged(row0,col0,row0+1,col0));
    			neighbors.add(exchanged(row0,col0,row0-1,col0));
    		}
    	}
    	
    	return neighbors;
    }
    
    private Board exchanged(int orgRow, int orgCol, int newRow, int newCol)
    {
    	int [][] tiles = new int [dimension][dimension];
    	for(int i =0; i<dimension; i++)
    	{
    		for(int j=0; j<dimension; j++)
    		{
    			tiles[i][j] = board[i][j];
    		}
    	}
    	
    	int org = tiles[orgRow][orgCol];
    	tiles[orgRow][orgCol]= tiles[newRow][newCol];
    	tiles[newRow][newCol] = org;
    	
    	Board change = new Board(tiles);
    	
    	return change;
    }
   

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
    	if(row0 == 0)
    	{
    		if(col0 == 0)
    		{
    			return exchanged(row0,col0+1,row0+1,col0);
    	    }
    		else if (col0 == dimension-1)
    		{
    			return exchanged(row0,col0-1,row0+1,col0);
    		}
    		else
    		{
    			return exchanged(row0,col0+1,row0+1,col0);
    		}
    	}
    	else if(row0 == dimension-1)
    	{
    		if(col0 == 0)
    		{
    			return exchanged(row0,col0+1,row0-1,col0);
    		}
    		else if (col0 == dimension-1)
    		{
    			return exchanged(row0,col0-1,row0-1,col0);
    		}
    		else
    		{
    			return exchanged(row0,col0+1,row0-1,col0);
    		}
    	}
    	else
    	{
    		if(col0 == 0)
    		{
    			return exchanged(row0,col0+1,row0+1,col0);
    		}
    		else if (col0 == dimension-1)
    		{
    			return exchanged(row0,col0-1,row0+1,col0);
    		}
    		else
    		{
    			return exchanged(row0,col0+1,row0+1,col0);
    		}
    	}
    }

    // unit testing (not graded)
    public static void main(String[] args)
    {
    	
    	/*int[][] board1 = new int[3][3];
    	board1[0][0] = 0;
    	board1[0][1] = 1;
    	board1[0][2] = 3;
    	
    	board1[1][0] = 4;
    	board1[1][1] = 2;
    	board1[1][2] = 5;
    	
    	board1[2][0] = 7;
    	board1[2][1] = 8;
    	board1[2][2] = 6;
    	
    	
    	
    	Board tell = new Board(board1);
    	System.out.println(tell.toString());
    	System.out.println(tell.manhattan());
    	tell.neighbors();
    	System.out.println(tell.toString());
    	System.out.println(tell.manhattan());*/
    	
    }

}
