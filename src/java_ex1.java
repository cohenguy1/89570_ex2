import java.io.IOException;

public class java_ex1
{
	
	public static void main(String[] args) 
	{
		try
		{
			String inputFilePath = "input.txt";
			
			FileHandler fileHandler = new FileHandler();
			Infrastructure mapToSolve = fileHandler.ParseFile(inputFilePath);
			
			PathResult path = FindPath(mapToSolve);
			
			String outputFilePath = "output.txt";
			fileHandler.WriteResult(outputFilePath, path);
		}
		catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
		
	}
	
	/*
	 * Returns the path to the goal, according to the algorithm to solve
	 */
	public static PathResult FindPath(Infrastructure mapToSolve)
	{
		PathFinder pathFinder;
		
		// Create appropriate path finder
		if (mapToSolve.SearchAlgo == SearchAlgorithm.IDS)
		{
			pathFinder = new IdsPathFinder();
		}
		else
		{
			pathFinder = new UcsPathFinder();
		}
		
		// find the path according to the path finder type
		return pathFinder.FindPath(mapToSolve);
	}

}
