package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;


// docker-compose up
// Запуск SUT с поддержкой для MySQL: java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
// Запуск SUT с поддержкой для Postgres: java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();
    private static final String url = System.getProperty("db.url");

    // Подключение к БД
    @SneakyThrows
    public static Connection getConn() {
        return DriverManager.getConnection(url, "app", "pass");
    }

    //Очистка данных
    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
    }

    // Платёж дебетовой картой
    @SneakyThrows
    public static String getDebitPaymentStatus() {
        String SqlStatus = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        return getResult(SqlStatus);
    }

    // Платёж кредитной картой
    @SneakyThrows
    public static String getCreditPaymentStatus() {
        String SqlStatus = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        return getResult(SqlStatus);
    }

    // Статус
    @SneakyThrows
    private static String getResult(String query) {
        String result = "";
        var runner = new QueryRunner();
        try (var connection = getConn()) {
            result = runner.query(connection, query, new ScalarHandler<>());
        }
        return result;
    }

}