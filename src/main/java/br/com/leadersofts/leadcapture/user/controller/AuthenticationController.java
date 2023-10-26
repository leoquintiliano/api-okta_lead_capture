package br.com.leadersofts.leadcapture.user.controller;

import br.com.leadersofts.leadcapture.user.domain.User;
import br.com.leadersofts.leadcapture.user.record.AuthenticationRecord;
import br.com.leadersofts.leadcapture.user.record.LoginResponseDTO;
import br.com.leadersofts.leadcapture.user.record.RegisterDTO;
import br.com.leadersofts.leadcapture.user.repository.UserRepository;
import br.com.leadersofts.leadcapture.user.service.TokenService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationRecord data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = this.tokenService.generateToken( (User) auth.getPrincipal());

        var authorities = new ArrayList<String>();
       auth.getAuthorities().stream().map(accumulator -> authorities.add(accumulator.getAuthority())).collect(Collectors.toList());

        return ResponseEntity.ok(new LoginResponseDTO(token,((User)auth.getPrincipal()).getUsername(),authorities));

    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid RegisterDTO payload) {

        if(Objects.nonNull(this.userRepository.findByLogin(payload.login()))) {
            return ResponseEntity.badRequest().build();
        }
        var encryptedPassword = new BCryptPasswordEncoder().encode(payload.password());
        var newUser = new User(payload.login(),encryptedPassword,payload.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

}
