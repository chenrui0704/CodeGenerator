package ${package}.entity;

import ${package}.entity.${table.className};

public interface ${className}Service  {

	Object queryAll(${table.className} ${table.classNameFirstLower});

	Object add(${table.className} ${table.classNameFirstLower});

	Object update(${table.className} ${table.classNameFirstLower});


}