package com.green.greengramver1.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CustomFileUtilsTest {

    @Autowired          // DI를 해달라는 명령어
    CustomFileUtils utils;

    @Test
    void makeFolders() {
        String result = utils.makeFolders("ddd3");
        System.out.println("result: " + result);
    }
    @Test
    void makeRandomFileName() {
        String result = utils.makeRandomFileName();
        System.out.println("makeRandomFileName: " + result);
    }

    @Test
    void getExt() {
        String ext = utils.getExt("aaa.bbb.png");
        System.out.println("ext : " + ext);
    }
    @Test
    void makeRandomFileName_2() {
        String str = utils.makeRandomFileName("mmmmmmmmm.png");
        System.out.println(str);
    }
}