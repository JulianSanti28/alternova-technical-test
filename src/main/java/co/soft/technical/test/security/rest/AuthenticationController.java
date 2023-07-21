package co.soft.technical.test.security.rest;


import co.soft.technical.test.security.dto.JwtDTO;
import co.soft.technical.test.security.dto.LoginDTO;
import co.soft.technical.test.security.dto.UserDTO;
import co.soft.technical.test.security.jwt.JwtProvider;
import co.soft.technical.test.security.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

    // ~ Dependencies
    // ====================================================================
    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final JwtProvider jwtProvider;

    // ~ Dependency Injection
    // ====================================================================
    public AuthenticationController(AuthenticationManager authenticationManager, IUserService userService, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    // ~ API Methods
    // ====================================================================
    @PostMapping("/new")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody UserDTO userDTO){
        Map<String, String> messages = new HashMap<>();
        if(userService.existsByUsername(userDTO.getUsername())){
            messages.put("error", "Ya existe un usuario con el nombre de usuario dado");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }
        if(userService.existsByEmail(userDTO.getEmail())){
            messages.put("error", "Ya existe un usuario con el email dado");
            return new ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }
        userService.save(userDTO);
        messages.put("correcto", "Usuario registrado exitosamente");
        return new ResponseEntity<>(messages,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (loginDTO.getUsername(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDTO jwtDTO = new JwtDTO(jwt,userDetails.getUsername(),userDetails.getAuthorities());
        return new ResponseEntity<>(jwtDTO,HttpStatus.OK);
    }
}
