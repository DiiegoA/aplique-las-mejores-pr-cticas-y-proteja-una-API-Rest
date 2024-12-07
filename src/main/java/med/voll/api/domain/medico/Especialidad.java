package med.voll.api.domain.medico;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Especialidad {

    ORTOPEDIA("ortopedia"),
    CARDIOLOGIA("cardiologia"),
    GINECOLOGIA("ginecologia"),
    PEDIATRIA("pediatria");

    private final String value;

    Especialidad(String value) {
        this.value = value;
    }

    public static Especialidad fromValue(String value) {
        for (Especialidad especialidad : Especialidad.values()) {
            if (especialidad.value.equalsIgnoreCase(value)) {
                return especialidad;
            }
        }
        throw new IllegalArgumentException("Especialidad no válida: " + value);
    }

    @JsonValue
    public String toValue() {
        return value;
    }
}