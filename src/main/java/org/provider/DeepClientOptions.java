package org.provider;

public class DeepClientOptions {
    private Object gqlClient;
    private String table;
    private String linksSelectReturning;
    private String selectReturning;
    private String valuesSelectReturning;
    private String selectorsSelectReturning;
    private String filesSelectReturning;
    private String defaultSelectName;

    public DeepClientOptions(Object gqlClient, String table, String linksSelectReturning,
                             String valuesSelectReturning, String selectorsSelectReturning,
                             String filesSelectReturning, String defaultSelectName) {
        this.gqlClient = gqlClient;
        this.table = table;
        this.linksSelectReturning = linksSelectReturning;
        this.selectReturning = this.linksSelectReturning;
        this.valuesSelectReturning = valuesSelectReturning;
        this.selectorsSelectReturning = selectorsSelectReturning;
        this.filesSelectReturning = filesSelectReturning;
        this.defaultSelectName = defaultSelectName;
    }

    public DeepClientOptions(Object gqlClient) {
        this(gqlClient, "links", "id type_id from_id to_id value", "id link_id value",
                "item_id selector_id", "id link_id name mimeType", "SELECT");
    }

    public Object getGqlClient() {
        return gqlClient;
    }

    public void setGqlClient(Object gqlClient) {
        this.gqlClient = gqlClient;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getLinksSelectReturning() {
        return linksSelectReturning;
    }

    public void setLinksSelectReturning(String linksSelectReturning) {
        this.linksSelectReturning = linksSelectReturning;
        this.selectReturning = this.linksSelectReturning;
    }

    public String getSelectReturning() {
        return selectReturning;
    }

    public String getValuesSelectReturning() {
        return valuesSelectReturning;
    }

    public void setValuesSelectReturning(String valuesSelectReturning) {
        this.valuesSelectReturning = valuesSelectReturning;
        this.selectReturning = this.valuesSelectReturning;
    }

    public String getSelectorsSelectReturning() {
        return selectorsSelectReturning;
    }

    public void setSelectorsSelectReturning(String selectorsSelectReturning) {
        this.selectorsSelectReturning = selectorsSelectReturning;
        this.selectReturning = this.selectorsSelectReturning;
    }

    public String getFilesSelectReturning() {
        return filesSelectReturning;
    }

    public void setFilesSelectReturning(String filesSelectReturning) {
        this.filesSelectReturning = filesSelectReturning;
        this.selectReturning = this.filesSelectReturning;
    }

    public String getDefaultSelectName() {
        return defaultSelectName;
    }

    public void setDefaultSelectName(String defaultSelectName) {
        this.defaultSelectName = defaultSelectName;
    }
}
