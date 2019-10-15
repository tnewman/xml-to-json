package io.github.tnewman.xmltojson.record;

import io.github.tnewman.xmltojson.XMLToJSONException;
import io.github.tnewman.xmltojson.mapping.AttributeMapping;
import io.github.tnewman.xmltojson.mapping.AttributeType;
import unitedstates.US;

import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RecordConverter {
    public List<Record> convertRecords(List<AttributeMapping> attributeMappings, List<Record> recordsToConvert) {
        List<Record> convertedRecords = new ArrayList<>();
        HashMap<String, AttributeMapping> mappingsBySourceName = new HashMap<>();

        attributeMappings.forEach((mapping) -> {
            mappingsBySourceName.put(mapping.getSourceName(), mapping);
        });

        for (Record record : recordsToConvert) {
            try {
                List<Attribute> convertedAttributes = convertRecord(mappingsBySourceName, record);
                convertedRecords.add(new Record(convertedAttributes));
            } catch (ClassCastException e) {
                throw new XMLToJSONException("The records contained a value that does not match the mappings type.");
            }
        }

        return convertedRecords;
    }

    private List<Attribute> convertRecord(HashMap<String, AttributeMapping> mappingsBySourceName, Record record) {
        List<Attribute> convertedAttributes = new ArrayList<>();

        for (Attribute attribute : record.getAttributes()) {
            AttributeMapping mapping = mappingsBySourceName.get(attribute.getName());

            if (mapping == null) {
                throw new XMLToJSONException("The records contained an unsupported mapping.");
            }

            if (mapping.getSourceType() == mapping.getDestinationType()) {

                convertedAttributes.add(new Attribute(mapping.getDestinationName(), attribute.getValue()));

            } else if (mapping.getSourceType() == AttributeType.STATE &&
                    mapping.getDestinationType() == AttributeType.ABBREVIATED_STATE) {

                String abbreviatedState = convertStateToAbbreviatedState((String) attribute.getValue());
                convertedAttributes.add(new Attribute(mapping.getDestinationName(), abbreviatedState));

            } else if (mapping.getSourceType() == AttributeType.GENDER_CHARACTER &&
                mapping.getDestinationType() == AttributeType.SEX) {

                String sex = convertGenderToSex((char) attribute.getValue());
                convertedAttributes.add(new Attribute(mapping.getDestinationName(), sex));

            } else if (mapping.getSourceType() == AttributeType.DATE &&
                    mapping.getDestinationType() == AttributeType.AGE) {

                int age = convertDateOfBirthToAge((Date) attribute.getValue());
                convertedAttributes.add(new Attribute(mapping.getDestinationName(), age));

            } else {
                throw new XMLToJSONException("The list mappings contain an unsupported conversion.");
            }
        }
        return convertedAttributes;
    }

    private String convertStateToAbbreviatedState(String state) {
        US usState = US.parse(state);

        if (usState == null) {
            throw new XMLToJSONException("The records contain an unsupported state.");
        }

        return usState.getANSIAbbreviation();
    }

    private String convertGenderToSex(char gender) {
        switch(gender) {
            case 'f':
                return "female";
            case 'm':
                return "male";
            default:
                throw new XMLToJSONException("The records contain an unsupported gender.");
        }
    }

    private int convertDateOfBirthToAge(Date dateOfBirth) {
        return Period.between(
                dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        ).getYears();
    }
}
