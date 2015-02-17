package com.example.helloworld;

import co.paralleluniverse.fibers.dropwizard.FiberApplication;
import co.paralleluniverse.fibers.dropwizard.FiberDBIFactory;
import co.paralleluniverse.fibers.dropwizard.FiberHttpClientBuilder;
import com.example.helloworld.resources.HelloWorldResource;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.util.Arrays;
import org.apache.http.client.HttpClient;
import org.skife.jdbi.v2.IDBI;

public class HelloWorldApplication extends FiberApplication<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/", "/api"));
    }

    @Override
    public void fiberRun(HelloWorldConfiguration configuration,
            final Environment environment) throws ClassNotFoundException {



        final HttpClient fhc = new FiberHttpClientBuilder(environment).
                using(configuration.getHttpClientConfiguration()).
                build("FiberHttpClient");

        final IDBI jdbi = new FiberDBIFactory().build(environment, configuration.getDatabase(), "postgresql");
        final MyDAO dao = jdbi.onDemand(MyDAO.class);

        final HelloWorldResource helloWorldResource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName(),
                fhc,
                jdbi,
                dao
        );

        environment.jersey().register(new ResourceListingProvider());
        environment.jersey().register(new ApiDeclarationProvider());
        environment.jersey().register(new ApiListingResourceJSON());
        ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new DefaultJaxrsApiReader());

        SwaggerConfig config = ConfigFactory.config();
        config.setApiVersion("0.1");
        config.setBasePath("http://localhost:8080");

        environment.jersey().register(helloWorldResource);

    }
}
