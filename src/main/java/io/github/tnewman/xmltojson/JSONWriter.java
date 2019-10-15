package io.github.tnewman.xmltojson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.tnewman.xmltojson.record.Attribute;
import io.github.tnewman.xmltojson.record.Record;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class JSONWriter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public void writeRecordsToJSON(List<Record> records, Path jsonFile) {
        ArrayNode arrayNode = this.objectMapper.createArrayNode();

        for (Record record : records) {
            ObjectNode objectNode = objectMapper.createObjectNode();

            for (Attribute attribute : record.getAttributes()) {
                objectNode.putPOJO(attribute.getName(), attribute.getValue());
            }

            arrayNode.add(objectNode);
        }

        try {
            this.objectMapper.writeValue(jsonFile.toFile(), arrayNode);
        } catch (IOException e) {
            throw new XMLToJSONException(e);
        }
    }
}
