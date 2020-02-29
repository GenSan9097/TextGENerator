package com.Gengen6848;

public class MyTabForText extends MyTabManager {

	public MyTabForText(EditorConsole console, String title) {
		super(console, title);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void setTitle(String title) {
		// TODO 自動生成されたメソッド・スタブ
		int index = tabpane.getSelectedIndex();
		getLabelTabTitleAt(index).setText(title);
	}

}
