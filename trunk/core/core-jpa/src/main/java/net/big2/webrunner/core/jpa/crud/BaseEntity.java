package net.big2.webrunner.core.jpa.crud;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity implements CrudEntity, TimestampSupportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date creationDate;
    private Date modifiedDate;

    @PrePersist
    protected void onCreate() {
        Date now = new Date();
        creationDate = now;
        modifiedDate = now;
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedDate = new Date();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public Date getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
