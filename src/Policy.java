
public class Policy 
{
	// Action for each map location
	StepDirection Action[][];

	// Reward for each map location
	double Reward[][];

	int MapSize;

	public Policy(int mapSize)
	{
		MapSize = mapSize;

		Action = new StepDirection[MapSize][MapSize];

		Reward = new double[MapSize][MapSize];
	}

	/*
	 * Copies policy from other policy to our policy
	 */
	public void Set(Policy otherPolicy) 
	{
		for (int row = 0; row < MapSize; row++)
		{
			for (int column = 0; column < MapSize; column++)
			{
				Action[row][column] = otherPolicy.Action[row][column];
				Reward[row][column] = otherPolicy.Reward[row][column];
			}
		}

	}

	/*
	 * Compares two policies and returns true if they are identical
	 */
	public static boolean PoliciesIdentical(Policy policy1, Policy policy2) 
	{
		for (int row = 0; row < policy1.MapSize; row++)
		{
			for (int column = 0; column < policy1.MapSize; column++)
			{
				if ((policy1.Reward[row][column] != policy2.Reward[row][column]) ||
					(policy1.Action[row][column] != policy2.Action[row][column]))
				{
					return false;
				}
			}
		}

		return true;
	}
}
