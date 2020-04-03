package com.test.emailservice.modules.users.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.emailservice.core.entities.AbstractEntity;
import com.test.emailservice.modules.users.resources.UserResource;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?", check = ResultCheckStyle.COUNT)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
@NamedStoredProcedureQuery(name = "user.validate",
    procedureName = "validate_users_request",parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "requestData", type = String.class)
})
public class User extends AbstractEntity<Long> {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;


    @Column(name = "mobile_number", nullable = true)
    private String mobileNumber;

    @Column(nullable = true)
    private String website;

    @Column(name = "verified_at", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date verifiedAt;


    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id + "," +
                "\"name\":\"" + name + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"mobileNumber\":\"" + mobileNumber + "\"," +
                "\"website\":\"" + website + "\"," +
                "\"verifiedAt\":\"" + verifiedAt + "\"" +
                "}";
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Date verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public UserResource toUserResource() {
        return UserResource.builder()
                       .id(id)
                       .name(name)
                       .email(email)
                       .mobileNumber(mobileNumber)
                       .website(website)
                       .build();
    }
}
