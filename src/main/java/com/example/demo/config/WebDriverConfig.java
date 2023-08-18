package com.example.demo.config;

import com.example.demo.enums.ScreenSize;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebDriverConfig {

    private Config config = ConfigFactory.load();
    
    private Boolean isHeadless() {
        return config.getBoolean("browser.headless");
    }

    private Dimension getScreenSize() {
        String screenSizeConfig = config.getString("browser.screenSize");
        ScreenSize screenSize = ScreenSize.valueOf(screenSizeConfig);
        return new Dimension(screenSize.getWidth(), screenSize.getHeight());
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        if (isHeadless()) {
            options.addArguments("--headless");
        }
        options.addArguments("--window-size=" + getScreenSize().getWidth() + "," + getScreenSize().getHeight());
        options.addArguments(config.getStringList("browser.chrome.args"));
        return options;
    }

    private FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        if (isHeadless()) {
            options.addArguments("--headless");
        }
        options.addArguments("--window-size=" + getScreenSize().getWidth() + "," + getScreenSize().getHeight());
        options.addArguments(config.getStringList("browser.firefox.args"));
        return options;
    }

    private EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        if (isHeadless()) {
            options.addArguments("--headless");
        }
        options.addArguments("--window-size=" + getScreenSize().getWidth() + "," + getScreenSize().getHeight());
        options.addArguments(config.getStringList("browser.edge.args"));
        return options;
    }

    private OperaOptions getOperaOptions() {
        OperaOptions options = new OperaOptions();
        if (isHeadless()) {
            options.addArguments("--headless");
        }
        options.addArguments("--window-size=" + getScreenSize().getWidth() + "," + getScreenSize().getHeight());
        options.addArguments(config.getStringList("browser.opera.args"));
        return options;
    }

    @Bean
    public WebDriver webDriver() {
        String browserType = StringUtils.lowerCase(config.getString("browser.type"));

        return switch (browserType) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(getChromeOptions());
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver(getFirefoxOptions());
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver(getEdgeOptions());
            }
            case "opera" -> {
                WebDriverManager.operadriver().setup();
                yield new OperaDriver(getOperaOptions());
            }
            default -> throw new IllegalArgumentException("Invalid browser type: " + browserType);
        };
    }
}

