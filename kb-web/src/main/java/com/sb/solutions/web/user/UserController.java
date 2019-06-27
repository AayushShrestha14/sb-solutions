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
    public UserController(UserService userService, RoleService roleService,MailThreadService mailThreadService) {
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

    @PostMapping(value="/dismiss")
    public ResponseEntity<?> dismissBranchAndRole(@RequestBody User user) {
        return new RestResponseDto().successModel(userService.dismissAllBranchAndRole(user));
    }

    @GetMapping (value="/mail")
    public ResponseEntity<?> dismissBranchAndRoleMailtes() throws IOException, MessagingException {
        List<String> bcc = new ArrayList<>();
        List<String> attached = new ArrayList<>();
        attached.add("http://localhost:8086/images/userSignature/2019-04-21-22-06-33samriddi.jpg");
        bcc.add("rujnmahrzn@gmail.com");
        bcc.add("davidrana132@gmail.com");
        BaseHttp baseHttp = new BaseHttp();
        Email email = new Email();
        email.setBody(this.template());
        email.setTo("elwyncrestha@gmail.com");
        email.setBcc(bcc);
        email.setAttachment(attached);
        mailThreadService.sendMail(email);
        return new RestResponseDto().successModel(baseHttp);
    }


    public String template(){
        String email = "<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\n" +
                "<!-- START HEADER/BANNER -->\n" +
                "\n" +
                "\t\t<tbody><tr>\n" +
                "\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t<table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\" valign=\"top\" background=\"https://designmodo.com/demo/emailtemplate/images/header-background.jpg\" bgcolor=\"#66809b\" style=\"background-size:cover; background-position:top;height=\" 400\"\"=\"\">\n" +
                "\t\t\t\t\t\t\t<table class=\"col-600\" width=\"600\" height=\"400\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"40\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"line-height: 0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<img style=\"display:block; line-height:0px; font-size:0px; border:0px;\" src=\"https://designmodo.com/demo/emailtemplate/images/logo.png\" width=\"109\" height=\"103\" alt=\"logo\">\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"font-family: 'Raleway', sans-serif; font-size:37px; color:#ffffff; line-height:24px; font-weight: bold; letter-spacing: 7px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\tEMAIL <span style=\"font-family: 'Raleway', sans-serif; font-size:37px; color:#ffffff; line-height:39px; font-weight: 300; letter-spacing: 7px;\">TEMPLATE</span>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"font-family: 'Lato', sans-serif; font-size:15px; color:#ffffff; line-height:24px; font-weight: 300;\">\n" +
                "\t\t\t\t\t\t\t\t\t\tA creative email template for your email campaigns, promotions <br>and products across different email platforms.\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"50\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</tbody></table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\n" +
                "\n" +
                "<!-- END HEADER/BANNER -->\n" +
                "\n" +
                "\n" +
                "<!-- START 3 BOX SHOWCASE -->\n" +
                "\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t<table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-left:20px; margin-right:20px; border-left: 1px solid #dbd9d9; border-right: 1px solid #dbd9d9;\">\n" +
                "\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t<td height=\"35\"></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\" style=\"font-family: 'Raleway', sans-serif; font-size:22px; font-weight: bold; color:#2a3a4b;\">A creative way to showcase your content</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td height=\"10\"></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\" style=\"font-family: 'Lato', sans-serif; font-size:14px; color:#757575; line-height:24px; font-weight: 300;\">\n" +
                "\t\t\t\t\t\t\tPrepare some  flat icons of your choice. You can place your content below.<br>\n" +
                "\t\t\t\t\t\t\tMake sure it's awesome.\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t</tbody></table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t<table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-left: 1px solid #dbd9d9; border-right: 1px solid #dbd9d9; \">\n" +
                "\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t<td height=\"10\"></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t<table class=\"col3\" width=\"183\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"30\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<table class=\"insider\" width=\"133\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tbody><tr align=\"center\" style=\"line-height:0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img style=\"display:block; line-height:0px; font-size:0px; border:0px;\" src=\"https://designmodo.com/demo/emailtemplate/images/icon-about.png\" width=\"69\" height=\"78\" alt=\"icon\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"15\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: 'Raleway', Arial, sans-serif; font-size:20px; color:#2b3c4d; line-height:24px; font-weight: bold;\">About Us</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"10\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: 'Lato', sans-serif; font-size:14px; color:#757575; line-height:24px; font-weight: 300;\">Place some cool text here.</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"30\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t<table width=\"1\" height=\"20\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\">\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"20\" style=\"font-size: 0;line-height: 0;border-collapse: collapse;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<p style=\"padding-left: 24px;\">&nbsp;</p>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t<table class=\"col3\" width=\"183\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"30\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<table class=\"insider\" width=\"133\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tbody><tr align=\"center\" style=\"line-height:0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img style=\"display:block; line-height:0px; font-size:0px; border:0px;\" src=\"https://designmodo.com/demo/emailtemplate/images/icon-team.png\" width=\"69\" height=\"78\" alt=\"icon\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"15\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: 'Raleway', sans-serif; font-size:20px; color:#2b3c4d; line-height:24px; font-weight: bold;\">Our Team</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"10\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: 'Lato', sans-serif; font-size:14px; color:#757575; line-height:24px; font-weight: 300;\">Place some cool text here.</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"30\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t<table width=\"1\" height=\"20\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\">\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"20\" style=\"font-size: 0;line-height: 0;border-collapse: collapse;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<p style=\"padding-left: 24px;\">&nbsp;</p>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t<table class=\"col3\" width=\"183\" border=\"0\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"30\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<table class=\"insider\" width=\"133\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tbody><tr align=\"center\" style=\"line-height:0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img style=\"display:block; line-height:0px; font-size:0px; border:0px;\" src=\"https://designmodo.com/demo/emailtemplate/images/icon-portfolio.png\" width=\"69\" height=\"78\" alt=\"icon\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"15\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: 'Raleway',  sans-serif; font-size:20px; color:#2b3c4d; line-height:24px; font-weight: bold;\">Our Portfolio</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"10\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: 'Lato', sans-serif; font-size:14px; color:#757575; line-height:24px; font-weight: 300;\">Place some cool text here.</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"30\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</tbody></table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t\t<td height=\"5\"></td>\n" +
                "\t\t</tr>\n" +
                "\n" +
                "\n" +
                "<!-- END 3 BOX SHOWCASE -->\n" +
                "\n" +
                "\n" +
                "<!-- START AWESOME TITLE -->\n" +
                "\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t<table align=\"center\" class=\"col-600\" width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\" bgcolor=\"#2a3b4c\">\n" +
                "\t\t\t\t\t\t\t<table class=\"col-600\" width=\"600\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"33\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t<table class=\"col1\" width=\"183\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td height=\"18\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<img style=\"display:block; line-height:0px; font-size:0px; border:0px;\" class=\"images_style\" src=\"https://designmodo.com/demo/emailtemplate/images/icon-title.png\" alt=\"img\" width=\"156\" height=\"136\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t<table class=\"col3_one\" width=\"380\" border=\"0\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tbody><tr align=\"left\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: 'Raleway', sans-serif; font-size:20px; color:#f1c40f; line-height:30px; font-weight: bold;\">This title is definitely awesome! </td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"5\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr align=\"left\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: 'Lato', sans-serif; font-size:14px; color:#fff; line-height:24px; font-weight: 300;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\tThe use of flat colors in web design is more than a recent trend, it is a style designers have used for years to create impactful visuals. When you hear \"flat\", it doesn't mean boring it just means minimalist.\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"10\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr align=\"left\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"button\" style=\"border: 2px solid #fff;\" bgcolor=\"#2b3c4d\" width=\"30%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"30\" align=\"center\" style=\"font-family: 'Open Sans', Arial, sans-serif; font-size:13px; color:#ffffff;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" style=\"color:#ffffff;\">Read more</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"33\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</tbody></table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\n" +
                "\n" +
                "<!-- END AWESOME TITLE -->\n" +
                "\n" +
                "\n" +
                "<!-- START WHAT WE DO -->\n" +
                "\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t<table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-left:20px; margin-right:20px;\">\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t<tbody><tr>\n" +
                "\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t<table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\" border-left: 1px solid #dbd9d9; border-right: 1px solid #dbd9d9;\">\n" +
                "\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t<td height=\"50\"></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"right\">\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t<table class=\"col2\" width=\"287\" border=\"0\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"line-height:0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<img style=\"display:block; line-height:0px; font-size:0px; border:0px;\" class=\"images_style\" src=\"https://designmodo.com/demo/emailtemplate/images/icon-responsive.png\" width=\"169\" height=\"138\">\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t<table width=\"287\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" class=\"col2\" style=\"\">\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<table class=\"insider\" width=\"237\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tbody><tr align=\"left\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: 'Raleway', sans-serif; font-size:23px; color:#2a3b4c; line-height:30px; font-weight: bold;\">What we do?</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"5\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: 'Lato', sans-serif; font-size:14px; color:#7f8c8d; line-height:24px; font-weight: 300;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\tWe create responsive websites with modern designs and features for small businesses and organizations that are professionally developed and SEO optimized.\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</tbody></table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\n" +
                "\n" +
                "<!-- END WHAT WE DO -->\n" +
                "\n" +
                "\n" +
                "\n" +
                "<!-- START READY FOR NEW PROJECT -->\n" +
                "\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t<table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\" border-left: 1px solid #dbd9d9; border-right: 1px solid #dbd9d9;\">\n" +
                "\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t<td height=\"50\"></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t<td align=\"center\" bgcolor=\"#34495e\">\n" +
                "\t\t\t\t\t\t\t<table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"35\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"font-family: 'Raleway', sans-serif; font-size:20px; color:#f1c40f; line-height:24px; font-weight: bold;\">Ready for new project?</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"20\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"font-family: 'Lato', sans-serif; font-size:14px; color:#fff; line-height: 1px; font-weight: 300;\">\n" +
                "\t\t\t\t\t\t\t\t\t\tCheck out our price below.\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"40\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</tbody></table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\n" +
                "\n" +
                "<!-- END READY FOR NEW PROJECT -->\n" +
                "\n" +
                "\n" +
                "<!-- START PRICING TABLE -->\n" +
                "\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t<table width=\"600\" class=\"col-600\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\" border-left: 1px solid #dbd9d9; border-right: 1px solid #dbd9d9;\">\n" +
                "\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t<td height=\"50\"></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t<table style=\"border:1px solid #e2e2e2;\" class=\"col2\" width=\"287\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"40\" align=\"center\" bgcolor=\"#2b3c4d\" style=\"font-family: 'Raleway', sans-serif; font-size:18px; color:#f1c40f; line-height:30px; font-weight: bold;\">Small Business Website</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<table class=\"insider\" width=\"237\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"20\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr align=\"center\" style=\"line-height:0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: 'Lato', sans-serif; font-size:48px; color:#2b3c4d; font-weight: bold; line-height: 44px;\">$150</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"15\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"15\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table width=\"100\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"border: 2px solid #2b3c4d;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"30\" align=\"center\" style=\"font-family: 'Lato', sans-serif; font-size:14px; font-weight: 300; color:#2b3c4d;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" style=\"color: #2b3c4d;\">Learn More</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"30\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t<table width=\"1\" height=\"20\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\">\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"20\" style=\"font-size: 0;line-height: 0;border-collapse: collapse;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<p style=\"padding-left: 24px;\">&nbsp;</p>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t<table style=\"border:1px solid #e2e2e2;\" class=\"col2\" width=\"287\" border=\"0\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"40\" align=\"center\" bgcolor=\"#2b3c4d\" style=\"font-family: 'Raleway', sans-serif; font-size:18px; color:#f1c40f; line-height:30px; font-weight: bold;\">E-commerce Website</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<table class=\"insider\" width=\"237\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"20\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr align=\"center\" style=\"line-height:0px;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"font-family: 'Lato', sans-serif; font-size:48px; color:#2b3c4d; font-weight: bold; line-height: 44px;\">$289</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"30\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<table width=\"100\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\" border: 2px solid #2b3c4d;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"30\" align=\"center\" style=\"font-family: 'Lato', sans-serif; font-size:14px; font-weight: 300; color:#2b3c4d;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\" style=\"color: #2b3c4d;\">Learn More</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"10\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"20\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t</tbody></table>\n" +
                "\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</tbody></table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\n" +
                "\n" +
                "<!-- END PRICING TABLE -->\n" +
                "\n" +
                "\n" +
                "<!-- START FOOTER -->\n" +
                "\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t<table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\" border-left: 1px solid #dbd9d9; border-right: 1px solid #dbd9d9;\">\n" +
                "\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t<td height=\"50\"></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td align=\"center\" bgcolor=\"#34495e\" background=\"https://designmodo.com/demo/emailtemplate/images/footer.jpg\" height=\"185\">\n" +
                "\t\t\t\t\t\t\t<table class=\"col-600\" width=\"600\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"25\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" style=\"font-family: 'Raleway',  sans-serif; font-size:26px; font-weight: 500; color:#f1c40f;\">Follow us for some cool stuffs</td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td height=\"25\"></td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t</tbody></table><table align=\"center\" width=\"35%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "\t\t\t\t\t\t\t\t<tbody><tr>\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" width=\"30%\" style=\"vertical-align: top;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<a href=\"https://www.facebook.com/designmodo\" target=\"_blank\"> <img src=\"https://designmodo.com/demo/emailtemplate/images/icon-fb.png\"> </a>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" class=\"margin\" width=\"30%\" style=\"vertical-align: top;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t <a href=\"https://twitter.com/designmodo\" target=\"_blank\"> <img src=\"https://designmodo.com/demo/emailtemplate/images/icon-twitter.png\"> </a>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t<td align=\"center\" width=\"30%\" style=\"vertical-align: top;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<a href=\"https://plus.google.com/+Designmodo/posts\" target=\"_blank\"> <img src=\"https://designmodo.com/demo/emailtemplate/images/icon-googleplus.png\"> </a>\n" +
                "\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t</tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t</td></tr></tbody></table>\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</tbody></table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\n" +
                "<!-- END FOOTER -->\n" +
                "\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t</tbody></table>";

        return email;
    }

}
