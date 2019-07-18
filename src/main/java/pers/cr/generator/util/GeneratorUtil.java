package pers.cr.generator.util;

import cn.org.rapid_framework.generator.provider.db.table.TableFactory;
import cn.org.rapid_framework.generator.provider.db.table.model.Table;
import freemarker.template.Configuration;
import freemarker.template.Template;
import pers.cr.generator.util.jdbc.BaseDao;
import pers.cr.generator.util.jdbc.DataSource;
import pers.cr.generator.util.model.View;

import java.io.*;
import java.util.*;

public class GeneratorUtil {

    private List<Table> tableList = new ArrayList<>();
    private Map<String, Object> parameterMap = new HashMap<>();
    private List<File> templateFileList = new ArrayList<>();
    private File generatorDirectory;
    private Properties properties = new Properties();
    private DataSource dataSource = new DataSource();
    private BaseDao baseDao;


    private Map<String, List<View>> viewsMap = new HashMap<>(); // key = viewName  value = viewField
    private List<List<View>> viewsList = new ArrayList<>();

    public void generatorByView(String viewName) {
        loadConfig();
        parameterMap.put("tempateDirectory", properties.getProperty("viewTempateDirectory"));
        initDataSource();
        getViews(viewName);
        getTemplateFile();
        viewsMap.forEach((k,v)->{ createTemplateFileByView(k,v); });
    }

    // 获取所有视图对象
    public void getViews(String viewName) {
        System.out.println(dataSource);
        if (viewName.equals("all")) {
            String database = properties.get("database").toString();
            String sql = "SELECT table_name FROM INFORMATION_SCHEMA.views where TABLE_SCHEMA = '" + database + "';";
            List<Map<String, Object>> viewsList = baseDao.select(sql);
            for (Map<String, Object> views : viewsList) {
                String vName = views.get("TABLE_NAME").toString();
                viewsMap.put(vName, getViewsList(vName));
            }
        } else {
            for (String s : viewName.split(",")) {
                viewsMap.put(s, getViewsList(s));
            }
        }
    }

    // 根据单个视图名称获取视图对象
    public List<View> getViewsList(String viewName) {
        List<View> viewList = new ArrayList<>();
        String sql = "select  * from " + viewName + " limit 0,1";
        try {
            Map<String, Object> map = baseDao.select(sql).get(0);
            for (Object o : map.keySet()) {
                String key = o.toString();
                String type = "0";
                if (key.toLowerCase().contains("time") || key.toLowerCase().contains("date")) {
                    type = "1";
                } else if (key.toLowerCase().contains("status")) {
                    type = "2";
                }
                View view = new View(key, type);
                viewList.add(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("视图不存在");
        }
        return viewList;
    }

    // 初始化数据源
    public void initDataSource() {
        dataSource.setUrl(properties.get("url").toString());
        dataSource.setDriver(properties.get("driver").toString());
        dataSource.setPassword(properties.get("password").toString());
        dataSource.setUsername(properties.get("username").toString());
        baseDao = new BaseDao(dataSource);
    }

    //根据table生成
    public void generatorByTable(String tableName) {
        getTable(tableName);
        loadConfig();
        getTemplateFile();
        tableList.forEach(x -> {
            createTemplateFileByTable(x);
        });
    }

    //1.获取要生成的表的信息
    public void getTable(String tableName) {
        if (tableName.equals("all")) {
            tableList = TableFactory.getInstance().getAllTables();
        } else {
            for (String s : tableName.split(",")) {
                tableList.add(TableFactory.getInstance().getTable(s));
            }
        }
    }

    //2.加载配置文件信息
    public void loadConfig() {
        try {
            properties.load(GeneratorUtil.class.getClassLoader().getResourceAsStream("generator.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        parameterMap.put("tempateDirectory", properties.getProperty("tempateDirectory"));
        generatorDirectory = new File(properties.getProperty("generatorDirectory"));
        parameterMap.put("package", properties.getProperty("package"));
    }

    //3.获取模板文件
    public void getTemplateFile() {
        File tempateDirectory = new File(parameterMap.get("tempateDirectory").toString());
        if (tempateDirectory.exists()) {//如果模板文件夹目录存在就获取当前文件夹目录下的所有文件
            getFiles(tempateDirectory, templateFileList);
        }
    }

    //3.2 根据指定文件夹目录获取文件夹下的文件
    public void getFiles(File folder, List<File> list) {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {//如果是文件夹，就递归遍历
                getFiles(file, list);
            } else {
                list.add(file);
            }
        }
    }

    //4. 根据当前Table生成模板
    public void createTemplateFileByTable(Table table) {
        appendParameterMapByTable(table);
        Configuration configuration = new Configuration();
        configuration.setEncoding(Locale.getDefault(), "UTF-8");
        templateFileList.forEach(x -> {
            create(getTemplate(configuration, x), x, configuration);
        });
    }

    //4.3根据当前模板文件，生成文件
    public void create(Template template,  File templateFile, Configuration configuration) {
        replaceContent(template, createFile(templateFile, configuration));
    }

    //4.5  根据模板文件和生成文件  更替内容
    public void replaceContent(Template template, File file) {
        try (Writer writer = new FileWriter(file)) {
            template.process(parameterMap, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //4.4 创建文件
    public File createFile(File templateFile, Configuration configuration) {
        String fileName = templateFile.getName();
        String absPath = templateFile.getAbsolutePath();//全路径
        fileName = absPath.substring(parameterMap.get("tempateDirectory").toString().length());//全路径-模板路径 = 模板文件路径
        try {
            StringWriter name = new StringWriter();
            Template template = new Template(generatorDirectory.getName(), new StringReader(fileName), configuration);
            template.process(parameterMap, name);//替换名称
            fileName = name.toString();
            File file = new File(generatorDirectory, fileName);
            file.getParentFile().mkdirs();
            file.createNewFile();
            return file;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //4.2 获取当前模板文件
    public Template getTemplate(Configuration configuration, File file) {
        try (Reader reader = new InputStreamReader(new FileInputStream(file))) {
            return new Template(file.getName(), reader, configuration);
        } catch (Exception e) {
            return null;
        }
    }

    //4.1  根据当前Table添加参数到map中
    public void appendParameterMapByTable(Table table) {
        parameterMap.put("table", table);
        parameterMap.put("className", table.getClassName());
    }

    // 添加视图名称和视图对应的字段到map
    public void appendParameterMapByView(String viewName,List<View> viewList){
        parameterMap.put("viewList",viewList);
        parameterMap.put("viewName",viewName);
    }

    //4. 根据当前Table生成模板
    public void createTemplateFileByView(String viewName,List<View> viewList) {
        appendParameterMapByView(viewName,viewList);
        Configuration configuration = new Configuration();
        configuration.setEncoding(Locale.getDefault(), "UTF-8");
        templateFileList.forEach(x -> {
            create(getTemplate(configuration, x), x, configuration);
        });
    }



}
