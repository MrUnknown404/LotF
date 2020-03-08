package main.java.lotf;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import main.java.lotf.client.Camera;
import main.java.lotf.client.KeyHandler;
import main.java.lotf.client.Window;
import main.java.lotf.client.gui.HudConsole;
import main.java.lotf.client.gui.HudDebug;
import main.java.lotf.client.gui.HudInventory;
import main.java.lotf.client.gui.HudMain;
import main.java.lotf.client.renderer.Renderer;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.init.Collectibles;
import main.java.lotf.init.Commands;
import main.java.lotf.init.Items;
import main.java.lotf.init.Tiles;
import main.java.lotf.util.ConfigHandler;
import main.java.lotf.util.Console;
import main.java.lotf.util.Console.WarningType;
import main.java.lotf.util.GetResource;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.WorldHandler;

/**
 * @author -Unknown-
 */
public final class Main {
	private static Main main;
	private static GameLoop gameLoop;
	
	public static final int HUD_WIDTH = 256, HUD_HEIGHT = 144;
	private int width = HUD_WIDTH, height = HUD_HEIGHT, w2, h2;
	private static int fps;
	
	public static final String SAVE_LOCATION = System.getProperty("user.home") + "/Documents/My Games/LotF/";
	public static final String ROOM_FOLDER_LOCATION = "/main/resources/lotf/assets/rooms/";
	public static final String TEXTURE_FOLDER_LOCATION = "/main/resources/lotf/assets/textures/";
	public static final String FONT_FOLDER_LOCATION = "/main/resources/lotf/assets/fonts/";
	public static final String LANG_FOLDER_LOCATION = "/main/resources/lotf/assets/lang/";
	
	private Map<String, Gamestate> gamestate = new HashMap<String, Gamestate>();
	
	private final DebugConsole console = new DebugConsole();
	private final HudConsole consoleHud = new HudConsole();
	private final HudDebug debugHud = new HudDebug();
	private final HudMain mainHud = new HudMain();
	private final HudInventory invHud = new HudInventory();
	
	private Renderer renderer;
	private Camera camera;
	private KeyHandler keyHandler;
	private WorldHandler worldHandler;
	
	public static boolean isDebug;
	
	public static void main(String args[]) {
		for (String s : args) {
			if (s.equalsIgnoreCase("-debug")) {
				isDebug = true;
			}
		}
		
		main = new Main();
		main.start();
	}
	
	private synchronized void start() {
		new Window("LotF!", gameLoop = new GameLoop());
		Console.getTimeExample();
		Console.print(WarningType.Info, "Window size: " + new Vec2i(width, height));
		Console.print(WarningType.Info, "Starting!");
		
		preInit();
		init();
		postInit();
		
		gameLoop.start();
	}
	
	private void preInit() {
		Console.print(WarningType.Info, "Pre-Initialization started...");
		
		File f = new File(SAVE_LOCATION);
		if (!f.exists()) {
			f.mkdirs();
			Console.print(WarningType.Info, "File path does not exist! Created file path now!");
		}
		
		keyHandler = new KeyHandler();
		
		ConfigHandler.loadConfigs(keyHandler);
		GetResource.getLangFile();
		
		Commands.registerAll();
		Collectibles.registerAll();
		Items.registerAll();
		Tiles.registerAll();
		
		renderer = new Renderer();
		renderer.getTextures();
		
		mainHud.setupFonts();
		invHud.setupFonts();
		mainHud.setupTextures();
		invHud.setupTextures();
		
		gameLoop.setupComponents();
		
		Console.print(WarningType.Info, "Pre-Initialization finished!");
	}
	
	private void init() {
		Console.print(WarningType.Info, "Initialization started...");
		
		worldHandler = new WorldHandler();
		camera = new Camera();
		
		Console.print(WarningType.Info, "Initialization finished!");
	}
	
	private void postInit() {
		Console.print(WarningType.Info, "Post-Initialization started...");
		
		Console.print(WarningType.Info, "Post-Initialization finished!");
	}
	
	private void tick() {
		keyHandler.tick();
		
		if (getGamestate() == Gamestate.run) {
			worldHandler.tick();
			renderer.tick();
			camera.tick();
		} else if (getGamestate() == Gamestate.softPause) {
			invHud.tick();
			worldHandler.getPlayer().tick();
			camera.tick();
		} else if (getGamestate() == Gamestate.hardPause) {
			if (isDebug) {
				consoleHud.tick();
			}
		}
	}
	
