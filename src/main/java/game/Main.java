package game;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);
    public static class Player {
        String name;
        int hp, maxHp, attack, defense, level, healUses;
        boolean defending;

        Player(String name) {
            this.name = name;
            this.level = 1;
            this.maxHp = 100;
            this.hp = 100;
            this.attack = 15;
            this.defense = 5;
            this.healUses = 2;
            this.defending = false;
        }

        void levelUp() {
            level++;
            maxHp += 10;
            hp = maxHp; // Fully heal on level up
            attack += 2;
            defense += 1;
        }

        boolean isAlive() {
            return hp > 0;
        }
    }

    // Beginner-friendly Enemy class
    public static class Enemy {
        String name;
        int hp, maxHp, attack, defense;
        String asciiArt;

        Enemy(String name, int hp, int attack, int defense, String asciiArt) {
            this.name = name;
            this.hp = hp;
            this.maxHp = hp;
            this.attack = attack;
            this.defense = defense;
            this.asciiArt = asciiArt;
        }

        boolean isAlive() {
            return hp > 0;
        }
    }

    // Helper method to create an enemy based on the player's level
    static Enemy createRandomEnemy(int playerLevel) {
        int roll = random.nextInt(3); // 0, 1, or 2
        
        // Stats scale slightly with player level
        int bonusHp = (playerLevel - 1) * 10;
        int bonusAttack = (playerLevel - 1) * 2;
        int bonusDefense = (playerLevel - 1) * 1;

        if (roll == 0) {
            return new Enemy("Stormtrooper", 50 + bonusHp, 8 + bonusAttack, 3 + bonusDefense, AsciiArt.ENEMY_1);
        } else if (roll == 1) {
            return new Enemy("Sith Apprentice", 80 + bonusHp, 12 + bonusAttack, 6 + bonusDefense, AsciiArt.ENEMY_2);
        } else {
            return new Enemy("Bounty Hunter", 60 + bonusHp, 10 + bonusAttack, 4 + bonusDefense, AsciiArt.ENEMY_3);
        }
    }

    // A simple method to get a number from the user between min and max
    static int readInt(int min, int max) {
        while (true) {
            System.out.print("Choose action (" + min + "-" + max + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= min && choice <= max) {
                    System.out.println();
                    return choice;
                }
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(AsciiArt.INTRO_LOGO);
        System.out.println("=== STAR WARS RPG ===");
        
        System.out.print("Are you a male or female Jedi? (m/f): ");
        String gender = scanner.nextLine().trim().toLowerCase();
        if (gender.startsWith("f")) {
            System.out.println(AsciiArt.FEMALE_CHAR);
        } else {
            System.out.println(AsciiArt.MALE_CHAR);
        }
        
        System.out.print("Enter your Jedi's name: ");
        String heroName = scanner.nextLine().trim();
        if (heroName.isEmpty()) {
            heroName = "Jedi";
        }

        Player player = new Player(heroName);
        System.out.println("Welcome, Jedi " + player.name + "! May the Force be with you.");

        int wins = 0; // Track number of wins
        boolean playing = true; // Main game loop flag
        boolean bossDefeated = false; // Final boss status

        // Main Game Loop
        while (playing && player.isAlive()) {
            Enemy enemy;
            boolean isBoss = false;
            
            if (wins == 3) { // Boss battle after 3 wins
                enemy = new Enemy("Mech 2-3F65G-D", 200, 20, 10, AsciiArt.BOSS);
                isBoss = true;
            } else {
                enemy = createRandomEnemy(player.level);
            }
            
            player.healUses = 2; // Reset heals for each new battle

            System.out.println("\n-----------------------------------------");
            if (isBoss) {
                System.out.println("BOSS BATTLE: A wild " + enemy.name + " appears!");
            } else {
                System.out.println("A wild " + enemy.name + " appears!");
            }
            System.out.println(enemy.asciiArt);
            System.out.println("-----------------------------------------");

            int round = 1;
            boolean battleOver = false;

            // Battle Loop
            while (player.isAlive() && enemy.isAlive() && !battleOver) {
                System.out.println("\n--- Round " + round + " ---");
                System.out.println(player.name + " (Level " + player.level + ") HP: " + player.hp + "/" + player.maxHp);
                System.out.println(enemy.name + " HP: " + enemy.hp + "/" + enemy.maxHp);
                
                player.defending = false;
                boolean validAction = false;

                // Player Turn
                // Player Turn
                while (!validAction) {
                    System.out.println("\n1. Attack");
                    System.out.println("2. Defend");
                    System.out.println("3. Heal (" + player.healUses + " uses left)");

                    int action = readInt(1, 3);

                    if (action == 1) { // Attack
                        int damage = Math.max(1, player.attack - enemy.defense); // Minimum 1 damage

                        // Critical Hit System: 15% chance to deal double damage
                        int critRoll = random.nextInt(100); // random number from 0 to 99
                        boolean isCritical = critRoll < 15;

                        if (isCritical) {
                            damage = damage * 2;
                            System.out.println("*** CRITICAL HIT! ***");
                        }

                        enemy.hp -= damage;
                        System.out.println("You struck the " + enemy.name + " with your lightsaber for " + damage + " damage!");
                        validAction = true;
                    }
                   
                    else if (action == 2) { // Defend
                        player.defending = true;
                        System.out.println("You use the Force to deflect incoming attacks. Damage halved this round.");
                        validAction = true;
                    } 
                    else if (action == 3) { // Heal
                        if (player.healUses > 0) {
                            if (player.hp < player.maxHp) {
                                int healAmount = 30;
                                player.hp += healAmount;
                                if (player.hp > player.maxHp) {
                                    player.hp = player.maxHp;
                                }
                                player.healUses--;
                                System.out.println("You used a Bacta Tank! Your HP is now " + player.hp + "/" + player.maxHp + ".");
                                validAction = true;
                            } else {
                                System.out.println("Your HP is already full! Choose something else.");
                            }
                        } else {
                            System.out.println("You have no heals left! Choose something else.");
                        }
                    }
                }

                if (enemy.hp <= 0) {
                    System.out.println("\nYou defeated the " + enemy.name + "!");
                    battleOver = true;
                    if (isBoss) {
                        System.out.println("\n*** CONGRATULATIONS! YOU HAVE DEFEATED THE FINAL BOSS AND SAVED THE GALAXY! ***");
                        playing = false; // End game after defeating the boss
                        bossDefeated = true;
                    }
                    break;
                }

                // Enemy Turn
                System.out.println("\nEnemy's Turn:");
                int enemyDamage = enemy.attack - player.defense;
                
                if (player.defending) {
                    enemyDamage = enemyDamage / 2; // Damage is halved if defending
                }
                
                enemyDamage = Math.max(1, enemyDamage); // Minimum 1 damage
                player.hp -= enemyDamage;
                System.out.println("The " + enemy.name + " attacked you for " + enemyDamage + " damage!");
                
                round++;
            }

            // After Battle
            if (player.isAlive()) {
                wins++;
                if (!playing) {
                    break; // Boss was defeated and game is over
                }
                player.levelUp();
                System.out.println("\n*** LEVEL UP! ***");
                System.out.println("You are now Level " + player.level + "! Your stats have increased, and you are fully healed.");
                
                System.out.print("\nDo you want to face another enemy? (y/n): ");
                String answer = scanner.nextLine().trim().toLowerCase();
                if (!answer.startsWith("y")) {
                    playing = false;
                }
            }
        }

        // Game Over
        System.out.println("\n=== GAME OVER ===");
        if (!player.isAlive()) {
            System.out.println("You were defeated in battle.");
        } else if (bossDefeated) {
            System.out.println("You have brought peace to the galaxy!");
        } else {
            System.out.println("You decided to rest and end your adventure.");
        }
        System.out.println("Final Level: " + player.level);
        System.out.println("Enemies Defeated: " + wins);
        
        scanner.close();
    }
}
