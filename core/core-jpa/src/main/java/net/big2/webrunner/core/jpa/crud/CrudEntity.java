package net.big2.webrunner.core.jpa.crud;

public interface CrudEntity {
    Long getId();
    
    void setId(Long id);

    String getDisplayName();
}
