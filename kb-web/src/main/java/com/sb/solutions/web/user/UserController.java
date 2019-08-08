package com.sb.solutions.web.user;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import com.sb.solutions.web.user.dto.ChangePasswordDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.service.RoleService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.constant.EmailConstant.Template;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.date.DateManipulator;
import com.sb.solutions.core.utils.email.Email;
import com.sb.solutions.core.utils.email.MailSenderService;
import com.sb.solutions.core.utils.file.FileUploadUtils;

/**
 * @author Sunil Babu Shrestha on 12/27/2018
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Value("${bank.name}")
    private String bankName;

    private final UserService userService;
    private final RoleService roleService;
    private final MailSenderService mailSenderService;

    @Autowired
    public UserController(
        UserService userService,
        RoleService roleService,
        MailSenderService mailSenderService) {
        this.userService = userService;
        this.roleService = roleService;
        this.mailSenderService = mailSenderService;
    }

    @GetMapping(path = "/authenticated")
    public ResponseEntity<?> getAuthenticated() {
        return new RestResponseDto().successModel(userService.getAuthenticated());
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
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
    public ResponseEntity<?>
    sergetAll(@RequestBody Object searchDto,
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

            DateManipulator dateManipulator = new DateManipulator(new Date());
            user.setResetPasswordTokenExpiry(dateManipulator.addDays(1));
            User savedUser = userService.save(user);

            // mailing
            Email email = new Email();
            email.setTo(savedUser.getEmail());
            email.setResetPasswordLink(
                referer + "#/newPassword?username=" + username + "&reset=" + resetToken);
            email.setExpiry(savedUser.getResetPasswordTokenExpiry().toString());
            email.setBankName(this.bankName);
            mailSenderService.send(Template.RESET_PASSWORD, email);

            return new RestResponseDto().successModel(resetToken);
        }
    }

    @GetMapping(value = "/get-all-doc-transfer/{id}")
    public ResponseEntity<?> getAllForDocTransfer(@PathVariable Long id) {
        return new RestResponseDto().successModel(userService.getRoleWiseBranchWiseUserList(null,null,id));
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
                    User updatedUser = userService.updatePassword(u.getUsername(), u.getPassword());
                    Email email = new Email();
                    email.setTo(updatedUser.getEmail());
                    email.setToName(updatedUser.getName());
                    email.setBankName(this.bankName);
                    mailSenderService.send(Template.RESET_PASSWORD_SUCCESS, email);
                    return new RestResponseDto()
                        .successModel(updatedUser);
                }
            } else {
                return new RestResponseDto().failureModel("Initiate Reset Password Process first");
            }
        }
    }

    @PostMapping(value = "/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto passwordDto) {
        User user = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if(!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())){
            return new RestResponseDto().failureModel("Invalid Old Password");
        }
        userService.updatePassword(user.getName(), passwordDto.getNewPassword());
        return new RestResponseDto().successModel("Password Changed Successfully");
    }

}
