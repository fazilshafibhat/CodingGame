package self_learning.coding_game.dtos;

import self_learning.coding_game.entities.RegistrationStatus;

public class UserRegistrationDto {
    private final String userName;
    private final String contestName;
    private final RegistrationStatus registrationStatus;

    public UserRegistrationDto(String userName, String contestName, RegistrationStatus registrationStatus) {
        this.userName = userName;
        this.contestName = contestName;
        this.registrationStatus = registrationStatus;
    }

    public String getUserName() {
        return userName;
    }

    public String getContestName() {
        return contestName;
    }

    public RegistrationStatus registrationStatus() {
        return registrationStatus;
    }

    @Override
    public String toString() {
        return "RegisterContestDto [contestName=" + contestName + ", registrationStatus=" + registrationStatus
                + ", userName=" + userName + "]";
    }

}
