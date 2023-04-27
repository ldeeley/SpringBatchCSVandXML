package com.example.springbatchcsvandxml.config;

import com.example.springbatchcsvandxml.entity.Laptop;
import com.example.springbatchcsvandxml.entity.MyISO;
import com.example.springbatchcsvandxml.entity.Owner;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.concurrent.atomic.AtomicInteger;

public class MyISOItemReader implements ItemReader<MyISO> {

    private AtomicInteger atomicIntegerCounter = new AtomicInteger();

    public MyISOItemReader() {
        this.atomicIntegerCounter.set(0);
    }

    @Override
    public MyISO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        int idVal;

        if((idVal = atomicIntegerCounter.getAndIncrement()) < 250){
            MyISO myNewISO = new MyISO((Integer) idVal,"Lester",new Laptop(1,"AppleMac",new Owner(1,"Zeeley","Newcastle")));
            return myNewISO;
        } else return null;

    }

}
