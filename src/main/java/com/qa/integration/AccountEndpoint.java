package com.qa.integration;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.qa.service.business.AccountService;

@Path("/account") //http://localhost:8080/finalaccount/rest/account
public class AccountEndpoint {

	@Inject
	private AccountService service;

	@Path("/json") //http://localhost:8080/finalaccount/rest/account/json
	@GET //Method is activated on GET requests
	@Produces({ "application/json" })
	public String getAllAccounts() {
		return service.getAllAccounts();
	}

	@Path("/json") //http://localhost:8080/finalaccount/rest/account/json
	@POST //Method is activated on POST requests
	@Produces({ "application/json" })
	public String addAccount(String account) {
		return service.addAccount(account);
	}

	@Path("/json/{id}") //http://localhost:8080/finalaccount/rest/account/json/<account id to be updated>
	@PUT //Method is activated on PUT requests
	@Produces({ "application/json" })
	public String updateAccount(@PathParam("id") Long id, String account) {
		return service.updateAccount(id, account);
	}

	@Path("/json/{id}") //http://localhost:8080/finalaccount/rest/account/json/<account id to be deleted>
	@DELETE //Method is activated on DELETE requests
	@Produces({ "application/json" })
	public String deleteAccount(@PathParam("id") Long id) {
		return service.deleteAccount(id);

	}

	public void setService(AccountService service) {
		this.service = service;
	}

}