package boot;

import com.tfc.flame.FlameConfig;
import com.tfc.flame.FlameLog;
import com.tfc.utils.Files;
import com.tfc.utils._flame.FlameLoader;
import com.tfc.utils._flame.dependency_management.Manager;
import com.tfc.utils.groovy.flame.GroovyFlameClassLoader;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Launch {
	private static final GroovyFlameClassLoader loader;
	private static final Manager dependencyManager;
	
	static {
		FlameConfig.field = new FlameLog();
		try {
			ArrayList<File> allBattles = new ArrayList<>();
			File[] files = new File(Files.dir+"\\battles").listFiles();
			for (File f:files) {
				if (f.isDirectory())
					for (File f1:f.listFiles())
						if (f1.getName().endsWith(".jar") || f1.getName().endsWith(".zip") || f1.isDirectory())
							allBattles.add(f1);
			}
			allBattles.addAll(
					Arrays.asList(
							new File(Files.dir + "\\Javatale.jar"),
							new File(Files.dir + "\\src\\main\\js"),
							new File(Files.dir + "\\build\\classes\\java\\main"),
							new File(Files.dir)
					)
			);
			dependencyManager = Manager.constructFromDependencies(GroovyFlameClassLoader.class,
					"libs/jython.zip,https://repo1.maven.org/maven2/org/python/jython/2.7.0/jython-2.7.0.jar," +
							"libs/scala.zip,https://repo1.maven.org/maven2/org/scala-lang/scala-library/2.11.12/scala-library-2.11.12.jar," +
							"libs/lua.zip,https://repo1.maven.org/maven2/org/luaj/luaj-jse/3.0.1/luaj-jse-3.0.1.jar," +
							"libs/kotlinx-debug.zip,https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-debug/1.3.9/kotlinx-coroutines-debug-1.3.9.jar," +
							"libs/kotlinx-core.zip,https://repo1.maven.org/maven2/org/jetbrains/kotlinx/kotlinx-coroutines-core/1.3.9/kotlinx-coroutines-core-1.3.9.jar," +
							genGroovyDep("json") +
							genGroovyDep("jmx") +
							genGroovyDep("groovysh") +
							genGroovyDep("nio") +
							genGroovyDep("console") +
							genGroovyDep("bsf") +
							genGroovyDep("console") +
							genGroovyDep("ant") +
							genGroovyDep("xml") +
							genGroovyDep("swing") +
							genGroovyDep("servlet") +
							genGroovyDep("backports-compat23") +
							genGroovyDep("jsr223") +
							genKTDep("") +
							genKTDep("reflect") +
							genKTDep("stdlib-jdk8") +
							"libs/java_script.zip,https://plugins.gradle.org/m2/com/moowork/gradle/gradle-node-plugin/1.3.1/gradle-node-plugin-1.3.1.jar,",
					"",
					allBattles.toArray(new File[allBattles.size()])
			);
			loader = (GroovyFlameClassLoader) dependencyManager.getLoader();
			File f = new File(Files.dir + "\\battles\\callHelper.py");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
				FileWriter writer = new FileWriter(f);
				writer.write("from game import Game\n\n\ndef set_player_pos(x, y):\n    Game.playerX = x\n    Game.playerY = y\n\n\ndef set_player_pos_x(x):\n    Game.playerX = x\n\n\ndef set_player_pos_y(y):\n    Game.playerY = y\n\n\ndef get_player_x():\n    return Game.playerX\n\n\ndef get_player_y():\n    return Game.playerY\n\n\ndef get_keys():\n    return Game.keys\n\n\ndef is_pressed(char):\n    return Game.keys.contains(char)\n\n\ndef rotate(amt):\n    Game.globalRotation = Game.globalRotation + amt\n\n\ndef set_rotation(amt):\n    Game.globalRotation = amt\n\n\ndef get_rotation():\n    return Game.globalRotation\n\n\ndef get_offset_x():\n    return Game.globalOffsetX\n\n\ndef get_offset_y():\n    return Game.globalOffsetY\n\n\ndef set_offset_x(amt):\n    Game.globalOffsetX = amt\n\n\ndef set_offset_y(amt):\n    Game.globalOffsetY = amt\n\n\ndef set_scale_x(amt):\n    Game.globalScaleX = amt\n\n\ndef set_scale_y(amt):\n    Game.globalScaleY = amt\n\n\ndef get_scale_x():\n    return Game.globalScaleX\n\n\ndef get_scale_y():\n    return Game.globalScaleY\n\n\ndef set_board_pos(x,y):\n    Game.boardX = x\n    Game.boardY = y\n\n\ndef set_board_x(val):\n    Game.boardX = val\n\n\ndef set_board_y(val):\n    Game.boardY = val\n\n\ndef get_board_x():\n    return Game.boardX\n\n\ndef get_board_y():\n    return Game.boardY\n\n\ndef set_board_size(x, y):\n    Game.boardX = x\n    Game.boardY = y\n\n\ndef set_board_size_x(amt):\n    Game.boardX = amt\n\n\ndef set_board_size_y(amt):\n    Game.boardY = amt\n");
				writer.close();
			}
		} catch (IOException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		loader.load("game.Game").getMethod("main", String[].class).invoke(null, (Object) args);
	}
	
	public static String genGroovyDep(String Package) {
		return "libs/groovy-" + Package + ".zip,https://repo1.maven.org/maven2/org/codehaus/groovy/groovy-all/2.3.11/groovy-all-2.3.11.jar,".replace("all", Package);
	}
	
	public static String genKTDep(String Package) {
		if (Package.equals(""))
			return "libs/kotlin.zip,https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/1.4.10/kotlin-stdlib-1.4.10.jar,";
		else
			return "libs/kotlin-" + Package + ".zip,https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-" + Package + "/1.4.10/kotlin-" + Package + "-1.4.10.jar,";
	}
}
