package com.Gengen6848;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public class FontStyleRenderer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		// TODO 自動生成されたメソッド・スタブ
		JLabel label = (JLabel)super.getListCellRendererComponent(
	            list,value,index,isSelected,cellHasFocus);
		int style = 0;
		if (value.equals("標準")) {
			style = Font.PLAIN;
		} else if (value.equals("太字")) {
			style = Font.BOLD;
		} else if (value.equals("斜体")) {
			style = Font.ITALIC;
		} else {
			style = Font.BOLD + Font.ITALIC;
		}
		
		Font font = new Font("Monospaced", style, 20);
		label.setFont(font);
		return label;
	}

}
