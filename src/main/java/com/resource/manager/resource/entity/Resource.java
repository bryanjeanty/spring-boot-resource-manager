package com.resource.manager.resource.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "resource")
public class Resource implements Serializable {
		private static final long serialVersionUID = 3L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
		private long id;
		
		@JsonProperty("recordId")
		@Column(name = "record_id")
		private long recordId;
		
		public Resource() {}
		
		public long getId() {
			return id;	
		}
		
		public long getRecordId() {
			return recordId;
		}
		
		public void setRecordId(long recordId) {
			this.recordId = recordId;
		}
}