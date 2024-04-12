# Приложение для поиска фильмов

Приложение позволяет искать фильмы по названию или фильтрам и просматривать детальную информацию о конкретном фильме или сериале.
В приложении используется [API кинопоиска](https://api.kinopoisk.dev/documentation#/)

## Предварительная настройка проекта

Для работы приложения потребуется токен кинопоиска. Способ его получения описан в [документации](https://api.kinopoisk.dev/documentation#/)

После получения токен необходимо добавить в приложение для этого создайте файл KinopoiskApiTokenConst.kt и добавьте его в приложение, например можно разместить его по пути: app/java/com/example/cinemaaggregator/common/network
Внутри файла создайте переменную TOKEN (String) и присвойте ей значение вашего токена. 

Примерное содержание созданного файла:

package com.example.cinemaaggregator.common.network

```kotlin
object KinopoiskApiTokenConst {
    const val TOKEN = "YOUR_TOKEN_HERE"
}
```

Проверьте, что токен работает в классе HeaderInterceptor, также расположенном в app/java/com/example/cinemaaggregator/common/network

При неправильно указанном токене сервер будет возвращать ошибку 401.

## Примеры запросов и ответов

В приложении используются 7 запросов (3 основных и 4 вспомогательных). 
Для каждого запроса укащывается baseUrl("https://api.kinopoisk.dev/") и header "X-API-KEY" с токеном

### Основные запросы

1. Запрос на получение списка фильмов по фильтрам
{

```kotlin
@GET("v1.4/movie")
    suspend fun searchWithFilters(
        @QueryMap options: Map<String, String>
    ): MoviesSearchResponse
```

Этот запрос принимает в себя следующие параметры:
"notNullFields" = "name" - Для получения списка только фильмов с названием
"page" - Необходимо для пагинации ("limit" остается страндартный, равный 10)
"year" - Для фильтрации по году выхода, может принимать в себя диапозон(2007-2017). Этот фильтр может быть пустым
"countries.name" - Страна производства. Этот фильтр может быть пустым
"ageRating" - Возрастное ограничение фильма. Этот фильтр может быть пустым
"genres.name" - Жанр фильма. Этот фильтр может быть пустым
"rating.kp" - Рейтинг с сайта кинопоиска, принимает минимальное значение (от 6). Этот фильтр может быть пустым

В качестве ответа приходит Json объект со списком фильмов и количеством найденных страниц. Второе нужно для пагинации
Список фильмов конвертируется в объект с необходимой минимальной информацией 
![image](https://github.com/BigMag74/Cinema_aggregator/assets/116560396/69a0a8b0-b053-4754-beaf-f8d775d2c48c)

Это id фильма, название, год выхода, описание, длина, возрастной рейтинг, постер и список стран производства

2. Запрос на получение списка фильмов по названию
![image](https://github.com/BigMag74/Cinema_aggregator/assets/116560396/f998d766-3448-4390-951a-0d614a4de7df)
Этот запрос принимает лишь 2 параметра:
"page" - Необходимо для пагинации ("limit" остается страндартный, равный 10)
"query" - Поисковый запрос пользователя

В качестве ответа приходит Json объект со списком фильмов и количеством найденных страниц. Второе нужно для пагинации
Список фильмов конвертируется в объект с необходимой минимальной информацией 
![image](https://github.com/BigMag74/Cinema_aggregator/assets/116560396/69a0a8b0-b053-4754-beaf-f8d775d2c48c)

3. Запрос на получение фильма по id
![image](https://github.com/BigMag74/Cinema_aggregator/assets/116560396/37474554-7fdd-4cfa-b005-dd52b351db1e)
Этот запрос принимает в качестве "Path" параметра id фильма. и Возвращает более обширную информацию о фильме.
![image](https://github.com/BigMag74/Cinema_aggregator/assets/116560396/909c21f7-a63e-4655-a736-bd4323027eb8)
А именно: id фильма, его название, год выпуска, описание, длительность, возрастной рейтинг, страны производства, постер, список жанров, рейтинг кинопоиска, список людей учавствующих в производстве (актеры, операторы и т.д.) и указатель на то является ли этот объект сериалом или фильмом

### Вспомогательные запросы

1. Запрос на получение списка стран и жанров
![image](https://github.com/BigMag74/Cinema_aggregator/assets/116560396/92081295-cb6f-4913-80d2-633c81c0b9b8)
Этот запрос принимает один параметр "field" в который передается
либо "countries.name" для получения списка стран
либо "genres.name" для получения списка жанров
Далее эти списки используются при выборе фильтров.

2. Запрос на получение всех доступных изображений для конкретного фильма по id
![image](https://github.com/BigMag74/Cinema_aggregator/assets/116560396/27c4cec2-5943-4117-9e34-1c24fb87a85d)
В качестве ответа приходит список Url ссылок, необходимый для "карусели" на экране с подробной информацией о фильме

3. Запрос на получение списка отзывов по id
![image](https://github.com/BigMag74/Cinema_aggregator/assets/116560396/a4d41ee9-57a6-4ba1-8909-78e94f6c62e8)
Это принимает id фильма и текущую страницу "page" для пагинации

В качестве ответа приходит список отзывов и общее количество страниц
![image](https://github.com/BigMag74/Cinema_aggregator/assets/116560396/d1653cc9-b930-4649-b749-2e62d096f221)
Каждый отзыв состоит из Названия, самого отзыва, типа (Позитивный, Негативный, Нейтральный) и никнейма/имени автора этого отзыва

4. Запрос на получение списка сезонов и эпизодов, необходимый если показывается информация о сериале
![image](https://github.com/BigMag74/Cinema_aggregator/assets/116560396/3df6fe73-7b22-4e3a-87fa-c2115509103d)
В качестве ответа приходит список сезонов. В каждом сезоне содержится название и список эпизодов
![image](https://github.com/BigMag74/Cinema_aggregator/assets/116560396/5e59e014-29d0-4c4b-a40d-f51dd4bf8bc7)
В каждоом эпизоде указывается только его номер и название
![image](https://github.com/BigMag74/Cinema_aggregator/assets/116560396/234baab4-05b5-490a-a355-0c6e433de359)
