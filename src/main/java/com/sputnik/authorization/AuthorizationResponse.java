package com.sputnik.authorization;

public class AuthorizationResponse {
    private boolean admin;

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }
}
