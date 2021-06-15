package mailSelection;


import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	public Preferences pref;
	


@Override
public void start(Stage stage) throws Exception {
	Preferences pref;
    pref = Preferences.userNodeForPackage(String.class); 
    /*try {
        pref.clear();
    } catch (BackingStoreException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
    }*/
	String isOpen = "no";
	pref = Preferences.userNodeForPackage(String.class); 
	if(pref.get("open", isOpen) == null) {
		isOpen = "no";
	}
	else { isOpen = pref.get("open", isOpen);}
	//isOpen = pref.get("open", isOpen);
	
	 Parent root;
	 Scene scene;
	if(isOpen.equals("yes")) {
	    root = FXMLLoader.load(getClass().getResource("TPMain.fxml"));
	     scene = new Scene(root);
	    stage.setTitle("Nouveaux paramétres");
	    //stage.getIcons().add(new Image("/Images/pss.png"));
	    stage.setScene(scene);
	    stage.show();
	    
		}
	else {
	    root = FXMLLoader.load(getClass().getResource("MyWorkspace.fxml"));
	     scene = new Scene(root);
	    stage.setTitle("Nouveaux paramétres");
	    //stage.getIcons().add(new Image("/Images/pss.png"));
	    stage.setScene(scene);
	    stage.show();
	    
		}
}


/**
 * @param args the command line arguments
 */
public static void main(String[] args) {
    launch(args);
}

}