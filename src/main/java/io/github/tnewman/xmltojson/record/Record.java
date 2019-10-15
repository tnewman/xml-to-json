package io.github.tnewman.xmltojson.record;

import java.util.List;
import java.util.Objects;

public class Record {

    private List<Attribute> attributes;

    public Record(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
