package assorted_projectiles;

import game.Game;
import game.utils.Projectile;

import java.util.function.Consumer;

public class FireflyProjectile extends Projectile {
	public FireflyProjectile(int x, int y, Consumer<Projectile> motion, Consumer<Projectile> render) {
		super(x, y, motion, render);
	}
	
	public FireflyProjectile(int x, int y, int width, int height, Consumer<Projectile> motion, Consumer<Projectile> render) {
		super(x, y, width, height, motion, render);
	}
	
	public FireflyProjectile(int x, int y, String resource, Consumer<Projectile> motion, Consumer<Projectile> render) {
		super(x, y, resource, motion, render);
	}
	
	public FireflyProjectile(int x, int y, int width, int height, int damage, int invulTime, String resource, Consumer<Projectile> motion, Consumer<Projectile> render) {
		super(x, y, width, height, damage, invulTime, resource, motion, render);
	}
	
	public FireflyProjectile(int x, int y, int width, int height, int damage, int invulTime, Consumer<Projectile> motion, Consumer<Projectile> render) {
		super(x, y, width, height, damage, invulTime, motion, render);
	}
	
	public FireflyProjectile(Consumer<Projectile> motion, Consumer<Projectile> render) {
		super(motion, render);
	}
	
	public double xTarg = x;
	public double xStart = x;
	public double yTarg = y;
	public double yStart = y;
	public float progress = 1;
	public int delayer = 0;
	public int maxDelay = 100;
	
	@Override
	public void update() {
		if (progress >= 0.99f) {
			x = xTarg;
			y = yTarg;
			xStart = x;
			yStart = y;
			xTarg = Game.playerX;
			yTarg = Game.playerY;
			progress = 0;
			delayer = 0;
		}
		if (delayer <= maxDelay) {
			delayer++;
		} else {
			this.x = Game.lerp(progress, (float) xStart, (float) xTarg);
			this.y = Game.lerp(progress, (float) yStart, (float) yTarg);
			progress += (1 - progress) / 64f;
		}
		
		super.update();
	}
}
