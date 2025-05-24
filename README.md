# Okey Simulator

Bu proje, 4 oyunculu Türk Okey oyununun simülasyonunu gerçekleştiren bir Java uygulamasıdır.

## Özellikler

- 106 taşlı tam okey destesi (her renkten 2×1–13, + 2×sahte-okey)
- Gösterge taşı belirleme ve gerçek okey tanımlama
- 15/14 taş dağıtımı
- El değerlendirme (seri/run ve grup/set kombinasyonları)
- Joker kullanımı (sahte-okey ve gösterge-okey)
- Backtracking ile optimal el hesaplama
- En düşük deadwood sayısına göre kazanan belirleme

## Gereksinimler

- Java 8 veya üzeri
- Maven 3.6 veya üzeri

## Kurulum

```bash
git clone https://github.com/yourusername/okey-simulator.git
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

## Okey Kuralları

1. **Deste**: 106 taş (her renkten 1-13 arası ikişer taş + 2 sahte-okey)
2. **Gösterge**: Sahte-okey olmayan rastgele bir taş
3. **Okey**: Göstergenin aynı renkteki bir üst sayısı
4. **Dağıtım**: Bir oyuncuya 15, diğerlerine 14'er taş
5. **Kazanma**: En az deadwood (bitmeye uzaklık) sayısına sahip oyuncu(lar)

### El Değerlendirme

1. **Seri (Run)**: Aynı renkte ardışık en az 3 taş
2. **Grup (Set)**: Aynı sayılı farklı renkte en az 3 taş
3. **Joker Kullanımı**: Sahte-okey ve gerçek okey herhangi bir taş yerine kullanılabilir
