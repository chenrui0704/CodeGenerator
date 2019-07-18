using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BookStore.Models
{

    public class ${className}
    {
       <#list table.columns as column>
       <#assign length ="${column.javaType?length}">
       <#assign end ="${column.javaType?last_index_of(\".\")}">
       public ${column.javaType?substring("${end}"?number+1,"${length}"?number)} ${column.columnNameLower} { get; set; }
       </#list>
    }
}





