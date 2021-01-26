package ru.syntez.camel.router.test.utils;

import com.inspiresoftware.lib.dto.geda.adapter.BeanFactory;
import com.inspiresoftware.lib.dto.geda.adapter.ValueConverter;
import ru.syntez.camel.router.test.entities.AdditionalData;

import java.util.List;

public class AdditionalDataConverter implements ValueConverter {

    /** {@inheritDoc} */
    public Object convertToDto(final Object object, final BeanFactory beanFactory) {
        final List<AdditionalData> valueList = (List<AdditionalData>) object;
        if (valueList != null && !valueList.isEmpty()) {
            return Integer.valueOf(valueList.get(0).getValue());
        }
        return null;
    }

    @Override
    public Object convertToEntity(Object o, Object o1, BeanFactory beanFactory) {
        return null;
    }
}
