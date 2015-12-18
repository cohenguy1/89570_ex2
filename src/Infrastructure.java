/*
 * Holds Infrastructure for the Policy Finder 
 */
public class Infrastructure
{
	char[][] Map;

	int Size;
	
	MapLocation[][] MapLocations;
	
	public Infrastructure(int mapSize)
	{
		Size = mapSize;
		
		// Create the map
		Map = new char[Size][Size];		
		
		for (int row = 0; row < Size; row++)
		{
			for (int column = 0; column < Size; column++)
			{
				MapLocations[row][column] = new MapLocation(row, column);
			}
		}
	}
}

