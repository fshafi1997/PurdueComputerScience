import java.util.*;
import java.lang.*;

import static org.junit.Assert.assertEquals;

public class Pokemon {
    private String name;
    private int id;
    private String type = "Fire";
    private int healthPower = 0;
    private double baseAttackPower = 1;

    private static int NUM_POKEMONS = 0;

    //Pokemon constructor
    public Pokemon(String name, String type, int healthPower, double baseAttackPower) {
        if (type.equalsIgnoreCase("fire") || type.equalsIgnoreCase("water") || type.equalsIgnoreCase("grass") || type.equalsIgnoreCase("electric")) {
            this.type = MyUtils.formatStr(type);
        }
        if (healthPower >= 0) {
            this.healthPower = healthPower;
        }
        if (baseAttackPower > 0) {
            this.baseAttackPower = baseAttackPower;
        }
        this.name = name;
        this.id = NUM_POKEMONS;
        NUM_POKEMONS++;

    }

    //Accessors
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getHealthPower() {
        return healthPower;
    }

    public double getBaseAttackPower() {
        return baseAttackPower;
    }

    //mutators
    public boolean setType(String type) {
        if (type.equalsIgnoreCase("fire") || type.equalsIgnoreCase("water") || type.equalsIgnoreCase("grass") || type.equalsIgnoreCase("electric")) {
            this.type = type;
            return true;
        } else return false;
    }

    public boolean setHealthPower(int healthPower) {
        if (healthPower >= 0) {
            this.healthPower = healthPower;
            return true;
        } else {
            this.healthPower = 0;
            return false;
        }
    }

    public boolean setBaseAttackPower(double baseAttackPower) {
        if (baseAttackPower > 0) {
            this.baseAttackPower = baseAttackPower;
            return true;
        } else return false;
    }

    //Object methods
    public String toString() {
        String string1 = "\n" + "Name: " + getName() + "\n" + "ID: " + getId() + "\n" + "Type: " + getType() + "\n" +
                "Health power: " + getHealthPower() + "\n" + "Base attack power: " + getBaseAttackPower();
        return string1;
    }

    public boolean isDead() {
        if (getHealthPower() > 0) {
            return false;
        } else return true;
    }

    public void boostHealthPower(int healthPower) {
        int newPokemonIncreasedHealth = getHealthPower() + healthPower;
        this.healthPower = newPokemonIncreasedHealth;
    }

    public void reduceHealthPower(int healthPower) {
        int newPokemonReducedHealth = getHealthPower() - healthPower;
        if (newPokemonReducedHealth < 0) {
            this.healthPower = 0;
        } else if (newPokemonReducedHealth >= 0) {
            this.healthPower = newPokemonReducedHealth;
        }
    }

    //Battle methods
    public static double getAttackMultiplier(Pokemon attacker, Pokemon defender) {
        if ((attacker.getType().equalsIgnoreCase("grass")) && (defender.getType().equalsIgnoreCase("grass"))) {
            return 0.5;
        }
        if ((attacker.getType().equalsIgnoreCase("grass")) && (defender.getType().equalsIgnoreCase("electric"))) {
            return 1.0;
        }
        if ((attacker.getType().equalsIgnoreCase("grass")) && (defender.getType().equalsIgnoreCase("water"))) {
            return 2;
        }
        if ((attacker.getType().equalsIgnoreCase("grass")) && (defender.getType().equalsIgnoreCase("fire"))) {
            return 0.5;
        }
        if ((attacker.getType().equalsIgnoreCase("electric")) && (defender.getType().equalsIgnoreCase("electric"))) {
            return 0.5;
        }
        if ((attacker.getType().equalsIgnoreCase("electric")) && (defender.getType().equalsIgnoreCase("water"))) {
            return 2;
        }
        if ((attacker.getType().equalsIgnoreCase("electric")) && (defender.getType().equalsIgnoreCase("fire"))) {
            return 1;
        }
        if ((attacker.getType().equalsIgnoreCase("electric")) && (defender.getType().equalsIgnoreCase("grass"))) {
            return 0.5;
        }
        if ((attacker.getType().equalsIgnoreCase("water")) && (defender.getType().equalsIgnoreCase("water"))) {
            return 0.5;
        }
        if ((attacker.getType().equalsIgnoreCase("water")) && (defender.getType().equalsIgnoreCase("electric"))) {
            return 1;
        }
        if ((attacker.getType().equalsIgnoreCase("water")) && (defender.getType().equalsIgnoreCase("fire"))) {
            return 2;
        }
        if ((attacker.getType().equalsIgnoreCase("water")) && (defender.getType().equalsIgnoreCase("grass"))) {
            return 0.5;
        }
        if ((attacker.getType().equalsIgnoreCase("fire")) && (defender.getType().equalsIgnoreCase("fire"))) {
            return 0.5;
        }
        if ((attacker.getType().equalsIgnoreCase("fire")) && (defender.getType().equalsIgnoreCase("electric"))) {
            return 1;
        }
        if ((attacker.getType().equalsIgnoreCase("fire")) && (defender.getType().equalsIgnoreCase("water"))) {
            return 0.5;
        }
        if ((attacker.getType().equalsIgnoreCase("fire")) && (defender.getType().equalsIgnoreCase("grass"))) {
            return 2;
        }
        return -1;
    }

