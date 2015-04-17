public class SHungryFishStrategy extends FishStrategy{
	
	public void move(Fish fish, Pond pond, double x, double y)
	{
		double[] location = pond.findNearestBigFish(x, y);
        fish.swimAway(location[0], location[1]);
	}
}
