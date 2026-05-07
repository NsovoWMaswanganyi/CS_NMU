import java.io.*;
import java.util.*;

public class Program {

    public static boolean areTagsMatched(String xml) {
        Stack<String> stack = new Stack<>();
        int i = 0;
        while (i < xml.length()) {
            if (xml.charAt(i) == '<') {
                int closeIndex = xml.indexOf('>', i);
                if (closeIndex == -1) {
                    System.out.println("Invalid tag detected");
                    return false;
                }

                String tag = xml.substring(i + 1, closeIndex).trim();
                boolean selfClosing = tag.endsWith("/");
                boolean isClosing = tag.startsWith("/");

                // extract tag name
                String tagName;
                if (selfClosing) {
                    tagName = tag.substring(0, tag.length() - 1).split("\\s+")[0];
                } else if (isClosing) {
                    tagName = tag.substring(1).split("\\s+")[0];
                } else {
                    tagName = tag.split("\\s+")[0];
                }

                tagName = tagName.toLowerCase();

                if (selfClosing) {
                    // Self-closing tag, do nothing
                } else if (isClosing) {
                    if (stack.isEmpty() || !stack.peek().equals(tagName)) {
                        System.out.println("Missing <" + tagName + ">");
                        return false;
                    } else {
                        stack.pop();
                    }
                } else {
                    stack.push(tagName);
                }

                i = closeIndex + 1;
            } else {
                i++;
            }
        }

        if (!stack.isEmpty()) {
            while (!stack.isEmpty()) {
                System.out.println("Missing </" + stack.pop() + ">");
            }
            return false;
        }

        return true;
    }

    public static boolean isMatchedXMLFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            System.out.println("File not found or error reading file: " + filename);
            return false;
        }

        return areTagsMatched(content.toString());
    }

    public Program() {
        String xml1 = "<div><p>Hello <strong>world</strong></p><hr/></div>";
        String xml2 = "<div><p>Hello <strong>world</p><hr/></div>";
        String xml3 = "<div><p></p></div>";
        String xml4 = "<div><p></div></p> ";
        String xml5 = "<div><span></div>";
        String xml6 = "<div><img src='x.jpg'/></div>";

        System.out.println("xml1 is valid: " + areTagsMatched(xml1));
        System.out.println();
        System.out.println("xml2 is valid: " + areTagsMatched(xml2));
        System.out.println();
        System.out.println("xml3 is valid: " + areTagsMatched(xml3));
        System.out.println();
        System.out.println("xml4 is valid: " + areTagsMatched(xml4));
        System.out.println();
        System.out.println("xml5 is valid: " + areTagsMatched(xml5));
        System.out.println();
        System.out.println("xml6 is valid: " + areTagsMatched(xml6));

        // To test with a file: Didn't get time to test this one
        // System.out.println("From file: " + isMatchedXMLFile("path_to_file.xml"));
    }


    public static void main(String[] args) {
        new Program();
    }
}

