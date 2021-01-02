package by.ksu.training.entity;

import by.ksu.training.view.ResourceManager;

public enum  Role {

    ADMINISTRATOR("value.role.str1"),
    TRAINER("value.role.str3"),
    VISITOR("value.role.str2");


    private static ResourceManager manager = ResourceManager.INSTANCE;
    private String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return manager.getString(value);
    }

    public Integer getIdentity() {
        return ordinal();
    }

    public static Role getByIdentity(Integer identity) {
        return Role.values()[identity];
    }

    @Override
    public String toString() {
        return getValue();
    }
}
