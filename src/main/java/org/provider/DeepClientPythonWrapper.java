package org.provider;

import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class DeepClientPythonWrapper {
    private PythonInterpreter interpreter;

    public DeepClientPythonWrapper() {
        interpreter = new PythonInterpreter();
    }

    public PyObject select(String token, String url, Object exp, PyObject options) {
        try {
            interpreter.exec("from deep_client_interface import select");
            PyObject selectFunction = interpreter.get("select");
            return selectFunction.__call__(Py.java2py(token), Py.java2py(url), Py.java2py(exp), options);
        } catch (PyException e) {
            e.printStackTrace();
            return Py.None;
        }
    }

    public void close() {
        interpreter.cleanup();
    }
}
