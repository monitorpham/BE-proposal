package com.thupx.tms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A EquiqmentGroup.
 */
@Entity
@Table(name = "equiqment_group")
public class EquiqmentGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name_group")
    private String nameGroup;

    @ManyToOne
    @JsonIgnoreProperties(value = "equiqmentGroups", allowSetters = true)
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public EquiqmentGroup nameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
        return this;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public Department getDepartment() {
        return department;
    }

    public EquiqmentGroup department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquiqmentGroup)) {
            return false;
        }
        return id != null && id.equals(((EquiqmentGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquiqmentGroup{" +
            "id=" + getId() +
            ", nameGroup='" + getNameGroup() + "'" +
            "}";
    }
}
