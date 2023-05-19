package com.farhad.example.websocket.todo.auth;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("auth")
public class AuthController {
    
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam(name = "username", defaultValue = "anonymous") String username) {
        ResponseCookie cookie =  ResponseCookie
                                    .from("username", username)
                                    .path("/")
                                    .build();
        return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .location(URI.create("/api"))
                        .build();
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseCookie cookie =  ResponseCookie
                                    .from("username", null)
                                    .path("/")
                                    .maxAge(0)
                                    .build();
        return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .location(URI.create("/api"))
                        .build();
    }

//     @RequestMapping(value = "/login", method = RequestMethod.GET)
//     public String login(@RequestParam(name = "username", defaultValue = "anonymous") String username, HttpServletResponse response) throws IOException {
//         response.addCookie(new Cookie("username", username));
//         response.sendRedirect("/api");
//         log.info("login {}", username);
//         return "redirect:/api";
//     }

//     @RequestMapping(value = "/logout", method = RequestMethod.GET)
//     public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
//         String username = null;
//         for (Cookie cookie : request.getCookies()) {
//             if(cookie.getName().equals("username")) {
//                 username = cookie.getValue();
//                 cookie.setValue(null);
//                 cookie.setMaxAge(0);
//                 cookie.setPath(request.getContextPath());
//                 response.addCookie(cookie);
//             }
//         }
//         response.sendRedirect("/api");
//         log.info("logout {}", username);
//         return "redirect:/api";
//     }
}
