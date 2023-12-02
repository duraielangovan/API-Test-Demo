package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContainerMeta{
    public String u_workphone;
    public String u_division;
    public String riskthreatname;
    public String lastdetectiontime;
    public String u_createdate;
    public String u_hiredate;
    public String entityid;
    public String u_encrypted;
    public String u_costcentername;
    public String rg_id;
    public String risktypeid;
    public String u_id;
    public String id;
    public String u_country;
    public String resourcedata;
    public String u_companycode;
    public String u_lastname;
    public String u_firstname;
    public String u_userid;
    public String tenantname;
    public String policyname;
    public String resourcename;
    public String rg_type;
    public String policyviolator;
    public String u_employeeid;
    public String u_department;
    public String u_jobcode;
    public String userdata;
    public String riskthreatid;
    public String policyid;
    public String u_employeetypedescription;
    public String tenantid;
    public String u_status;
    public String rg_resourcetypeid;
    public String uniqueid;
    public String categoryid;
    public String timestamp;
    public String u_lanid;
    public String rg_name;
    public String violator;
    public String u_title;
    public String sandbox;
    public String policycategory;
    public String resourcetype;
    public String doctype;
    public String u_statusdescription;
    public String lastgeneratetime;
    public String u_fullname;
    public String u_masked;
    public String u_employeetype;
    public String _version_;
    public String u_workemail;
    public String riskscore;
    public String u_manageremployeeid;
    public String u_location;
    public String category;
    public String remarks;
    public String incrementflag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContainerMeta)) return false;
        ContainerMeta that = (ContainerMeta) o;
        return lastdetectiontime == that.lastdetectiontime && u_createdate == that.u_createdate && u_hiredate == that.u_hiredate && u_encrypted == that.u_encrypted && rg_id == that.rg_id && risktypeid == that.risktypeid && u_id == that.u_id && riskthreatid == that.riskthreatid && policyid == that.policyid && tenantid == that.tenantid && u_status == that.u_status && rg_resourcetypeid == that.rg_resourcetypeid && categoryid == that.categoryid && timestamp == that.timestamp && sandbox == that.sandbox && lastgeneratetime == that.lastgeneratetime && u_masked == that.u_masked && _version_ == that._version_ && riskscore == that.riskscore && incrementflag == that.incrementflag && Objects.equals(u_workphone, that.u_workphone) && Objects.equals(u_division, that.u_division) && Objects.equals(riskthreatname, that.riskthreatname) && Objects.equals(entityid, that.entityid) && Objects.equals(u_costcentername, that.u_costcentername) && Objects.equals(id, that.id) && Objects.equals(u_country, that.u_country) && Objects.equals(resourcedata, that.resourcedata) && Objects.equals(u_companycode, that.u_companycode) && Objects.equals(u_lastname, that.u_lastname) && Objects.equals(u_firstname, that.u_firstname) && Objects.equals(u_userid, that.u_userid) && Objects.equals(tenantname, that.tenantname) && Objects.equals(policyname, that.policyname) && Objects.equals(resourcename, that.resourcename) && Objects.equals(rg_type, that.rg_type) && Objects.equals(policyviolator, that.policyviolator) && Objects.equals(u_employeeid, that.u_employeeid) && Objects.equals(u_department, that.u_department) && Objects.equals(u_jobcode, that.u_jobcode) && Objects.equals(userdata, that.userdata) && Objects.equals(u_employeetypedescription, that.u_employeetypedescription) && Objects.equals(uniqueid, that.uniqueid) && Objects.equals(u_lanid, that.u_lanid) && Objects.equals(rg_name, that.rg_name) && Objects.equals(violator, that.violator) && Objects.equals(u_title, that.u_title) && Objects.equals(policycategory, that.policycategory) && Objects.equals(resourcetype, that.resourcetype) && Objects.equals(doctype, that.doctype) && Objects.equals(u_statusdescription, that.u_statusdescription) && Objects.equals(u_fullname, that.u_fullname) && Objects.equals(u_employeetype, that.u_employeetype) && Objects.equals(u_workemail, that.u_workemail) && Objects.equals(u_manageremployeeid, that.u_manageremployeeid) && Objects.equals(u_location, that.u_location) && Objects.equals(category, that.category) && Objects.equals(remarks, that.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(u_workphone, u_division, riskthreatname, lastdetectiontime, u_createdate, u_hiredate, entityid, u_encrypted, u_costcentername, rg_id, risktypeid, u_id, id, u_country, resourcedata, u_companycode, u_lastname, u_firstname, u_userid, tenantname, policyname, resourcename, rg_type, policyviolator, u_employeeid, u_department, u_jobcode, userdata, riskthreatid, policyid, u_employeetypedescription, tenantid, u_status, rg_resourcetypeid, uniqueid, categoryid, timestamp, u_lanid, rg_name, violator, u_title, sandbox, policycategory, resourcetype, doctype, u_statusdescription, lastgeneratetime, u_fullname, u_masked, u_employeetype, _version_, u_workemail, riskscore, u_manageremployeeid, u_location, category, remarks, incrementflag);
    }
}

