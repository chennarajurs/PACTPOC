package com;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.RestPactRunner;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.target.MockMvcTarget;
import com.controller.UserController;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

@RunWith(RestPactRunner.class)
@PactFolder("mypacts")
@Provider("provider_service")
public class PactProviderTest
{
    @TestTarget
    public final MockMvcTarget target = new MockMvcTarget();

    @InjectMocks
    private UserController userController;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        target.setControllers(userController);
    }

    @State("test GET User details")
    public void toGetState()
    {
    }
}