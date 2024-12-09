package vttp.ssf_prac3.repo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf_prac3.model.Todo;
import vttp.ssf_prac3.util.Util;


@Repository
public class TodoRepo implements Util{
    
    @Autowired
    @Qualifier(Util.template)
    RedisTemplate<String, String> stringTemplate;

    public boolean create(String id, String entry){
        stringTemplate.opsForHash().put(Util.redisKey, id, entry);
        return true;
    }

    public Todo getById(String id) throws ParseException{

        String entry = (String) stringTemplate.opsForHash().get(Util.redisKey, id);
        String[] terms = entry.split(Util.delimiter);
        // for (String s : terms){
        //     System.out.println(s);
        // }
        Todo todo = new Todo();
        todo.setId(terms[0]);
        todo.setName(terms[1]);
        todo.setDescription(terms[2]);
        todo.setDueDate(parseDateLong(terms[3]));
        todo.setPriority((terms[4]));
        todo.setStatus((terms[5]));
        todo.setCreatDate(parseDateLong(terms[6]));
        todo.setUpdateDate(parseDateLong(terms[7]));

        return todo;
    }

    public boolean deleteById(String id){
        long number = stringTemplate.opsForHash().delete(Util.redisKey, id);
        return (number>0)? true : false;
    }

    public boolean updateById(String redisKey, String id, String updatedEntry){
        boolean isFound = deleteById(id);
        return isFound? create(id, updatedEntry) : false;
    }

    public Set<String> getKeys(){
        Set<Object> keys = stringTemplate.opsForHash().keys(Util.redisKey);
        return keys.stream().map(k -> (String) k).collect(Collectors.toSet());
    }

    public boolean checkHashTable(){
        return stringTemplate.hasKey(Util.redisKey);
    }

    public Date parseDateLong(String longDateString) throws ParseException{

        Long longDate = Long.parseLong(longDateString);
		Date date = new Date(longDate);

		return date;
	}



}