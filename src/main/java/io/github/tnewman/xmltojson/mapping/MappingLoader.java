package io.github.tnewman.xmltojson.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.tnewman.xmltojson.XMLToJSONException;

import java.io.IOException;
import java.nio.file.Path;

public class MappingLoader {

    private ObjectMapper objectMapper = new ObjectMapper();

    public ListMapping loadMappingsFromJSON(final Path mappingsFile) {
        try {
            return this.objectMapper.readValue(mappingsFile.toFile(), ListMapping.class);
        } catch (IOException e) {
            throw new XMLToJSONException(e);
        }
    }
}
