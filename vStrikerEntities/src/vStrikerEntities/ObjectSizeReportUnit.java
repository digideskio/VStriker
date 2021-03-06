package vStrikerEntities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the OBJECT_SIZE_REPORT_UNIT database table.
 * 
 */
@Entity
@Table(name="OBJECT_SIZE_REPORT_UNIT", schema="VSTRIKERDB")
@NamedQuery(name="ObjectSizeReportUnit.findAll", query="SELECT o FROM ObjectSizeReportUnit o")

public class ObjectSizeReportUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="OBJECT_SIZE_REPORT_UNIT_ID")
	private int objectSizeReportUnitId;

	@Column(name="REPORT_UNIT_NAME")
	private String reportUnitName;

	@Column(name="REPORT_UNIT_VALUE")
	private String reportUnitValue;




	public ObjectSizeReportUnit() {
	}

	public int getObjectSizeReportUnitId() {
		return this.objectSizeReportUnitId;
	}

	public void setObjectSizeReportUnitId(int objectSizeReportUnitId) {
		this.objectSizeReportUnitId = objectSizeReportUnitId;
	}

	public String getReportUnitName() {
		return this.reportUnitName;
	}

	public void setReportUnitName(String reportUnitName) {
		this.reportUnitName = reportUnitName;
	}

	public String getReportUnitValue() {
		return this.reportUnitValue;
	}

	public void setReportUnitValue(String reportUnitValue) {
		this.reportUnitValue = reportUnitValue;
	}

	   @Override
	    public String toString() {
	        return this.reportUnitName;
	    }

}