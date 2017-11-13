package planet.builders;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import planet.delegators.Planet;

public class PlanetImageBuilder {
	
	Planet planet;

	public PlanetImageBuilder(Planet planet) {
		this.planet = planet;
		selectImage();
	}

	private void selectImage() {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(new Stage());
		
		if (file == null)
			return;
	
		setPlanetImage(file.getAbsolutePath());
	}

	private void setPlanetImage( String imagePath ) {
		planet.setPlanetImage(imagePath);
	}
	
}
