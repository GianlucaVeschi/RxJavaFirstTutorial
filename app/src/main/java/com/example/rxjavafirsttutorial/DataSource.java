package com.example.rxjavafirsttutorial;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    public static List<Task> createTaskList(){
        List<Task> tasks = new ArrayList<>();

        tasks.add(new Task("Study Reactive Programming",true,   3));
        tasks.add(new Task("Tell Bianca to fuck off",   false,  1));
        tasks.add(new Task("Fuck Benedetta again",      true,  2));
        tasks.add(new Task("Unload the dishwasher",     false,  0));
        tasks.add(new Task("Make dinner",               true,   5));

        return tasks;
    }
}
