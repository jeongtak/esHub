<%@page import="java.util.List"%>
<%@page import="java.util.AbstractMap.SimpleEntry"%>
<%@page import="asis.eshub.server.Main"%>

<html>
<body>
<h2>EsHub Admin Page</h2>
<hr>
<h3>Server instance list</h3>
<%
	List<SimpleEntry<String, Object>> serverList = Main.SERVER_LIST;
	
	for(SimpleEntry entry : serverList){
		out.println("> "+entry.getKey()+" : "+entry.getValue()+"<br>");
	}

%>
</body>
</html>
