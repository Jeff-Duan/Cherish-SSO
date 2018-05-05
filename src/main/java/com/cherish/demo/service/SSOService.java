package com.cherish.demo.service;

import com.cherish.demo.dao.SSODao;
import com.cherish.demo.entity.User;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class SSOService {

    private static final Logger logger = LoggerFactory.getLogger(SSOService.class);
    public static final String RESULT_SUCCESS = "SUCCESS";
    public static final String RESULT_ERROR = "ERROR";

    @Autowired
    SSODao SSODao;

    @Autowired
    Gson gson;

    public String login(String name, String password, HttpSession session) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return RESULT_ERROR;
        }
        Optional<User> optional = Optional.ofNullable(SSODao.selectOneUser(new User(name, password)));
        if (optional.isPresent()) {
            //记录登陆日志
            logger.info("用户[" + name + "]" + "于" + new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").format(new Date()) + "登陆Cherish-Hui(SSO).");
            //保存Session
            session.setAttribute("User", gson.toJson(optional.get()));
            return RESULT_SUCCESS;
        }
        return RESULT_ERROR;
    }

}
