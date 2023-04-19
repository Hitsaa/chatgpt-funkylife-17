package com.chatgpt.funkylife.enums;

public enum ChatgptRole {
    USER ("user"),
    ADMIN ("admin");

    private String action;
    
    ChatgptRole(String action) {
        this.action = action;
    }

    public String getAction()
    {
        return this.action;
    }
}
