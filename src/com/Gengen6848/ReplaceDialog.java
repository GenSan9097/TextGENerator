package com.Gengen6848;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.text.BadLocationException;

public class ReplaceDialog extends JDialog implements ActionListener, MouseListener {
	
	private Font dialogFont;
	
	private JPanel fieldPanel;
	private JPanel searchPanel;
	private JLabel searchLabel;
	private JTextField searchField;
	private JPanel replacePanel;
	private JLabel replaceLabel;
	private JTextField replaceField;
	
	private JPanel optionAndDirection;
	private JPanel optionPanel;
	private JCheckBox distinguishCase;
	private JCheckBox wrap;
	
	private JPanel directionPanel;
	private JRadioButton backward;	//変数名をupwardにすべき?
	private JRadioButton forward;		//変数名をdownwardにすべき?
	
	private JPanel buttonPanel;
	private JButton searchAllButton;
	private JButton searchNextButton;
	private JButton replaceNextButton;
	private JButton replaceAllButton;
	
	
	private String searchWords;
	private int currentCaret;
	private int textLength;
	private String searchText;
	private int searchWordIndex = -1;
	private String replaceWords;
	private int replaceWordIndex = -1;
	
	private GridBagLayout layout;
	private JPanel motherPanel;
	
	JTextArea textarea;
	
