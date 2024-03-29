package edu.stthomas.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {
    private String id;
    static HashMap<String,String> users = new HashMap<>();
    private static Map<String, Integer> cashierLevels = new HashMap<>();

    static{
        users.put("sara3224", "sara3224");
        users.put("stre1002", "stre1002");
        users.put("sun04877", "sun04877");
        users.put("alinaqvi", "thisismypassword");

        cashierLevels.put("sara3224",1);
        cashierLevels.put("stre1002",1);
        cashierLevels.put("sun04877",1);
        cashierLevels.put("alinaqvi",2);
    }

    public User(String id) {
        this.id = id;
    }

    public static boolean authenticate(String user, String password) {
        boolean valid = false;
        if(users.containsKey(user) && password!= null && Objects.equals(password, users.get(user))) {
            valid = true;
        }
        return valid;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        int level = 0;
        try {
            level = cashierLevels.get(getId());
        }catch(Exception e){

        }
        return level;
    }
}
