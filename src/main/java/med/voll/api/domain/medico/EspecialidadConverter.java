package med.voll.api.domain.medico;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EspecialidadConverter implements AttributeConverter<Especialidad, String> {

    @Override
    public String convertToDatabaseColumn(Especialidad attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toValue(); // Guarda el valor en minúscula en la base de datos
    }

    @Override
    public Especialidad convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            throw new IllegalArgumentException("La especialidad almacenada en la base de datos no puede ser nula o estar vacía.");
        }
        return Especialidad.fromValue(dbData); // Llama al método del enum para validar y convertir
    }
}