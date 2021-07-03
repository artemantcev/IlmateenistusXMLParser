package ee.lagunemine.ilmateenistus;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class DOMToFormattedTextConverter implements StringConvertible {

    private final Document document;

    private final static String TAB_LEVEL_FIRST = "\t";
    private final static String TAB_LEVEL_SECOND = "\t\t";
    private final static String TAB_LEVEL_THIRD = "\t\t\t";
    private final static String NEW_LINE = System.getProperty("line.separator");

    /**
     * Constructor.
     *
     * @param document DOM object which should be prettified.
     */
    public DOMToFormattedTextConverter(Document document) {
        this.document = document;
    }

    /**
     * Convert a DOM object to readable format.
     *
     * @return formatted weather output.
     */
    public String convertToString() {
        StringBuilder result = new StringBuilder();
        List<Element> dateElementList = prepareElementList(document.getDocumentElement().getChildNodes());

        for (Element dateElement : dateElementList) {
            this.appendDateData(dateElement, result);
        }

        return result.toString();
    }

    /**
     * Append the daily forecast elements to result.
     *
     * @param dateElement Element object with forecast data for a given date.
     * @param result StringBuilder object for output.
     */
    private void appendDateData(Element dateElement, StringBuilder result) {
        List<Element> dayPartElementList = prepareElementList(dateElement.getChildNodes());

        result.append(NEW_LINE)
                .append("===========================FORECAST FOR DATE ")
                .append(dateElement.getAttribute("date"))
                .append("===========================")
                .append(NEW_LINE);

        for (Element dayPartElement : dayPartElementList) {
            this.appendDayPartData(dayPartElement, result);
        }
    }

    /**
     * Append the day/night elements to result.
     *
     * @param dayPartElement Element object with forecast data for a day/night of a given day.
     * @param result StringBuilder object for output.
     */
    private void appendDayPartData(Element dayPartElement, StringBuilder result) {
        List<Element> dataElementList = prepareElementList(dayPartElement.getChildNodes());

        result.append(TAB_LEVEL_FIRST)
                .append("====For ")
                .append(dayPartElement.getTagName())
                .append("====")
                .append(NEW_LINE);

        for (Element element : dataElementList) {
            String textContent = element.getTextContent();

            switch (element.getTagName()) {
                case "place":
                    this.appendPlaceData(element, result);
                    break;
                case "wind":
                    this.appendWindData(element, result);
                    break;
                case "phenomenon":
                    result.append(TAB_LEVEL_SECOND)
                            .append("Phenomenon: ")
                            .append(textContent)
                            .append(NEW_LINE);
                    break;
                case "tempmin":
                    result.append(TAB_LEVEL_SECOND)
                            .append("Minimum temp.: ")
                            .append(textContent)
                            .append(NEW_LINE);
                    break;
                case "tempmax":
                    result.append(TAB_LEVEL_SECOND)
                            .append("Maximum temp.: ")
                            .append(textContent)
                            .append(NEW_LINE);
                    break;
                case "sea":
                    result.append(TAB_LEVEL_SECOND)
                            .append("Sea status: ")
                            .append("\n")
                            .append(TAB_LEVEL_THIRD)
                            .append(textContent)
                            .append(NEW_LINE)
                            .append(NEW_LINE);
                    break;
                case "peipsi":
                    result.append(TAB_LEVEL_SECOND)
                            .append("Peipsi järv status: ")
                            .append("\n")
                            .append(TAB_LEVEL_THIRD)
                            .append(textContent)
                            .append(NEW_LINE)
                            .append(NEW_LINE);
                    break;
                case "text":
                    result.append(TAB_LEVEL_SECOND)
                            .append("Text: ")
                            .append("\n")
                            .append(TAB_LEVEL_THIRD)
                            .append(textContent)
                            .append(NEW_LINE)
                            .append(NEW_LINE);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Append the place information to result.
     *
     * @param placeElement Element object with day or night forecast data for particular place.
     * @param result StringBuilder object for output.
     */
    private void appendPlaceData(Element placeElement, StringBuilder result) {
        List<Element> placeElementList = prepareElementList(placeElement.getChildNodes());

        result.append(TAB_LEVEL_SECOND)
                .append("Place information:")
                .append(NEW_LINE);

        for (Element element : placeElementList) {
            String textContent = element.getTextContent();

            switch (element.getTagName()) {
                case "name":
                    result.append(TAB_LEVEL_THIRD)
                            .append("Town: ")
                            .append(textContent)
                            .append(NEW_LINE);
                    break;
                case "phenomenon":
                    result.append(TAB_LEVEL_THIRD)
                            .append("Phenomenon: ")
                            .append(textContent)
                            .append(NEW_LINE);
                    break;
                case "tempmin":
                    result.append(TAB_LEVEL_THIRD)
                            .append("Minimum temp.: ")
                            .append(textContent)
                            .append(NEW_LINE);
                    break;
                case "tempmax":
                    result.append(TAB_LEVEL_THIRD)
                            .append("Maximum temp.: ")
                            .append(textContent)
                            .append(NEW_LINE);
                    break;
                default:
                    break;
            }
        }

        result.append(NEW_LINE);
    }

    /**
     * Append the wind information to result.
     *
     * @param windElement Element object with day or night wind data for particular place.
     * @param result StringBuilder object for output.
     */
    private void appendWindData(Element windElement, StringBuilder result) {
        List<Element> placeElementList = prepareElementList(windElement.getChildNodes());

        result.append(TAB_LEVEL_SECOND).append("Wind information:").append(NEW_LINE);

        for (Element element : placeElementList) {
            String textContent = element.getTextContent();

            switch (element.getTagName()) {
                case "name":
                    result.append(TAB_LEVEL_THIRD)
                            .append("Location: ")
                            .append(textContent)
                            .append(NEW_LINE);
                    break;
                case "direction":
                    result.append(TAB_LEVEL_THIRD)
                            .append("Direction of wind: ")
                            .append(textContent)
                            .append(NEW_LINE);
                    break;
                case "gust":
                    result.append(TAB_LEVEL_THIRD)
                            .append("Gusts: yes")
                            .append(NEW_LINE);
                    break;
                default:
                    break;
            }
        }

        result.append(NEW_LINE);
    }

    /**
     * Create List with Elements from a given NodeList.
     *
     * @param nodeList object which should be turned into List of elements.
     */
    private static List<Element> prepareElementList(NodeList nodeList) {
        List<Element> elementList = new ArrayList<Element>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                elementList.add((Element) nodeList.item(i));
            }
        }

        return elementList;
    }
}
