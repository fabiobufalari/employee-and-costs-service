// Path: employee-and-costs-service/src/main/java/com/bufalari/employee/enums/EmploymentType.java
package com.bufalari.employee.enums;

/**
 * Enum representing different types of employment contracts.
 * Enum representando diferentes tipos de contratos de trabalho.
 */
public enum EmploymentType {
    FULL_TIME("Full-Time", "Tempo Integral"),
    PART_TIME("Part-Time", "Meio Período"),
    CONTRACTOR("Contractor", "Contratado/Terceirizado"),
    INTERN("Intern", "Estagiário"),
    TEMPORARY("Temporary", "Temporário");

    private final String descriptionEn;
    private final String descriptionPt;

    EmploymentType(String descriptionEn, String descriptionPt) {
        this.descriptionEn = descriptionEn;
        this.descriptionPt = descriptionPt;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public String getDescriptionPt() {
        return descriptionPt;
    }

    // Optional: Method to get description based on Locale
    // Opcional: Método para obter descrição baseado no Locale
    // public String getDescription(Locale locale) {
    //     return Locale.CANADA.equals(locale) || Locale.US.equals(locale) || Locale.UK.equals(locale) ? descriptionEn : descriptionPt;
    // }
}