package planet.builders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import planet.delegators.Planet;

public class PlanetFileBuilder {
	private String imageId;
	private String planetName;
	private String planetDiameterKM;
	private String planetMeanSurfaceTempC;
	private String planetNumberOfMoons;
	
	private Planet planet;
			
	public PlanetFileBuilder(Planet planet) {
		this.planet = planet;
		this.imageId = planet.getPlanetImagePath();
		this.planetName = planet.getPlanetName();
		this.planetDiameterKM = planet.getPlanetDiameterKM();
		this.planetMeanSurfaceTempC = planet.getPlanetMeanSurfaceTempC();
		this.planetNumberOfMoons = planet.getPlanetNumberOfMoons();
		savePlanet();
	}

	private void savePlanet() {
		if (!isPlanetReadyToSave())
			return;
		savePlanetToFile();
	}

    private boolean isPlanetReadyToSave( ) {
		boolean returnCode = false;
		
		if (imageId.compareTo("images/no_image.png") == 0)
			planet.showErrorAlert("Error: cannot save file\nNo image selected");
		else if (isEmptyString(planetName))
			planet.showErrorAlert("Error: cannot save file\nNo name specified");
		else if (isEmptyString(planetDiameterKM))
			planet.showErrorAlert("Error: cannot save file\nNo diameter specified");
		else if (isEmptyString(planetMeanSurfaceTempC))
			planet.showErrorAlert("Error: cannot save file\nNo temperature specified");
		else if (isEmptyString(planetNumberOfMoons))
			planet.showErrorAlert("Error: cannot save file\nNo number of moons specified");
		else
			returnCode = true;
	
		return returnCode;
	}
	
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
	    	planetInformation += imageId + "\n";
	    	planetInformation += planetName + "\n";
	    planetInformation += planetDiameterKM + "\n";
	    	planetInformation += planetMeanSurfaceTempC + "\n";
	    	planetInformation += planetNumberOfMoons + "\n";
	    	
	    	return planetInformation;
    }

}
