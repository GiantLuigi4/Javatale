package battles.example;

import game.Game;

import java.util.ArrayList;

public class UI {
	private static boolean hasBandage = true;
	
	public static String displayText = "";
	public static boolean forceAttk = false;
	public static int attack = 0;
	
	public static void fillMenu(String name, ArrayList<String> elements) {
		switch (name) {
			case "fight":
				elements.add("Nothing");
				break;
			case "act":
				elements.add("Check");
				elements.add("Attack1");
				elements.add("Attack2");
				break;
			case "item":
				if (hasBandage)
					elements.add("Bandage");
				break;
			case "mercy":
				elements.add("Spare");
				elements.add("Flea");
				break;
			default:
				break;
		}
	}
	
	public static void handleInput(String menu, String input) {
		forceAttk = false;
		displayText = "";
		switch (menu) {
			case "act":
				if (input.equals("Check")) {
					displayText = "Nothing 1 ATK 0 DEF\nIt is literally nothing";
				} else if (input.equals("Attack1")) {
					forceAttk = true;
					attack = 1;
				} else if (input.equals("Attack2")) {
					forceAttk = true;
					attack = 2;
				}
				break;
			case "item":
				displayText = "You used the bandage.\n10 HP restored.";
				Game.hp += 10;
				Game.hp = Math.max(Game.hp,Game.healths[Game.lvl-1]);
				break;
			case "mercy":
				if (input.equals("Spare")) {
					Game.battleName = "";
					Game.inGame = false;
				} else {
					displayText = "";
				}
			default:
				break;
		}
	}
}
