package com.sjkz1.minetils.utils;

public enum SpecialMember {
    SJKZ1("SJKZ1","46448e1b402e42e0ad0e8a51ca5abe6a"),
    ToastKung("ToastKung","49d9c08b9a7940d3a35f95e1d3f2edbb"),
    SaruKunG_("NemuideSaru","8bfe2e86003b458b9bcc4063cbe8ecbd"),
    Faithz71("Faithz71","4445e7b87ad54ec2acf9b94bbb9d3646"),
    SwordAce("SwordAce","ba8abd58510e4a04b9ff00caa397c1b1");

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
