package com.resource.manager.resource.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "data")
public class Data implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @JsonProperty("name")
    @Column(name = "Name", length = 100)
    private String name;

    @NotBlank
    @JsonProperty("code")
    @Column(name = "Code")
    private int code;

    @JsonProperty("versionNumber")
    @Column(name = "VersionNumber")
    private int versionNumber;

    private String columnNames;

    private String columnValues;

    public Data() {
        super();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getCode() {
        return this.code;
    }

    public int getVersionNumber() {
        return this.versionNumber;
    }

    @JsonProperty("columnNames")
    public String getColumnNames() {
        return this.columnNames;
    }

    @JsonProperty("columnValues")
    public String getColumnValues() {
        return this.columnValues;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber == null ? 0 : versionNumber;
    }

    public void setColumnNames(String columnNames) {
        this.columnNames = columnNames;
    }

    public void setColumnValues(String columnValues) {
        this.columnValues = columnValues;
    }
}