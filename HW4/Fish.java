/**
* Fish
*
* A single fish in the simulation
*/

import java.lang.Math;
import java.util.Random;

public class Fish extends Subject
{
  private FishStrategy fishStrategy;
  private static Random random = new Random();
  private static int numberOfFish = 0;

  private double hunger;            // A value between 0 (hungry) and 1 (full)
  private double size;
  private double x;                 // Where the fish is
  private double y;
  private int id; 
  
  private final double initialHunger = 0.9;
  private final double initialSize = 1.0;
  private final double starvingBorder = 0.2;
  private final double hungryBorder = 0.7;
  private final double plantFishBorder = 7.0;
  private final double bigFishBorder = 5.0;

  // Don't want to be able to directly query the fish for information, but do 
  // need to get information for logging or displaying on a GUI, etc.

  public Fish(double x, double y, FishReport report)
  {
    // A fish is born!
    hunger = initialHunger;
    size = initialSize;

    // Put it in the pond
    this.x = x;
    this.y = y;

    // And give it an id
    id = numberOfFish;
    numberOfFish++;

    // Who to report to?
    addObserver(report);
    updateMoveStrategy();
    updateVars();
  }

  public double getSize()           
  {
    return size;
  }
  
  private void updateVars(){
    double[] params = {hunger, size, x, y};
    notifyObs(params);
  } 
  
  public void age(double timePassed)
  {
    // Growth is based on how much time has passed and how much food
    // has been eaten
    double deltaSize = size * (1 + hunger * Math.exp(-size * timePassed));
    size = size + deltaSize;

    // If the fish grows a lot (relative to the size change), lots of food
    // has been consumed
    hunger = hunger * Math.exp(-deltaSize/size);
    updateMoveStrategy();   
    updateVars();
  }
  
  private void updateMoveStrategy(){
	if (hunger < starvingBorder && size < plantFishBorder)
		fishStrategy = new SStarveFishStrategy();
	else if (hunger < starvingBorder && size > plantFishBorder)
		fishStrategy = new LStarveFishStrategy();
	else if (hunger > starvingBorder && hunger < hungryBorder && size > bigFishBorder)
		fishStrategy = new LHungryFishStrategy();
	else if (hunger > starvingBorder && hunger < hungryBorder && size < bigFishBorder)
		fishStrategy = new SHungryFishStrategy();
	else
		fishStrategy = new fullFishStrategy();
  }
  
  public void move(Pond pond)
  {
	fishStrategy.move(this, pond, x, y);
  }

  // Swim towards a location
  public void swimTowards(double tx, double ty)
  {
    double distance = Math.sqrt((tx - x)*(tx - x) + (ty - y)*(ty - y));
    x = x + (tx/distance);
    y = y + (ty/distance);
    updateVars();
  }


  // Swim away from a location
  public void swimAway(double tx, double ty)
  {
    double distance = Math.sqrt((tx - x)*(tx - x) + (ty - y)*(ty - y));
    x = x - (tx/distance);
    y = y - (ty/distance);

    updateVars();
  }


  // Just swim around
  public void swimRandomly()
  {
    System.out.println("FISH #" + id + ": I'm swimming around!");

    x = x + random.nextDouble();
    y = y + random.nextDouble();

    updateVars();
  }

  // Just let the world know I hid!
  public void hide()
  {
    System.out.println("FISH #" + id + ": I'm hiding!");
  }
}
