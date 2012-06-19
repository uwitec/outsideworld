package com.util;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.HttpParams;

public class AllCookieSpecFactory implements CookieSpecFactory {

    @Override
    public CookieSpec newInstance(HttpParams arg0) {
        return new BrowserCompatSpec() {

            @Override
            public void validate(Cookie cookie, CookieOrigin origin)
                    throws MalformedCookieException {
                
            }
        };
    }
}
