package com.test.emailservice.modules.email.entities;

import com.test.emailservice.core.entities.AbstractEntity;
import com.test.emailservice.modules.email.resources.SmtpResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE smtp_hosts SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?", check = ResultCheckStyle.COUNT)
@Table(name = "smtp_hosts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Smtp extends AbstractEntity<Integer> {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String host;

    private String username;
    private String password;
    private int port;



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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public SmtpResource toHostResource() {
        return SmtpResource.builder()
                       .id(id)
                       .host(host)
                       .name(name)
                       .username(username)
                       .password(password)
                       .port(port)
                       .build();
    }
}
