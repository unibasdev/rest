package it.unibas.mediapesata.filtri;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class LocalDateTimeParamCoverter implements ParamConverterProvider {

    private final static Logger logger = LoggerFactory.getLogger(LocalDateTimeParamCoverter.class);
    private static final String PATTERN = "yyyy-MM-dd HH:mm";

    @Override
    @SuppressWarnings("unchecked")
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.equals(LocalDateTime.class)) {
            return (ParamConverter<T>) new LocalDateConverter();
        }
        return null;
    }

    class LocalDateConverter implements ParamConverter<LocalDateTime> {

        @Override
        public LocalDateTime fromString(String value) {
            logger.trace("Richiesta la conversione in LocalDate del valore String {}", value);
            if (value == null) {
                return null;
            }
            LocalDateTime localDate = LocalDateTime.parse(value, DateTimeFormatter.ofPattern(PATTERN));
            return localDate;
        }

        @Override
        public String toString(LocalDateTime value) {
            logger.trace("Richiesta la conversione in String del Calendar  {}", value);
            if (value == null) {
                return null;
            }
            return value.format(DateTimeFormatter.ofPattern(PATTERN));
        }

    }
}
