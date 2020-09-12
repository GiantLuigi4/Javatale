package game.utils;

import com.tfc.utils.BiObject;
import game.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class Projectile {
	public int framesExisted = 0;
	public double x = 0;
	public double y = 0;
	public double width = 16;
	public double height = 16;
	public int damage = 1;
	public int invulTime = 100;
	public String resource = "assets/builtin/default_proj.png";
	public final Consumer<Projectile> motion;
	public final Consumer<Projectile> render;
	
	public Projectile(int x, int y, Consumer<Projectile> motion, Consumer<Projectile> render) {
		this.x = x;
		this.y = y;
		this.motion = motion;
		this.render = render;
	}
	
	public Projectile(int x, int y, int width, int height, Consumer<Projectile> motion, Consumer<Projectile> render) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.motion = motion;
		this.render = render;
	}
	
	public Projectile(int x, int y, String resource, Consumer<Projectile> motion, Consumer<Projectile> render) {
		this.x = x;
		this.y = y;
		this.resource = resource;
		this.motion = motion;
		this.render = render;
	}
	
	public Projectile(int x, int y, int width, int height, int damage, int invulTime, String resource, Consumer<Projectile> motion, Consumer<Projectile> render) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.damage = damage;
		this.invulTime = invulTime;
		this.resource = resource;
		this.motion = motion;
		this.render = render;
	}
	
	public Projectile(int x, int y, int width, int height, int damage, int invulTime, Consumer<Projectile> motion, Consumer<Projectile> render) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.damage = damage;
		this.invulTime = invulTime;
		this.motion = motion;
		this.render = render;
	}
	
	public Projectile(Consumer<Projectile> motion, Consumer<Projectile> render) {
		this.motion = motion;
		this.render = render;
	}
	
	public void update() {
		motion.accept(this);
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
					Game.hp -= damage;
					Game.invul = invulTime;
				}
			}
		}
	}
	
	public void render(Graphics2D g2d) throws IOException {
		InputStream stream = Projectile.class.getClassLoader().getResourceAsStream(resource);
		BufferedImage img = ImageIO.read(stream);
		stream.close();
		g2d.drawImage(
				img,
				(int) (this.x - (this.width / 2)),
				(int) (this.y - (this.height / 2)),
				null
		);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Projectile that = (Projectile) o;
		return framesExisted == that.framesExisted &&
				Double.compare(that.x, x) == 0 &&
				Double.compare(that.y, y) == 0 &&
				Double.compare(that.width, width) == 0 &&
				Double.compare(that.height, height) == 0 &&
				damage == that.damage &&
				invulTime == that.invulTime &&
				Objects.equals(resource, that.resource);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(framesExisted, x, y, width, height, damage, invulTime, resource);
	}
}
