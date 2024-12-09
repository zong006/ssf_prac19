package vttp.ssf_prac3.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import vttp.ssf_prac3.util.Util;

public class Todo {
    private String id;

    @Size(min = 10, max = 50, message = "Name should be between 10 to 50 characters.")
    private String name;

    @Size(max = 255, message = "Description should contain a maximum of 255 characters.")
    private String description;

    @Future(message = "Due date must be in the future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    private String priority;
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;

    public Todo() {
        this.id = UUID.randomUUID().toString().substring(0,8);
        // this.createDate = new Date();
        // this.updateDate = new Date();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreatDate(Date createDate) {
        this.createDate = createDate;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {

        Long dueLong =  this.dueDate.getTime();
        Long createLong = this.createDate.getTime();
        Long updateLong = this.updateDate.getTime();

        return id + Util.delimiter 
                + name + Util.delimiter 
                + description + Util.delimiter 
                + dueLong + Util.delimiter
                + priority + Util.delimiter 
                + status + Util.delimiter 
                + createLong + Util.delimiter
                + updateLong;
    }
    
    
}
