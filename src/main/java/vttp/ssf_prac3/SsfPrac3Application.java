package vttp.ssf_prac3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf_prac3.model.Todo;
import vttp.ssf_prac3.repo.TodoRepo;
import vttp.ssf_prac3.util.Util;

@SpringBootApplication
public class SsfPrac3Application implements CommandLineRunner{

	@Autowired
	TodoRepo todoRepo;

	public static void main(String[] args) {
		SpringApplication.run(SsfPrac3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		boolean x = loadJsonToRedis();
		if (x){
			System.out.println("created in redis");
		}
		else{
			System.out.println("already loaded in");
		}
	}

	private boolean loadJsonToRedis() throws IOException, ParseException{
		
		if (!todoRepo.checkHashTable()){
			JsonArray todosArray = loadFileAsArray();

			for (int i = 0 ; i < todosArray.size() ; i++){
				Todo todo = new Todo();
				JsonObject jsonObject = todosArray.getJsonObject(i);
				String id = todo.getId();
				todo.setName(jsonObject.getString("name"));
				todo.setDescription(jsonObject.getString("description"));

				todo.setDueDate(parseDateString(jsonObject.getString("due_date")));
				todo.setPriority(jsonObject.getString("priority_level"));
				todo.setStatus(jsonObject.getString("status"));

				todo.setCreatDate(parseDateString(jsonObject.getString("created_at")));
				todo.setUpdateDate(parseDateString(jsonObject.getString("updated_at")));
				
				todoRepo.create(id, todo.toString());
			}
			return true;
		}
		return false;

	}

	private JsonArray loadFileAsArray() throws IOException{

		File f = new File(Util.filePath);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		JsonReader jsonReader = Json.createReader(br);
		JsonArray jsonData = jsonReader.readArray();
		jsonReader.close();
		br.close();

		return jsonData;
	}

	public Date parseDateString(String dateString) throws ParseException{

		String dateString2 = dateString.replaceAll("/", "-").replace(",", "");
        SimpleDateFormat sdf = new SimpleDateFormat("E dd-MM-yyyy");
		Date date = sdf.parse(dateString2);
		return date;
	}

	



}
