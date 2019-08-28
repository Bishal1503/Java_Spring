package com.JPoP2.LibraryService.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Entity
public class Library implements List<Library> {

    @Id
    @GeneratedValue
    private Long id;
    private String callno;
    private String name;
    private String author;
    private String publisher;
    private Integer quantity;
    private Integer issued;
    private BigDecimal price;

    public Library() {

    }

    public Library(Long id, String callno, String name, String author, String publisher, Integer quantity, Integer issued, BigDecimal price) {
        this.id = id;
        this.callno = callno;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
        this.issued = issued;
        this.price = price;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCallno() {
        return callno;
    }

    public void setCallno(String callno) {
        this.callno = callno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIssued() {
        return issued;
    }

    public void setIssued(Integer issued) {
        this.issued = issued;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Library Details{" +
                "id=" + id +
                ", callno=" + callno +
                ", name=" + name + '\'' +
                ", author=" + author + '\'' +
                ", publisher=" + publisher +
                ", quantity=" + quantity +
                ", issued=" + issued +
                ", price=" + price +
                '}';
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Library> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Library library) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Library> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Library> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Library get(int index) {
        return null;
    }

    @Override
    public Library set(int index, Library element) {
        return null;
    }

    @Override
    public void add(int index, Library element) {

    }

    @Override
    public Library remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<Library> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Library> listIterator(int index) {
        return null;
    }

    @Override
    public List<Library> subList(int fromIndex, int toIndex) {
        return null;
    }
}