package com.xgen.interview;

import com.xgen.interview.Pricer;
import com.xgen.interview.ShoppingCart;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;


public class ShoppingCartTest {

    @Test
    public void canAddAnItem() {
        ShoppingCart sc = new ShoppingCart(new Pricer());
        sc.addItem("apple", 1);

        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        sc.printReceipt();
        assertEquals(String.format("apple - 1 - €1.00%n--------------------%nTotal - €1.00%n"), myOut.toString());
    }

    @Test
    public void canAddMoreThanOneItem() {
        ShoppingCart sc = new ShoppingCart(new Pricer());

        sc.addItem("apple", 2);

        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        sc.printReceipt();
        assertEquals(String.format("apple - 2 - €2.00%n--------------------%nTotal - €2.00%n"), myOut.toString());
    }

    @Test
    public void canAddDifferentItems() {
        ShoppingCart sc = new ShoppingCart(new Pricer());

        sc.addItem("apple", 2);
        sc.addItem("banana", 1);

        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        sc.printReceipt();

        String result = myOut.toString();

//        if (result.startsWith("apple")) {

// I commented those lines of code because we do not need to worry about the order
// in which the items are printed anymore

            assertEquals(String.format("apple - 2 - €2.00%nbanana - 1 - €2.00%n--------------------%nTotal - €4.00%n"), result);
//        } else {
//            assertEquals(String.format("banana - 1 - €2.00%napple - 2 - €2.00%n--------------------%nTotal - €4.00%n"), result);
//        }
    }

    @Test
    public void doesntExplodeOnMysteryItem() {
        ShoppingCart sc = new ShoppingCart(new Pricer());

        sc.addItem("crisps", 2);

        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        sc.printReceipt();
        assertEquals(String.format("crisps - 2 - €0.00%n--------------------%nTotal - €0.00%n"), myOut.toString());
    }

    //new test for printing priceFirst on each line with one item
    @Test
    public void printPriceFirstWithOneItem() {
        int branch = 1;
        ShoppingCart sc = new ShoppingCart(new Pricer(), branch);

        sc.addItem("banana", 1);

        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        sc.printReceipt();

        String result = myOut.toString();

        assertEquals(String.format("€2.00 - banana - 1%n--------------------%nTotal - €2.00%n"), result);
    }

    //new test for printing priceFirst on each line with multiple items
    @Test
    public void printPriceFirstWithMultipleItems() {
        int branch = 1;
        ShoppingCart sc = new ShoppingCart(new Pricer(), branch);

        sc.addItem("banana", 2);
        sc.addItem("apple", 2);
        sc.addItem("crisps", 2);
        sc.addItem("chocolate", 3);

        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        sc.printReceipt();

        String result = myOut.toString();

        assertEquals(String.format(
                "€4.00 - banana - 2%n" +
               "€2.00 - apple - 2%n" +
               "€0.00 - crisps - 2%n" +
               "€0.00 - chocolate - 3%n" +
                        "--------------------%n" +
                "Total - €6.00%n"), result);
    }
}


