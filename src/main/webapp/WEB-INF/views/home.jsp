<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Minesweeper</title>
	<link rel="stylesheet" href="resources/style.css">
</head>
<body>
<h1>
	Minesweeper  
</h1>
<p class="result">${result }</p>
<!-- Create bottoms for each to send information to the HomeController -->
	<table>
		<c:forEach items="${mineSweeper }" var="i" varStatus="status1">
			<tr>
				<c:forEach items="${i }" var="j" varStatus="status2">
					<c:if test="${showingMS[status1.index][status2.index] }">
						<c:if test="${j == 10}">
							<td>
								<img src="http://www.rw-designer.com/icon-image/3078-48x48x32.png" 
								alt="Mine" style="width:16px;height:16px;">
							</td>
						</c:if>
						<c:if test="${j < 10 && j > 0}">
							<td>
								${j }
							</td>
						</c:if>
						<c:if test="${j == 0}">
							<td>
							</td>
						</c:if>
					</c:if>
					<c:if test="${showingMS[status1.index][status2.index] == false}">
						<td>
							<form name="choice" class="mines" action="http://localhost:8080/minesweeper/" method="get">
								<input type="hidden" name="index1" value="${status1.index}">
								<input type="hidden" name="index2" value="${status2.index}">

								<input type="submit" class="inside" value="">
							</form>
						</td>
					</c:if>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
	<p>
		<c:if test="${result2 == 0}">
			<form name="startOver" class="insideForms" action="http://localhost:8080/minesweeper/" method="get">
				<input type="hidden" name="startOver" value="True">
				
				<input type="submit" class="insideForms2" value="Start Over">
			</form>
		</c:if>
		<c:if test="${result2 == 1}">
			<form name="nextLevel" class="insideForms" action="http://localhost:8080/minesweeper/" method="get">
				<input type="hidden" name="nextLevel" value="True">
				
				<input type="submit" class="insideForms2" value="Next Level">
			</form>
		</c:if>
	</p>
</body>
</html>
