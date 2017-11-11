package planet.builders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import planet.delegators.Planet;

public class PlanetBuilder 
{
    private ImageView planetImage;   
    
    private TextField planetName;
    private TextField planetDiameterKM;
    private TextField planetMeanSurfaceTempC;
    private TextField planetNumberOfMoons;
        
	private Planet planet;
		
	// builder will basically get the planet file from the UI and will build the planet object to populate the UI fields.

	public PlanetBuilder(ImageView planetImage, TextField planetName, TextField planetDiameterKM,
			TextField planetMeanSurfaceTempC, TextField planetNumberOfMoons) {
		this.planetImage = planetImage;
		this.planetName = planetName;
		this.planetDiameterKM = planetDiameterKM;
		this.planetMeanSurfaceTempC = planetMeanSurfaceTempC;
		this.planetNumberOfMoons = planetNumberOfMoons;
		
		planet = new Planet();
		savePlanet();
	}

//	public PlanetBuilder(File file) {
//		this.file = file;
//	}

	public PlanetBuilder() {
		// TODO Auto-generated constructor stub
	}

	private void savePlanet() {
		if (!isPlanetReadyToSave())
			return;
		savePlanetToFile();
	}
//
//    public void loadPlanetInformationFromFile( File file ) {
//	    	try 	{
//	    		readFromFile(file);
//	    	}
//	    	catch (FileNotFoundException e) {
//	    		planet.showErrorAlert("Error: file not found\n" + e.getLocalizedMessage());
//	    	}
//	    	catch (NoSuchElementException e) {
//	    		planet.showErrorAlert("Error: could not load planet information\n" + e.getLocalizedMessage());
//	    	}
//    }
	// delegate??
	private boolean isPlanetReadyToSave( ) {
		boolean returnCode = false;
		String imageId = planetImage.getId();
		
		if (imageId.compareTo("images/no_image.png") == 0)
			planet.showErrorAlert("Error: cannot save file\nNo image selected");
		else if (isEmptyString(planetName.getText()))
			planet.showErrorAlert("Error: cannot save file\nNo name specified");
		else if (isEmptyString(planetDiameterKM.getText()))
			planet.showErrorAlert("Error: cannot save file\nNo diameter specified");
		else if (isEmptyString(planetMeanSurfaceTempC.getText()))
			planet.showErrorAlert("Error: cannot save file\nNo temperature specified");
		else if (isEmptyString(planetNumberOfMoons.getText()))
			planet.showErrorAlert("Error: cannot save file\nNo number of moons specified");
		else
			returnCode = true;
	
		return returnCode;
	}
	
	// delegate??
	private boolean isEmptyString( String stringToCheck ) {
		if (stringToCheck.isEmpty())
			return true;
		
		return false;
	}
    private void savePlanetToFile( ) {
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.getExtensionFilters().add(new ExtensionFilter("TXT files", "*.txt"));
	    	File file = fileChooser.showSaveDialog(new Stage());
	    	
	    	if (file == null)
	    		return;
	    	
	    	writePlanetInformationToFile(file, getPlanetInformationString());
    }
    
    // delegate??
    private void writePlanetInformationToFile( File file, String fileContent ) {
	    	try	{
	    		writeToFile(file, fileContent);
	    	}
	    	catch (IOException e) {
	    		planet.showErrorAlert("Error: Did not write Planet to file\n" + e.getLocalizedMessage());
	    	}
    }
    
    private void writeToFile(File file, String fileContent) throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(fileContent);
		fileWriter.close();
    }
    
    private String getPlanetInformationString( ) {
	    	String planetInformation = "";    	
	    	planetInformation += planetImage.getId() + "\n";
	    	planetInformation += planetName.getText() + "\n";
	    planetInformation += planetDiameterKM.getText() + "\n";
	    	planetInformation += planetMeanSurfaceTempC.getText() + "\n";
	    	planetInformation += planetNumberOfMoons.getText() + "\n";
	    	
	    	return planetInformation;
    }
    
//    
//    // delegate??
//    public void loadPlanetInformationFromFile( File file ) {
//	    	try 	{
//	    		readFromFile(file);
//	    	}
//	    	catch (FileNotFoundException e) {
//	    		planet.showErrorAlert("Error: file not found\n" + e.getLocalizedMessage());
//	    	}
//	    	catch (NoSuchElementException e) {
//	    		planet.showErrorAlert("Error: could not load planet information\n" + e.getLocalizedMessage());
//	    	}
//    }
//    
//    private void readFromFile( File file ) throws FileNotFoundException {
//		Scanner scanner = new Scanner(file);
//		//setPlanetImage(scanner.nextLine());
//		readPlanetName(scanner.nextLine());
//		readPlanetDiameter(scanner.nextLine());
//		readPlanetTemperature(scanner.nextLine());
//		readPlanetNumberOfMoons(scanner.nextLine());
//		scanner.close();
//    }
//    
////    private void setPlanetImage( String imagePath ) {
////		planet.setPlanetImage(imagePath);
////		//planetImage.setImage(planet.getPlanetImage());
////		//planetImage.setId(planet.getPlanetImagePath());
////    }
//    
//    private void readPlanetName( String name ) {
//	    	planet.savePlanetName(name);
//    		//planetName.setText(planet.getPlanetName());
//	    	//fancyPlanetName.setText(planet.getPlanetName());
//    }
//    
//    private void readPlanetDiameter( String diameterKM ) {
//	    	if (diameterKM.equals(""))
//	    		return;
//	    	
//	    	planet.savePlanetDiameter(diameterKM);
//	    //	planetDiameterM.setText(planet.getPlanetDiameterM());
//	    	//planetDiameterKM.setText(planet.getPlanetDiameterKM());
//    }
//    
//    private void readPlanetTemperature( String temperatureC ) {
//	    	if (temperatureC.equals(""))
//	    		return;
//	    	
//	    	planet.savePlanetMeanSurfaceTemp(temperatureC);
//	   // 	planetMeanSurfaceTempC.setText(planet.getPlanetMeanSurfaceTempC());
//	    	//planetMeanSurfaceTempF.setText(planet.getPlanetMeanSurfaceTempF());
//    }
//    
//    private void readPlanetNumberOfMoons( String numberOfMoons ) {
//	    	if (numberOfMoons.equals(""))
//	    		return;
//	    	
//	    	planet.savePlanetNumberOfMoons(numberOfMoons);
//	   // 	planetNumberOfMoons.setText(planet.getPlanetNumberOfMoons());
//    }
	
}
