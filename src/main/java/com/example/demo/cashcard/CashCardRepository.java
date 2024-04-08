package com.example.demo.cashcard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CashCardRepository extends CrudRepository<CashCard, Long> {
}
