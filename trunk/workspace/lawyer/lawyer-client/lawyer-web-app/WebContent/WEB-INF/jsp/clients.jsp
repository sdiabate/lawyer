<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Clients management</title>
<style>
/*       .username.ng-valid {
          background-color: lightgreen;
      }
      .username.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .username.ng-dirty.ng-invalid-minlength {
          background-color: yellow;
      }
 
      .email.ng-valid {
          background-color: lightgreen;
      }
      .email.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .email.ng-dirty.ng-invalid-email {
          background-color: yellow;
      } */

/* app.css */
	body, #mainWrapper {
		height: 70%;
		background-color: rgb(245, 245, 245);
	}
	
	body {
		text-align: center;
		vertical-align: middle;
	}
</style>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body ng-app="myApp" class="ng-cloak">
	<div class="generic-container" ng-controller="clientController">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="lead">Client registration form</span>
			</div>
			<div class="formcontainer">
				<form name="myForm" class="form-horizontal">
				
					<input type="hidden" ng-model="client.id" />
					
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-label">Gender</label>
							<div class="col-md-2">
								<select class="form-control input-sm" ng-model="selectedGender" ng-options="x.label for x in genders"></select>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-label">First name</label>
							<div class="col-md-8">
								<input type="text" class="form-control input-sm" ng-model="client.firstName" />
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-label">Last name</label>
							<div class="col-md-8">
								<input type="text" class="form-control input-sm" ng-model="client.lastName" />
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-label">Birth date</label>
							<span class="col-md-2">
								<input type="text" class="form-control input-sm" ng-model="client.address" />
							</span>
							<label class="col-md-1 control-label">City</label>
							<span class="col-md-2">
								<input type="text" class="form-control input-sm" ng-model="client.city" />
							</span>
							<label class="col-md-1 control-label">Country</label>
							<span class="col-md-2">
								<input type="text" class="form-control input-sm" ng-model="client.country" />
							</span>
						</div>
					</div>
					
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-label">Job</label>
							<div class="col-md-8">
								<input type="text" class="form-control input-sm" ng-model="client.post" />
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-label">Address</label>
							<div class="col-md-8">
								<input type="text" class="form-control input-sm" ng-model="client.address" />
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-label">Mobile phone</label>
							<span class="col-md-2">
								<input type="text" class="form-control input-sm" ng-model="client.mobilePhone" />
							</span>
							<label class="col-md-1 control-label">Land Phone</label>
							<span class="col-md-2">
								<input type="text" class="form-control input-sm" ng-model="client.landPhone" />
							</span>
							<label class="col-md-1 control-label">Fax</label>
							<span class="col-md-2">
								<input type="text" class="form-control input-sm" ng-model="client.fax" />
							</span>
						</div>
					</div>

					<div class="row">
						<div class="form-actions floatRight">
							<input type="button" ng-click="submit()" value="{{!client.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" />
							<button type="button" ng-click="reset()" class="btn btn-warning btn-sm">Reset form</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		
         <div class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">List of Users</span></div>
              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                              <th>First name</th>
                              <th>Last name</th>
                              <th>Address</th>
                              <th>Phone</th>
                              <th width="20%"></th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="c in clients">
                              <td><span ng-bind="c.firstName"></span></td>
                              <td><span ng-bind="c.lastName"></span></td>
                              <td><span ng-bind="c.address"></span></td>
                              <td><span ng-bind="c.mobilePhone"></span></td>
                              <td>
                              	<button type="button" ng-click="edit(c)" class="btn btn-success custom-width">Edit</button>
                              	<button type="button" ng-click="remove(c)" class="btn btn-danger custom-width">Remove</button>
                              </td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
	</div>
	
	<!-- <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js" ></script> -->
	<script src="<c:url value='/static/js/angular.min.1.5.8.js' />"></script>
	<script src="<c:url value='/static/js/app.js' />"></script>
	<script src="<c:url value='/static/js/service/clientService.js' />"></script>
	<script src="<c:url value='/static/js/controller/clientController.js' />"></script>
</body>
</html>