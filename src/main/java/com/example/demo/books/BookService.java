package com.example.demo.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book getBookByid(String id) {
        System.out.println("GETTING BOOK FROM SERVICE layer");
        return Book.getById(id);
    }
}
