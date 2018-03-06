package org.swat.mockito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockingExample.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.swat.mockito"})
public class MockingExample {
    @MockBean
    private CarService carService;

    @Autowired
    private VehicleDelegator delegator;

    @Before
    public void before() {
        doReturn("MERC").when(carService).type();
        doCallRealMethod().when(carService).process();
    }

    @Test
    public void verify() {
        assertEquals("MERC", delegator.process("MERC"));
    }
}
