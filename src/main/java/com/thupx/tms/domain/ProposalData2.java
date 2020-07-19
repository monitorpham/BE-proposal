package com.thupx.tms.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.ChronoUnit;

public class ProposalData2 implements Serializable {
	private Proposal proposal;
	private Long progressDetailId;
	private String currentProgressName;
	private Integer remainingDate;
	private ZonedDateTime deadLine;
	
	public ProposalData2(Proposal proposal, Long progressDetailId, String currentProgressName, ZonedDateTime deadLine, Integer remainingDate) {
		super();
		this.proposal = proposal;
		this.progressDetailId = progressDetailId;
		this.currentProgressName = currentProgressName;
//		this.remainingDate = calDeadLine(ZonedDateTime.now(), proposal.getStartDate(), ChronoUnit.DAYS);
		this.remainingDate = remainingDate;
		this.deadLine = deadLine;
	}
	public Proposal getProposal() {
		return proposal;
	}
	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}
	public Long getProgressDetailId() {
		return progressDetailId;
	}
	public void setProgressDetailId(Long progressDetailId) {
		this.progressDetailId = progressDetailId;
	}
	public String getCurrentProgressName() {
		return currentProgressName;
	}
	public void setCurrentProgressName(String currentProgressName) {
		this.currentProgressName = currentProgressName;
	}
	public Integer getRemainingDate() {
		return remainingDate;
	}
	public void setRemainingDate(Integer remainingDate) {
		this.remainingDate = remainingDate;
	}
	public ZonedDateTime getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(ZonedDateTime deadLine) {
		this.deadLine = deadLine;
	}
	
	
//	private Integer calDeadLine(ZonedDateTime currentDate,ZonedDateTime createDateProposal,ChronoUnit unit) {
//		return (int) (long) unit.between(createDateProposal,currentDate);
//	}
	
}
