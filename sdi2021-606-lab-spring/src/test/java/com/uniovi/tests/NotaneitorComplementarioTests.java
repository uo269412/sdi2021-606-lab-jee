package com.uniovi.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.pageobjects.complementarios.PO_RegisterTeacherView;
import com.uniovi.tests.util.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class NotaneitorComplementarioTests {

	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Program Files\\Mozilla Firefox\\geckodriver024win64.exe";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	@BeforeClass
	static public void begin() {
	}

	@AfterClass
	static public void end() {
		driver.quit();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// PR01. Añadiendo profesores con datos válidos.
	@Test
	public void PR01() {
		PO_PrivateView.defaultLogin(driver, "99999988F", "123456", "Notas del usuario");
		PO_View.checkElement(driver, "btn", "//a[contains(@href, 'teacher/add')]");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'teacher/add')]"); 
		elementos.get(0).click(); 
		PO_RegisterTeacherView.fillFormAddTeacher(driver, 3, "Pedro", "Suarez", "Ingles", "53429543F"); 
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]"); 
		elementos.get(3).click(); 
		elementos = PO_View.checkElement(driver, "text", "Pedro");
		
	}

	// PR02. Añadiendo profesores con datos inválidos (nombre y categoría inválidos)
	@Test
	public void PR02() {
		PO_PrivateView.defaultLogin(driver, "99999988F", "123456", "Notas del usuario");
		PO_View.checkElement(driver, "btn", "//a[contains(@href, 'teacher/add')]");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'teacher/add')]"); 
		elementos.get(0).click(); 
		PO_RegisterTeacherView.fillFormAddTeacher(driver, 3, "Pedro", "Suarez", "Ingles", "23523523w4532454sfsf"); 
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]"); 
		PO_RegisterTeacherView.checkKey(driver, "Error.teacher.dniFormat", PO_Properties.getSPANISH());
	}

	// P303. Verificar que solo los usuarios autorizados pueden dar de alta un profesor
	@Test
	public void PR03() {
		PO_PrivateView.defaultLogin(driver, "99999993D", "123456", "Notas del usuario");
		PO_View.checkElement(driver, "btn", "//a[contains(@href, 'teacher/add')]");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'teacher/add')]"); 
		elementos.get(0).click(); 
		//Error no es admin, no puede acceder
		PO_PrivateView.defaultLogin(driver, "99999988F", "123456", "Notas del usuario");
		PO_View.checkElement(driver, "btn", "//a[contains(@href, 'teacher/add')]");
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'teacher/add')]"); 
		elementos.get(0).click(); 
	}



}
