package game;

import com.tfc.utils.BiObject;
import game.utils.Projectile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Game implements KeyListener, MouseMotionListener, MouseListener {
	//Game variables
	public static int frameNumber = 0;
	public static boolean inGame = false;
	public static int mouseX = 0;
	public static int mouseY = 0;
	public static boolean isLeftDown = false;
	public static String battleName = "";
	public static boolean inAttack = false;
	public static boolean inMenu = false;
	public static int menuItem = 0;
	public static int menuElement = 0;
	public static final FontRenderer font;
	public static final int[] healths = new int[20];
	public static boolean inResponse = false;
	
	static {
		try {
			font = new FontRenderer("assets/builtin/uibattlesmall.png");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	//Board variables
	public static double boardWidth = 649 / 2f;
	public static double boardHeight = 385 / 2f;
	public static double boardX = 0;
	public static double boardY = 180;
	
	//Attack variables
	public static ArrayList<Projectile> projectiles = new ArrayList<>();
	
	//Display variables
	public static double globalRotation = 0;
	public static double globalOffsetX = 0;
	public static double globalOffsetY = 0;
	public static double globalScaleX = 1;
	public static double globalScaleY = 1;
	
	//Player variables
	public static double playerX = boardX;
	public static double playerY = boardY;
	public static double playerVelocY = 0;
	public static boolean onFloor = false;
	public static int jumpTime = 0;
	public static int soulType = 0;
	public static int lvl = 20;
	public static int hp = 20;
	public static int invul = 0;
	public static String name = "Frisk";
	
	//Battle memory (because python interpreter is being wacky)
	//Need to make my own python interpreter at some point
	public static HashMap<String, Object> memory = new HashMap<>();
	
	//Executing directory
	public static final String dir = System.getProperty("user.dir");
	
	//Display
	public static JFrame gameFrame = new JFrame("Javatale");
	public static final Display disp = new Display();
	
	public static void main(String[] args) {
//		gameFrame.setSize(248 * 2, 200 * 2);
		int width = 656;
		int height = 515;
		Toolkit tk = Toolkit.getDefaultToolkit();
		gameFrame.setSize(width, height);
		gameFrame.setLocation(
				tk.getScreenSize().width / 2 - (width / 2),
				tk.getScreenSize().height / 2 - (height / 2) + 1
		);
		gameFrame.setResizable(false);
		gameFrame.add(disp);
		String healthsText = "20\n24\n28\n32\n36\n40\n44\n48\n52\n56\n60\n64\n68\n72\n76\n80\n84\n88\n92\n99\n";
		String[] vals = healthsText.split("\n");
		for (int i = 0; i < 20; i++) healths[i] = Integer.parseInt(vals[i].replace("\n", ""));
		hp = healths[lvl - 1];
		try {
			InputStream soulStream = Game.class.getClassLoader().getResourceAsStream("assets/builtin/soul.png");
			InputStream iconStream = Game.class.getClassLoader().getResourceAsStream("assets/builtin/icon.png");
			assert soulStream != null;
			BufferedImage soul = ImageIO.read(soulStream);
			assert iconStream != null;
			BufferedImage icon = ImageIO.read(iconStream);
			Display.colorSoul(soul, new Color(255, 0, 0));
			BufferedImage disp = new BufferedImage(soul.getWidth() * 2, soul.getHeight() * 2, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = (Graphics2D) disp.getGraphics();
			g2d.scale(2, 2);
			g2d.drawImage(soul, 0, 0, null);
			g2d.translate(8, 8);
			g2d.scale(0.5f, 0.5f);
			g2d.drawImage(icon, 0, 0, null);
			gameFrame.setIconImage(disp);
		} catch (Throwable ignored) {
		}
		Game listeners = new Game();
		gameFrame.addKeyListener(listeners);
		gameFrame.addMouseMotionListener(listeners);
		gameFrame.addMouseListener(listeners);
		gameFrame.setVisible(true);
		while (gameFrame.isVisible()) {
			try {
				loop(frameNumber);
			} catch (Throwable ignored) {
			}
			frameNumber++;
		}
		Runtime.getRuntime().exit(0);
	}
	
	public static void loop(int frame) throws FileNotFoundException {
		Date time = new Date();
		if (inGame) {
			//Call the battle's main function
			try {
				if (inAttack && inResponse) {
					playerX = boardX;
					playerY = boardY;
					inResponse = false;
				}
				Class.forName("battles." + battleName + ".Main").getMethod("main", int.class).invoke(null, frame);
			} catch (Throwable ignored) {
				ignored.printStackTrace();
			}
			
			for (Projectile proj : projectiles) proj.update();
			
			if (menuItem < 0) menuItem = 3;
			else if (menuItem > 3) menuItem = 0;
			
			if (inAttack) {
				inResponse = false;
				inMenu = false;
				handleControls(soulType);
				//Min and max didn't work, so I have to use this
				if (playerX < (boardX + 7) - Math.abs((boardWidth / 2)))
					playerX = (boardX + 7) - Math.abs((boardWidth / 2));
				if (playerX > (boardX - 7) + Math.abs((boardWidth / 2)))
					playerX = (boardX - 7) + Math.abs((boardWidth / 2));
				if (playerY < (boardY + 8) - Math.abs((boardHeight / 2)))
					playerY = (boardY + 8) - Math.abs((boardHeight / 2));
				if (playerY > (boardY - 8) + Math.abs((boardHeight / 2))) {
					playerY = (boardY - 8) + Math.abs((boardHeight / 2));
					playerVelocY = 0;
					onFloor = true;
					jumpTime = 0;
				}
				invul--;
				if (invul <= 0) invul = 0;
			} else if (!inMenu) {
				if (keysCodes.contains(KeyEvent.VK_LEFT)) {
					menuItem -= 1;
					keysCodes.remove((Integer) KeyEvent.VK_LEFT);
				} else if (keysCodes.contains(KeyEvent.VK_RIGHT)) {
					menuItem += 1;
					keysCodes.remove((Integer) KeyEvent.VK_RIGHT);
				}
				if (menuItem == 0)
					playerX = -212;
				else if (menuItem == 1)
					playerX = -166 + 70;
				else if (menuItem == 2)
					playerX = -45 + 70;
				else if (menuItem == 3)
					playerX = 72 + 70;
				playerY = 282 + 17;
				boardWidth = 442;
				boardX = 0;
				int height = 102;
				boardY = (246) - (height / 2f);
				boardHeight = height;
				int width = 429;
				boardX = (442 / 2f - 13) - (width / 2f);
				boardWidth = width;
				if (keysCodes.contains(KeyEvent.VK_ENTER)) {
//					inAttack = true;
					inMenu = true;
					playerX = boardX;
					playerY = boardY;
					keysCodes.remove((Integer) KeyEvent.VK_ENTER);
				}
				invul = 0;
//				System.out.println((99-20f)/20);
			} else if (!inResponse) {
				try {
					String menuType = "";
					switch (menuItem) {
						case 0:
							menuType = "fight";
							break;
						case 1:
							menuType = "act";
							break;
						case 2:
							menuType = "item";
							break;
						case 3:
							menuType = "mercy";
							break;
					}
					ArrayList<String> elements = new ArrayList<>();
					Class.forName("battles." + battleName + ".UI").getMethod("fillMenu", String.class, ArrayList.class).invoke(null, menuType, elements);
					switch (menuElement) {
						case 0:
							playerX = -194;
							playerY = 169;
							break;
//						case 1:
//							playerX = -194;
//							playerY = 169;
//							break;
					}
					if (keysCodes.contains(KeyEvent.VK_ENTER)) {
						Class.forName("battles." + battleName + ".UI").getMethod("handleInput", String.class, String.class).invoke(null, menuType, elements.get(menuElement));
						playerX = boardX;
						playerY = boardY;
						if (Game.inGame) {
							inResponse = true;
							inAttack = false;
							inMenu = false;
							playerX = 10000;
							playerY = 10000;
						}
						menuElement = 0;
					} else if (elements.isEmpty() || keysCodes.contains(KeyEvent.VK_Z)) {
						menuElement = 0;
						inMenu = false;
						inResponse = false;
						inAttack = false;
					}
				} catch (Throwable ignored) {
					inAttack = true;
					ignored.printStackTrace();
				}
			} else {
				playerX = 10000;
				playerY = 10000;
			}
		}
		
		if (keysCodes.contains(KeyEvent.VK_ESCAPE)) {
			battleName = "";
			inGame = false;
			menuItem = 0;
			inMenu = false;
			inAttack = false;
			hp = healths[lvl - 1];
		}
		
		disp.repaint();
		try {
			while (time.getTime() - new Date().getTime() > -10) ;
		} catch (Throwable ignored) {
		}
	}
	
	public static void handleControls(int soulType) {
		float speed = 0.75f;
		switch (soulType) {
			case 0:
				//Default controls
				if (keysCodes.contains(KeyEvent.VK_LEFT))
					playerX -= speed;
				if (keysCodes.contains(KeyEvent.VK_RIGHT))
					playerX += speed;
				if (keysCodes.contains(KeyEvent.VK_UP))
					playerY -= speed;
				if (keysCodes.contains(KeyEvent.VK_DOWN))
					playerY += speed;
				break;
			case 1:
				//Blue controls
				if (keysCodes.contains(KeyEvent.VK_LEFT))
					playerX -= 1;
				if (keysCodes.contains(KeyEvent.VK_RIGHT))
					playerX += 1;
				if (jumpTime <= 150 / 5 && keysCodes.contains(KeyEvent.VK_UP)) {
					playerVelocY = -0.25 * 5;
					jumpTime++;
				} else {
					jumpTime = 1000000;
					playerVelocY += 0.05;
				}
				if (keysCodes.contains(KeyEvent.VK_DOWN))
					playerVelocY = Math.max(0.5, playerVelocY);
				playerY += playerVelocY;
				break;
		}
	}
	
	//Inputs
	public static final ArrayList<Character> keys = new ArrayList<>();
	public static final ArrayList<Integer> keysCodes = new ArrayList<>();
	
	@Override
	public void keyTyped(KeyEvent e) {
		if (!keys.contains(e.getKeyChar()))
			keys.add(e.getKeyChar());
		if (!keysCodes.contains(e.getKeyCode()))
			keysCodes.add(e.getKeyCode());
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!keys.contains(e.getKeyChar()))
			keys.add(e.getKeyChar());
		if (!keysCodes.contains(e.getKeyCode()))
			keysCodes.add(e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (keys.contains(e.getKeyChar()))
			keys.remove((Character) e.getKeyChar());
		if (keysCodes.contains(e.getKeyCode()))
			keysCodes.remove((Integer) e.getKeyCode());
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
//		if (e.getButton() == 1) isLeftDown = true;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) isLeftDown = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 1) isLeftDown = false;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	public static float lerp(float pct, float start, float end) {
		return ((start * (1 - pct)) + (end * (pct)));
	}
	
	private static final HashMap<String, ArrayList<BiObject<String, Object>>> resetables = new HashMap();
	
	public static void markResetable(String className, String fieldName, Object value) {
		if (!resetables.containsKey(className))
			resetables.put(className, new ArrayList<>());
		boolean hasField = false;
		for (BiObject<String, Object> obj : resetables.get(className)) {
			if (obj.getObject1().equals(fieldName)) hasField = true;
		}
		if (!hasField) resetables.get(className).add(new BiObject<>(fieldName, value));
	}
	
	public static void reset() {
		resetables.forEach((clazz, list) -> {
			try {
				Class<?> c = Class.forName(clazz);
				list.forEach(obj -> {
					try {
						c.getField(obj.getObject1()).set(null, obj.getObject2());
					} catch (IllegalAccessException | NoSuchFieldException e) {
						e.printStackTrace();
					}
				});
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
	}
}
