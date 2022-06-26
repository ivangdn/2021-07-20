/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Giornalista;
import it.polito.tdp.yelp.model.Model;
import it.polito.tdp.yelp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnUtenteSimile"
    private Button btnUtenteSimile; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="cmbUtente"
    private ComboBox<User> cmbUtente; // Value injected by FXMLLoader

    @FXML // fx:id="txtX1"
    private TextField txtX1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	String minReviewS = txtN.getText();
    	try {
    		int minReview = Integer.parseInt(minReviewS);
    		Integer anno = cmbAnno.getValue();
    		if(anno==null) {
    			txtResult.setText("Devi selezionare un anno\n");
        		return;
    		}
    		
    		String msg = model.creaGrafo(minReview, anno);
    		txtResult.setText(msg);
    		
    		cmbUtente.getItems().clear();
    		cmbUtente.getItems().addAll(model.getUsers());
    		
    	} catch(NumberFormatException e) {
    		txtResult.setText("Devi inserire un numero 'n' valido\n");
    		return;
    	}
    	
    }

    @FXML
    void doUtenteSimile(ActionEvent event) {
    	txtResult.clear();
    	
    	User u = cmbUtente.getValue();
    	if(u==null) {
    		txtResult.setText("Devi selezionare un utente\n");
    		return;
    	}
    	List<User> vicini = model.utentiPiuSimili(u);
    	txtResult.appendText("Utenti più simili a "+u+" :\n\n");
    	for(User user : vicini) {
    		txtResult.appendText(user.toString()+"\n");
    	}
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	
    	int x1;
    	int x2;
    	try {
    		x1 = Integer.parseInt(txtX1.getText());
    		x2 = Integer.parseInt(txtX2.getText());
    	} catch(NumberFormatException e) {
    		txtResult.setText("x1 e x2 devono essere valori numerici interi");
    		return;
    	}
    	
    	if(x2 > model.getUsers().size()) {
    		txtResult.setText("x2 deve essere minore o uguale al numero di utenti");
    		return;
    	}
    	
    	if(x1 > x2) {
    		txtResult.setText("x1 deve essere minore di x2");
    		return;
    	}
    	
    	this.model.simula(x1, x2);
    	txtResult.appendText("Numero di giorni: "+model.getNumeroGiorni()+"\n");
    	
    	for(Giornalista g : model.getGiornalisti()) {
    		txtResult.appendText("Giornalista "+g.getId()+": "+g.getNumeroIntervistati()+" intervistati\n");
    	}
    	
    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUtenteSimile != null : "fx:id=\"btnUtenteSimile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbUtente != null : "fx:id=\"cmbUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX1 != null : "fx:id=\"txtX1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        for(int anno=2005; anno<=2013; anno++) {
        	cmbAnno.getItems().add(anno);
        }
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
