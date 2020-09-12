package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class FontRenderer {
	private final BufferedImage image;
	
	public FontRenderer(String resource) throws IOException {
		InputStream stream = FontRenderer.class.getClassLoader().getResourceAsStream(resource);
		image = ImageIO.read(stream);
		stream.close();
	}
	
	public int draw(char c, int x, int y, Graphics2D g2d) {
		int dx = 0;
		int sx = 1;
		int dy = 0;
		int sy = 1;
		float additionalWidth = 0;
		int xOff = 0;
		int yOff = 0;
		int ySize = 23;
		switch (c) {
			case 'a':
				dx = 0;
				sx = 4;
				dy = 0;
				sy = 10;
				additionalWidth = 1;
				break;
			case 'b':
				dx = 10;
				sx = 4;
				dy = 0;
				sy = 10;
				break;
			case 'c':
				dx = 20;
				sx = 4;
				dy = 0;
				sy = 10;
				break;
			case 'd':
				dx = 30;
				sx = 4;
				dy = 0;
				sy = 10;
				break;
			case 'e':
				dx = 40;
				sx = 4;
				dy = 0;
				sy = 10;
				break;
			case 'f':
				dx = 50;
				sx = 4;
				dy = 0;
				sy = 10;
				break;
			case 'g':
				dx = 60;
				sx = 4;
				dy = 0;
				sy = 10;
				break;
			case 'h':
				dx = 70;
				sx = 4;
				dy = 0;
				sy = 10;
				break;
			case 'i':
				dx = 80;
				sx = 4;
				dy = 0;
				sy = 10;
				break;
			case 'j':
				dx = 90;
				sx = 4;
				dy = 0;
				sy = 10;
				break;
			case 'k':
				dx = 100;
				sx = 4;
				dy = 0;
				sy = 10;
				break;
			case 'l':
				dx = 110;
				sx = 4;
				dy = 0;
				sy = 10;
				break;
			case 'm':
				dx = 120;
				sx = 5;
				dy = 0;
				sy = 10;
				break;
			case 'n':
				dx = 131;
				sx = 4;
				dy = 0;
				sy = 10;
				break;
			case 'o':
				dx = 1;
				sx = 4;
				dy = 10;
				sy = 10;
				break;
			case 'p':
				dx = 11;
				sx = 4;
				dy = 10;
				sy = 10;
				break;
			case 'q':
				dx = 21;
				sx = 4;
				dy = 10;
				sy = 10;
				break;
			case 'r':
				dx = 31;
				sx = 4;
				dy = 10;
				sy = 10;
				break;
			case 's':
				dx = 41;
				sx = 4;
				dy = 10;
				sy = 10;
				break;
			case 't':
				dx = 51;
				sx = 4;
				dy = 10;
				sy = 10;
				break;
			case 'u':
				dx = 61;
				sx = 4;
				dy = 10;
				sy = 10;
				break;
			case 'v':
				dx = 71;
				sx = 4;
				dy = 10;
				sy = 10;
				break;
			case 'w':
				dx = 81;
				sx = 5;
				dy = 10;
				sy = 10;
				break;
			case 'x':
				dx = 92;
				sx = 4;
				dy = 10;
				sy = 10;
				break;
			case 'y':
				dx = 102;
				sx = 4;
				dy = 10;
				sy = 10;
				break;
			case 'z':
				dx = 112;
				sx = 4;
				dy = 10;
				sy = 10;
				break;
			case '0':
				dx = 104;
				sx = 4;
				dy = 30;
				sy = 10;
				break;
			case '1':
				dx = 114;
				sx = 4;
				dy = 30;
				sy = 10;
				break;
			case '2':
				dx = 121;
				sx = 6;
				dy = 30;
				sy = 10;
				xOff = -1;
				additionalWidth = 0;
				break;
			case '3':
				dx = 133;
				sx = 4;
				dy = 30;
				sy = 10;
				break;
			case '4':
				dx = 3;
				sx = 4;
				dy = 40;
				sy = 10;
				break;
			case '5':
				dx = 13;
				sx = 4;
				dy = 40;
				sy = 10;
				break;
			case '6':
				dx = 23;
				sx = 4;
				dy = 40;
				sy = 10;
				break;
			case '7':
				dx = 33;
				sx = 4;
				dy = 40;
				sy = 10;
				break;
			case '8':
				dx = 43;
				sx = 4;
				dy = 40;
				sy = 10;
				break;
			case '9':
				dx = 53;
				sx = 4;
				dy = 40;
				sy = 10;
				break;
			case '*':
				dx = 130;
				sx = 8;
				dy = 140;
				sy = 10;
				ySize = (int) (25 / 1.6);
				yOff = 4;
				break;
			case '/':
				dx = 125;
				sx = 5;
				dy = 50;
				sy = 10;
				xOff = 2;
				additionalWidth = 2;
				break;
		}
		BufferedImage render = image.getSubimage(dx, dy, sx, sy);
		g2d.drawImage(render, x + xOff, y + yOff, (int) (sx / 4f * (9 + additionalWidth)), ySize, null);
		return (int) (sx / 4f * (9 + additionalWidth));
	}
}
