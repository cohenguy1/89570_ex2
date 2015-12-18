import java.util.*;

public class PolicyFinder 
{
	protected List<StepDirection> AllSteps = Arrays.asList(StepDirection.values());

	public Policy FindPolicy(Infrastructure map)
	{
		Policy initialPolicy = new Policy(map.Size);

		Policy nextIterationPolicy = new Policy(map.Size);

		do
		{
			initialPolicy.Set(nextIterationPolicy);

			for (int row = 0; row < map.Size; row++)
			{
				for (int column = 0; column < map.Size; column++)
				{
					if (map.Map[row][column] != 'W' && map.Map[row][column] != 'G')
					{
						FindBestAction(map, map.MapLocations[row][column], initialPolicy, nextIterationPolicy);
					}
				}
			}

			// repeat until the next policy is identical to this one
		} while (!Policy.PoliciesIdentical(initialPolicy, nextIterationPolicy));

		return initialPolicy;
	}



	public void FindBestAction(Infrastructure map, MapLocation location, Policy currentPolicy, Policy nextIterationPolicy)
	{
		StepDirection bestStep = StepDirection.NotAvailable;
		double bestProfit = 0;

		for (StepDirection step : AllSteps)
		{
			if (DirectionAvailable(map, location, step))
			{
				double stepProfit = GetProfit(map, location, step, currentPolicy);

				if ((bestStep == StepDirection.NotAvailable) || 
						(stepProfit > bestProfit))
				{
					bestStep = step;
					bestProfit = stepProfit;
				}
			}
		}

		nextIterationPolicy.Action[location.Row][location.Column] = bestStep;
		nextIterationPolicy.Reward[location.Row][location.Column] = bestProfit;
	}

	private double GetProfit(Infrastructure map, MapLocation location, StepDirection step, Policy currentPolicy)
	{
		switch (step)
		{
		case R:
		case L:
		case U:
		case D:
			MapLocation newLocation = MapLocation.GetNewLocation(location, step);
			return currentPolicy.Reward[newLocation.Row][newLocation.Column] + GetNodeCost(map.Map, newLocation);
		case RD:
		case LD:
		case RU:
		case LU:
			return GetProfitFromDiagonalStep(map.Map, location, step, currentPolicy);
		default:
			return -1;
		}
	}

	private double GetProfitFromDiagonalStep(char[][] map, MapLocation location, StepDirection step, Policy currentPolicy)
	{
		MapLocation possibleLocation1 = MapLocation.GetNewLocation(location, step);
		double possibleReward1 = currentPolicy.Reward[possibleLocation1.Row][possibleLocation1.Column] + GetNodeCost(map, possibleLocation1);

		StepDirection possibleStep2;
		StepDirection possibleStep3;

		switch (step)
		{
		case RD:
			possibleStep2 = StepDirection.R;
			possibleStep3 = StepDirection.D;
			break;
		case LD:
			possibleStep2 = StepDirection.L;
			possibleStep3 = StepDirection.D;
			break;
		case RU:
			possibleStep2 = StepDirection.R;
			possibleStep3 = StepDirection.U;
			break;
		case LU:
			possibleStep2 = StepDirection.L;
			possibleStep3 = StepDirection.U;
			break;
		default:
			return -1;
		}

		MapLocation possibleLocation2 = MapLocation.GetNewLocation(location, possibleStep2);
		double possibleReward2 = currentPolicy.Reward[possibleLocation2.Row][possibleLocation2.Column] + GetNodeCost(map, possibleLocation2);

		MapLocation possibleLocation3 = MapLocation.GetNewLocation(location, possibleStep3);
		double possibleReward3 = currentPolicy.Reward[possibleLocation3.Row][possibleLocation3.Column] + GetNodeCost(map, possibleLocation3);

		return 0.7 * possibleReward1 + 0.15 * possibleReward2 + 0.15 * possibleReward3;
	}


	/*
	 *  Gets the cost of a single location by its type
	 */
	protected int GetNodeCost(char[][] map, MapLocation location) throws IllegalArgumentException
	{
		switch (map[location.Row][location.Column])
		{
		case 'S':
			return 0;
		case 'W':
			// if we check for cost of water, it means something went wrong
			throw new IllegalArgumentException("Infinite Cost");
		case 'R':
			return -1;
		case 'D':
			return -3;
		case 'H':
			return -10;
		case 'G':
			return 100;
		}

		return 0;
	}

	/*
	 * Returns if we can go to this location or not.
	 * We cannot go in case we are out of map bounds or if the Water on the map blocks us
	 */
	protected boolean DirectionAvailable(Infrastructure map, MapLocation location, StepDirection step)
	{
		if (step == StepDirection.NotAvailable)
		{
			return false;
		}

		int mapSideLimit = map.Size - 1;

		// in case we are out of map bounds
		if (location.Row == 0)
		{
			if (step == StepDirection.LU || step == StepDirection.RU || step == StepDirection.U)
			{
				return false;
			}
		}
		else if (location.Row == mapSideLimit)
		{
			if (step == StepDirection.LD || step == StepDirection.RD || step == StepDirection.D)
			{
				return false;
			}
		}

		if (location.Column == 0)
		{
			if (step == StepDirection.LU || step == StepDirection.LD || step == StepDirection.L)
			{
				return false;
			}
		}
		else if (location.Column == mapSideLimit)
		{
			if (step == StepDirection.RU || step == StepDirection.RD || step == StepDirection.R)
			{
				return false;
			}
		}

		// We are inside map bounds
		// Checks if water blocks us from going to the new location
		if (step == StepDirection.RD)
		{
			if (map.Map[location.Row + 1][location.Column + 1] == 'W' ||
					map.Map[location.Row + 1][location.Column] == 'W' ||
					map.Map[location.Row][location.Column + 1] == 'W')
			{
				return false;
			}
		}
		else if (step == StepDirection.LD)
		{
			if (map.Map[location.Row + 1][location.Column - 1] == 'W' ||
					map.Map[location.Row + 1][location.Column] == 'W' ||
					map.Map[location.Row][location.Column - 1] == 'W')
			{
				return false;
			}
		}
		else if (step == StepDirection.LU)
		{
			if (map.Map[location.Row - 1][location.Column - 1] == 'W' ||
					map.Map[location.Row - 1][location.Column] == 'W' ||
					map.Map[location.Row][location.Column - 1] == 'W')
			{
				return false;
			}
		}
		else if (step == StepDirection.RU)
		{
			if (map.Map[location.Row - 1][location.Column + 1] == 'W' ||
					map.Map[location.Row - 1][location.Column] == 'W' ||
					map.Map[location.Row][location.Column + 1] == 'W')
			{
				return false;
			}
		}
		else if (step == StepDirection.R)
		{
			if (map.Map[location.Row][location.Column + 1] == 'W')
			{
				return false;
			}
		}
		else if (step == StepDirection.L)
		{
			if (map.Map[location.Row][location.Column - 1] == 'W')
			{
				return false;
			}
		}
		else if (step == StepDirection.U)
		{
			if (map.Map[location.Row - 1][location.Column] == 'W')
			{
				return false;
			}
		}
		else if (step == StepDirection.D)
		{
			if (map.Map[location.Row + 1][location.Column] == 'W')
			{
				return false;
			}
		}

		return true;
	}
}
