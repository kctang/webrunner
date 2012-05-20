package net.big2.webrunner.core.jpa.crud;

import java.util.Date;

public interface TimestampSupportEntity {
    Date getCreationDate();

    void setCreationDate(Date creationDate);

    Date getModifiedDate();

    void setModifiedDate(Date modifiedDate);
}
