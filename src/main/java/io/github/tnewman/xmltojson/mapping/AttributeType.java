package io.github.tnewman.xmltojson.mapping;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AttributeType {
    @JsonProperty("abbreviatedState")
    ABBREVIATED_STATE,

    @JsonProperty("age")
    AGE,

    @JsonProperty("integer")
    INTEGER,

    @JsonProperty("string")
    STRING,

    @JsonProperty("date")
    DATE,

    @JsonProperty("genderCharacter")
    GENDER_CHARACTER,

    @JsonProperty("sex")
    SEX,

    @JsonProperty("state")
    STATE,
}
