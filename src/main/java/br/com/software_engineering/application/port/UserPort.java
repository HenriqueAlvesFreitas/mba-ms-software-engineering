package br.com.software_engineering.application.port;

import br.com.software_engineering.application.domain.User;
import br.com.software_engineering.application.dtos.request.UserDTO;
import br.com.software_engineering.application.dtos.response.PageResponse;
import br.com.software_engineering.infra.web.RestResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserPort {

    public ResponseEntity<RestResponse<User>> save(UserDTO dto);
    public ResponseEntity<RestResponse<PageResponse<User>>> getAll(String name, String email, String phone, int page);
    public ResponseEntity<RestResponse<User>> updateUser(UserDTO user, UUID id);
    public ResponseEntity<RestResponse<Object>> deleteUser(UUID id);
}
