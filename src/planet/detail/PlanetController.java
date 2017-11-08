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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class PlanetController implements Initializable
{
    @FXML
    private ImageView planetImage;

    @FXML
    private Button selectImageButton;

    @FXML
    private TextField planetName;

    @FXML
    private TextField planetDiameterKM;

    @FXML
    private TextField planetDiameterM;

    @FXML
    private TextField planetMeanSurfaceTempC;

    @FXML
    private TextField planetMeanSurfaceTempF;

    @FXML
    private TextField planetNumberOfMoons;

    @FXML
    private Label fancyPlanetName;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		savePlanetImage("images/no_image.png");	
	}

    @FXML
    void selectImage( ActionEvent event )
    {
    	FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(new Stage());
		savePlanetImage(file.getAbsolutePath());
    }
    
    // delegate??
    private void savePlanetImage( String imagePath )
    {
    	try
    	{
    		Image image = new Image(new FileInputStream(imagePath));
    		planetImage.setImage(image);
    		planetImage.setId(imagePath);
    	}
    	catch (FileNotFoundException e)
    	{
    		System.err.println("Error: invalid file\n" + e.getLocalizedMessage());
    	}
    }

    @FXML
    void loadPlanet( ActionEvent event )
    {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("TXT files", "*.txt"));
    	
    	File file = fileChooser.showSaveDialog(new Stage());
    	
    	if (file == null)
    		return;
    	
    	loadPlanetInformationFromFile(file);
    }
    
    // delegate??
    private void loadPlanetInformationFromFile( File file )
    {
    	try
    	{
    		Scanner scanner = new Scanner(file);
    		savePlanetImage(scanner.nextLine());
    		readPlanetName(scanner.nextLine());
    		readPlanetDiameter(scanner.nextLine());
    		readPlanetTemperature(scanner.nextLine());
    		readPlanetNumberOfMoons(scanner.nextLine());
    		scanner.close();
    	}
    	catch (FileNotFoundException e)
    	{
    		System.err.println("Error: file not found\n" + e.getLocalizedMessage());
    	}
    	catch (NoSuchElementException e)
    	{
    		System.err.println("Error: could not load planet information\n" + e.getLocalizedMessage());
    	}
    }
    
    // delegate??
    private void readPlanetName( String name )
    {
    	planetName.setText(name);
    	fancyPlanetName.setText(name);
    }
    
    // delegate??
    private void readPlanetDiameter( String diameterKM )
    {
    	if (diameterKM.equals(""))
    		return;
    	
    	savePlanetDiameter(diameterKM);
    }
    
    // delegate???
    private void readPlanetTemperature( String temperatureC )
    {
    	if (temperatureC.equals(""))
    		return;
    	
    	savePlanetMeanSurfaceTemp(temperatureC);
    }
    
    private void readPlanetNumberOfMoons( String numberOfMoons )
    {
    	if (numberOfMoons.equals(""))
    		return;
    	
    	savePlanetNumberOfMoons(numberOfMoons);
    }
    
    @FXML
    void savePlanet( ActionEvent event )
    {
    	if (!isPlanetReadyToSave())
    		return;
    	
    	savePlanetToFile();
    }
    
    // delegate??
    private boolean isPlanetReadyToSave( )
    {
    	String name = planetName.getText();
    	
    	// do I need to check the other fields too??
    	if (name.equals(""))
    		return false;
    	
    	return true;
    }
    
    // delegate??
    private void savePlanetToFile( )
    {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("TXT files", "*.txt"));
    	
    	File file = fileChooser.showSaveDialog(new Stage());
    	
    	if (file == null)
    		return;
    	
    	writePlanetInformationToFile(file, getPlanetInformationString());
    }
    
    // delegate??
    private void writePlanetInformationToFile( File file, String fileContent )
    {
    	try
    	{
    		FileWriter fileWriter = new FileWriter(file);
    		fileWriter.write(fileContent);
    		fileWriter.close();
    	}
    	catch (IOException e)
    	{
    		System.err.println("Error: Did not write Planet to file\n" + e.getLocalizedMessage());
    	}
    }
    
    private String getPlanetInformationString( )
    {
    	String planetInformation = "";
    	
    	planetInformation += planetImage.getId() + "\n";
    	planetInformation += planetName.getText() + "\n";
    	planetInformation += planetDiameterKM.getText() + "\n";
    	planetInformation += planetMeanSurfaceTempC.getText() + "\n";
    	planetInformation += planetNumberOfMoons.getText() + "\n";
    	
    	return planetInformation;
    }
    
    @FXML
    void setPlanetName( ActionEvent event )
    {
    	String nameOfPlanet = planetName.getText();

    	if (nameOfPlanet.length() < 0 || nameOfPlanet.length() > 256)
    		System.err.println("Error: Planet name must be between 1 and 256 characters long");
    	else if (nameOfPlanet.matches("^[A-Za-z0-9 \\-\\.]+$"))
    	{
    		fancyPlanetName.setText(nameOfPlanet);
    		return;
    	}
    	else
    		System.err.println("Error: Planet name contains invalid characters [A-Za-z0-9 -.]");
    	
    	planetName.setText("");
    }
    
    @FXML
    void setPlanetDiameter( ActionEvent event )
    {
    	savePlanetDiameter(planetDiameterKM.getText());
    }
    
    // delegate???
    private void savePlanetDiameter( String planetDiameterKMText )
    {
    	try
    	{
    		double diameterKM = Double.parseDouble(planetDiameterKMText);
    		
    		if (diameterKM < 0 || diameterKM > 200000)
    			throw new NumberFormatException("Diameter (KM) must be between 0 and 200,000");
    		
    		String km = String.format("%,.1f", diameterKM);
    		String mi = String.format("%,.1f", diameterKM * 0.621371);
    		
    		planetDiameterKM.setText(km);
    		planetDiameterM.setText(mi);
    	}
    	catch (NumberFormatException e)
    	{
    		planetDiameterKM.setText("");
    		planetDiameterM.setText("");
    		System.err.println("Error: Invalid diameter (KM) input\n" + e.getLocalizedMessage());
    	}
    }
    
    @FXML
    void setPlanetMeanSurfaceTemp( ActionEvent event )
    {
    	savePlanetMeanSurfaceTemp(planetMeanSurfaceTempC.getText());
    }
    
    // delegate??
    private void savePlanetMeanSurfaceTemp( String planetSurfaceTempCText )
    {
    	try 
    	{
    		double meanSurfaceTempC = Double.parseDouble(planetSurfaceTempCText);
    		
    		if (meanSurfaceTempC < -273.15 || meanSurfaceTempC > 500.0)
    			throw new NumberFormatException("Degrees (C) must be between -273.15 and 500.00");
    		
    		planetMeanSurfaceTempC.setText("" + meanSurfaceTempC);
    		planetMeanSurfaceTempF.setText("" + ((meanSurfaceTempC * 9 / 5) + 32));
    	}
    	catch (NumberFormatException e)
    	{
    		planetMeanSurfaceTempC.setText("");
    		planetMeanSurfaceTempF.setText("");
    		System.err.println("Error: Invalid temperature (C) input\n" + e.getLocalizedMessage());
    	}
    }
    
    @FXML
    void setNumberOfMoons( ActionEvent event )
    {
    	savePlanetNumberOfMoons(planetNumberOfMoons.getText());
    }
    
    // delegate??
    private void savePlanetNumberOfMoons( String numberOfMoonsText )
    {
    	try 
    	{
    		int numberOfMoons = Integer.parseInt(numberOfMoonsText);
    		
    		if (numberOfMoons < 0 || numberOfMoons > 1000)
    			throw new NumberFormatException("Number of moons must be between 0 and 1,000");
    		
    		String formattedNumberOfMoons = String.format("%,d", numberOfMoons);
    		planetNumberOfMoons.setText(formattedNumberOfMoons);
    	}
    	catch (NumberFormatException e)
    	{
    		planetNumberOfMoons.setText("");
    		System.err.println("Error: Invalid Integer input\n" + e.getLocalizedMessage());
    	}
    }
}