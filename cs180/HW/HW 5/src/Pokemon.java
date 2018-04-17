import java.util.*;
import java.lang.*;

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
            this.type = type;
        }
        if (healthPower >= 0) {
            this.healthPower = healthPower;
        }
        if (baseAttackPower > 0) {
            this.baseAttackPower = baseAttackPower;
        }

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
            return true;
        } else return false;
    }

    public boolean setHealthPower(int healthPower) {
        if (healthPower >= 0) {
            return true;
        } else return false;
    }

    public boolean setBaseAttackPower(double baseAttackPower) {
        if (baseAttackPower > 0) {
            return true;
        } else return false;
    }

    //Object methods
    public String toString() {
        String string1 = "Name: " + getName() + "\n" + "ID: " + getId() + "\n" + "Type: " + getType() + "\n" +
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
        boolean noHealthPowerRemaining;
        noHealthPowerRemaining = false;
        while (!noHealthPowerRemaining) {
            if (((p1.getHealthPower()) - ((p2.getBaseAttackPower()) * getAttackMultiplier(p1, p2))) <= 0) {
                return 2;
            } else if (((p2.getHealthPower()) - ((p1.getBaseAttackPower()) * getAttackMultiplier(p2, p1))) <= 0) {
                return 1;
            }
        }
        return 0;
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

        boolean noHealthPowerRemaining;
        noHealthPowerRemaining = false;
        while (!noHealthPowerRemaining) {
            if (((temporaryHealthPower) - ((temporaryGetBaseAttack2) * getAttackMultiplier(p1, p2))) <= 0) {
                return 2;
            } else if (((temporaryHealthPower2) - ((temporaryGetBaseAttack) * getAttackMultiplier(p2, p1))) <= 0) {
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the first Pokemon's name:");
        String namePokemon1 = MyUtils.formatStr(scanner.next());
        System.out.println(namePokemon1);
        System.out.println("Enter the first Pokemon's type:");
        String typePokemon1 = MyUtils.formatStr(scanner.next());
        System.out.println(typePokemon1);
        boolean hpPokemon1;
        do {
            System.out.println("Enter the first Pokemon's Health Power (HP):");
            hpPokemon1 = MyUtils.isNumeric(scanner.next());
            if (hpPokemon1 == false) {
                System.out.println("Invalid Health Power (HP) entered. Please re-enter.");
            } else if (hpPokemon1 == true) {
                break;
            }
        } while (hpPokemon1 != true);
        boolean attackPowerPokemon1;
        do {
            System.out.println("Enter the first Pokemon's base Attacks Power:");
            attackPowerPokemon1 = MyUtils.isNumeric(scanner.next());
            if (attackPowerPokemon1 == false) {
                System.out.println("Invalid Attack's Power entered. Please re-enter.");
            } else if (attackPowerPokemon1 == true) {
                break;
            }
        } while (attackPowerPokemon1 != true);
        Pokemon pokemon1 = new Pokemon(namePokemon1,typePokemon1,hpPokemon1,(double)attackPowerPokemon1)



    }


}
