package ee.lagunemine.ilmateenistus;

import org.w3c.dom.Document;

public class Main {

    private final static String url = "http://www.ilmateenistus.ee/ilma_andmed/xml/forecast.php?lang=eng";

    /**
     * Main method.
     *
     * @param args input arguments.
     */
    public static void main(String[] args) {
        ApiClient client = new ApiClient(url);
        Document document = client.getDomFromXML();
        String rawXML = client.getRawXML();

        DOMToFormattedTextConverter domToFormattedTextConverter = new DOMToFormattedTextConverter(document);
        XMLToJSONConverter jsonWeatherDomConverter = new XMLToJSONConverter(rawXML);

        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*= HUMAN READABLE OUTPUT START =*=*=*=*=*=*=*=*=*=*=*=*=*");
        System.out.println(domToFormattedTextConverter.convertToString());
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*= HUMAN READABLE OUTPUT FINISH =*=*=*=*=*=*=*=*=*=*=*=*=*");

        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*= JSON OUTPUT START =*=*=*=*=*=*=*=*=*=*=*=*=*");
        System.out.println(jsonWeatherDomConverter.convertToString());
        System.out.println("=*=*=*=*=*=*=*=*=*=*=*=*=*= JSON OUTPUT FINISH =*=*=*=*=*=*=*=*=*=*=*=*=*");
    }
}
