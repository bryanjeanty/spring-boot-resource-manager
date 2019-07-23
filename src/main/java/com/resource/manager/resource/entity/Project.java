package com.resource.manager.resource.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project implements Serializable {
    private static final long serialVersionUID = 4L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "version")
    private int version = 0;
    
    @Column(name = "filename")
    private String filename = "";

    public Project() {}

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }
    
    public String getFilename() {
        return filename;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
}