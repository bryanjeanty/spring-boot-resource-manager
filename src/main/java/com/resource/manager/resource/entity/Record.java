package com.resource.manager.resource.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "record")
public class Record implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("type")
    @Column(name = "type")
    private String type;

    @JsonProperty("typeId")
    @Column(name = "type_id")
    private int typeId;

    @JsonProperty("keys")
    @Column(name = "keys")
    private String keys;

    @JsonProperty("keyValues")
    @Column(name = "key_values")
    private String keyValues;

    @JsonProperty("dataTypes")
    @Column(name = "data_types")
    private String dataTypes;

    /*@JsonProperty("version")
    private int version;*/

    @JsonProperty("editable")
    private boolean editable;

    @Column(name = "encrypted_filename")
    private String encryptedFilename;

    public Record() {
        super();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getKeys() {
        return keys;
    }

    public String getKeyValues() {
        return keyValues;
    }

    public String getDataTypes() {
        return dataTypes;
    }

    /*public int getVersion() {
        return version;
    }*/

    public boolean getEditable() {
        return editable;
    }

    public String getEncryptedFilename() {
        return encryptedFilename;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public void setKeyValues(String keyValues) {
        this.keyValues = keyValues;
    }

    public void setDataTypes(String dataTypes) {
        this.dataTypes = dataTypes;
    }

    /*public void setVersion(int version) {
        this.version = version;
    }*/

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setEncryptedFilename(String encryptedFilename) {
        this.encryptedFilename = encryptedFilename;
    }
}