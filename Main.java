import javax.swing.*;

abstract class Animal {
    String name;
    Animal(String name) {
        this.name = name;
    }
    abstract void makeSound();
}
interface Flyable {
    void fly();
}
class Bird extends Animal implements Flyable {
    Bird(String name) {
        super(name);
    }
    @Override
    void makeSound() {
        System.out.println(name + " chirps.");
    }
    @Override
    public void fly() {
        System.out.println(name + " is flying.");
    }
}
public class Main {
    public static void main (String[] args) {
        /*JFrame frame = new JFrame();
        frame.setSize(500,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);*/

        Bird bird = new Bird("Parrot");
        bird.makeSound();
        bird.fly();
    }
}
