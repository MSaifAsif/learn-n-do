package com.example.demo.cashcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
class CashCardApi {

    @Autowired
    private CashCardService cashCardService;

    @PostMapping
    private ResponseEntity<CashCard> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb, Principal principal) {
        CashCard savedCashCard = cashCardService.createCashCard(newCashCardRequest, principal.getName());
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> getById(@PathVariable final Long requestedId, Principal principal) {
        Optional<CashCard> optionalCashCard = cashCardService.getById(requestedId, principal.getName());
        if (optionalCashCard.isPresent()) {
            return ResponseEntity.ok(optionalCashCard.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    private ResponseEntity<Iterable<CashCard>> findAll(Pageable pageable, Principal principal) {
        Page<CashCard> response = cashCardService.findAll(principal.getName(), pageable);
        return ResponseEntity.ok(response.getContent());
    }

    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate, Principal principal) {
        Optional<CashCard> optionalCashCard = Optional.ofNullable(cashCardService.getCardRepositoryByIdAndOwner(requestedId, principal.getName()));
        if (optionalCashCard.isPresent()) {
            CashCard updatedCashCard = new CashCard(optionalCashCard.get().id(), cashCardUpdate.amount(), principal.getName());
            cashCardService.save(updatedCashCard);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCashCard(@PathVariable Long id, Principal principal) {
        if (cashCardService.existsByIdAndOwner(id, principal.getName())) {
            cashCardService.remove(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
