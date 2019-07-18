package ${package}.entity;

import ${package}.util.model.ModelDao;
import ${package}.entity.${table.className};
import ${package}.dao.${table.className}Dao;
import ${package}.service.${className}Service;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ${className}ServiceImpl implements ${className}Service {

	@Autowired
    ${table.className}Dao ${table.classNameFirstLower}Dao;

	@Override
	public Object queryAll(${table.className} ${table.classNameFirstLower}){
		List<${table.className}> list = ${table.classNameFirstLower}Dao.query(${table.classNameFirstLower});
		return  list;
	}

	@Override
	public Object add(${table.className} ${table.classNameFirstLower}){
		${table.classNameFirstLower}Dao.insert(${table.classNameFirstLower});
		return  "100";
	}

	@Override
	public Object update(${table.className} ${table.classNameFirstLower}){
		${table.classNameFirstLower}Dao.updateById(${table.classNameFirstLower});
		return "100";
	}

}