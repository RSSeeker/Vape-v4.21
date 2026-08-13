package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;

public class MPlayerEvent_NameFormat
extends Mapping {
    private MappingField hashField;
    private MappingField urlField;

    public String getUrl(Object packetHandle) {
        return (String)this.urlField.getObject(packetHandle);
    }

    public MPlayerEvent_NameFormat() {
        super(MappedClasses.l3);
        Class<String> urlFieldType = String.class;
        boolean urlFieldPublic = true;
        String urlFieldName = "url";
        MPlayerEvent_NameFormat mapping = this;
        this.urlField = mapping.J(urlFieldName, urlFieldPublic, urlFieldType);
        Class<String> hashFieldType = String.class;
        boolean hashFieldPublic = true;
        String hashFieldName = "hash";
        MPlayerEvent_NameFormat hashMapping = this;
        this.hashField = hashMapping.J(hashFieldName, hashFieldPublic, hashFieldType);
    }

    public void setUrl(Object packetHandle, String url) {
        this.urlField.setObject(packetHandle, url);
    }

    public String getHash(Object packetHandle) {
        return (String)this.hashField.getObject(packetHandle);
    }
}

