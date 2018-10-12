package com.tosim.fileshare.web.service.impl;

import com.tosim.fileshare.web.WebApi;
import com.tosim.fileshare.web.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebApi.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class FileServiceImplTest {
    @Autowired
    private FileService fileService;

    @Test
    public void searchFiles() {
//        fileService.searchFiles("好", 1, 5);
    }
}
