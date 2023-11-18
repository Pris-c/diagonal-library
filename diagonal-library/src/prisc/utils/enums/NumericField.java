package prisc.utils.enums;

public enum NumericField {

        YEAR("Year"), ID("Id");

        private final String fieldDescription;

    public String getFieldDescription() {
        return fieldDescription;
    }

    NumericField(final String description) {
            this.fieldDescription = description;
        }

}
