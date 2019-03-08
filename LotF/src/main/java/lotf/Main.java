package main.java.lotf;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;
import java.io.File;

import main.java.lotf.client.Camera;
import main.java.lotf.client.KeyInput;
import main.java.lotf.client.MouseInput;
import main.java.lotf.client.Renderer;
import main.java.lotf.client.Window;
import main.java.lotf.client.gui.ConsoleHud;
import main.java.lotf.client.gui.DebugHud;
import main.java.lotf.client.gui.Hud;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.init.InitCommands;
import main.java.lotf.init.InitItems;
import main.java.lotf.util.Console;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.WorldHandler;
import main.java.lotfbuilder.MainBuilder;

public final class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = -2518563563721413864L;
	
	private static final int HUD_WIDTH = 256, HUD_HEIGHT = 144;
	private static int width = 256, height = 144, w2, h2;
	private static boolean isDebug, isBuilder;
	
	private int fps;
	private boolean running = false;
	
	private Thread thread;
	
	private static final DebugConsole CONSOLE = new DebugConsole();
	private final DebugHud debugHud = new DebugHud();
	private final ConsoleHud consoleHud = new ConsoleHud();
	private final Hud hud = new Hud();
	
	private static WorldHandler worldHandler;
	private static Camera camera;
	private Renderer renderer;
	private KeyInput keyInput;
	
	private static final String SAVE_LOCATION = System.getProperty("user.home") + "/Documents/My Games/LotF/";
	private static final String BASE_LOCATION_ROOMS = "main/resources/lotf/assets/rooms/";
	private static final String BASE_LOCATION_TEXTURES = "/main/resources/lotf/assets/textures/";
	
	public static Gamestate gamestate = Gamestate.run;
	
	public static void main(String args[]) {
		for (String str : args) {
			if (str.equals("-debug")) {
				isDebug = true;
				new MainConsole();
			} else if (str.equals("-builder")) {
				isBuilder = true;
			}
		}
		
		if (isBuilder) {
			new MainBuilder();
		} else {
			new Main();
		}
	}
	
	public Main() {
		new Window("LotF!", this);
	}
	
	public synchronized void start() {
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
		
		Console.print(Console.WarningType.Info, "Creating file path!");
		File f = new File(SAVE_LOCATION);
		if (!f.exists()) {
			f.mkdirs();
		}
		
		MouseInput mouse = new MouseInput();
		keyInput = new KeyInput();
		
		addKeyListener(keyInput);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		addMouseWheelListener(mouse);
		addComponentListener(new ComponentListener() {
			@Override public void componentShown(ComponentEvent e) {}
			@Override public void componentMoved(ComponentEvent e) {}
			@Override public void componentHidden(ComponentEvent e) {}
			
			@Override
			public void componentResized(ComponentEvent e) {
				resize();
			}
		});
		
		InitItems.registerAll();
		
		Console.print(Console.WarningType.Info, "Pre-Initialization finished!");
	}
	
	private void init() {
		Console.print(Console.WarningType.Info, "Initialization started...");
		
		InitCommands.registerAll();
		
		Console.print(Console.WarningType.Info, "Initialization finished!");
	}
	
	private void postInit() {
		Console.print(Console.WarningType.Info, "Post-Initialization started...");
		
		worldHandler = new WorldHandler();
		camera = new Camera();
		
		renderer = new Renderer();
		
		renderer.loadTextures();
		hud.loadTextures();
		hud.loadFonts();
		
		Console.print(Console.WarningType.Info, "Post-Initialization finished!");
		
		thread = new Thread(this);
		thread.start();
		running = true;
		Console.print(Console.WarningType.Info, "Started thread!");
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		Console.print(Console.WarningType.Info, "Started run loop!");
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
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
		keyInput.tick();
		
		if (Main.gamestate.fId == 0) {
			worldHandler.tick();
			worldHandler.tickAnimation();
			camera.tick();
		} else if (Main.gamestate.fId == 1) {
			worldHandler.tickAnimation();
		} else if (Main.gamestate.fId == 2) {
			
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			Console.print(Console.WarningType.Info, "Started render loop!");
			return;
		}
		
		Graphics g2 = bs.getDrawGraphics();
		Graphics2D g = (Graphics2D) g2;
		
		double scale = Math.min((double) width / HUD_WIDTH, (double) height / HUD_HEIGHT);
		int w = (int) Math.ceil((HUD_WIDTH * scale));
		w2 = (int) Math.ceil((width - w) / scale);
		int h = (int) Math.ceil((HUD_HEIGHT * scale));
		h2 = (int) Math.ceil((height - h) / scale);
		
		g.scale(scale, scale);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		g2.translate(w2 / 2, h2 / 2);
		g2.translate(-camera.getPos().getX(), -camera.getPos().getY());
		renderer.render(g);
		g2.translate(camera.getPos().getX(), camera.getPos().getY());
		
		g.setColor(Color.BLACK);
		g.fillRect((int) (w / scale), 0, w2, height);
		g.fillRect(-w2, 0, w2, height);
		g.fillRect(0, (int) (h / scale), width, h2);
		g.fillRect(0, -h2, width, h2);
		
		hud.render(g);
		
		debugHud.getInfo(worldHandler.getPlayer());
		debugHud.drawText(g, "FPS: " + fps);
		consoleHud.draw(g);
		
		g2.dispose();
		bs.show();
	}
	
	public static void save() {
		Console.print(Console.WarningType.Info, "Started saving...");
		gamestate = Gamestate.hardPause;
		worldHandler.getPlayer().savePlayerData();
		gamestate = Gamestate.run;
		Console.print(Console.WarningType.Info, "Finished saving!");
	}
	
	public static void load() {
		Console.print(Console.WarningType.Info, "Started loading...");
		worldHandler.getPlayer().loadPlayerData();
		Console.print(Console.WarningType.Info, "Finished loading!");
	}
	
	private void resize() {
		width = MathHelper.clamp(getWidth(), 0, Integer.MAX_VALUE);
		height = MathHelper.clamp(getHeight(), 0, Integer.MAX_VALUE);
	}
	
	public static int getWindowWidth() {
		return width;
	}
	
	public static int getWindowHeight() {
		return height;
	}
	
	public static int getGameWidth() {
		return w2;
	}
	
	public static int getGameHeight() {
		return h2;
	}
	
	public static int getHudWidth() {
		return HUD_WIDTH;
	}
	
	public static int getHudHeight() {
		return HUD_HEIGHT;
	}
	
	public static boolean getIsDebug() {
		return isDebug;
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
	
	public static DebugConsole getCommandConsole() {
		return CONSOLE;
	}
	
	public static Camera getCamera() {
		return camera;
	}
	
	public static WorldHandler getWorldHandler() {
		return worldHandler;
	}
	
	public enum Gamestate {
		run      (0),
		softPause(1),
		hardPause(2);
		
		public final int fId;
		
		private Gamestate(int id) {
			fId = id;
		}
		
		public static Gamestate getFromNumber(int id) {
			for (Gamestate type : values()) {
				if (type.fId == id) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid Type id: " + id);
		}
	}
}

