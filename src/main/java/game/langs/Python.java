package game.langs;

import game.Game;
import org.python.core.PyCode;
import org.python.util.PythonInterpreter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Python {
	private static final PythonInterpreter interpreter = createInterp();
	
	private static PythonInterpreter createInterp() {
		System.setProperty("python.import.site", "false");
		return new PythonInterpreter();
	}
	
	public static String getName(File file) {
		return file
				.getPath()
				.substring(0, file.getPath().lastIndexOf('.'))
				.substring(Game.dir.length() + 1)
				.replace("/", ".")
				.replace("\\", ".")
				;
	}
	
	public static PyCode open(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		StringBuilder code = new StringBuilder();
		while (sc.hasNextLine())
			code.append(sc.nextLine()).append("\n");
		sc.close();
		return interpreter.compile(code.toString(), getName(file));
	}
	
	public static void exec(File file) throws FileNotFoundException {
		interpreter.execfile(new FileInputStream(file));
	}
	
	public static PyCode read(String name, String code) {
		return interpreter.compile(code, name);
	}
	
	public static PyCode read(String code) {
		return interpreter.compile(code);
	}
	
	public static void exec(String code) {
		System.out.println(code);
		interpreter.exec(code);
	}
	
	public static void exec(String code, String name) {
		interpreter.execfile(new ByteArrayInputStream(code.getBytes()), name);
	}
	
	public static void exec(PyCode code) {
		interpreter.exec(code);
	}
}
