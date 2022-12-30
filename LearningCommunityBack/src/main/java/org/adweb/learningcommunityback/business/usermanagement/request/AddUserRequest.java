package org.adweb.learningcommunityback.business.usermanagement.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddUserRequest {
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "[0-9a-zA-Z]{6,12}", message = "用户名必须为6～10位的字母或数字")
    private String username;

    @NotBlank(message = "真实姓名不能为空")
    @Pattern(regexp = "[^\\x00-\\xff]{2,4}", message = "真实姓名必须是2～4个汉字")
    private String realname;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮件不符合格式")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "[0-9a-z]{32}", message = "密码格式不对，前端应该将密码用md5编码后，发到后端；且字母应该全部小写")
    private String password;

    @NotBlank(message = "类型不能为空")
    @Pattern(regexp = "(Student)|(TA)|(Teacher)", message = "类型必须是学生、助教、教师之一")
    private String type;

}
