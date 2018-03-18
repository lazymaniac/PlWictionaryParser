package com.mindmap.jane.wiktionary;

import com.mindmap.jane.domain.RawWikiUnit;
import com.mindmap.jane.wiktionary.dictionary.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Przepisuje slownik dictionary do nowego pliku. Usuwa niepotrzebne dane.
 */
@Service
public class WiktionaryXMLExporter {

    private static final Logger LOG = LoggerFactory.getLogger(WiktionaryXMLExporter.class);

    private String absolutePath = System.getProperty("user.dir");
    private final String outputFilename = "dump_wiktionary.xml";

    /**
     * Domyślne wyjscie.
     */
    private PrintWriter outStream;

    public WiktionaryXMLExporter() {
    }

    /**
     * zapisuje baze danych do nowego pliku.
     *
     * @param dictionary
     */
    public void exportDatabase(Dictionary dictionary) {
        openOutputStream(absolutePath + "/" + outputFilename);

        outStream.println("<?xml version=\"1.0\"?>\n<wiki>");

        for (RawWikiUnit rawWikiUnit : dictionary.getRawWikiUnits()) {
            if (rawWikiUnit.getTitle().contains(":"))
                continue;
            outStream.print("<page>\n");
            outStream.print("<title>" + rawWikiUnit.getTitle() + "</title>\n");
            outStream.print("<text>\n");
            outStream.print(rawWikiUnit.getText() + "\n");
            outStream.print("</text>\n");
            outStream.print("</page>\n\n");
        }

        outStream.println("</wiki>");
        outStream.close();

        LOG.info("Saved: " + dictionary.getRawWikiUnits().size() + "raw units");
    }

    private PrintWriter openOutputStream(final String fileName) {
        LOG.info("Export units to file: {}", fileName);
        try {
            File file = new File(fileName);
            file.createNewFile();
            outStream = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            LOG.debug("Error while saving to file", e);
        } catch (IOException e) {
            LOG.debug("Can't create file", e);
        }

        return outStream;
    }
}
