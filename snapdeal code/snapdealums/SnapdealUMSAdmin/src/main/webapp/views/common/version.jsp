<%@ page language="java" import="java.util.Properties, java.io.InputStream" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
<h1 align="center">War-Version</h1>
</body>
<%
Properties prop = new Properties();
InputStream is = pageContext.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
if(is != null){
	prop.load(is);
%>
<h3>Manifest-Version: <%=prop.getProperty("Manifest-Version")%></h3><br />
<h3>Archiver-Version: <%=prop.getProperty("Archiver-Version")%></h3><br />
<h3>Created-By: <%=prop.getProperty("Created-By")%></h3><br />
<h3>Built-By: <%=prop.getProperty("Built-By")%></h3><br />
<h3>Build-Jdk: <%=prop.getProperty("Build-Jdk")%></h3><br />
<%
	is.close();
} else {
%>
	Can't read /META-INF/MANIFEST.MF file.
<%
}
%>
</html>
