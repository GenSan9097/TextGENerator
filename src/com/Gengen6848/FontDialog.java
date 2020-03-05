package com.Gengen6848;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class FontDialog extends JDialog implements ActionListener {
	
	public Font[] availableFont;
	
	public Font dialogFont;
	
	public JPanel panel_mother;
	public JPanel panel_interface;
	public JPanel panel_okCancel;
	public JPanel panel_fontType;
	public JPanel panel_fontStyle;
	public JPanel panel_fontSize;
	public JPanel panel_sample;
	
	public JLabel label_fontType;
	public JLabel label_fontStyle;
	public JLabel label_fontSize;
	
	public JComboBox combo_fontType;
	public JComboBox combo_fontStyle;
	public JComboBox combo_fontSize;
	
	public String[] String_availableFont;
	public String[] label_fontStyleList;
	
	public JLabel[] label_sample;
	
	public JButton button_ok;
	public JButton button_cancel;
	
	public JTextArea textarea;
	
	public FontDialog(JFrame frame, JTextArea textarea) {
		super(frame, "フォント");
		setBounds(frame.getWidth() / 2 - 50, frame.getHeight() / 2 - 50, 500, 630);
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setResizable(false);
		
		this.textarea = textarea;
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    //availableFont = ge.getAllFonts();
	    //String_availableFont = new String[availableFont.length];
		String_availableFont = ge.getAvailableFontFamilyNames();
		availableFont = new Font[String_availableFont.length];
	    for (int i = 0; i < String_availableFont.length; i++) {
	    	availableFont[i] = new Font(String_availableFont[i], Font.PLAIN, 14);
	    	//String_availableFont[i].setFont(availableFont[i]);
	    }
	    
	    dialogFont = new Font("Monospaced", Font.PLAIN, 14);
		
	    panel_mother = new JPanel(new BorderLayout() );
	    panel_okCancel = new JPanel(new FlowLayout() );
		panel_interface = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		panel_interface.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		
		panel_fontType = new JPanel();
		panel_fontType.setLayout(new BoxLayout(panel_fontType, BoxLayout.Y_AXIS));
		panel_fontStyle = new JPanel();
		panel_fontStyle.setLayout(new BoxLayout(panel_fontStyle, BoxLayout.Y_AXIS) );
		panel_fontSize = new JPanel();
		panel_fontSize.setLayout(new BoxLayout(panel_fontSize, BoxLayout.Y_AXIS) );
		panel_sample = new JPanel();
		panel_sample.setLayout(new BoxLayout(panel_sample, BoxLayout.X_AXIS));
		//panel_sample.setBorder();
		
		label_fontType = new JLabel("フォント名");
		label_fontType.setFont(dialogFont);
		label_fontStyle = new JLabel("フォントスタイル");
		label_fontStyle.setFont(dialogFont);
		label_fontSize = new JLabel("フォントサイズ");
		label_fontSize.setFont(dialogFont);
		
		combo_fontType = new JComboBox(String_availableFont);
		combo_fontType.setRenderer(new FontTypeRenderer() );
		Font currentFont = textarea.getFont();
		combo_fontType.setSelectedItem(currentFont.getName() );
		combo_fontType.setFont(textarea.getFont() );
		combo_fontType.addActionListener(this);
		combo_fontType.setPreferredSize(new Dimension(150, 25) );
		
		label_fontStyleList = new String[4];
		label_fontStyleList[0] = new String("標準");
		//label_fontStyleList[0].setFont(dialogFont.deriveFont(Font.PLAIN) );
		label_fontStyleList[1] = new String("太字");
		//label_fontStyleList[1].setFont(dialogFont.deriveFont(Font.BOLD) );
		label_fontStyleList[2] = new String("斜体");
		//label_fontStyleList[2].setFont(dialogFont.deriveFont(Font.ITALIC) );
		label_fontStyleList[3] = new String("太字 斜体");
		//label_fontStyleList[3].setFont(dialogFont.deriveFont(Font.BOLD + Font.ITALIC) );
		combo_fontStyle = new JComboBox(label_fontStyleList);
		combo_fontStyle.setRenderer(new FontStyleRenderer() );
		combo_fontStyle.setFont(dialogFont);
		combo_fontStyle.addActionListener(this);
		combo_fontStyle.setPreferredSize(new Dimension(50, 25) );
		
		
		combo_fontSize = new JComboBox(
				new String[] {"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28",
						"36", "48", "72"} );
		combo_fontSize.setFont(dialogFont);
		combo_fontSize.addActionListener(this);
		
		label_sample = new JLabel[7];
		label_sample[0] = new JLabel("A");
		label_sample[1] = new JLabel("a");
		label_sample[2] = new JLabel("あ");
		label_sample[3] = new JLabel("ア");
		label_sample[4] = new JLabel("安");
		label_sample[5] = new JLabel("0");
		label_sample[6] = new JLabel("1");
		for(JLabel sample : label_sample) {
			sample.setFont(textarea.getFont() );
			panel_sample.add(sample);
		}
		
		
		panel_fontType.add(label_fontType);
		panel_fontType.add(combo_fontType);
		panel_fontStyle.add(label_fontStyle);
		panel_fontStyle.add(combo_fontStyle);
		panel_fontSize.add(label_fontSize);
		panel_fontSize.add(combo_fontSize);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		//gbc.gridheight = 2;
		gbc.weightx = 1.0d;
		gbc.weighty = 1.8d;
		layout.setConstraints(panel_fontType, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		//gbc.gridheight = 1;
		gbc.weightx = 1.0d;
		gbc.weighty = 1.0d;
		layout.setConstraints(panel_fontStyle, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		//gbc.gridheight = 1;
		gbc.weightx = 1.0d;
		gbc.weighty = 1.0d;
		layout.setConstraints(panel_fontSize, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		//gbc.gridheight = 1;
		gbc.weightx = 1.0d;
		gbc.weighty = 1.0d;
		layout.setConstraints(panel_sample, gbc);
		
		
		panel_interface.add(panel_fontType);
		panel_interface.add(panel_fontStyle);
		panel_interface.add(panel_fontSize);
		panel_interface.add(panel_sample);
		
		panel_mother.add(panel_interface, BorderLayout.CENTER);
		
		button_ok = new JButton("OK");
		button_cancel = new JButton("キャンセル");
		button_ok.setFont(dialogFont);
		button_cancel.setFont(dialogFont);
		button_ok.addActionListener(this);
		button_cancel.addActionListener(this);
		
		panel_okCancel.add(button_ok);
		panel_okCancel.add(button_cancel);
		panel_mother.add(panel_okCancel, BorderLayout.SOUTH);
		
		getContentPane().add(panel_mother, BorderLayout.CENTER);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Object obj = e.getSource();
		if (obj == combo_fontType) {
			int select = combo_fontType.getSelectedIndex();
			Font selectedFont = new Font(String_availableFont[select], Font.PLAIN, 14);
			if (select != -1) {
				combo_fontType.setFont(selectedFont);
				for(JLabel sample : label_sample) {
					sample.setFont(selectedFont);
				}
			}
		} else if (obj == combo_fontStyle) {
			int select = combo_fontStyle.getSelectedIndex();
			Font selectedFont = new Font(String_availableFont[combo_fontType.getSelectedIndex()], select, 14);
			if (select != -1) {
				for (JLabel sample : label_sample) {
					sample.setFont(selectedFont);
				}
			}
		} else if (obj == combo_fontSize) {
			int select = combo_fontSize.getSelectedIndex();
			System.out.println(select);
			select = fixFontSizeValue(select);
			Font selectedFont = new Font(String_availableFont[combo_fontType.getSelectedIndex()], combo_fontStyle.getSelectedIndex(), select);
			if (select != -1) {
				for (JLabel sample : label_sample) {
					sample.setFont(selectedFont);
				}
			}
		} else if (obj == button_ok) {
			
		} else {
			
		}
	}
	
	public int fixFontSizeValue (int unfixedValue) {
		int fixedValue;
		
		if (unfixedValue < 4) {
			fixedValue = unfixedValue + 8;
			System.out.println(fixedValue + "\na");
		} else if (unfixedValue < 13) {
			fixedValue = (unfixedValue + 2) * 2;
			System.out.println(fixedValue + "\nb");
		} else {
			int x = unfixedValue - 13;
			fixedValue = 36 + 12 * x;
			System.out.println(fixedValue + "\nc");
		}
		
		return fixedValue;
	}
	
	public void setTextarea(JTextArea textarea) {
		this.textarea = textarea;
	}
	
}
