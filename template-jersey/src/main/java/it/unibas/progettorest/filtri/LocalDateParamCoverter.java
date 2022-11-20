package it.unibas.progettorest.filtri;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Provider
@Slf4j
public class LocalDateParamCoverter implements ParamConverterProvider {

    private static final String PATTERN = "yyyy-MM-dd";

    @Override
    @SuppressWarnings("unchecked")
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.equals(LocalDate.class)) {
            return (ParamConverter<T>) new LocalDateConverter();
        }
        return null;
    }

    class LocalDateConverter implements ParamConverter<LocalDate> {

        @Override
        public LocalDate fromString(String value) {
            log.trace("Richiesta la conversione in LocalDate del valore String {}", value);
            if (value == null) {
                return null;
            }
            LocalDate localDate = LocalDate.parse(value, DateTimeFormatter.ofPattern(PATTERN));
            return localDate;
        }

        @Override
        public String toString(LocalDate value) {
            log.trace("Richiesta la conversione in String del Calendar  {}", value);
            if (value == null) {
                return null;
            }
            return value.format(DateTimeFormatter.ofPattern(PATTERN));
        }

    }
}
