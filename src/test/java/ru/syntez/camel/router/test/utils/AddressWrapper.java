package ru.syntez.camel.router.test.utils;

import com.inspiresoftware.lib.dto.geda.annotations.Dto;
import lombok.Data;
import org.junit.Ignore;
import ru.syntez.camel.router.test.entities.AddressModel;
import ru.syntez.camel.router.test.entities.AddressModelExt;

@Data
@Dto
@Ignore
public class AddressWrapper {
    private Integer addressId;
    private String addressCity;
    private String addressStreet;
    private Integer rateValue1;
    private String addressCountry;
    private String addressState;
    private String addressDistrict;
    private String houseNumber;
    public AddressWrapper(AddressModel addressModel, AddressModelExt addressModelExt) {
        this.addressId = addressModel.getAddressId();
        this.addressCity = addressModel.getAddressCity();
        this.addressStreet = addressModel.getAddressStreet();
        this.rateValue1 = addressModel.getRateValue1();
        this.addressCountry = addressModelExt.getAddressCountry();
        this.addressState = addressModelExt.getAddressState();
        this.addressDistrict = addressModelExt.getAddressDistrict();
        this.houseNumber = addressModelExt.getHouseNumber();
    }
}
