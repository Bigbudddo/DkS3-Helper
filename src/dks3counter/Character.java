/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dks3counter;

/**
 *
 * @author stuby
 */
public class Character {
    
    private int id;
    private String name;
    private int deathCounter;
    
    public int getId() { return id; }
    public void setId(int value) { id = value; }
    
    public String getName() { return name; }
    public void setName(String value) { name = value; }
    
    public int getDeathCounter() { return deathCounter; }
    public void setDeathCounter(int value) { deathCounter = value; }
    
    public Character() {
        this.name = null;
        this.deathCounter = 0;
    }
    
    public Character(String name, int deathCount) {
        this.name = name;
        this.deathCounter = deathCount;
    }
    
    public Character(int id, String name, int deathCount) {
        this.id = id;
        this.name = name;
        this.deathCounter = deathCount;
    }
    
    public void IncrementDeathCounter() {
        deathCounter++;
        UpdateCharacter();
    }
    
    public void DecrementDeathCounter() {
        deathCounter--;
        if (deathCounter < 0) { deathCounter = 0; }
        UpdateCharacter();
    }
    
    @Override
    public String toString() {
        return "(ID: " + getId() + ", Name: " + getName() + ", " +
                "Death Counter: " + getDeathCounter() + ")";
    }
    
    public String deathCounterToString() {
        String timesText = (deathCounter == 1) ? " time" : " times";
        String retVal = String.valueOf(getDeathCounter()) + timesText;
        return retVal;
    }
    
    private void UpdateCharacter() {
        HibernateCharacterDAO dao = new HibernateCharacterDAO();
        dao.UpdateCharacter(this);
    }
}
