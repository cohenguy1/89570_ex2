
public class Policy 
{
	StepDirection Action[][];

	double Reward[][];

	int MapSize;

	public Policy(int mapSize)
	{
		MapSize = mapSize;

		Action = new StepDirection[MapSize][MapSize];

		Reward = new double[MapSize][MapSize];
	}

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
