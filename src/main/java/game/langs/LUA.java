package game.langs;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LUA {
	private final Globals globals = JsePlatform.standardGlobals();
	
	public LuaValue compile(String text, String name) {
		return globals.load(text, name);
	}
	
	public LuaValue compile(String file) {
		return globals.loadfile(file);
	}
	
	public LuaValue execute(LuaValue value) {
		return value.call();
	}
	
	public LuaValue execute(LuaValue value, String arg) {
		return value.call(arg);
	}
	
	public LuaValue execute(LuaValue value, LuaValue arg1) {
		return value.call(arg1);
	}
	
	public LuaValue execute(LuaValue value, LuaValue arg1, LuaValue arg2) {
		return value.call(arg1, arg2);
	}
	
	public LuaValue execute(LuaValue value, LuaValue arg1, LuaValue arg2, LuaValue arg3) {
		return value.call(arg1, arg2, arg3);
	}
}
