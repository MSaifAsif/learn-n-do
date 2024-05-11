package com.example.demo.cashcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CashCardService {

    @Autowired
    private CashCardRepository cashCardRepository;

    CashCard createCashCard(CashCard newCashCardRequest, String principalName) {
        CashCard cashCardWithOwner = new CashCard(null, newCashCardRequest.amount(), principalName);
        return cashCardRepository.save(cashCardWithOwner);
    }

    Optional<CashCard> getById(Long requestedId, String principalName) {
        return Optional.ofNullable(getCardRepositoryByIdAndOwner(requestedId, principalName));
    }

    CashCard getCardRepositoryByIdAndOwner(Long requestedId, String principalName) {
        return cashCardRepository.findByIdAndOwner(requestedId, principalName);
    }

    Page<CashCard> findAll(String name, Pageable pageable) {
        return cashCardRepository.findByOwner(
                name,
                PageRequest.of(
                        pageable.getPageNumber(), pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))
                )
        );
    }

    void save(final CashCard cashCard) {
        cashCardRepository.save(cashCard);
    }

    boolean existsByIdAndOwner(Long id, String name) {
        return cashCardRepository.existsByIdAndOwner(id, name);
    }

    void remove(Long id) {
        cashCardRepository.deleteById(id);
    }
}
