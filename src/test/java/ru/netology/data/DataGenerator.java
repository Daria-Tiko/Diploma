package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private DataGenerator() {
    }

    private static Faker fakerEn = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

    // _____ГЕНЕРАЦИЯ ДАННЫХ для класса DataCard_____

    // генерация валидных значений
    public static DataCard getValidApprovedCard() {
        return new DataCard(approvedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCode());
    }

    public static DataCard getValidDeclinedCardNumber() {
        return new DataCard(declinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCode());
    }
    // генерация невалидных значений

    //случайная карта
    public static DataCard getOutsiderCardNumber() {
        return new DataCard(getRandomCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCode());
    }

    //Прошедший месяц
    public static DataCard getValueLastMonth() {
        return new DataCard(approvedCardNumber(), getLastMonth(), getValidYear(), getValidHolder(), getValidCode());
    }

    //месяц значение "00"
    public static DataCard getInvalidMonth() {
        return new DataCard(approvedCardNumber(), "00", getValidYear(), getValidHolder(), getValidCode());
    }

    //год прошедшее значение
    public static DataCard getValueLastYear() {
        return new DataCard(approvedCardNumber(), getValidMonth(), getLastYear(), getValidHolder(), getValidCode());
    }

    //год значение больше от текущего
    public static DataCard getValueYearMoreThanCurrent() {
        return new DataCard(approvedCardNumber(), getValidMonth(), getNextYear(), getValidHolder(), getValidCode());
    }

    //Владелец кириллицей
    public static DataCard getValueRuHolder() {
        return new DataCard(approvedCardNumber(), getValidMonth(), getValidYear(), getRuHolder(), getValidCode());
    }

    //Владелец только Имя En
    public static DataCard getOneValueEnHolder() {
        return new DataCard(approvedCardNumber(), getValidMonth(), getValidYear(), getEnHolder(), getValidCode());
    }

    //Владелец пустое поле
    public static DataCard getEmptyHolderCard() {
        return new DataCard(approvedCardNumber(), getValidMonth(), getValidYear(), "", getValidCode());
    }

    //CVC/CVV двузначное число
    public static DataCard getValueInvalidCode() {
        return new DataCard(approvedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getTwoDigitValueCode());
    }


    // _____ГЕНЕРАЦИЯ ЗНАЧЕНИЙ_____

    // генерация значения карты допустимого и запрещенного
    public static String approvedCardNumber() {

        return "4444444444444441";
    }

    public static String declinedCardNumber() {

        return "4444444444444442";
    }

    //генерация поля Номер банковской карты - случайное значение
    public static String getRandomCardNumber() {
        return fakerEn.business().creditCardNumber();
    }

    //генерация поля Месяц - валидное значение
    public static String getValidMonth() {
        String validMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return validMonth;
    }

    // генерация поля Месяц - прошедшее значение
    public static String getLastMonth() {
        String lastMonth = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
        return lastMonth;
    }

    //генерация поля Год - валидное значение
    public static String getValidYear() {
        String validYear = LocalDate.now().format(DateTimeFormatter.ofPattern("YY"));
        return validYear;
    }

    //генерация поля Год - прошлое значение
    public static String getLastYear() {
        String lastYear = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("YY"));
        return lastYear;
    }

    // генерация поля Год - значение больше текущего на 7 лет
    public static String getNextYear() {
        String nextYear = LocalDate.now().plusYears(7).format(DateTimeFormatter.ofPattern("YY"));
        return nextYear;
    }

    //генерация поля Владелец - валидные данные
    public static String getValidHolder() {
        return fakerEn.name().fullName();
    }

    //генерация поля Владелец - значение на кириллице
    public static String getRuHolder() {
        return fakerRu.name().fullName();
    }

    //генерация поля Владелец - единичное значение поля на латинице
    public static String getEnHolder() {
        return fakerEn.name().firstName();
    }

    //генерация поля CVC/CVV - валидное значение
    public static String getValidCode() {
        return fakerEn.numerify("###");
    }

    // генерация поля CVC/CVV - двузначное число
    public static String getTwoDigitValueCode() {
        return fakerEn.numerify("##");
    }

}

