package com.example;

import com.example.service.OutputExcleService2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelApplicationTests {

	@Test
	public void contextLoads() {
		OutputExcleService2 outputExcleService2 = new OutputExcleService2();
		try {
			outputExcleService2.outputExcel("1");
			System.out.println("--------------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
