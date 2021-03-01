import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

public class testKodu {

    @Test
    public void webSiteAcma(){

        String baseUrl = "https://www.gittigidiyor.com/";
        String lFiyat;
        String sFiyat;

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sabri.aydogan\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.navigate().to(baseUrl); //gittigidiyor.com sitesinin Ana Sayfasına yönlendirme.
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get(baseUrl + "uye-girisi"); //Üye giriş ekranına yönlendirme.

        driver.findElement(By.id("L-UserNameField")).sendKeys("denemegitti@gmail.com");
        driver.findElement(By.id("L-PasswordField")).sendKeys("denemegitti1");
        driver.findElement(By.id("gg-login-enter")).click();
        driver.findElement(By.name("k")).sendKeys("Bilgisayar");
        driver.findElement(By.className("sc-1yew439-1")).click();

        driver.navigate().to("https://www.gittigidiyor.com/arama/?k=Bilgisayar&sf=2");

        int minimum = 0;
        int maksimum = 48;
        int randomSayi = ThreadLocalRandom.current().nextInt(minimum, maksimum + 1);
        System.out.println(randomSayi);

        driver.findElement(By.xpath(".//*[@product-index='"+randomSayi+"']")).click(); //random ürün seçme

        WebElement elementToClick = driver.findElement(By.xpath("//button[contains(text(), 'Sepete Ekle')]"));

        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+elementToClick.getLocation().y+")");

        elementToClick.click();


        //Liste fiyatının çekilmesi
        WebElement element = driver.findElement(By.cssSelector("#sp-price"));
        lFiyat = element.getAttribute("value");
        System.out.println(lFiyat);
        //System.out.println(element.getText());
        //lFiyat=element.getText();

        driver.findElement(By.className("dIB")).click();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Seçilen ürünün id'sinin bulunması
        WebElement activeElement = driver.findElement(By.className("product-item-box"));
        String id = activeElement.getAttribute("id");
        System.out.println(id);

        //Seçilen ürünün sepetteki fiyatının bulunması
        WebElement fiyatElement = driver.findElement(By.cssSelector("#"+id+" > div.gg-w-24.gg-d-24.gg-t-24.gg-m-24.padding-none.product-item-box-container > div.gg-w-6.gg-d-6.gg-t-6.gg-m-16.pull-right-m > div.total-price-box > input"));
        sFiyat = fiyatElement.getAttribute("value");
        System.out.println("Ürünün sepet fiyatı: "+sFiyat);//ürün fiyat alma

        //Sepetteki ürünün adetinin 2 yapılması
        driver.findElement(By.className("number-selection")).click();
        driver.findElement(By.cssSelector("#"+id+" > div.gg-w-24.gg-d-24.gg-t-24.gg-m-24.padding-none.product-item-box-container > div.gg-w-3.gg-d-3.gg-t-4.gg-m-16.pull-right-m.hidden-m.padding-none > div > div.gg-input.gg-input-select > select > option:nth-child(2)")).click();

        float lf=Float.parseFloat(lFiyat);
        float sf=Float.parseFloat(sFiyat);
        if (lf==sf){
            System.out.println("liste fiyatı : "+lf+" Sepet fiyatı : "+sf+" eşittir.");
        }else{
            System.out.println("liste fiyatı : "+lf+" Sepet fiyatı : "+sf+" eşit değildir.");
        }
        //Sepetteki ürünün sepetten kaldırılması
        //driver.findElement(By.className("btn-delete")).click();
    }
}
