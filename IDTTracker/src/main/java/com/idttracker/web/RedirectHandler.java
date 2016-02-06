package com.idttracker.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class RedirectHandler extends AbstractHandler {

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setContentLength(0);
		response.sendRedirect("/");
		baseRequest.setHandled(true);
		
	}

}
