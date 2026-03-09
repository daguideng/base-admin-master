package cn.huanzi.qch.baseadmin.entity;

import java.util.Date;

public class Test_user {
    private String userId;

    private String loginName;

    private String userName;

    private String password;

    private String valid;

    private String limitedIp;

    private Date expiredTime;

    private Date lastChangePwdTime;

    private Date lastLoginTime;

    private String limitMultiLogin;

    private Date createTime;

    private Date updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid == null ? null : valid.trim();
    }

    public String getLimitedIp() {
        return limitedIp;
    }

    public void setLimitedIp(String limitedIp) {
        this.limitedIp = limitedIp == null ? null : limitedIp.trim();
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Date getLastChangePwdTime() {
        return lastChangePwdTime;
    }

    public void setLastChangePwdTime(Date lastChangePwdTime) {
        this.lastChangePwdTime = lastChangePwdTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLimitMultiLogin() {
        return limitMultiLogin;
    }

    public void setLimitMultiLogin(String limitMultiLogin) {
        this.limitMultiLogin = limitMultiLogin == null ? null : limitMultiLogin.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}