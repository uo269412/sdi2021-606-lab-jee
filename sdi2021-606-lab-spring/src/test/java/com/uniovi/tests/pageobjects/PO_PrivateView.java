package com.uniovi.tests.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_PrivateView extends PO_NavView {
	static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep) {
		// Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 5);
		// Seleccionamos el alumnos userOrder
		new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
		// Rellenemos el campo de descripción
		WebElement description = driver.findElement(By.name("description"));
		description.clear();
		description.sendKeys(descriptionp);
		WebElement score = driver.findElement(By.name("score"));
		score.click();
		score.clear();
		score.sendKeys(scorep);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	static public void defaultLogin(WebDriver driver, String user, String password, String expectedTextField) {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, user, password);
		// COmprobamos que entramos en la pagina privada del Profesor
		PO_View.checkElement(driver, "text", expectedTextField);
	}

	public static void checkMenuMarks(WebDriver driver, String command, int userOrder, String description,
			String score) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'marks-menu')]/a");
		elementos.get(0).click();
		if (command.contains("add")) {
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/add')]");
			elementos.get(0).click();
			PO_PrivateView.fillFormAddMark(driver, userOrder, description, score);
			elementos = PO_View.checkElement(driver, "text", "Nota Nueva 1");
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
			elementos.get(3).click();
		}  else if (command.contains("delete")) {
			elementos.get(0).click(); 
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/list')]"); 
			elementos.get(0).click(); 
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]"); 
			elementos.get(3).click(); 
			elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
			elementos.get(0).click(); 
			elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
			elementos.get(3).click(); 
			SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1",PO_View.getTimeout() ); 
		}		
		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");

	}
}