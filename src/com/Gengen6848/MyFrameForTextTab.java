package com.Gengen6848;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

public class MyFrameForTextTab extends MyFrameManager {

	public static final int SEARCH_DIALOG = 0;
	public static final int REPLACE_DIALOG = 1;
	
	public JPanel panel_toolbar;
	public JLabel label_tool_createNew;
	public JLabel label_tool_openFile;
	public JLabel label_tool_save;
	public JLabel label_tool_undo;
	public JLabel label_tool_redo;
	public JLabel label_tool_copy;
	public JLabel label_tool_cut;
	public JLabel label_tool_paste;
	
	
	
	public MyFrameForTextTab(EditorConsole console, String title) {
		super(console, title);
		// TODO 自動生成されたコンストラクター・スタブ
		
		panel_toolbar = new JPanel(new FlowLayout() );
		
		label_tool_createNew = new JLabel(new ImageIcon("src/com/Gengen6848/createNew.png"));
		label_tool_openFile = new JLabel(new ImageIcon("src/com/Gengen6848/openFile.png"));
		label_tool_save = new JLabel(new ImageIcon("src/com/Gengen6848/save.png"));
		label_tool_undo = new JLabel(new ImageIcon("src/com/Gengen6848/undo.png"));
		label_tool_redo = new JLabel(new ImageIcon("src/com/Gengen6848/redo.png"));
		label_tool_copy = new JLabel(new ImageIcon("src/com/Gengen6848/copy.png"));
		label_tool_cut = new JLabel(new ImageIcon("src/com/Gengen6848/cut.png"));
		label_tool_paste = new JLabel(new ImageIcon("src/com/Gengen6848/paste.png"));
		
		implementTool(label_tool_createNew,  "新規作成");
		implementTool(label_tool_openFile, "開く");
		implementTool(label_tool_save, "上書き保存");
		implementTool(label_tool_undo, "元に戻す");
		implementTool(label_tool_redo, "再実行");
		implementTool(label_tool_copy, "コピー");
		implementTool(label_tool_cut, "切り取り");
		implementTool(label_tool_paste, "貼り付け");
	}
	
	public void implementTool(JLabel tool, String toolTip) {
		tool.setPreferredSize(new Dimension(16, 16) );
		tool.addMouseListener(this);
		tool.setToolTipText(toolTip);
		panel_toolbar.add(tool);
		
	}

	@Override
	public void setTitle() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void setTitle(String title) {
		// TODO 自動生成されたメソッド・スタブ
		getJFrame().setTitle(title);
	}
	
	public void actionPerformed(ActionEvent e_Act) {
		
	}
	
	public void mousePressed(MouseEvent e_Mouse) {
		Object obj = e_Mouse.getSource();
		if (obj == label_tool_createNew) {
			createNew.doClick();
		} else if (obj == label_tool_openFile) {
			openFile.doClick();
		} else if (obj == label_tool_save) {
			save.doClick();
		} else if (obj == label_tool_undo) {
			undo.doClick();
		} else if (obj == label_tool_redo) {
			redo.doClick();
		} else if (obj == label_tool_copy) {
			
		} else if (obj == label_tool_cut) {
			
		} else if (obj == label_tool_paste) {
			
		}
	}
	
	public void mouseEntered(MouseEvent e_Mouse) {
		Object obj = e_Mouse.getSource();
		if (obj == label_tool_createNew) {
			label_statusbar.setText("現在のタブで無題のファイルを作成します");
		} else if (obj == label_tool_openFile) {
			label_statusbar.setText("現在のタブで既存のファイルを開きます");
		} else if (obj == label_tool_save) {
			label_statusbar.setText("編集中のファイルを保存します");
		} else if (obj == label_tool_undo ) {
			label_statusbar.setText("編集エリアをひとつ前の状態に戻します");
		} else if (obj == label_tool_redo) {
			label_statusbar.setText("編集エリアの戻す操作を取り消します");
		} else if (obj == label_tool_copy) {
			label_statusbar.setText("選択した内容をクリップボードにコピーします");
		} else if (obj == label_tool_cut) {
			label_statusbar.setText("選択した内容をクリップボードに切り取ります");
		} else if (obj == label_tool_paste) {
			label_statusbar.setText("クリップボードの内容を貼り付けます");
		}
	}
	
	public void mouseExited(MouseEvent e_Mouse) {
		label_statusbar.setText("　");
	}
	
	public void stateChanged(ChangeEvent e_Change) {
		
	}

}
