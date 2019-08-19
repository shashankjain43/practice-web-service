/**
 * 
 */
package com.snapdeal.web.security;

import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;

/**
 * This class will take care of possibility of <strong>Session<strong> hijacking or fixation. It collects the
 * information from the old session and invalidates the existing session causing the session with the sent by 
 * intruder to get invalidated.
 * <p>
 * After invalidating the old the information from the old cart is copied in to a new session for the same request which
 * will keep the cart or any information already existing to the old session intact.
 * 
 * @author fanendra
 */
public class SessionIdentifierValidator {
    /**
     * Copies the data from the existing session to a new session and invalidates the old session.
     * <p>
     * This method must be called after successful authentication of the user.
     * 
     * @param request
     * @return
     * @throws AuthenticationException
     */
    public static HttpSession changeSessionIdentifier(HttpServletRequest request) {
        HttpSession oldSession = request.getSession(false);
        Map<String, Object> temp = new ConcurrentHashMap<String, Object>();
        //if session exists copy the data from it and invalidate it
        if (oldSession != null) {
            Enumeration e = oldSession.getAttributeNames();
            while (e != null && e.hasMoreElements()) {
                String name = (String) e.nextElement();
                Object value = oldSession.getAttribute(name);
                temp.put(name, value);
            }
            oldSession.invalidate();
        }
        HttpSession newSession = request.getSession();
        for (Map.Entry<String, Object> stringObjectEntry : temp.entrySet()) {
            newSession.setAttribute(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        return newSession;
    }
}
