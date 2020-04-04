package com.test.emailservice.modules.email.entities;

import com.test.emailservice.core.entities.AbstractEntity;
import com.test.emailservice.modules.email.presenters.SmtpEmailPresenter;
import com.test.emailservice.modules.email.resources.SmtpEmailResource;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE smtp_emails SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?", check = ResultCheckStyle.COUNT)
@Table(name = "smtp_emails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmtpEmail extends AbstractEntity<Long> {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="email_from")
    private String from;

    @Column(name = "email_to")
    private String to;

    private String cc;
    private String bcc;
    private String subject;
    private String message;

    @Column(name = "user_id")
    private long userId;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Smtp.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private Smtp host;

    public SmtpEmailResource toSmtpEmailResource() {
        SmtpEmailPresenter resource = new SmtpEmailPresenter();
        return  resource.map(this);
    }

}
