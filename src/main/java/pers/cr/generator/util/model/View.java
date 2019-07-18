package pers.cr.generator.util.model;

public class View {

    private String key;

    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public View() {
    }

    public View(String key, String type) {
        this.key = key;
        this.type = type;
    }
}
