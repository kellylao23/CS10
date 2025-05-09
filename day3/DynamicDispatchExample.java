public class DynamicDispatchExample {
    public static void main(String[] args) {
        Person alice = new Person("Alice", "f00xzy");
        Instructor bob = new Instructor("Bob","f00abc");
        System.out.println(alice);
        bob.setName("Bobby");
        System.out.println(bob);
    }
}
