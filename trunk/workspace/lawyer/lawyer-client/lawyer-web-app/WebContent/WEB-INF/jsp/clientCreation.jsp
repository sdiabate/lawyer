<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<title>Clients creation</title>
	
	<style type="text/css">
	
		.error {
			color: #ff0000;
		}
		
		.errorblock {
			color: #000;
			background-color: #ffEEEE;
			border: 3px solid #ff0000;
			padding: 8px;
			margin: 16px;
		}
		
		.textFieldAutoSize {
			width: 100%;
			-webkit-box-sizing: border-box; /* <=iOS4, <= Android  2.3 */
			-moz-box-sizing: border-box; /* FF1+ */
			box-sizing: border-box; /* Chrome, IE8, Opera, Safari 5.1*/
		}
		
		.separationTR {
			height: 30px;
		}
		
		.separationDIV {
			width: 100%;
			height: 10px; 
			border-bottom: 1px solid black; 
			text-align: center;
		}
		
		.separationSPAN {
			background-color: white;
			padding: 0 10px;
		}
	</style>
</head>
<body>

	<form:form method="post" modelAttribute="client" action="clientCreation">
		<form:errors path="*" cssClass="errorblock" element="div" />
		<form:hidden path="id" />
		<table border="0" align="center">
			<tr>
				<td colspan="6" align="center"><h2>Client registration/update</h2></td>
			</tr>
			<tr>
				<td>Gender</td>
				<td>
					<form:select path="gender">
						<form:options items="${genders}" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td>First name</td>
				<td colspan="5">
					<form:input path="firstName" cssClass="textFieldAutoSize" /> 
					<b><i><form:errors path="firstName" cssclass="error" /></i></b>
				</td>
			</tr>
			<tr>
				<td>Last name</td>
				<td colspan="5">
					<form:input path="lastName" cssClass="textFieldAutoSize" /> 
					<b><i><form:errors path="lastName" cssclass="error" /></i></b>
				</td>
			</tr>
			<tr>
				<td>Birth date</td>
				<td>
					<form:input path="birthdate" /> <b><i>
					<form:errors path="birthdate" cssclass="error" /></i></b>
				</td>
				<td>Country</td>
				<td>
					<form:input path="country" />
					<b><i><form:errors path="country" cssclass="error" /></i></b>
				</td>
				<td>City</td>
				<td>
					<form:input path="city" /> 
					<b><i><form:errors path="city" cssclass="error" /></i></b>
				</td>
			</tr>
			<tr>
				<td>Post</td>
				<td colspan="5">
					<form:input path="post" cssClass="textFieldAutoSize" /> 
					<b><i><form:errors path="post" cssclass="error" /></i></b>
				</td>
			</tr>
			<tr class="separationTR">
				<td colspan="6">
					<div class="separationDIV">
						<span class="separationSPAN">
							Contact <!--Padding is optional-->
						</span>
					</div>
				</td>
			</tr>
			<tr>
				<td>Address</td>
				<td colspan="5">
					<form:input path="address" cssClass="textFieldAutoSize" /> 
					<b><i><form:errors path="address" cssclass="error" /></i></b>
				</td>
			</tr>
			<tr>
				<td>Mobile</td>
				<td>
					<form:input path="mobilePhone" /> 
					<b><i><form:errors path="mobilePhone" cssclass="error" /></i></b>
				</td>
				<td>Land</td>
				<td>
					<form:input path="landPhone" /> 
					<b><i><form:errors path="landPhone" cssclass="error" /></i></b>
				</td>
				<td>Fax</td>
				<td>
					<form:input path="fax" /> 
					<b><i><form:errors path="fax" cssclass="error" /></i></b>
				</td>
			</tr>
			<tr class="separationTR">
				<td colspan="6">
					<div class="separationDIV">
						<span class="separationSPAN">
							Identity document <!--Padding is optional-->
						</span>
					</div>
				</td>
			</tr>
			<tr>
				<td>Type</td>
				<td>
					<form:select path="idCardType">
						<form:options items="${idCardTypes}" itemLabel="label" />
					</form:select>
				</td>
				<td>Number</td>
				<td>
					<form:input path="idCardNumber" /> 
					<b><i><form:errors path="idCardNumber" cssclass="error" /></i></b>
				</td>
				<td>Date</td>
				<td>
					<form:input path="idCardDate" /> 
					<b><i><form:errors path="idCardDate" cssclass="error" /></i></b>
				</td>
			</tr>
			<tr>
				<td>Country</td>
				<td>
					<form:input path="idCardCountry" /> 
					<b><i><form:errors path="idCardCountry" cssclass="error" /></i></b>
				</td>
			</tr>
			<tr>
				<td colspan="6" align="right">
					<br/>
					<input name="cancel" type="submit" value="cancel" />
					<input name="save" type="submit" value="Save" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>