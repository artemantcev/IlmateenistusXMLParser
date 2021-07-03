package ee.lagunemine.ilmateenistus;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;

public class ApiClient {

    private final String url;

    /**
     * Constructor.
     *
     * @param url weather website URL.
     */
    public ApiClient(String url) {
        this.url = url;
    }

    /**
     * Get DOM object from external XML source.
     *
     * @return a new DOM document.
     */
    public Document getDomFromXML() {
        Document document;

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(new URL(url).openStream());
        } catch (Exception e) {
            return null;
        }

        return document;
    }

    /**
     * Get raw XML string from external XML source.
     *
     * @return a new XML String.
     */
    public String getRawXML() {
        String xml;

        try {
            xml = IOUtils.toString(new URL(url).openStream());
        } catch (Exception e) {
            return null;
        }

        return xml;
    }
}
