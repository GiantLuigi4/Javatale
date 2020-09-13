package assorted_projectiles;

import game.Game;
import game.utils.Projectile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.function.Consumer;

public class SnakeProjectile extends Projectile {
	public final Projectile parent;
	public Projectile child;
	public final Projectile head;
	public int shake = 0;
	public boolean containsPlayer = false;
	public boolean collidesOther = false;
	public int collisionCount = 0;
	public boolean isSolid = true;
	public boolean bodyRenderResource = false;
	
	public SnakeProjectile(int x, int y, Consumer<Projectile> motion, Consumer<Projectile> render, Projectile parent, Projectile head) {
		super(x, y, motion, render);
		this.parent = parent;
		if (parent == null) this.head = this;
		else this.head = head;
	}
	
	public SnakeProjectile(int x, int y, int width, int height, Consumer<Projectile> motion, Consumer<Projectile> render, Projectile parent, Projectile head) {
		super(x, y, width, height, motion, render);
		this.parent = parent;
		if (parent == null) this.head = this;
		else this.head = head;
	}
	
	public SnakeProjectile(int x, int y, String resource, Consumer<Projectile> motion, Consumer<Projectile> render, Projectile parent, Projectile head) {
		super(x, y, resource, motion, render);
		this.parent = parent;
		if (parent == null) this.head = this;
		else this.head = head;
	}
	
	public SnakeProjectile(int x, int y, int width, int height, int damage, int invulTime, String resource, Consumer<Projectile> motion, Consumer<Projectile> render, Projectile parent, Projectile head) {
		super(x, y, width, height, damage, invulTime, resource, motion, render);
		this.parent = parent;
		if (parent == null) this.head = this;
		else this.head = head;
	}
	
	public SnakeProjectile(int x, int y, int width, int height, int damage, int invulTime, Consumer<Projectile> motion, Consumer<Projectile> render, Projectile parent, Projectile head) {
		super(x, y, width, height, damage, invulTime, motion, render);
		this.parent = parent;
		if (parent == null) this.head = this;
		else this.head = head;
	}
	
	public SnakeProjectile(Consumer<Projectile> motion, Consumer<Projectile> render, Projectile parent, Projectile head) {
		super(motion, render);
		this.parent = parent;
		if (parent == null) this.head = this;
		else this.head = head;
	}
	
