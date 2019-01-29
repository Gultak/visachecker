package de.gultak;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unit test for simple App.
 */
public class ApplicationTest {
    private static final String visaNumber = "1901406"; // "1901243";

    @Test
    public void testApplication() {
        new Application(visaNumber).run(); //ClassLoader.getSystemClassLoader().getResourceAsStream("pdf-abholbereite-visa-neu-data.pdf"));
    }

    @Ignore @Test
    public void testMatch() {
        String string = " Stand/ Стан на 29.01.2019:  Barcode/  mitzubringende Dokumente/  Visum Код Документи, які необхідно мати при собі spätestens abholen bis/  Забрати візу до 1881636 RP, ES 05.04.2019 1886208 RP, ES, KV 21.01.2019 1888210 RP, ES, KV 21.01.2019 1889410 (neu:1898225) RP, ES, KV, HU (Orig.) 08.01.2019 1891128 RP, ES, KV 22.04.2019 1891422 RP, ES, AG, Zusicherung im Original (+1 16.11.2018  Kopie) 1891424 RP, ES 1891425 RP, ES 1892681 RP, ES 30.10.2018 1892828 RP, ES, KV, AG 21.02.2019 1893518 RP, ES 22.10.2018 1893668 RP, ES, Geburtsurkunde im Original (mit 24.04.2019 Apostille und Übersetzung wenn ukrainische Geburtsurkunde) 1894159 (neu: 1900698) RP, ES, neuer RP 08.04.2019 1894176 RP, ES, KV, AG 23.01.2019 1895061 RP, ES 23.04.2019 1895464 RP, ES, KV 24.01.2019 1895548 (neu 1898405) RP, ES, AG 21.01.2019 1895730 RP, ES 27.03.2019 1895733 RP, ES, ERG, FNB, KV 29.05.2019 1895823 RP, ES, SKN 31.01.2019 1896835 RP, ES, NV, GU mit Apost./dt.Übers. 19.11.2018 1897160 RP, ES 19.03.2019 1897163 RP, ES, KV 19.03.2019 1897244 (neu: 1899578) RP, ES 28.12.2018 1897500, 1897506 RP, ES, PV, KV 18.02.2019 1897566 RP, ES 05.02.2019 1897567 RP, ES 22.04.2019 1897675 RP, ES 02.04.2019 1897833 RP, ES, Zulassung 01.02.2019 1897842 RP, ES, KV 24.04.2019 1897869 RP, ES, AG (Zeitraum über 90 Tage) 05.02.2019 1897987 RP, ES, ausreichender KV-Schutz 24.04.2019 1898091 RP, ES 15.04.2019  ANSCHRIFT TEL : E-MAIL:    wul. Lwa Tolstoho, 57, Businesszentrum „101 Tower“, 22. Etage + 380 44 2811400 visa@kiew.diplo.de   01901 Kiew FAX:  INTERNET:   ANSCHRIFT + 49 30 5000 67099 www.kiew.diplo.de   ";
        Matcher matcher = Application.pattern.matcher(string);
        Assert.assertTrue(matcher.matches());
    }
}
