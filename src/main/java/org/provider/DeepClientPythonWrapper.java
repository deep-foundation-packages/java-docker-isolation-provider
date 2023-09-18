package org.provider;

import jep.Jep;
import jep.SharedInterpreter;
import jep.Interpreter;
import jep.JepException;
import java.util.*;
import java.util.stream.Collectors;

public class DeepClientPythonWrapper {
    private final Interpreter jep;

    public DeepClientPythonWrapper() throws JepException {
        jep = new SharedInterpreter();
        jep.exec("import sys\n" +
                "sys.path.append('.')");
        jep.exec("from deep_client_interface import *");
    }

    public void finalizePython() {
        if (jep != null) {
            jep.close();
        }
    }

    public Map<String, Object> select(String token, String url, Object query) throws JepException {
        Map<String, Object> result = new HashMap<>();
        try {
            jep.set("token", token);
            jep.set("url", url);
            jep.set("query", query);
            jep.exec("result = select(token, url, query)");
            result = jep.getValue("result", Map.class);
        } catch (JepException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Object> insert(String token, String url, Object query) throws JepException {
        Map<String, Object> result = new HashMap<>();
        try {
            jep.set("token", token);
            jep.set("url", url);
            jep.set("query", query);
            jep.exec("result = insert(token, url, query)");
            result = jep.getValue("result", Map.class);
        } catch (JepException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void close() {
        finalizePython();
    }

    public static void main(String[] args) {
        System.out.println("Start: ");
        try {
            DeepClientPythonWrapper wrapper = new DeepClientPythonWrapper();

           /* String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWFsbG93ZWQtcm9sZXMiOlsiYWRtaW4iXSwieC1oYXN1cmEtZGVmYXVsdC1yb2xlIjoiYWRtaW4iLCJ4LWhhc3VyYS11c2VyLWlkIjoiMzgwIn0sImlhdCI6MTY5MTkxMTQxM30.W0GOuqOvRZrgrVZkLaceKTPBitXwR-1WlxLgxUZXOnY";
            String url = "http://192.168.0.135:3006/gql";*/
            /*Map<String, Object> query = new HashMap<>();
            query.put("key", "value");*/

            /*Map<String, Object> result = wrapper.select(token, url, 1);
            System.out.println("Result: " + result);*/

           // wrapper.close();
            System.out.println("try: ");
        } catch (JepException e) {
            e.printStackTrace();
        }
    }
}
