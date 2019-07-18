package ${package}.entity;

import java.util.Date;
public class ${className} {

<#list table.columns as column>
<#assign length ="${column.javaType?length}">
<#assign end ="${column.javaType?last_index_of(\".\")}">
	private ${column.javaType?substring("${end}"?number+1,"${length}"?number)} ${column.columnNameLower};
</#list>

<#list table.columns as column>
<#assign length ="${column.javaType?length}">
<#assign end ="${column.javaType?last_index_of(\".\")}">
	public void set${column.columnName}(${column.javaType?substring("${end}"?number+1,"${length}"?number)} value) {
		this.${column.columnNameLower} = value;
	}

	public ${column.javaType?substring("${end}"?number+1,"${length}"?number)} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
</#list>


	public ${className} (){

	}

	public ${className} (
			<#list table.columns as column>
<#assign length ="${column.javaType?length}">
<#assign end ="${column.javaType?last_index_of(\".\")}">
 ${column.javaType?substring("${end}"?number+1,"${length}"?number)} ${column.columnNameLower} <#if column_has_next>,</#if>
</#list>
		){
	<#list table.columns as column>
	<#assign length ="${column.javaType?length}">
	<#assign end ="${column.javaType?last_index_of(\".\")}">
		this.${column.columnNameLower} = ${column.columnNameLower};
	</#list>
	}


}