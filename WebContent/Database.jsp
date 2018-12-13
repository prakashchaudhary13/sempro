<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% String msg=request.getParameter("msg");
if(msg!=null)
	out.println("<center><h3>"+msg+"</h3></center>");
%>
<center>
<form action="Query" method=post>
Query:<br/> <input type="Text" name="sql" required height=300px width=300px>
<input type ="submit" name="submit" value="Execute">
</form>
</center>
</body>
</html>