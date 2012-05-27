package net.big2.webrunner.core.web;

import net.big2.webrunner.core.common.storage.StorageService;
import net.big2.webrunner.core.common.storage.StorageServiceException;
import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;
import net.big2.webrunner.core.jpa.crud.WebRunnerRepository;
import net.big2.webrunner.core.jpa.crud.support.CrudSupport;
import net.big2.webrunner.core.jpa.crud.support.CrudSupportException;
import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.format;

@RequestMapping("obj")
@Controller
public class CrudSupportController extends BaseController {
    public static final String BASE_REQUEST_MAPPING = "obj";

    public static final String ATTRIBUTE_PAGE = "page";
    public static final String ATTRIBUTE_ENTITY = "entity";

    public static final String ATTRIBUTE_FLASH_MESSAGE = "flashMessage";

    public static final String TEMPLATE_LIST = "list";
    public static final String TEMPLATE_NEW_FORM = "newForm";
    public static final String TEMPLATE_EDIT_FORM = "editForm";
    public static final String ATTRIBUTE_ITEM_MAP = "itemMap";
    public static final String ATTRIBUTE_STORAGE_SERVICE_URL = "storageBaseUrl";

    protected Logger log = LoggerFactory.getLogger(CrudSupportController.class);
    protected Map<String, CrudSupport> crudSupportMap;

    protected StorageService storageService;
    protected ApplicationContext applicationContext;

    @Autowired
    public CrudSupportController(ApplicationContext applicationContext, StorageService storageService) {
        this.applicationContext = applicationContext;
        this.storageService = storageService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String listCrudSupport() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SerializationConfig serializationConfig = mapper.getSerializationConfig();
        serializationConfig.addMixInAnnotations(CrudSupport.class,
                CrudSupportMixin.class);
        return mapper.writeValueAsString(new AjaxResponse(crudSupportMap));
    }

    @SuppressWarnings("UnusedDeclaration")
    @JsonAutoDetect(value = JsonMethod.NONE)
    abstract class CrudSupportMixin {
        @JsonProperty
        abstract Class getCrudEntityClass();

        @JsonProperty
        abstract String getSlug();

        @JsonProperty
        abstract String getName();

        @JsonProperty
        abstract List<String> getNewFieldList();

        @JsonProperty
        abstract List<String> getEditFieldList();

        @JsonProperty
        abstract List<String> getListFieldList();

        @JsonProperty
        abstract int getListFieldLinkIndex();
    }

    @RequestMapping(value = "{type:[a-z-]+}", method = RequestMethod.GET)
    public String list(@PathVariable String type, @RequestParam(defaultValue = "") String q, Pageable
            pageable, Model model) throws CrudSupportControllerException {
        CrudSupport crudSupport = findCrudSupport(type, model);

        // validate pagable sorting
        if (pageable.getSort() != null) {
            Iterator<Sort.Order> orders = pageable.getSort().iterator();
            while (orders.hasNext()) {
                String property = orders.next().getProperty();
                if (!crudSupport.getListFieldList().contains(property)) {
                    throw new CrudSupportControllerException(format("Invalid property to sort by [%s]", property));
                }
            }
        }

        Page page;
        JpaRepository jpaRepository = crudSupport.getJpaRepository();
        if (jpaRepository instanceof WebRunnerRepository) {
            WebRunnerRepository webRunnerRepository = (WebRunnerRepository) jpaRepository;
            page = webRunnerRepository.list(q, pageable);
        } else {
            log.warn(format("%s is not a WebRunnerRepositoy", jpaRepository.getClass().getName()));
            page = jpaRepository.findAll(pageable);
        }
        model.addAttribute(ATTRIBUTE_PAGE, page);

        return crudSupport.getTemplatePath() + TEMPLATE_LIST;
    }

