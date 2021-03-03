package ${package}.entity;

import java.util.Date;
import lombok.Data;

@Data
public class ${className} {

<#list table.columns as column>
<#assign length ="${column.javaType?length}">
<#assign end ="${column.javaType?last_index_of(\".\")}">
	private ${column.javaType?substring("${end}"?number+1,"${length}"?number)} ${column.columnNameLower}; // ${column.columnAlias}
</#list>

}