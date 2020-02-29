package com.Gengen6848;

import java.awt.Component;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

public class MyTextArea extends EditorConsole implements UndoableEditListener, DocumentListener {
	
	private File file;
	private boolean savedOrNot;
	private JTextArea textarea;
	private Font textfont;
	private JScrollPane scrollpane;
	private JFileChooser filechooser;
	private FileWriter filewriter;
	private StringBuilder stringbuilder;
	private UndoManager undo;
	
	public MyTextArea() {
		textarea = new JTextArea();
		textarea.setLineWrap(true);
		
		Reader configreader = null;
		String font_type = "ＭＳ 明朝";
		int font_style = Font.PLAIN;
		int font_size = 24;
		filechooser = new JFileChooser();
		
		String currentDirectory = null; 
		
		try {
			configreader = new FileReader("src/com/Gengen6848/TextAreaConfiguration.properties");
			Properties prop = new Properties();
			prop.load(configreader);
			font_type = prop.getProperty("font_type");
			System.out.println(Font.PLAIN);
			font_style = Integer.parseInt(prop.getProperty("font_style") );
			font_size = Integer.parseInt(prop.getProperty("font_size") );
			currentDirectory = prop.getProperty("filechooser");
			
			
			configreader.close();
			
		} catch (Exception e1) {
			System.out.println("テキストエリアの設定読み込みでのエラー");
			System.out.println(e1.getMessage() );
		} finally {
			if (configreader != null) {
				try {
					configreader.close();
				} catch (IOException e2) {}
			}
		}
		
		textfont = new Font(font_type, font_style, font_size);
		textarea.setFont(textfont);
		filechooser = new JFileChooser(currentDirectory);
		FileNameExtensionFilter filter_all = new FileNameExtensionFilter("テキスト系全て", "txt", "java", "kt", "py", "properties", "xml", "csv");
		FileNameExtensionFilter filter_txt = new FileNameExtensionFilter("テキストファイル(.txt)", "txt");
		FileNameExtensionFilter filter_java = new FileNameExtensionFilter("Javaファイル(.java)", "java");
		FileNameExtensionFilter filter_kotlin = new FileNameExtensionFilter("Kotlinファイル(.kt)", "kt");
		FileNameExtensionFilter filter_python = new FileNameExtensionFilter("Pythonファイル(.py)", "py");
		FileNameExtensionFilter filter_prop = new FileNameExtensionFilter("プロパティファイル(.Properties)", "properties");
		FileNameExtensionFilter filter_xml = new FileNameExtensionFilter("XMLファイル(.xml)", "xml");
		FileNameExtensionFilter filter_csv = new FileNameExtensionFilter("CSVファイル(.csv)", "csv");
		filechooser.setFileFilter(filter_all);
		filechooser.addChoosableFileFilter(filter_txt);
		filechooser.addChoosableFileFilter(filter_java);
		filechooser.addChoosableFileFilter(filter_kotlin);
		filechooser.addChoosableFileFilter(filter_python);
		filechooser.addChoosableFileFilter(filter_prop);
		filechooser.addChoosableFileFilter(filter_xml);
		filechooser.addChoosableFileFilter(filter_csv);
		
		scrollpane = new JScrollPane(textarea);
		scrollpane.setViewportView(textarea);
		
		file = null;
		savedOrNot = true;
		
		setTitle(getFileName() );
		
		stringbuilder = new StringBuilder();
		
		undo = new UndoManager();
		
		textarea.getDocument().addUndoableEditListener(this);
		textarea.getDocument().addDocumentListener(this);
		
		//EditorConsoleを格納するクラスの変数
	}
	
	public boolean getSavedOrNot() {
		return savedOrNot;
	}
	
	public void setSavedOrNot(boolean bool) {
		savedOrNot = bool;
	}
	
	@Override
	public Component getMainComponent() {
		// TODO 自動生成されたメソッド・スタブ
		return scrollpane;
	}
	
	public String getFileName() {
		if (file == null) {
			return "無題";
		} else {
			return file.getName();
		}
	}
	
	
	@Override
	public void createNew() {
		// TODO 自動生成されたメソッド・スタブ
		int yesNoCancelOpt = 0;
		System.out.println(getSavedOrNot() );
		if (getSavedOrNot() == false) {
			yesNoCancelOpt = notSavedAllert();
			System.out.println(yesNoCancelOpt);
		}
		
		if(yesNoCancelOpt != 2) {
			file = null;
			textarea.setText("");
			owner.setTitle(getFileName() );
			setSavedOrNot(true);
			if (undo != null) {
				undo.die();
			}
		} else {
			//do nothing
		}
	}

