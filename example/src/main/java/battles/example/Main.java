package battles.example;

import assorted_projectiles.FireflyProjectile;
import game.Game;
import game.utils.Projectile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

//import assorted_projectiles.SnakeProjectile;

public class Main {
	public static int num = 100;
	public static int num2 = 0;
	public static double attackNum = 0;
	public static final Random rng = new Random();
	public static boolean inAttack = false;
	public static final int attackCount = 2;
	public static float messageProgress = 0;
	public static int attacksDone = 0;
	public static int msgNum = 0;
	public static final String[] messages = new String[]{
			"Hello, welcome to the example battle.",
			"This battle has a total of " + attackCount + "\nimplemented attacks.",
			"It also has a total of " + 4 + " messages.",
			"This is the last message, it will\nrandomly cycle through the messages \nfrom here."
	};
	
	public static void main(int frame) {
		Game.markResetable(Main.class.getName());
		Game.soulType = 0;
		Game.globalOffsetX = 0;
		Game.globalOffsetY = 0;
		if (Game.inAttack) {
			messageProgress = 0;
			if (!inAttack)
				attackNum = rng.nextDouble();
//			attackNum = 0;
			if (attackNum * attackCount <= 1)
				attack1(frame);
			else if (attackNum * attackCount <= 2)
				attack2(frame);
//			else if (attackNum * attackCount <= 3)
//				attack3(frame);
			else Game.inAttack = false;
			inAttack = true;
		} else {
			if (inAttack) {
				attacksDone++;
				msgNum = attacksDone;
				if (msgNum > messages.length)
					msgNum = rng.nextInt(messages.length);
			}
			inAttack = false;
		}
	}
	
	public static void draw(Graphics2D g2d) {
		if (!Game.inAttack && !Game.inMenu) {
			messageProgress+=0.1f;
			if (messageProgress > messages[msgNum].length()+3) {
				messageProgress = messages[msgNum].length()+3;
			}
			if (Game.keysCodes.contains(KeyEvent.VK_Z)) {
				messageProgress = messages[msgNum].length()+3;
			}
			int i = 0;
			int x = -209;
			int y = 156;
			for (char c : ("*  " + messages[msgNum]).toLowerCase().toCharArray()) {
				if (i <= messageProgress) {
					x += Game.font.draw(c, x, y, g2d) + 2;
				}
				if (c == '\n') {
					x = -186;
					y += 16;
				}
				if (i >= 3 && c == ' ') {
					x+=3;
				}
				i++;
			}
		} else if (Game.inResponse) {
			if (UI.displayText.equals("")) {
				Game.inAttack = true;
			} else {
				if (Game.keysCodes.contains(KeyEvent.VK_ENTER)) {
					Game.inAttack = true;
				}
				if (Game.keysCodes.contains(KeyEvent.VK_Z)) {
					messageProgress = UI.displayText.length()+3;
				}
				messageProgress+=0.1f;
				if (messageProgress > UI.displayText.length()+3) {
					messageProgress = UI.displayText.length()+3;
				}
				int i = 0;
				int x = -209;
				int y = 156;
				for (char c : ("*  " + UI.displayText).toLowerCase().toCharArray()) {
					if (i <= messageProgress) {
						x += Game.font.draw(c, x, y, g2d) + 2;
					}
					if (c == '\n') {
						x = -186;
						y += 16;
					}
					if (i >= 3 && c == ' ') {
						x+=3;
					}
					i++;
				}
			}
		}
	}
	
	public static void attack1(int frame) {
		Game.boardWidth = Math.sin(frame / 300f) * 30 + 300;
		Game.boardHeight = 300;
		Game.boardX = Math.cos((frame + Game.playerX) / 32f) * 32;
		Game.boardY = Math.sin((frame + Game.playerY) / 32f) * 32;
		if (num >= 120) {
			int count = 8;
			for (int i = 0; i < count; i++) {
				int degrees = i * (360 / count) + num2;
				Projectile proj = new Projectile(
						(Proj) -> {
							Proj.x = Math.cos(Math.toRadians((Proj.framesExisted * 1f) + degrees)) * (Proj.framesExisted / 2f);
							Proj.y = Math.sin(Math.toRadians((Proj.framesExisted * 1f) + degrees)) * (Proj.framesExisted / 2f);
							if (Proj.framesExisted >= 600) {
								Game.projectiles.remove(Proj);
							}
						}, (Proj) -> {
				}
				);
				Game.projectiles.add(proj);
			}
			num = 0;
			num2 += 5;
		}
		num++;
		if (num2 == 50) {
			Game.inAttack = false;
			Game.projectiles.clear();
			num2 = 0;
			num = 0;
		}
	}
	
	public static void attack2(int frame) {
		if (num % 50 == 0) {
			int x = rng.nextInt(800) - 400;
			int y = rng.nextInt(800) - 400;
			if (x >= -250 && x <= 239) {
				if (y <= 260 && y >= -53) {
					if (y <= (260 + -53) / 2) {
						y = -53;
					} else {
						y = 260;
					}
				}
			}
			x = Math.max(-250, Math.min(239, x));
			y = Math.max(-53, Math.min(260, y));
			if (num <= 100 * 20) {
				FireflyProjectile proj = new FireflyProjectile(
						x, y,
						(Proj) -> {
						},
						(Proj) -> {
						}
				);
				proj.maxDelay = 100;
				Game.projectiles.add(proj);
			}
		}
		Game.boardWidth = 30;
		Game.boardHeight = 30;
		Game.boardX = Math.max(-230, Math.min(Game.playerX, 219));
		Game.boardY = Math.max(-33, Math.min(Game.playerY, 240));
		num++;
		if (num >= 2000) {
			Game.inAttack = false;
			Game.projectiles.clear();
			num2 = 0;
			num = 0;
		}
	}
	
//	public static void attack3(int frame) {
//		Game.soulType = 1;
//		Game.boardWidth = 600;
//		Game.boardHeight = 600;
//		Game.boardX = Game.playerX / 2f;
//		Game.boardY = Game.playerY / 2f;
//		Game.globalOffsetX = -Game.playerX;
//		Game.globalOffsetY = -Game.playerY + 120;
//		if (!inAttack) {
//			Projectile proj = null;
//			Projectile head = new SnakeProjectile(0, 250, (Proj) -> {
//			}, (Proj) -> {
//			}, null, null);
//			for (int i = 0; i < 63; i++) {
//				proj = new SnakeProjectile(0, 250 - i * 16, (Proj) -> {
//				}, (Proj) -> {
//				}, proj, head);
//				Game.projectiles.add(proj);
//			}
//			Game.projectiles.add(head);
//		}
//		num++;
//		if (num >= 7500) {
//			Game.inAttack = false;
//			Game.projectiles.clear();
//			num2 = 0;
//			num = 0;
//		}
//	}
	
	//These setters are fallbacks for the reset method
	public static void setNum(int num) {
		Main.num = num;
	}
	
	public static void setNum2(int num2) {
		Main.num2 = num2;
	}
	
	public static void setAttackNum(double attackNum) {
		Main.attackNum = attackNum;
	}
	
	public static void setInAttack(boolean inAttack) {
		Main.inAttack = inAttack;
	}
	
	public static void setMessageProgress(float messageProgress) {
		Main.messageProgress = messageProgress;
	}
	
	public static void setAttacksDone(int attacksDone) {
		Main.attacksDone = attacksDone;
	}
	
	public static void setMsgNum(int msgNum) {
		Main.msgNum = msgNum;
	}
}
