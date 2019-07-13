package com.resource.manager.resource.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "formula")
public class Formula implements Serializable {
    private static final long serialVersionUID = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "editable")
    private boolean editable;

    public Formula() {

    }
    public int getId(){
        
        return this.id;

    }
    public int getRecordId()
    {
        return this.id;

    }
    public void setRecordId(int id){
        this.id= id; 
    }

    public boolean getEditable() {
        return editable;
    }
    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}