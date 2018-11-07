package neverless.repository.util;

import neverless.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InjectionUtilImpl implements InjectionUtil {

    @Autowired
    private SessionUtil sessionUtil;

    @Override
    public SessionUtil getSessionUtil() {
        return sessionUtil;
    }
}
