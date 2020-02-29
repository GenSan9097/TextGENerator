package com.Gengen6848;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;

public class SearchDialog extends JDialog implements ActionListener, MouseListener{

	private Font dialogFont;
	
	private JPanel searchPanel;
	private JLabel searchLabel;
	private JTextField searchField;
	
	private JPanel optionAndDirection;
	private JPanel optionPanel;
	private JCheckBox distinguishCase;
	private JCheckBox wrap;
	
	private JPanel directionPanel;
	private JRadioButton backward;
	private JRadioButton forward;
	
	private JPanel buttonPanel;
	private JButton searchAllButton;
	private JButton searchNextButton;
	
	
	private String searchWords;
	private int currentCaret;
	private int textLength;
	private String searchText;
	private int searchWordIndex;
	
	private GridBagLayout layout;
	private JPanel motherPanel;
	
	JTextArea textarea;
	
	public SearchDialog(JFrame owner, JTextArea textarea) {
		super(owner);
		setTitle("検索");
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setSize(600, 240);
		setResizable(false);
		
		this.textarea = textarea;
		
		dialogFont = new Font("Monospaced", Font.PLAIN, 14);
		
		layout = new GridBagLayout();
		motherPanel = new JPanel();
		motherPanel.setLayout(layout);
		
		searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout() );
		searchLabel = new JLabel("検索する文字列: ");
		searchLabel.setFont(dialogFont);
		searchField = new JTextField("", 25);
		searchField.addMouseListener(this);
		searchPanel.add(searchLabel);
		searchPanel.add(searchField);
		
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
		searchNextButton.setPreferredSize(searchAllButton.getPreferredSize() );
		buttonPanel.add(searchAllButton);
		buttonPanel.add(searchNextButton);
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.LINE_START;
		layout.setConstraints(searchPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		layout.setConstraints(optionAndDirection, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		layout.setConstraints(buttonPanel, gbc);
		
		motherPanel.add(searchPanel);
		motherPanel.add(optionAndDirection);
		motherPanel.add(buttonPanel);
		
		getContentPane().add(motherPanel, BorderLayout.CENTER);
		
	}
	
	public String getLastWord(String key) {
			FileReader file = null;
		String str = "";
		try {
			file = new FileReader("lastSearchWords.properties");
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
	
	public void setTextArea(JTextArea textarea) {
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
		//MouseListenerインタフェースで実装するのはこのメソッドのみ
		searchField.select(0, searchField.getText().length() );
	}
	
	public void mousePressed(MouseEvent e_ME) {
		
	}
	
	public void mouseReleased(MouseEvent e_ME) {
		
	}
	
	public void mouseEntered(MouseEvent e_ME) {
		
	}
	
	public void mouseExited(MouseEvent eE_ME) {
		
	}
	
	public void actionPerformed(ActionEvent e_AE) {
		try { 
			Object obj = e_AE.getSource();
			if (obj == searchAllButton) {
				currentCaret = textarea.getCaretPosition();
				searchWords = searchField.getText();
				searchText = textarea.getText();
				if (distinguishCase.isSelected() ) {
					searchWordIndex = searchText.indexOf(searchWords);
				} else {
					searchWordIndex = searchText.toUpperCase().indexOf(searchWords.toUpperCase() );
				}
				if (searchWordIndex != -1) {
					textarea.select(searchWordIndex, searchWordIndex + searchWords.length() );
				} else {
					//JOptionPaneをポップさせたい。あとで訂正
					dispose();
					JOptionPane.showMessageDialog(getOwner(), new JLabel("\" " + searchWords + " \"" + "\nは見つかりませんでした", JLabel.CENTER),
					"test", JOptionPane.PLAIN_MESSAGE
					);
					System.out.println(searchWords + " not found.");
				}
				
			} else if (obj == searchNextButton) {
				/*1*/	
				searchWords = searchField.getText();
				/*2*/
				currentCaret = textarea.getCaretPosition();
				/*3*/
				textLength = textarea.getText().length();
				/*4*/
				if (forward.isSelected() ) {
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
						//↑currentCaretも足すことで次のselectメソッドを行う前に
						//検索方向が上か下かによるif分岐をしないで済む
					} else {						//方向:上へ なら
						searchWordIndex = searchText.toUpperCase().lastIndexOf(searchWords.toUpperCase() );
					}
				} else {							//大/小文字の区別:選択かつ
					if (forward.isSelected() == true) {		//方向:下へ なら
						searchWordIndex = searchText.indexOf(searchWords);
						//↑currentCaretも足すことで次のselectメソッドを行う前に
						//検索方向が上か下かによるif分岐をしないで済む
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
				} else {		//含まれていなかったら
					if (wrap.isSelected() == true) {		//折り返し:ありなら
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
		} catch (BadLocationException BLE) {
			System.out.println(BLE);
		}
	}
}
