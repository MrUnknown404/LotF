package main.java.lotf.entities;

import java.util.ArrayList;
import java.util.List;

import main.java.lotf.entities.util.Entity;
import main.java.lotf.entities.util.EntityInfo;
import main.java.lotf.util.IInteractable;
import main.java.lotf.util.math.Vec2f;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;

public class EntityNPC extends Entity implements IInteractable {
	
	protected final boolean useRandomDialogue;
	protected List<String> dialogue = new ArrayList<String>();
	protected int currentLine = 0;
	
	public EntityNPC(EntityInfo info, Room room, Vec2f pos, Vec2i size, List<String> dialogue, boolean useRandomDialogue) {
		super(info, room, pos, size);
		this.dialogue = dialogue;
		this.useRandomDialogue = useRandomDialogue;
	}
	
	public EntityNPC(EntityInfo info, Room room, Vec2f pos, List<String> dialogue, boolean useRandomDialogue) {
		super(info, room, pos, new Vec2i(14, 14));
		this.dialogue = dialogue;
		this.useRandomDialogue = useRandomDialogue;
	}
	
	@Override
	public void tick() {
		
	}
	
	@Override
	public void softReset() {
		hardReset();
	}
	
	@Override
	public void hardReset() {
		if (!useRandomDialogue) {
			currentLine = 0;
		}
	}
	
	@Override
	public void onInteract() {
		
	}
}
