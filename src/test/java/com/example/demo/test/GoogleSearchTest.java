package com.example.demo.test;

import com.example.demo.annotation.CustomFileSource;
import com.example.demo.annotation.CustomParameterizedTest;
import com.example.demo.annotation.CustomParameterizedTestExtension;
import com.example.demo.data.TestData;
import com.example.demo.pages.utils.GooglePageUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(CustomParameterizedTestExtension.class)
public class GoogleSearchTest {

    @Autowired
    private WebDriver driver;

    @Autowired
    private GooglePageUtil googlePageUtil;

    @Test
    @CustomParameterizedTest(name = "Search Test #{index} - Search for: {0}")
    @CustomFileSource(file = "/testcases/data.csv", clazz = TestData.class)
    public void performGoogleSearch(TestData testData) {
        driver.get("https://www.google.com");
        googlePageUtil.performSearch(testData.getSearchTerm());

        String actualTitle = driver.getTitle();
        Assertions.assertTrue(actualTitle.contains(testData.getExpectedResult()), "Actual title does not contain the expected result.");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

