package com.cherish.demo.entity;

public class User {

    private long userId;
    private String userName;
    private String userEnglishName;
    private String userPassword;
    private long userSex;
    private String userBirth;
    private String userOriginProvince;
    private String userOriginCity;
    private String userOriginArea;
    private String userTel;
    private long userStatus;
    private String userEntryTime;
    private String userQuitTime;
    private long userDeptId;
    private long userPositionId;

    private Dept dept;
    private Position position;

    public User() {
        this.userBirth = "1980-01-01";
        this.userEntryTime = "1980-01-01";
        this.userQuitTime = "1980-01-01";
    }

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEnglishName() {
        return userEnglishName;
    }

    public void setUserEnglishName(String userEnglishName) {
        this.userEnglishName = userEnglishName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public long getUserSex() {
        return userSex;
    }

    public void setUserSex(long userSex) {
        this.userSex = userSex;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth.substring(0, 10);
    }

    public String getUserOriginProvince() {
        return userOriginProvince;
    }

    public void setUserOriginProvince(String userOriginProvince) {
        this.userOriginProvince = userOriginProvince;
    }

    public String getUserOriginCity() {
        return userOriginCity;
    }

    public void setUserOriginCity(String userOriginCity) {
        this.userOriginCity = userOriginCity;
    }

    public String getUserOriginArea() {
        return userOriginArea;
    }

    public void setUserOriginArea(String userOriginArea) {
        this.userOriginArea = userOriginArea;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public long getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(long userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserEntryTime() {
        return userEntryTime;
    }

    public void setUserEntryTime(String userEntryTime) {
        this.userEntryTime = userEntryTime.substring(0, 10);
    }

    public String getUserQuitTime() {
        return userQuitTime;
    }

    public void setUserQuitTime(String userQuitTime) {
        this.userQuitTime = userQuitTime.substring(0, 10);
    }

    public long getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptId(long userDeptId) {
        this.userDeptId = userDeptId;
    }

    public long getUserPositionId() {
        return userPositionId;
    }

    public void setUserPositionId(long userPositionId) {
        this.userPositionId = userPositionId;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
