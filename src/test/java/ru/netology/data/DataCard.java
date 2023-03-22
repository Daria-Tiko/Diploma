package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataCard {
    private String number;
    private String month;
    private String year;
    private String cardholder;
    private String cvc;
}

