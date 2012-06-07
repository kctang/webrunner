package net.big2.webrunner.core.jpa.crud.support;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;
import net.big2.webrunner.core.jpa.crud.fg.FieldGenerator;
import net.big2.webrunner.core.jpa.crud.fg.FieldGeneratorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.multipart.MultipartRequest;

import javax.annotation.PostConstruct;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.beans.PropertyEditorSupport;
import java.util.*;

import static java.lang.String.format;
import static net.big2.webrunner.core.jpa.crud.fg.ReflectionUtils.*;

public class DefaultCrudSupport<T extends CrudEntity> implements CrudSupport<T> {
    protected Logger log = LoggerFactory.getLogger(DefaultCrudSupport.class);

    @Autowired
    protected ApplicationContext applicationContext;

    protected boolean visible;
    protected Class<T> crudEntityClass;
    protected String slug;
    protected String name;
    protected String templatePath;
    protected JpaRepository<T, Long> jpaRepository;
    protected List<String> newFieldList;
    protected List<String> editFieldList;
    protected List<String> listFieldList;
    protected int listFieldLinkIndex;
    protected Map<String, Field> newFieldMap;
    protected Map<String, Field> editFieldMap;
    protected Map<String, Field> listFieldMap;

    protected FieldGenerator fieldGenerator;
    protected Map<String, JpaRepository> jpaRepositoryMap;

    public static final String DEFAULT_TEMPLATE_PATH = "obj/";

    /**
     *
     * @param crudEntityClass
     * @param jpaRepository
     * @param fieldGenerator
     */
    public DefaultCrudSupport(
            Class<T> crudEntityClass, JpaRepository<T, Long> jpaRepository,
            FieldGenerator fieldGenerator) {
        this.visible = true;
        this.crudEntityClass = crudEntityClass;
        this.jpaRepository = jpaRepository;
        this.fieldGenerator = fieldGenerator;
        templatePath = DEFAULT_TEMPLATE_PATH;
        name = Field.toLabel(crudEntityClass.getSimpleName());
        slug = Field.toSlug(crudEntityClass.getSimpleName());

        newFieldList = new ArrayList<String>();
        editFieldList = new ArrayList<String>();
        listFieldList = new ArrayList<String>();
        listFieldLinkIndex = 0;
        newFieldMap = new HashMap<String, Field>();
        editFieldMap = new HashMap<String, Field>();
        listFieldMap = new HashMap<String, Field>();
    }

    @PostConstruct
    @Override
    public void init() throws CrudSupportException {
        initFieldMaps();

        jpaRepositoryMap = applicationContext.getBeansOfType(JpaRepository.class);
    }

    @Override
    public void initFieldMaps() throws CrudSupportException {
        try {
            fieldGenerator.populateFieldList(crudEntityClass, newFieldList);
            fieldGenerator.populateFieldList(crudEntityClass, editFieldList);
            fieldGenerator.populateFieldList(crudEntityClass, listFieldList);

            fieldGenerator.populateFieldMap(crudEntityClass, newFieldList, newFieldMap);
            fieldGenerator.populateFieldMap(crudEntityClass, editFieldList, editFieldMap);
            fieldGenerator.populateFieldMap(crudEntityClass, listFieldList, listFieldMap);
        } catch (FieldGeneratorException e) {
            throw new CrudSupportException("Error initializing field maps", e);
        }
    }

