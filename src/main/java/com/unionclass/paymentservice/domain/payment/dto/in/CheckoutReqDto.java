package com.unionclass.paymentservice.domain.payment.dto.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckoutReqDto {

    private String mId;
    private String url;
    private String sessionCreationApiVersion;
    private String gtid;
    private String flowMode;

    @Builder
    public CheckoutReqDto(String mId, String url, String sessionCreationApiVersion, String gtid, String flowMode) {
        this.mId = mId;
        this.url = url;
        this.sessionCreationApiVersion = sessionCreationApiVersion;
        this.gtid = gtid;
        this.flowMode = flowMode;
    }
}
