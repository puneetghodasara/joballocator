package ui.comp;

import javafx.scene.control.TableCell;

public class NumberedCell<T> extends TableCell<T, Integer>{

	public NumberedCell() {
	}

	private final static String blank = new String();
	
	@Override
	protected void updateItem(Integer item, boolean empty) {
		super.updateItem(item, empty);
		if(!empty){
			int i = getTableRow().getIndex();
			setText(String.valueOf(i+1));
		}else{
			setText(blank);
		}
	}
}