    public static int battle(Pokemon p1, Pokemon p2) {
        int newHealthp1;
        int newHealthp2;
        System.out.println("function");
        while (true) {
            newHealthp1 = (int) ((p1.getHealthPower()) - ((p2.getBaseAttackPower()) * getAttackMultiplier(p1, p2)));
            p1.setHealthPower(newHealthp1);
            newHealthp2 = (int) ((p2.getHealthPower()) - ((p1.getBaseAttackPower()) * getAttackMultiplier(p2, p1)));
            p2.setHealthPower(newHealthp2);
            System.out.println(p1.getHealthPower());
            System.out.println(p2.getHealthPower());
            if (p1.getHealthPower() <= 0) {
                break;
            } else if (p2.getHealthPower() <= 0) {
                break;
            }
//            }
        }
        if (p1.getHealthPower() <= 0) {
            return 2;
        } else return 1;
    }

    public static int battleOracle(Pokemon p1, Pokemon p2) {
        String temporaryName = p1.getName();
        String temporaryType = p1.getType();
        int temporaryHealthPower = p1.getHealthPower();
        double temporaryGetBaseAttack = p1.getBaseAttackPower();
        String temporaryName2 = p2.getName();
        String temporaryType2 = p2.getType();
        int temporaryHealthPower2 = p2.getHealthPower();
        double temporaryGetBaseAttack2 = p2.getBaseAttackPower();

        Pokemon testP1 = new Pokemon(temporaryName, temporaryType, temporaryHealthPower, temporaryGetBaseAttack);
        Pokemon testP2 = new Pokemon(temporaryName2, temporaryType2, temporaryHealthPower2, temporaryGetBaseAttack2);
        int returnOfBattle = battle(testP1, testP2);
        return returnOfBattle;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the first Pokemon's name:");
        String namePokemon1 = MyUtils.formatStr(scanner.next());
        System.out.println(namePokemon1);
        System.out.println("Enter the first Pokemon's type:");
        String typePokemon1 = MyUtils.formatStr(scanner.next());
        System.out.println(typePokemon1);

        //Check all this
        boolean hpPokemon1;
        String hpOfPokemon1;
        do {
            System.out.println("Enter the first Pokemon's Health Power (HP):");
            hpOfPokemon1 = scanner.next();
            hpPokemon1 = MyUtils.isNumeric(hpOfPokemon1);
            if (hpPokemon1 == false) {
                System.out.println("Invalid Health Power (HP) entered. Please re-enter.");
            } else if (hpPokemon1 == true) {
                break;
            }
        } while (hpPokemon1 != true);
        boolean attackPowerPokemon1;
        String attackPowerOfPokemon1;
        do {
            System.out.println("Enter the first Pokemon's base Attacks Power:");
            attackPowerOfPokemon1 = scanner.next();
            attackPowerPokemon1 = MyUtils.isNumeric(attackPowerOfPokemon1);
            if (attackPowerPokemon1 == false) {
                System.out.println("Invalid Attack's Power entered. Please re-enter.");
            } else if (attackPowerPokemon1 == true) {
                break;
            }
        } while (attackPowerPokemon1 != true);
        Pokemon pokemon1 = new Pokemon(namePokemon1, typePokemon1, Integer.parseInt(hpOfPokemon1), Double.parseDouble(attackPowerOfPokemon1));
        //Pokemon2

        System.out.println("Enter the second Pokemon's name:");
        String namePokemon2 = MyUtils.formatStr(scanner.next());
        System.out.println(namePokemon2);
        System.out.println("Enter the second Pokemon's type:");
        String typePokemon2 = MyUtils.formatStr(scanner.next());
        System.out.println(typePokemon2);

        boolean hpPokemon2;
        String hpOfPokemon2;
        do {
            System.out.println("Enter the second Pokemon's Health Power (HP):");
            hpOfPokemon2 = scanner.next();
            hpPokemon2 = MyUtils.isNumeric(hpOfPokemon2);
            if (hpPokemon2 == false) {
                System.out.println("Invalid Health Power (HP) entered. Please re-enter.");
            } else if (hpPokemon2 == true) {
                break;
            }
        } while (hpPokemon2 != true);
        boolean attackPowerPokemon2;
        String attackPowerOfPokemon2;
        do {
            System.out.println("Enter the second Pokemon's base Attacks Power:");
            attackPowerOfPokemon2 = scanner.next();
            attackPowerPokemon2 = MyUtils.isNumeric(attackPowerOfPokemon2);
            if (attackPowerPokemon2 == false) {
                System.out.println("Invalid Attack's Power entered. Please re-enter.");
            } else if (attackPowerPokemon2 == true) {
                break;
            }
        } while (attackPowerPokemon2 != true);

        Pokemon pokemon2 = new Pokemon(namePokemon2, typePokemon2, Integer.parseInt(hpOfPokemon2), Double.parseDouble(attackPowerOfPokemon2));

        int battleResults = battle(pokemon1, pokemon2);
        System.out.println("Reducing by " + pokemon2.getBaseAttackPower());
        System.out.println(pokemon1.getHealthPower());
        System.out.println("Reducing by " + pokemon1.getBaseAttackPower());
        System.out.println(pokemon2.getHealthPower());

        System.out.println("First Pokemon's Stats after the battle: " + pokemon1.toString());
        System.out.println("--------------- ");
        System.out.print("" + "\n");
        System.out.println("Second Pokemon's Stats after the battle: " + pokemon2.toString());
        System.out.println("==================== ");
        System.out.print("" + "\n");

        if (battleResults == 2) {
            System.out.println("The winner of the battle is " + pokemon2.getName());
        } else if (battleResults == 1) {
            System.out.println("The winner of the battle is " + pokemon1.getName());
        }
    }
}


