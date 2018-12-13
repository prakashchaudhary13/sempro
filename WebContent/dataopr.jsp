<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
textarea {
    width: 100%;
    height: 150px;
    padding: 12px 20px;
    box-sizing: border-box;
    border: 2px solid #ccc;
    border-radius: 4px;
    background-color: #f8f8f8;
    resize: none;
}
input[type=button], input[type=submit], input[type=reset] {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 16px 32px;
    text-decoration: none;
    margin: 4px 2px;
    cursor: pointer;
}
</style>
<head><title>Database Administration</title></head>
</head>
<body>
<% String sql=request.getParameter("sql");
	if(sql==null)
		sql="";
%>
<center>
<form action="QueryExecutor" method="post">
 <textarea id="message" name="query" rows="8" cols="50" value="Enter query...."><% out.print(sql); %></textarea><br/> 
 <input type="submit" value="Execute">
</form>
<% String msg=request.getParameter("msg");
	if(msg==null)
		msg="";
%>
<h4><% out.print(msg); %></h4>
</center>
</body>
</html>