package net.big2.webrunner.core.jpa.crud.support;

import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;
import java.util.Map;

/**
 * Definition interface for CRUD support.
 *
 * @param <T>
 */
public interface CrudSupport<T extends CrudEntity> {
    Class<T> getCrudEntityClass();

    /**
     * The slug forms part of the URL to provides access to CRUD operations.
     * @return Slug.
     */
    String getSlug();

    /**
     * Human readable name for this object.
     * @return Name.
     */
    String getName();

    /**
     * Base path used to resolve view templates.
     * @return Template path.
     */
    String getTemplatePath();

    JpaRepository<T, Long> getJpaRepository();

    List<String> getNewFieldList();
    
    List<String> getEditFieldList();
    
    List<String> getListFieldList();
    
    int getListFieldLinkIndex();

    Map<String, Field> getNewFieldMap();

    Map<String, Field> getEditFieldMap();

    Map<String, Field> getListFieldMap();

    void init() throws CrudSupportException;

    /**
     * This method is designed to be called on every request to facilitate changing fields during debugging sessions.
     *
     * <p></p>
     * Initializes {@code getNewFieldMap()}, {@code getEditFieldMap()}, and {@code getListFieldMap} from values in
     * {@code getNewFieldList()}, {@code getEditFieldList()} and {@code getListFieldList()}. This method will reset
     * the maps before initialization.
     */
    void initFieldMaps() throws CrudSupportException;

    void initBinder(WebDataBinder binder);

    String formatListHeader(String fieldName);

    String formatListValue(String fieldName, Object value, T t);

    /**
     * Called if validation passed but before saving the entity. This method should be used to process file uploads.
     *
     * @param t entity entity object being saved
     * @param request multipart request object
     * @return true to proceed with entity save operation, false otherwise.
     */
    boolean onEditSave(T t, BindingResult result, MultipartRequest request) throws CrudSupportException;

    boolean onNewSave(T t, BindingResult result, MultipartRequest request) throws CrudSupportException;

    /**
     * // TODO: javadoc why can return null
     *
     * @param field
     * @param crudEntity
     * @return
     */
    Object loadNewItems(Field field, T crudEntity, Model model);

    /**
     * TODO: javadoc why can return null
     *
     * @param field
     * @param crudEntity
     * @return
     */
    Object loadEditItems(Field field, T crudEntity, Model model);
}
