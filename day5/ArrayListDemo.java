import java.util.ArrayList;
import java.util.List;

/**
 * Sample program to demonstrate List functionality
 *
 * @author Tim Pierson, Dartmouth CS10, Fall 2024
 */
public class ArrayListDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(1,3);
        System.out.println(list);
        Integer b = list.get(1);
        System.out.println(b);
        list.remove(1);
        System.out.println(list);
        list.set(1,4);
        System.out.println(list);
        System.out.println(list.size());
    }
}
