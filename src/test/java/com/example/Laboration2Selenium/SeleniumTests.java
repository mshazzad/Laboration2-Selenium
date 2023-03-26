package com.example.Laboration2Selenium;



//import org.junit.jupiter.api.*;
import dev.failsafe.internal.util.Assert;
import net.bytebuddy.build.Plugin;
import net.bytebuddy.dynamic.loading.ByteArrayClassLoader;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.function.Try;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.test.context.event.annotation.AfterTestExecution;

//import static com.example.Selenium.demo.BaseClass.driver;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class SeleniumTests {


    BaseClass baseClass = new BaseClass();

  @BeforeEach
  void Start()
   {
    // start driver accept cookies using help av Base class.
       BaseClass.NavigatePage("https://svtplay.se");
       BaseClass.AcceptSvtCookies();
    }




    // Test 1: Kontrollera att webbplatsens titel stämmer
    @Test
    void CheckSvtPlayWebPageTitle(){

        String svtTitle1=  BaseClass.driver.getTitle();
        assertEquals("SVT Play", svtTitle1);
        BaseClass.driver.quit();
    }
    //Test2: Kontrollera att webbplatsens logotyp är synlig
    @Test
    void CheckSvtPlayLogoVisible(){
        WebElement logo = BaseClass.driver.findElement(By.className("sc-31022b15-0"));
        logo.isEnabled();
        logo.isDisplayed();
        String svt =logo.getText();
        assertEquals( "SVT Play logotyp", svt );
        BaseClass.driver.quit();

    }
    // Test3: Kontrollera namnen på de tre länkarna i huvudmenyn “Start, Program, Kanaler”
    @Test
    void checkHuvudMenynLink(){

         WebElement kanalLink = BaseClass.driver.findElement(By.xpath("/html/body/div/div/div[3]/div/header/div[2]/div/div/nav/ul/li[3]/a"));
        String kanal =kanalLink.getText();

        WebElement program = BaseClass.driver.findElement(By.xpath("/html/body/div/div/div[3]/div/header/div[2]/div/div/nav/ul/li[2]/a"));
        String program1 =program.getText();

        WebElement start = BaseClass.driver.findElement(By.xpath("/html/body/div/div/div[3]/div/header/div[2]/div/div/nav/ul/li[1]/a"));
        String start1 =start.getText();

        WebDriverWait wait = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(10));
        assertEquals("KANALER",kanal);
        assertEquals("PROGRAM",program1);
        assertEquals("START", start1);

        BaseClass.driver.quit();
   }

    // Test4: Kontrollera att länken för “Tillgänglighet i SVT Play” är synlig och att rätt länktext visas.
    @Test
    void CheckTillganglighetVisible()  {

        WebElement linkText1 = BaseClass.driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]/a/span[2]"));
        String text=  linkText1.getText();
        assertEquals("Tillgänglighet i SVT Play", text);
        BaseClass.driver.quit();
    }

    // Test5: Följ länken Tillgänglighet i SVT Play och kontrollera huvudrubriken
    @Test
    void CheckTillganglighetToHuvudRubrik()  {
        WebElement linkText1 = BaseClass.driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]/a/span[2]"));
        linkText1.click();

        WebElement h1Text = BaseClass.driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/main/div/div/div[1]/h1"));
        String rubrik=  h1Text.getText();
        assertEquals("Så arbetar SVT med tillgänglighet", rubrik);
        BaseClass.driver.quit();
    }

    // Test6: Använd metoden “click()” för att navigera in till sidan “Program”. Kontrollera antalet kategorier som listas.
    @Test
    void CheckNavigateToProgram()
    {
        //BaseClass.NavigatePage("https://svtplay.se");
        //BaseClass.AcceptSvtCookies();

        WebElement program = BaseClass.driver.findElement(By.xpath("//a[@href=\"/program\"]"));
        program.click();

        WebDriverWait wait1 = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(20));
        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("sc-a9073dc0-0")));

        List<WebElement> programList = BaseClass.driver.findElements(By.className("sc-a9073dc0-0"));
        assertEquals(17, programList.size(), "Antalet Program har andrät ...");  // Antal program list ändrar i SVT play.Det är inte fast
        BaseClass.driver.quit();
    }

    // (Ytterligare 5 olika test för SVT Play)
    @Test
    void CheckSearchFilm_ClassName()   //Test7: Använd sök förmular och hitta söktexter, kontrollera om det hittats
    {
        WebElement programSearch = BaseClass.driver.findElement(By.className("sc-8961da81-13"));

        programSearch.sendKeys("Filmer");
        programSearch.sendKeys(Keys.ENTER);

        WebDriverWait wait1 = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(20));
        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("sc-da02705a-8")));
        WebElement checkSearch = BaseClass.driver.findElement(By.className("sc-da02705a-8"));
        String text=   checkSearch.getText();
        assertEquals("Filmer", text);
       // programSearch.click();
        BaseClass.driver.quit();
    }
