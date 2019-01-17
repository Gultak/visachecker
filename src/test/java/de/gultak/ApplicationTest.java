package de.gultak;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class ApplicationTest
{
    private static final String visaNumber = "1901243"; // "1901406";

    @Test
    public void testApplication() {
        new Application(visaNumber).run(); //ClassLoader.getSystemClassLoader().getResourceAsStream("pdf-abholbereite-visa-neu-data.pdf"));
    }
}
