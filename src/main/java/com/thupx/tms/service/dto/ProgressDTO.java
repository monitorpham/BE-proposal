package com.thupx.tms.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.thupx.tms.domain.Progress} entity.
 */
public class ProgressDTO implements Serializable {
    
    private Long id;

    private String contentTask;

    private Integer limit;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentTask() {
        return contentTask;
    }

    public void setContentTask(String contentTask) {
        this.contentTask = contentTask;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgressDTO)) {
            return false;
        }

        return id != null && id.equals(((ProgressDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgressDTO{" +
            "id=" + getId() +
            ", contentTask='" + getContentTask() + "'" +
            ", limit=" + getLimit() +
            "}";
    }
}
