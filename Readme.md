**Дипломная работа профессии «Тестировщик ПО»**
=

Суть проекта
=

Вэб-сервис: покупка тура "Путешествие дня" 

Приложение предлагает купить тур по определённой цене с помощью двух способов:

1) Оплата по дебетовой карте
2) Оплата по кредитной карте

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

* Сервису платежей - Payment Gate
* Кредитному сервису - Credit Gate

Приложение в собственной СУБД сохраняет информацию о том, каким способом был совершён платёж и успешно ли он был совершён.

Установка и запуск
----
1. Загрузить на свой ПК репозиторий с проектом

Для запуска тестов на вашем ПК должно быть установлено следующее ПО:
- IntelliJ IDEA
- Git
- Docker Desktop
- Google Chrome (или другой браузер)

2. Открыть проект в IntelliJ IDEA
3. Запускаем контейнеры из файла docker-compose.yml командой в терминале
        
       docker-compose up -d

4. Запускаем SUT командой в терминале:
   - для MySQL:

         java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
   - для PostgreSQL:
   
         java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

5. Запускаем авто-тесты командой в терминале:
   - для MySQL:
   
         ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
   - для PostgreSQL:
   
         ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"

    > Сервис будет доступен в браузере по адресу: http://localhost:8080/


6. Генерируем отчёт по итогам тестирования с помощью Allure. Отчёт автоматически откроется в браузере с помощью команды в терминале:

         ./gradlew allureServe
После генерации и работы с отчётом, останавливаем работу allureServe в терминале сочетанием клавиш CTRL + C и подтверждаем действие в терминале вводом Y.

Если необходимо перезапустить контейнеры, приложение или авто-тесты, нужно остановить работу сервисов в терминале сочетанием клавиш CTRL + C и перезапустить их, повторив шаги 1-3.
