package ${package}.entity;

import java.util.List;
import ${package}.entity.${table.className};

public interface ${className}Mapper  {

	List<${table.className}> query(${table.className} ${table.classNameFirstLower});

	${table.className} queryById(Integer id);

	void insert(${table.className} ${table.classNameFirstLower});

	void updateById(${table.className} ${table.classNameFirstLower});

	void deleteById(Integer id);

	void delete(${table.className} ${table.classNameFirstLower});

}