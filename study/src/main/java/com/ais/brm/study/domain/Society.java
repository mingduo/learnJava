package com.ais.brm.study.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Society {

    private String name;

    public static String Advisors = "advisors";
    public static String President = "president";

    private List<Inventor> members = new ArrayList<>();
    private Map officers = new HashMap();

    {
        officers.put("a", 15);
        officers.put("a2", 28);
        officers.put("a3", 30);
        officers.put("a4", 10);

    }

    public Void addMembers(List<Inventor> members) {
        this.members.addAll(members);
        return null;
    }

    public List getMembers() {
        return members;
    }

    public Map getOfficers() {
        return officers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMember(String name) {
        for (Inventor inventor : members) {
            if (inventor.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}