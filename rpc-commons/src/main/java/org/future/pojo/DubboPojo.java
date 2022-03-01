package org.future.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DubboPojo implements Serializable {

    private String id;

    private String name;

    private int age;

    private Date birthday;

}
