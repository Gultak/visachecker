package de.gultak;

import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class Application {

    private static Logger logger = Logger.getAnonymousLogger();

    private static final String visaURL = "https://kiew.diplo.de/blob/1350604/2a61a4399c8a3e3a686419ad5a6797b4/pdf-abholbereite-visa-neu-data.pdf";

    private static final String visaNumberIra = "1901406";

    private static final String targetEmail = "visa@gultak.de";

    private final String visaNumber;

    public Application() {
        this.visaNumber = visaNumberIra;
    }

    public Application(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        try {
            URL url = new URL(visaURL);
            try (InputStream stream = url.openStream()) {
                run(stream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(InputStream filestream) {
        try (PDDocument pdfDocument = PDDocument.load(filestream)) {
            ObjectExtractor extractor = new ObjectExtractor(pdfDocument);
            SpreadsheetExtractionAlgorithm algorithm = new SpreadsheetExtractionAlgorithm();
            PageIterator pages = extractor.extract();
            while (pages.hasNext()) {
                Page page = pages.next();
                for (Table table : algorithm.extract(page))
                    check(table);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void check(Table table) {
        logger.info(() -> "Check Table " + table + "...");
        logger.info(() -> "   Column number: " + table.getColCount());
        boolean sent = false;
        if (table.getColCount() == 3)
            for (List<RectangularTextContainer> row : table.getRows()) {
                logger.info(() -> "      Checking row: " + row.toString().replaceAll("\\r", "\n"));
                String visaText = row.get(0).getText();
                boolean containsNumber = visaText.contains(visaNumber);
                logger.info(() -> "      Possible Visa #: " + visaText.replaceAll("\\r", "\n") + " -> " + containsNumber);
                if (containsNumber)
                    sent |= sendMail(row.get(1).getText(), row.get(2).getText());
            }
        logger.info(() -> "   ...done.");
        logger.warning(sent ? "Mail sent." : " No Mail sent.");
    }

    private boolean sendMail(String documents, String date) {
        logger.info(() -> ("   ----> Sending Mail... [" + documents + ", " + date + "]").replaceAll("\\r", "\n"));
        try {
            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host", "localhost");
            Session session = Session.getDefaultInstance(properties);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("visa@gultak.de"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(targetEmail));
            message.setSubject("Visa #" + visaNumber + " is ready to pickup!", "ISO-8859-1");
            message.setText("Hi,\n\nYour visa application is ready." +
                    "\nThe visa should be picked up till " + date +
                    "\nPlease bring the following documents: " + documents +
                    "\n" +
                    "\nPlease check under " + visaURL + " for further information." +
                    "\n" +
                    "\nYour private visa checker.", "UTF-8");
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
