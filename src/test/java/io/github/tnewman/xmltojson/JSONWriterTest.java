package io.github.tnewman.xmltojson;

import io.github.tnewman.xmltojson.JSONWriter;
import io.github.tnewman.xmltojson.record.Attribute;
import io.github.tnewman.xmltojson.record.Record;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JSONWriterTest {

    private JSONWriter jsonWriter;

    @Before
    public void before() {
        this.jsonWriter = new JSONWriter();
    }

    @Test
    public void testWriteRecordsToJSON() throws IOException {
        List<Record> records = Arrays.asList(
                new Record(Collections.singletonList(new Attribute("testString", "test"))),
                new Record(Collections.singletonList(new Attribute("testInt", 1))));

        Path jsonFile = Files.createTempFile("records", ".json");

        this.jsonWriter.writeRecordsToJSON(records, jsonFile);

        assertThat(new String(Files.readAllBytes(jsonFile))).isEqualTo("[{\"testString\":\"test\"},{\"testInt\":1}]");
    }
}
