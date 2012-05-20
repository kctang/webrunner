package net.big2.webrunner.core.jpa.crud.support;

import net.big2.webrunner.core.common.storage.StorageService;
import net.big2.webrunner.core.common.storage.StorageServiceException;
import net.big2.webrunner.core.jpa.crud.CrudEntity;
import net.big2.webrunner.core.jpa.crud.Field;
import net.big2.webrunner.core.jpa.crud.fg.FieldGenerator;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class FileUploadCrudSupport<T extends CrudEntity> extends DefaultCrudSupport<T> {
    protected StorageService storageService;

    public FileUploadCrudSupport(
            Class<T> crudEntityClass, JpaRepository<T, Long> jpaRepository,
            FieldGenerator fieldGenerator, StorageService storageService) {
        super(crudEntityClass, jpaRepository, fieldGenerator);
        this.storageService = storageService;
    }

    @Override
    public boolean onEditSave(T t, BindingResult result, MultipartRequest request) throws CrudSupportException {
        boolean valid = true;

        for (Field field : editFieldMap.values()) {
            boolean currentValid = true;

            // process file uploads
            if (field.getType() == Field.Type.FILE) {
                try {
                    Boolean previouslyUploaded = Boolean.valueOf(BeanUtils.getProperty(t, field.getUploadedProperty()));

                    MultipartFile file = request.getFile(field.getId());
                    Boolean uploaded = file != null && file.getSize() != 0;

                    // validate required file upload
                    if (!field.isOptional() && !(previouslyUploaded || uploaded)) {
                        result.addError(new FieldError("entity", field.getId(), "Required"));
                        valid = false;
                        currentValid = false;
                    }

                    // validate for supported file types
                    if (uploaded && field.getValidContentTypes().length > 0) {
                        if (!Arrays.asList(field.getValidContentTypes()).contains(file.getContentType())) {
                            result.addError(new FieldError("entity", field.getId(), String.format("Invalid file. Expecting %s", Arrays.toString(field.getValidContentTypes()))));
                            valid = false;
                            currentValid = false;
                        }
                    }

                    if (uploaded && currentValid) {
                        // process upload
                        BeanUtils.setProperty(t, field.getUploadedProperty(), true);
                        // TODO: questionable save to make multi file upload field work
                        t = getJpaRepository().save(t);
                        storageService.store(field.getId(), t.getId().toString(), file.getBytes());
                    }

                } catch (InvocationTargetException e) {
                    throw new CrudSupportException("Error processing file upload", e);
                } catch (NoSuchMethodException e) {
                    throw new CrudSupportException("Error processing file upload", e);
                } catch (IllegalAccessException e) {
                    throw new CrudSupportException("Error processing file upload", e);
                } catch (StorageServiceException e) {
                    throw new CrudSupportException("Error processing file upload", e);
                } catch (IOException e) {
                    throw new CrudSupportException("Error processing file upload", e);
                }
            }
        }

        return valid;
    }

    @Override
    public boolean onNewSave(T t, BindingResult result, MultipartRequest request) throws CrudSupportException {
        boolean valid = true;

        // new entity save with file upload require two pass

        // pass 1: validate
        for (Field field : editFieldMap.values()) {
            // process file uploads
            if (field.getType() == Field.Type.FILE) {
                MultipartFile file = request.getFile(field.getId());
                Boolean uploaded = file != null && file.getSize() != 0;

                // validate required file upload
                if (!field.isOptional() && !(uploaded)) {
                    result.addError(new FieldError("entity", field.getId(), "Required"));
                    valid = false;
                }

                // validate for supported file types
                if (uploaded && field.getValidContentTypes().length > 0) {
                    if (!Arrays.asList(field.getValidContentTypes()).contains(file.getContentType())) {
                        result.addError(new FieldError("entity", field.getId(), String.format("Invalid file. Expecting %s", Arrays.toString(field.getValidContentTypes()))));
                        valid = false;
                    }
                }
            }
        }

        // pass 2: if all validation pass, save entity & store
        if (valid) {
            // save to get new entity id
            t = getJpaRepository().save(t);

            for (Field field : editFieldMap.values()) {
                if (field.getType() == Field.Type.FILE) {
                    try {
                        MultipartFile file = request.getFile(field.getId());
                        Boolean uploaded = file != null && file.getSize() != 0;

                        if (uploaded) {
                            BeanUtils.setProperty(t, field.getUploadedProperty(), true);
                            storageService.store(field.getId(), t.getId().toString(), file.getBytes());
                        }

                    } catch (InvocationTargetException e) {
                        throw new CrudSupportException("Error processing file upload", e);
                    } catch (IllegalAccessException e) {
                        throw new CrudSupportException("Error processing file upload", e);
                    } catch (StorageServiceException e) {
                        throw new CrudSupportException("Error processing file upload", e);
                    } catch (IOException e) {
                        throw new CrudSupportException("Error processing file upload", e);
                    }
                }
            }
        }

        return valid;
    }

}
