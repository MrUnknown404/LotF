package main.java.lotfbuilder;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import main.java.lotf.init.Tiles;
import main.java.lotf.tile.Tile;
import main.java.lotf.util.Console;
import main.java.lotf.util.Console.WarningType;
import main.java.lotf.util.RuntimeTypeAdapterFactory;
import main.java.lotf.util.math.MathH;
import main.java.lotf.util.math.Vec2i;
import main.java.lotfbuilder.client.Camera;
import main.java.lotfbuilder.client.KeyHandler;
import main.java.lotfbuilder.client.MouseHandler;
import main.java.lotfbuilder.client.Renderer;
import main.java.lotfbuilder.client.Window;
import main.java.lotfbuilder.client.gui.HudBuilder;
import main.java.lotfbuilder.client.ui.UIHandler;
import main.java.lotfbuilder.world.RoomBuilder;

/**
 * @author -Unknown-
 */
public class MainBuilder {
	public static MainBuilder main;
	private static BuilderLoop builderLoop;
	
	public static final int HUD_WIDTH = 256, HUD_HEIGHT = 144;
	private int width = HUD_WIDTH, height = HUD_HEIGHT, w2, h2;
	private static int fps;
	
	public static final String TEXTURE_FOLDER_LOCATION = "/main/resources/lotfbuilder/assets/textures/";
	
	public RoomBuilder builder;
	public Camera camera;
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	private Renderer renderer;
	private HudBuilder hud;
	
	private Gson gson;
	
	public synchronized void start() {
		new Window("LotF Builder!", builderLoop = new BuilderLoop());
		Console.getTimeExample();
		Console.print(WarningType.Info, "Window size: " + new Vec2i(width, height));
		Console.print(WarningType.Info, "Starting!");
		
		preInit();
		init();
		postInit();
		
		builderLoop.start();
	}
	
	private void preInit() {
		Console.print(WarningType.Info, "Pre-Initialization started...");
		
		keyHandler = new KeyHandler();
		mouseHandler = new MouseHandler();
		
		Tiles.registerAll();
		
		renderer = new Renderer();
		renderer.setupTextures();
		
		builder = new RoomBuilder();
		hud = new HudBuilder();
		hud.setupTextures();
		
		UIHandler.registerUI();
		
		builderLoop.setupComponents();
		
		Console.print(WarningType.Info, "Pre-Initialization finished!");
	}
	
	private void init() {
		Console.print(WarningType.Info, "Initialization started...");
		
		camera = new Camera();
		
		RuntimeTypeAdapterFactory<Tile> factory = RuntimeTypeAdapterFactory.of(Tile.class, "class")
				.registerSubtype(Tile.class, Tile.class.getCanonicalName());
		GsonBuilder gson = new GsonBuilder().serializeNulls().registerTypeAdapterFactory(factory);
		this.gson = gson.create();
		
		Console.print(WarningType.Info, "Initialization finished!");
	}
	
	private void postInit() {
		Console.print(WarningType.Info, "Post-Initialization started...");
		
		Console.print(WarningType.Info, "Post-Initialization finished!");
	}
	
	private void tick() {
		if (!MainBuilder.main.builder.isInvOpen()) {
			camera.tick();
		}
		
		keyHandler.tick();
	}
	
	public double scale;
	
	private void render(Graphics2D g) {
		scale = Math.min((double) width / HUD_WIDTH, (double) height / HUD_HEIGHT);
		int w = (int) Math.ceil((HUD_WIDTH * scale));
		w2 = (int) Math.ceil((width - w) / scale);
		int h = (int) Math.ceil((HUD_HEIGHT * scale));
		h2 = (int) Math.ceil((height - h) / scale);
		
		g.scale(scale, scale);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		g.translate(w2 / 2, h2 / 2);
		if (!builder.isInvOpen()) {
			g.translate(-camera.getPosX(), -camera.getPosY());
			renderer.render(g);
			g.translate(camera.getPosX(), camera.getPosY());
		}
		
		g.setColor(Color.RED);
		g.drawString("FPS: " + fps, 0, 143);
		hud.draw(g);
		
		g.setColor(Color.BLACK);
		g.fillRect((int) (w / scale), 0, w2, height);
		g.fillRect(-w2, 0, w2, height);
		g.fillRect(0, (int) (h / scale), width, h2);
		g.fillRect(0, -h2, width, h2);
		g.dispose();
	}
	
	public void requestFocus() {
		builderLoop.requestFocus();
	}
	
	public int getWindowWidth() {
		return width;
	}
	
	public int getWindowHeight() {
		return height;
	}
	
	public int getExtraWidth() {
		return w2;
	}
	
	public int getExtraHeight() {
		return h2;
	}
	
	public Gson getGson() {
		return gson;
	}
	
	public class BuilderLoop extends Canvas implements Runnable {
		private static final long serialVersionUID = 3019429277912683042L;
		
		private boolean running = false;
		private Thread thread;
		
		public void start() {
			thread = new Thread(this);
			thread.start();
			running = true;
			
			MainBuilder.main.requestFocus();
			
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
			
			MainBuilder.this.render((Graphics2D) bs.getDrawGraphics());
			
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
			addMouseListener(mouseHandler);
			addMouseMotionListener(mouseHandler);
			addMouseWheelListener(mouseHandler);
			addComponentListener(new ComponentListener() {
				@Override
				public void componentShown(ComponentEvent e) {
				}
				
				@Override
				public void componentMoved(ComponentEvent e) {
				}
				
				@Override
				public void componentHidden(ComponentEvent e) {
				}
				
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
