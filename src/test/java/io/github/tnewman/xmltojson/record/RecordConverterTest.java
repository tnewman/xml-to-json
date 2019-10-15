package io.github.tnewman.xmltojson.record;

import io.github.tnewman.xmltojson.XMLToJSONException;
import io.github.tnewman.xmltojson.mapping.AttributeMapping;
import io.github.tnewman.xmltojson.mapping.AttributeType;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RecordConverterTest {

    private RecordConverter recordConverter;

    @Before
    public void before() {
        this.recordConverter = new RecordConverter();
    }

    @Test
    public void testConvertRecordsWithSameTypes() {
        List<AttributeMapping> attributeMapping = Collections.singletonList(new AttributeMapping("source",
                AttributeType.STRING, "destination", AttributeType.STRING));

        List<Record> records = Collections.singletonList(
                new Record(Collections.singletonList(new Attribute("source", "test"))));

        List<Record> convertedRecords = recordConverter.convertRecords(attributeMapping, records);

        Record record = convertedRecords.get(0);
        assertThat(record.getAttributes().get(0).getName()).isEqualTo("destination");
        assertThat(record.getAttributes().get(0).getValue()).isEqualTo("test");
    }

    @Test
    public void testConvertRecordsWithStateToAbbreviatedState() {
        List<AttributeMapping> attributeMapping = Collections.singletonList(new AttributeMapping("state",
                AttributeType.STATE, "state", AttributeType.ABBREVIATED_STATE));

        List<Record> records = Collections.singletonList(
                new Record(Collections.singletonList(new Attribute("state", "Michigan"))));

        List<Record> convertedRecords = recordConverter.convertRecords(attributeMapping, records);

        Record record = convertedRecords.get(0);
        assertThat(record.getAttributes().get(0).getName()).isEqualTo("state");
        assertThat(record.getAttributes().get(0).getValue()).isEqualTo("MI");
    }

    @Test(expected = XMLToJSONException.class)
    public void testConvertRecordsWithStateToAbbreviatedStateWithBadState() {
        List<AttributeMapping> attributeMapping = Collections.singletonList(new AttributeMapping("state",
                AttributeType.STATE, "state", AttributeType.ABBREVIATED_STATE));

        List<Record> records = Collections.singletonList(
                new Record(Collections.singletonList(new Attribute("state", "M i"))));

        recordConverter.convertRecords(attributeMapping, records);
    }

    @Test
    public void testConvertRecordsWithGenderToSex() {
        List<AttributeMapping> attributeMapping = Collections.singletonList(new AttributeMapping("gender",
                AttributeType.GENDER_CHARACTER, "sex", AttributeType.SEX));

        List<Record> records = Collections.singletonList(
                new Record(Collections.singletonList(new Attribute("gender", 'f'))));

        List<Record> convertedRecords = recordConverter.convertRecords(attributeMapping, records);

        Record record = convertedRecords.get(0);
        assertThat(record.getAttributes().get(0).getName()).isEqualTo("sex");
        assertThat(record.getAttributes().get(0).getValue()).isEqualTo("female");
    }

    @Test(expected = XMLToJSONException.class)
    public void testConvertRecordsWithBadGender() {
        List<AttributeMapping> attributeMapping = Collections.singletonList(new AttributeMapping("gender",
                AttributeType.GENDER_CHARACTER, "sex", AttributeType.SEX));

        List<Record> records = Collections.singletonList(
                new Record(Collections.singletonList(new Attribute("gender", 'b'))));

        recordConverter.convertRecords(attributeMapping, records);
    }

    @Test
    public void testConvertRecordsWithDateToAge() {
        List<AttributeMapping> attributeMapping = Collections.singletonList(new AttributeMapping(
                "dateOfBirth", AttributeType.DATE, "age", AttributeType.AGE));

        List<Record> records = Collections.singletonList(
                new Record(Collections.singletonList(new Attribute("dateOfBirth", new Date()))));

        List<Record> convertedRecords = recordConverter.convertRecords(attributeMapping, records);

        Record record = convertedRecords.get(0);
        assertThat(record.getAttributes().get(0).getName()).isEqualTo("age");
        assertThat(record.getAttributes().get(0).getValue()).isEqualTo(0);
    }

    @Test(expected = XMLToJSONException.class)
    public void testConvertRecordsWithMismatchedTypes() {
        List<AttributeMapping> attributeMapping = Collections.singletonList(new AttributeMapping(
                "dateOfBirth", AttributeType.DATE, "age", AttributeType.AGE));

        List<Record> records = Collections.singletonList(
                new Record(Collections.singletonList(new Attribute("dateOfBirth", "test"))));

        recordConverter.convertRecords(attributeMapping, records);
    }

    @Test(expected = XMLToJSONException.class)
    public void testConvertRecordsWithMissingSourceAttribute() {
        List<AttributeMapping> attributeMapping = Collections.singletonList(new AttributeMapping(
                "test", AttributeType.DATE, "test", AttributeType.AGE));

        List<Record> records = Collections.singletonList(
                new Record(Collections.singletonList(new Attribute("test2", "test2"))));

        recordConverter.convertRecords(attributeMapping, records);
    }

    @Test(expected = XMLToJSONException.class)
    public void testConvertRecordsWithUnsupportedConversion() {
        List<AttributeMapping> attributeMapping = Collections.singletonList(new AttributeMapping(
                "test", AttributeType.INTEGER, "test", AttributeType.STRING));

        List<Record> records = Collections.singletonList(
                new Record(Collections.singletonList(new Attribute("test", 1))));

        recordConverter.convertRecords(attributeMapping, records);
    }
}
