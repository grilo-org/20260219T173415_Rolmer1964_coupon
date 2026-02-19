package com.tenda.coupon.exceptions.custom;


import com.tenda.coupon.exceptions.dto.ErrorForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BadRequestException extends RuntimeException {
    private String internalErrorMessage;
    private List<ErrorForm> errorFormList;
    private String externalErrorMessage;

}
