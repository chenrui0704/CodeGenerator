package ${package}.entity;

import ${package}.entity.${table.className};
import ${package}.service.${table.className}Service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/${table.classNameFirstLower}")
public class ${className}Controller  {

	@Autowired
    ${table.className}Service ${table.classNameFirstLower}Service;

	@RequestMapping("/queryAll")
	public Object queryAll(@RequestBody ${table.className} ${table.classNameFirstLower}){
		return  ${table.classNameFirstLower}Service.queryAll(${table.classNameFirstLower});
	}

	@RequestMapping("/add")
	public Object add(@RequestBody ${table.className} ${table.classNameFirstLower}){
		return  ${table.classNameFirstLower}Service.add(${table.classNameFirstLower});
	}

	@RequestMapping("/update")
	public Object update(@RequestBody ${table.className} ${table.classNameFirstLower}){
		return  ${table.classNameFirstLower}Service.update(${table.classNameFirstLower});
	}

}