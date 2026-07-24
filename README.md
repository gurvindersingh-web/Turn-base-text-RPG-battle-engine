# Star Wars Text-Based RPG Battle Engine

A console-based, turn-based RPG battle engine written in Java, set in the Star Wars universe. Master the Force, wield your lightsaber, and battle iconic enemies scaling up to a final boss showdown to save the galaxy!
<img width="840" height="808" alt="image" src="https://github.com/user-attachments/assets/297021ce-24ce-43eb-856a-0d900ecef8a0" />

---

## 🚀 Features


---

## 🛠️ Requirements

- **Java Development Kit (JDK) 21** or higher.
- **Apache Maven** (for building and executing).

---

## 🎮 How to Play

### 1. Clone & Navigate
```bash
git clone https://github.com/gurvindersingh-web/Turn-base-text-RPG-battle-engine.git
cd Turn-base-text-RPG-battle-engine
```

### 2. Compile and Run
Run the game using Maven:
```bash
mvn compile exec:java
```

### 3. Build a JAR (Optional)
If you'd like to package the project into a JAR file and run it manually:
```bash
mvn clean package
java -cp target/classes game.Main
```

---

## 📂 Project Structure

```
├── pom.xml                  # Maven configuration with Java 21 target
└── src
    └── main
        └── java
            └── game
                ├── Main.java      # Main gameplay loop & entity statistics
                └── AsciiArt.java  # Retrievable ASCII art graphics
```

---

## ⚔️ Game Mechanics

### Stats Progression per Level Up
- **Max HP**: +10 (and heals player to full)
- **Attack**: +2
- **Defense**: +1

### Enemy Attributes (Base Stats)
| Enemy | Base HP | Base Attack | Base Defense |
| :--- | :---: | :---: | :---: |
| **Stormtrooper** | 50 | 8 | 3 |
| **Bounty Hunter** | 60 | 10 | 4 |
| **Sith Apprentice** | 80 | 12 | 6 |
| **Mech 2-3F65G-D (Boss)** | 200 | 20 | 10 |

*Note: Regular enemy stats scale linearly with your level.*
```
Bonus HP = (Player Level - 1) * 10
Bonus Attack = (Player Level - 1) * 2
Bonus Defense = (Player Level - 1) * 1
```

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
