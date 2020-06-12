package com.xx.design.principle.demeter;

import java.util.ArrayList;
import java.util.List;

public class Boss {
    public void commandCheckNumber(TeamLeader teamLeader){
        List<Course> courseList = new ArrayList<Course>();
        for(int i=0; i<20; i++){
            courseList.add(new Course());
        }
    }
}
