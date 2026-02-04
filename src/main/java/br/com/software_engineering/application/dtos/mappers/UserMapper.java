package br.com.software_engineering.application.dtos.mappers;


import br.com.software_engineering.application.domain.User;
import br.com.software_engineering.application.domain.valueObjects.Email;
import br.com.software_engineering.application.domain.valueObjects.EmailConverter;
import br.com.software_engineering.application.domain.valueObjects.Phone;
import br.com.software_engineering.application.domain.valueObjects.PhoneConverter;
import br.com.software_engineering.application.dtos.request.UserDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(UserDTO dto, @MappingTarget User user);

    default Email mapEmail(String value) {
        return new EmailConverter().convertToEntityAttribute(value);

    }

    default Phone mapPhone(String value) {
        return new PhoneConverter().convertToEntityAttribute(value);

    }

}
