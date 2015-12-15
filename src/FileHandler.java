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
				
		// read line of map size
		String dimension = reader.readLine();
		SetMapDimension(dimension, mapToSolve);
		
		ReadMap(reader, mapToSolve);
		
		reader.close();
		
		return mapToSolve;
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

	public void WriteResult(String outputFileName, Policy policy) throws IOException
	{
		FileWriter fileWriter;
		
		fileWriter = new FileWriter(outputFileName);
		
		BufferedWriter writer = new BufferedWriter(fileWriter);
		
		
		writer.close();
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
}
