/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quickstore.login;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;


/**
 *
 * @author jmferreira1978@gmail.com
 */
public class AuthorizationListener implements PhaseListener {

    private static final String USER_LOGIN_OUTCOME = "login.xhtml";
    public static final String USER_SESSION_KEY = "user";

    @Override
    public void afterPhase(PhaseEvent event) {

        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isLoginPage = (currentPage.lastIndexOf(USER_LOGIN_OUTCOME) > -1);
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session == null) {
            NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
            nh.handleNavigation(facesContext, null, USER_LOGIN_OUTCOME);
        } else {
            Object currentUser = session.getAttribute(USER_SESSION_KEY);

            if (!isLoginPage && (currentUser == null || currentUser == "")) {
                NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
                nh.handleNavigation(facesContext, null, USER_LOGIN_OUTCOME);
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {

    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
