package main.java.lotf.entities.ai.movement;

import java.util.Random;

import main.java.lotf.entities.ai.AIType;
import main.java.lotf.entities.util.Entity;
import main.java.lotf.util.enums.EnumDirection;

public class AiMovementRandom<T extends Entity> implements AIType<T> {

	protected final T parent;
	protected final float moveSpeed;
	private boolean isMoving;
	private int chanceToMoveTimer = 120 + new Random().nextInt(120), moveTimer;
	private EnumDirection dir;
	
	public AiMovementRandom(T parent, float moveSpeed) {
		this.parent = parent;
		this.moveSpeed = moveSpeed;
	}
	
	@Override
	public boolean attemptAction() {
		if (isMoving) {
			if (moveTimer > 0) {
				moveTimer--;
			} else {
				isMoving = false;
			}
		} else {
			if (chanceToMoveTimer == 0) {
				chanceToMoveTimer = 120 + new Random().nextInt(120);
				moveTimer = 40 + new Random().nextInt(30);
				isMoving = true;
				
				dir = EnumDirection.values()[new Random().nextInt(4)];
				//TODO don't move towards direction if its a wall
			} else {
				chanceToMoveTimer--;
			}
		}
		
		return isMoving;
	}
	
	@Override
	public void action() {
		switch (dir) {
			case east:
				parent.addPosX(moveSpeed);
				break;
			case north:
				parent.addPosY(-moveSpeed);
				break;
			case south:
				parent.addPosY(moveSpeed);
				break;
			case west:
				parent.addPosX(-moveSpeed);
				break;
		}
	}
	
	@Override
	public boolean isActionRunning() {
		return isMoving;
	}
	
	@Override
	public void reset() {
		isMoving = false;
		chanceToMoveTimer = 120 + new Random().nextInt(120);
	}
	
	@Override
	public T getParent() {
		return parent;
	}
}
