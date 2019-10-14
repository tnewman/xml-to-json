package io.github.tnewman.xmltojson.record;

import io.github.tnewman.xmltojson.XMLToJSONException;
import io.github.tnewman.xmltojson.mapping.AttributeMapping;
import io.github.tnewman.xmltojson.mapping.AttributeType;
import io.github.tnewman.xmltojson.mapping.ListMapping;
import unitedstates.US;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RecordConverter {
    public List<Record> convertRecords(ListMapping listMapping, List<Record> recordsToConvert) {
        List<Record> convertedRecords = new ArrayList<>();
        HashMap<String, AttributeMapping> mappingsBySourceName = new HashMap<>();

        listMapping.getAttributeMappings().forEach((mapping) -> {
            mappingsBySourceName.put(mapping.getSourceName(), mapping);
        });

        for (Record record : recordsToConvert) {
            List<Attribute> convertedAttributes = new ArrayList<>();

            for (Attribute attribute : record.getAttributes()) {
                AttributeMapping mapping = mappingsBySourceName.get(attribute.getName());

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
                    throw new XMLToJSONException("The list mappings contain an unsupported conversion");
                }
            }

            convertedRecords.add(new Record(convertedAttributes));
        }

        return convertedRecords;
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
        return (int) ChronoUnit.YEARS.between(new Date().toInstant(), dateOfBirth.toInstant());
    }
}
