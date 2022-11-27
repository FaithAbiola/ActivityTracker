package com.faithabiola.activity_tracker.services.serviceImpl;

import com.faithabiola.activity_tracker.dto.UserResponseDto;
import com.faithabiola.activity_tracker.dto.UserSignUpDto;
import com.faithabiola.activity_tracker.entities.User;
import com.faithabiola.activity_tracker.repositories.UserRepository;
import com.faithabiola.activity_tracker.services.UserService;
import com.faithabiola.activity_tracker.utils.ApiResponse;
import com.faithabiola.activity_tracker.utils.ResponseManager;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final ResponseManager responseManager;

    @Override
    public ApiResponse saveUser(UserSignUpDto signUpDto) {

        //To set the error message to be sent when a field is not provided
        if (signUpDto.getFirstName() == null)
            return responseManager.error("First Name is required");
        else if (signUpDto.getLastName() == null)
            return responseManager.error("Last Name is required");
        else if (signUpDto.getGender() == null)
            return responseManager.error("Gender is required");
        else if (signUpDto.getEmail() == null)
            return responseManager.error("Email is required");
        else if (signUpDto.getPassword() == null)
            return responseManager.error("Password is required");

        // Checking database if email already exist
        boolean emailExist = userRepository.existsByEmail(signUpDto.getEmail());
        if(emailExist)
            return responseManager.error("Email already exists");

        // Using UserSignupDto object to create User Object
        User user = mapper.map(signUpDto, User.class);

        userRepository.save(user);

        // use the user object to create UserResponseDto Object
        UserResponseDto userResponseDto = mapper.map(user, UserResponseDto.class);
        return responseManager.success(userResponseDto);
    }

    @Override
    public ApiResponse loginUser(String email, String password) {
        User user =  userRepository.getUserByEmailAndPassword(email, password);
        if(user != null){
            UserResponseDto userLoggedIn = mapper.map(user, UserResponseDto.class);
            return responseManager.success(userLoggedIn);
        }
        else
            return responseManager.error("User not found");

    }

}