	@Override
	public void update() {
		double dir = 0;
		if (this.parent == null) {
			double xOff = Game.playerX - this.x;
			double yOff = Game.playerY - this.y;
//			if ((xOff + yOff/2f) < 16) {
//				dir = Math.atan2(Game.playerX - this.x, Game.playerY - this.y);
//			} else {
				dir = Math.atan2((Game.playerX + (Math.sin(shake * 0.005f) * 128)) - this.x, (Game.playerY + (Math.cos(shake * 0.005f) * 128)) - this.y);
//			}
			double oldX = this.x;
			double oldY = this.y;
//			this.x = Game.playerX + (Math.cos(shake/128f)*64);
//			this.y = Game.playerY + (Math.sin(shake/128f)*64);
//			this.x += Math.sin(dir) * 0.5f + (Math.sin(dir) * (Math.sin(shake * 0.01f) * 1f));
//			this.y += Math.cos(dir) * 0.5f + (Math.cos(dir) * (Math.cos(shake * 0.01f) * 1f));
			this.x += Math.sin(dir) * 0.5f;
			this.y += Math.cos(dir) * 0.5f;
			if (containsPlayer) {
				dir = Math.atan2(this.x - oldX, this.y - oldY);
			}
			shake += 1;
		} else {
			dir = Math.atan2(parent.x - this.x, parent.y - this.y);
			this.x = parent.x - Math.sin(dir) * 16;
			this.y = parent.y - Math.cos(dir) * 16;
		}
		framesExisted++;
		if (
				(x - (width / 2f)) <= Game.playerX + 6 &&
						(x + (width / 2f)) >= Game.playerX - 6
		) {
			if (
					(y - (height / 2f)) <= Game.playerY + 6 &&
							(y + (height / 2f)) >= Game.playerY - 6
			) {
				if (Game.invul <= 0) {
					if (head instanceof SnakeProjectile) {
						if (this.parent == null || ((SnakeProjectile) head).collisionCount >= 3) {
							Game.hp -= damage;
							Game.invul = invulTime;
						}
					} else {
						Game.hp -= damage;
						Game.invul = invulTime;
					}
				}
				if (containsPlayer) {
					double sin = Math.sin(Math.toRadians(Math.toDegrees(dir)));
					double cos = Math.cos(Math.toRadians(Math.toDegrees(dir)));
					if (collidesOther) {
						Game.playerX = this.x + sin * 5;
						Game.playerY = this.y + cos * 5;
					} else {
						Game.playerX = this.x + sin * 15;
						Game.playerY = this.y + cos * 15;
					}
					Game.jumpTime = 0;
					Game.playerVelocY = 0;
//					if (this.parent == null) {
//						this.y += 1;
//					}
				} else {
					if (head instanceof SnakeProjectile && ((SnakeProjectile) head).containsPlayer) {
					} else if (isSolid) {
						if (Game.playerY < this.y) {
							if (Game.soulType == 1)
								Game.playerY = Game.lerp(0.5f, (float) Game.playerY, (float) (this.y - this.height / 1.1D));
							Game.playerY = Game.lerp(0.075f, (float) Game.playerY, (float) (this.y - this.height / 2D));
							Game.jumpTime = 0;
							Game.playerVelocY = 0;
						} else if (Game.playerY > this.y) {
							if (Game.soulType == 1)
								Game.playerY = Game.lerp(0.5f, (float) Game.playerY, (float) (this.y + this.height / 1.1D));
							Game.playerY = Game.lerp(0.075f, (float) Game.playerY, (float) (this.y + this.height / 2D));
						} else {
							Game.playerY = this.y;
							containsPlayer = true;
						}
						if (Game.playerX < this.x)
							Game.playerX = Game.lerp(0.075f, (float) Game.playerX, (float) (this.x - this.width / 1.0D));
						else if (Game.playerX > this.x)
							Game.playerX = Game.lerp(0.075f, (float) Game.playerX, (float) (this.x + this.width / 1.0D));
						else {
							Game.playerX = this.x;
							containsPlayer = true;
						}
					}
				}
				if (head instanceof SnakeProjectile) {
					((SnakeProjectile) head).collidesOther = true;
					((SnakeProjectile) head).collisionCount += 1;
				}
				if (parent instanceof SnakeProjectile)
					((SnakeProjectile) parent).child = this;
				if (this.parent == null) {
					containsPlayer = true;
				}
			} else
				containsPlayer = false;
		} else
			containsPlayer = false;
		collidesOther = false;
		collisionCount = 0;
	}
	
	@Override
	public void render(Graphics2D g2d) throws IOException {
		if (this.child == null || this.parent == null) super.render(g2d);
		g2d.setColor(new Color(255,255,255));
		if (this.parent != null) {
			try {
				AffineTransform source = g2d.getTransform();
				g2d.translate((this.x + this.parent.x)/2f,(this.y + this.parent.y)/2f);
				g2d.scale(1,1);
				int scale = 16;
				//https://stackoverflow.com/questions/2839508/java2d-increase-the-line-width0
				g2d.setStroke(new BasicStroke(16*scale));
				g2d.scale(1f/scale,1f/scale);
				if (this.parent == this.head) g2d.drawLine(0,0,(int)((parent.x-this.x)*(scale/8f)),(int)((parent.y-this.y)*(scale/8f)));
				else g2d.drawLine(0,0,(int)((parent.x-this.x)*(scale))/4,(int)((parent.y-this.y)*(scale))/4);
				g2d.setTransform(source);
			} catch (Throwable ignored) {}
			if (bodyRenderResource) super.render(g2d);
		}
	}
}
