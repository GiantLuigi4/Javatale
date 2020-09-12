package game;

import com.tfc.utils.Files;
import game.langs.Python;
import game.utils.Projectile;
import org.python.core.PyCode;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static game.Game.hp;

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
			
			float xC = 0;
			for (char c : "chara".toCharArray())
				xC += Game.font.draw(c, -225 + (int) xC, 253, g2d) + 2f;
			
			for (char c : ("lv " + Game.lvl).toCharArray())
				xC += Game.font.draw(c, -205 + (int) xC, 253, g2d) + 2f;
			
			AffineTransform defaultTransform = g2d.getTransform();
			
			g2d.translate(Game.boardX, Game.boardY);
			g2d.setColor(new Color(0xFFFFFF));
			g2d.fillRect((int) ((-(Game.boardWidth / 2)) - 3), (int) ((-(Game.boardHeight / 2)) - 3), (int) ((Game.boardWidth) + 6), (int) ((Game.boardHeight) + 6));
			g2d.setColor(new Color(0));
			g2d.fillRect((int) (-(Game.boardWidth / 2) + 1), (int) ((-Game.boardHeight / 2) + 1), (int) Game.boardWidth - 2, (int) Game.boardHeight - 2);
			
			g2d.setTransform(defaultTransform);
			
			try {
//				PyCode code = Python.open(new File(Game.dir + "\\battles\\test\\draw.py"));
//				Python.exec(code);
			} catch (Throwable e) {
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
			try {
				InputStream soul = Display.class.getClassLoader().getResourceAsStream("assets/builtin/soul.png");
				assert soul != null;
				BufferedImage image = ImageIO.read(soul);
				Color c = g2d.getColor();
				Color recolored = new Color(c.getRed(), c.getBlue(), c.getGreen(), Game.invul >= 1 ? 128 : 255);
				colorSoul(image, recolored);
				int size = 12;
				g2d.drawImage(
						image,
						-size / 2, -size / 2,
						size, size + 1,
						null
				);
				soul.close();
			} catch (Throwable err) {
				g2d.fillRect(-5, -5, 10, 10);
			}
			
			g2d.setTransform(defaultTransform);
			
			int x = -224;
			try {
				InputStream fight = null;
				if (!Game.inAttack && Game.menuItem == 0)
					fight = Display.class.getClassLoader().getResourceAsStream("assets/builtin/fightbt_select.png");
				else fight = Display.class.getClassLoader().getResourceAsStream("assets/builtin/fightbt_norm.png");
				assert fight != null;
				BufferedImage image = ImageIO.read(fight);
				g2d.drawImage(
						image,
						x, 282,
						83, 33,
						null
				);
				fight.close();
				x += 116;
			} catch (Throwable err) {
				g2d.fillRect(-5, -5, 10, 10);
			}
			
			try {
				InputStream act = null;
				if (!Game.inAttack && Game.menuItem == 1)
					act = Display.class.getClassLoader().getResourceAsStream("assets/builtin/actbt_select.png");
				else act = Display.class.getClassLoader().getResourceAsStream("assets/builtin/actbt_norm.png");
				assert act != null;
				BufferedImage image = ImageIO.read(act);
				g2d.drawImage(
						image,
						x, 282,
						83, 33,
						null
				);
				act.close();
				x += 121;
			} catch (Throwable err) {
				g2d.fillRect(-5, -5, 10, 10);
			}
			
			try {
				InputStream item = null;
				if (!Game.inAttack && Game.menuItem == 2)
					item = Display.class.getClassLoader().getResourceAsStream("assets/builtin/itembt_select.png");
				else item = Display.class.getClassLoader().getResourceAsStream("assets/builtin/itembt_norm.png");
				assert item != null;
				BufferedImage image = ImageIO.read(item);
				g2d.drawImage(
						image,
						x, 282,
						83, 33,
						null
				);
				item.close();
				x += 117;
			} catch (Throwable err) {
				g2d.fillRect(-5, -5, 10, 10);
			}
			
			try {
				InputStream mercy = null;
				if (!Game.inAttack && Game.menuItem == 3)
					mercy = Display.class.getClassLoader().getResourceAsStream("assets/builtin/mercybt_select.png");
				else mercy = Display.class.getClassLoader().getResourceAsStream("assets/builtin/mercybt_norm.png");
				assert mercy != null;
				BufferedImage image = ImageIO.read(mercy);
				g2d.drawImage(
						image,
						x, 282,
						83, 33,
						null
				);
				mercy.close();
				x += 116;
			} catch (Throwable err) {
				g2d.fillRect(-5, -5, 10, 10);
			}
			
			int maxHealth = Game.healths[Game.lvl - 1];
			for (int i = 0; i < maxHealth; i++) {
				float width = 0.92f;
				float point = i * width;
				if (i < hp) g2d.setColor(new Color(255, 255, 0));
				else g2d.setColor(new Color(255, 0, 0));
				g2d.fillRect(-40 + (int) point, 258, (int) Math.round(width), 16);
				x = -40 + (int) point;
			}
			
			try {
				InputStream hp = Display.class.getClassLoader().getResourceAsStream("assets/builtin/HP.png");
				assert hp != null;
				BufferedImage image = ImageIO.read(hp);
				float scale = 0.8f;
				AffineTransform old = g2d.getTransform();
				g2d.translate(-63.5f, 261.5f);
				g2d.scale(0.95f, 1f);
				g2d.drawImage(
						image,
						0, 0,
						(int) (23 * scale), (int) (10 * scale),
						null
				);
				g2d.setTransform(old);
				hp.close();
			} catch (Throwable err) {
				g2d.fillRect(-5, -5, 10, 10);
			}
			
			String text = Game.hp + " / " + maxHealth;
			AffineTransform old = g2d.getTransform();
			float x1 = x;
			for (char c : text.toCharArray()) {
				g2d.translate(x1 + 12, 253);
				float scale = 0.975f;
				g2d.scale(scale, 1);
				g2d.translate(-1, 0);
				g2d.scale(1f / scale, 1);
				x1 += Game.font.draw(c, 0, 0, g2d) + 2.5f;
				g2d.setTransform(old);
			}
			
			for (int i = 0; i < Game.projectiles.size(); i++) {
				Projectile proj = Game.projectiles.get(i);
				try {
					proj.render(g2d);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
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
	
	public static BufferedImage colorSoul(BufferedImage image, Color color) {
		int rgb = 0;
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (x == 0 && y == 0) {
					rgb = image.getRGB(x, y);
				}
				if (image.getRGB(x, y) != rgb) {
					image.setRGB(x, y, color.getRGB());
				}
			}
		}
		return image;
	}
}
