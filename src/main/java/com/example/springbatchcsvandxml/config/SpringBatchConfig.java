package com.example.springbatchcsvandxml.config;

import com.example.springbatchcsvandxml.entity.Laptop;
import com.example.springbatchcsvandxml.entity.MyCsvISO;
import com.example.springbatchcsvandxml.entity.MyISO;
import com.example.springbatchcsvandxml.entity.Owner;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
//@EnableBatchProcessing
public class SpringBatchConfig {

    private JobRepository jobRepository;

    @Bean
    public ItemReader<MyISO> myISOItemReader()  {
        return new MyISOItemReader();
    }

    @Bean
    public MyISOItemProcessor myISOItemProcessor(){
        return new MyISOItemProcessor();
    }

    @Bean
    @Scope("prototype")
    public Laptop laptop(){
        return new Laptop();
    }

    @Bean
    @Scope("prototype")
    public Owner owner(){
        return new Owner();
    }

    @Bean
    public FlatFileItemWriter<MyISO> myISOItemWriter() {


        FlatFileItemWriter<MyISO> myISOFlatFileItemWriter = new FlatFileItemWriter<>();
        myISOFlatFileItemWriter.setResource(new FileSystemResource("myiso.csv"));

        DelimitedLineAggregator<MyISO> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<MyISO> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();

        beanWrapperFieldExtractor.setNames(new String[]{"myISOid","myISOfirstName","laptop.laptopId","laptop.laptopBrandName","laptop.laptopOwner.ownerId","laptop.laptopOwner.ownerName","laptop.laptopOwner.ownerTeam"});

        lineAggregator.setFieldExtractor(beanWrapperFieldExtractor);

        myISOFlatFileItemWriter.setLineAggregator(lineAggregator);

        return myISOFlatFileItemWriter;
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("first step",jobRepository)
                .<MyISO,MyISO>chunk(100,platformTransactionManager)
                .reader(myISOItemReader())
                .processor(myISOItemProcessor())
                .writer(myISOItemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job runJob(JobRepository jobRepository,PlatformTransactionManager platformTransactionManager){
        return new JobBuilder("myISOtoCSV",jobRepository)
                .flow(step1(jobRepository, platformTransactionManager)).end().build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor asyncTaskExecutor=new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

    @Bean
    public StaxEventItemWriter<MyISO> itemWriter(){
        return new StaxEventItemWriterBuilder<MyISO>()
                .name("myISOXML")
                .marshaller(isoXMLMarshaller())
                .resource(new FileSystemResource("myiso.xml"))
                .rootTagName("myISO")
                .overwriteOutput(true)
                .build();
    }

    @Bean
    public XStreamMarshaller isoXMLMarshaller(){
        XStreamMarshaller isoXMLMarshaller = new XStreamMarshaller();

        Map<String,Class> aliases = new HashMap<>();
        aliases.put("myISO", MyISO.class);
        aliases.put("Laptop", Laptop.class);
        aliases.put("Owner", Owner.class);

        isoXMLMarshaller.setAliases(aliases);

        return isoXMLMarshaller;
    }

}
