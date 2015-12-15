import java.io.IOException;

public class java_ex2
{
	
	public static void main(String[] args) 
	{
		try
		{
			String inputFilePath = "input.txt";
			
			FileHandler fileHandler = new FileHandler();
			Infrastructure map = fileHandler.ParseFile(inputFilePath);
			
			PolicyFinder policyFinder = new PolicyFinder();
			Policy policy = policyFinder.FindPolicy(map);
			
			String outputFilePath = "output.txt";
			fileHandler.WriteResult(outputFilePath, policy);
		}
		catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}
