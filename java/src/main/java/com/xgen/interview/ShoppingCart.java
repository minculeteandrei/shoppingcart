package com.xgen.interview;

import java.lang.reflect.Array;
import java.util.*;


/**
 * This is the current implementation of ShoppingCart.
 * Please write a replacement
 */
public class ShoppingCart implements IShoppingCart {
    HashMap<String, Integer> contents = new HashMap<>();

    //for second task, in order to keep the order in which the items were scanned
    //this addition does not increase the asymptotic complexity of the program.
    LinkedHashSet<String> order = new LinkedHashSet<>();
    Pricer pricer;

    //for the third task, I added an extra attribute to the ShoppingCart class
    //which represents the branch in which the shoppingCart is registered.
    //if the branch is 1, then the style of printing is price-first
    //this allows for further easy modifications to the printing style
    int branch;

    public ShoppingCart(Pricer pricer) {
        this.pricer = pricer;
        this.branch = 0;
    }

    public ShoppingCart(Pricer pricer, int branch) {
        this.pricer = pricer;
        this.branch = branch;
    }

    public void addItem(String itemType, int number) {
        if (!contents.containsKey(itemType)) {
            contents.put(itemType, number);
        } else {
            int existing = contents.get(itemType);
            contents.put(itemType, existing + number);
        }


        order.add(itemType);
    }


    public void printBasedOnBranch(String product, int number, String priceString){
        if(this.branch == 1){
            System.out.println(priceString + " - " + product + " - " + number);
        } else
            System.out.println(product + " - " + number + " - " + priceString);
    }

    public void printReceipt() {

        //Second task - the keys[] array is the array of the linkedHashSet
        //So we print the elements on the receipt in the desired order
        Object[] keys = order.toArray();
        float sum = (float) 0;

        for (int i = 0; i < Array.getLength(keys); i++) {
            // we still obtain the no. of products through the hashMap.
            int price = pricer.getPrice((String) keys[i]) * contents.get(keys[i]);
            float priceFloat = (float) price / 100;
            sum += priceFloat;
            String priceString = String.format("€%.2f", priceFloat);
            printBasedOnBranch((String) keys[i], contents.get(keys[i]), priceString);
        }
        String sumString = String.format("€%.2f", sum);

        System.out.println("--------------------");
        System.out.println("Total - " + sumString);

    }
}
