package battles.example;

import game.Game;
import game.utils.Projectile;

public class Main {
	private static int num = 100;
	private static int num2 = 0;
	
	public static void main(int frame) {
		if (Game.inAttack) {
			Game.boardWidth = Math.sin(frame / 300f) * 30 + 100;
			Game.boardHeight = 60;
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
		}
	}
}
