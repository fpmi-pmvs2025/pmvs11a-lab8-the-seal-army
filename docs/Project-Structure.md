# Диаграмма файлов приложения SuperWeather

## Основная структура проекта
```mermaid
graph TD
    A[pmvs11a-lab8-the-seal-army] --> B[README.md]
    A --> C[assets]
    C --> C2[Database/]
    C --> C3[Diagrams/]
    C --> C4[Screenshots/]

    A --> D[SuperWeather]
    D --> E[app]
    E --> F[src]

    F --> G[androidTest]
    G --> G1[java/by/bsu/superweather]
    G1 --> G2[ExampleInstrumentedTest.kt]
    G1 --> G3[MainActivityTest.kt]
    G1 --> G4[UITest.kt]
    G1 --> G5[WeatherUITest.kt]

    F --> H[main]
    H --> H1[AndroidManifest.xml]
    H --> H2[app_icon-playstore.png]
    H --> H3[java/by/bsu/superweather]

    H3 --> I1[data]
    I1 --> I2[CityDatabaseHelper.kt]
    I1 --> I3[Data.kt]

    H3 --> J1[files]
    J1 --> J2[UI.kt]
    J1 --> J3[Weather.kt]

    H3 --> K1[ui]
    K1 --> K2[theme]
    K2 --> K3[Color.kt]
    K2 --> K4[Theme.kt]
    K2 --> K5[Type.kt]

    H3 --> H4[MainActivity.kt]
    H --> H5[res/]

    F --> L[test]

    A --> M[docs]
    M --> M1[_config.yml]
    M --> M2[Functional-Requirements.md]
    M --> M3[Project-Structure.md]
    M --> M4[Specifications.md]
    M --> M5[Database-Schema.md]
    M --> M6[index.md]
    M --> M7[Presentation.md]
```
## Ключевые директории и файлы

### 1. Модуль данных (`data/`)

| Файл                  | Назначение                                                                 |
|-----------------------|---------------------------------------------------------------------------|
| `CityDatabaseHelper.kt` | **Управление SQLite БД**:<br>- Создание/обновление таблиц<br>- Кэширование городов<br>- Работа с историей поиска |
| `Data.kt`              | **Модель данных погоды**:<br>- Поля: `city`, `time`, `temp` и др.<br>- Парсинг JSON из API |

### 2. UI компоненты (`files/`)

| Компонент            | Функционал                                                                 |
|----------------------|---------------------------------------------------------------------------|
| `UI.kt`        | **Утилиты для UI**:<br>-  Компоненты оформления и элементов интерфейса |
| `Weather.kt`       | **Отображение погоды**:<br>- Обработка погодных данных для показа в UI                                 |

### 3. Ресурсы (res/)
```mermaid
graph LR
    H[res] --> D1[drawable]
    H --> D2[mipmap-anydpi]
    H --> D3[mipmap-anydpi-v26]
    H --> D4[mipmap-hdpi]
    H --> D5[mipmap-mdpi]
    H --> D6[mipmap-xhdpi]
    H --> D7[mipmap-xxhdpi]
    H --> D8[mipmap-xxxhdpi]
    H --> D9[values]
    H --> D10[xml]

    D1 --> I1[ic_launcher_background.xml]
    D1 --> I2[ic_launcher_foreground.xml]
    D1 --> I3[search.xml]
    D1 --> I4[sky.jpg]
    D1 --> I5[skyy.jpg]
    D1 --> I6[sync.xml]
    D1 --> I7[water.jpg]
    D1 --> I8[weather.png]

    D2 --> M1[ic_launcher.xml]
    D2 --> M2[ic_launcher_round.xml]

    D3 --> V1[app_icon.xml]
    D3 --> V2[app_icon_round.xml]

    D4 --> H1[app_icon.webp]
    D4 --> H2[app_icon_foreground.webp]
    D4 --> H3[app_icon_round.webp]

    D5 --> M5[app_icon.webp]
    D5 --> M6[app_icon_foreground.webp]
    D5 --> M7[app_icon_round.webp]

    D6 --> X1[app_icon.webp]
    D6 --> X2[app_icon_foreground.webp]
    D6 --> X3[app_icon_round.webp]

    D7 --> XX1[app_icon.webp]
    D7 --> XX2[app_icon_foreground.webp]
    D7 --> XX3[app_icon_round.webp]

    D8 --> XXX1[app_icon.webp]
    D8 --> XXX2[app_icon_foreground.webp]
    D8 --> XXX3[app_icon_round.webp]

    D9 --> V9[app_icon_background.xml]
    D9 --> V10[colors.xml]
    D9 --> V11[strings.xml]
    D9 --> V12[themes.xml]

    D10 --> X10[backup_rules.xml]
    D10 --> X11[data_extraction_rules.xml]
```

### 4. Тесты (src/test/)
<img src="https://github.com/fpmi-pmvs2025/pmvs11a-lab8-the-seal-army/blob/f376e8f1421231fb36f22e160b1f776dbc767f31/assets/Diagrams/testDiagram.png" alt="Распределение тестов" width="600"/>

## Полная структура
```gherkin
pmvs11a-lab8-the-seal-army/
├── README.md
├── assets/
│   ├── Database/
│   │   
│   ├── Diagrams/
│   │  
│   └── Screenshots/
│       
├── docs/
│   ├── _config.yml
│   ├── index.md
│   ├── Functional-Requirements.md
│   ├── Project-Structure.md
│   ├── Specifications.md
│   ├── Presentation.md
│   └── Database-Schema.md
└── SuperWeather/
    └── app/
        └── src/
            ├── androidTest/
            │   └── java/by/bsu/superweather/
            │       ├── ExampleInstrumentedTest.kt
            │       ├── MainActivityTest.kt
            │       ├── UITest.kt
            │       └── WeatherUITest.kt
            ├── main/
            │   ├── AndroidManifest.xml
            │   ├── app_icon-playstore.png
            │   ├── java/by/bsu/superweather/
            │   │   ├── data/
            │   │   │   ├── CityDatabaseHelper.kt
            │   │   │   └── Data.kt
            │   │   ├── files/
            │   │   │   ├── UI.kt
            │   │   │   └── Weather.kt
            │   │   ├── ui/
            │   │   │   └── theme/
            │   │   │       ├── Color.kt
            │   │   │       ├── Theme.kt
            │   │   │       └── Type.kt
            │   │   └── MainActivity.kt
            │   └── res/
            │       
            └── test/
                └── java/by/bsu/superweather/
                    ├── CityDatabaseHelperTest.kt
                    ├── DataTest.kt
                    ├── ExampleUnitTest.kt
                    └── WeatherTest.kt
    
```