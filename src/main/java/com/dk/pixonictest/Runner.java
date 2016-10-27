/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dk.pixonictest;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 *
 * @author dkuz
 */
public class Runner {

    private static Callable generateTest(String name) {
        return (Callable) () -> {
            System.out.println("Run ==> [ " + name + " ] Actual Date: " + (new Date()));
            return 0;
        };
    }

    public static void main(final String[] args) throws InterruptedException {
        System.out.println("Test for Pixonic");
        MyExecutor ex = new MyExecutor();
        Date dt = new Date(); // now
        for (int i = 0; i < 3; i++) {
            ex.submit(dt, generateTest("Test " + i));
        }
        dt.setTime(dt.getTime() + 10000L); // now + 10 seconds
        ex.submit(dt, generateTest("After 10 sec."));
        ex.stop();
    }

}
