package io.github.tnewman.xmltojson;

import io.github.tnewman.xmltojson.mapping.ListMapping;
import io.github.tnewman.xmltojson.mapping.MappingLoader;
import io.github.tnewman.xmltojson.record.Record;
import io.github.tnewman.xmltojson.record.RecordConverter;

import java.nio.file.Path;
import java.util.List;

public class XMLToJSONConverter {

    // Note: In a production application, or if I wanted to use mocks, I would use dependency injection here.

    private MappingLoader mappingLoader = new MappingLoader();

    private XMLLoader xmlLoader = new XMLLoader();

    private JSONWriter jsonWriter = new JSONWriter();

    private RecordConverter recordConverter = new RecordConverter();

    public void convertXMLToJSON(Path mappingsPath, Path xmlPath, Path jsonPath) {
        ListMapping listMapping = this.mappingLoader.loadMappingsFromJSON(mappingsPath);

        List<Record> inputRecords = this.xmlLoader.loadRecordsFromXML(listMapping, xmlPath);

        List<Record> outputRecords = this.recordConverter.convertRecords(
                listMapping.getAttributeMappings(), inputRecords);

        this.jsonWriter.writeRecordsToJSON(outputRecords, jsonPath);
    }
}