@Test
    void CheckKanaler_Xpath() //Test8: Navigera till Kanal och hitta "Tv-tablå"
    {
        WebElement kanalLink = BaseClass.driver.findElement(By.xpath("/html/body/div/div/div[3]/div/header/div[2]/div/div/nav/ul/li[3]/a"));
        kanalLink.click();

        WebDriverWait wait1 = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(15));
        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"tv-tabla\"]")));

        WebElement svt1 = BaseClass.driver.findElement(By.xpath("//*[@id=\"tv-tabla\"]"));
        String svat1check= svt1.getText();
        assertEquals("Tv-tablå", svat1check);
        BaseClass.driver.quit();

    }
    @Test
    void CheckBarnProgram_CSS(){  //Test9: Kontrollera att kan Navigera till Barn katagorier

      WebElement start1 = BaseClass.driver.findElement(By.cssSelector("[href='/kategori/barn']"));
      start1.click();
      WebDriverWait wait1 = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(15));
      wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("span.sc-6b1c5b48-1")));
      WebElement barn =BaseClass.driver.findElement(By.cssSelector("span.sc-6b1c5b48-1"));
      String barn1= barn.getText();
      assertEquals("BARN", barn1);
      BaseClass.driver.quit();
    }
@Test
    void CheckNyheter()  //Test10: Kontrollera att Nyheter syns
    {
        WebDriverWait wait1 = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(15));
        wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[href='/lista/svtId_jnD6vny/nyheter']")));
        WebElement nyheter = BaseClass.driver.findElement(By.cssSelector("[href='/lista/svtId_jnD6vny/nyheter']"));
        nyheter.click();

        WebDriverWait wait = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"play_main-content\"]/section/div/div/article[1]/div/a/div/img")));
        WebElement image =  BaseClass.driver.findElement(By.cssSelector("a.sc-b6440fda-1[href='/nyheter-direkt']"));  //css=tag.class[attribute=value]
        boolean result = image.isDisplayed();
        assertTrue(result, "Nyheter verkar inte synas...");


        BaseClass.driver.quit();
    }
@Test
    void CheckBackToStart(){   //Test11:  Kontrollera att, kan tillbacka  till Start sida från nyheter
    WebDriverWait wait1 = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(15));
    wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[href='/lista/svtId_jnD6vny/nyheter']")));
    WebElement nyheter = BaseClass.driver.findElement(By.cssSelector("[href='/lista/svtId_jnD6vny/nyheter']"));
    nyheter.click();

    WebElement start = BaseClass.driver.findElement(By.cssSelector("[type=\"start\"]"));
    start.click();
    String svtTitle1=  BaseClass.driver.getTitle();
    assertEquals("SVT Play", svtTitle1);
    BaseClass.driver.quit();
}
@Test
void CheckSearchText(){  // Test12: Kontrollera att sök text visas i sök resulatet
    WebElement programSearch = BaseClass.driver.findElement(By.className("sc-8961da81-13"));

    programSearch.sendKeys("Agenda");
    programSearch.sendKeys(Keys.ENTER);
    WebDriverWait wait1 = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(15));
    wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("span.sc-da02705a-8")));
   //WebElement agenda =BaseClass.driver.findElement(By.cssSelector("p:contains('Fördjupande samhällsprogram')"));
   WebElement agenda = BaseClass.driver.findElement(By.cssSelector("span.sc-da02705a-8"));

    String agendaText = agenda.getText();
    assertEquals("Agenda", agendaText);
    BaseClass.driver.quit();
}


/*
    @AfterAll
    static void teardown() {

           }


Locate by ID == tag#id (or) #id
Locate by class ==Tag.className
Locate by Name or any attribute==tagName[attribute=‘attribute_Value’] (or) [attribute=‘attribute_Value’]


css=tag:contains("inner text")
css=tag#id
css=tag.class
css=tag[attribute=value]
css=tag.class[attribute=value]
     */




}
