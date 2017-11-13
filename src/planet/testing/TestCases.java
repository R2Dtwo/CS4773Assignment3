package planet.testing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

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
	    	expectedOut = "";    	
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
		planetDiameter = "13456.0";
		planetTemp = "123.0";
		planetMoons = "2";
		
		planet.savePlanetName(planetName);
		planet.setPlanetImage(imagePath);
		planet.savePlanetDiameter(planetDiameter);
		planet.savePlanetMeanSurfaceTemp(planetTemp);
		planet.savePlanetNumberOfMoons(planetMoons);
		
		return planet;
	}
	
	@Test
	public void testBuildingPlanetFile() {
		Planet planet = new Planet();
		imagePath = "images/neptune.png";
		planetName = "Venus";
		planetDiameter = "13456.0";
		planetTemp = "123.0";
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
	public void testWithInvalidImage() {
		Planet planet = new Planet();
		imagePath = "";
				
		try {
		    planet.setPlanetImage(imagePath);
		    // if we got here, no exception was thrown, which is bad
		    fail(); 
		} 
		catch (Exception e) {
			assertEquals( e.getMessage().startsWith("Error: invalid file"), true);
		}  	
	}
	
	@Test
	public void testWithInvalidName() {
		Planet planet = new Planet();
		
		try {
		    planet.savePlanetName("Nep$Tune");
		    fail(); 
		} 
		catch (Exception e) {
			assertEquals(e.getMessage(), "Error: Planet name contains invalid characters [A-Za-z0-9 -.]" );

		}  
	}
	
	@Test
	public void testWithInvalidNameLength() {
		Planet planet = new Planet();
		
		try {
		    planet.savePlanetName("");
		    fail(); 
		} 
		catch (Exception e) {
			assertEquals(e.getMessage(), "Error: Planet name must be between 1 and 256 characters long" );
		}  
	}
	
	@Test
	public void testWithInvalidDiameter() {
		Planet planet = new Planet();
		
		try {
		    planet.savePlanetDiameter("40000000");
		    fail(); 
		} 
		catch (Exception e) {
			assertEquals(e.getMessage(), "Diameter (KM) must be between 0 and 200,000" );
		}  
	}
	
	@Test
	public void testWithInvalidTemp() {
		Planet planet = new Planet();
		
		try {
		    planet.savePlanetMeanSurfaceTemp("40000000");
		    fail(); 
		} 
		catch (Exception e) {
			assertEquals(e.getMessage().startsWith("Degrees (C) must be between -273.15 and 500.00" ), true);
		}  
	}
	
	@Test
	public void testWithInvalidMoonsNum() {
		Planet planet = new Planet();
		
		try {
		    planet.savePlanetNumberOfMoons("40000000");
		    fail(); 
		} 
		catch (Exception e) {
			assertEquals(e.getMessage().startsWith("Number of moons must be between 0 and 1,000" ), true);
		}  
	}
	
	@Test
	public void testLoadPlanetInfoFromFile() {
		File file = new File("tests/Venus.txt");
		Planet planet = buildPlanet();
		PlanetBuilder builder = new PlanetBuilder(planet);
		
		try {
			builder.readFromFile(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		assertEquals(imagePath, planet.getPlanetImagePath());
		assertEquals(planetName, planet.getPlanetName());
		assertEquals(planetDiameter, planet.getPlanetDiameterKM());
		assertEquals(planetTemp, planet.getPlanetMeanSurfaceTempC());
		assertEquals(planetMoons, planet.getPlanetNumberOfMoons());
	}
	
	@Test
	public void testConvertDiameterFromKMToMi() {
		Planet planet = buildPlanet();
		
		assertEquals("253.4", planet.getPlanetDiameterM());
	}
	@Test
	public void testConvertTempFromCToF() {
		Planet planet = buildPlanet();
		
		assertEquals("8,361.2", planet.getPlanetMeanSurfaceTempF());
	}
}
