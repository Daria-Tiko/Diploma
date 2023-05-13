package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataGenerator;
import ru.netology.data.SQLHelper;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {
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

    // **Перечень сценариев для вкладки "Купить":**

    // Оплата картой approvedCardNumber
    @Test
    void shouldGetApprovedCard() {
        var mainPage = new StartPage();
        var payment = mainPage.paymentButtonClick();
        payment.inputData(DataGenerator.getValidApprovedCard());
        payment.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getDebitPaymentStatus());
    }

    // Оплата картой ValidDeclinedCardNumber
    @Test
    void shouldRejectPurchaseValidDeclinedCard() {
        var mainPage = new StartPage();
        var payment = mainPage.paymentButtonClick();
        payment.inputData(DataGenerator.getValidDeclinedCardNumber());
        payment.notificationErrorIsVisible();
        assertEquals("DECLINED", SQLHelper.getDebitPaymentStatus());
    }

    // Оплата отсутствующей в условиях картой
    @Test
    void shouldRejectPurchaseInvalidCard() {
        var mainPage = new StartPage();
        var payment = mainPage.paymentButtonClick();
        payment.inputData(DataGenerator.getOutsiderCardNumber());
        payment.notificationErrorIsVisible();
    }

    // Поле "Месяц" имеет значение предшествующее текущему
    @Test
    void shouldDeclinePaymentCardMonthExpired() {
        var mainPage = new StartPage();
        var payment = mainPage.paymentButtonClick();
        payment.inputData(DataGenerator.getValueLastMonth());
        payment.waitForWrongCardExpirationMessage();
    }

    // Формат месяца "00"
    @Test
    void shouldRejectPaymentIfMonthHas00Value() {
        var mainPage = new StartPage();
        var payment = mainPage.paymentButtonClick();
        payment.inputData(DataGenerator.getInvalidMonth());
        payment.waitForWrongCardExpirationMessage();
    }

    // Неактуальный срок действия карты
    @Test
    void shouldDeclinePaymentCardYearExpired() {
        var mainPage = new StartPage();
        var payment = mainPage.paymentButtonClick();
        payment.inputData(DataGenerator.getValueLastYear());
        payment.waitForCardExpiredMessage();
    }

    // Срок действия карты больше на 7 от текущего года
    @Test
    void shouldRefuseToPayIfTheYearExceeds7Years() {
        var mainPage = new StartPage();
        var payment = mainPage.paymentButtonClick();
        payment.inputData(DataGenerator.getValueYearMoreThanCurrent());
        payment.waitForWrongCardExpirationMessage();
    }

    // Поле "Владелец" заполнено кириллицей
    @Test
    void shouldDeclinePaymentIfHolderRu() {
        var mainPage = new StartPage();
        var payment = mainPage.paymentButtonClick();
        payment.inputData(DataGenerator.getValueRuHolder());
        payment.waitForInvalidCharactersMessage();
    }

    // Поле "Владелец" заполнено только имя или только фамилия
    @Test
    void shouldDeclinePaymentIfOwnerNameOnlyEn() {
        var mainPage = new StartPage();
        var payment = mainPage.paymentButtonClick();
        payment.inputData(DataGenerator.getOneValueEnHolder());
        payment.waitForWrongFormatMessage();
    }

    //Поле "Владелец" пустое
    @Test
    void shouldRejectPaymentIfOwnerEmpty() {
        var mainPage = new StartPage();
        var payment = mainPage.paymentButtonClick();
        payment.inputData(DataGenerator.getEmptyHolderCard());
        payment.waitForValidationFieldMessage();
    }

    //двухзначное значения поля "CVC/CVV"
    @Test
    void shouldDeclinePaymentIfCVCTwoDigitValue() {
        var mainPage = new StartPage();
        var payment = mainPage.paymentButtonClick();
        payment.inputData(DataGenerator.getValueInvalidCode());
        payment.waitForWrongFormatMessage();
    }

}
