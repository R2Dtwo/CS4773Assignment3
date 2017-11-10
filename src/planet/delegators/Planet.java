package planet.delegators;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class Planet
{
	private String planetImagePath;
	private Image planetImage;
	private String planetName;
	private double planetDiameterKM;
	private double planetDiameterM;
	private double planetMeanSurfaceTempC;
	private double planetMeanSurfaceTempF;
	private int planetNumberOfMoons;
	
	public Planet ( )
	{
		planetImagePath = "";
		planetName = "";
		planetDiameterKM = -1; // getter should return empty string if value invalid
		planetDiameterM = -1;
		planetMeanSurfaceTempC = -1;
		planetMeanSurfaceTempF = -1;
		planetNumberOfMoons = -1;
	}
	
	// delegate??
    public void setPlanetImage( String imagePath )
    {
    		try
    		{
	    		Image image = new Image(new FileInputStream(imagePath));
	    		planetImage = image;
	    		planetImagePath = imagePath;
	    	}
	    	catch (FileNotFoundException e)
	    	{
	    		showErrorAlert("Error: invalid file\n" + e.getLocalizedMessage());
	    	}
    }
    
    public Image getPlanetImage( )
    {
    	return planetImage;
    }
    
    public String getPlanetImagePath( )
    {
    	return planetImagePath;
    }

    public void showErrorAlert( String errorMessage )
    {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Invalid Planet Input");
    	alert.setContentText(errorMessage);
    	System.err.println(errorMessage);
    	alert.showAndWait();
    }
}