package com.app.muhammadgamal.swapy.SwapData;

public class SwapRequestOff {

    private String toID, toName, toOffDate, toOffDay, toPhone, toAccount, toCompanyBranch, toEmail, toImageUrl, toPreferredOff;
    private String fromID, fromName, fromOffDate, fromOffDay, fromPhone, fromAccount, fromCompanyBranch, fromEmail, fromImageUrl, fromPreferredOff;
    private int accepted, approved; //true = 1, false = 0, waiting = -1

    public SwapRequestOff(){

    }

    public SwapRequestOff(String toID,
                          String toName,
                          String toOffDate,
                          String toOffDay,
                          String toPhone,
                          String toAccount,
                          String toCompanyBranch,
                          String toEmail,
                          String toImageUrl,
                          String toPreferredOff,
                          String fromID,
                          String fromName,
                          String fromOffDate,
                          String fromOffDay,
                          String fromPhone,
                          String fromAccount,
                          String fromCompanyBranch,
                          String fromEmail,
                          String fromImageUrl,
                          String fromPreferredOff,
                          int accepted,
                          int approved) {
        this.toID = toID;
        this.toName = toName;
        this.toOffDate = toOffDate;
        this.toOffDay = toOffDay;
        this.toPhone = toPhone;
        this.toAccount = toAccount;
        this.toCompanyBranch = toCompanyBranch;
        this.toEmail = toEmail;
        this.toImageUrl = toImageUrl;
        this.toPreferredOff = toPreferredOff;
        this.fromID = fromID;
        this.fromName = fromName;
        this.fromOffDate = fromOffDate;
        this.fromOffDay = fromOffDay;
        this.fromPhone = fromPhone;
        this.fromAccount = fromAccount;
        this.fromCompanyBranch = fromCompanyBranch;
        this.fromEmail = fromEmail;
        this.fromImageUrl = fromImageUrl;
        this.fromPreferredOff = fromPreferredOff;
        this.accepted = accepted;
        this.approved = approved;
    }

    public String getToID() {
        return toID;
    }

    public void setToID(String toID) {
        this.toID = toID;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToOffDate() {
        return toOffDate;
    }

    public void setToOffDate(String toOffDate) {
        this.toOffDate = toOffDate;
    }

    public String getToOffDay() {
        return toOffDay;
    }

    public void setToOffDay(String toOffDay) {
        this.toOffDay = toOffDay;
    }

    public String getToPhone() {
        return toPhone;
    }

    public void setToPhone(String toPhone) {
        this.toPhone = toPhone;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getToCompanyBranch() {
        return toCompanyBranch;
    }

    public void setToCompanyBranch(String toCompanyBranch) {
        this.toCompanyBranch = toCompanyBranch;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getToImageUrl() {
        return toImageUrl;
    }

    public void setToImageUrl(String toImageUrl) {
        this.toImageUrl = toImageUrl;
    }

    public String getToPreferredOff() {
        return toPreferredOff;
    }

    public void setToPreferredOff(String toPreferredOff) {
        this.toPreferredOff = toPreferredOff;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromOffDate() {
        return fromOffDate;
    }

    public void setFromOffDate(String fromOffDate) {
        this.fromOffDate = fromOffDate;
    }

    public String getFromOffDay() {
        return fromOffDay;
    }

    public void setFromOffDay(String fromOffDay) {
        this.fromOffDay = fromOffDay;
    }

    public String getFromPhone() {
        return fromPhone;
    }

    public void setFromPhone(String fromPhone) {
        this.fromPhone = fromPhone;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getFromCompanyBranch() {
        return fromCompanyBranch;
    }

    public void setFromCompanyBranch(String fromCompanyBranch) {
        this.fromCompanyBranch = fromCompanyBranch;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromImageUrl() {
        return fromImageUrl;
    }

    public void setFromImageUrl(String fromImageUrl) {
        this.fromImageUrl = fromImageUrl;
    }

    public String getFromPreferredOff() {
        return fromPreferredOff;
    }

    public void setFromPreferredOff(String fromPreferredOff) {
        this.fromPreferredOff = fromPreferredOff;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }
}
