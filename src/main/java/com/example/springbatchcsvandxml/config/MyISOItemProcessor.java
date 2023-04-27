package com.example.springbatchcsvandxml.config;

import com.example.springbatchcsvandxml.entity.MyCsvISO;
import com.example.springbatchcsvandxml.entity.MyISO;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;

public class MyISOItemProcessor implements ItemProcessor<MyISO, MyISO> {
    @Override
    public MyISO process(MyISO item) {

//        JAXBContext jaxbContext = JAXBContext.newInstance(MyXmlISO.class);
//        Marshaller marshaller = jaxbContext.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
//        marshaller.marshal(item,new File("laptop.xml"));

        return item;

    }
}
