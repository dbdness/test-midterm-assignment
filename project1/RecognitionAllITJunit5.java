package net.sf.javaanpr.test;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;

public class RecognitionAllITJunit5 {

    @TestFactory
    Stream<DynamicTest> testAllSnapshots() throws IOException {
        List<File> testData = Arrays.asList(RecognitionAllIT.testParameters());
        return testData.stream().map(snapshot -> DynamicTest.dynamicTest(
                "Testing " + snapshot.getName(), () -> testSnapshot(snapshot)
        ));
    }

    private void testSnapshot(File snapshot) throws IOException, ParserConfigurationException, SAXException {
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
