package main.java.lotf;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;

import main.java.lotf.client.KeyInput;
import main.java.lotf.client.MouseInput;
import main.java.lotf.client.Renderer;
import main.java.lotf.client.Window;
import main.java.lotf.client.gui.ConsoleHud;
import main.java.lotf.client.gui.DebugHud;
import main.java.lotf.client.gui.Hud;
import main.java.lotf.commands.util.DebugConsole;
import main.java.lotf.init.InitCommands;
import main.java.lotf.init.InitEntities;
import main.java.lotf.init.InitItems;
import main.java.lotf.util.Console;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.Vec2i;
import main.java.lotf.world.RoomHandler;

public final class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = -2518563563721413864L;
	
	public static final int WIDTH_DEF = 224 * 2, HEIGHT_DEF = 126 * 2;
	private static int width = 432, height = 213;
	
	private int fps;
	private boolean running = false;
	private static final boolean isDebug = false;
	
	private Thread thread;
	
	private static final DebugConsole CONSOLE = new DebugConsole();
	private final DebugHud debugHud = new DebugHud();
	private final ConsoleHud consoleHud = new ConsoleHud();
	private final Hud hud = new Hud();
	
	private static RoomHandler roomHandler;
	private Renderer renderer;
	
	public static Gamestate gamestate = Gamestate.run;
	
	public static void main(String args[]) {
		if (isDebug) {
			new ConsoleMain();
		}
		
		new Main();
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
		
		MouseInput mouse = new MouseInput();
		
		addKeyListener(new KeyInput());
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
		InitEntities.registerAll();
		
		Console.print(Console.WarningType.Info, "Pre-Initialization finished!");
	}
	
	private void init() {
		Console.print(Console.WarningType.Info, "Initialization started...");
		
		InitCommands.registerAll();
		
		Console.print(Console.WarningType.Info, "Initialization finished!");
	}
	
	private void postInit() {
		Console.print(Console.WarningType.Info, "Post-Initialization started...");
		
		roomHandler = new RoomHandler();
		
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
		if (Main.gamestate.fId >= 1) {
			return;
		}
		
		consoleHud.tick();
		
		roomHandler.tick();
	}
	
	public static float scaleWidth, scaleHeight;
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			Console.print(Console.WarningType.Info, "Started render loop!");
			return;
		}
		
		Graphics g2 = bs.getDrawGraphics();
		Graphics2D g = (Graphics2D) g2;
		
		double scale = Math.min((double) width / WIDTH_DEF, (double) height / HEIGHT_DEF);
		int w = (int) Math.ceil((WIDTH_DEF * scale));
		int w2 = (int) Math.ceil((width - w) / scale);
		int h = (int) Math.ceil((HEIGHT_DEF * scale));
		int h2 = (int) Math.ceil((height - h) / scale);
		
		g.scale(scale, scale);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		g2.translate(w2 / 2, h2 / 2);
		//g2.translate(CAMERA.getPositionX(), CAMERA.getPositionY());
		renderer.render(g);
		//g2.translate(-CAMERA.getPositionX(), -CAMERA.getPositionY());
		
		g.setColor(Color.RED); //change to black later
		g.fillRect((int) (w / scale), 0, w2, height);
		g.fillRect(-w2, 0, w2, height);
		g.fillRect(0, (int) (h / scale), width, h2);
		g.fillRect(0, -h2, width, h2);
		
		hud.render(g);
		
		debugHud.getInfo(roomHandler.getPlayer());
		debugHud.drawText(g, "FPS: " + fps);
		consoleHud.draw(g);
		
		g2.dispose();
		bs.show();
	}
	
	private void resize() {
		width = MathHelper.clamp(getWidth(), 0, Integer.MAX_VALUE);
		height = MathHelper.clamp(getHeight(), 0, Integer.MAX_VALUE);
	}
	
	public static boolean getIsDebug() {
		return isDebug;
	}
	
	public static DebugConsole getCommandConsole() {
		return CONSOLE;
	}
	
	public static RoomHandler getRoomHandler() {
		return roomHandler;
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

