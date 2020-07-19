package com.thupx.tms.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * A DTO for the {@link com.thupx.tms.domain.ProgessDetaill} entity.
 */
public class ProgessDetaillDTO implements Serializable {
    
    private Long id;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private String note;


    private Long proposalId;

    private Long progressId;
    
    
    
    public ProgessDetaillDTO() {
		super();
	}

	public ProgessDetaillDTO(Long proposalId, Long progressId) {
		super();
		this.proposalId = proposalId;
		this.progressId = progressId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public Long getProgressId() {
        return progressId;
    }

    public void setProgressId(Long progressId) {
        this.progressId = progressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgessDetaillDTO)) {
            return false;
        }

        return id != null && id.equals(((ProgessDetaillDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgessDetaillDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", note='" + getNote() + "'" +
            ", proposalId=" + getProposalId() +
            ", progressId=" + getProgressId() +
            "}";
    }
}
