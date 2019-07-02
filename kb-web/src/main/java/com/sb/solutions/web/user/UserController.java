package com.sb.solutions.web.user;

import com.sb.solutions.api.basehttp.BaseHttp;
import com.sb.solutions.api.rolePermissionRight.entity.Role;
import com.sb.solutions.api.rolePermissionRight.service.RoleService;
import com.sb.solutions.api.user.entity.User;
import com.sb.solutions.api.user.service.UserService;
import com.sb.solutions.core.dto.RestResponseDto;
import com.sb.solutions.core.dto.SearchDto;
import com.sb.solutions.core.utils.PaginationUtils;
import com.sb.solutions.core.utils.email.Email;
import com.sb.solutions.core.utils.email.MailThreadService;
import com.sb.solutions.core.utils.file.FileUploadUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


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
    public UserController(UserService userService, RoleService roleService, MailThreadService mailThreadService) {
        this.userService = userService;
        this.roleService = roleService;
        this.mailThreadService = mailThreadService;
    }

    private String signaturePath = null;
    private String profiePath = null;

    @GetMapping(path = "/authenticated")
    public ResponseEntity<?> getAuthenticated() {
        return new RestResponseDto().successModel(userService.getAuthenticated());
    }


    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        if (profiePath != null) {
            user.setProfilePicture(profiePath);
            profiePath = null;
        }
        if (signaturePath != null) {
            user.setSignatureImage(signaturePath);
            signaturePath = null;
        }
        user.toString();
        return new RestResponseDto().successModel(userService.save(user));
    }

    @PostMapping(value = "/uploadFile")
    public ResponseEntity<?> saveUserFile(@RequestParam("file") MultipartFile multipartFile,
                                          @RequestParam("type") String type) {
        System.out.println();
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

    @GetMapping(value = "/mail")
    public ResponseEntity<?> dismissBranchAndRoleMailtes() throws IOException, MessagingException {
        List<String> bcc = new ArrayList<>();
        List<String> attached = new ArrayList<>();
        attached.add("http://localhost:8086/images/userSignature/2019-04-21-22-06-33samriddi.jpg");
        bcc.add("rujnmahrzn@gmail.com");
        BaseHttp baseHttp = new BaseHttp();
        Email email = new Email();
        email.setTo("rujnmahrzn@gmail.com");
        email.setBcc(bcc);
        email.setSubject("Reset Password");
        email.setAttachment(attached);
        mailThreadService.sendMail(email);
        return new RestResponseDto().successModel(baseHttp);
    }


}
