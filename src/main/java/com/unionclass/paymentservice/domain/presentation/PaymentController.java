package com.unionclass.paymentservice.domain.presentation;

import com.unionclass.paymentservice.domain.application.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@Tag(name = "payment")
public class PaymentController {

    private final PaymentService paymentService;
}
