package com.uniovi.sdi2223entrega1n.pageobjects;

import com.uniovi.sdi2223entrega1n.entities.User;
import com.uniovi.sdi2223entrega1n.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_UserListView extends PO_NavView {

    public static List<WebElement> getUsersList(WebDriver driver) {
        return SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
    }

    public static void compareOneByOneTwoUsersLists(WebDriver driver, List<WebElement> usersList, List<User> usersSystem) {
        for(int i=0;i< usersList.size(); i++){
            WebElement userWeb=usersList.get(i);
            User user=usersSystem.get(i);
            Assertions.assertEquals(user.getEmail(), userWeb.findElement(By.xpath("./td[1]")).getText());
            Assertions.assertEquals(user.getName(), userWeb.findElement(By.xpath("./td[2]")).getText());
            Assertions.assertEquals(user.getLastName(), userWeb.findElement(By.xpath("./td[3]")).getText());
        }
    }

    public static void clickDeleteButton(WebDriver driver) {
        WebElement deleteBtn=driver.findElement(By.id("btnBorrar"));
        deleteBtn.click();
    }

    public static void markCheckBoxUser(WebDriver driver, WebElement user) {
        WebElement firstUserChechbox=user.findElement(By.name("userIds"));
        firstUserChechbox.click();
    }
}
