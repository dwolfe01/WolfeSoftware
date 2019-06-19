package com.wolfesoftware.pact;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;

@RunWith(PactRunner.class)
@Provider("user_provider")
@PactFolder("/tmp/pacts")
public class ProviderContractTest {

    @TestTarget
    public final Target target = new HttpTarget("http", "localhost", 8080, "/");

    @Before
    public void start() {
    	SpringApplication.run(MyProvider.class);
    }

    @State("test GET")
    public void toGetState() {
    }

}