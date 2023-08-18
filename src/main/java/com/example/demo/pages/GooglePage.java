package com.example.demo.pages;
import com.example.demo.annotation.FindByConfig;
import com.example.demo.annotation.Name;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class GooglePage extends BasePage {

    public GooglePage(WebDriver driver) {
        super(driver);
    }

    @Name("Search Box")
    @FindByConfig("google.textbox.search.name")
    private WebElement searchBox;


    @Name("Search Button")
    @FindByConfig("google.button.search.name")
    private WebElement searchButton;


    public void search(String text) {
        searchBox.sendKeys(text);
        searchButton.click();
    }

    public void clickSearchButton() {
        searchButton.click();
    }
}
