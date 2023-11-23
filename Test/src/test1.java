class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
   
    void sound() {
        System.out.println("Dog barks");
    }

    void wagTail() {
        System.out.println("Dog wags its tail");
    }
}

public class test1 {
    public static void main(String[] args) {
    	Animal ani =new Animal();
    	ani.sound();
    	Animal objani=new Dog();
    	objani.sound();
    	Dog dog = new Dog();
    	dog.sound();
    	
    	
    
    }
}
