package com.resource.manager.resource.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "record")
public class Record implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @JsonProperty("type")
    @Column(name = "type", length = 100)
    private String type;

    @JsonProperty("keys")
    @Column(name = "keys")
    private String keys;

    @JsonProperty("keyValues")
    @Column(name = "key_values")
    private String keyValues;

    @JsonProperty("dataType")
    @Column(name = "data_type")
    private String dataType;

    @JsonProperty("version")
    private long version;

    @JsonProperty("editable")
    private boolean editable;

    public Record() {
        super();
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getKeys() {
        return keys;
    }

    public String getKeyValues() {
        return keyValues;
    }

    public String getDataType() {
        return dataType;
    }

    public long getVersion() {
        return version;
    }

    public boolean getEditable() {
        return editable;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public void setKeyValues(String keyValues) {
        this.keyValues = keyValues;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}