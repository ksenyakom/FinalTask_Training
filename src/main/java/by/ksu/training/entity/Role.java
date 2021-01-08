package by.ksu.training.entity;

public enum  Role {

    ADMINISTRATOR,
    TRAINER,
    VISITOR;

    public Integer getIdentity() {
        return ordinal();
    }

    public static Role getByIdentity(Integer identity) {
        return Role.values()[identity];
    }

}
