package planet.detail;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import planet.builders.PlanetBuilder;
import planet.builders.PlanetFileBuilder;
import planet.builders.PlanetImageBuilder;

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
		planet.setPlanetImage("images/no_image.png");
		planetImage.setImage(planet.getPlanetImage());
		planetImage.setId(planet.getPlanetImagePath());
	}

    @FXML
    void selectImage( ActionEvent event ) {
    		new PlanetImageBuilder(planet);
    		planetImage.setImage(planet.getPlanetImage());
    		planetImage.setId(planet.getPlanetImagePath());	
    	}

    @FXML
    void loadPlanet( ActionEvent event ) {
	    	new PlanetBuilder(planet);
		planetImage.setId(planet.getPlanetImagePath());
		planetImage.setImage(planet.getPlanetImage());
		planetName.setText(planet.getPlanetName());
		fancyPlanetName.setText(planet.getPlanetName());
		planetDiameterKM.setText(planet.getPlanetDiameterKM());
		planetDiameterM.setText(planet.getPlanetDiameterM());
	    	planetMeanSurfaceTempC.setText(planet.getPlanetMeanSurfaceTempC());
	    	planetMeanSurfaceTempF.setText(planet.getPlanetMeanSurfaceTempF());
 	    	planetNumberOfMoons.setText(planet.getPlanetNumberOfMoons());
    }

    @FXML
    void setPlanetName( ActionEvent event ) {
	    planet.savePlanetName(planetName.getText());
    		planetName.setText(planet.getPlanetName());
	    	fancyPlanetName.setText(planet.getPlanetName());	
    }
    
    @FXML
    void setPlanetDiameter( ActionEvent event ) {
   		planet.savePlanetDiameter(planetDiameterKM.getText());
   	  	planetDiameterM.setText( planet.getPlanetDiameterM() );
   	  	planetDiameterKM.setText( planet.getPlanetDiameterKM() );
    }
    
    @FXML
    void setPlanetMeanSurfaceTemp( ActionEvent event ) {
   		planet.savePlanetMeanSurfaceTemp(planetMeanSurfaceTempC.getText());
	    	planetMeanSurfaceTempC.setText(planet.getPlanetMeanSurfaceTempC());
	    	planetMeanSurfaceTempF.setText(planet.getPlanetMeanSurfaceTempF());
    }
    
    @FXML
    void setNumberOfMoons( ActionEvent event ) {
    		planet.savePlanetNumberOfMoons(planetNumberOfMoons.getText());
	    	planetNumberOfMoons.setText(planet.getPlanetNumberOfMoons());
    }
    
    @FXML
    void savePlanet( ActionEvent event ) {
	   new PlanetFileBuilder(planet);
    }
}