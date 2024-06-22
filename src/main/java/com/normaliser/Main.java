package com.normaliser;

public class Main {

    public static void main(String[] args) throws Exception {

        JsonReader jsonReader = new JsonReader("memory.json");
        Manager manager = Manager.getInstance(jsonReader, new Normaliser());

        String jt = "Java engineer";

        jt = "Jr Analyst Account";
        System.out.println(manager.normalise(jt));

    }
}
