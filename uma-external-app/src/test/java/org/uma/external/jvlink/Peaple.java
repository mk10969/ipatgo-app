package org.uma.external.jvlink;

public class Peaple {

    private final String name;
    private final Integer age;

    public Peaple(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    public Integer ageDifference(final Peaple peaple) {
        return this.age - peaple.age;
    }

    @Override
    public String toString() {
        return String.format("%s - %d", name, age);
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
