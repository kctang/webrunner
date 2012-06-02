package net.big2.webrunner.core.common.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:conf/webrunner.xml"})
@ActiveProfiles("test")
public abstract class BaseWebRunnerTest     {
    protected Logger log = LoggerFactory.getLogger(BaseWebRunnerTest.class);

}
