package com.thupx.tms.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Progress.
 */
@Entity
@Table(name = "progress")
public class Progress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content_task")
    private String contentTask;

    @Column(name = "jhi_limit")
    private Integer limit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentTask() {
        return contentTask;
    }

    public Progress contentTask(String contentTask) {
        this.contentTask = contentTask;
        return this;
    }

    public void setContentTask(String contentTask) {
        this.contentTask = contentTask;
    }

    public Integer getLimit() {
        return limit;
    }

    public Progress limit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Progress)) {
            return false;
        }
        return id != null && id.equals(((Progress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Progress{" +
            "id=" + getId() +
            ", contentTask='" + getContentTask() + "'" +
            ", limit=" + getLimit() +
            "}";
    }
}
