import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class CadastroConta {

        private ChromeDriver driver;
        private WebDriverWait wait;
        private static String nome = "";
        private static String email = "";
        private static String senha = "";
        private Random random;

        @Before
        public void acessarUrl(){
            System.getProperty("webdriver.chrome.driver", "chromedriver.exe");
            this.driver = new ChromeDriver();
            wait = new WebDriverWait(driver, 90);
            driver.get("https://srbarriga.herokuapp.com/login");
            driver.manage().window().maximize();
        }

        @Test
        public void cadastroUsuario(){
            this.driver.findElement(By.xpath("//a[@href='/cadastro']")).click();

            this.random = new Random();
            int number = random.nextInt() * 1000;
            setNome("Jeniffer" + number);
            setEmail("jnTeste2@gmail.com");
            setSenha("123");

            this.driver.findElement(By.id("nome")).sendKeys(getNome());
            this.driver.findElement(By.id("email")).sendKeys(getEmail());
            this.driver.findElement(By.id("senha")).sendKeys(getSenha());

            this.driver.findElement(By.xpath("//input[@value='Cadastrar']")).click();
            wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.xpath("//div[@role='alert']"))));
            String telaUsuarioSucesso = this.driver.findElement(By.xpath("//div[@role='alert']")).getText();
            Assert.assertEquals("Usuário inserido com sucesso", telaUsuarioSucesso);
        }

        @Test
        public void validarLogin(){
            this.driver.findElement(By.xpath("//a[@href='/login']")).click();

            this.random = new Random();
            int number = random.nextInt() * 1000;
            setEmail("jnTeste2@gmail.com");
            setSenha("123");


            this.driver.findElement(By.id("email")).sendKeys(getEmail());
            this.driver.findElement(By.id("senha")).sendKeys(getSenha());

            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[@class='btn btn-primary']"))));
            this.driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();

            wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.xpath("//div[@class='alert alert-success']"))));
            String loginSucesso = this.driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
            Assert.assertEquals("Bem vindo, Jeniffer-1437549200!", loginSucesso);

            criarConta();
        }

        @Test
        public void criarConta(){
            this.driver.findElement(By.xpath("//a[@class='dropdown-toggle']")).click();
            this.driver.findElement(By.xpath("//a[@href='/addConta']")).click();

            wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id("nome"))));
            this.driver.findElement(By.id("nome")).sendKeys("Conta Teste2");
            this.driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();

            wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.xpath("//div[@class='alert alert-success']"))));
            String contaSucesso = this.driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
            Assert.assertEquals("Conta adicionada com sucesso!", contaSucesso);

            criarMovimentacao();
        }

        @Test
        public void criarMovimentacao(){
            this.driver.findElement(By.xpath("//a[@href='/movimentacao']")).click();
            Select select = new Select(this.driver.findElement(By.xpath("//select[@id='tipo']")));
            select.selectByVisibleText("Despesa");
            this.driver.findElement(By.id("data_transacao")).sendKeys("12/10/2019");
            this.driver.findElement(By.id("data_pagamento")).sendKeys("12/11/2019");
            this.driver.findElement(By.id("descricao")).sendKeys("Teste");
            this.driver.findElement(By.id("interessado")).sendKeys("Jeniffer");
            this.driver.findElement(By.id("valor")).sendKeys("123");
            Select selectConta = new Select(this.driver.findElement(By.xpath("//select[@id='conta']")));
            selectConta.selectByVisibleText("Conta Teste2");
            this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.id("status_pago"))));
            this.driver.findElement(By.id("status_pago")).click();
            this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//button[@class='btn btn-primary']"))));
            this.driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
            String movimentacaoComSucesso = this.driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
            Assert.assertEquals("Movimentação adicionada com sucesso!", movimentacaoComSucesso);

        }

        @After
        public void fecharBrowser(){this.driver.quit();}

        public static void setNome(String nome){CadastroConta.nome = nome;}

        public static String getNome(){return nome;}

        public static String getEmail(){return email;}

        public static void setEmail(String email){CadastroConta.email = email;}

        public static String getSenha(){return senha;}

        public static void setSenha(String senha){CadastroConta.senha = senha;}

}
