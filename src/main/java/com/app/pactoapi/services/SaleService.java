package com.app.pactoapi.services;

import com.app.pactoapi.database.entities.Sale;
import com.app.pactoapi.database.repositories.SaleRepository;
import com.app.pactoapi.dtos.sale.NewEditSaleDto;
import com.app.pactoapi.enums.PaymentStatus;
import com.app.pactoapi.exceptions.errors.BadRequestException;
import com.app.pactoapi.exceptions.errors.NotFoundException;
import com.app.pactoapi.commons.Utils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Page<Sale> findAll(String q, String transactionId, int page, int itemsPerPage, Sort.Direction sortDirection) {
        return saleRepository.findAll(q, transactionId, Utils.loggedUser().getId(),
                PageRequest.of(page, itemsPerPage, Sort.by(sortDirection, "id"))
        );
    }

    public Sale findById(Long id) {
        return saleRepository.findById(id).orElseThrow(() -> new NotFoundException("Sales not found"));
    }

    public Sale save(NewEditSaleDto dto) {
        Sale saleToSave = Optional.ofNullable(dto.getId())
                .map(this::findById)
                .orElseGet(Sale::new);

        saleToSave.setDescription(dto.getDescription());
        saleToSave.setAmount(dto.getAmount());
        saleToSave.setCurrency(dto.getCurrency());
        saleToSave.setUser(Utils.loggedUser());

        return saleRepository.save(saleToSave);
    }

    @Transactional
    public void delete(Long id) {
        final var sale = findById(id);

        if (hasSuccessfulPayment(sale))
            throw new BadRequestException("cannotDeletePaidSale");

        saleRepository.delete(sale);
    }

    @Transactional
    public void deleteAll(List<Long> ids) {
        ids.forEach(this::delete);
    }

    private boolean hasSuccessfulPayment(Sale sale) {
        return sale.getPayments().stream().anyMatch(it -> it.getStatus() == PaymentStatus.SUCCESS);
    }

}
