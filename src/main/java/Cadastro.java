import com.google.common.annotations.VisibleForTesting;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Cadastro {

    private ChromeDriver driver; // declaração a nível de classe
    private WebDriverWait wait;

    public ChromeDriver acessaUrl(String url){
        System.getProperty("webdriver.chrome.driver", "chromedriver.exe");
        this.driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 90);
        driver.get(url);
        driver.manage().window().maximize();
        return driver;
    }

    @Test
    public void cadastroSucesso(){
        // acessando o site
       this.driver =  acessaUrl("https://automacaocombatista.herokuapp.com/treinamento/home");
       // clicar no Formulário
       this.driver.findElement(By.xpath("//a[@class='collapsible-header ']")).click();

       wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@href='/users/new']"))));
       // clicar no Criar Usuário
       this.driver.findElement(By.xpath("//a[@href='/users/new']")).click();

       // Preencher os campos obrigatórios
       this.driver.findElement(By.id("user_name")).sendKeys("Fernanda");
       this.driver.findElement(By.id("user_lastname")).sendKeys("Kubayashi");
       this.driver.findElement(By.id("user_email")).sendKeys("fernanda@teste.com.br");

       wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//input[@name='commit']"))));
       //clicar no botão "Criar"
       this.driver.findElement(By.xpath("//input[@name='commit']")).click();

       wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id("notice"))));
       // Pega o texto "Usuário Criado com sucesso"
       this.driver.findElement(By.id("notice")).getText();
       String valorTela = this.driver.findElement(By.id("notice")).getText();

        Assert.assertEquals("Usuário Criado com sucesso", valorTela);

        this.driver.quit();

    }
}
