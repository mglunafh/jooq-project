package org.some.generic.jooq.api;

import static junit.framework.TestCase.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.some.generic.jooq.TestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class, SampleSimpleApplication.class})
@EnableAutoConfiguration(exclude = FlywayAutoConfiguration.class)
public class SimpleControllerTest {

  @Autowired
  private SimpleController controller;

  @Before
  public void setUp() {
  }

  @Test
  public void testIndex() throws IOException {

    String index = controller.index();
    byte[] bytes = index.getBytes(StandardCharsets.UTF_8);
    assertTrue(index.length() > 300);
//    Files.write(Paths.get("some_index.html"), bytes);
  }
}