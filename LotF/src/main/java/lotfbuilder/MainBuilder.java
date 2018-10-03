package main.java.lotfbuilder;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;

import main.java.lotf.Main;
import main.java.lotf.util.Console;
import main.java.lotf.util.math.MathHelper;
import main.java.lotf.util.math.Vec2i;
import main.java.lotfbuilder.client.Camera;
import main.java.lotfbuilder.client.KeyInput;
import main.java.lotfbuilder.client.MouseInput;
import main.java.lotfbuilder.client.Renderer;
import main.java.lotfbuilder.client.Window;
import main.java.lotfbuilder.client.gui.DebugHud;
import main.java.lotfbuilder.room.RoomBuilder;

public final class MainBuilder extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private static final int HUD_WIDTH = 224 * 2, HUD_HEIGHT = 126 * 2;
	private static int width = 432, height = 213, w2, h2;
	
	private static final DebugHud debugHud = new DebugHud();
	private static Renderer renderer;
	
	private int fps;
	private boolean running = false;
	
	private Thread thread;
	
	private static boolean doesRoomExist;
	private static RoomBuilder roomBuilder;
	private static Camera cam = new Camera();
	
	public static Main.Gamestate gamestate = Main.Gamestate.run;
	
	public MainBuilder() {
		new Window("LotF Builder", this);
	}
	
	public void start() {
		Console.getTimeExample();
		Console.print("Window size: " + new Vec2i(width, height));
		Console.print(Console.WarningType.Info, "Starting!");
		resize();
		
		preInit();
		init();
		postInit();
	}
	
	public void preInit() {
		Console.print(Console.WarningType.Info, "Pre-Initialization started...");
		
		roomBuilder = new RoomBuilder();
		
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
		
		Console.print(Console.WarningType.Info, "Pre-Initialization finished!");
	}
	
	public void init() {
		Console.print(Console.WarningType.Info, "Initialization started...");
		
		Console.print(Console.WarningType.Info, "Initialization finished!");
	}
	
	public void postInit() {
		Console.print(Console.WarningType.Info, "Post-Initialization started...");
		
		renderer = new Renderer();
		renderer.loadTextures();
		
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
		if (gamestate == Main.Gamestate.run) {
			cam.tick();
			
			if (doesRoomExist) {
				roomBuilder.tick();
			}
		}
	}
	
	public static double scale;
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			Console.print(Console.WarningType.Info, "Started render loop!");
			return;
		}
		
		Graphics g2 = bs.getDrawGraphics();
		Graphics2D g = (Graphics2D) g2;
		
		scale = Math.min((double) width / HUD_WIDTH, (double) height / HUD_HEIGHT);
		int w = (int) Math.ceil((HUD_WIDTH * scale));
		w2 = (int) Math.ceil((width - w) / scale);
		int h = (int) Math.ceil((HUD_HEIGHT * scale));
		h2 = (int) Math.ceil((height - h) / scale);
		
		g.scale(scale, scale);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int) (width * 100), (int) (height * 100));
		
		g2.translate(w2 / 2, h2 / 2);
		g2.translate(cam.pos.getX(), cam.pos.getY());
		
		renderer.render(g);
		g2.translate(-cam.pos.getX(), -cam.pos.getY());
		
		g.setColor(Color.BLACK);
		g.fillRect((int) (w / scale), 0, w2, height);
		g.fillRect(-w2, 0, w2, height);
		g.fillRect(0, (int) (h / scale), width, h2);
		g.fillRect(0, -h2, width, h2);
		
		renderer.renderHud(g);
		debugHud.drawText(g, "FPS: " + fps);
		
		g2.dispose();
		bs.show();
	}
	
	private void resize() {
		width = MathHelper.clamp(getWidth(), 0, Integer.MAX_VALUE);
		height = MathHelper.clamp(getHeight(), 0, Integer.MAX_VALUE);
	}
	
	public static void setDoesRoomExist(boolean bool) {
		doesRoomExist = bool;
	}
	
	public static Camera getCamera() {
		return cam;
	}
	
	public static RoomBuilder getRoomBuilder() {
		return roomBuilder;
	}
	
	public static int getHudWidth() {
		return HUD_WIDTH;
	}
	
	public static int getHudHeight() {
		return HUD_HEIGHT;
	}

	public static boolean getDoesRoomExist() {
		return doesRoomExist;
	}
}
