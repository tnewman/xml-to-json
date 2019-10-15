package io.github.tnewman.xmltojson;

import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java -jar xml-to-json.jar PATH_TO_MAPPINGS PATH_TO_XML_INPUT PATH_TO_JSON_OUTPUT");
            return;
        }

        Path mappingsPath = Paths.get(args[0]);
        Path xmlInputPath = Paths.get(args[1]);
        Path jsonOutputPath = Paths.get(args[2]);

        XMLToJSONConverter xmlToJSONConverter = new XMLToJSONConverter();
        xmlToJSONConverter.convertXMLToJSON(mappingsPath, xmlInputPath, jsonOutputPath);
    }
}
