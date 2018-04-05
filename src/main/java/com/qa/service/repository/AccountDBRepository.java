package com.qa.service.repository;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.Collection;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.qa.domain.Account;
import com.qa.util.JSONUtil;

@Transactional(SUPPORTS) //Sets transactional tag for the class
@Default //Sets this class as the default implementation of the 'AccountRepository' interface
public class AccountDBRepository implements AccountRepository {

	@PersistenceContext(unitName = "primary")
	private EntityManager manager; //Request entity manager (the object that talks to the database), configured in persistence.xml

	@Inject //Tells the bean container to take over the lifecycle management of this object
	private JSONUtil util; //Provides with new object instance

	@Override
	public String getAllAccounts() {
		Query query = manager.createQuery("Select a FROM Account a"); //Query object
		Collection<Account> accounts = (Collection<Account>) query.getResultList(); //Save result of query to variable
		return util.getJSONForObject(accounts); //Return variable as JSON objects
	}

	@Override
	@Transactional(REQUIRED) //Overrides @Transactional(SUPPORTS) tag
	public String createAccount(String accout) {
		Account anAccount = util.getObjectForJSON(accout, Account.class); //Get JSON object of the account to insert into database
		manager.persist(anAccount); //Insert into database
		return "{\"message\": \"account has been sucessfully added\"}"; //Return success message
	}

	@Override
	@Transactional(REQUIRED) //Overrides @Transactional(SUPPORTS) tag
	public String updateAccount(Long id, String accountToUpdate) {
		Account updatedAccount = util.getObjectForJSON(accountToUpdate, Account.class); //Get JSON object of the account to update with
		Account accountFromDB = findAccount(id); //Find the account we want to update
		if (accountToUpdate != null) { //If account exists
			accountFromDB = updatedAccount; //Overwrite local object
			manager.merge(accountFromDB); //Insert local object into database
		}
		return "{\"message\": \"account sucessfully updated\"}"; //Return success message
	}

	@Override
	@Transactional(REQUIRED)
	public String deleteAccount(Long id) {
		Account accountInDB = findAccount(id);
		if (accountInDB != null) {
			manager.remove(accountInDB);
		}
		return "{\"message\": \"account sucessfully deleted\"}"; //Return success message
	}

	private Account findAccount(Long id) {
		return manager.find(Account.class, id);
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}

}