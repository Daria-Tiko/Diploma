package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataGenerator;
import ru.netology.data.SQLHelper;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditTest {
    public static String url = System.getProperty("sut.url");

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void openPage() {
        open(url);
    }

    // **Перечень сценариев для вкладки "Купить в кредит"**

    // Оплата картой approved Card
    @Test
    void shouldGetApprovedCard() {
        var mainPage = new StartPage();
        var credit = mainPage.creditButtonClick();
        credit.inputData(DataGenerator.getValidApprovedCard());
        credit.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditPaymentStatus());
    }

    //Оплата картой Declined Card
    @Test
    void shouldRejectPurchaseValidDeclinedCard() {
        var mainPage = new StartPage();
        var credit = mainPage.creditButtonClick();
        credit.inputData(DataGenerator.getValidDeclinedCardNumber());
        credit.notificationErrorIsVisible();
        assertEquals("DECLINED", SQLHelper.getCreditPaymentStatus());
    }

    // Оплата отсутствующей в условиях картой
    @Test
    void shouldRejectPurchaseInvalidCard() {
        var mainPage = new StartPage();
        var credit = mainPage.creditButtonClick();
        credit.inputData(DataGenerator.getOutsiderCardNumber());
        credit.notificationErrorIsVisible();
    }

    // Поле "Месяц" имеет значение предшествующее текущему
    @Test
    void shouldDeclinePaymentCardMonthExpired() {
        var mainPage = new StartPage();
        var credit = mainPage.creditButtonClick();
        credit.inputData(DataGenerator.getValueLastMonth());
        credit.waitForWrongCardExpirationMessage();
    }

    // Формат месяца "00"
    @Test
    void shouldRejectPaymentIfMonthHas00Value() {
        var mainPage = new StartPage();
        var credit = mainPage.creditButtonClick();
        credit.inputData(DataGenerator.getInvalidMonth());
        credit.waitForWrongCardExpirationMessage();
    }

    // Карта просрочена
    @Test
    void shouldDeclinePaymentCardYearExpired() {
        var mainPage = new StartPage();
        var credit = mainPage.creditButtonClick();
        credit.inputData(DataGenerator.getValueLastYear());
        credit.waitForCardExpiredMessage();
    }

    // Срок действия карты больше текущего на 7 лет
    @Test
    void shouldRefuseToPayIfTheYearExceeds7Years() {
        var mainPage = new StartPage();
        var credit = mainPage.creditButtonClick();
        credit.inputData(DataGenerator.getValueYearMoreThanCurrent());
        credit.waitForWrongCardExpirationMessage();
    }

    // Поле "Владелец" заполнено кириллицей
    @Test
    void shouldDeclinePaymentIfHolderRu() {
        var mainPage = new StartPage();
        var credit = mainPage.creditButtonClick();
        credit.inputData(DataGenerator.getValueRuHolder());
        credit.waitForInvalidCharactersMessage();
    }

    // Поле "Владелец" заполнено только имя или только фамилия
    @Test
    void shouldDeclinePaymentIfOwnerNameOnlyEn() {
        var mainPage = new StartPage();
        var credit = mainPage.creditButtonClick();
        credit.inputData(DataGenerator.getOneValueEnHolder());
        credit.waitForWrongFormatMessage();
    }

    // Пустое значение поля "Владелец"
    @Test
    void shouldRejectPaymentIfOwnerEmpty() {
        var mainPage = new StartPage();
        var credit = mainPage.creditButtonClick();
        credit.inputData(DataGenerator.getEmptyHolderCard());
        credit.waitForValidationFieldMessage();
    }

    // Двухзначное значения поля "CVC/CVV"
    @Test
    void shouldDeclinePaymentIfCVCTwoDigitValue() {
        var mainPage = new StartPage();
        var credit = mainPage.creditButtonClick();
        credit.inputData(DataGenerator.getValueInvalidCode());
        credit.waitForWrongFormatMessage();
    }

}
