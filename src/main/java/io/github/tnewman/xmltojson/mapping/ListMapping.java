package io.github.tnewman.xmltojson.mapping;

import java.util.List;

public class ListMapping {

    private String listElementAttributeName;

    private List<AttributeMapping> attributeMappings;

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
}
