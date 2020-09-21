package main.java.lotf.util;

import main.java.lotf.Main.Gamestate;

public interface IStateTickable extends ITickable {
	public Gamestate whenToTick();
}
