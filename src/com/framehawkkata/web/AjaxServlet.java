package com.framehawkkata.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import com.framehawkkata.web.*;
import com.framehawkkata.model.*;
/**
 * Servlet implementation class AjaxServlet fronts top.jsp.  It has a BestSellerList once constructed.
 * It simply gets the List of Products from this object, and puts them into request scope.
 */
@WebServlet("/AjaxServlet")
public class AjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BestSellerList list;
    /**
     * Default constructor. 
     */
    public AjaxServlet() {
		List<Product> old = new ArrayList<Product>();
		old.add(new Product("iPad"));
		old.add(new Product("iPhone"));
		old.add(new Product("Nexus 7"));
		old.add(new Product("Foo"));
		old.add(new Product("Bar"));
		old.add(new Product("Baz"));
		old.add(new Product("Bop"));
		old.add(new Product("Stuff"));
		old.add(new Product("More Stuff"));
		old.add(new Product("Widgets"));
		List<List<Product>> forMock=new ArrayList<List<Product>>();
		forMock.add(old);
		list=new BestSellerList(new MockProductDAO(forMock));
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("bestSellers", list.getBestSellers());
		RequestDispatcher rd=getServletContext().getRequestDispatcher("/WEB-INF/ajax/top.jsp");
		rd.forward(request, response);
	}

}
