package com.automation;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Demo {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get("https://dap.pe-lab3.bdc-rancher.tecnotree.com/dap-web-app/service-configuration");
        driver.manage().window().maximize();
          
    }
}
