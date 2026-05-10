package nmmu.wrap301;

import nmmu.wrap301.menu.Menu;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.lang.reflect.Method;

/**
 * Utility class that builds a Menu from an XML description using reflection.
 */
public class MenuBuilderUtil {

    /**
     * Build a Menu from the given XML file.
     *
     * @param xmlFile path to the XML file
     * @return the constructed Menu
     */
    public static Menu build(String xmlFile) {
        try {
            // Parse XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(xmlFile));
            doc.getDocumentElement().normalize();

            // Root <menu> element
            Element root = doc.getDocumentElement();
            String controllerClassName = root.getAttribute("controller");
            String menuTitle = root.getAttribute("title");

            // Instantiate controller via reflection
            Class<?> controllerClass = Class.forName(controllerClassName);
            Object controller = controllerClass.getDeclaredConstructor().newInstance();

            // Build menu
            return buildMenu(root, controller, menuTitle);

        } catch (Exception e) {
            throw new RuntimeException("Error building menu from XML", e);
        }
    }

    /**
     *  build a Menu or SubMenu from XML.
     */
    private static Menu buildMenu(Element menuElement, Object controller, String title) throws Exception {
        Menu menu = new Menu(title);

        NodeList children = menuElement.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                if (element.getTagName().equals("choice")) {
                    String text = element.getAttribute("text");
                    String action = element.getAttribute("method");

                    Method method = controller.getClass().getMethod(action);
                    menu.add(text, () -> {
                        try {
                            method.invoke(controller);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                } else if (element.getTagName().equals("submenu")) {
                    String text = element.getAttribute("text");
                    String subTitle = element.getAttribute("method"); // submenu title
                    Menu subMenu = buildMenu(element, controller, subTitle);
                    menu.add(text, subMenu);
                }
            }
        }
        return menu;
    }
}

