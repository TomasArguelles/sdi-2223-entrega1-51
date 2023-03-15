package com.uniovi.sdi2223entrega1n.entities;

public enum LogType {
    PET("PET"), LOGIN_EX("LOG-EX"), LOGIN_ERR("LOG-ERR"),
    ALTA("ALTA"), LOGOUT("LOGOUT");

    private String name;

    LogType(String name) {
        this.name = name;
    }
}
