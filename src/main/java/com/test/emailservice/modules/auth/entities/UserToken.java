package com.test.emailservice.modules.auth.entities;

import com.test.emailservice.core.entities.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user_tokens SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?", check = ResultCheckStyle.COUNT)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tokens")
@Builder
public class UserToken extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tokenable_type")
    private String tokenableType;

    @Column(name = "tokenable_id")
    private long tokenalbeId;

    private String token;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTokenableType() {
        return tokenableType;
    }

    public void setTokenableType(String tokenableType) {
        this.tokenableType = tokenableType;
    }

    public long getTokenalbeId() {
        return tokenalbeId;
    }

    public void setTokenalbeId(long tokenalbeId) {
        this.tokenalbeId = tokenalbeId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
