package planet.delegators;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PlanetError {
    public static void showErrorAlert( String errorMessage ) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Invalid Planet Input");
    	alert.setContentText(errorMessage);
    	System.err.println(errorMessage);
    	alert.showAndWait();
}
}
