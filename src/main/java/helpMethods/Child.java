package helpMethods;

public class Child {
    int age;

    public Child(int age) {
        if (age >= 0 && age <= 17) {
            this.age = age;
        } else {
            System.out.println("child age should be between 0 and 17");
        }
    }

    public int getAge() {
        return age;
    }
}
