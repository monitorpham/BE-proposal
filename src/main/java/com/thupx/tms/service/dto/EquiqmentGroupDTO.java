package com.thupx.tms.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.thupx.tms.domain.EquiqmentGroup} entity.
 */
public class EquiqmentGroupDTO implements Serializable {
    
    private Long id;

    private String nameGroup;


    private Long departmentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquiqmentGroupDTO)) {
            return false;
        }

        return id != null && id.equals(((EquiqmentGroupDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquiqmentGroupDTO{" +
            "id=" + getId() +
            ", nameGroup='" + getNameGroup() + "'" +
            ", departmentId=" + getDepartmentId() +
            "}";
    }
}
