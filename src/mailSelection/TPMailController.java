package mailSelection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.prefs.Preferences;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TPMailController implements Initializable{
	
	private static String MODULE_NAME ;
	private static String TP_NAME ;
	private static String workspace ;
	private static File file = new File("filo.xml");
	JAXBContext jaxbcontext;
	Students students;
	private Student currentStudent = new Student();
	private Serie currentSerie = new Serie();
	 List<Module> mds = new ArrayList<Module>() ;
	 List<Student> stds = new ArrayList<Student>() ;
	 List<Serie> sris = new ArrayList<Serie>() ;
	 @FXML
	    TextArea codeArea;
	@FXML
	ComboBox<String> studentCombo,moduleCombo,tpCombo;
	@FXML
	Button next,previous;
	@FXML
	private TreeView<String> jtree;
	File filetree;
	File studentFile;
	@FXML
	private TextField fname, lname,email, groupe;
	@FXML
	private TextArea comment;

	
	@FXML public void next() {
		
				int i =0;
				previous.setDisable(false);
		ObservableList<String> path = studentCombo.getItems();
		i = studentCombo.getSelectionModel().getSelectedIndex();
		i++;
		studentCombo.getSelectionModel().select(i);
		if(i< path.size()) {
		studentFile = new File(filetree.getAbsoluteFile() +"\\" + path.get(i));
		if(studentFile.isDirectory())
		jtree.setRoot( getNodesForDirectory(studentFile));	
	}else {next.setDisable(true);}
		}
	
	@FXML public void previous() {
		next.setDisable(false);
		int i =0;
		ObservableList<String> path = studentCombo.getItems();
		i = studentCombo.getSelectionModel().getSelectedIndex();
		i--;
        
		studentCombo.getSelectionModel().select(i);
		if(i> 0) {
		
		studentFile = new File(filetree.getAbsoluteFile() +"\\" + path.get(i));
		if(studentFile.isDirectory())
		jtree.setRoot( getNodesForDirectory(studentFile));	
	}else {previous.setDisable(true);}
		}
	
	@FXML public void send() {
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("mailSelection/mailSelect.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 1136, 557));
            stage.show();
            // Hide this current window (if this is what you want)
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	@FXML public void setting(ActionEvent event) {
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("mailSelection/MyWorkspace.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.show();
            stage.setScene(new Scene(root, 600, 533));
            // Hide this current window (if this is what you want)
            ((Node) event.getSource()).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	@FXML public void showCode() throws IOException {
		 TreeItem<String> name = jtree.getSelectionModel().getSelectedItem();
		 if(name!=null) {
		 String fileName = name.getValue();
		 while(name != jtree.getRoot()){
		 fileName = name.getParent().getValue() +"\\"+ fileName;
		 name = name.getParent();
		 }
		 File file = new File(workspace+"\\"+MODULE_NAME+"\\"+TP_NAME+"\\"+ fileName+"\\");
		 if (file.isFile()) {
		 Scanner sc = new Scanner(file);
         codeArea.setText(sc.nextLine());
         sc.close();}
		 else {}
		 }
		 else {}
	}
	
	@FXML public void studentSelected() {
		filetree = new File(workspace +MODULE_NAME+ "\\" + TP_NAME);
		if(filetree.exists()) {
		String s = studentCombo.getValue();
		String linkselected = filetree.getAbsoluteFile() +"\\" + s;
		studentFile = new File(linkselected);
		if(studentFile.exists() && studentFile.isDirectory()) {
        jtree.setRoot( getNodesForDirectory(studentFile));
        next.setDisable(false);
        previous.setDisable(false);
        fillTexts();}
		else {

		}
		}
	}
	

	 @FXML
	 public void listModules(){
		 ObservableList<String> moduleListDirectory  = FXCollections.observableArrayList();
		 File filemodules = new File(workspace);
		 for(File fi : filemodules.listFiles()) {
			 if(fi.isDirectory() && fi.exists())
				 moduleListDirectory.add(fi.getName());
		 }
		 moduleCombo.setItems(moduleListDirectory);
		 
	 }
	 

	 @FXML
	 public void listSeries() { 
		 ObservableList<String> serieListDirectory  = FXCollections.observableArrayList();
		 System.out.println(MODULE_NAME);
		 File fileseries = new File(workspace  + MODULE_NAME + "\\" );
		 for(File fi : fileseries.listFiles()) {
			 serieListDirectory.add(fi.getName());
		 }
		 tpCombo.setItems(serieListDirectory);
		 
	 }
	

	 @FXML
	 public void listStudents() { 
		 ObservableList<String> studentListDirectory  = FXCollections.observableArrayList();
		 filetree = new File(workspace +MODULE_NAME+ "\\" + TP_NAME);
		 if(filetree!=null && filetree.isDirectory()) {
		 for(File fi : filetree.listFiles()) {
			 studentListDirectory.add(fi.getName());
		 }
		 studentCombo.setItems(studentListDirectory);
		 }
	 }
	 
	 @FXML
		public void addStudent() {
		 Marshaller m;
			try {
				 jaxbcontext = JAXBContext.newInstance(Students.class);
				 Unmarshaller jaxbUnmarshaller = jaxbcontext.createUnmarshaller();
				 students = (Students) jaxbUnmarshaller.unmarshal(file); 
				 if(!checkStudent(studentFile.getName()) ) {
					ArrayList<Module> modules = new ArrayList<>();
					 currentStudent = new Student(studentFile.getName(),
								" ", " ", 
								" ", " ",
								modules);
					 students.getStudents().add(currentStudent);
					 stds.add(currentStudent);
						m = jaxbcontext.createMarshaller();
						m.marshal(students,  file);
						System.out.println("new student created");
					}
				 
				
			    if(!checkModule(studentFile.getName(),MODULE_NAME)){
			    	 jaxbcontext = JAXBContext.newInstance(Students.class);
					 Unmarshaller jaxbUnmarshaller1 = jaxbcontext.createUnmarshaller();
					 students = (Students) jaxbUnmarshaller1.unmarshal(file); 
					 ArrayList<Serie> series = new ArrayList<>();
					 Module md = new Module(currentStudent.getStudentid(),currentStudent.getModules().size()+1, MODULE_NAME,series);
					 for(int i = 0; i<students.getStudents().size(); i++) {
						 if(students.getStudents().get(i).getStudentid().equals(studentFile.getName())) {
							 students.getStudents().get(i).getModules().add(md);
							 mds.add(md);
							currentStudent = students.getStudents().get(i);
							
						 }
						 
					 }
					 m = jaxbcontext.createMarshaller();
						m.marshal(students,  file);
						
						System.out.println("new module created");
				 }
			    if(!checkSerie(studentFile.getAbsolutePath().toString())){
					
					 
					 Serie ser = new Serie(studentFile.getName(),1 , TP_NAME, "empty", "no","no", studentFile.getAbsolutePath().toString());
					 for(int i = 0; i<students.getStudents().size(); i++) {
						 if(students.getStudents().get(i).getStudentid().equals(studentFile.getName())) {
						 for (int j =0; j<students.getStudents().get(i).getModules().size(); j++) {
							 if (students.getStudents().get(i).getModules().get(j).getName().equals(MODULE_NAME)) {
								 ArrayList<Serie> series = new ArrayList<>();
								 students.getStudents().get(i).getModules().get(j).setSeries(series);
								 students.getStudents().get(i).getModules().get(j).getSeries().add(ser);
								 currentStudent = students.getStudents().get(i);
								 stds.add(currentStudent);
								 sris.add(ser);
							 }}
						 }
					 }
					 m = jaxbcontext.createMarshaller();
						//m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						m.marshal(students,  file);
						System.out.println("new serie created");
					 
				 }
				
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				fillTexts();
			}
		 
	 }
	 
	 	@FXML
		public void editStudent() {
	 		
	 		currentStudent.setFirstname(fname.getText());
	 		currentStudent.setLastname(lname.getText());
	 		currentStudent.setEmail(email.getText());
	 		currentStudent.setTp(groupe.getText());
			Marshaller m;
			try {
				 jaxbcontext = JAXBContext.newInstance(Students.class);
				 Unmarshaller jaxbUnmarshaller = jaxbcontext.createUnmarshaller();
				 students = (Students) jaxbUnmarshaller.unmarshal(file); 
				 for(int i=0; i<students.getStudents().size(); i++) {
					 if(students.getStudents().get(i).getStudentid().equals(currentStudent.getStudentid())) {
						 students.getStudents().get(i).setFirstname(fname.getText());
						 students.getStudents().get(i).setLastname(lname.getText());
						 students.getStudents().get(i).setEmail(email.getText());
						 students.getStudents().get(i).setTp(groupe.getText());
						 for (int j =0; j<students.getStudents().get(i).getModules().size(); j++) {
							 if (students.getStudents().get(i).getModules().get(j).getName().equals(MODULE_NAME) ) {
								 for(int k=0; k<students.getStudents().get(i).getModules().get(j).getSeries().size();k++) {
									 if (students.getStudents().get(i).getModules().get(j).getSeries().get(k).getPath().equals(studentFile.getAbsolutePath())) {
										 currentSerie.setComment(comment.getText());
										 students.getStudents().get(i).getModules().get(j).getSeries().get(k).setComment(comment.getText());
									 }
								 }
							 }
						 }
					 }
					
				 }
				 
				m = jaxbcontext.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				m.marshal(students,  file);
				
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
		}
	 	
		public boolean checkModule(String studentid, String MODULE_NAME) {
			for (int i = 0; i<mds.size(); i++) {
				if(mds.get(i).getStudentid().equals(studentid) && mds.get(i).getName().equals(MODULE_NAME)) { return true;}
			}
			return false;
		}
		 	
	 	
	public boolean checkStudent(String studentid) {
		for (int i = 0; i<stds.size(); i++) {
			if(stds.get(i).getStudentid().equals(studentid)) { currentStudent = stds.get(i); return true;}
		}
		return false;
	}
	 	
	 	
	
	public boolean checkSerie(String path) {//check in all series if there is a student with the same path
		for(int i=0; i<sris.size(); i++) {
			if(sris.get(i).getPath().equals(path) && sris.get(i).getStudentid().equals(currentStudent.getStudentid())) {
				for(int j=0; j<stds.size();j++){
					if(sris.get(i).getStudentid().equals( stds.get(j).getStudentid())) {
						currentSerie = sris.get(i);
						System.out.println(currentSerie.getTitle());
						return true;
					}
				}		
			}
		} return false;
		
	}
	
	@FXML public void tpSelected() {
		String tp = tpCombo.getValue();		
		System.out.println(tp);
		TP_NAME = tp;
		filetree = new File(workspace +MODULE_NAME+ "\\" + TP_NAME);
		if(tp!=null && filetree.isDirectory()) {
		listStudents();
		treeController();
		fillTexts();
		}
	
	}
	
	@FXML public void moduleSelected() {
		tpCombo.setValue(null);
		String module = moduleCombo.getValue();		
		System.out.println(moduleCombo.getValue());
		MODULE_NAME = module;
		//filetree = new File(workspace +MODULE_NAME+ "\\" + TP_NAME);
		//listStudents();
		listSeries();
		//treeController();
	//	fillTexts();
	
	}
	
	@FXML void fillTexts() {
		
		fname.setText("");
		lname.setText("");
		email.setText("");
		groupe.setText("");
		comment.setText("");
		//moduleCombo.setValue(null);
		//tpCombo.setValue(null);
		if (checkStudent(studentFile.getName()) ){ 
			fname.setText(currentStudent.getFirstname());
			lname.setText(currentStudent.getLastname());
			email.setText(currentStudent.getEmail());
			groupe.setText(currentStudent.getTp());
			if(checkModule(studentFile.getName(),MODULE_NAME))
			moduleCombo.setValue(MODULE_NAME);
			else {addStudent(); System.out.println("hna");}
			if(checkSerie(studentFile.getAbsolutePath().toString())) {
			comment.setText(currentSerie.getComment());
			tpCombo.setValue(TP_NAME);}
			else{addStudent(); System.out.println("hna");}

		}
	
		else {addStudent();}
		
		
	}
	
	public TreeItem<String> getNodesForDirectory(File directory) { //Returns a TreeItem representation of the specified directory
        TreeItem<String> root = new TreeItem<String>(directory.getName());
        if (directory.isDirectory()) {
        for(File f : directory.listFiles()) {
            if(f.isDirectory()) { //Then we call the function recursively
            	
                root.getChildren().add(getNodesForDirectory(f));
                
            } else {
                root.getChildren().add(new TreeItem<String>(f.getName()));
               
            }
        }

        return root;}
        else {
        	return null;
        }
    }
	@FXML 
	private void treeController(){
		if (filetree.isDirectory()) {
		String[] path = filetree.list();
		if(path.length != 0) {
		studentFile = new File(filetree.getAbsoluteFile() +"\\" + path[0]);
        jtree.setRoot( getNodesForDirectory(studentFile));
        studentCombo.setValue(studentFile.getName());}
      //  moduleCombo.setValue(MODULE_NAME);
       // tpCombo.setValue(TP_NAME);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Preferences pref;
		pref = Preferences.userNodeForPackage(String.class); 
		/*try {
			pref.clear();
		} catch (BackingStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/

		MODULE_NAME = pref.get("module",MODULE_NAME);
		TP_NAME = pref.get("tp",TP_NAME);
		workspace = pref.get("directory",workspace)+"\\";
		listModules();
		listSeries() ;	
		listStudents();
		treeController();


		 
		 try {
				 jaxbcontext = JAXBContext.newInstance(Students.class);
				Unmarshaller jaxbUnmarshaller = jaxbcontext.createUnmarshaller();
				 students = (Students) jaxbUnmarshaller.unmarshal(file);

				for(int i=0;i<students.getStudents().size();i++) {
					 stds.add(new Student(students.getStudents().get(i).getStudentid(),
			        		  students.getStudents().get(i).getLastname(),
			        		  students.getStudents().get(i).getFirstname(),
			        		  students.getStudents().get(i).getEmail(),
			        		  students.getStudents().get(i).getTp(),
			        		  students.getStudents().get(i).getModules()));
					          
					    
					 for (int j=0; j<students.getStudents().get(i).getModules().size();j++) {
						 mds.add(new Module(students.getStudents().get(i).getStudentid(),
								 	students.getStudents().get(i).getModules().get(j).getModuleid(),
						    		students.getStudents().get(i).getModules().get(j).getName(),
						    		students.getStudents().get(i).getModules().get(j).getSeries()
						    		));	   
						 
						 for(int k = 0; k<students.getStudents().get(i).getModules().get(j).getSeries().size();k++){
							 sris.add(new Serie (students.getStudents().get(i).getStudentid(),
									 	students.getStudents().get(i).getModules().get(j).getModuleid(),
									 	students.getStudents().get(i).getModules().get(j).getSeries().get(k).getTitle(),
									 	students.getStudents().get(i).getModules().get(j).getSeries().get(k).getComment(),
									 	students.getStudents().get(i).getModules().get(j).getSeries().get(k).getCompleted(),
									 	students.getStudents().get(i).getModules().get(j).getSeries().get(k).getAlreadySent(),
									 	students.getStudents().get(i).getModules().get(j).getSeries().get(k).getPath()
									 	
									 	));
						 }
								    
					 }
					
				}

	}  catch (Exception e) {
        e.printStackTrace();
    }
		 
		 fillTexts();
		 }
	
	
}
