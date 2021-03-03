package pers.cr.generator.util;

import pers.cr.generator.util.jdbc.BaseDao;
import pers.cr.generator.util.jdbc.DataSource;
import pers.cr.generator.util.model.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GeneratorMain {

    public static void main(String[] args) {
        // 根据数据库表生成  全部生成用 all  多个,隔开  1.all 2. test,test2   3. test
         new GeneratorUtil().generatorByTable("f_order_box");
    }

}
