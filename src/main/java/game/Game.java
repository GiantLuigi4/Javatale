package game;

import org.python.core.PyCode;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game implements KeyListener {
	//Game variables
	public static int frameNumber = 0;
	
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
	public static JFrame gameFrame = new JFrame("Game");
	public static final Display disp = new Display();
	
	public static void main(String[] args) throws FileNotFoundException {
		gameFrame.setSize(248 * 2, 200 * 2);
		gameFrame.add(disp);
		gameFrame.addKeyListener(new Game());
		gameFrame.setVisible(true);
		while (gameFrame.isVisible()) {
			loop(frameNumber);
			frameNumber++;
		}
		Runtime.getRuntime().exit(0);
	}
	
	public static void loop(int frame) throws FileNotFoundException {
		//Call the battle's main function
		PyCode code = Python.open(new File(dir + "\\battles\\test\\main.py"));
		Python.exec(code);
		
		handleControls(soulType);
		
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
		
		disp.repaint();
		try {
			Thread.sleep(1);
		} catch (Throwable ignored) {
		}
	}
	
	public static void handleControls(int soulType) {
		switch (soulType) {
			case 0:
				//Default controls
				if (keys.contains('a'))
					playerX -= 0.1;
				if (keys.contains('d'))
					playerX += 0.1;
				if (keys.contains('w'))
					playerY -= 0.1;
				if (keys.contains('s'))
					playerY += 0.1;
				break;
			case 1:
				//Blue controls
				if (keys.contains('a'))
					playerX -= 0.2;
				if (keys.contains('d'))
					playerX += 0.2;
				if (jumpTime <= 150 && keys.contains('w')) {
					playerVelocY = -0.25;
					jumpTime++;
				} else {
					jumpTime = 1000000;
					playerVelocY += 0.01;
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
}
