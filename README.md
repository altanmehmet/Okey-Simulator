# Okey Simulator

Bu proje, Okey oyununun simülasyonunu gerçekleştiren bir Java uygulamasıdır.

## Özellikler

- 106 taşlı tam okey destesi (her renkten 2×1–13, + 2×sahte-okey)
- 15/14 taş dağıtımı
- El değerlendirme (seri/run ve grup/set kombinasyonları)
- Joker kullanımı (sahte-okey ve okey)
- Backtracking ile optimal el hesaplama
- Kalan taş sayısına sayısına göre kazanan belirleme

## Gereksinimler

- Java 8 veya üzeri
- Maven 3.6 veya üzeri

## Kurulum

```bash
git https://github.com/altanmehmet/Okey-Simulator.git
cd okey-simulator
mvn clean package
```

## Çalıştırma

```bash
java -jar target/okey-simulator-1.0-SNAPSHOT.jar
```

## Proje Yapısı

```
okey-simulator/
├── src/
│   ├── main/java/com/okey/
│   │   ├── model/           # Veri modelleri (Color, Tile)
│   │   ├── game/           # Oyun mantığı (Deck, Player, Game)
│   │   └── util/           # Yardımcı sınıflar (HandEvaluator)
│   └── test/java/com/okey/
│       ├── model/          # Model testleri
│       ├── game/           # Oyun mantığı testleri
│       └── util/           # Yardımcı sınıf testleri
```

