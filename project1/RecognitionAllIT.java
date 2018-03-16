package net.sf.javaanpr.test;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xml.sax.SAXException;

import static org.hamcrest.MatcherAssert.assertThat;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Properties;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class RecognitionAllIT {

    private File snapshot;

   @Parameterized.Parameters(name = "Testing {0}")
    public static File[] testParameters() throws FileNotFoundException, IOException{
       String snapshotDirPath = "src/test/resources/snapshots";

       File snapshotDir = new File(snapshotDirPath);
       File[] snapshots = snapshotDir.listFiles();
       assertThat(snapshots, not(nullValue()));
       assertThat(snapshots.length, greaterThan(0));

       return snapshots;
    }

    public RecognitionAllIT(File snapshot){
        this.snapshot = snapshot;
    }

    @Test
    public void testAllSnapshots() throws IOException, ParserConfigurationException, SAXException{
        String resultsPath = "src/test/resources/results.properties";
        InputStream resultsStream = new FileInputStream(new File(resultsPath));

        Properties properties = new Properties();
        properties.load(resultsStream);
        resultsStream.close();
        assertThat(properties.size(), greaterThan(0));

        CarSnapshot carSnap = new CarSnapshot(new FileInputStream(snapshot));
        assertThat("carSnap should not be null", carSnap, not(nullValue()));
        assertThat("carSnap.image should not be null", carSnap.getImage(), not(nullValue()));

        String snapName = snapshot.getName();
        String plateCorrect = properties.getProperty(snapName);
        assertThat(plateCorrect, not(nullValue()));

        Intelligence intel = new Intelligence();
        assertThat(intel, not(nullValue()));

        String numberPlate = intel.recognize(carSnap, false);

        assertThat(numberPlate, is(plateCorrect));

        carSnap.close();
    }



}

