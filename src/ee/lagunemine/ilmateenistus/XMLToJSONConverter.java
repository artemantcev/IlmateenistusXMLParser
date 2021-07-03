package ee.lagunemine.ilmateenistus;

import org.json.JSONObject;
import org.json.XML;

public class XMLToJSONConverter implements StringConvertible {

    private final String xml;

    /**
     * Constructor
     *
     * @param xml raw XML data from external source.
     */
    public XMLToJSONConverter(String xml) {
        this.xml = xml;
    }

    /**
     * Convert a raw XML string to JSON string.
     *
     * @return String with JSON data.
     */
    public String convertToString() {
        return XML.toJSONObject(xml).toString();
    }
}
