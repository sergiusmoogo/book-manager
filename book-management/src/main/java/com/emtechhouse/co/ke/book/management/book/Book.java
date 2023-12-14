package com.emtechhouse.co.ke.book.management.book;


import jakarta.persistence.*;
import lombok.*;

@Entity
//@Getter
//@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private Author author;

    private int publicationYear;
    private double price;


}
