package battles.example;

import game.Game;
import game.utils.Projectile;

import java.util.Random;

public class Main {
	private static int num = 100;
	private static int num2 = 0;
	private static double attackNum = 0;
	private static final Random rng = new Random();
	private static boolean inAttack = false;
	private static final int attackCount = 2;
	
	public static void main(int frame) {
		Game.soulType = 0;
		Game.globalOffsetX = 0;
		Game.globalOffsetY = 0;
		if (Game.inAttack) {
			if (!inAttack)
				attackNum = rng.nextDouble();
//			attackNum = 1;
			if (attackNum * attackCount <= 1)
				attack1(frame);
			else if (attackNum * attackCount <= 2)
				attack2(frame);
			else if (attackNum * attackCount <= 3)
				attack3(frame);
			else Game.inAttack = false;
			inAttack = true;
		} else {
			inAttack = false;
		}
	}
	
	public static void attack1(int frame) {
		Game.boardWidth = Math.sin(frame / 300f) * 30 + 300;
		Game.boardHeight = 300;
		Game.boardX = Math.cos((frame + Game.playerX) / 32f);
		Game.boardY = Math.sin((frame + Game.playerY) / 32f);
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
	
	private static void attack2(int frame) {
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
	
	private static void attack3(int frame) {
		Game.soulType = 1;
		Game.boardWidth = 600;
		Game.boardHeight = 600;
		Game.boardX = Game.playerX / 2f;
		Game.boardY = Game.playerY / 2f;
		Game.globalOffsetX = -Game.playerX;
		Game.globalOffsetY = -Game.playerY + 120;
		if (!inAttack) {
			Projectile proj = null;
			Projectile head = new SnakeProjectile(0, 0, (Proj) -> {
			}, (Proj) -> {
			}, null, null);
			for (int i = 0; i < 63; i++) {
				proj = new SnakeProjectile(0, 0, (Proj) -> {
				}, (Proj) -> {
				}, proj, head);
				Game.projectiles.add(proj);
			}
			Game.projectiles.add(head);
		}
		num++;
		if (num >=51000) {
			Game.inAttack = false;
			Game.projectiles.clear();
			num2 = 0;
			num = 0;
		}
	}
}
