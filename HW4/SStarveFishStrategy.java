public class SStarveFishStrategy extends FishStrategy{
	
	public void move(Fish fish, Pond pond, double x, double y)
	{
	    double[] location = pond.findNearestPlant(x, y);
        fish.swimTowards(location[0], location[1]);
	}
}
