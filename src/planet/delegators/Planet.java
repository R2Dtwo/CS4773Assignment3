package planet.delegators;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class Planet {
	private String planetImagePath;
	private Image planetImage;
	private static String planetName;
	private double planetDiameterKM;
	private double planetDiameterM;
	private double planetMeanSurfaceTempC;
	private double planetMeanSurfaceTempF;
	private int planetNumberOfMoons;
	
	public Planet ( ) {
 		planetImagePath = "";
		planetName = "";
		planetDiameterKM = -1; 
		planetDiameterM = -1;
		planetMeanSurfaceTempC = -1;
		planetMeanSurfaceTempF = -1;
		planetNumberOfMoons = -1;
	}
	
    public void setPlanetImage( String imagePath ) {
    		try {
    			setImagePath(imagePath);
	    	}
	    	catch (FileNotFoundException e) {
	    		this.showErrorAlert("Error: invalid file\n" + e.getLocalizedMessage());
	    	}
    }
    
    private void setImagePath( String imagePath ) throws FileNotFoundException {
    		Image image = new Image(new FileInputStream(imagePath));
		planetImage = image;
		planetImagePath = imagePath;
    }
    
    public Image getPlanetImage( ) {
    		return planetImage;
    }
    
    public String getPlanetImagePath( ) {
    		return planetImagePath;
    }
     
    public void savePlanetName(String name) {
	    	if (name.length() < 0 || name.length() > 256)
	    		this.showErrorAlert("Error: Planet name must be between 1 and 256 characters long");
	    	else if (name.matches("^[A-Za-z0-9 \\-\\.]+$")) {
	    		planetName = name;
	    		return;
	    	}
	    	else
	    		this.showErrorAlert("Error: Planet name contains invalid characters [A-Za-z0-9 -.]");
    }
    
    public String getPlanetName() {
    		return planetName;
    }

    public void savePlanetDiameter( String planetDiameterKMText ) {    
    		try	{
	    		checkDiameterToSet(planetDiameterKMText);
	    	}
	    	catch (NumberFormatException e) {
	    		this.showErrorAlert("Error: Invalid diameter (KM) input\n" + e.getLocalizedMessage());
	    	}
    }
    
    private void checkDiameterToSet( String planetDiameterKMText ) {
		planetDiameterKMText = planetDiameterKMText.replaceAll("[,]", "");
    		double diameterKM = Double.parseDouble(planetDiameterKMText);

    		if (diameterKM < 0 || diameterKM > 200000)
			throw new NumberFormatException("Diameter (KM) must be between 0 and 200,000");
		
		planetDiameterKM = diameterKM;
		planetDiameterM = diameterKM * 0.621371;
    }
    
    public String getPlanetDiameterM() {
    		if (planetDiameterM == -1)
    			return "";

    		return String.format("%,.1f", planetDiameterM);
    }
    
    public String getPlanetDiameterKM() {
    		if (planetDiameterKM == -1)
    			return "";
    		
    		return String.format("%,.1f", planetDiameterKM);

    }
    
    public void savePlanetMeanSurfaceTemp( String planetSurfaceTempCText ) {
	    	try 	{
	    		checkTempToSet(planetSurfaceTempCText);
	    	}
	    	catch (NumberFormatException e) {
	    		this.showErrorAlert("Error: Invalid temperature (C) input\n" 
	    				+ e.getLocalizedMessage());
	    	}
    }
    
    private void checkTempToSet( String planetSurfaceTempCText ) {
		double meanSurfaceTempC = Double.parseDouble(planetSurfaceTempCText);
		
		if (meanSurfaceTempC < -273.15 || meanSurfaceTempC > 500.0)
			throw new NumberFormatException("Degrees (C) must be between -273.15 and 500.00");
		
		planetMeanSurfaceTempC = meanSurfaceTempC;
		planetMeanSurfaceTempF = ((meanSurfaceTempC * 9 / 5) + 32);
    }
    
    public String getPlanetMeanSurfaceTempC() {
    		if (planetMeanSurfaceTempC == -1)
    			return "";
    		return (String) (""+String.format("%,.1f", planetMeanSurfaceTempC));
    }
    
    public String getPlanetMeanSurfaceTempF() {
    		if (planetMeanSurfaceTempF == -1)
    			return "";
    		return (String) (""+String.format("%,.1f", planetMeanSurfaceTempF));
    }
    
    public void savePlanetNumberOfMoons( String numberOfMoonsText ) {
    		try {
    			checkMoonsToSet(numberOfMoonsText);
	    	}
	    	catch (NumberFormatException e) {
	    		this.showErrorAlert("Error: Invalid Integer input\n" + e.getLocalizedMessage());
	    	}
    }
    
    private void checkMoonsToSet( String numberOfMoonsText ) {
		int numberOfMoons = Integer.parseInt(numberOfMoonsText);
		
		if (numberOfMoons < 0 || numberOfMoons > 1000)
			throw new NumberFormatException("Number of moons must be between 0 and 1,000");
		
		planetNumberOfMoons = numberOfMoons;
    }
    
    public String getPlanetNumberOfMoons() {
    		if (planetNumberOfMoons == -1)
    			return "";
    		return String.format("%,d", planetNumberOfMoons);
    }
    
    public void showErrorAlert( String errorMessage ) {
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Invalid Planet Input");
	    	alert.setContentText(errorMessage);
	    	System.err.println(errorMessage);
	    	alert.showAndWait();
    }
}