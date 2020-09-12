package game;

import com.tfc.utils.Files;
import game.langs.Python;
import org.python.core.PyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
	
	//Board variables
	public static double boardWidth = 649 / 2f;
	public static double boardHeight = 385 / 2f;
	public static double boardX = 0;
	public static double boardY = 180;
	
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
	public static int soulType = 1;
	
	//Battle memory (because python interpreter is being wacky)
	public static HashMap<String, Object> memory = new HashMap<>();
	
	//Executing directory
	public static final String dir = System.getProperty("user.dir");
	
	//Display
	public static JFrame gameFrame = new JFrame("Javatale");
	public static final Display disp = new Display();
	
	public static void main(String[] args) throws FileNotFoundException {
//		gameFrame.setSize(248 * 2, 200 * 2);
		int width = 656;
		int height = 515;
		Toolkit tk = Toolkit.getDefaultToolkit();
		gameFrame.setSize(width, height);
		gameFrame.setLocation(
				tk.getScreenSize().width/2-(width/2),
				tk.getScreenSize().height/2-(height/2)+1
		);
		gameFrame.setResizable(false);
		gameFrame.add(disp);
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
//				System.out.println(Game.class.getClassLoader().getResource("example/Main.class"));
				Class.forName("battles."+battleName+".Main").getMethod("main", int.class).invoke(null, frame);
//				PyCode code = Python.open(new File(Files.dir + "\\battles\\"+battleName+"\\main.py"));
//				Python.exec(code);
			} catch (Throwable ignored) {
				ignored.printStackTrace();
			}
			
			handleControls(soulType);
			
			if (menuItem < 0) menuItem = 3;
			else if (menuItem > 3) menuItem = 0;
			
			//Min and max didn't work, so I have to use this
			if (playerX < (boardX + 6) - Math.abs((boardWidth / 2))) playerX = (boardX + 6) - Math.abs((boardWidth / 2));
			if (playerX > (boardX - 6) + Math.abs((boardWidth / 2))) playerX = (boardX - 6) + Math.abs((boardWidth / 2));
			if (playerY < (boardY + 6) - Math.abs((boardHeight / 2))) playerY = (boardY + 6) - Math.abs((boardHeight / 2));
			if (playerY > (boardY - 6) + Math.abs((boardHeight / 2))) {
				playerY = (boardY - 6) + Math.abs((boardHeight / 2));
				playerVelocY = 0;
				onFloor = true;
				jumpTime = 0;
			}
		}
		
		disp.repaint();
		try {
			while (time.getTime() - new Date().getTime() > -10);
		} catch (Throwable ignored) {
		}
	}
	
	public static void handleControls(int soulType) {
		switch (soulType) {
			case 0:
				//Default controls
				if (keys.contains('a'))
					playerX -= 1;
				if (keys.contains('d'))
					playerX += 1;
				if (keys.contains('w'))
					playerY -= 1;
				if (keys.contains('s'))
					playerY += 1;
				break;
			case 1:
				//Blue controls
				if (keys.contains('a'))
					playerX -= 1;
				if (keys.contains('d'))
					playerX += 1;
				if (jumpTime <= 150/5 && keys.contains('w')) {
					playerVelocY = -0.25 * 5;
					jumpTime++;
				} else {
					jumpTime = 1000000;
					playerVelocY += 0.05;
				}
				if (keys.contains('s')) {
					playerVelocY = 0.5;
				}
				playerY += playerVelocY;
				break;
		}
	}
	
	public static final ArrayList<Character> keys = new ArrayList<>();
	
	@Override
	public void keyTyped(KeyEvent e) {
		if (!keys.contains(e.getKeyChar()))
			keys.add(e.getKeyChar());
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (!keys.contains(e.getKeyChar()))
			keys.add(e.getKeyChar());
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (keys.contains(e.getKeyChar()))
			keys.remove((Character) e.getKeyChar());
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
}
