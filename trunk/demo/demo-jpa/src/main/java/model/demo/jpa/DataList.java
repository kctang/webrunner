package model.demo.jpa;

import net.big2.webrunner.core.jpa.crud.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class DataList extends BaseEntity {
    @NotEmpty
    @Size(min = 3, max = 250)
    private String dataKey;

    @ElementCollection
    private List<String> valueList;

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    @Override
    public String getDisplayName() {
        return getDataKey();
    }
}
