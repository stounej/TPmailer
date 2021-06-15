package mailSelection;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;


public class myWorkspace implements Initializable {
	
	public static String matierOfWorkspace;
	public static String tpOfWorkspace;
	public static String selectedDirectory;
	public static Preferences pref;
	@FXML private TextField selectedDir;
	@FXML static private DirectoryChooser directoryChooser = new DirectoryChooser();
	@FXML private Accordion accordion;
	@FXML private TitledPane matiere,tpList;
	@FXML BorderPane mainPane;
	@FXML Button dirButton,tpContinuer,matContinuer;
	@FXML ComboBox<String> matiers, tpgroup;
	@FXML private CheckBox neverShow;
	
	static { directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			//titledpanes = accordion.getPanes();
	
	}

	@FXML void begin(ActionEvent event) {
		
		Parent root;
		if(neverShow.isSelected()) {
		String isOpen = "yes";
		pref = Preferences.userNodeForPackage(String.class);
		pref.put("open", isOpen);
}
	if(selectedDirectory != null || matierOfWorkspace != null || tpOfWorkspace != null) {
		pref = Preferences.userNodeForPackage(String.class);
	
		pref.put("directory", selectedDirectory);
		pref.put("module", matierOfWorkspace);
		pref.put("tp", tpOfWorkspace);

        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("mailSelection/TPMain.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 763, 577));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	else {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setContentText("Ooops, you should enter a valid parameters");

		alert.showAndWait();
		
	}
	}
	@FXML void dirChoose() {
		 Window primaryStage = null;
		File selectDirect = directoryChooser.showDialog(primaryStage);
		if(selectDirect==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setContentText("Ooops, you should enter a valid pathname");

			alert.showAndWait();
		}
		else {		selectedDirectory = selectDirect.getAbsolutePath();}
		if(selectedDirectory != null) {
		selectedDir.setText(selectedDirectory);
		dirButton.setDisable(false);
		}

		
	}
	private boolean expandNext(Accordion acc) {
	    int index = acc.getPanes().indexOf(acc.getExpandedPane());
	    int newIndex = Math.min(index + 1, acc.getPanes().size() );
	    if (acc.getPanes().size() > newIndex) {
	    acc.setExpandedPane(acc.getPanes().get(newIndex));
	    return true;
	    }
	    else {
	    	return false;
	    }
	}
	
	@FXML private void continuer() throws IOException {
		if (selectedDir.getText().equals(null) || selectedDir.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setContentText("Ooops, you should enter a valid pathname");

			alert.showAndWait();
		}

		
		if(accordion.getExpandedPane() == null) {
		accordion.setExpandedPane(accordion.getPanes().get(0));
		matiereslistDirectories();
		}
		else {
		if (expandNext(accordion)) {
			switch(accordion.getPanes().indexOf(accordion.getExpandedPane())) {
			case 1 :
				System.out.println(accordion.getPanes().indexOf(accordion.getExpandedPane()));
				tpOfWorkspace = tpgroup.getValue();
				
				
				if(matierOfWorkspace!=null && tpOfWorkspace!=null) {
				matiere.setText("Matiére: "+ matierOfWorkspace+"Groupe de TP: "+tpOfWorkspace);
				System.out.println(accordion.getPanes().indexOf(accordion.getExpandedPane()));
				try (Stream<Path> paths = Files.walk(Paths.get(selectedDirectory +
				"\\" +matierOfWorkspace+ "\\" + tpOfWorkspace+"\\"))) {
				    paths
				        .filter(Files::isRegularFile).peek( e ->
				        	tpContinuer.setDisable(false)
				        );;
				}
				break;	
			}
			
			}}
		
		else {
			accordion.setExpandedPane(null);
		}
		}
	}
	

	
	@FXML private void matiereslistDirectories() {
		
		File file = new File(selectedDirectory+ "\\");
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		ObservableList<String> matierList = FXCollections.observableArrayList(directories);
		matiers.setItems(matierList);
		
	}
	
	@FXML private void tplistDirectories() {
		if(matiers.getValue()!=null) {
		matierOfWorkspace = matiers.getValue();
		
		
		
		File file = new File(selectedDirectory+  "\\" + matierOfWorkspace);
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		ObservableList<String> tpList = FXCollections.observableArrayList(directories);
		tpgroup.setItems(tpList);

	}
	}

		
		
		@FXML private void newMatier() {
			TextInputDialog td = new TextInputDialog("Veuillez entrer la matiere.");
			
			td.setHeaderText("Graphes");
			td.showAndWait();
			File theDir = new File(selectedDirectory + "\\" + td.getEditor().getText()+"\\");
			if (!theDir.exists()){
			    theDir.mkdirs();
			    matiereslistDirectories();
			}	
		  
		}
		@FXML private void newTp() {
			TextInputDialog td = new TextInputDialog("Veuillez entrer le tp.");
			td.setHeaderText("TP");
			td.showAndWait();
			File theDir = new File(selectedDirectory +"\\" +matierOfWorkspace+ "\\" + td.getEditor().getText()+"\\");
			if (!theDir.exists()){
			    theDir.mkdirs();
			    tplistDirectories();
			}	
		  
		}
		
		@FXML private void importfile() {
			
			 Window primaryStage = null;
			 final DirectoryChooser fileChooser = new DirectoryChooser();
             File files = fileChooser.showDialog(primaryStage);
             File path = new File(selectedDirectory+"\\" +matierOfWorkspace+"\\" +tpOfWorkspace+"\\" );
             try {
            	 FileUtils.copyDirectory(files, path);
			} catch (IOException e) {
				e.printStackTrace();
			}
             if(files!=null) {
            	 tpContinuer.setDisable(false);
             }
		}
		
		@FXML private void replacefile() {
			 Window primaryStage = null;
			 final DirectoryChooser fileChooser = new DirectoryChooser();
             File files = fileChooser.showDialog(primaryStage);
             File path = new File(selectedDirectory+"\\" +matierOfWorkspace+"\\" +tpOfWorkspace+"\\" );
             try {
            	 FileUtils.deleteDirectory(path);
            	 FileUtils.copyDirectory(files, path);
			} catch (IOException e) {
				e.printStackTrace();
			}
             if(files!=null) {
            	 tpContinuer.setDisable(false);
             }
		}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		File fff = new File(System.getProperty("user.home"));
		System.out.println(fff.getAbsolutePath());
		pref = Preferences.userNodeForPackage(String.class);
		selectedDirectory = pref.get("directory", selectedDirectory);
		selectedDir.setText(selectedDirectory);
		if(selectedDirectory != null) {dirButton.setDisable(false);
		matierOfWorkspace = pref.get("module", matierOfWorkspace);
		tpgroup.setDisable(false);}
		matiers.setValue(matierOfWorkspace);
		if(matierOfWorkspace != null) matContinuer.setDisable(false);
		tpOfWorkspace = pref.get("tp", tpOfWorkspace);
		tpgroup.setValue(tpOfWorkspace);
		if(tpOfWorkspace != null) tpContinuer.setDisable(false);
		 matiers.valueProperty().addListener((obs, oldItem, newItem) -> {
	         if (newItem == null) {
	           //  label.setText("");
	         } else {
	        	 tpgroup.setDisable(false);
	        	 tplistDirectories();
	         }
	     });
		 tpgroup.valueProperty().addListener((obs, oldItem, newItem) -> {
	         if (newItem == null) {
	           //  label.setText("");
	         } else {
	        	 matContinuer.setDisable(false);
	         }
	     });
	
	}
	

}
