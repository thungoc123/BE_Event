package EventManagement.Event.controller;

import EventManagement.Event.DTO.LoginRequestDTO;
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
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/Auth")
@CrossOrigin(origins = "*")
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


//    @PostMapping("/login")
//    public ResponseEntity<?> signIn(@RequestBody String email, @RequestBody String password) {
//
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
//        authenticationManager.authenticate(token);
//
//        Account account = feedbackService.findByEmail(email);
//        String roleUser = account.getRole().getRoleName();
//
//
//
//
//        String jwtToken = jwtHelper.generateToken(roleUser);
//
//        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setStatusCode(200);
//        baseResponse.setData(jwtToken);
//
//        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
//    }
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> signIn(@RequestBody LoginRequestDTO loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        try {
            // Thực hiện xác thực
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            // Xác thực thành công, lấy thông tin tài khoản
            Account account = feedbackService.findByEmail(email);
            String roleUser = account.getRole().getRoleName();

            // Tạo mã JWT
            String jwtToken = jwtHelper.generateToken(roleUser);

            // Chuẩn bị phản hồi thành công
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(200);
            baseResponse.setData(jwtToken);

            return ResponseEntity.ok(baseResponse);
        } catch (AuthenticationException e) {
            // Xử lý lỗi xác thực (đăng nhập không thành công)
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            baseResponse.setMessage("Invalid email or password");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(baseResponse);
        }
    }
}
