package repository.demo.jpa.datalist;

import model.demo.jpa.DataList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DataListRepository extends JpaRepository<DataList, Long>, DataListRepositoryCustom {
    Page<DataList> findByDataKeyLike(String dataKey, Pageable pageable);

    @Transactional(readOnly = true)
    DataList findByDataKey(String dataKey);
}
