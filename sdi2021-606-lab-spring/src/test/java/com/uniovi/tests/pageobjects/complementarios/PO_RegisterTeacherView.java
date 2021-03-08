package com.uniovi.tests.pageobjects.complementarios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.util.SeleniumUtils;

public class PO_RegisterTeacherView extends PO_NavView {
	static public void fillFormAddTeacher(WebDriver driver, int userOrder, String nombrep, String apellidosp, String categoriap, String dnip) {
		// Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 5);
		// Seleccionamos el alumnos userOrder
		new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
		// Rellenemos el campo de descripción
		WebElement dni = driver.findElement(By.name("dni"));
		dni.clear();
		dni.sendKeys(dnip);
		WebElement nombre = driver.findElement(By.name("nombre"));
		nombre.clear();
		nombre.sendKeys(nombrep);
		WebElement apellidos = driver.findElement(By.name("apellidos"));
		apellidos.clear();
		apellidos.sendKeys(apellidosp);
		WebElement categoria = driver.findElement(By.name("categoria"));
		categoria.clear();
		categoria.sendKeys(categoriap);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
}
