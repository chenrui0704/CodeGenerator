package ${package}.entity;

import ${package}.util.model.ModelDao;
import ${package}.entity.${table.className};
import ${package}.dao.${table.className}Mapper;

import cn.com.aps.util.MyResult;
import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ${className}Service {

	@Autowired
    ${table.className}Mapper ${table.classNameFirstLower}Mapper;

	public MyResult queryAll(${table.className} ${table.classNameFirstLower}){
		List<${table.className}> list = ${table.classNameFirstLower}Mapper.query(${table.classNameFirstLower});
		return MyResult.success(list );
	}

	public MyResult add(${table.className} ${table.classNameFirstLower}){
		${table.classNameFirstLower}Mapper.insert(${table.classNameFirstLower});
		return MyResult.success();
	}

	public MyResult update(${table.className} ${table.classNameFirstLower}){
		${table.classNameFirstLower}Mapper.updateById(${table.classNameFirstLower});
		return MyResult.success();
	}

}