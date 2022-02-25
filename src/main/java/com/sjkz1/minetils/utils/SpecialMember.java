package com.sjkz1.minetils.utils;

public enum SpecialMember {
    TornNgern("TornNgern","46448e1b402e42e0ad0e8a51ca5abe6a"),
    ToastKung("ToastKung","49d9c08b9a7940d3a35f95e1d3f2edbb");

    private final String name;
    private final String uuid;
    public static final SpecialMember[] VALUES = SpecialMember.values();

    SpecialMember(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }
    public String getUuid()
    {
        return this.uuid;
    }
    public String getName()
    {
        return this.name;
    }
}