    @RequestMapping(value = "{type}/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable String type, @PathVariable Long id, Model model) throws
            CrudSupportControllerException {
        CrudSupport<? extends CrudEntity> crudSupport = findCrudSupport(type, model);

        CrudEntity crudEntity = crudSupport.getJpaRepository().findOne(id);
        if (crudEntity == null) {
            throw new CrudSupportControllerException(
                    format("Error loading entity [%s] by ID [%s]", type, id));
        } else {
            model.addAttribute(ATTRIBUTE_ENTITY, crudEntity);
            loadEditItems(model, crudSupport, crudSupport.getEditFieldMap(), crudEntity);
            return crudSupport.getTemplatePath() + TEMPLATE_EDIT_FORM;

        }
    }

    private void loadEditItems(Model model, CrudSupport crudSupport, Map<String, Field> fieldMap, CrudEntity crudEntity) {
        Map itemMap = new HashMap();

        for (Field field : fieldMap.values()) {
            Object obj = crudSupport.loadEditItems(field, crudEntity, model);
            if (obj != null) {
                itemMap.put(field.getId(), obj);
            }
        }

        model.addAttribute(ATTRIBUTE_ITEM_MAP, itemMap);
    }

    private void loadNewItems(Model model, CrudSupport crudSupport, Map<String, Field> fieldMap, CrudEntity crudEntity) {
        Map itemMap = new HashMap();

        for (Field field : fieldMap.values()) {
            Object obj = crudSupport.loadNewItems(field, crudEntity, model);
            if (obj != null) {
                itemMap.put(field.getId(), obj);
            }
        }

        model.addAttribute(ATTRIBUTE_ITEM_MAP, itemMap);
    }

    @RequestMapping(value = "{type}/{id}", method = RequestMethod.POST)
    public String editSave(
            @PathVariable String type, @PathVariable Long id,
            @Valid @ModelAttribute(ATTRIBUTE_ENTITY) CrudEntity crudEntity, BindingResult result, Model model,
            RedirectAttributes redirectAttributes, MultipartRequest request) throws CrudSupportControllerException, CrudSupportException {
        CrudSupport crudSupport = findCrudSupport(type, model);

        if (!result.hasErrors() && crudSupport.onEditSave(crudEntity, result, request)) {
            crudEntity = (CrudEntity) crudSupport.getJpaRepository().save(crudEntity);
            // set entity again, might be different object after save (just in case)
            model.addAttribute(ATTRIBUTE_ENTITY, crudEntity);

            redirectAttributes.addFlashAttribute(ATTRIBUTE_FLASH_MESSAGE, "Saved.");
            return "redirect:../{type}";

        } else {

            loadEditItems(model, crudSupport, crudSupport.getEditFieldMap(), crudEntity);
            return crudSupport.getTemplatePath() + TEMPLATE_EDIT_FORM;
        }


    }

    @RequestMapping(value = "{type}/new", method = RequestMethod.GET)
    public String newForm(
            @PathVariable String type,
            @ModelAttribute(ATTRIBUTE_ENTITY) CrudEntity crudEntity,
            Model model) throws CrudSupportControllerException {
        CrudSupport crudSupport = findCrudSupport(type, model);

        model.addAttribute(ATTRIBUTE_ENTITY, crudEntity);
        loadNewItems(model, crudSupport, crudSupport.getNewFieldMap(), crudEntity);
        return crudSupport.getTemplatePath() + TEMPLATE_NEW_FORM;
    }

    @RequestMapping(value = "{type}/new", method = RequestMethod.POST)
    public String newSave(
            @PathVariable String type, @Valid @ModelAttribute(ATTRIBUTE_ENTITY) CrudEntity crudEntity,
            BindingResult result, Model model,
            RedirectAttributes redirectAttributes, MultipartRequest request)
            throws CrudSupportControllerException, CrudSupportException {
        CrudSupport crudSupport = findCrudSupport(type, model);

        if (!result.hasErrors() && crudSupport.onNewSave(crudEntity, result, request)) {
            crudEntity = (CrudEntity) crudSupport.getJpaRepository().save(crudEntity);
            // set entity again, might be different object after save (just in case)
            model.addAttribute(ATTRIBUTE_ENTITY, crudEntity);

            redirectAttributes.addFlashAttribute(ATTRIBUTE_FLASH_MESSAGE, "Saved.");
            return "redirect:../{type}";

        } else {
            loadNewItems(model, crudSupport, crudSupport.getNewFieldMap(), crudEntity);
            return crudSupport.getTemplatePath() + TEMPLATE_NEW_FORM;

        }
    }

    @RequestMapping(value = "{type}/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String type, @PathVariable Long id, Model model,
                         RedirectAttributes redirectAttributes) throws CrudSupportControllerException {
        CrudSupport crudSupport = findCrudSupport(type, model);

        if (crudSupport.getJpaRepository().exists(id)) {
            try {
                crudSupport.getJpaRepository().delete(id);
            } catch(DataIntegrityViolationException e) {
                throw new CrudSupportControllerException(
                        format("Cannot delete %s [%d] referenced by other objects", crudSupport.getName(), id), e);
            }

            redirectAttributes.addFlashAttribute(ATTRIBUTE_FLASH_MESSAGE, "Deleted.");
            return "redirect:.";

        } else {
            throw new CrudSupportControllerException(
                    format("Entity [%s] ID [%s] not found", type, id));
        }
    }

    @RequestMapping(value = "{type}/{id}/delete/{field}")
    public String deleteField(
            @ModelAttribute(ATTRIBUTE_ENTITY) CrudEntity crudEntity,
            @PathVariable String type, @PathVariable Long id, @PathVariable String field,
            Model model) throws CrudSupportControllerException {
        CrudSupport crudSupport = findCrudSupport(type, model);

        // TODO: why use editFieldMap instead of newFieldMap?
        Map<String, Field> fieldMap = crudSupport.getEditFieldMap();
        Field f = fieldMap.get(field);

        try {
            if (Field.Type.FILE == f.getType()) {
                // delete from storage
                storageService.delete(f.getId(), crudEntity.getId().toString());
                // indicate as deleted in entity
                BeanUtils.setProperty(crudEntity, f.getUploadedProperty(), Boolean.FALSE);
                // save entity
                crudSupport.getJpaRepository().save(crudEntity);
                log.debug(format("Deleted file [%s] from entity", field));
            }

            model.asMap().clear();
            return "redirect:../../{id}";
        } catch (InvocationTargetException e) {
            throw new CrudSupportControllerException("Error deleting field", e);
        } catch (StorageServiceException e) {
            throw new CrudSupportControllerException("Error deleting field", e);
        } catch (IllegalAccessException e) {
            throw new CrudSupportControllerException("Error deleting field", e);
        }
    }

    @PostConstruct
    public void init() throws CrudSupportControllerException {
        crudSupportMap = new TreeMap<String, CrudSupport>();

        // TODO: remove need to create *CrudSupport with @CrudSupport(listFields="", newFields="", editFields="")
/*
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.TYPE)
        public @interface CrudSupport {
            String[] listFields();
            String[] newFields() default {};
            String[] editFields() default {};
        }
*/

        // register all CrudSupport beans to crudSupportMap
        if (applicationContext != null) {
            Map<String, CrudSupport> crudSupportBeanMap = applicationContext.getBeansOfType(CrudSupport.class);

            for (String key : crudSupportBeanMap.keySet()) {
                CrudSupport crudSupport = crudSupportBeanMap.get(key);
                crudSupportMap.put(crudSupport.getSlug(), crudSupport);
                log.debug(format("Registering CrudSupport [%s]", crudSupport.getSlug()));
            }
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

        for (CrudSupport crudSupport : crudSupportMap.values()) {
            crudSupport.initBinder(binder);
        }
    }


    /**
     * Overrides Spring's command object creation to ensure CrudSupport's object type is created.
     *
     * @param request request parameter.
     * @return CrudSupport type specific object if URL matches, otherwise returns new Object().
     * @throws CrudSupportControllerException on error.
     */
    @ModelAttribute(ATTRIBUTE_ENTITY)
    public Object createCommandObject(HttpServletRequest request) throws CrudSupportControllerException {
        String type = getTypeForEdit(request.getRequestURI(), request.getContextPath());

        // matched edit URL
        if (type != null) {
            // get ID and load from repository
            Long id = getIdForEdit(request.getRequestURI(), request.getContextPath());
            if (id != null) {
                CrudSupport crudSupport = crudSupportMap.get(type);
                return crudSupport.getJpaRepository().findOne(id);
            } else {
                throw new CrudSupportControllerException(
                        format("Unable to extract ID to edit [%s]", request.getRequestURI()));
            }

        } else {
            type = getTypeForNew(request.getRequestURI(), request.getContextPath());

            if (type != null) {
                CrudSupport crudSupport = crudSupportMap.get(type);
                if (crudSupport != null) {
                    try {
                        return crudSupport.getCrudEntityClass().newInstance();
                    } catch (InstantiationException e) {
                        throw new CrudSupportControllerException(
                                format("Error creating command object [%s] for type [%s]",
                                        crudSupport.getCrudEntityClass().getName(), crudSupport.getSlug()), e);
                    } catch (IllegalAccessException e) {
                        throw new CrudSupportControllerException(
                                format("Error creating command object [%s] for type [%s]",
                                        crudSupport.getCrudEntityClass().getName(), crudSupport.getSlug()), e);
                    }
                }
            }
        }

        // for unsupported types, return new Object()
        return new Object();
    }

    @ModelAttribute(ATTRIBUTE_STORAGE_SERVICE_URL)
    public String getStorageServiceBaseUrl() {
        return storageService.getBaseUrl();
    }

    protected CrudSupport<? extends CrudEntity> findCrudSupport(String type, Model model) throws CrudSupportControllerException {
        CrudSupport<? extends CrudEntity> crudSupport = crudSupportMap.get(type);

        if (crudSupport == null) {
            throw new CrudSupportControllerException(format("Invalid CrudSupport type [%s]", type));
        } else {
            try {
                // TODO: remove from production (or make it optional)
                crudSupport.initFieldMaps();
            } catch (CrudSupportException e) {
                throw new CrudSupportControllerException("Error initializing field maps", e);
            }

            model.addAttribute("cs", crudSupport);

            return crudSupport;
        }
    }

    protected String getTypeForEdit(String uri, String contextPath) {
        String retVal = uri.replaceAll(contextPath + "/" + BASE_REQUEST_MAPPING + "/([\\w\\-]+)/[\\d]+", "$1");

        if (uri.equals(retVal)) {
            retVal = null;
        }

        // TODO: optimize
        // convert "product/delete/thumbnail" to "product"
        if (retVal!=null && retVal.contains("/")) {
            retVal = retVal.substring(0, retVal.indexOf('/'));
        }

        return retVal;
    }

    protected Long getIdForEdit(String uri, String contextPath) {
        String id = uri.replaceAll(contextPath + "/" + BASE_REQUEST_MAPPING + "/[\\w\\-]+/([\\d]+)", "$1");

        if (uri.equals(id)) {
            return null;
        }

        // TODO: optimize
        // convert "2/delete/thumbnail" to "2"
        if (id.contains("/")) {
            id = id.substring(0, id.indexOf('/'));
        }

        return Long.parseLong(id);
    }

    protected String getTypeForNew(String uri, String contextPath) {
        String retVal = uri.replaceAll(contextPath + "/" + BASE_REQUEST_MAPPING + "/([\\w\\-]+)/new", "$1");

        if (uri.equals(retVal)) {
            retVal = null;
        }

        return retVal;
    }

}
