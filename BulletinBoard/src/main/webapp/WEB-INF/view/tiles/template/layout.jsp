<%@ page contentType="text/html" %>
<%@ taglib prefix="tiles"  uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<title><tiles:getAsString name="title" /></title>

<meta content="text/html; charset=windows-1252" http-equiv="Content-Type" />
</head>
<body>
<table style="margin-left: auto; margin-right: auto;" cellpadding="0" cellspacing="0" width="700" >
	<tr>
		<td colspan="2"><tiles:insertAttribute name="menu"/></td>
	</tr>
	<tr height="150">
	</tr>
	
	<tr>
		<td calspan="2"><tiles:insertAttribute name="body" /></td>
	</tr>
	
</table>


</body>
</html>
