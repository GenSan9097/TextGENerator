package com.Gengen6848;

public abstract class ConsoleOwner extends EditorConsole {
	public EditorConsole console;
	
	public ConsoleOwner(EditorConsole console) {
		setConsole(console);
	}
	
	public void setConsole(EditorConsole console) {
		this.console = console;
		console.setOwner(this);
	}
}
