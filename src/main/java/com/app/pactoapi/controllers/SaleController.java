package com.app.pactoapi.controllers;

import com.app.pactoapi.commons.ResponseResult;
import com.app.pactoapi.commons.ResultPageDto;
import com.app.pactoapi.database.entities.Sale;
import com.app.pactoapi.dtos.payment.PaymentResponseDto;
import com.app.pactoapi.dtos.sale.SaleDetailsDto;
import com.app.pactoapi.dtos.sale.NewEditSaleDto;
import com.app.pactoapi.dtos.sale.SaleDetailsResponseDto;
import com.app.pactoapi.routes.Routes;
import com.app.pactoapi.services.PaymentService;
import com.app.pactoapi.services.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SaleController {
    private final SaleService saleService;
    private final PaymentService paymentService;

    public SaleController(SaleService saleService, PaymentService paymentService) {
        this.saleService = saleService;
        this.paymentService = paymentService;
    }

    @PostMapping(Routes.Admin.Sales.path)
    public ResponseResult<SaleDetailsDto> save(@RequestBody NewEditSaleDto dto) {
        return ResponseResult.success(new SaleDetailsDto(saleService.save(dto)));
    }

    @DeleteMapping(Routes.Admin.Sales.path)
    public ResponseResult<Object> delete(@RequestParam List<Long> ids) {
        saleService.deleteAll(ids);
        return ResponseResult.success(HttpStatus.OK);
    }

    @GetMapping(Routes.Admin.Sales.path)
    public ResponseResult<ResultPageDto<Sale, SaleDetailsDto>> findAll(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int itemsPerPage,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(required = false) String transactionId
    ) {
        Page<Sale> salePage = saleService.findAll(q, transactionId, page, itemsPerPage, sortDirection);

        List<SaleDetailsDto> saleDetailsDtos = salePage.getContent().stream()
                .map(SaleDetailsDto::new)
                .collect(Collectors.toList());

        ResultPageDto<Sale, SaleDetailsDto> resultPageDto = ResultPageDto.of(
                salePage.getTotalElements(),
                salePage.getTotalPages(),
                salePage.getNumber(),
                saleDetailsDtos
        );

        return ResponseResult.success(resultPageDto);
    }

    @GetMapping(Routes.Admin.Sales.ById.path)
    public ResponseResult<SaleDetailsDto> findById(@PathVariable Long id) {
        return ResponseResult.success(new SaleDetailsDto(saleService.findById(id)));
    }

    @GetMapping(Routes.Admin.Sales.ById.Full.path)
    public ResponseResult<SaleDetailsResponseDto> findFullById(@PathVariable Long id) {
        var saleDetails = new SaleDetailsResponseDto(saleService.findById(id));

        saleDetails.setPayments(paymentService.findBySaleId(id).stream().map(PaymentResponseDto::new).collect(Collectors.toList()));

        return ResponseResult.success(saleDetails);
    }
}
