package com.thupx.tms.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.thupx.tms.domain.HospitalDepartment} entity.
 */
public class HospitalDepartmentDTO implements Serializable {
    
    private Long id;

    private String hospitalDepartmentName;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHospitalDepartmentName() {
        return hospitalDepartmentName;
    }

    public void setHospitalDepartmentName(String hospitalDepartmentName) {
        this.hospitalDepartmentName = hospitalDepartmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HospitalDepartmentDTO)) {
            return false;
        }

        return id != null && id.equals(((HospitalDepartmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HospitalDepartmentDTO{" +
            "id=" + getId() +
            ", hospitalDepartmentName='" + getHospitalDepartmentName() + "'" +
            "}";
    }
}
