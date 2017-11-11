package planet.detail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import planet.builders.PlanetBuilder;
import planet.delegators.Planet;

public class PlanetController implements Initializable {
    @FXML private ImageView planetImage;
    
    @FXML private Button selectImageButton;
    
    @FXML private TextField planetName;
    @FXML private TextField planetDiameterKM;
    @FXML private TextField planetDiameterM;
    @FXML private TextField planetMeanSurfaceTempC;
    @FXML private TextField planetMeanSurfaceTempF;
    @FXML private TextField planetNumberOfMoons;
    
    @FXML private Label fancyPlanetName;
    
    private Planet planet;
        
	@Override
	public void initialize( URL arg0, ResourceBundle arg1 ) {
		planet = new Planet();
		setPlanetImage("images/no_image.png");
	}

    @FXML
    void selectImage( ActionEvent event ) {
    		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(new Stage());
		
		if (file == null)
			return;

		setPlanetImage(file.getAbsolutePath());
    }
    
    private void setPlanetImage( String imagePath ) {
    		planet.setPlanetImage(imagePath);
		planetImage.setImage(planet.getPlanetImage());
		planetImage.setId(planet.getPlanetImagePath());
    }

    @FXML
    void loadPlanet( ActionEvent event ) {
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("OK to Overwrite?");
	    	alert.setContentText("Are you sure you want to overwrite the field values?");
	    	alert.showAndWait();
	    	
	    	if (alert.getResult() == ButtonType.CANCEL)
	    		return;
	    	
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.getExtensionFilters().add(new ExtensionFilter("TXT files", "*.txt"));
	    	File file = fileChooser.showOpenDialog(new Stage());
	    	if (file == null)
	    		return;
	    	
	    	loadPlanetInformationFromFile(file);
//	    	new PlanetBuilder();
    }
    
    public void loadPlanetInformationFromFile( File file ) {
	    	try 	{
	    		readFromFile(file);
	    	}
	    	catch (FileNotFoundException e) {
	    		planet.showErrorAlert("Error: file not found\n" + e.getLocalizedMessage());
	    	}
	    	catch (NoSuchElementException e) {
	    		planet.showErrorAlert("Error: could not load planet information\n" + e.getLocalizedMessage());
	    	}
    }
    
    private void readFromFile( File file ) throws FileNotFoundException {
		Scanner scanner = new Scanner(file);
		setPlanetImage(scanner.nextLine());
		readPlanetName(scanner.nextLine());
		readPlanetDiameter(scanner.nextLine());
		readPlanetTemperature(scanner.nextLine());
		readPlanetNumberOfMoons(scanner.nextLine());
		scanner.close();
    }
    
    private void readPlanetName( String name ) {
    		planet.savePlanetName(name);
    		planetName.setText(planet.getPlanetName());
	    		fancyPlanetName.setText(planet.getPlanetName());
    }
    
    private void readPlanetDiameter( String diameterKM ) {
	    	if (diameterKM.equals(""))
	    		return;
    		diameterKM = diameterKM.replaceAll("[.,]", "");
	    	planet.savePlanetDiameter(diameterKM);    	
	    	planetDiameterM.setText( planet.getPlanetDiameterM() );
	    	planetDiameterKM.setText( planet.getPlanetDiameterKM() );
    }
    
    private void readPlanetTemperature( String temperatureC ) {
	    	if (temperatureC.equals(""))
	    		return;
	    	
	    	planet.savePlanetMeanSurfaceTemp(temperatureC);
	    	planetMeanSurfaceTempC.setText(planet.getPlanetMeanSurfaceTempC());
	    	planetMeanSurfaceTempF.setText(planet.getPlanetMeanSurfaceTempF());
    }
    
    private void readPlanetNumberOfMoons( String numberOfMoons ) {
	    	if (numberOfMoons.equals(""))
	    		return;
	    	
	    	planet.savePlanetNumberOfMoons(numberOfMoons);
	    	planetNumberOfMoons.setText(planet.getPlanetNumberOfMoons());
    }
    
    @FXML
    void setPlanetName( ActionEvent event ) {
	    	readPlanetName(planetName.getText());
//    		planetName.setText(planet.getPlanetName());
//	    	fancyPlanetName.setText(planet.getPlanetName());
    }
    
    @FXML
    void setPlanetDiameter( ActionEvent event ) {
   		readPlanetDiameter(planetDiameterKM.getText());
    }
    
    @FXML
    void setPlanetMeanSurfaceTemp( ActionEvent event ) {
   		readPlanetTemperature(planetMeanSurfaceTempC.getText());
    }
    
    @FXML
    void setNumberOfMoons( ActionEvent event ) {
    		readPlanetNumberOfMoons(planetNumberOfMoons.getText());
    }
    
    @FXML
    void savePlanet( ActionEvent event ) {
	    	new PlanetBuilder(planetImage.getId(), planetName.getText(), planetDiameterKM.getText()
	    			, planetMeanSurfaceTempC.getText(), planetNumberOfMoons.getText() );    	
    }
}