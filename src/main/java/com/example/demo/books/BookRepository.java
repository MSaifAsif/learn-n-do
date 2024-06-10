package com.example.demo.books;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;

@Repository
public interface BookRepository extends CrudRepository<Id, Book> {
}
