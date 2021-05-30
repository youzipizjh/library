package com.library.library.response;

import lombok.Data;

@Data
public class resborrow {
    private String bid;
    private String bname;
    private String author;
    private String isbn;
    private String borrowdate;
    private String shouldreturn;
    private String returndate;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBorrowdate() {
        return borrowdate;
    }

    public void setBorrowdate(String borrowdate) {
        this.borrowdate = borrowdate;
    }

    public String getShouldreturn() {
        return shouldreturn;
    }

    public void setShouldreturn(String shouldreturn) {
        this.shouldreturn = shouldreturn;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }
}
