package com.test.emailservice.modules.users.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequestValidationResult {
    @JsonProperty("email_exists")
    private boolean emailExists;

    @JsonProperty("mobile_number_exists")
    private boolean  mobileNumberExists;

    @JsonProperty("website_exists")
    private boolean websiteExists;

    public boolean isEmailExists() {
        return emailExists;
    }

    public void setEmailExists(boolean emailExists) {
        this.emailExists = emailExists;
    }

    public boolean isMobileNumberExists() {
        return mobileNumberExists;
    }

    public void setMobileNumberExists(boolean mobileNumberExists) {
        this.mobileNumberExists = mobileNumberExists;
    }

    public boolean isWebsiteExists() {
        return websiteExists;
    }

    public void setWebsiteExists(boolean websiteExists) {
        this.websiteExists = websiteExists;
    }
}
