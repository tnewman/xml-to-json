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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(attributes, record.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes);
    }
}
