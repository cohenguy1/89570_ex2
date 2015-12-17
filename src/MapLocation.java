
public class MapLocation 
{
	// location on map
	public int Row;
	public int Column;
	
	public MapLocation(int row, int column)
	{
		Row = row;
		Column = column;
	}
	
	/*
	 * Given a location and a step, returns the new location
	 */
	public static MapLocation GetNewLocation(MapLocation location, StepDirection step) 
	{
		switch (step)
		{
		case R:
			return new MapLocation(location.Row, location.Column + 1);
		case RD:
			return new MapLocation(location.Row + 1, location.Column + 1);
		case D:
			return new MapLocation(location.Row + 1, location.Column);
		case LD:
			return new MapLocation(location.Row + 1, location.Column - 1);
		case L:
			return new MapLocation(location.Row, location.Column - 1);
		case LU:
			return new MapLocation(location.Row - 1, location.Column - 1);
		case U:
			return new MapLocation(location.Row - 1, location.Column);
		case RU:
			return new MapLocation(location.Row - 1, location.Column + 1);
		default:
			return location;
		}
	}

}
