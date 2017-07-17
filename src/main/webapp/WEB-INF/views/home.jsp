<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Minesweeper</title>
</head>
<body>
<h1>
	Minesweeper  
</h1>
<p>${result }</p>
<!-- Create bottoms for each to send information to the HomeController -->
	<table>
		<c:forEach items="${mineSweeper }" var="i" varStatus="status1">
			<tr>
				<c:forEach items="${i }" var="j" varStatus="status2">
					<c:if test="${showingMS[status1.index][status2.index] }">
						<td>${j }</td>
					</c:if>
					<c:if test="${showingMS[status1.index][status2.index] == false}">
						<td>
							<form name="choice" action="http://localhost:8080/minesweeper/" method="get">
								<input type="hidden" name="index1" value="${status1.index}">
								<input type="hidden" name="index2" value="${status2.index}">

								<input type="submit" class="inside" value="Choice">
							</form>
						</td>
					</c:if>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
