package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class StartPage {

    // Элементы стартовой страницы
    private final SelenideElement heading = $$("h2").find(text("Путешествие дня"));
    private final SelenideElement paymentButton = $$("button").find(text("Купить"));
    private final SelenideElement creditButton = $$("button").find(text("Купить в кредит"));
    private final SelenideElement form = $("form");


    public StartPage() {
        heading.shouldBe(visible);
        paymentButton.shouldBe(visible);
        creditButton.shouldBe(visible);

    }

    //Переход на страницу дебетовой оплаты
    public PaymentPage paymentButtonClick() {
        paymentButton.click();
        form.shouldBe(visible);
        return new PaymentPage();
    }

    //Переход на страницу оплаты в кредит
    public CreditPage creditButtonClick() {
        creditButton.click();
        form.shouldBe(visible);
        return new CreditPage();
    }
}
