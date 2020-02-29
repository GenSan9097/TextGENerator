package com.Gengen6848;


import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.undo.UndoManager;

public abstract class EditorConsole {
	public ConsoleOwner owner;
	
	public void setOwner(ConsoleOwner owner) {
		this.owner = owner;
	}
	
	public abstract void createNew();
	public abstract void openFile();
	public abstract int save();
	public abstract int saveAs();
	public abstract int close();
	public abstract void undo();
	public abstract void redo();
	
	public abstract int notSavedAllert();
	
	public abstract void setTitle(String title);
	public abstract JFrame getJFrame();
	public abstract Component getMainComponent();
	public abstract UndoManager getUndoManager();
}
