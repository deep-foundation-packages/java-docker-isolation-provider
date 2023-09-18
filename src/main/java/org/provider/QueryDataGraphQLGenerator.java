package org.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryDataGraphQLGenerator {

    public static class QueryData {
        String tableName;
        String operation;
        String queryName;
        String returning;
        Map<String, Object> variables;
        List<String> fields;
        Map<String, String> fieldTypes;
        int index;
        List<String> defs;
        List<String> args;
        String alias;
        String resultAlias;
        Map<String, Object> resultVariables;
    }

    public static Map<String, String> fieldsInputs(String tableName) {
        Map<String, String> fieldTypes = new HashMap<>();
        fieldTypes.put("distinct_on", "[" + tableName + "_select_column!]");
        fieldTypes.put("limit", "Int");
        fieldTypes.put("offset", "Int");
        fieldTypes.put("order_by", "[" + tableName + "_order_by!]");
        fieldTypes.put("where", tableName + "_bool_exp!");
        return fieldTypes;
    }

    public static QueryData generateQueryData(Map<String, Object> options) {
        String table_name = (String) options.get("tableName");
        String operation = (String) options.getOrDefault("operation", "query");
        String query_name = (String) options.getOrDefault("queryName", table_name);
        String returning = (String) options.getOrDefault("returning", "id");
        Map<String, Object> variables = (Map<String, Object>) options.getOrDefault("variables", new HashMap<>());
        List<String> fields = new ArrayList<>();
        fields.add("distinct_on");
        fields.add("limit");
        fields.add("offset");
        fields.add("order_by");
        fields.add("where");
        Map<String, String> fieldTypes = fieldsInputs(table_name);

        QueryData result = new QueryData();
        result.tableName = table_name;
        result.operation = operation;
        result.queryName = query_name;
        result.returning = returning;
        result.variables = variables;
        result.fields = fields;
        result.fieldTypes = fieldTypes;
        result.index = 0;
        result.defs = new ArrayList<>();
        result.args = new ArrayList<>();
        result.alias = "";
        result.resultAlias = result.alias + result.index;
        result.resultVariables = new HashMap<>();
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            result.resultVariables.put(entry.getKey() + result.index, entry.getValue());
        }

        return result;
    }

    public static Map<String, Object> generateQuery(Map<String, Object> options) {
        List<Map<String, Object>> queries = (List<Map<String, Object>>) options.getOrDefault("queries", new ArrayList<>());
        String operation = (String) options.getOrDefault("operation", "query");
        String name = (String) options.getOrDefault("name", "QUERY");
        String alias = (String) options.getOrDefault("alias", "q");

        List<QueryData> calledQueries = new ArrayList<>();
        for (int i = 0; i < queries.size(); i++) {
            QueryData queryData = generateQueryData(queries.get(i));
            calledQueries.add(queryData);
        }

        List<String> defsList = new ArrayList<>();
        for (QueryData queryData : calledQueries) {
            defsList.addAll(queryData.defs);
        }
        String defs = String.join(",", defsList);

        List<String> queryBodyList = new ArrayList<>();
        for (QueryData queryData : calledQueries) {
            queryBodyList.add(queryData.resultAlias + ": " + queryData.queryName + "(" + String.join(",", queryData.args) + ") { " + queryData.returning + " }");
        }
        String queryBody = String.join(",", queryBodyList);

        String query_string = operation + " " + name + " (" + defs + ") {" + queryBody + "}";

        Map<String, Object> variables = new HashMap<>();
        for (QueryData queryData : calledQueries) {
            variables.putAll(queryData.resultVariables);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("query", query_string);
        result.put("variables", variables);
        result.put("query_string", query_string);
        return result;
    }

    public static void main(String[] args) {
        Map<String, Object> options = new HashMap<>();
        List<Map<String, Object>> queries = new ArrayList<>();
        Map<String, Object> query1 = new HashMap<>();
        query1.put("tableName", "User");
        query1.put("operation", "query");
        query1.put("queryName", "getUser");
        query1.put("returning", "name");
        Map<String, Object> variables1 = new HashMap<>();
        variables1.put("id", 1);
        query1.put("variables", variables1);
        queries.add(query1);

        Map<String, Object> query2 = new HashMap<>();
        query2.put("tableName", "Post");
        query2.put("operation", "query");
        query2.put("queryName", "getPost");
        query2.put("returning", "title");
        Map<String, Object> variables2 = new HashMap<>();
        variables2.put("id", 2);
        query2.put("variables", variables2);
        queries.add(query2);

        options.put("queries", queries);

        Map<String, Object> generatedQuery = generateQuery(options);
        System.out.println("Generated Query: " + generatedQuery.get("query"));
        System.out.println("Variables: " + generatedQuery.get("variables"));
    }
}