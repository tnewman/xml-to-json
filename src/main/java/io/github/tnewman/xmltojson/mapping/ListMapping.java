package io.github.tnewman.xmltojson.mapping;

import java.util.List;
import java.util.Objects;

public class ListMapping {
    private String listAttributeName;

    private String listElementAttributeName;

    private List<AttributeMapping> attributeMappings;

    public String getListAttributeName() {
        return listAttributeName;
    }

    public void setListAttributeName(String listAttributeName) {
        this.listAttributeName = listAttributeName;
    }

    public String getListElementAttributeName() {
        return listElementAttributeName;
    }

    public void setListElementAttributeName(String listElementAttributeName) {
        this.listElementAttributeName = listElementAttributeName;
    }

    public List<AttributeMapping> getAttributeMappings() {
        return attributeMappings;
    }

    public void setAttributeMappings(List<AttributeMapping> attributeMappings) {
        this.attributeMappings = attributeMappings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListMapping that = (ListMapping) o;
        return Objects.equals(listAttributeName, that.listAttributeName) &&
                Objects.equals(listElementAttributeName, that.listElementAttributeName) &&
                Objects.equals(attributeMappings, that.attributeMappings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listAttributeName, listElementAttributeName, attributeMappings);
    }
}
