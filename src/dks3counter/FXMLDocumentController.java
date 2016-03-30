/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dks3counter;

import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.StageStyle;


/**
 *
 * @author stuby
 */
public class FXMLDocumentController implements Initializable {
    
    //Controls for linking with the FXML document
    @FXML
    private Label lbl_death_display;
    
    private Character loadedCharacter;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadedCharacter = null;
    }
    
    @FXML
    private void menu_NewCharacter() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("New Character");
        dialog.setHeaderText(null);
        dialog.setContentText("Characters Name:");
        dialog.initStyle(StageStyle.UTILITY);
        
        //Traditional way of getting the response value
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String characterName = result.get();
            System.out.println("You name: " + characterName);
            
            HibernateCharacterDAO dao = new HibernateCharacterDAO();
            Character c = new Character(characterName, 0);
            dao.StoreCharacter(c);
        }
    }
    
    @FXML
    private void menu_LoadCharacter() {
        HibernateCharacterDAO dao = new HibernateCharacterDAO();
        List<String> choices = dao.GetAllCharactersName();
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Load Character");
        dialog.setHeaderText(null);
        dialog.setContentText("Select Character:");
        dialog.initStyle(StageStyle.UTILITY);
        
        //Traditional way of getting the response value
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Choice: " + result.get());
            String[] valSplit = result.get().split("-");
            loadCharacter(Integer.parseInt(valSplit[0]));
        }
    }
    
    @FXML
    private void menu_Quit () {
        System.exit(0);
    }
    
    @FXML
    private void menu_About (ActionEvent event) {
        alertDialog(AlertType.INFORMATION, "About DkS3 Helper", "DkS3 Helper by Stuart-Harrison.com", "Version: 1.0");
    }
    
    @FXML
    private void btn_death_plusAction () {
        incrementDeath();
    }
    
    @FXML
    private void btn_death_minusAction () {
        decrementDeath();
    }
    
    @FXML
    private void key_releasedEvent(KeyEvent key) {
        if (key.getCode() == KeyCode.F1) { incrementDeath(); }
        if (key.getCode() == KeyCode.F2) { decrementDeath(); }
    }
    
    private void loadCharacter(int id) {
        HibernateCharacterDAO dao = new HibernateCharacterDAO();
        loadedCharacter = dao.GetCharacterByID(id);
        lbl_death_display.setText(loadedCharacter.deathCounterToString());
    }
    
    private void incrementDeath() {
        if (loadedCharacter != null) {
            loadedCharacter.IncrementDeathCounter();
            lbl_death_display.setText(loadedCharacter.deathCounterToString());
        }
        else {
            alertDialog(AlertType.ERROR, "No Character Loaded", null, "You need to either create a new Character or load an existing Character to use this feature.");
        }
    }
    
    private void decrementDeath() {
        if (loadedCharacter != null) {
            loadedCharacter.DecrementDeathCounter();
            lbl_death_display.setText(loadedCharacter.deathCounterToString());
        }
        else {
            alertDialog(AlertType.ERROR, "No Character Loaded", null, "You need to either create a new Character or load an existing Character to use this feature.");
        }
    }
    
    private void alertDialog(AlertType a, String title, String header, String content) {
        Alert alert = new Alert(a);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
    }
}
