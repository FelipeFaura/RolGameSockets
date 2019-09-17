package com.example.animarol;

import android.util.Log;

import java.io.Serializable;

public class PlayerEntity implements Serializable {
    enum Classes{
        Warrior,
        Mage,
        Assassin
    }
    Classes playerClass;
    String name;
    int level;
    int health;
    int strength;
    int agility;
    int constitution;
    int dexterity;
    int weapon;
    int armor;
    int attack;
    int defense;

    public PlayerEntity(Classes playerClass,String name){
        this.playerClass = playerClass;
        this.name = name;
        this.level = 1;

        switch (this.playerClass){
            case Warrior:
                this.health = 200;
                this.strength = 5;
                this.agility = 2;
                this.constitution = 5;
                this.dexterity = 2;
                this.weapon = 10;
                this.armor = 10;
                this.attack = 5;
                this.defense = 5;
                break;
            case Mage:
                this.health = 80;
                this.strength = 1;
                this.agility = 3;
                this.constitution = 3;
                this.dexterity = 5;
                this.weapon = 2;
                this.armor = 2;
                this.attack = 5;
                this.defense = 5;
                break;
            case Assassin:
                this.health = 120;
                this.strength = 3;
                this.agility = 5;
                this.constitution = 5;
                this.dexterity = 3;
                this.weapon = 5;
                this.armor = 5;
                this.attack = 5;
                this.defense = 5;
                break;
        }
    }
    public void ShowPlayerData(){
        Log.d("DATACLASSPLAYER", this.playerClass.toString());
        Log.d("DATACLASSPLAYER", this.name);
        Log.d("DATACLASSPLAYER", Integer.toString(this.level));
        Log.d("DATACLASSPLAYER", Integer.toString(this.health));
        Log.d("DATACLASSPLAYER", Integer.toString(this.strength));
        Log.d("DATACLASSPLAYER", Integer.toString(this.agility));
        Log.d("DATACLASSPLAYER", Integer.toString(this.constitution));
        Log.d("DATACLASSPLAYER", Integer.toString(this.dexterity));
        Log.d("DATACLASSPLAYER", Integer.toString(this.weapon));
        Log.d("DATACLASSPLAYER", Integer.toString(this.armor));
        Log.d("DATACLASSPLAYER", Integer.toString(this.attack));
        Log.d("DATACLASSPLAYER", Integer.toString(this.defense));
    }
}
