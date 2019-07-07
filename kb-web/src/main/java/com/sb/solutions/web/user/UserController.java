package com.sb.solutions.web.user;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.service.RoleService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.email.Email;
import com.sb.solutions.core.utils.email.MailThreadService;
import com.sb.solutions.core.utils.email.template.ResetPassword;
import com.sb.solutions.core.utils.file.FileUploadUtils;

/**
 * @author Sunil Babu Shrestha on 12/27/2018
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final MailThreadService mailThreadService;

    @Autowired
    public UserController(UserService userService, RoleService roleService,
        MailThreadService mailThreadService) {
        this.userService = userService;
        this.roleService = roleService;
        this.mailThreadService = mailThreadService;
    }

    @GetMapping(path = "/authenticated")
    public ResponseEntity<?> getAuthenticated() {
        return new RestResponseDto().successModel(userService.getAuthenticated());
    }


    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        user.toString();
        return new RestResponseDto().successModel(userService.save(user));
    }

    @PostMapping(value = "/uploadFile")
    public ResponseEntity<?> saveUserFile(@RequestParam("file") MultipartFile multipartFile,
        @RequestParam("type") String type) {
        return FileUploadUtils.uploadFile(multipartFile, type);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "/list")
    public ResponseEntity<?> getAll(@RequestBody SearchDto searchDto,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(userService.findAllPageable(searchDto, PaginationUtils
                .pageable(page, size)));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.")})
    @PostMapping(value = "listByRole")
    public ResponseEntity<?> getUserByRole(@RequestBody Collection<Role> roles,
        @RequestParam("page") int page, @RequestParam("size") int size) {
        return new RestResponseDto()
            .successModel(userService.findByRole(roles, PaginationUtils.pageable(page, size)));
    }

    @GetMapping(value = "listRole")
    public ResponseEntity<?> getRoleList() {
        return new RestResponseDto().successModel(roleService.findAll());
    }

    @GetMapping(value = "/{id}/users")
    public ResponseEntity<?> getUserList(@PathVariable Long id) {
        return new RestResponseDto().successModel(userService.findByRoleAndBranch(id, null));
    }


    @RequestMapping(method = RequestMethod.GET, path = "/statusCount")
    public ResponseEntity<?> getUserStatusCount() {
        return new RestResponseDto().successModel(userService.userStatusCount());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/csv")
    public ResponseEntity<?> csv(@RequestBody SearchDto searchDto) {
        return new RestResponseDto().successModel(userService.csv(searchDto));
    }

    @PostMapping(value = "/dismiss")
    public ResponseEntity<?> dismissBranchAndRole(@RequestBody User user) {
        return new RestResponseDto().successModel(userService.dismissAllBranchAndRole(user));
    }

    /*@GetMapping (value="/mail")
    public ResponseEntity<?> mail() throws IOException, MessagingException {
        List<String> bcc = new ArrayList<>();
        List<String> attached = new ArrayList<>();
        attached.add("http://localhost:8086/images/userSignature/2019-04-21-22-06-33samriddi.jpg");
        bcc.add("rujnmahrzn@gmail.com");
        bcc.add("davidrana132@gmail.com");
        BaseHttp baseHttp = new BaseHttp();
        Email email = new Email();
        email.setBody(SampleTemplate.sampleTemplate());
        email.setTo("elwyncrestha@gmail.com");
        email.setBcc(bcc);
        email.setAttachment(attached);
        mailThreadService.sendMail(email);
        return new RestResponseDto().successModel(baseHttp);
    }*/

    @GetMapping(value = "/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("username") String username,
        @RequestHeader("referer") final String referer) {
        User user = userService.getByUsername(username);
        if (user == null) {
            return new RestResponseDto().failureModel("User not found!");
        } else {
            String resetToken = UUID.randomUUID().toString() + new Date().getTime();
            user.setModifiedBy(user.getId());
            user.setResetPasswordToken(resetToken);

            Date expiry = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(expiry);
            c.add(Calendar.DATE, 1);
            expiry = c.getTime();
            user.setResetPasswordTokenExpiry(expiry);
            User savedUser = userService.save(user);

            // mailing
            Email email = new Email();
            email.setSubject("Reset Password");
            email.setBody(ResetPassword.resetPasswordTemplate(savedUser.getUsername(),
                referer + "#/newPassword?username=" + username + "&reset=" + resetToken,
                savedUser.getResetPasswordTokenExpiry().toString()));
            email.setTo(savedUser.getEmail());
            mailThreadService.sendMail(email);

            return new RestResponseDto().successModel(resetToken);
        }
    }

    @PostMapping(value = "/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody User u) {
        User user = userService.getByUsername(u.getUsername());
        if (user == null) {
            return new RestResponseDto().failureModel("User not found!");
        } else {
            if (user.getResetPasswordToken() != null) {
                if (user.getResetPasswordTokenExpiry().before(new Date())) {
                    return new RestResponseDto()
                        .failureModel("Reset Token has been expired already");
                } else {
                    return new RestResponseDto()
                        .successModel(userService.updatePassword(u.getUsername(), u.getPassword()));
                }
            } else {
                return new RestResponseDto().failureModel("Initiate Reset Password Process first");
            }
        }
    }
}
