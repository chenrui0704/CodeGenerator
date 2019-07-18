package ${package}.dao;
import java.util.List;

import ${package}.util.model.ModelDao;
import ${package}.entity.${table.className};
public  interface ${className}Dao extends ModelDao<${className}>  {

    void delete(${table.className} ${table.classNameFirstLower});


}
