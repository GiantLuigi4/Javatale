package game;

import com.tfc.utils.Files;
import game.langs.Python;
import org.python.core.PyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;

public class Display extends JComponent {
	public static Graphics2D graphics2D = null;
	boolean pressed = false;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(0));
		g2d.fillRect(0, 0, Game.gameFrame.getWidth(), Game.gameFrame.getHeight());
		if (Game.inGame) {
			graphics2D = g2d;
			
			g2d.scale(1f / (248 * 2), 1f / (200 * 2));
			g2d.scale(Game.gameFrame.getWidth(), Game.gameFrame.getHeight());
			g2d.fillRect(0, 0, Game.gameFrame.getWidth(), Game.gameFrame.getHeight());
			g2d.translate(248 / 1f, 100 / 2f);
			
			g2d.translate(Game.globalOffsetX, Game.globalOffsetY);
			g2d.rotate(Math.toRadians(Game.globalRotation));
			g2d.scale(Game.globalScaleX, Game.globalScaleY);
			
			AffineTransform defaultTransform = g2d.getTransform();
			
			g2d.translate(Game.boardX, Game.boardY);
			g2d.setColor(new Color(0xFFFFFF));
			g2d.fillRect((int) ((-(Game.boardWidth / 2)) - 2), (int) ((-(Game.boardHeight / 2)) - 2), (int) ((Game.boardWidth) + 4), (int) ((Game.boardHeight) + 4));
			g2d.setColor(new Color(0));
			g2d.fillRect((int) (-(Game.boardWidth / 2) + 1), (int) ((-Game.boardHeight / 2) + 1), (int) Game.boardWidth - 2, (int) Game.boardHeight - 2);
			
			g2d.setTransform(defaultTransform);
			
			try {
				PyCode code = Python.open(new File(Game.dir + "\\battles\\test\\draw.py"));
				Python.exec(code);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			switch (Game.soulType) {
				case 0:
					g2d.setColor(new Color(255, 0, 0));
					break;
				case 1:
					g2d.setColor(new Color(0, 0, 255));
					break;
				default:
					g2d.setColor(new Color(255, 255, 255));
					break;
			}
			
			g2d.translate(Game.playerX, Game.playerY);
			g2d.fillRect(-5, -5, 10, 10);
			
			graphics2D = null;
		} else {
			int y = 5;
			for (File f : new File(Files.dir + "\\battles").listFiles()) {
				if (f.isDirectory()) {
					if (Game.mouseY <= y + 60 && Game.mouseY >= y + 30)
						if (Game.isLeftDown) {
							g2d.setColor(new Color(128, 128, 128));
							pressed = true;
						} else if (!pressed) {
							g2d.setColor(new Color(192, 192, 192));
						} else {
							Game.battleName = f.getName();
							Game.inGame = true;
							pressed = false;
						}
					else g2d.setColor(new Color(255, 255, 255));
					g2d.fillRect(0, y, 100000, 30);
					g2d.setColor(new Color(0));
					g2d.drawString(f.getName(), 10, y + 20);
					g2d.drawString(f.getName(), 11, y + 21);
					y += 35;
				}
			}
		}
	}
}
