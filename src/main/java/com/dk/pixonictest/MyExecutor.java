/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dk.pixonictest;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author dkuz
 */
public class MyExecutor {

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(16);
    private final ConcurrentHashMap<Long, Long> shift = new ConcurrentHashMap<>();

    public void submit(final Date dt, Callable c) {
        final long now = System.currentTimeMillis();
        final long startTime = dt.getTime();
        long delay = startTime > now ? startTime - now : 0;
        long shiftMs = shift.merge(startTime, 0L, (oldV, newV) -> oldV + 1L);
        System.out.println("Added to queue ==> Date: " + dt + " Callable: " + c.toString());
        executor.schedule(c, delay + shiftMs, TimeUnit.MILLISECONDS);
    }

    public void stop() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(3, TimeUnit.MINUTES);
    }

}
