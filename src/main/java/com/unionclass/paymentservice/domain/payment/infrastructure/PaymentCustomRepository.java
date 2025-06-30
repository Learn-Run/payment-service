package com.unionclass.paymentservice.domain.payment.infrastructure;

import com.unionclass.paymentservice.common.response.CursorPage;
import com.unionclass.paymentservice.domain.payment.dto.in.CursorPageParamReqDto;
import com.unionclass.paymentservice.domain.payment.dto.out.GetPaymentUuidResDto;

public interface PaymentCustomRepository {

    CursorPage<GetPaymentUuidResDto> findPaymentUuidsByCursor(CursorPageParamReqDto dto);
}
