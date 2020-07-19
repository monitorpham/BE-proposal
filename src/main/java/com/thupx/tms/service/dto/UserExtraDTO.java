package com.thupx.tms.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.thupx.tms.domain.UserExtra} entity.
 */
public class UserExtraDTO implements Serializable {
    
    private Long id;

    private String phone;


    private Long userId;

    private String userLogin;

    private Long equiqmentGroupId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getEquiqmentGroupId() {
        return equiqmentGroupId;
    }

    public void setEquiqmentGroupId(Long equiqmentGroupId) {
        this.equiqmentGroupId = equiqmentGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtraDTO)) {
            return false;
        }

        return id != null && id.equals(((UserExtraDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtraDTO{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", equiqmentGroupId=" + getEquiqmentGroupId() +
            ", equiqmentGroupId=" + getEquiqmentGroupId() +
            "}";
    }
}
