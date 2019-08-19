package com.snapdeal.ums.server.services.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.core.DisableServiceRequest;
import com.snapdeal.core.EnableServiceRequest;
import com.snapdeal.core.GetServerBehaviourContextRequest;
import com.snapdeal.core.GetServerBehaviourContextResponse;
import com.snapdeal.core.ServerBehaviourContextSRO;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.entity.DisabledURL;
import com.snapdeal.ums.core.entity.ServerBehaviourContext;
import com.snapdeal.ums.dao.IServerBehaviourContextDao;
import com.snapdeal.ums.server.services.IServerBehaviourContextService;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.utils.UMSStringUtils;

/**
 * Service class to operate upon the server behaviour context.
 * 
 * @author ashish.saxena
 * 
 */

@Service
@Transactional
public class ServerBehaviourContextService implements
		IServerBehaviourContextService {

	// String defaultDisabledServerProfile;
	@Autowired
	private IUMSConvertorService umsConvertorService;

	@Autowired
	private IServerBehaviourContextDao serverBehaviourContextDao;

	private Set<String> temporarilyDisabledURLs = new HashSet<String>();

	public ServerBehaviourContextService() {
		super();

	}

	private ServerBehaviourContext serverBehaviourContext;

	public ServerBehaviourContext getServerBehaviour() {
		return serverBehaviourContext;
	}

	public void setServerBehaviour(ServerBehaviourContext serverBehaviourContext) {
		this.serverBehaviourContext = serverBehaviourContext;
	}

	private static final Logger log = LoggerFactory
			.getLogger(ServerBehaviourContextService.class);

	/**
	 * Loading default server behaviour context from cache
	 */

	public void loadServerBehaviourContext(String defaultServerBehaviourContext) {

		serverBehaviourContext = serverBehaviourContextDao
				.getServerBehaviourContext(defaultServerBehaviourContext);
		if (serverBehaviourContext == null) {
			log.info("The loaded ServerBehaviourContext does not exist in the DB: ");
		} else {
			// TODO:
			// ServerBehaviourContextSRO serverBehaviourContextSRO =
			// umsConvertorService
			// .getServerBehaviourContextSROFromEntity(serverBehaviourContext);
			// setServerBehaviour(serverBehaviourContext);
		}
	}

	/**
	 * 
	 * 
	 */

	public GetServerBehaviourContextResponse getCurrentServerBehaviourContext() {

		log.info("Getting current server behaviour context : ");
		GetServerBehaviourContextResponse serverContextResponse = new GetServerBehaviourContextResponse();
		// Set<DisabledURL> disabledURLs = new HashSet<DisabledURLSRO>();
		Set<DisabledURL> disabledURLs = serverBehaviourContext
				.getDisbaledURLs();
		if (disabledURLs == null) {
			log.info("No Disabled URL's exist");
		}
		ServerBehaviourContextSRO serverBehaviourContextSRO = umsConvertorService
				.getServerBehaviourContextSROFromEntity(getServerBehaviour());

		serverContextResponse
				.setServerBehaviourContextSRO(serverBehaviourContextSRO);

		return serverContextResponse;

	}

	/**
	 * @throws CloneNotSupportedException
	 */
	public GetServerBehaviourContextResponse enableService(
			EnableServiceRequest request) {

		GetServerBehaviourContextResponse behaviourContextResponse = new GetServerBehaviourContextResponse();

		String url = request.getUrl();

		if (UMSStringUtils.isNotNullNotEmpty(url)) {

			log.info("EnableServiceRequest for URL: " + url);

			Set<DisabledURL> disabledURLs = serverBehaviourContext
					.getDisbaledURLs();

			if (disabledURLs != null) {
				disabledURLs.remove(new DisabledURL(url.trim()));

			} else {
				log.error(ErrorConstants.NO_DISABLED_URL_CURRENT_SERVER_BEHAVIOUR_CONTEXT
						.getMsg());
				behaviourContextResponse.setSuccessful(false);
				behaviourContextResponse
						.setCode(String
								.valueOf(ErrorConstants.NO_DISABLED_URL_CURRENT_SERVER_BEHAVIOUR_CONTEXT
										.getCode()));
				behaviourContextResponse
						.setMessage(String
								.valueOf(ErrorConstants.NO_DISABLED_URL_CURRENT_SERVER_BEHAVIOUR_CONTEXT
										.getMsg()));

				return behaviourContextResponse;
			}
			serverBehaviourContext = serverBehaviourContextDao
					.updateContext(serverBehaviourContext);

			ServerBehaviourContextSRO serverBehaviourContextSRO = umsConvertorService
					.getServerBehaviourContextSROFromEntity(serverBehaviourContext);
			try {
				behaviourContextResponse
						.setServerBehaviourContextSRO((ServerBehaviourContextSRO) serverBehaviourContextSRO
								.clone());

			} catch (CloneNotSupportedException cloneNotSupportedException) {
				behaviourContextResponse.setSuccessful(false);
				behaviourContextResponse.setCode(String
						.valueOf(ErrorConstants.UNEXPECTED_ERROR.getCode()));
			}
		} else {
			log.error(ErrorConstants.DISABLED_URL_NOT_PRESENT.getMsg());
			behaviourContextResponse.setSuccessful(false);
			behaviourContextResponse
					.setCode(String
							.valueOf(ErrorConstants.DISABLED_URL_NOT_PRESENT
									.getCode()));
			behaviourContextResponse.setMessage(String
					.valueOf(ErrorConstants.DISABLED_URL_NOT_PRESENT.getMsg()));
		}

		return behaviourContextResponse;

	}

	public GetServerBehaviourContextResponse disableService(
			DisableServiceRequest request) {

		GetServerBehaviourContextResponse behaviourContextResponse = new GetServerBehaviourContextResponse();

		String url = request.getUrl();
		log.info("DisableServiceRequest for URL: " + url);

		if (UMSStringUtils.isNotNullNotEmpty(url)) {

			String trimmedUrl = url.trim();
			DisabledURL serviceURLToBeDisabled = new DisabledURL(trimmedUrl);

			Set<DisabledURL> currentDisabledURLs = serverBehaviourContext
					.getDisbaledURLs();

			if (currentDisabledURLs != null
					&& currentDisabledURLs.contains(serviceURLToBeDisabled)) {
				behaviourContextResponse.setSuccessful(false);
				behaviourContextResponse
						.setCode(String
								.valueOf(ErrorConstants.DISABLED_URL_PRESENT
										.getCode()));
				behaviourContextResponse.setMessage(String
						.valueOf(ErrorConstants.DISABLED_URL_PRESENT.getMsg()));

				return behaviourContextResponse;
			}

			else {

				DisabledURL disabledURL = serverBehaviourContextDao
						.getDisabledServiceURL(trimmedUrl);

				if (disabledURL != null) {

					serverBehaviourContext.addDisbaledURLs(disabledURL);
				}

				else {
					serverBehaviourContext
							.addDisbaledURLs(serviceURLToBeDisabled);
				}

				/**
				 * updating the server behaviour context in database
				 */

				serverBehaviourContext = serverBehaviourContextDao
						.updateContext(serverBehaviourContext);
			}
			ServerBehaviourContextSRO serverBehaviourContextSRO = umsConvertorService
					.getServerBehaviourContextSROFromEntity(serverBehaviourContext);
			try {
				behaviourContextResponse
						.setServerBehaviourContextSRO((ServerBehaviourContextSRO) serverBehaviourContextSRO
								.clone());
			} catch (CloneNotSupportedException e) {
				behaviourContextResponse.setSuccessful(false);
				behaviourContextResponse.setCode(String
						.valueOf(ErrorConstants.UNEXPECTED_ERROR.getCode()));
			}

		} else {

			log.error(ErrorConstants.DISABLED_URL_NOT_PRESENT.getMsg());
			behaviourContextResponse.setSuccessful(false);
			behaviourContextResponse
					.setCode(String
							.valueOf(ErrorConstants.DISABLED_URL_NOT_PRESENT
									.getCode()));
			behaviourContextResponse.setMessage(String
					.valueOf(ErrorConstants.DISABLED_URL_NOT_PRESENT.getMsg()));

		}
		return behaviourContextResponse;

	}

	/**
	 * Method to create new server behaviour context.
	 * 
	 * @input GetServerBehaviourContextRequest containing SRO
	 * @return GetServerBehaviourContextResponse
	 */

	public GetServerBehaviourContextResponse createServerBehaviourContext(
			GetServerBehaviourContextRequest behaviourContextRequest) {

		String name = null;

		boolean isSpecialCharPresent = false;

		log.info("Creating new ServerBehaviourContext:");

		GetServerBehaviourContextResponse behaviourContextResponse = new GetServerBehaviourContextResponse();

		/**
		 * Checks if disabled urls in the request are null, throws an error
		 * message in response if null
		 */

		if (behaviourContextRequest.getServerBehaviourContextSRO()
				.getDisbaledURLs() != null) {

			ServerBehaviourContextSRO serverBehaviourContextSRO = new ServerBehaviourContextSRO(
					behaviourContextRequest.getServerBehaviourContextSRO()
							.getName().trim(), behaviourContextRequest
							.getServerBehaviourContextSRO().getDisbaledURLs());

			name = serverBehaviourContextSRO.getName();

			if (name.matches(".*?['\\-—¿].*")) {
				log.info("name contains a special character");
				isSpecialCharPresent = true;
			}

			// Pattern p = Pattern.compile("[^a-z0-9 ]",
			// Pattern.CASE_INSENSITIVE);
			// Matcher m = p.matcher(name);
			// b = m.find();

			if (UMSStringUtils.isNotNullNotEmpty(serverBehaviourContextSRO
					.getName()) || (!isSpecialCharPresent)) {

				ServerBehaviourContext newServerBehaviourContext = new ServerBehaviourContext(
						serverBehaviourContextSRO.getName());
				newServerBehaviourContext = umsConvertorService
						.getServerBehaviourContextEntityfromSRO(serverBehaviourContextSRO);

				/**
				 * Checks if server behaviour context to be created already
				 * exists, throws an error message if exists.
				 */

				if (serverBehaviourContextDao
						.getServerBehaviourContext(newServerBehaviourContext
								.getName()) == null) {

					Set<DisabledURL> newDisabledURL = new HashSet<DisabledURL>();

					for (DisabledURL disabledURL : newServerBehaviourContext
							.getDisbaledURLs()) {

						if (disabledURL != null
								&& (disabledURL.getUrl() != null && (!disabledURL
										.getUrl().trim().equals("")))) {

							DisabledURL disabledURLfromDB = serverBehaviourContextDao
									.getDisabledServiceURL(disabledURL.getUrl()
											.trim());
							if (disabledURLfromDB != null) {

								newDisabledURL.add(disabledURLfromDB);

							} else {
								newDisabledURL.add(new DisabledURL(disabledURL
										.getUrl().trim()));
							}
						} else {

							log.error(ErrorConstants.DISABLED_URL_NOT_PRESENT
									.getMsg());
							behaviourContextResponse.setSuccessful(false);
							behaviourContextResponse
									.setCode(String
											.valueOf(ErrorConstants.DISABLED_URL_NOT_PRESENT
													.getCode()));
							behaviourContextResponse
									.setMessage(String
											.valueOf(ErrorConstants.DISABLED_URL_NOT_PRESENT
													.getMsg()));
							return behaviourContextResponse;

						}
					}

					newServerBehaviourContext.setDisbaledURLs(newDisabledURL);

					newServerBehaviourContext = serverBehaviourContextDao
							.updateContext(newServerBehaviourContext);

				}

				else {
					log.error(ErrorConstants.SERVER_BEHAVIOUR_CONTEXT_EXISTS
							.getMsg());
					behaviourContextResponse.setSuccessful(false);
					behaviourContextResponse
							.setCode(String
									.valueOf(ErrorConstants.SERVER_BEHAVIOUR_CONTEXT_EXISTS
											.getCode()));
					behaviourContextResponse
							.setMessage(String
									.valueOf(ErrorConstants.SERVER_BEHAVIOUR_CONTEXT_EXISTS
											.getMsg()));
					return behaviourContextResponse;

				}

				serverBehaviourContextSRO = umsConvertorService
						.getServerBehaviourContextSROFromEntity(newServerBehaviourContext);
			} else {

				log.error(ErrorConstants.INVALID_NAME.getMsg());
				behaviourContextResponse.setSuccessful(false);
				behaviourContextResponse.setCode(String
						.valueOf(ErrorConstants.INVALID_NAME.getCode()));
				behaviourContextResponse.setMessage(String
						.valueOf(ErrorConstants.INVALID_NAME.getMsg()));
				return behaviourContextResponse;

			}
			try {
				behaviourContextResponse
						.setServerBehaviourContextSRO((ServerBehaviourContextSRO) serverBehaviourContextSRO
								.clone());
			} catch (CloneNotSupportedException e) {
				behaviourContextResponse.setSuccessful(false);
				behaviourContextResponse.setCode(String
						.valueOf(ErrorConstants.UNEXPECTED_ERROR.getCode()));
			}
		} else {
			log.error(ErrorConstants.DISABLED_URL_NOT_PRESENT.getMsg());
			behaviourContextResponse.setSuccessful(false);
			behaviourContextResponse
					.setCode(String
							.valueOf(ErrorConstants.DISABLED_URL_NOT_PRESENT
									.getCode()));
			behaviourContextResponse.setMessage(String
					.valueOf(ErrorConstants.DISABLED_URL_NOT_PRESENT.getMsg()));

		}
		return behaviourContextResponse;

	}

	public boolean isServiceURLDisabled(String url) {
		Set<DisabledURL> disabledURLs = serverBehaviourContext
				.getDisbaledURLs();
		boolean isServiceURLDisabled = false;
		if (disabledURLs != null) {
			 
			
			boolean isServiceSBCDisabled = disabledURLs.contains(new DisabledURL(url));
			boolean isTemporarilyDisabled =false;
			
			if(temporarilyDisabledURLs!=null){
				isTemporarilyDisabled = temporarilyDisabledURLs.contains(url);
			}
			
			isServiceURLDisabled = isTemporarilyDisabled || isServiceSBCDisabled;
		}

		return isServiceURLDisabled;
	}
	
	public void temporarilyDisableServices(Collection<String> extTemporaryURLs){
		
		if(extTemporaryURLs!=null && (!extTemporaryURLs.isEmpty())){
		temporarilyDisabledURLs.addAll(extTemporaryURLs);
		}
	}
	
	public void enableTemporarilyDisabledServices(Collection<String> extTemporaryURLs){
		
		if(extTemporaryURLs!=null && (!extTemporaryURLs.isEmpty())){
		temporarilyDisabledURLs.removeAll(extTemporaryURLs);
		}
	}

	// @Override
	// public GetServerBehaviourContextResponse enableAllServices(
	// EnableAllServicesRequest enableAllServicesRequest) {
	// log.info("Enabling all SERVICES!");
	// // ServerBehaviourContext serverBehaviourContext=getServerBehaviour();
	// Set<DisabledURL> disabledURLs = serverBehaviourContext
	// .getDisbaledURLs();
	// if (disabledURLs != null && disabledURLs.size() > 0) {
	// disabledURLs.clear();
	// }
	//
	// GetServerBehaviourContextResponse behaviourContextResponse = new
	// GetServerBehaviourContextResponse();
	//
	// serverBehaviourContextDao.updateContext(serverBehaviourContext);
	//
	// ServerBehaviourContextSRO serverBehaviourContextSRO = umsConvertorService
	// .getServerBehaviourContextSROFromEntity(serverBehaviourContext);
	//
	// try {
	// behaviourContextResponse
	// .setServerBehaviourContextSRO((ServerBehaviourContextSRO)
	// serverBehaviourContextSRO
	// .clone());
	// } catch (CloneNotSupportedException e) {
	// behaviourContextResponse.setSuccessful(false);
	// behaviourContextResponse.setCode(String
	// .valueOf(ErrorConstants.UNEXPECTED_ERROR.getCode()));
	// }
	//
	// return behaviourContextResponse;
	//
	// }

}
