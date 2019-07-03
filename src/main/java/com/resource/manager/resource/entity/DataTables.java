package com.resource.manager.resource.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dataTables")
public class DataTables implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ReferenceTableVersionNumber", nullable = false)
    private int refTableVersionNum;

    @Column(name = "derivedTableVersionNumber", nullable = false)
    private int derTableVersionNum;

    public DataTables() {
    }

    public int getId() {
        return this.id;
    }

    public int getRefTableVersionNum() {
        return this.refTableVersionNum;
    }

    public int getDerTableVersionNum() {
        return this.derTableVersionNum;
    }

    public void setRefTableVersionNum(Integer refTableVersionNum) {
        this.refTableVersionNum = refTableVersionNum == null ? this.refTableVersionNum : refTableVersionNum;
    }

    public void setDerTableVersionNum(Integer derTableVersionNum) {
        this.derTableVersionNum = derTableVersionNum == null ? this.derTableVersionNum : derTableVersionNum;
    }
}