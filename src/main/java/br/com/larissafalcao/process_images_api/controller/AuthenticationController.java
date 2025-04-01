package br.com.larissafalcao.process_images_api.controller;


import br.com.larissafalcao.process_images_api.controller.dto.request.AuthenticationDTO;
import br.com.larissafalcao.process_images_api.controller.openapi.AuthenticationControllerOpenApi;
import br.com.larissafalcao.process_images_api.persistence.domain.UserEntity;
import br.com.larissafalcao.process_images_api.security.TokenJwtData;
import br.com.larissafalcao.process_images_api.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationControllerOpenApi {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

	@PostMapping(path = "/api/login")
    public ResponseEntity authenticate(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(), authenticationDTO.getSenha());
        var authentication= authenticationManager.authenticate(authenticationToken);
        var tokenJwt = tokenService.generateToken((UserEntity)authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJwtData(tokenJwt));
    } 

}

