package org.API.dtos;

import java.util.List;
import java.util.ArrayList;

import org.API.entities.CarEntity;
import org.API.entities.UserEntity;

//Utility class for mapping between DTOs and entities
public class DTOMapper {

    //Converts UserEntity to UserDTO
    public static UserDTO toUserDTO(UserEntity user){
        if (user == null) return null;
        return new UserDTO(user.getId(), user.getUsername());
    }

    //Converts a list of UserEntity to a list of UserDTO
    public static List<UserDTO> toUserDTOList(List<UserEntity> users){
        List<UserDTO> dtoList = new ArrayList<>();
        for (UserEntity user : users) {
            dtoList.add(toUserDTO(user));
        }
        return dtoList;
    }

    //Converts CarEntity to CarDTO
    public static CarDTO toCarDTO(CarEntity car){
        if (car == null) return null;
        
        Long userId = null;
        if (car.getUser() != null) {
            userId = car.getUser().getId();
        }
        return new CarDTO(car.getId(), car.getManufacturer(), car.getYear(), car.getModel(), car.getMilage(), userId);
    }

    //Converts a list of CarEntity to a list of CarDTO
    public static List<CarDTO> toCarDTOList(List<CarEntity> cars){
        List<CarDTO> dtoList = new ArrayList<>();
        for (CarEntity car : cars) {
            dtoList.add(toCarDTO(car));
        }
        return dtoList;
    }
}
