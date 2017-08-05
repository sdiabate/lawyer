<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript">
	function selectClient() {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if(xhttp.readyState == 4 && xhttp.status == 200) {
				document.getElementById("randomClient").innerHTML = xhttp.responseText;
			}
		};
		xhttp.open("GET", "randomClient", true);
		xhttp.send();
	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Clients list</title>
</head>
<body>
	<h2 align="center">List of the clients</h2>
	<br />
	<div align="center"><a href="clientCreationForm">Add new client</a></div>
	<br />
	<table border="1"  align="center" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
		<thead>
			<tr>
				<th height="50">First name</th>
				<th>Last name</th>
				<th>Phone</th>
				<th>Address</th>
				<th />
			</tr>
		</thead>
		<tbody align="center">
			<c:forEach items="${clients}" var="client">
				<tr>
					<td><c:out value="${client.firstName}" /></td>
					<td><c:out value="${client.lastName}" /></td>
					<td><c:out value="${client.mobilePhone}" /></td>
					<td><c:out value="${client.address}" /></td>
					<td>
						<c:url value="/clientUpdate" var="update">
							<c:param name="idClient" value="${client.id}" />
						</c:url>
                        <a href="${update}">
                            Update
                        </a>
                        <br />
						<c:url value="/clientDeletion" var="delete">
							<c:param name="idClient" value="${client.id}" />
						</c:url>
                        <a href="${delete}" onclick="return confirm('Do you really want to delete this client ?')">
                            Delete
                        </a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br /><br />
	<div align="center">
		<h3>The following section tests AJAX request</h3>
		<input type="button" value="Randomly select a client" onclick="selectClient();" />
		<br /><br />
		<div id="randomClient">*****</div>
	</div>
</body>
</html>