	private void render(Graphics2D g) {
		double scale = Math.min((double) width / HUD_WIDTH, (double) height / HUD_HEIGHT);
		int w = (int) Math.ceil((HUD_WIDTH * scale));
		w2 = (int) Math.ceil((width - w) / scale);
		int h = (int) Math.ceil((HUD_HEIGHT * scale));
		h2 = (int) Math.ceil((height - h) / scale);
		
		g.scale(scale, scale);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		g.translate(w2 / 2, h2 / 2);
		g.translate(-camera.getPosX(), -camera.getPosY());
		renderer.render(g);
		g.translate(camera.getPosX(), camera.getPosY());
		
		g.setColor(Color.BLACK);
		g.fillRect((int) (w / scale), 0, w2, height);
		g.fillRect(-w2, 0, w2, height);
		g.fillRect(0, (int) (h / scale), width, h2);
		g.fillRect(0, -h2, width, h2);
		
		mainHud.draw(g);
		invHud.draw(g);
		
		if (isDebug) {
			debugHud.draw(g);
			consoleHud.draw(g);
		}
		
		g.dispose();
	}
	
	public void setGamestate(Class<?> clazz, Gamestate state) {
		gamestate.put(clazz.getSimpleName(), state);
	}
	
	public boolean shouldPlayerHaveControl() {
		if ((isDebug && console.isConsoleOpen()) || getGamestate() != Gamestate.run) {
			return false;
		}
		
		return true;
	}
	
	public Gamestate getGamestate() {
		for (Entry<String, Gamestate> e : gamestate.entrySet()) {
			if (e.getValue() != Gamestate.run) {
				return e.getValue();
			}
		}
		
		return Gamestate.run;
	}
	
	public int getWindowWidth() {
		return width;
	}
	
	public int getWindowHeight() {
		return height;
	}
	
	public int getGameWidth() {
		return w2;
	}
	
	public int getGameHeight() {
		return h2;
	}
	
	public int getFPS() {
		return fps;
	}
	
	public WorldHandler getWorldHandler() {
		return worldHandler;
	}
	
	public DebugConsole getCommandConsole() {
		return console;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public static Main getMain() {
		return main;
	}
	
	public enum Gamestate {
		run,
		softPause,
		hardPause
	}
	
	public void requestFocus() {
		gameLoop.requestFocus();
	}
	
	public class GameLoop extends Canvas implements Runnable {
		private static final long serialVersionUID = -2518563563721413864L;
		
		private boolean running = false;
		private Thread thread;
		
		public void start() {
			thread = new Thread(this);
			thread.start();
			running = true;
			
			Console.print(WarningType.Info, "Started thread!");
			resize();
		}
		
		@Override
		public void run() {
			Console.print(WarningType.Info, "Started run loop!");
			requestFocus();
			long lastTime = System.nanoTime(), timer = System.currentTimeMillis();
			double amountOfTicks = 60.0, ns = 1000000000 / amountOfTicks, delta = 0;
			int frames = 0;
			
			while (running) {
				long now = System.nanoTime();
				delta += (now - lastTime) / ns;
				lastTime = now;
				
				while (delta >= 1) {
					tick();
					delta--;
				}
				
				if (running) {
					render();
					frames++;
					
					if (System.currentTimeMillis() - timer > 1000) {
						timer += 1000;
						fps = frames;
						frames = 0;
					}
				} else {
					stop();
				}
			}
		}
		
		private void render() {
			if (!Window.canRender) {
				return;
			}
			
			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				Console.print(WarningType.Info, "Started render loop!");
				return;
			}
			
			Main.this.render((Graphics2D) bs.getDrawGraphics());
			
			bs.show();
		}
		
		private synchronized void stop() {
			try {
				thread.join();
				running = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void setupComponents() {
			addKeyListener(keyHandler);
			addComponentListener(new ComponentListener() {
				@Override public void componentShown(ComponentEvent e) {}
				@Override public void componentMoved(ComponentEvent e) {}
				@Override public void componentHidden(ComponentEvent e) {}
				
				@Override
				public void componentResized(ComponentEvent e) {
					resize();
				}
			});
		}
		
		private void resize() {
			width = MathH.clamp(getWidth(), 0, Integer.MAX_VALUE);
			height = MathH.clamp(getHeight(), 0, Integer.MAX_VALUE);
		}
	}
}
