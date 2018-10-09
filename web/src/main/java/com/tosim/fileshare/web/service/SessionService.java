package com.tosim.fileshare.web.service;

import com.tosim.fileshare.common.domain.FsUser;
import org.apache.shiro.session.Session;

public interface SessionService {

    Boolean setSessionedUserBySession(Session session, FsUser newUser);

    Boolean removeSessionedUserBySession(Session session);

    FsUser getSessionedUserBySession(Session session);

    String getSessionedUserIdBySession(Session session);
}
