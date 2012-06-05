package repository.demo.jpa.datalist;

import model.demo.jpa.DataList;
import net.big2.webrunner.core.jpa.crud.BaseWebRunnerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DataListRepositoryImpl extends BaseWebRunnerRepository<DataList> implements DataListRepositoryCustom {
    @Override
    public Page<DataList> list(String q, Pageable pageable) {
        DataListRepository repository = (DataListRepository) getJpaRepository();

        return repository.findByDataKeyLike("%" + q + "%", pageable);
    }

    @Override
    public DataList testCreateEntity() {
        DataList dataList = new DataList();
        dataList.setDataKey("dataKey");
        return dataList;
    }
}
