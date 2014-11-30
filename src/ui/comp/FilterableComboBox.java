package ui.comp;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class FilterableComboBox<T> extends ComboBox<T>{
	
	public FilterableComboBox(int startFilterAt) {
		super();
		
		// Filterable component must be editable
		this.setEditable(true);
		
		// On anything typed, as it might not be the exact object from items,
		// Clear it
		this.getEditor().setOnKeyTyped(e->{
			this.getSelectionModel().clearSelection();
			// TODO It is clearing on focus as well
			// Make sure it doesn't
		});
		
		// Add listener to filter it
		this.getEditor().textProperty().addListener((items, oldVal, newVal)->{

			if(this.getSelectionModel().getSelectedIndex()!=-1){
				T selectedItem = this.getSelectionModel().getSelectedItem();
	    		if(newVal!=null && selectedItem!=null &&
	    				newVal.equals(selectedItem.toString())){
	    			return;
	    		}	
			}
    		
    		String text = this.getEditor().getText();
    		this.show();
    		if(text.length()<startFilterAt)
    			return;
    		this.getItems().clear();
    		this.getItems().addAll(filteredItems(text));
    		return;
    	});
	}
	
	// How to filter is user dependent
	public ObservableList<T> filteredItems(String userText){
		return getItems();
	}
	
}
