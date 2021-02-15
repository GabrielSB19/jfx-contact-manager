package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.*;

public class ContactManagerGUI {
	
	private ContactManager contactManager;
	
	public ContactManagerGUI(ContactManager contactManager) {
		this.contactManager = contactManager;
	}
	
    @FXML
    private Pane mainPane;
    
    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button btnSave;
    
    @FXML
    private TableView<Contact> tblMain;

    @FXML
    private TableColumn<Contact, Integer> tblNum;

    @FXML
    private TableColumn<Contact, String> tblName;

    @FXML
    private TableColumn<Contact, String> tblEmail;
    
    public void onTable() {
    	ObservableList<Contact> newList;
    	newList = FXCollections.observableArrayList(contactManager.getContact());
    	
    	tblMain.setItems(newList);
    	tblName.setCellValueFactory(new PropertyValueFactory<Contact,String>("name"));
    	tblEmail.setCellValueFactory(new PropertyValueFactory<Contact,String>("email"));
    }


    @FXML
    void onAdd(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddContact.fxml"));
		
		fxmlLoader.setController(this);    	
		Parent addContactPane = fxmlLoader.load();
    	
		mainPane.getChildren().clear();
    	mainPane.getChildren().setAll(addContactPane);
    }

    @FXML
    void onList(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddList.fxml"));
		
		fxmlLoader.setController(this);
		Parent contactListPane = fxmlLoader.load();
    	
		mainPane.getChildren().clear();
    	mainPane.getChildren().setAll(contactListPane);
    	
    	onTable();
    }
    
    @FXML
    void saveContact(ActionEvent event) {
    	contactManager.addContact(txtName.getText(), txtEmail.getText());
    	
    	txtName.clear();
    	txtEmail.clear();
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Account created");
	    alert.setHeaderText("New Account");
	    alert.setContentText("The new account has been created");
	
	    alert.showAndWait();
    }
    
    @FXML
    void onAbout(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Contact Manager");
	    alert.setHeaderText("Credits");
	    alert.setContentText("Gabriel Suarez / APO II");
	
	    alert.showAndWait();
    }

    @FXML
    void onExportData(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Save File");
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files","*.txt"));
    	File f = fileChooser.showSaveDialog(mainPane.getScene().getWindow());
    	if(f != null) {
    		try {
				contactManager.exportData(f.getAbsolutePath());
				Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Export Contacts");
			    alert.setContentText("The contacts data was exported succesfully");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Export Contacts");
			    alert.setContentText("The contacts data was not exported. An error ocurred");
			    
			    alert.showAndWait();
			}
    	}
    }

    @FXML
    void onImportData(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Data File");
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files","*.txt"));
    	File f = fileChooser.showOpenDialog(mainPane.getScene().getWindow());
    	if (f != null) {
    		try {
				contactManager.importData(f.getAbsolutePath());
				Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("Import Contacts");
			    alert.setContentText("The contacts data was imported succesfully");
			
			    alert.showAndWait();
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
			    alert.setTitle("Import Contacts");
			    alert.setContentText("The contacts data was not imported. An error ocurred");
			    
			    alert.showAndWait();
			}
    	}
    }
}
