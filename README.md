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

```kotlin
data class MoviePartialModel(
    val id: Int,
    val name: String?,
    val year: Int?,
    val description: String?,
    val movieLength: Int?,
    val ageRating: Int?,
    val countries: List<Country>?,
    val poster: Poster?
)
```

Это id фильма, название, год выхода, описание, длина, возрастной рейтинг, постер и список стран производства



2. Запрос на получение списка фильмов по названию

```kotlin
    @GET("v1.4/movie/search")
    suspend fun searchByName(
        @QueryMap options: Map<String, String>
    ): MoviesSearchResponse
```

Этот запрос принимает лишь 2 параметра:
"page" - Необходимо для пагинации ("limit" остается страндартный, равный 10)
"query" - Поисковый запрос пользователя

В качестве ответа приходит Json объект со списком фильмов и количеством найденных страниц. Второе нужно для пагинации
Список фильмов конвертируется в объект с необходимой минимальной информацией 

```kotlin
data class MoviePartialModel(
    val id: Int,
    val name: String?,
    val year: Int?,
    val description: String?,
    val movieLength: Int?,
    val ageRating: Int?,
    val countries: List<Country>?,
    val poster: Poster?
)
```



3. Запрос на получение фильма по id

```kotlin
    @GET("v1.4/movie/{movieId}")
    suspend fun getMovieById(@Path("movieId") movieId: Int): MovieByIdResponse
```

Этот запрос принимает в качестве "Path" параметра id фильма. и Возвращает более обширную информацию о фильме.

```kotlin
data class MovieByIdResponse(
    val id: Int,
    val name: String?,
    val year: Int?,
    val description: String?,
    val movieLength: Int?,
    val ageRating: Int?,
    val countries: List<Country>?,
    val poster: Poster?,
    val genres: List<Genre>?,
    val rating: Rating?,
    val persons:List<Person>?,
    val isSeries:Boolean?,
)
```

А именно: id фильма, его название, год выпуска, описание, длительность, возрастной рейтинг, страны производства, постер, список жанров, рейтинг кинопоиска, список людей учавствующих в производстве (актеры, операторы и т.д.) и указатель на то является ли этот объект сериалом или фильмом

### Вспомогательные запросы

1. Запрос на получение списка стран и жанров

```kotlin
    @GET("v1/movie/possible-values-by-field")
    suspend fun getFields(
        @Query("field") field: String
    ): List<Field>
```

Этот запрос принимает один параметр "field" в который передается
либо "countries.name" для получения списка стран
либо "genres.name" для получения списка жанров
Далее эти списки используются при выборе фильтров.



2. Запрос на получение всех доступных изображений для конкретного фильма по id

```kotlin
    @GET("v1.4/image")
    suspend fun getPostersById(
        @Query("movieId") movieId: Int
    ): PostersResponse
```

В качестве ответа приходит список Url ссылок, необходимый для "карусели" на экране с подробной информацией о фильме



3. Запрос на получение списка отзывов по id

```kotlin
    @GET("v1.4/review")
    suspend fun getReviewsById(
        @QueryMap options: Map<String, String>
    ): ReviewsResponse
```

Это принимает id фильма и текущую страницу "page" для пагинации

В качестве ответа приходит список отзывов и общее количество страниц

Каждый отзыв состоит из Названия, самого отзыва, типа (Позитивный, Негативный, Нейтральный) и никнейма/имени автора этого отзыва

```kotlin
data class Review(
    val title: String?,
    val review: String?,
    val type: String?,
    val author: String?,
)
```



4. Запрос на получение списка сезонов и эпизодов, необходимый если показывается информация о сериале

```kotlin
    @GET("v1.4/season")
    suspend fun getSeasonsAndEpisodesById(
        @Query("movieId") movieId: Int
    ): SeasonsAndEpisodesResponse
```

В качестве ответа приходит список сезонов. В каждом сезоне содержится название и список эпизодов

```kotlin
data class Season(
    val name: String?,
    val episodes: List<Episode>?
)
```

В каждоом эпизоде указывается только его номер и название

```kotlin
data class Episode(
    val number: Int?,
    val name: String?,
)
```
