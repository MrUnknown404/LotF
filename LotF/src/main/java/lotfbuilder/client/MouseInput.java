package main.java.lotfbuilder.client;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;

import main.java.lotf.items.ItemEmpty;
import main.java.lotf.items.util.Slot;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.Room;
import main.java.lotfbuilder.MainBuilder;

public final class MouseInput extends MouseAdapter {
	
	private static Vec2i camVec = new Vec2i(), hudVec = new Vec2i();
	private int mouse;
	
	public void mousePressed(MouseEvent e) {
		int key = e.getButton();
		mouse = key;
		setVecs(e);
		
		if (MainBuilder.getDoesRoomExist()) {
			if (key == 1) {
				if (MainBuilder.getRoomBuilder().isOpen) {
					List<Slot> ss = MainBuilder.getRoomBuilder().getSelectedPage().getSlotsList();
					for (int i = 0; i < ss.size(); i++) {
						Slot s = ss.get(i);
						if (new Rectangle(hudVec.getX(), hudVec.getY(), 1, 1).intersects(s.getBoundsAll().x, s.getBoundsAll().y, s.getBoundsAll().width, s.getBoundsAll().height)) {
							if (!(MainBuilder.getRoomBuilder().getSelectedPage().getItems().get(i) instanceof ItemEmpty)) {
								if (MainBuilder.getRoomBuilder().hand == null) {
									MainBuilder.getRoomBuilder().hand = MainBuilder.getRoomBuilder().getSelectedPage().getItems().get(i);
								} else {
									MainBuilder.getRoomBuilder().hand = null;
								}
							} else {
								MainBuilder.getRoomBuilder().hand = null;
							}
						}
					}
				}
				
				boolean tb = false;
				List<Slot> ss = MainBuilder.getRoomBuilder().selInv.getSlotsList();
				for (int i = 0; i < ss.size(); i++) {
					Slot s = ss.get(i);
					if (new Rectangle(hudVec.getX(), hudVec.getY(), 1, 1).intersects(s.getBoundsAll().x, s.getBoundsAll().y - 38, s.getBoundsAll().width, s.getBoundsAll().height)) {
						if (MainBuilder.getRoomBuilder().isOpen) {
							if (MainBuilder.getRoomBuilder().hand != null) {
								MainBuilder.getRoomBuilder().selInv.getItems().set(i, MainBuilder.getRoomBuilder().hand);
								MainBuilder.getRoomBuilder().hand = null;
							} else {
								MainBuilder.getRoomBuilder().selInv.getItems().set(i, new ItemEmpty());
							}
						} else {
							MainBuilder.getRoomBuilder().selectedSlot = i;
						}
						tb = true;
					}
				}
				
				if (!tb) {
					place(e);
				}
			} else if (key == 3) {
				delete(e);
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		setVecs(e);
	}
	
	public void mouseDragged(MouseEvent e) {
		setVecs(e);
		
		if (MainBuilder.getDoesRoomExist()) {
			if (mouse == 1) {
				place(e);
			} else if (mouse == 3) {
				delete(e);
			}
		}
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (MainBuilder.getDoesRoomExist()) {
			if (e.getWheelRotation() == -1) {
				if (!MainBuilder.getRoomBuilder().isOpen) {
					MainBuilder.getRoomBuilder().decreaseSelectedSlot();
				} else {
					MainBuilder.getRoomBuilder().addSelectedPage();
				}
			} else if (e.getWheelRotation() == 1) {
				if (!MainBuilder.getRoomBuilder().isOpen) {
					MainBuilder.getRoomBuilder().addSelectedSlot();
				} else {
					MainBuilder.getRoomBuilder().decreaseSelectedPage();
				}
			}
		}
	}
	
	public void setVecs(MouseEvent e) {
		camVec = new Vec2i((e.getX() / MainBuilder.scale) - MainBuilder.getCamera().pos.getX(), (e.getY() / MainBuilder.scale) - MainBuilder.getCamera().pos.getY());
		hudVec = new Vec2i(e.getX() / MainBuilder.scale, e.getY() / MainBuilder.scale);
	}
	
	public void place(MouseEvent e) {
		setVecs(e);
		
		if (MainBuilder.getRoomBuilder().isOpen) {
			return;
		}
		
		Room r = MainBuilder.getRoomBuilder().getRoom();
		if (new Rectangle(r.getBounds().x, r.getBounds().y, r.getBounds().width, r.getBounds().height).intersects(new Rectangle(camVec.getX(), camVec.getY(), 1, 1))) {
			MainBuilder.getRoomBuilder().setTile(new Vec2i(camVec.getX() / Tile.TILE_SIZE, camVec.getY() / Tile.TILE_SIZE));
		}
	}
	
	public void delete(MouseEvent e) {
		setVecs(e);
		
		if (MainBuilder.getRoomBuilder().isOpen) {
			return;
		}
		
		Room r = MainBuilder.getRoomBuilder().getRoom();
		if (new Rectangle(r.getBounds().x, r.getBounds().y, r.getBounds().width, r.getBounds().height).intersects(new Rectangle(camVec.getX(), camVec.getY(), 1, 1))) {
			MainBuilder.getRoomBuilder().deleteTile(new Vec2i(camVec.getX() / Tile.TILE_SIZE, camVec.getY() / Tile.TILE_SIZE));
		}
	}
	
	public static Vec2i getHudVec() {
		return hudVec;
	}
}
