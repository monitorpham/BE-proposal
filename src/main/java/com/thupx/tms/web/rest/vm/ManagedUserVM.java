package com.thupx.tms.web.rest.vm;

import com.thupx.tms.service.dto.UserDTO;
import javax.validation.constraints.Size;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
    private String phone;
    private Long equiqmentGroupId;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPhone() {
        return phone;
    }
    

    public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getEquiqmentGroupId() {
		return equiqmentGroupId;
	}

	public void setEquiqmentGroupId(Long equiqmentGroupId) {
		this.equiqmentGroupId = equiqmentGroupId;
	}

	// prettier-ignore
    @Override
    public String toString() {
        return "ManagedUserVM{" + super.toString() + "} ";
    }
}
