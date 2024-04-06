package application.jf;

import java.io.Serializable;
import java.time.LocalDate;

public class UserData implements Serializable {

    private String username;
    private String password;
    private String name;
    private double height;
    private int age;
    private String gender;
    private double weight;
    private double goalWeight;
    private int workoutsPerWeek;
    private double AMRval;
    LocalDate lastLogin;
    double currentCals;
    double currentProtein;

    public UserData(String username, String password, String name, double height, int age,
            String gender, double weight, double goalWeight, int workoutsPerWeek, double AMRval,
            LocalDate lastLogin, double cals, double protein) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.goalWeight = goalWeight;
        this.workoutsPerWeek = workoutsPerWeek;
        this.AMRval = AMRval;
        this.lastLogin = lastLogin;
        this.currentCals = cals;
        this.currentProtein = protein;
    }

    protected String getUsername() {
        return username;
    }
    protected String getPassword() {
        return password;
    }
    protected String getName() {
        return name;
    }
    protected double getHeight() {
        return height;
    }
    protected int getAge() {
        return age;
    }
    protected String getGender() {
        return gender;
    }
    protected double getWeight() {
        return weight;
    }
    protected double getGoalWeight() {
        return goalWeight;
    }
    protected int getWorkoutsPerWeek() {
        return workoutsPerWeek;
    }
    protected double getAMRval() {
        return AMRval;
    }
    protected double getCurrentCals() {
        return currentCals;
    }
    protected double getCurrentProtein() {
        return currentProtein;
    }

    protected void setLastLogin(LocalDate date) {
        this.lastLogin = date;
    }
    protected void setCurrentCals(double calories) {
        this.currentCals = calories;
    }
    protected void setCurrentProtein(double protein) {
        this.currentProtein = protein;
    }
    protected void setName(String name) {
        this.name = name;
    }

    protected void setHeight(double height) {
        this.height = height;
    }

    protected void setAge(int age) {
        this.age = age;
    }

    protected void setGender(String gender) {
        this.gender = gender;
    }

    protected void setWeight(double weight) {
        this.weight = weight;
    }

    protected void setGoalWeight(double goalWeight) {
        this.goalWeight = goalWeight;
    }

    protected void setWorkoutsPerWeek(int workoutsPerWeek) {
        this.workoutsPerWeek = workoutsPerWeek;
    }

    protected void setAMRval(double AMRval) {
        this.AMRval = AMRval;
    }
}
