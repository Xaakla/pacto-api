package com.app.pactoapi.services;

import com.app.pactoapi.database.entities.Payment;
import com.app.pactoapi.database.entities.Sale;
import com.app.pactoapi.database.repositories.PaymentRepository;
import com.app.pactoapi.enums.PaymentStatus;
import com.app.pactoapi.exceptions.errors.BadRequestException;
import com.app.pactoapi.exceptions.errors.NotFoundException;
import com.app.pactoapi.paymentprocessing.PaymentProcessorFactory;
import com.app.pactoapi.paymentprocessing.models.CreditCardModel;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final SaleService saleService;
    private final PaymentRepository paymentRepository;
    private final PaymentProcessorFactory paymentProcessorFactory;

    public PaymentService(SaleService saleService,
                          PaymentRepository paymentRepository,
                          PaymentProcessorFactory paymentProcessorFactory) {
        this.saleService = saleService;
        this.paymentRepository = paymentRepository;
        this.paymentProcessorFactory = paymentProcessorFactory;
    }

    @Transactional
    public Payment payWithCreditCard(Long saleId, CreditCardModel creditCardModel, Long amount) {
        final var sale = saleService.findById(saleId);
        assertPaymentAmountIsLessThanTotalRemainingAmount(sale, amount);

        final var paymentProcessor = paymentProcessorFactory.getByCurrency(sale.getCurrency());
        final var validationResult = paymentProcessor.validateCard(creditCardModel);
        if (!validationResult.isValid())
            throw new BadRequestException("Credit card validation failed");

        final var paymentResult = paymentProcessor.payWithCreditCard(creditCardModel, sale.getId().toString(), amount);

        return paymentRepository.save(new Payment(null, paymentResult.getTransactionId(), paymentProcessor.getType(), paymentResult.getPaymentStatus(), sale, amount));
    }

    public Payment cancelPayment(Long paymentId) {
        final var payment = findById(paymentId);
        if (!payment.getStatus().isRefundable()) {
            throw new BadRequestException("Payment is not refundable");
        }

        final var paymentProcessor = paymentProcessorFactory.getByPaymentProcessorType(payment.getPaymentProcessor());
        final var cancellationResult = paymentProcessor.cancelPayment(payment.getTransactionId(), payment.getAmount());

        if (cancellationResult.isFailed())
            throw new BadRequestException("Payment cancelation failed");

        payment.setStatus(PaymentStatus.REFUNDED);
        return paymentRepository.save(payment);
    }

    private void assertPaymentAmountIsLessThanTotalRemainingAmount(Sale sale, Long amount) {
        final var alreadyPaid = sale.getPayments().stream()
                .filter(it -> it.getStatus() == PaymentStatus.SUCCESS).mapToLong(Payment::getAmount).sum();

        if (alreadyPaid + amount > sale.getAmount()) {
            throw new BadRequestException("Amount is too large for this sale");
        }
    }

    private Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new NotFoundException("payment not found"));
    }

    public List<Payment> findBySaleId(Long saleId) {
        return paymentRepository.findAllBySaleId(saleId);
    }
}
