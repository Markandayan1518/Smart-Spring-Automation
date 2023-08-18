package com.example.demo.pages.utils;

import com.example.demo.pages.GooglePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GooglePageUtil {

    private final GooglePage googlePage;

    @Autowired
    public GooglePageUtil(GooglePage googlePage) {
        this.googlePage = googlePage;
    }

    public void performSearch(String query) {
        googlePage.search(query);
        googlePage.clickSearchButton();
    }
}