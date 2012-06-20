package com.qq.group;

import java.util.List;
import com.dao.CommonDAO;
import com.model.policy.QQInfo;
import com.util.SpringFactory;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        CommonDAO commonDAO = SpringFactory.getBean("commonDAO");
        List<QQInfo> qqs = commonDAO.getAll(QQInfo.class);
        for (QQInfo qq : qqs) {
            Login l = new Login();
            PollGroupMessage p = new PollGroupMessage(l.login(qq.getUserName(), qq.getPassword()),qq.getElements());         
            p.start();
        }
        //PollGroupMessage p = new PollGroupMessage(l.login("38348450", "zhdwangtravelsky"));
    }
}
