package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataCard;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private final SelenideElement heading = $$("h3").find(text("Кредит по данным карты"));
    private final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("[placeholder='08']");
    private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement holderField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cvcField = $("[placeholder='999']");

    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    private final SelenideElement notificationSuccess = $(".notification_status_ok");
    private final SelenideElement notificationError = $(".notification_status_error");

    private final SelenideElement validationFieldMessage = $(byText("Поле обязательно для заполнения"));

    private final SelenideElement wrongFormatMessage = $(byText("Неверный формат"));

    private final SelenideElement invalidCharMessage = $(byText("Поле содержит недопустимые символы"));

    private final SelenideElement wrongExpirationMessage = $(byText("Неверно указан срок действия карты"));

    private final SelenideElement cardExpiredMessage = $(byText("Истёк срок действия карты"));

    public CreditPage() {
        heading.shouldBe(visible);
    }

    public void InputData(DataCard card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        holderField.setValue(card.getCardholder());
        cvcField.setValue(card.getCvc());
        continueButton.click();
    }

    public void clearFields() {
        cardNumberField.doubleClick().sendKeys(Keys.BACK_SPACE);
        monthField.doubleClick().sendKeys(Keys.BACK_SPACE);
        yearField.doubleClick().sendKeys(Keys.BACK_SPACE);
        holderField.doubleClick().sendKeys(Keys.BACK_SPACE);
        cvcField.doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    public PaymentPage clear() {
        clearFields();
        return new PaymentPage();
    }

    public void notificationSuccessIsVisible() {
        notificationSuccess.shouldBe(visible, Duration.ofSeconds(11));
    }

    public void notificationErrorIsVisible() {
        notificationError.shouldBe(visible, Duration.ofSeconds(11));
    }

    public void waitForValidationFieldMessage() {
        validationFieldMessage.shouldBe(visible, Duration.ofSeconds(11));
    }

    public void waitForWrongFormatMessage() {
        wrongFormatMessage.shouldBe(visible, Duration.ofSeconds(11));
    }

    public void waitForInvalidCharactersMessage() {
        invalidCharMessage.shouldBe(visible, Duration.ofSeconds(11));
    }

    public void waitForWrongCardExpirationMessage() {
        wrongExpirationMessage.shouldBe(visible, Duration.ofSeconds(11));
    }

    public void waitForCardExpiredMessage() {
        cardExpiredMessage.shouldBe(visible, Duration.ofSeconds(11));
    }
}
