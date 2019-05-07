package main.java.lotf;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;
import java.io.File;

import main.java.lotf.client.KeyHandler;
import main.java.lotf.client.Window;
import main.java.lotf.client.gui.ConsoleHud;
import main.java.lotf.client.gui.DebugHud;
import main.java.lotf.client.renderer.Renderer;
import main.java.lotf.commands.InitCommands;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.util.Console;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.World;

public final class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = -2518563563721413864L;
	private static Main main;
	
	private static final int HUD_WIDTH = 256, HUD_HEIGHT = 144;
	private int width = HUD_WIDTH, height =HUD_HEIGHT, w2, h2;
	
	private static final String SAVE_LOCATION = System.getProperty("user.home") + "/Documents/My Games/LotF/";
	private static final String BASE_LOCATION_ROOMS = "/main/resources/lotf/assets/rooms/";
	private static final String BASE_LOCATION_TEXTURES = "/main/resources/lotf/assets/textures/";
	
	private Gamestate gamestate = Gamestate.run, prevGamestate;
	
	private int fps;
	private boolean running = false;
	private Thread thread;
	
	private final DebugConsole console = new DebugConsole();
	private final ConsoleHud consoleHud = new ConsoleHud();
	private final DebugHud debugHud = new DebugHud();
	
	private Renderer renderer;
	
	private World world;
	
	public static void main(String args[]) {
		main = new Main();
		new Window("LotF!", main);
		main.start();
	}
	
	private synchronized void start() {
		Console.getTimeExample();
		Console.print("Window size: " + new Vec2i(width, height));
		Console.print(Console.WarningType.Info, "Starting!");
		
		resize();
		
		preInit();
		init();
		postInit();
	}
	
	private void preInit() {
		Console.print(Console.WarningType.Info, "Pre-Initialization started...");
		
		File f = new File(SAVE_LOCATION);
		if (!f.exists()) {
			f.mkdirs();
		}
		Console.print(Console.WarningType.Info, "Created file path!");
		
		renderer = new Renderer();
		renderer.getTextures();
		
		InitCommands.registerAll();
		
		addKeyListener(new KeyHandler().registerKeys());
		addComponentListener(new ComponentListener() {
			@Override public void componentShown(ComponentEvent e) {}
			@Override public void componentMoved(ComponentEvent e) {}
			@Override public void componentHidden(ComponentEvent e) {}
			
			@Override
			public void componentResized(ComponentEvent e) {
				resize();
			}
		});
		
		Console.print(Console.WarningType.Info, "World creation started...");
		world = new World(new Vec2i(2, 2)); //TODO temporary
		Console.print(Console.WarningType.Info, "World creation finished!");
		
		Console.print(Console.WarningType.Info, "Pre-Initialization finished!");
	}
	
	private void init() {
		Console.print(Console.WarningType.Info, "Initialization started...");
		
		Console.print(Console.WarningType.Info, "Initialization finished!");
	}
	
	private void postInit() {
		Console.print(Console.WarningType.Info, "Post-Initialization started...");
		
		Console.print(Console.WarningType.Info, "Post-Initialization finished!");
		
		thread = new Thread(this);
		thread.start();
		running = true;
		Console.print(Console.WarningType.Info, "Started thread!");
	}
	
	private synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		Console.print(Console.WarningType.Info, "Started run loop!");
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
	
	private void tick() {
		consoleHud.tick();
		renderer.tick();
		
		if (gamestate == Gamestate.run) {
			world.tick();
		} else if (gamestate == Gamestate.softPause) {
			
		} else if (gamestate == Gamestate.hardPause) {
			
		}
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			Console.print(Console.WarningType.Info, "Started render loop!");
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		double scale = Math.min((double) width / HUD_WIDTH, (double) height / HUD_HEIGHT);
		int w = (int) Math.ceil((HUD_WIDTH * scale));
		w2 = (int) Math.ceil((width - w) / scale);
		int h = (int) Math.ceil((HUD_HEIGHT * scale));
		h2 = (int) Math.ceil((height - h) / scale);
		
		g.scale(scale, scale);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		g.translate(w2 / 2, h2 / 2);
		renderer.render(g);
		
		g.setColor(Color.BLACK);
		g.fillRect((int) (w / scale), 0, w2, height);
		g.fillRect(-w2, 0, w2, height);
		g.fillRect(0, (int) (h / scale), width, h2);
		g.fillRect(0, -h2, width, h2);
		
		debugHud.render(g, "" + fps);
		consoleHud.draw(g);
		
		g.dispose();
		bs.show();
	}
	
	public void setGamestate(Gamestate state) {
		prevGamestate = gamestate;
		gamestate = state;
	}
	
	public void revertGamestate() {
		gamestate = prevGamestate;
	}
	
	private void resize() {
		width = MathHelper.clamp(getWidth(), 0, Integer.MAX_VALUE);
		height = MathHelper.clamp(getHeight(), 0, Integer.MAX_VALUE);
	}
	
	public boolean shouldPlayerHaveControl() {
		if (console.isConsoleOpen()) {
			return false;
		}
		
		return true;
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
	
	public static int getHudWidth() {
		return HUD_WIDTH;
	}
	
	public static int getHudHeight() {
		return HUD_HEIGHT;
	}
	
	public static String getSaveLocation() {
		return SAVE_LOCATION;
	}
	
	public static String getBaseLocationRooms() {
		return BASE_LOCATION_ROOMS;
	}
	
	public static String getBaseLocationTextures() {
		return BASE_LOCATION_TEXTURES;
	}
	
	public World getWorld() {
		return world;
	}
	
	public DebugConsole getCommandConsole() {
		return console;
	}
	
	public Gamestate getGamestate() {
		return gamestate;
	}
	
	public static Main getMain() {
		return main;
	}
	
	public enum Gamestate {
		run,
		softPause,
		hardPause;
	}
}

