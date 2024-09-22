package com.app.pactoapi.controllers;

import com.app.pactoapi.commons.ResponseResult;
import com.app.pactoapi.dtos.payment.NewPaymentDto;
import com.app.pactoapi.dtos.payment.PaymentResponseDto;
import com.app.pactoapi.Routes;
import com.app.pactoapi.services.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(Routes.Admin.Payment.path)
    public ResponseResult<PaymentResponseDto> createPayment(@RequestBody NewPaymentDto paymentDto) {
        var response = new PaymentResponseDto(paymentService.payWithCreditCard(paymentDto.getSaleId(), paymentDto.getCreditCard(), paymentDto.getAmount()));
        return ResponseResult.success(response);
    }

    @PutMapping(Routes.Admin.Payment.ById.path)
    public ResponseResult<PaymentResponseDto> cancelPayment(@PathVariable Long id) {
        var response = new PaymentResponseDto(paymentService.cancelPayment(id));
        return ResponseResult.success(response);
    }
}
