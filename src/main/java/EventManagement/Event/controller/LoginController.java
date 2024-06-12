package EventManagement.Event.controller;

import EventManagement.Event.entity.Account;
import EventManagement.Event.payload.BaseResponse;
import EventManagement.Event.repository.AccountRepository;
import EventManagement.Event.service.FeedbackService;
import EventManagement.Event.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Auth")
public class LoginController {
//    Spel : Spring boot express language
//    @PreAuthorize() : Khi gọi link thì kiểm luôn quyền trước khi chạy logic code
//    @PostAuthorize() : Chạy logic code xong mới kiểm tra quyền


    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private FeedbackService feedbackService;


    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestParam String email, @RequestParam String password) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(token);

        Account account = feedbackService.findByEmail(email);
        String roleUser = account.getRole().getRoleName();




        String jwtToken = jwtHelper.generateToken(roleUser);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(jwtToken);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
