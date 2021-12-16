package uz.pdp.lesson_5;

import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {

        Set<String> set = new HashSet<>();
        set.add("salom");
        set.add("qalay");
        set.add("nima");

        System.out.println(set.contains("salomm"));

    }
}
