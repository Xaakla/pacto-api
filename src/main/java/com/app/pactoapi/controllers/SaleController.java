package com.app.pactoapi.controllers;

import com.app.pactoapi.commons.ResponseResult;
import com.app.pactoapi.commons.ResultPageDto;
import com.app.pactoapi.dtos.payment.PaymentResponseDto;
import com.app.pactoapi.dtos.sale.NewEditSaleDto;
import com.app.pactoapi.dtos.sale.SaleListDto;
import com.app.pactoapi.dtos.sale.SaleDetailsDto;
import com.app.pactoapi.Routes;
import com.app.pactoapi.services.ModelMapperService;
import com.app.pactoapi.services.PaymentService;
import com.app.pactoapi.services.SaleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SaleController {
    private final ModelMapperService modelMapperService;
    private final SaleService saleService;
    private final PaymentService paymentService;

    public SaleController(
            ModelMapperService modelMapperService,
            SaleService saleService,
            PaymentService paymentService) {
        this.modelMapperService = modelMapperService;
        this.saleService = saleService;
        this.paymentService = paymentService;
    }

    @PostMapping(Routes.Admin.Sales.path)
    public ResponseResult<SaleListDto> save(@RequestBody @Valid NewEditSaleDto dto) {
        return ResponseResult.success(new SaleListDto(saleService.save(dto)));
    }

    @DeleteMapping(Routes.Admin.Sales.path)
    public ResponseResult<Object> delete(@RequestParam List<Long> ids) {
        saleService.deleteAll(ids);
        return ResponseResult.success(HttpStatus.OK);
    }

    @GetMapping(Routes.Admin.Sales.path)
    public ResponseResult<ResultPageDto<SaleListDto>> findAll(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "") String transactionId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int itemsPerPage,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection
    ) {
        return ResponseResult.success(
                modelMapperService.toPage(SaleListDto.class,
                        saleService.findAll(q, transactionId, page, itemsPerPage, sortDirection)
                )
        );
    }

    @GetMapping(Routes.Admin.Sales.ById.path)
    public ResponseResult<SaleListDto> findById(@PathVariable Long id) {
        return ResponseResult.success(new SaleListDto(saleService.findById(id)));
    }

    @GetMapping(Routes.Admin.Sales.ById.Full.path)
    public ResponseResult<SaleDetailsDto> findFullById(@PathVariable Long id) {
        var saleDetails = new SaleDetailsDto(saleService.findById(id));

        saleDetails.setPayments(paymentService.findBySaleId(id).stream().map(PaymentResponseDto::new).collect(Collectors.toList()));

        return ResponseResult.success(saleDetails);
    }
}