	public ReplaceDialog(Frame owner, JTextArea textarea) {
		super(owner);
		setTitle("検索");
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setSize(600, 280);
		setResizable(false);
		
		this.textarea = textarea;
		
		dialogFont = new Font("Monospaced", Font.PLAIN, 14);
		
		layout = new GridBagLayout();
		motherPanel = new JPanel();
		motherPanel.setLayout(layout);
		
		fieldPanel = new JPanel();
		fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS) );
		searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout() );
		searchLabel = new JLabel("検索する文字列: ");
		searchLabel.setFont(dialogFont);
		//searchField = new JTextField(getLastWord("searchField"), 25);
		searchField = new JTextField("", 25);
		searchField.addMouseListener(this);
		
		replacePanel = new JPanel();
		replacePanel.setLayout(new FlowLayout() );
		replaceLabel = new JLabel("置換後の文字列: ");
		replaceLabel.setFont(dialogFont);
		//replaceField = new JTextField(getLastWord("replaceField"), 25);
		replaceField = new JTextField(" ", 25);
		replaceField.addMouseListener(this);
		
		searchPanel.add(searchLabel);
		searchPanel.add(searchField);
		replacePanel.add(replaceLabel);
		replacePanel.add(replaceField);
		fieldPanel.add(searchPanel);
		fieldPanel.add(replacePanel);
		
		optionAndDirection = new JPanel();
		optionAndDirection.setLayout(new BoxLayout(optionAndDirection, BoxLayout.X_AXIS) );
		
		optionPanel = new JPanel();
		EmptyBorder innerBorder_opt = new EmptyBorder(0, 0, 0, 0);
		TitledBorder outerBorder_opt = new TitledBorder(innerBorder_opt, "オプション",
				TitledBorder.LEFT, TitledBorder.TOP);
		outerBorder_opt.setTitleFont(dialogFont);
		optionPanel.setBorder(outerBorder_opt);
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS) );
		distinguishCase = new JCheckBox("大文字小文字を区別する");
		distinguishCase.setFont(dialogFont);
		wrap = new JCheckBox("折り返しあり");
		wrap.setFont(dialogFont);
		optionPanel.add(distinguishCase);
		optionPanel.add(wrap);
		
		directionPanel = new JPanel();
		EmptyBorder innerBorder_direction = new EmptyBorder(0, 0, 0, 0);
		TitledBorder outerBorder_direction = new TitledBorder(innerBorder_direction, "検索する方向",
				TitledBorder.LEFT, TitledBorder.TOP);
		outerBorder_direction.setTitleFont(dialogFont);
		directionPanel.setBorder(outerBorder_direction);
		backward = new JRadioButton("上へ");
		backward.setFont(dialogFont);
		backward.addActionListener(this);
		forward = new JRadioButton("下へ", true);
		forward.setFont(dialogFont);
		forward.addActionListener(this);
		directionPanel.add(backward);
		directionPanel.add(forward);
		
		optionAndDirection.add(optionPanel);
		optionAndDirection.add(directionPanel);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS) );
		searchAllButton = new JButton("先頭から検索");
		searchAllButton.setFont(dialogFont);
		searchAllButton.addActionListener(this);
		searchNextButton = new JButton("次を検索");
		searchNextButton.setFont(dialogFont);
		searchNextButton.addActionListener(this);
		replaceNextButton = new JButton("置換して次に");
		replaceNextButton.setFont(dialogFont);
		replaceNextButton.addActionListener(this);
		replaceAllButton = new JButton("すべて置換");
		replaceAllButton.setFont(dialogFont);
		replaceAllButton.addActionListener(this);
		buttonPanel.add(searchAllButton);
		buttonPanel.add(searchNextButton);
		buttonPanel.add(replaceNextButton);
		buttonPanel.add(replaceAllButton);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.LINE_START;
		layout.setConstraints(fieldPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		layout.setConstraints(optionAndDirection, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		layout.setConstraints(buttonPanel, gbc);
		
		motherPanel.add(fieldPanel);
		motherPanel.add(optionAndDirection);
		motherPanel.add(buttonPanel);
		
		getContentPane().add(motherPanel, BorderLayout.CENTER);
		
	}
	
	public String getLastWord(String key) {
		FileReader file = null;
		String str = "";
		try {
			file = new FileReader("lastSearchedWords.properties");
			Properties p = new Properties();
			p.load(file);
			str = p.getProperty(key);
		} catch (Exception e) {
			System.out.print(e.getMessage() );
		} finally {
			if (file != null) {
				try {
					file.close();
				}catch (Exception e) {
					System.out.println(e.getMessage() );
				}
			}
		}
		
		return str;
	}
	
	public void setLastWord() {
		
	}
	
	public void getTextArea(JTextArea textarea) {
		this.textarea = textarea;
	}
	
	@Override
	public void setVisible(boolean bool) {
		super.setVisible(bool);
		if (bool) {
			searchField.select(0, searchField.getText().length() );
		}
	}
	
	public void mouseClicked(MouseEvent e_ME) {
		//実装するのはこのメソッドのみ
		Object obj = e_ME.getSource();
		if (obj == searchField) {
			searchField.select(0, searchField.getText().length() );
		} else if (obj == replaceField) {
			replaceField.select(0, replaceField.getText().length() );
		}
	}
	
	public void mousePressed(MouseEvent e_ME) {}
	
	public void mouseReleased(MouseEvent e_ME) {}
	
	public void mouseEntered(MouseEvent e_ME) {}
	
	public void mouseExited(MouseEvent eE_ME) {}
	
	public void actionPerformed(ActionEvent e_AE) {
		Object obj = e_AE.getSource();
		if (obj == searchAllButton) {
			currentCaret = textarea.getCaretPosition();
			searchWords = searchField.getText();
			searchText = textarea.getText();
			if (distinguishCase.isSelected() == false) {
				searchWordIndex = searchText.toUpperCase().indexOf(searchWords.toUpperCase() );
			} else {
				searchWordIndex = searchText.indexOf(searchWords);
			}
			if (searchWordIndex != -1) {
				textarea.select(searchWordIndex, searchWordIndex + searchWords.length() );
			} else {
				//JOptionPaneをポップさせたい。あとで訂正
				dispose();
				JOptionPane.showMessageDialog(getOwner(), "\" " + searchWords + " \"" + "\nは見つかりませんでした",
				"test", JOptionPane.PLAIN_MESSAGE
				);
			}
		} else if (obj == searchNextButton) {
			searchNext();
		} else if (obj == replaceNextButton) {
			if ( textarea.getSelectedText() != null && textarea.getSelectedText().equals(searchField.getText() ) ) {
				searchWords = searchField.getText();
				searchWordIndex = textarea.getSelectionStart();
				replaceWords = replaceField.getText();
				textarea.replaceSelection(replaceWords);
				textarea.select(searchWordIndex, searchWordIndex + replaceWords.length() );//01/02ここまで
				searchNext();
			} else {
				searchNext();
			}
		} else if (obj == replaceAllButton) {
			searchWords = searchField.getText();
			searchText = textarea.getText();
			if (searchText.contains(searchWords) == true) {
				replaceWords = replaceField.getText();
				textarea.setText(searchText.replace(searchWords, replaceWords) );
			}
		} else if (obj == backward) {
			if (forward.isSelected() == true) {
				forward.setSelected(false);
			} else {
				backward.setSelected(true);
			}
		} else if (obj == forward) {
			if (backward.isSelected() == true) {
				backward.setSelected(false);
			} else {
				forward.setSelected(true);
			}
		}
	}
	
	public void searchNext() {
		try {
			/*1*/	
			searchWords = searchField.getText();
			/*2*/
			currentCaret = textarea.getCaretPosition();
			/*3*/
			textLength = textarea.getText().length();
			/*4*/
			if (forward.isSelected() == true) {
				searchText = textarea.getText(currentCaret, textLength - currentCaret);
			} else {
				if (textarea.getSelectedText() == null) {
					searchText = textarea.getText(0, currentCaret);
				} else {
					searchText = textarea.getText(0, textarea.getSelectionStart() );
				}
			}
			/*5*/
			if (distinguishCase.isSelected() == false) {	//大/小文字の区別:非選択かつ
				if (forward.isSelected() == true) {		//方向:下へ なら
					searchWordIndex = searchText.toUpperCase().indexOf(searchWords.toUpperCase() );
				} else {						//方向:上へ なら
					searchWordIndex = searchText.toUpperCase().lastIndexOf(searchWords.toUpperCase() );
				}
			} else {							//大/小文字の区別:選択かつ
				if (forward.isSelected() == true) {		//方向:下へ なら
					searchWordIndex = searchText.indexOf(searchWords);
				} else {						//方向:上へ なら
					searchWordIndex = searchText.lastIndexOf(searchWords);
				}
			}
			
			/*6*/
			if (searchWordIndex != -1) {					//searchWordsが含まれていたなら
				if (forward.isSelected() == true) {
					textarea.select(currentCaret + searchWordIndex, 
					currentCaret + searchWordIndex + searchWords.length()
					);
				} else {
					textarea.select(searchWordIndex, searchWordIndex + searchWords.length() );
					
				}
			} else {								//含まれていなかったら
				if (wrap.isSelected() == true) {			//折り返し:ありなら
					searchText = textarea.getText();
					if (forward.isSelected() == true) {		//方向:下へ なら
						
						if (distinguishCase.isSelected() == false) {	//大/小文字の区別:未選択なら
							searchWordIndex = searchText.toUpperCase().indexOf(searchWords.toUpperCase() );
						} else {
							searchWordIndex = searchText.indexOf(searchWords);
						}
					} else {						//方向:上 なら
						if (distinguishCase.isSelected() == false) {	//大/小文字の区別:未選択なら
							searchWordIndex = searchText.toUpperCase().lastIndexOf(searchWords.toUpperCase() );
						} else {
							searchWordIndex = searchText.lastIndexOf(searchWords);
						}
					}
				}
				
				if (searchWordIndex != -1) {
					textarea.select(searchWordIndex, searchWordIndex + searchWords.length() );
				}
			}
			
			if (searchWordIndex == -1) {
				dispose();
				JOptionPane.showMessageDialog(getOwner(), "\" " + searchWords + " \"" + "\nは見つかりませんでした",
				"test - " + getTitle(), JOptionPane.PLAIN_MESSAGE);
			}
		} catch (BadLocationException BLD) {
			System.out.println(BLD);
		}
	}
}