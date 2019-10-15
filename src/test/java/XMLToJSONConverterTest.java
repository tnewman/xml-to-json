import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.tnewman.xmltojson.XMLToJSONConverter;
import io.github.tnewman.xmltojson.XMLToJSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class XMLToJSONConverterTest {

    private XMLToJSONConverter xmlToJSONConverter;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void before() {
        this.xmlToJSONConverter = new XMLToJSONConverter();
    }

    @Test
    public void testConvertXMLToJSON() throws URISyntaxException, IOException {
        Path jsonMappings = Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("testmappings.json")).toURI());

        Path xmlData = Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("testdata.xml")).toURI());

        Path jsonFile = Files.createTempFile("records", ".json");

        this.xmlToJSONConverter.convertXMLToJSON(jsonMappings, xmlData, jsonFile);

        ArrayNode records = (ArrayNode) this.objectMapper.readTree(jsonFile.toFile());

        assertThat(records.get(0).get("patientid").asInt()).isEqualTo(1234);
        assertThat(records.get(0).get("sex").asText()).isEqualTo("male");
        assertThat(records.get(0).get("state").asText()).isEqualTo("MI");
        assertThat(records.get(0).get("name").asText()).isEqualTo("John Smith");
        assertThat(records.get(0).get("age").asInt()).isGreaterThan(55);

        assertThat(records.get(1).get("patientid").asInt()).isEqualTo(5678);
        assertThat(records.get(1).get("sex").asText()).isEqualTo("female");
        assertThat(records.get(1).get("state").asText()).isEqualTo("OH");
        assertThat(records.get(1).get("name").asText()).isEqualTo("Jane Smith");
        assertThat(records.get(1).get("age").asInt()).isGreaterThan(45);
    }

    @Test(expected = XMLToJSONException.class)
    public void testConvertXMLToJSONWithBadData() throws URISyntaxException, IOException {
        Path jsonMappings = Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("testbadmappings.json")).toURI());

        Path xmlData = Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("testbaddata.xml")).toURI());

        Path jsonFile = Files.createTempFile("records", ".json");

        this.xmlToJSONConverter.convertXMLToJSON(jsonMappings, xmlData, jsonFile);
    }
}