    @Override
    public void initBinder(WebDataBinder binder) {
        // enable type conversion from String to CrudEntity
        binder.registerCustomEditor(crudEntityClass, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                T entity = getJpaRepository().findOne(Long.parseLong(text));
                setValue(entity);
            }

            @Override
            public String getAsText() {
                Object object = getValue();
                if (object instanceof CrudEntity) {
                    CrudEntity entity = (CrudEntity) object;
                    return entity.getId().toString();
                } else {
                    return null;
                }
            }
        });
    }

    @Override
    public boolean onEditSave(T t, BindingResult result, MultipartRequest request) throws CrudSupportException {
        return true;
    }

    @Override
    public boolean onNewSave(T t, BindingResult result, MultipartRequest request) throws CrudSupportException {
        return true;
    }

    @Override
    public void postEditSave(T t, MultipartRequest request) throws CrudSupportException {
        // do nothing
    }

    @Override
    public void postNewSave(T t, MultipartRequest request) throws CrudSupportException {
        // do nothing
    }

    @Override
    public String formatListHeader(String fieldName) {
        return Field.toLabel(fieldName);
    }

    @Override
    public String formatListValue(String fieldName, Object value, T t) {
        if (value == null) {
            return "-";
        } else {
            // TODO: refactor use generics, remove instanceof
            if (value instanceof CrudEntity) {
                return ((CrudEntity) value).getDisplayName();
            } else {
                return value.toString();
            }
        }
    }

    @Override
    public Object loadNewItems(Field field, T crudEntity, Model model) {
        return loadItems(field, crudEntity, newFieldMap, model);
    }

    @Override
    public Object loadEditItems(Field field, T crudEntity, Model model) {
        return loadItems(field, crudEntity, editFieldMap, model);
    }

    protected Object loadItems(Field field, T crudEntity, Map<String, Field> fieldMap, Model model) {
        Object retVal = null;

        // TODO: must refactor this!
        // TODO: consideration for a matching strategy here similar to Fgs

        String fieldName = field.getEntityProperty();

        // auto load data for fields annotated with @ManyToMany
        if (field.getType() == Field.Type.SELECT &&
                isFieldAnnotatedWith(fieldName, crudEntityClass, ManyToMany.class)) {

            // proper resolution of fieldName >>> repository name
            // remove "s" from fieldName. i.e. "Users" should be "User"
            // TODO: make the "Repository" postfix configurable
            String repositoryName = fieldName.substring(0, fieldName.length() - 1) + "Repository";

            JpaRepository fieldRepository = jpaRepositoryMap.get(repositoryName);
            if (fieldRepository != null) {
                // load all values into map
                List<CrudEntity> crudEntityList = fieldRepository.findAll();
                Map<String, String> itemMap = new LinkedHashMap<String, String>();
                for (CrudEntity entity : crudEntityList) {
                    itemMap.put(entity.getId().toString(), entity.getDisplayName());
                }
                retVal = itemMap;

            } else {
                log.warn(format("Cannot find repository to load data for field [%s]", fieldName));
            }

        } else if (field.getType() == Field.Type.SELECT &&
                isFieldAnnotatedWith(fieldName, crudEntityClass, OneToOne.class)) {
            // TODO: make the "Repository" postfix configurable
            String repositoryName = fieldName + "Repository";

            JpaRepository fieldRepository = jpaRepositoryMap.get(repositoryName);
            if (fieldRepository != null) {
                // load all values into map
                List<CrudEntity> crudEntityList = fieldRepository.findAll();
                Map<String, String> itemMap = new LinkedHashMap<String, String>();
                if (field.isOptional()) {
                    itemMap.put("0", "---Select One---");
                }
                for (CrudEntity entity : crudEntityList) {
                    itemMap.put(entity.getId().toString(), entity.getDisplayName());
                }
                retVal = itemMap;

            } else {
                log.warn(format("Cannot find repository to load data for field [%s]", fieldName));
            }

        } else if (field.getType() == Field.Type.SELECT &&
                isFieldAnnotatedWith(fieldName, crudEntityClass, ManyToOne.class)) {
            // TODO: make the "Repository" postfix configurable
            String repositoryName = fieldName + "Repository";

            JpaRepository fieldRepository = jpaRepositoryMap.get(repositoryName);
            if (fieldRepository != null) {
                // load all values into map
                List<CrudEntity> crudEntityList = fieldRepository.findAll();
                Map<String, String> itemMap = new LinkedHashMap<String, String>();
                if (field.isOptional()) {
                    itemMap.put("0", "---Select One---");
                }
                for (CrudEntity entity : crudEntityList) {
                    itemMap.put(entity.getId().toString(), entity.getDisplayName());
                }
                retVal = itemMap;

            } else {
                log.warn(format("Cannot find repository to load data for field [%s]", fieldName));
            }

        }

        return retVal;
    }

    // === [ getters/setters ] ===
    @Override
    public Class<T> getCrudEntityClass() {
        return crudEntityClass;
    }

    public void setCrudEntityClass(Class<T> crudEntityClass) {
        this.crudEntityClass = crudEntityClass;
    }

    @Override
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public JpaRepository<T, Long> getJpaRepository() {
        return jpaRepository;
    }

    public void setJpaRepository(JpaRepository<T, Long> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<String> getNewFieldList() {
        return newFieldList;
    }

    public void setNewFieldList(List<String> newFieldList) {
        this.newFieldList = newFieldList;
    }

    @Override
    public List<String> getEditFieldList() {
        return editFieldList;
    }

    public void setEditFieldList(List<String> editFieldList) {
        this.editFieldList = editFieldList;
    }

    @Override
    public int getListFieldLinkIndex() {
        return listFieldLinkIndex;
    }

    public void setListFieldLinkIndex(int listFieldLinkIndex) {
        this.listFieldLinkIndex = listFieldLinkIndex;
    }

    @Override
    public List<String> getListFieldList() {
        return listFieldList;
    }

    public void setListFieldList(List<String> listFieldList) {
        this.listFieldList = listFieldList;
    }

    @Override
    public Map<String, Field> getNewFieldMap() {
        return newFieldMap;
    }

    public void setNewFieldMap(Map<String, Field> newFieldMap) {
        this.newFieldMap = newFieldMap;
    }

    @Override
    public Map<String, Field> getEditFieldMap() {
        return editFieldMap;
    }

    public void setEditFieldMap(Map<String, Field> editFieldMap) {
        this.editFieldMap = editFieldMap;
    }

    @Override
    public Map<String, Field> getListFieldMap() {
        return listFieldMap;
    }

    public void setListFieldMap(Map<String, Field> listFieldMap) {
        this.listFieldMap = listFieldMap;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
