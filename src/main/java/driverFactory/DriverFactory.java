package driverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
	//Singleton design pattern
	private static final DriverFactory instance = new DriverFactory();

	private DriverFactory() {
	}

	public static DriverFactory getInstance() {
		return instance;
	}

	//Factory design pattern
	private static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<WebDriver>();


	public WebDriver getDriver(String browser) {
		if (threadLocal.get() == null) {
			if (browser.equalsIgnoreCase("chrome")) {
				threadLocal.set(new ChromeDriver());

			} else if (browser.equalsIgnoreCase("firefox")) {
				threadLocal.set(new FirefoxDriver());

			}
		}
		return threadLocal.get();
	}

	public WebDriver getDriver(String browser, ChromeOptions options) {
		if (threadLocal.get() == null) {
			if (browser.equalsIgnoreCase("chrome")) {
				threadLocal.set(new ChromeDriver(options));

			}

		}
		return threadLocal.get();
	}
}