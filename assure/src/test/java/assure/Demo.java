package assure;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Demo {

	@BeforeClass
	public void BeforeClass() {
		System.out.println("BeforeClass---3");
	}

	@BeforeMethod
	public void BeforeMethod() {
		System.out.println("BeforeMethod---4");
	}

	@BeforeSuite
	public void BeforeSuite() {
		System.out.println("BeforeSuite---1");
	}

	@BeforeTest
	public void BeforeTest() {
		System.out.println("BeforeTest---2");
	}

	@Test(priority = 1)
	public void test() {
		System.out.println("test---1");
	}

	@Test(priority = 2)
	
	public void test1() {
		System.out.println("test---2");
	}

	@AfterClass
	public void AfterClass() {
		System.out.println("AfterClass---4");
	}

	@AfterMethod
	public void AfterMethod() {
		System.out.println("AfterMethod---1");
	}

	@AfterSuite
	public void AfterSuite() {
		System.out.println("AfterSuite---3");
	}

	@AfterTest
	public void AfterTest() {
		System.out.println("AfterTest---2");
	}
	@Test(priority = 3)
	public void test3() {
		System.out.println("test--3");
	}
}
