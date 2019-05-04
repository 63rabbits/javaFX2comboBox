package javaFX2comboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ComboBoxController {

	@FXML
	private ComboBox<String> cmb;
	@FXML
	private TextField txf01;
	@FXML
	private TextField txf02;

	@FXML
	private ComboBox<String> cmbHBind;
	@FXML
	private TextField txf01HBind;

	@FXML
	private ComboBox<String> cmbLBind;
	@FXML
	private TextField txf01LBind;
	@FXML
	private TextField txf02LBind;

	private ArrayList<String> cocktailNames = new ArrayList<>();
	private HashMap<String, String> cocktailHmap = new HashMap<>();

	@FXML
	void initialize() {

		// get Cocktail list
		{
			URL url = this.getClass().getResource("res/cocktail.csv");
			OpCsv csv = new OpCsv(url);

			TreeMap<Integer, String[]> map = csv.getCsv();
			Iterator<Integer> it = map.keySet().iterator();
			while (it.hasNext()) {
				int no = it.next();
				String[] words = map.get(no);
				String ename = words[0];
				String jname = words[1];
				if (cocktailHmap.containsKey(ename)) {
					String duplicateKey = ename + " ## duplicate ##";
					cocktailHmap.put(duplicateKey, jname + " (T_T)");
				}
				else {
					cocktailHmap.put(ename, jname);
				}
			}

			Iterator<String> itCocktail = (new TreeSet<>(cocktailHmap.keySet())).iterator(); // sort the key
			while (itCocktail.hasNext()) {
				cocktailNames.add(itCocktail.next());
			}
		}

		// Using Event Handler
		assert cmb != null : "fx:id=\"cmb\" was not injected: check your FXML file 'ComboBox.fxml'.";
		assert txf01 != null : "fx:id=\"txf01\" was not injected: check your FXML file 'ComboBox.fxml'.";
		assert txf02 != null : "fx:id=\"txf02\" was not injected: check your FXML file 'ComboBox.fxml'.";
		// NOTE !!
		this.cmb.getItems().setAll(cocktailNames);
		this.cmb.setUserData(cocktailHmap);
		this.cmb.setValue(cocktailNames.get(0));

		// Using Bind (High-level API)
		assert cmbHBind != null : "fx:id=\"cmbHBind\" was not injected: check your FXML file 'ComboBox.fxml'.";
		assert txf01HBind != null : "fx:id=\"txf01HBind\" was not injected: check your FXML file 'ComboBox.fxml'.";
		this.cmbHBind.getItems().setAll(cocktailNames);
		this.cmbHBind.setValue(cocktailNames.get(0));
		this.txf01HBind.textProperty().bind(this.cmbHBind.valueProperty());

		// Using Bind (Low-level API)
		assert cmbLBind != null : "fx:id=\"cmbLBind\" was not injected: check your FXML file 'ComboBox.fxml'.";
		assert txf01LBind != null : "fx:id=\"txf01LBind\" was not injected: check your FXML file 'ComboBox.fxml'.";
		assert txf02LBind != null : "fx:id=\"txf02LBind\" was not injected: check your FXML file 'ComboBox.fxml'.";
		this.cmbLBind.setUserData(cocktailHmap);
		this.cmbLBind.getItems().setAll(cocktailNames);
		this.cmbLBind.setValue(cocktailNames.get(0));
		this.txf01LBind.textProperty().bind(this.observer01(cmbLBind));
		this.txf02LBind.textProperty().bind(this.observer02(cmbLBind));
	}

	// Using Event Handler
	@FXML
	protected void cmbOnAction(ActionEvent event) {
		this.txf01.setText(this.cmb.getValue());
		@SuppressWarnings("unchecked")
		HashMap<String, String> hmap = (HashMap<String, String>) this.cmb.getUserData();
		// Check HashMap null check because it is started before the setUserData().
		if (hmap != null) {
			this.txf02.setText(hmap.get(this.cmb.getValue()));
		}
	}

	// Using Bind (High-level API)

	// Using Bind (Low-level API)
	private ObjectBinding<String> observer01(ComboBox<String> p) {
		final ComboBox<String> cmb = p;
		ObjectBinding<String> sBinding = new ObjectBinding<String>() {
			{
				super.bind(cmb.valueProperty());
			}

			@Override
			protected String computeValue() {
				return cmb.getValue();
			}
		};
		return sBinding;
	}

	private ObjectBinding<String> observer02(ComboBox<String> p) {
		final ComboBox<String> cmh = p;
		ObjectBinding<String> sBinding = new ObjectBinding<String>() {
			{
				super.bind(cmh.valueProperty());
			}

			@Override
			protected String computeValue() {
				@SuppressWarnings("unchecked")
				HashMap<String, String> hmap = (HashMap<String, String>) cmh.getUserData();
				return hmap.get(cmh.getValue());
			}
		};
		return sBinding;
	}

}
