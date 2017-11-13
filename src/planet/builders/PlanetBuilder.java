package planet.builders;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import planet.delegators.Planet;
import planet.delegators.PlanetError;

public class PlanetBuilder {
	Planet planet;

	public PlanetBuilder(Planet planet) {
		this.planet = planet;
		loadPlanet();
	}
	
	private void loadPlanet() {
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
	}
  
	private void loadPlanetInformationFromFile( File file ) {
	    	try 	{
	    		readFromFile(file);
	    	}
	    	catch (FileNotFoundException e) {
	    		PlanetError.showErrorAlert("Error: file not found\n" + e.getLocalizedMessage());
	    	}
	    	catch (NoSuchElementException e) {
	    		PlanetError.showErrorAlert("Error: could not load planet information\n" + e.getLocalizedMessage());
	    	}
	}

	public void readFromFile( File file ) throws FileNotFoundException {
		Scanner scanner = new Scanner(file);
		
		setPlanetImage(scanner.nextLine());
		readPlanetName(scanner.nextLine());
		readPlanetDiameter(scanner.nextLine());
		readPlanetTemperature(scanner.nextLine());
		readPlanetNumberOfMoons(scanner.nextLine());

		scanner.close();
	}
  
	private void setPlanetImage( String imagePath ) {
		planet.setPlanetImage(imagePath);
	}

	private void readPlanetName( String name ) {
  		planet.savePlanetName(name);
	}
  
	private void readPlanetDiameter( String diameterKM ) {
	  	if (diameterKM.equals(""))
	    		return;
	    	
	    	planet.savePlanetDiameter(diameterKM);
	}
  
	private void readPlanetTemperature( String temperatureC ) {
	    	if (temperatureC.equals(""))
	    		return;
	    	
	    	planet.savePlanetMeanSurfaceTemp(temperatureC);
	}
  
	private void readPlanetNumberOfMoons( String numberOfMoons ) {
	    	if (numberOfMoons.equals(""))
	    		return;
	    	
	    	planet.savePlanetNumberOfMoons(numberOfMoons);
	}	
}
