/*
 * 
 * MEDoKyService:
 * A web service component for learner modelling and learning recommendations.
 * Copyright (C) 2015 KTI, TUGraz, Contact: simone.kopeinik@tugraz.at
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Affero General Public License for more details.  
 * For more information about the GNU Affero General Public License see <http://www.gnu.org/licenses/>.
 * 
 */


package at.tugraz.kmi.medokyservice;
import java.util.logging.Logger;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class ErrorLogNotifier {

	public static void error(String msg) {
		errorEmail(msg);
		errorLog(msg);
	}

	
	public static void errorEmail(String msg) {
		
		Email email = new SimpleEmail();
		email.setHostName("smtp.mail.yahoo.com");
		email.setSmtpPort(465);
		DefaultAuthenticator auth = new DefaultAuthenticator("fera.bluem", "12!!test");
		email.setAuthenticator(auth);
		email.setSSLOnConnect(true);
		try {
			email.setFrom("fera.bluem@yahoo.com");
			email.setSubject("[MEDoKyService] Error");
			email.setMsg(msg);
			email.addTo("simone.kopeinik@tugraz.at");
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
		
	}

	public static void errorLog(String msg) {
		Logger logger = Logger.getLogger(ErrorLogNotifier.class.getName());
		logger.warning(msg);
	}

	
	public static void main(String[] args) throws EmailException {
		errorLog("Mein Text");
	}

}
