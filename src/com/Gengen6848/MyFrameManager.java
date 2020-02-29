package com.Gengen6848;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;


public abstract class MyFrameManager extends ConsoleOwner implements ActionListener, MouseListener {
	
	private static int windowCount = 0;
	private JFrame frame;
	public Font windowFont;
	public JPanel panel_mother;
	public JPanel panel_statusbar;
	public JLabel label_statusbar;
	public JMenuBar menubar;
	public JMenu menu_file;
	public JMenu menu_edit;
	public JMenu menu_view;
	public JMenuItem createNew;
	public JMenuItem openFile;
	public JMenuItem save;
	public JMenuItem saveAs;
	public JMenuItem close;
	public JMenuItem undo;
	public JMenuItem redo;
	public JCheckBoxMenuItem showStatusbar; 
	
	public MyFrameManager(EditorConsole console, String title) {
		super(console);
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Reader filereader = null;
		boolean statusbarSelected = false;
		try {
			filereader = new FileReader("src/com/Gengen6848/windowConfiguration.properties");
			Properties prop = new Properties();
			prop.load(filereader);
			int x = Integer.parseInt(prop.getProperty("x") );
			int y = Integer.parseInt(prop.getProperty("y") );
			int width = Integer.parseInt(prop.getProperty("width") );
			int height = Integer.parseInt(prop.getProperty("height") );
			statusbarSelected = Boolean.valueOf(prop.getProperty("statusbar") );
			System.out.println(statusbarSelected);
			
			filereader.close();
			
			frame.setBounds(x, y, width, height);
		} catch (Exception e1) {
			System.out.println(e1.getMessage() );
		} finally {
			if (filereader != null) {
				try {
					filereader.close();
				} catch (IOException e2) {}
			}
		}
		
		windowFont = new Font("ＭＳ ゴシック", Font.PLAIN, 14);
		
		menubar = new JMenuBar();
		
		menu_file = new JMenu("ファイル");
		menu_edit = new JMenu("編集");
		menu_view = new JMenu("表示");
		
		implementsJMenuAs("ファイル", menu_file);
		implementsJMenuAs("編集", menu_edit);
		implementsJMenuAs("表示", menu_view);
		
		createNew = new JMenuItem("新規作成");
		openFile = new JMenuItem("開く");
		save = new JMenuItem("上書き保存");
		saveAs = new JMenuItem("名前を付けて保存");
		close = new JMenuItem("閉じる");
		undo = new JMenuItem("元に戻す");
		redo = new JMenuItem("再実行");
		
		implementsJMenuItemAs("新規作成", createNew, menu_file);
		implementsJMenuItemAs("開く", openFile, menu_file);
		implementsJMenuItemAs("上書き保存", save, menu_file);
		implementsJMenuItemAs("名前を付けて保存", saveAs, menu_file);
		implementsJMenuItemAs("閉じる", close, menu_file);
		implementsJMenuItemAs("元に戻す", undo, menu_edit);
		implementsJMenuItemAs("再実行", redo, menu_edit);
		
		undo.setEnabled(getUndoManager().canUndo() );
		redo.setEnabled(getUndoManager().canRedo() );
		
		System.out.println(statusbarSelected);
		showStatusbar = new JCheckBoxMenuItem("ステータスバー", statusbarSelected);
		showStatusbar.setFont(windowFont);
		showStatusbar.addActionListener(this);
		showStatusbar.addMouseListener(this);
		menu_view.add(showStatusbar);
		
		createNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
		
		
		frame.setJMenuBar(menubar);
		
		panel_mother = new JPanel(new BorderLayout() );
		
		panel_statusbar = new JPanel();
		label_statusbar = new JLabel(" ");
		label_statusbar.setFont(windowFont);
		label_statusbar.setHorizontalAlignment(JLabel.LEADING);
		panel_statusbar.add(label_statusbar);
		
		if (statusbarSelected) {
			panel_mother.add(panel_statusbar, BorderLayout.SOUTH);
		}
		
		frame.getContentPane().add(panel_mother);
	}
	
	public void implementsJMenuAs(String menuName, JMenu menu) {
		menu.setFont(windowFont);
		menubar.add(menu);
	}
	
	public void implementsJMenuItemAs(String menuItemName, JMenuItem menuitem, JMenu menu) {
		menuitem.setFont(windowFont);
		menuitem.addActionListener(this);
		menuitem.addMouseListener(this);
		menu.add(menuitem);
	}
	
	public void actionPerformed(ActionEvent e_Act) {
		Object obj = e_Act.getSource();
		if (obj == createNew) {
			createNew();
			undo.setEnabled(getUndoManager().canUndo() );
			redo.setEnabled(getUndoManager().canRedo() );
		} else if (obj == openFile) {
			openFile();
			undo.setEnabled(getUndoManager().canUndo() );
			redo.setEnabled(getUndoManager().canRedo() );
		} else if (obj == save) {
			save();
		} else if (obj == saveAs) {
			saveAs();
		} else if (obj == close) {
			close();
		} else if (obj == undo) {
			undo();
			undo.setEnabled(getUndoManager().canUndo() );
			redo.setEnabled(getUndoManager().canRedo() );
		} else if (obj == redo) {
			redo();
			undo.setEnabled(getUndoManager().canUndo() );
			redo.setEnabled(getUndoManager().canRedo() );
		} else /* if (obj == showStatusbar)*/ {
			if (showStatusbar.isSelected() ) {
				panel_mother.add(panel_statusbar, BorderLayout.SOUTH);
				panel_mother.revalidate();
				panel_mother.repaint();
			} else {
				panel_mother.remove(panel_statusbar);
				panel_mother.revalidate();
				panel_mother.repaint();
			}
		}
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
		return 0;
	}
	
	public abstract void setTitle();

	@Override
	public int close() {
		// TODO 自動生成されたメソッド・スタブ
		if (console.close() == 0) {
			frame.dispose();
			windowCount--;
			if (windowCount == 0) {
				System.exit(0);
			}
		}
		return 0;
	}

	@Override
	public JFrame getJFrame() {
		// TODO 自動生成されたメソッド・スタブ
		return this.frame;
	}
	
	@Override
	public Component getMainComponent() {
		return this.frame;
	}
	
	@Override
	public UndoManager getUndoManager() {
		return console.getUndoManager();
	}
	
	public void mouseEntered(MouseEvent e_Mouse) {
		Object obj = e_Mouse.getSource();
		if (obj == createNew) {
			label_statusbar.setText("現在のタブで無題のファイルを作成します");
		} else if (obj == openFile) {
			label_statusbar.setText("現在のタブで既存のファイルを開きます");
		} else if (obj == save) {
			label_statusbar.setText("編集中のファイルを保存します");
		} else if (obj == saveAs) {
			label_statusbar.setText("編集中のファイルを新しいファイルとして保存します");
		} else if (obj == close) {
			label_statusbar.setText("このエディタを閉じます");
		} else if (obj == undo ) {
			label_statusbar.setText("編集エリアをひとつ前の状態に戻します");
		} else if (obj == redo) {
			label_statusbar.setText("編集エリアの戻す操作を取り消します");
		} else /*if (obj == showStatusbar) */ {
			label_statusbar.setText("このヒントを表示するエリアを除去します");
		}
	}
	
	public void mouseExited(MouseEvent e_Mouse) {
		label_statusbar.setText(" ");
	}
	
	public void mousePressed(MouseEvent e_Mouse) {
		
	}
	
	public void mouseReleased(MouseEvent e_Mouse) {
		
	}
	
	public void mouseClicked(MouseEvent e_Mouse) {
		
	}

	

}
