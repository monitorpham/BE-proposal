package com.thupx.tms.domain;

import java.io.Serializable;

import com.thupx.tms.service.dto.ProgessDetaillDTO;

public class ProposalData implements Serializable{
	private Proposal proposal;
	private ProgessDetaillDTO currentProgress;
	public ProposalData(Proposal proposal, ProgessDetaillDTO currentProgress) {
		super();
		this.proposal = proposal;
		this.currentProgress = currentProgress;
	}
	public Proposal getProposal() {
		return proposal;
	}
	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}
	public ProgessDetaillDTO getCurrentProgress() {
		return currentProgress;
	}
	public void setCurrentProgress(ProgessDetaillDTO currentProgress) {
		this.currentProgress = currentProgress;
	}
	
	

	
	
}
