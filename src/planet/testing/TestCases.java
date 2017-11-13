package planet.testing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

import planet.builders.PlanetBuilder;
import planet.builders.PlanetFileBuilder;
import planet.delegators.*;

public class TestCases {

	private static String expectedOut;
	private String imagePath;
	private String planetName;
	private String planetDiameter;
	private String planetTemp;
	private String planetMoons;
	
	public String getExpected() {	
	    	String expectedOut = "";    	
	    	expectedOut += imagePath + "\n";
	    	expectedOut += planetName + "\n";
	    	expectedOut += planetDiameter + "\n";
	    	expectedOut += planetTemp + "\n";
	    	expectedOut += planetMoons + "\n";
	    	
	    	return expectedOut;
	}
	
	public Planet buildPlanet() {
		Planet planet = new Planet();
		imagePath = "images/venus.png";
		planetName = "Venus";
		planetDiameter = "13456";
		planetTemp = "123";
		planetMoons = "2";
		
		planet.savePlanetName(planetName);
		planet.setPlanetImage(imagePath);
		planet.savePlanetDiameter(planetDiameter);
		planet.savePlanetMeanSurfaceTemp(planetTemp);
		planet.savePlanetNumberOfMoons(planetMoons);
		
		return planet;
	}
	
	//Cass need help testing all correct output
	@Test
	public void buildPlanetFile() {
		Planet planet = new Planet();
		imagePath = "images/neptune.png";
		planetName = "Venus";
		planetDiameter = "13456";
		planetTemp = "123";
		planetMoons = "2";
		
		planet.savePlanetName(planetName);
		planet.setPlanetImage(imagePath);
		planet.savePlanetDiameter(planetDiameter);
		planet.savePlanetMeanSurfaceTemp(planetTemp);
		planet.savePlanetNumberOfMoons(planetMoons);
		
		PlanetFileBuilder fileBuild = new PlanetFileBuilder(planet);
		
		String expectedOutput = getExpected();
		try {
			assertEquals(expectedOutput, fileBuild.getPlanetInformationString() );
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}
	
	@Test
	public void validateImage() {
		Planet planet = new Planet();
		imagePath = "";
				
		try {
		    planet.setPlanetImage(imagePath);
		    fail(); // if we got here, no exception was thrown, which is bad
		} 
		catch (Exception e) {
		    //final String expected = "Error: invalid file";
			assertEquals( e.getMessage().startsWith("Error: invalid file"), true);
		}  	
	}
	
	@Test
	public void validateName() {
		//for name with special characters
		Planet planet = new Planet();
		assertEquals(planet.savePlanetName("Np#$"), false);
//		try {
//		    planet.savePlanetName("Nep$Tune");
//		    fail(); // if we got here, no exception was thrown, which is bad
//		} 
//		catch (Exception e) {
//			assertEquals(e.getMessage(), "Error: Planet name contains invalid characters [A-Za-z0-9 -.]" );
//
//		}  
	}
	
	@Test
	public void invalidNameLength() {
		//for name with a name that is not long enough
		Planet planet = new Planet();
		
		try {
		    planet.savePlanetName("");
		    fail(); // if we got here, no exception was thrown, which is bad
		} 
		catch (Exception e) {
			assertEquals(e.getMessage(), "Error: Planet name must be between 1 and 256 characters long" );

		}  
	}
	
	@Test
	public void validateDiameter() {
		//validate Diameter; 
		Planet planet = new Planet();
		
		try {
		    planet.savePlanetDiameter("40000000");
		    fail(); // if we got here, no exception was thrown, which is bad
		} 
		catch (Exception e) {
			assertEquals(e.getMessage(), "Diameter (KM) must be between 0 and 200,000" );

		}  
	}
	
	@Test
	public void validateTemp() {
		//validate Diameter; 
		Planet planet = new Planet();
		
		try {
		    planet.savePlanetMeanSurfaceTemp("40000000");
		    fail(); // if we got here, no exception was thrown, which is bad
		} 
		catch (Exception e) {
			assertEquals(e.getMessage().startsWith("Degrees (C) must be between -273.15 and 500.00" ), true);

		}  
	}
	
	@Test
	public void validateMoons() {
		//validate Diameter; 
		Planet planet = new Planet();
		
		try {
		    planet.savePlanetNumberOfMoons("40000000");
		    fail(); // if we got here, no exception was thrown, which is bad
		} 
		catch (Exception e) {
			assertEquals(e.getMessage().startsWith("Number of moons must be between 0 and 1,000" ), true);

		}  
	}
	
	//TEST PLANET LOADING
	@Test
	public void loadPlanet() {
		File file = new File("tests/Venus.txt");
		Planet planet = buildPlanet();
		PlanetBuilder build = new PlanetBuilder(planet);
		
		try {
			build.readFromFile(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		assertEquals(planetName, planet.getPlanetName());

	}
	

}
