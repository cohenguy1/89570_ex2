import java.io.*;
import java.util.*;

/*
 * Class for handling I/O of files.
 */
public class FileHandler 
{
	
	public Infrastructure ParseFile(String inputFileName) throws IOException
	{
		Infrastructure mapToSolve = new Infrastructure();
		FileReader fileReader;
		
		fileReader = new FileReader(inputFileName);
		
		BufferedReader reader = new BufferedReader(fileReader);
		
		// read line of algorithm
		String algorithm = reader.readLine();
		SetSearchAlgorithm(algorithm, mapToSolve);
		
		// read line of map size
		String dimension = reader.readLine();
		SetMapDimension(dimension, mapToSolve);
		
		ReadMap(reader, mapToSolve);
		
		reader.close();
		
		return mapToSolve;
	}
	
	/*
	 * Sets the search algorithm according to the string in the input file
	 */
	private void SetSearchAlgorithm(String algorithm, Infrastructure infrastructure)
	{
		if (algorithm.equals("IDS"))
		{
			infrastructure.SearchAlgo = SearchAlgorithm.IDS;
		}
		else if (algorithm.equals("UCS"))
		{
			infrastructure.SearchAlgo = SearchAlgorithm.UCS;
		}
	}
	
	/*
	 * Sets map dimension and creates the map by its dimension
	 */
	private void SetMapDimension(String dimension, Infrastructure infrastructure)
	{
		infrastructure.Size = Integer.parseInt(dimension);
		
		// Create the map
		infrastructure.Map = new char[infrastructure.Size][infrastructure.Size];
	}
	
	/*
	 * Reads the map
	 */
	private void ReadMap(BufferedReader reader, Infrastructure infrastructure) throws IOException
	{
		int linesRead = 0;
		
		String line = reader.readLine();
		
		// read until EOF
		while (line != null)
		{
			// read each character in the line
			for (int i = 0; i < infrastructure.Size; i++)
			{
				infrastructure.Map[linesRead][i] = line.charAt(i); 
			}
			
			linesRead++;
			
			// read the next line
			line = reader.readLine();
		}
	}

	/*
	 * Writes the result (path or no-path)
	 */
	public void WriteResult(String outputFileName, PathResult pathResult) throws IOException
	{
		FileWriter fileWriter;
		
		fileWriter = new FileWriter(outputFileName);
		
		BufferedWriter writer = new BufferedWriter(fileWriter);
		
		if (!pathResult.pathExists)
		{
			writer.write("no path");
		}
		else
		{
			// Retrieve path cost
			int pathCost = GetPathCost(pathResult.resultPath);
			
			PrintPath(writer, pathResult.resultPath);
			
			writer.write(" ");
			
			// Print path cost
			writer.write(Integer.toString(pathCost));
		}
		
		writer.close();
	}
	
	private void PrintPath(BufferedWriter writer, Deque<PathStep> path) throws IOException
	{
		PathStep startLocation = new PathStep(0, 0);
		
		// until the path stack is empty
		while (!path.isEmpty())
		{
			PathStep pathStep = path.removeFirst();

			// path location has no stepped from, skip it
			if (!pathStep.equals(startLocation))
			{
				// Get how we stepped into the location
				String stepString = GetStepString(pathStep.GetSteppedFrom());
				
				// print the step
				writer.write(stepString);
				
				// in case we have another step
				if (!path.isEmpty())
				{
					writer.write("-");
				}
			}
		
		}
	}
	
	private String GetStepString(StepDirection step)
	{
		switch (step)
		{
		case R:
			return "R";
		case RD:
			return "RD";
		case D:
			return "D";
		case LD:
			return "LD";
		case L:
			return "L";
		case LU:
			return "LU";
		case U:
			return "U";
		case RU:
			return "RU";
		}
		
		return "";
	}

	/*
	 * Gets the path cost from the goal cost (first location in the stack)
	 */
	private int GetPathCost(Deque<PathStep> path)
	{
		PathStep goalStep = path.peekLast();
		
		return goalStep.GetCostToLocation();
	}
}
