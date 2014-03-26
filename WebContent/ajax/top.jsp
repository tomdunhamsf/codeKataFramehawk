<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${!empty requestScope.bestSellers}">
	<div id="bestSellerTitle">Best Sellers</div>
	<c:forEach items="${requestScope.bestSellers}" var="item">
		<div class="item">${item.name}</div>
	</c:forEach>
</c:if>