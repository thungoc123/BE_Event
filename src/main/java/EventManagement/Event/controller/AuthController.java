package EventManagement.Event.controller;

import EventManagement.Event.payload.DeleteResponse;
import EventManagement.Event.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-auth-resetpassword")
public class AuthController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        accountService.generateResetToken(request.getEmail());
        return ResponseEntity.ok(new DeleteResponse(" Reset token sent to your email"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        accountService.resetPassword(resetPasswordRequest.getToken(), resetPasswordRequest.getNewPassword());
        return ResponseEntity.ok(new DeleteResponse(" Password successfully reset"));
    }

    // Class để đọc yêu cầu quên mật khẩu từ body raw
    static class ForgotPasswordRequest {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    // Class để đọc yêu cầu reset mật khẩu từ body raw
    static class ResetPasswordRequest {
        private String token;
        private String newPassword;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}
