package com.example.demo.cashcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
class CashCardApi {

    @Autowired
    private CashCardRepository cashCardRepository;

    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> getById(@PathVariable final Long requestedId) {
        Optional<CashCard> optionalCashCard = cashCardRepository.findById(requestedId);
        if (optionalCashCard.isPresent()) {
            return ResponseEntity.ok(optionalCashCard.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
