package com.example.tlias_study_system;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class TliasStudySystemApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(TliasStudySystemApplicationTests.class);
    @Test
    void test(){
        int i =0;
        while(i++<40000) {
            log.debug("test1111");
            log.info("test1111");
        }
    }

}
