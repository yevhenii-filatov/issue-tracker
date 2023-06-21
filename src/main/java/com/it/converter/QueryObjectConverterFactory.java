package com.it.converter;

import com.it.model.rest.query.Query;
import com.it.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;

/**
 * @author Yevhenii Filatov
 * @since 3/28/23
 */

@Slf4j
public class QueryObjectConverterFactory<T extends Query> implements ConverterFactory<String, T> {
    public <D extends T> @NonNull Converter<String, D> getConverter(@NonNull Class<D> targetType) {
        return new StringToQueryObjectEntityConverter<>(targetType);
    }

    private record StringToQueryObjectEntityConverter<T extends Query>(Class<T> clazz) implements Converter<String, T> {
        public T convert(@NonNull String source) {
            return JsonUtils.fromString(source, this.clazz);
        }
    }
}
