package com.thupx.tms.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A HospitalDepartment.
 */
@Entity
@Table(name = "hospital_department")
public class HospitalDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "hospital_department_name")
    private String hospitalDepartmentName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHospitalDepartmentName() {
        return hospitalDepartmentName;
    }

    public HospitalDepartment hospitalDepartmentName(String hospitalDepartmentName) {
        this.hospitalDepartmentName = hospitalDepartmentName;
        return this;
    }

    public void setHospitalDepartmentName(String hospitalDepartmentName) {
        this.hospitalDepartmentName = hospitalDepartmentName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HospitalDepartment)) {
            return false;
        }
        return id != null && id.equals(((HospitalDepartment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HospitalDepartment{" +
            "id=" + getId() +
            ", hospitalDepartmentName='" + getHospitalDepartmentName() + "'" +
            "}";
    }
}
