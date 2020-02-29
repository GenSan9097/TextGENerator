package com.Gengen6848;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.undo.UndoManager;

public abstract class MyTabManager extends ConsoleOwner implements ActionListener, ChangeListener {
	
	public static int tabCount = 0;
	public JTabbedPane tabpane;
	public ArrayList<EditorConsole> consoleList;
	public ArrayList<JPanel> panel_tabComponent;
	public ArrayList<JLabel> label_tabTitle;
	public ArrayList<JButton> button_close;
	public ImageIcon icon_close;
	
	
	public MyTabManager(EditorConsole console, String title) {
		super(console);
		tabpane = new JTabbedPane();
		tabpane.addChangeListener(this);
		consoleList = new ArrayList<EditorConsole>();
		panel_tabComponent = new ArrayList<JPanel>();
		label_tabTitle = new ArrayList<JLabel>();
		icon_close = new ImageIcon("src/com/Gengen6848/button_close_1.png");
		button_close = new ArrayList<JButton>();
		
		addMyTab(console, title);
	}
	
	public void addMyTab(EditorConsole console, String title) {
		int index;
		if (tabCount == 0) {
			index = 0;
		} else {
			index = tabpane.getSelectedIndex() + 1;
		}
		
		consoleList.add(index, console);
		panel_tabComponent.add(index, new JPanel(new BorderLayout() ) );
		label_tabTitle.add(index, new JLabel(title) );
		getLabelTabTitleAt(index).setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 14) );
		button_close.add(index, new JButton(icon_close) );
		getButtonCloseAt(index).setPreferredSize(new Dimension(icon_close.getIconWidth(), icon_close.getIconHeight() ) );
		getButtonCloseAt(index).addActionListener(this);
		getPanelTabComponentAt(index).add(getLabelTabTitleAt(index), BorderLayout.CENTER );
		getPanelTabComponentAt(index).add(getButtonCloseAt(index), BorderLayout.EAST );
		getPanelTabComponentAt(index).setOpaque(false);
		tabpane.add(console.getMainComponent() );
		tabpane.setTabComponentAt(index, getPanelTabComponentAt(index) );
		tabpane.setSelectedIndex(index);
		tabCount++;
		System.out.println("tabCount:" + tabCount);
	}
	
	public void deleteMyTab(int index) {
		EditorConsole console = getConsoleAt(index);
		if (console.close() == 0) {
			tabCount--;
			consoleList.remove(index);
			panel_tabComponent.remove(index);
			label_tabTitle.remove(index);
			button_close.remove(index);
			tabpane.remove(index);
			System.out.println("tabCount" + tabCount);
			if (tabCount == 0) {
				getJFrame().dispose();
			}
		}
		
		
	}
	
	public EditorConsole getConsoleAt(int index) {
		return consoleList.get(index);
	}
	
	public JButton getButtonCloseAt(int index) {
		return button_close.get(index);
	}
	
	public JPanel getPanelTabComponentAt(int index) {
		return panel_tabComponent.get(index);
	}
	
	public JLabel getLabelTabTitleAt(int index) {
		return label_tabTitle.get(index);
	}
	
	@Override
	public Component getMainComponent() {
		// TODO 自動生成されたメソッド・スタブ
		return tabpane;
	}

	@Override
	public void createNew() {
		// TODO 自動生成されたメソッド・スタブ
		console.createNew();
	}

	@Override
	public void openFile() {
		// TODO 自動生成されたメソッド・スタブ
		console.openFile();
	}

	@Override
	public int save() {
		// TODO 自動生成されたメソッド・スタブ
		return console.save();
	}

	@Override
	public int saveAs() {
		// TODO 自動生成されたメソッド・スタブ
		return console.saveAs();
	}
	
	@Override
	public int close() {
		// TODO 自動生成されたメソッド・スタブ
		/*タブの総数を取得して、各タブ内のconsoleのclose()を行い、1つでも1を返せば
		(1つでも閉じてはいけないファイルがあれば)1を返し、すべて0を返したならば(すべて閉じていいならば)
		0を返す*/
		for (int i = 0; i < tabCount; i++) {
			if (getConsoleAt(i).close() == 1 ) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public void undo() {
		// TODO 自動生成されたメソッド・スタブ
		console.undo();
	}

	@Override
	public void redo() {
		// TODO 自動生成されたメソッド・スタブ
		console.redo();
	}

	@Override
	public int notSavedAllert() {
		// TODO 自動生成されたメソッド・スタブ
		return console.notSavedAllert();
	}

	public abstract void setTitle(String title);

	@Override
	public JFrame getJFrame() {
		// TODO 自動生成されたメソッド・スタブ
		return owner.getJFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e_Act) {
		// TODO 自動生成されたメソッド・スタブ
		Object obj = e_Act.getSource();
		int index = 0;
		for (JButton closeButton : button_close) {
			if (obj == closeButton) {
				System.out.println(index);
				break;
			}
			index++;
		}
		
		deleteMyTab(index);
		
	}

	@Override
	public void stateChanged(ChangeEvent e_Change) {
		// TODO 自動生成されたメソッド・スタブ
		 if (tabCount != 0) {
			 int index = tabpane.getSelectedIndex();
			 setConsole(getConsoleAt(index) );
				owner.setConsole(console);
		 }
		
	}

	public UndoManager getUndoManager() {
		return console.getUndoManager();
	}
}
