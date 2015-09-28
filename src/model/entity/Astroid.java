package model.entity;

import static model.GameGlobals.GHEIGHT;
import static model.GameGlobals.GWIDTH;
import static model.GameGlobals.MAX_ASTROID;

import java.awt.Dimension;

import controller.behaviour.AnimateBehaviour;
import controller.behaviour.CollisionBehaviour;
import controller.behaviour.MoveBehaviour;
import controller.behaviour.RenderBehaviour;
import controller.behaviour.UpdateBehaviour;
import library.Point;
import library.Rand;
import library.Vector;
import model.EntityID;
import model.ResourceManager;

public class Astroid extends GameObject {
	private MoveBehaviour moveContainer;
	private UpdateBehaviour updateContainer;
	private CollisionBehaviour collisionContainer;
	private AnimateBehaviour animateContainer;
	private RenderBehaviour renderContainer;
	
	private static int spawnCount = 0;
	
	public Astroid(MoveBehaviour mb, UpdateBehaviour ub, CollisionBehaviour cb, AnimateBehaviour ab, RenderBehaviour rb) {
		super(EntityID.Astroid);
		
		init();
		
		moveContainer = mb;
		moveContainer.add(this);
		
		updateContainer = ub;
		updateContainer.add(this);
		
		collisionContainer = cb;
		collisionContainer.add(this);
		
		animateContainer = ab;
		animateContainer.add(this);
		
		renderContainer = rb;
		renderContainer.add(this);
		
		spawnCount++;
	}
	
	private void init(){
		int size = Rand.randInt((GWIDTH/ 100) * 5, (GWIDTH / 100) * 10);
		setSize(new Dimension(size, size));
		
		Vector min;
		Vector max;
		
		switch(Rand.randInt(0,3)){
			case 0:
				
				System.out.println("Spawn Astroid: North");
				// North
				// Start position
				setPosition(new Point(Rand.randInt(size, GWIDTH - size), -size));
				
				// Direction
				min = new Vector((0 - getPosition().getX()) / (GHEIGHT - getPosition().getY()), 1f);
				max = new Vector((GWIDTH - getPosition().getX()) / (GHEIGHT - getPosition().getY()) , 1f);				
				
				setDirection(new Vector(Rand.randFloat(min.getX(), max.getX()), 1f));
				break;
			case 1:
				System.out.println("Spawn Astroid: East");
				// East
				setPosition(new Point(GWIDTH + size, Rand.randInt(size, GHEIGHT -size)));
				
				// Direction
				min = new Vector(1f, (0 - getPosition().getY()) / (0 - getPosition().getX()));
				max = new Vector(1f, (GHEIGHT - getPosition().getY()) / (0 - getPosition().getX()));				
				
				setDirection(new Vector(-1f, Rand.randFloat(min.getY(), max.getY())));
				break;
			case 2:
				System.out.println("Spawn Astroid: South");
				// South
				setPosition(new Point(Rand.randInt(size, GWIDTH - size), GHEIGHT + size));
				
				// Direction
				min = new Vector((0 - getPosition().getX()) / (getPosition().getY()), -1f);
				max = new Vector((GWIDTH - getPosition().getX()) / (getPosition().getY()) , -1f);				
				
				setDirection(new Vector(Rand.randFloat(min.getX(), max.getX()), -1));
				break;
			case 3:
				System.out.println("Spawn Astroid: West");
				//West
				setPosition(new Point(-size, Rand.randInt(size, GHEIGHT - size)));
				// Direction
				min = new Vector(1f, (0 - getPosition().getY()) / (GWIDTH - getPosition().getX()));
				max = new Vector(1f, (GHEIGHT - getPosition().getY()) / (GWIDTH - getPosition().getX()));				
				
				setDirection(new Vector(1f, Rand.randFloat(min.getY(), max.getY())));
				break;
				default:
					break;
		
		}
		
		setDirection(Vector.multiplyVector(getDirection(), Rand.randFloat(2, 10)));
		
		setSprite(ResourceManager.getInstance().getSprite("astroid"));
		setAlive(true);
	}
	
	public static boolean needsSpawn(){
		if(spawnCount < MAX_ASTROID){
			return true;
		}
		
		return false;
	}
	
	public void die(){		
		moveContainer.remove(this);
		updateContainer.remove(this);
		collisionContainer.remove(this);
		animateContainer.remove(this);
		renderContainer.remove(this);
		
		spawnCount--;
	}
}