	@Override
	public void openFile() {
		// TODO 自動生成されたメソッド・スタブ
		int yesNoCancelOpt = 0;
		if (getSavedOrNot() == false) {
			yesNoCancelOpt = notSavedAllert();
		}
		
		if (yesNoCancelOpt != 2) {
			int selected = filechooser.showOpenDialog(textarea);
			if (selected == JFileChooser.APPROVE_OPTION) {
				file = filechooser.getSelectedFile();
				System.out.println(file.getName() );
				
				try {
					BufferedReader buffReader = new BufferedReader((new FileReader(file)));
					
					String str;
					while ( (str = buffReader.readLine() ) != null ) {
						stringbuilder.append(str + "\n");
					}
					
					textarea.setText(stringbuilder.toString() );
					buffReader.close();
					stringbuilder.setLength(0);
					owner.setTitle(getFileName() );
					setSavedOrNot(true);
					if (undo != null) {
						undo.die();
					}
					
				} catch (FileNotFoundException error_FNF) {
					System.out.println(error_FNF);
				} catch (IOException error_IO) {
					System.out.println(error_IO);
				}
			} else {
				//do nothing
			}
		}
	}

	@Override
	public int save() {
		// TODO 自動生成されたメソッド・スタブ
		int SUCCESSFULLY_SAVED = 0;
		int SAVE_FAILED = 1;
		if (file == null) {
			return saveAs();
		} else {
			try {
				filewriter = new FileWriter(file);
				String[] strs = textarea.getText().split("\n");
				for (String str : strs) {
					stringbuilder.append(str + System.lineSeparator());
				}
				filewriter.write(stringbuilder.toString());
				filewriter.flush();
				filewriter.close();
				owner.setTitle(getFileName() );
				setSavedOrNot(true);
				if (undo != null) {
					undo.die();
				}
				return SUCCESSFULLY_SAVED;
				
			} catch (IOException error_IO) {
				System.out.println(error_IO);
				return SAVE_FAILED;
			}
		}
	}

	@Override
	public int saveAs() {
		// TODO 自動生成されたメソッド・スタブ
		int SUCCESSFULLY_SAVED = 0;
		int SAVE_FAILED = 1;
		int SAVE_CANCELED = 2;
		int selected = filechooser.showSaveDialog(textarea);
		if (selected == JFileChooser.APPROVE_OPTION) {
			file = filechooser.getSelectedFile();
			try {
				filewriter = new FileWriter(file);
				filewriter.write(textarea.getText() );
				filewriter.flush();
				filewriter.close();
				owner.setTitle(getFileName() );
				setSavedOrNot(true);
				if (undo != null) {
					undo.die();
				}
				return SUCCESSFULLY_SAVED;
				
			} catch (IOException error_IO) {
				System.out.println(error_IO);
				return SAVE_FAILED;
			}
		} else {
			return SAVE_CANCELED;
		}
	}
	
	@Override
	public int close() {
		// TODO 自動生成されたメソッド・スタブ
		//optionについて, JOptionPaneの定数
		int YES_OPTION = 0;		//保存して閉じる
		int NO_OPTION = 1;		//保存しないで閉じる
		//int CANCEL_OPTION = 2;	//閉じる動作のキャンセル
		int yesNoCancelOpt = 0;
		if (getSavedOrNot() == false) {
			yesNoCancelOpt = notSavedAllert();
		}
		
		if (yesNoCancelOpt == YES_OPTION || yesNoCancelOpt == NO_OPTION) {
			return 0;	//YesまたはNoが押されてファイルを閉じてもいいことを通知
		} else {
			return 1;	//Cancelが押されてファイルを閉じていはけないことを通知
		}
	}

	@Override
	public void undo() {
		// TODO 自動生成されたメソッド・スタブ
		undo.undo();
	}

	@Override
	public void redo() {
		// TODO 自動生成されたメソッド・スタブ
		undo.redo();
	}
	
	@Override
	public void undoableEditHappened(UndoableEditEvent e_undo) {
		// TODO 自動生成されたメソッド・スタブ
		undo.addEdit(e_undo.getEdit() );
	}

	@Override
	public int notSavedAllert() {
		// TODO 自動生成されたメソッド・スタブ
		//optionについて, JOptionPaneの定数
		//YES_OPTION == 0, NO_OPTION == 1, CANCEL_OPTION == 2
		int option = -1;
		JFrame frame = getJFrame();
		option = JOptionPane.showConfirmDialog(
			frame, "ファイル名:" + getFileName() + "\n内容が変更されています。保存しますか?",  frame.getTitle(),
			JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
				);
		
		if (option == JOptionPane.YES_OPTION) {
			return save();
		}
		
		return option; 
	}

	@Override
	public void setTitle(String title) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		setSavedOrNot(false);
		owner.setTitle("*" + getFileName() );
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		setSavedOrNot(false);
		owner.setTitle("*" + getFileName() );
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		setSavedOrNot(false);
		owner.setTitle("*" + getFileName() );
	}

	@Override
	public JFrame getJFrame() {
		// TODO 自動生成されたメソッド・スタブ
		return owner.getJFrame();
	}

	@Override
	public UndoManager getUndoManager() {
		return undo;
	}

	public void copy() {
		textarea.copy();
	}
	
	public void cut() {
		textarea.cut();
	}
	
	public void paste() {
		textarea.paste();
	}

}
