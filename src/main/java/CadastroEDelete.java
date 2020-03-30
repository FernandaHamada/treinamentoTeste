import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class CadastroEDelete {

    private ChromeDriver driver;
    private WebDriverWait wait;
    private static String name = "";
    private Random random;

    @Before
    public void acessarUrl(){
        System.getProperty("webdriver.chrome.driver", "chromedriver.exe");
        this.driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 90);
        driver.get("https://automacaocombatista.herokuapp.com/treinamento/home");
        driver.manage().window().maximize();
    }

    @Test
    public void cadastroSucesso(){
        this.driver.findElement(By.xpath("//a[@class='collapsible-header ']")).click();

        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@href='/users/new']"))));
        this.driver.findElement(By.xpath("//a[@href='/users/new']")).click();

        this.random = new Random();
        int number = random.nextInt() * 1000;
        setName("Bia" + number);

        this.driver.findElement(By.id("user_name")).sendKeys(getName());
        this.driver.findElement(By.id("user_lastname")).sendKeys("Almeida");
        this.driver.findElement(By.id("user_email")).sendKeys("AdriAlmeida@teste.com.br");
        this.driver.findElement(By.id("user_address")).sendKeys("Rua das flores, 22");
        this.driver.findElement(By.id("user_university")).sendKeys("Universidade do Sul");
        this.driver.findElement(By.id("user_profile")).sendKeys("Agente de Eventos");
        this.driver.findElement(By.id("user_gender")).sendKeys("Feminino");
        this.driver.findElement(By.id("user_age")).sendKeys("30");

        this.driver.findElement(By.xpath("//input[@name='commit']")).click();
        this.wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id("notice"))));
        this.driver.findElement(By.id("notice")).getText();
        String valorTela = this.driver.findElement(By.id("notice")).getText();

        Assert.assertEquals("Usuário Criado com sucesso", valorTela);
    }

    @Test
    public void apagarUsuario() throws InterruptedException {
        this.driver.findElement(By.xpath("//a[@class='collapsible-header ']")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@href='/users']"))));
        this.driver.findElement(By.xpath("//a[@href='/users']")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement((By.xpath("/html/body/div[3]/div/table/tbody/tr[1]/td[11]/a")))));
        while(this.driver.findElements(By.xpath("//a[contains(text(),'delete') and parent::td/parent::tr/child::td[contains(text(),'"+getName()+"')]]")).size() == 0){
            this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[contains(text(),'Próximo')]"))));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,5000)", "");
            jse.executeScript("arguments[0].click();", this.driver.findElement(By.xpath("//a[contains(text(),'Próximo')]")));
            Thread.sleep(1000);
        }
        this.driver.findElement(By.xpath("//a[contains(text(),'delete') and parent::td/parent::tr/child::td[contains(text(),'"+getName()+"')]]")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        this.wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id("notice"))));
        String texto_confirma = this.driver.findElement(By.id("notice")).getText();
        Assert.assertEquals("Seu Usuário foi removido com sucesso!", texto_confirma);
    }

    @Test
    public void selecionaLista() throws InterruptedException {
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@class='collapsible-header waves-teal' and contains(text(),'Busca de elementos')]"))));
        this.driver.findElement(By.xpath("//a[@class='collapsible-header waves-teal' and contains(text(),'Busca de elementos')]")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@href='/buscaelementos/dropdowneselect']"))));
        this.driver.findElement(By.xpath("//a[@href='/buscaelementos/dropdowneselect']")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//div[@class='select-wrapper' and following-sibling::label[contains(text(),'Desenho Favorito')]]"))));
        this.driver.findElement(By.xpath("//div[@class='select-wrapper' and following-sibling::label[contains(text(),'Desenho Favorito')]]")).click();
        Thread.sleep(3000);
        this.driver.findElement(By.xpath("//span[contains(text(),'Dragon Ball')]")).click();
        Select select = new Select(this.driver.findElement(By.xpath("//select[@id='dropdown']")));
        select.selectByVisibleText("Firefox");
        Thread.sleep(3000);
    }

    @After
    public void fecharBrowser(){
        this.driver.quit();
    }
    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        CadastroEDelete.name = name;
    }
}
