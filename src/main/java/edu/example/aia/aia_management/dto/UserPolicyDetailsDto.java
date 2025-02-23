package edu.example.aia.aia_management.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class UserPolicyDetailsDto {
    private IntegerProperty userPolicyDetailsId;
    private IntegerProperty userId;
    private IntegerProperty policyId;

    public UserPolicyDetailsDto(int userPolicyDetailsId, int userId, int policyId) {
        this.userPolicyDetailsId = new SimpleIntegerProperty(userPolicyDetailsId);
        this.userId = new SimpleIntegerProperty(userId);
        this.policyId = new SimpleIntegerProperty(policyId);
    }

    public UserPolicyDetailsDto() {

    }

    public IntegerProperty userPolicyDetailsIdProperty() {
        return userPolicyDetailsId;
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public IntegerProperty policyIdProperty() {
        return policyId;
    }

    // Getters and setters for the properties
    public int getUserPolicyDetailsId() {
        return userPolicyDetailsId.get();
    }

    public void setUserPolicyDetailsId(int userPolicyDetailsId) {
        this.userPolicyDetailsId.set(userPolicyDetailsId);
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public int getPolicyId() {
        return policyId.get();
    }

    public void setPolicyId(int policyId) {
        this.policyId.set(policyId);
    }

    @Override
    public String toString() {
        return "UserPolicyDetailsDto{" +
                "userPolicyDetailsId=" + userPolicyDetailsId.get() +
                ", userId=" + userId.get() +
                ", policyId=" + policyId.get() +
                '}';
    }
}
