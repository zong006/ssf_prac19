package vttp.ssf_prac3.util;

import java.util.Arrays;
import java.util.List;

public interface Util {
    String redisKey = "prac3";
    String template = "stringTemplate";


    String filePath = "./src/main/resources/static/todos.json";

    String delimiter = "THIS_IS_A_DELIMITER";

    List<String> statusOption = Arrays.asList("Pending", "In Progress", "Completed");
    List<String> priorityOption = Arrays.asList("high", "medium", "low");
    
}
