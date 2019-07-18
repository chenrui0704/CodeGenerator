package pers.cr.generator.util.jdbc;



public class DataSource {


    private String url = "";
    private String driver = "com.mysql.jdbc.Driver";
    private String username = "root";
    private String password = "focuss";

    public String getUrl() {
        return url;
    }
    public String getDriver() {
        return driver;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "url='" + url + '\'' +
                ", driver='" + driver + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
