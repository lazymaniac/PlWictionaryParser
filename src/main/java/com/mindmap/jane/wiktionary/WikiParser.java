package com.mindmap.jane.wiktionary;

import com.mindmap.jane.domain.RawWikiUnit;
import com.mindmap.jane.wiktionary.dictionary.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WikiParser extends DefaultHandler {

    private static final Logger log = LoggerFactory.getLogger(WikiParser.class);

    private String xmlFile;
    private String tempValue;
    private StringBuilder strBuild;
    private RawWikiUnit tempUnit;

    private Dictionary dictionary;
    private Set<RawWikiUnit> rawUnits;

    int counter = 0;
    boolean isWikiUnit = true;

    public WikiParser(String filename, Dictionary dictionary) {
        this.xmlFile = filename;
        this.dictionary = dictionary;
        this.rawUnits = new HashSet<>();
    }

    public void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);

        try {
            SAXParser parser = factory.newSAXParser();
            XMLReader xmlReader = parser.getXMLReader();
            xmlReader.setContentHandler(this);
            xmlReader.setErrorHandler(new ParserErrorHandler());
            xmlReader.parse(xmlFile);
        } catch (ParserConfigurationException e) {
            log.debug("Parser config error", e);
        } catch (SAXException e) {
            log.debug("SAXException: xml file corrupted", e);
        } catch (IOException e) {
            log.debug("IO error: Missing " + this.xmlFile + " file i /dump/ folder", e);
        }
    }

    @Override
    public final void startElement(final String uri, final String name,
                                   final String elementName, final Attributes attr) throws SAXException {
        strBuild = new StringBuilder();
        if (elementName.equalsIgnoreCase("page")) {
            tempUnit = new RawWikiUnit();
        }
    }

    @Override
    public final void endElement(final String uri, final String name,
                                 final String element) throws SAXException {

        if (element.equals("title")) {
            tempUnit.setTitle(tempValue);
        }

        if (element.equals("ns")) {
            if (tempValue.equals("0")) {
                isWikiUnit = true;
            } else {
                isWikiUnit = false;
            }
        }

        if (element.equals("text")) {
            tempUnit.setText(strBuild.toString());
        }

        if (element.equals("page")) {
            rawUnits.add(tempUnit);
            if (counter++ % 10000 == 0) {
                log.info("Parsed {} units", counter);
            }
            // print(tempUnit.getNazwa());
        }
    }

    @Override
    public final void endDocument() throws SAXException {
        dictionary.setRawWikiUnits(rawUnits);
        log.debug("Found :" + rawUnits.size() + " units.");
        rawUnits = null;
    }

    @Override
    public final void characters(final char[] ac, final int offset, final int count) throws SAXException {
        tempValue = new String(ac, offset, count);
        strBuild.append(tempValue);
    }

    /**
     * Handles and exceptions coming form WnParser.
     *
     * @author Sebastian Fabisz
     */
    private static class ParserErrorHandler implements ErrorHandler {

        /**
         * Helping method. Parses an exception.
         *
         * @param exception throws exception.
         * @return parsed message.
         */
        private String parseException(final SAXParseException exception) {
            String systemID = exception.getSystemId();

            if (systemID == null) {
                systemID = "null";
            }

            return "URI = " + systemID + " Line = " + exception.getLineNumber() + ": " + exception.getMessage();
        }

        @Override
        public void warning(final SAXParseException exception) throws SAXException {
            log.warn("Warning: " + parseException(exception));
        }

        @Override
        public void error(final SAXParseException exception) throws SAXException {
            String message = "Error: " + parseException(exception);
            throw new SAXException(message);
        }

        @Override
        public void fatalError(final SAXParseException exception) throws SAXException {
            String message = "Fatal Error: " + parseException(exception);
            throw new SAXException(message);
        }
    }


}
