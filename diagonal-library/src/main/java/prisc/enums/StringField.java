package prisc.enums;

public enum StringField {

    TITLE("Title"), AUTHOR("Author");
    private final String fieldDescription;

    public String getFieldDescription() {
        return fieldDescription;
    }

    StringField(final String description) {
        this.fieldDescription = description;
    }
}
