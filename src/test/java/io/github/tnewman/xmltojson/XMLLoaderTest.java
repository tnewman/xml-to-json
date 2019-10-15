package io.github.tnewman.xmltojson;

import io.github.tnewman.xmltojson.mapping.ListMapping;
import io.github.tnewman.xmltojson.mapping.MappingLoader;
import io.github.tnewman.xmltojson.record.Record;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class XMLLoaderTest {

    private MappingLoader mappingLoader = new MappingLoader();

    private XMLLoader xmlLoader;

    @Before
    public void before() {

        this.mappingLoader = new MappingLoader();
        this.xmlLoader = new XMLLoader();
    }

    @Test
    public void testLoadRecordsFromXML() throws URISyntaxException, IOException, ParseException {
        Path jsonMappings = Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("testmappings.json")).toURI());

        Path xmlData = Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("testdata.xml")).toURI());

        ListMapping listMapping = this.mappingLoader.loadMappingsFromJSON(jsonMappings);
        List<Record> records = this.xmlLoader.loadRecordsFromXML(listMapping, xmlData);

        Record patient = records.get(0);

        assertThat(patient.getAttributes().get(0).getName()).isEqualTo("id");
        assertThat(patient.getAttributes().get(0).getValue()).isEqualTo(1234);

        assertThat(patient.getAttributes().get(1).getName()).isEqualTo("gender");
        assertThat(patient.getAttributes().get(1).getValue()).isEqualTo('m');

        assertThat(patient.getAttributes().get(2).getName()).isEqualTo("name");
        assertThat(patient.getAttributes().get(2).getValue()).isEqualTo("John Smith");

        assertThat(patient.getAttributes().get(3).getName()).isEqualTo("state");
        assertThat(patient.getAttributes().get(3).getValue()).isEqualTo("Michigan");

        assertThat(patient.getAttributes().get(4).getName()).isEqualTo("dateOfBirth");
        assertThat(patient.getAttributes().get(4).getValue()).isEqualTo(
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse("1962-01-04T00:03:00.000"));
    }

    @Test(expected = XMLToJSONException.class)
    public void testLoadRecordsFromXMLWithBadRecords() throws URISyntaxException, IOException, ParseException {
        Path jsonMappings = Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("testmappings.json")).toURI());

        Path xmlData = Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("testbaddata.xml")).toURI());

        ListMapping listMapping = this.mappingLoader.loadMappingsFromJSON(jsonMappings);
        this.xmlLoader.loadRecordsFromXML(listMapping, xmlData);
    }
}
