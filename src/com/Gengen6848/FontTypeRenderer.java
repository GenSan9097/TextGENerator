package com.Gengen6848;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public class FontTypeRenderer extends  DefaultListCellRenderer{

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		// TODO 自動生成されたメソッド・スタブ
		
		JLabel label = (JLabel)super.getListCellRendererComponent(
	            list,value,index,isSelected,cellHasFocus);
		Font font = new Font((String)value, Font.PLAIN, 20);
		label.setFont(font);
		return label;
	}

}
