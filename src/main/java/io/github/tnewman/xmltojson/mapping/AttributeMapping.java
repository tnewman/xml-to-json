package io.github.tnewman.xmltojson.mapping;

import java.util.Objects;

public class AttributeMapping {

    private String sourceName;

    private AttributeType sourceType;

    private String destinationName;

    private AttributeType destinationType;

    public AttributeMapping() {
        // This empty constructor is needed for Jackson
    }

    public AttributeMapping(String sourceName, AttributeType sourceType, String destinationName,
                            AttributeType destinationType) {
        this.sourceName = sourceName;
        this.sourceType = sourceType;
        this.destinationName = destinationName;
        this.destinationType = destinationType;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public AttributeType getSourceType() {
        return sourceType;
    }

    public void setSourceType(AttributeType sourceType) {
        this.sourceType = sourceType;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public AttributeType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(AttributeType destinationType) {
        this.destinationType = destinationType;
    }
}
