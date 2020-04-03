package com.test.emailservice.modules.users.resources;


import com.test.emailservice.core.resources.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserListRequest extends Pagination {
    String sortBy[] = {"id", "name", "email", "mobileNumber", "website"};

    private String name;
    private String email;
    private String mobileNumber;
    private String website;

    public String[] getSortBy() {
        return sortBy;
    }

    public void setSortBy(String[] sortBy) {
        this.sortBy = sortBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
