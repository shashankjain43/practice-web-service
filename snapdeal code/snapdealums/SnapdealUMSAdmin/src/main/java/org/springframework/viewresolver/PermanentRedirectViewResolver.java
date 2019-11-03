package org.springframework.viewresolver;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * Convenient subclass of {@link UrlBasedViewResolver} that supports {@link InternalResourceView} (i.e. Servlets and
 * JSPs) and subclasses such as {@link JstlView}.
 * <p>
 * The view class for all views generated by this resolver can be specified via {@link #setViewClass}. See
 * {@link UrlBasedViewResolver}'s javadoc for details. The default is {@link InternalResourceView}, or {@link JstlView}
 * if the JSTL API is present.
 */
public class PermanentRedirectViewResolver extends UrlBasedViewResolver {

    public static String REDIRECT_URL_PREFIX_PERMANENTLY = "redirect-permanent:";

    @Override
    protected View createView(String viewName, Locale locale) throws Exception {
        // If this resolver is not supposed to handle the given view,
        // return null to pass on to the next resolver in the chain.
        if (!canHandle(viewName, locale)) {
            return null;
        }
        // Check for special "redirect:" prefix.
        if (viewName.startsWith(REDIRECT_URL_PREFIX_PERMANENTLY)) {
            String redirectUrl = viewName.substring(REDIRECT_URL_PREFIX_PERMANENTLY.length());
            RedirectView rv = new RedirectView(redirectUrl, isRedirectContextRelative(), false);
            rv.setExposeModelAttributes(false);
            rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
            return rv;
        }
        return super.createView(viewName, locale);
    }

}