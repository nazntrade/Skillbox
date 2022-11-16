package com.skillbox.skillboxkotlin;

import android.text.SpannableString;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        List<? super String> a = new ArrayList<>();
        a.add("asd");
        Object b = a.get(0);
        System.out.println(b);
        a.set(0, "");
        a.remove(0);
    }

    void test(Source<String> in) {
        Source<? extends Object> a = in;
    }

}

interface Source<T> {
    T next();
}
