package io.github.astro;

import io.github.astro.mantis.spring.boot.EnableMantis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@EnableMantis(scanBasePackages = {"io.github.astro"})
//@EnableDiscoveryClient
public class Provider {

    private static final Logger logger = LoggerFactory.getLogger(Provider.class);

    public static void main(String[] args) throws IOException {

        SpringApplication.run(Provider.class, args);
//        DefaultExporter<MyExport> exporter = new DefaultExporter<>(new MyExport());
//        exporter.setApplicationName("rpc-provider-normal");
//        exporter.export();

        System.in.read();
    }

}