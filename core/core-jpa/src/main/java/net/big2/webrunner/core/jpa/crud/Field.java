package net.big2.webrunner.core.jpa.crud;

import org.apache.commons.lang.StringUtils;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class Field {
    /**
     * Helper method to convert a string to slug.
     *
     * @param s
     * @return
     */
    public static String toSlug(String s) {
        Pattern NONLATIN = Pattern.compile("[^\\w-]");
        Pattern WHITESPACE = Pattern.compile("[\\s]");

        String nowhitespace = WHITESPACE.matcher(s).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");

        // replace capital letters with -small
        slug = slug.replaceAll("([A-Z])", "-$1");
        if (slug.startsWith("-")) {
            slug = slug.subSequence(1, slug.length()).toString();
        }

        // default values
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static String toLabel(String s) {
        s = StringUtils.uncapitalize(s);

        Pattern prependSpaceToUpper = Pattern.compile("([A-Z])");
        String label = prependSpaceToUpper.matcher(s).replaceAll(" $1");

        // default values
        return StringUtils.capitalize(label);
    }

    public static String extractFieldName(String fieldName) {
        String extractedFieldName = fieldName;
        if (extractedFieldName.contains(":")) {
            // strip prefix i.e. "file:"
            extractedFieldName = extractedFieldName.substring(extractedFieldName.indexOf(":") + 1);

            if (extractedFieldName.contains(":")) {
                // strip field postfix "thumbnail:image/jpg" to "thumbnail"
                extractedFieldName = extractedFieldName.substring(0, extractedFieldName.indexOf(":"));
            }
        }
        return extractedFieldName;
    }

    public enum Type {
        TEXT, TEXTAREA,
        CHECKBOX, CHECKBOXES,
        RADIO, RADIOS,
        PASSWORD,
        SELECT,
        HIDDEN,
        FILE,
        DATE,
        DATETIME,
        JSP
    }

    private Type type;
    private boolean viewOnly;

    private String id;
    private String label;
    private int size;
    private int rows;
    private int cols;
    private boolean multiple;
    private boolean optional;
    @Deprecated
    private String filename;
    private String entityProperty;

    private String uploadedProperty;
    private String[] validContentTypes;

    public String getUploadedProperty() {
        return uploadedProperty;
    }

    public void setUploadedProperty(String uploadedProperty) {
        this.uploadedProperty = uploadedProperty;
    }

    public static Field makeField(String fieldName) {

        Field field = new Field();
        // TODO: refactor, supersede uploadedProperty
        field.setEntityProperty(fieldName);
        // TODO: eval id usage
        field.setId(fieldName);
        field.setLabel(toLabel(fieldName));
        field.setType(Type.TEXT);

        return field;
    }

    public String getEntityProperty() {
        return entityProperty;
    }

    public void setEntityProperty(String entityProperty) {
        this.entityProperty = entityProperty;
    }

    public String[] getValidContentTypes() {
        return validContentTypes;
    }

    public void setValidContentTypes(String[] validContentTypes) {
        this.validContentTypes = validContentTypes;
    }

    @Deprecated
    public String getFilename() {
        return filename;
    }

    @Deprecated
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isViewOnly() {
        return viewOnly;
    }

    public void setViewOnly(boolean viewOnly) {
        this.viewOnly = viewOnly;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    /**
     * Optional value (used by file upload).
     *
     * @return
     */
    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }
}
