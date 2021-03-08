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

	public static void checkMenuMarks(WebDriver driver,String command,int userOrder, String description,
            String score) {
        // Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
        List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'marks-menu')]/a");
        elementos.get(0).click();
        if (command.equals("add")) {
            // Esperamos a aparezca la opción de añadir nota: //a[contains(@href,
            // 'mark/add')]
            elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/add')]");
           
            // Pinchamos en agregar Nota.
            elementos.get(0).click();
           
            // Ahora vamos a rellenar la nota. //option[contains(@value, '4')]
            PO_PrivateView.fillFormAddMark(driver, userOrder, description, score);
           
           
            // Ahora nos desconectamos
        } else if (command.equals("delete")) {
            // Pinchamos en la opción de lista de notas.
            elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'mark/list')]");
            elementos.get(0).click();
           
        }
       
        // Esperamos a que se muestren los enlaces de paginación la lista de notas
        elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
        // Nos vamos a la última página
        elementos.get(3).click();
       
        if (command.equals("delete")){
            elementos = PO_View.checkElement(driver, "free",
                    "//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
            elementos.get(0).click();
            // Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
            SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1", PO_View.getTimeout());
            // Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
            SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1", PO_View.getTimeout());
        } else if (command.equals("add")) {
            // Comprobamos que aparece la nota en la pagina
            elementos = PO_View.checkElement(driver, "text", "Nota Nueva 1");
        }
       
        PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
       
    }
}