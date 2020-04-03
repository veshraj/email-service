package com.test.emailservice.modules.email.entities;

import com.test.emailservice.core.entities.AbstractEntity;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE email_service_providers SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?", check = ResultCheckStyle.COUNT)
@Table(name = "email_service_providers")
public class Vendor extends AbstractEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int id;

    private String name;

    @Column(name = "class_name")
    private String className;

    @Column(name = "api_key")
    private String apiKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
