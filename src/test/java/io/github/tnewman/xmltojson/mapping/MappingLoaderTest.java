package io.github.tnewman.xmltojson.mapping;

import io.github.tnewman.xmltojson.XMLToJSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class MappingLoaderTest {

    private MappingLoader mappingLoader;

    @Before
    public void before() {
        this.mappingLoader = new MappingLoader();
    }

    @Test
    public void testLoadMappingsFromJSON() throws URISyntaxException, IOException {
        Path jsonMappings = Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("testmappings.json")).toURI());

        ListMapping listMapping = this.mappingLoader.loadMappingsFromJSON(jsonMappings);

        assertThat(listMapping.getListElementAttributeName()).isEqualTo("patient");

        AttributeMapping idMapping = listMapping.getAttributeMappings().get(0);
        assertThat(idMapping.getSourceName()).isEqualTo("id");
        assertThat(idMapping.getSourceType()).isEqualTo(AttributeType.INTEGER);
        assertThat(idMapping.getDestinationName()).isEqualTo("patientid");
        assertThat(idMapping.getDestinationType()).isEqualTo(AttributeType.INTEGER);
    }

    @Test(expected = XMLToJSONException.class)
    public void testLoadMappingsFromJSONWithBadMappings() throws URISyntaxException, IOException {
        Path jsonMappings = Paths.get(
                Objects.requireNonNull(getClass().getClassLoader().getResource("testbadmappings.json")).toURI());

        this.mappingLoader.loadMappingsFromJSON(jsonMappings);
    }
}
