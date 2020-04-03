package com.test.emailservice.modules.email.entities;

import com.test.emailservice.core.entities.AbstractEntity;
import com.test.emailservice.modules.email.presenters.SmtpEmailPresenter;
import com.test.emailservice.modules.email.presenters.VendorEmailPresenter;
import com.test.emailservice.modules.email.resources.VendorEmailResource;
import com.test.emailservice.modules.email.resources.VendorResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE vendor_emails SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?", check = ResultCheckStyle.COUNT)
@Table(name = "vendor_emails")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorEmail extends AbstractEntity<Long> {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "email_from")
    private String from;
    @Column(name = "email_to")
    private String to;
    private String subject;
    private String message;

//    @Column(name = "host_id")
//    private int hostId;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Vendor.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private Vendor vendor;

//    public int getHostId() {
//        return hostId;
//    }
//
//    public void setHostId(int hostId) {
//        this.hostId = hostId;
//    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public VendorEmailResource toVendorEmailResource() {
         VendorEmailPresenter resource = new VendorEmailPresenter();
         return  resource.map(this);
    }
}
