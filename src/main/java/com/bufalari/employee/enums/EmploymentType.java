package com.bufalari.employee.enums;

/**
 * Enum representing different types of employment contracts.
 * Enum representando diferentes tipos de contratos de trabalho.
 */
public enum EmploymentType {
    FULL_TIME("Full-Time", "Tempo Integral"),         // Funcionário em tempo integral
    PART_TIME("Part-Time", "Meio Período"),         // Funcionário em meio período
    CONTRACTOR("Contractor", "Contratado/Terceirizado"), // Prestador de serviço (PJ ou autônomo)
    INTERN("Intern", "Estagiário"),                 // Estagiário
    TEMPORARY("Temporary", "Temporário");             // Funcionário temporário (CLT ou contrato por prazo)

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

    // Optional: Método para obter descrição baseado no Locale
    // Opcional: Método para obter descrição baseado no Locale
    // public String getDescription(java.util.Locale locale) {
    //     // Exemplo simples, pode ser mais robusto
    //     if (java.util.Locale.CANADA.equals(locale) || java.util.Locale.US.equals(locale) || java.util.Locale.UK.equals(locale)) {
    //         return descriptionEn;
    //     } else { // Default para pt-BR ou outros
    //         return descriptionPt;
    //     }
    // }
}