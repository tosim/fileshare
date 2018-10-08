package com.tosim.fileshare.web.mapper;

import com.alibaba.fastjson.JSON;
import com.tosim.fileshare.common.domain.FsFile;
import com.tosim.fileshare.common.mapper.FsFileMapper;
import com.tosim.fileshare.web.WebApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebApi.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class FsFileMapperTest {
    @Autowired
    FsFileMapper fsFileMapper;

    @Test
    public void test() {
        FsFile fsFile = new FsFile();
        fsFile.setReduceFlag("08ff3717c9d744b1a63f961bcc41325c");
        System.out.println(JSON.toJSONString(fsFileMapper.selectOne(fsFile)));
    }
}
