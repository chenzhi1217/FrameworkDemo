package com.framework.data.gen;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Copyright (C), 2017/11/30 91账单
 * Author: chenzhi
 * Email: chenzhi@91zdan.com
 * Description:
 */
@Entity
public class Student {
    
    @Id
    private long userId;

    @Property(nameInDb = "NAME")
    private String userName;
    
    @Property(nameInDb = "PHONE")
    private String phone;

    @Generated(hash = 1268329715)
    public Student(long userId, String userName, String phone) {
        this.userId = userId;
        this.userName = userName;
        this.phone = phone;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
