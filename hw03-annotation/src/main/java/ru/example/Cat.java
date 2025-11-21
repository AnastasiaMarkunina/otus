package ru.example;

public class Cat {

    private String name;
    private int age;
    private String gender;
    private String breed;

    public Cat(String name, int age, String gender, String breed) {
        this.name = validateName(name);
        this.age = validateAge(age);
        this.gender = validateGender(gender);
        this.breed = breed;
    }

    public Cat(String name, int age, String gender) {
        this(name, age, gender, "Unknown");
    }

    public Cat(String name, String gender) {
        this(name, 0, gender, "Unknown");
    }

    public void setAge(int age) {
        this.age = validateAge(age);
    }

    public void setGender(String gender) {
        this.gender = validateGender(gender);
    }

    private void getMay() {
        System.out.println("Mayy!");
    }

    private String validateName(String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return name;
    }

    private int validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        return age;
    }

    private String validateGender(String gender) {
        if (!"male".equalsIgnoreCase(gender) && !"female".equalsIgnoreCase(gender)) {
            throw new IllegalArgumentException("Gender must be 'male' или 'female'");
        }
        return gender;
    }

    public String getInfo() {
        return String.format("Cat: {Name: '%s', Age: '%d', Gender: '%s', Breed: '%s'}", name, age, gender, breed);
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getBreed() {
        return breed;
    }
}
