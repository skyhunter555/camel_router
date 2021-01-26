package ru.syntez.camel.router.test.utils;

import com.inspiresoftware.lib.dto.geda.adapter.BeanFactory;
import com.inspiresoftware.lib.dto.geda.adapter.ValueConverter;
import ru.syntez.camel.router.test.entities.AdditionalData;
import java.util.ArrayList;
import java.util.List;

public class RateValueConverter implements ValueConverter {

    /** {@inheritDoc} */
    public Object convertToDto(final Object object, final BeanFactory beanFactory) {
        final Integer value = (Integer) object;
        List<AdditionalData> additionalDataList = new ArrayList<>();
        if (value != null) {
            AdditionalData additionalData = new AdditionalData();
            additionalData.setValue("500");
            additionalDataList.add(additionalData);
        }
        return additionalDataList;
    }

    @Override
    public Object convertToEntity(Object o, Object o1, BeanFactory beanFactory) {
        return null;
    }
}
