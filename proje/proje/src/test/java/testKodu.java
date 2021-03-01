import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

public class testKodu {
    String baseUrl = "https://www.gittigidiyor.com/";
    String lFiyat;
    String sFiyat;
    WebDriver driver;
    @Test
    public void webSiteAcma(){

        goHomePage();//Anasayfa yönlendirme methodu

        waitSeconds();

        goLoginPage();//Giriş işlemleri methodu

        waitSeconds();

        searchProduct();//Arama ve Sayfa yönlendirme methodu

        waitSeconds();

        randomProduct();//Random ürün seçme methodu

        waitSeconds();

        addToBasket();//Sepete ekleme methodu


        //Ürünün liste fiyatının çekilmesi
        WebElement element = driver.findElement(By.cssSelector("#sp-price"));
        lFiyat = element.getAttribute("value");
        System.out.println("Ürünün liste fiyatı : "+lFiyat);

        waitSeconds();

        //Sepete yönlendirme
        driver.findElement(By.className("dIB")).click();
        System.out.println("Sepet sayfası açıldı.");

        waitSeconds();

        //Seçilen ürünün id'sinin bulunması
        WebElement activeElement = driver.findElement(By.className("product-item-box"));
        String id = activeElement.getAttribute("id");

        waitSeconds();

        //Seçilen ürünün sepetteki fiyatının bulunması
        WebElement fiyatElement = driver.findElement(By.cssSelector("#"+id+" > div.gg-w-24.gg-d-24.gg-t-24.gg-m-24.padding-none.product-item-box-container > div.gg-w-6.gg-d-6.gg-t-6.gg-m-16.pull-right-m > div.total-price-box > input"));
        sFiyat = fiyatElement.getAttribute("value");
        System.out.println("Ürünün sepet fiyatı: "+sFiyat);

        waitSeconds();

        //Sepetteki ürünün adetinin 2 yapılması
        driver.findElement(By.className("number-selection")).click();
        driver.findElement(By.cssSelector("#"+id+" > div.gg-w-24.gg-d-24.gg-t-24.gg-m-24.padding-none.product-item-box-container > div.gg-w-3.gg-d-3.gg-t-4.gg-m-16.pull-right-m.hidden-m.padding-none > div > div.gg-input.gg-input-select > select > option:nth-child(2)")).click();
        System.out.println("Ürün adeti ikiye çıkarıldı.");

        waitSeconds();

        //Liste fiyatı ve Sepet Fiyatı karşılaştırılması
        float lf=Float.parseFloat(lFiyat);
        float sf=Float.parseFloat(sFiyat);
        if (lf==sf){
            System.out.println("Liste fiyatı : "+lf+" Sepet fiyatı : "+sf+" eşittir.");
        }else{
            System.out.println("Liste fiyatı : "+lf+" Sepet fiyatı : "+sf+" eşit değildir.");
        }

        waitSeconds();

        //Sepetteki ürünün sepetten kaldırılması
        driver.findElement(By.className("btn-delete")).click();
        System.out.println("Sepet boşaltıldı.");
    }

    private void addToBasket() {
        WebElement elementToClick = driver.findElement(By.xpath("//button[contains(text(), 'Sepete Ekle')]"));
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+elementToClick.getLocation().y+")");
        elementToClick.click();
        System.out.println("Seçilen ürün sepete eklendi.");
    }

    private void randomProduct() {
        int minimum = 0;
        int maksimum = 48;
        int randomSayi = ThreadLocalRandom.current().nextInt(minimum, maksimum + 1);
        System.out.println("Random üretilen sayı : "+randomSayi);

        //Random ürün seçme
        driver.findElement(By.xpath(".//*[@product-index='"+randomSayi+"']")).click();
        System.out.println("Random bir ürün seçildi.");
    }

    private void searchProduct() {
        //Bilgisayar kelimesi arama kutusuna yazdırılır ve arama işlemi başlatılır

        driver.findElement(By.name("k")).sendKeys("Bilgisayar");
        driver.findElement(By.className("sc-1yew439-1")).click();

        System.out.println("Bilgisayar kelimesi aratıldı.");

        //Sonuç ekranının 2. sayfasına yönlendirme yapılır
        driver.navigate().to("https://www.gittigidiyor.com/arama/?k=Bilgisayar&sf=2");
        System.out.println("2. sayfaya yönlendirildi.");
    }

    private void goLoginPage() {
        driver.get(baseUrl + "uye-girisi"); //Üye giriş ekranına yönlendirme.

        driver.findElement(By.id("L-UserNameField")).sendKeys("denemegitti@gmail.com");
        driver.findElement(By.id("L-PasswordField")).sendKeys("denemegitti1");
        driver.findElement(By.id("gg-login-enter")).click();
        System.out.println("Üye girişi yapıldı.");
    }

    private void goHomePage() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sabri.aydogan\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.navigate().to(baseUrl); //gittigidiyor.com sitesinin Ana Sayfasına yönlendirme.
        System.out.println("gittigidiyor ana sayfası açıldı.");
    }

    private void waitSeconds() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
