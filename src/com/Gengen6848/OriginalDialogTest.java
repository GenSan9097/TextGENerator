package com.Gengen6848;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class OriginalDialogTest extends JFrame implements ActionListener, WindowListener{
	
	public JButton searchDialogGenerator;
	public JButton replaceDialogGenerator;
	public JButton fontDialogGenerator;
	public JTextArea textarea;
	public JPanel panel_mother;
	public JPanel panel_dialogGenerator;
	public SearchDialog searchDialog;
	public ReplaceDialog replaceDialog;
	public FontDialog fontDialog;
	
	public int dialogType;
	public static final int NO_DIALOG = 0;
	public static final int SEARCH_DIALOG = 1;
	public static final int REPLACE_DIALOG = 2;
	public static final int Font_DIALOG = 3;
	
	public OriginalDialogTest() {
		setTitle("SearchDialogTst");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1000, 500);
		
		panel_mother = new JPanel(new BorderLayout() );
		panel_dialogGenerator = new JPanel(new FlowLayout() );
		textarea = new JTextArea("aaaaabbbbbccccc\naaabbbccc\naabb");
		System.out.println(textarea.getFont().getName() );
		panel_mother.add(textarea, BorderLayout.CENTER);
		panel_mother.add(panel_dialogGenerator, BorderLayout.SOUTH);
		
		searchDialogGenerator = new JButton("検索ダイアログ表示");
		searchDialogGenerator.addActionListener(this);
		panel_dialogGenerator.add(searchDialogGenerator);
		replaceDialogGenerator = new JButton("置換ダイアログ表示");
		replaceDialogGenerator.addActionListener(this);
		panel_dialogGenerator.add(replaceDialogGenerator);
		fontDialogGenerator = new JButton("フォントダイアログ表示");
		fontDialogGenerator.addActionListener(this);
		panel_dialogGenerator.add(fontDialogGenerator);
		
		
		getContentPane().add(panel_mother);
		
		searchDialog = new SearchDialog(this, textarea);
		searchDialog.addWindowListener(this);
		
		replaceDialog = new ReplaceDialog(this, textarea);
		replaceDialog.addWindowListener(this);
		
		fontDialog = new FontDialog(this, textarea); 
		
		dialogType = NO_DIALOG;
		
	}
	
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		OriginalDialogTest sample = new OriginalDialogTest();
		sample.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		//if (dialogType == NO_DIALOG) {
			Object obj = arg0.getSource();
			if (obj == searchDialogGenerator) {
				replaceDialog.setVisible(false);
				searchDialog.setVisible(true);
				fontDialog.setVisible(false);
				dialogType = SEARCH_DIALOG;
			} else if (obj == replaceDialogGenerator ) {
					searchDialog.setVisible(false);
					replaceDialog.setVisible(true);
					fontDialog.setVisible(false);
					dialogType = REPLACE_DIALOG;
			} else {
				searchDialog.setVisible(false);
				replaceDialog.setVisible(false);
				fontDialog.setVisible(true);
			}
		//}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		dialogType = NO_DIALOG;
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
