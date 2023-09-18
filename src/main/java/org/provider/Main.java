package org.provider;

import org.python.core.PyObject;
import org.python.core.Py;

public class Main {
    public static void main(String[] args) {
        DeepClientPythonWrapper pythonWrapper = new DeepClientPythonWrapper();

        PyObject result = pythonWrapper.select("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwczovL2hhc3VyYS5pby9qd3QvY2xhaW1zIjp7IngtaGFzdXJhLWFsbG93ZWQtcm9sZXMiOlsiYWRtaW4iXSwieC1oYXN1cmEtZGVmYXVsdC1yb2xlIjoiYWRtaW4iLCJ4LWhhc3VyYS11c2VyLWlkIjoiMzgwIn0sImlhdCI6MTY5MTkxMTQxM30.W0GOuqOvRZrgrVZkLaceKTPBitXwR-1WlxLgxUZXOnY",
                "http://192.168.0.135:3006/gql", 1, Py.None);

        if (result != Py.None) {
            System.out.println("Result: " + result);
        } else {
            System.out.println("Error occurred.");
        }

        pythonWrapper.close();
    }
}