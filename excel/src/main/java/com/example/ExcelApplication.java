package com.example;

import com.example.service.SaveDataDBService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class ExcelApplication {

	public static void main(String[] args) throws IOException, SQLException {
		SpringApplication.run(ExcelApplication.class, args);

		/**
		 * excel导入
		 */
		SaveDataDBService saveDataDB = new SaveDataDBService();
		saveDataDB.save();
	}
}
