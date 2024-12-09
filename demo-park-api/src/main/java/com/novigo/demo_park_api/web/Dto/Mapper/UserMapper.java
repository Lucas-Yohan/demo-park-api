package com.novigo.demo_park_api.web.Dto.Mapper;

import com.novigo.demo_park_api.web.Dto.UserCreateDto;
import com.novigo.demo_park_api.web.Dto.UserResponseDto;
import com.novigo.demo_park_api.Entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import java.util.List;
import java.util.stream.Collectors;


public class UserMapper {

    public static User toUser(UserCreateDto createDto){
        return new ModelMapper().map(createDto, User.class);
    }

    public static List<UserResponseDto> toListDto(List<User> user){
        return user.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public static UserResponseDto toDto(User user){
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(user, UserResponseDto.class);
    }


}
