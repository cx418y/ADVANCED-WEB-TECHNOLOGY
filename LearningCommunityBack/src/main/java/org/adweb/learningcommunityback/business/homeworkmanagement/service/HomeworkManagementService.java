package org.adweb.learningcommunityback.business.homeworkmanagement.service;

import org.adweb.learningcommunityback.business.coursemanagement.dto.MemberDTO;
import org.adweb.learningcommunityback.business.homeworkmanagement.dto.AllVersionBriefDTO;
import org.adweb.learningcommunityback.business.homeworkmanagement.dto.HomeworkBriefDTO;
import org.adweb.learningcommunityback.business.homeworkmanagement.dto.VersionBriefDTO;
import org.adweb.learningcommunityback.business.homeworkmanagement.request.ReleaseHomeworkRequest;
import org.adweb.learningcommunityback.entity.db.*;
import org.adweb.learningcommunityback.entity.response.SimpleSuccessfulPostResponse;
import org.adweb.learningcommunityback.preensure.access.EnsureTeacherHasCourseAccess;
import org.adweb.learningcommunityback.preensure.access.EnsureUserHasCourseAccess;
import org.adweb.learningcommunityback.preensure.access.EnsureUserHasHomeworkAccess;
import org.adweb.learningcommunityback.preensure.access.EnsureUserHasSectionAccess;
import org.adweb.learningcommunityback.preensure.course.CourseCode;
import org.adweb.learningcommunityback.preensure.course.EnsureCourseExists;
import org.adweb.learningcommunityback.preensure.homework.EnsureHomeworkExists;
import org.adweb.learningcommunityback.preensure.homework.HomeworkID;
import org.adweb.learningcommunityback.preensure.section.EnsureSectionExists;
import org.adweb.learningcommunityback.preensure.section.SectionID;
import org.adweb.learningcommunityback.preensure.user.EnsureTeacherExists;
import org.adweb.learningcommunityback.preensure.user.EnsureUserExists;
import org.adweb.learningcommunityback.preensure.user.TeacherUsername;
import org.adweb.learningcommunityback.preensure.user.Username;
import org.adweb.learningcommunityback.repository.*;
import org.adweb.learningcommunityback.utils.EntityDTOMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class HomeworkManagementService {
    @Resource
    private HomeworkRepository homeworkRepository;

    @Resource
    private HomeworkVersionRepository homeworkVersionRepository;

    @Resource
    private HomeworkNotificationRepository homeworkNotificationRepository;

    @Resource
    private Take2Repository take2Repository;

    @Resource
    private CourseRepository courseRepository;

    @Resource
    private UserRepository userRepository;

    @EnsureTeacherExists
    @EnsureCourseExists
    @EnsureTeacherHasCourseAccess
    public SimpleSuccessfulPostResponse releaseHomework(@TeacherUsername String username,
                                                        @CourseCode String courseCode,
                                                        String sectionID, String title, String details, int oldMax, int oldTTL) {
        Homework homework = new Homework(courseCode, sectionID, title, details, oldMax, oldTTL);
        homeworkRepository.save(homework);

        //通知所有修这门课的用户，type为NEW_HOMEWORK
        List<User> members = getCourseMember(courseCode);
        String type = HomeworkNotification.NEW_HOMEWORK;
        String courseName = courseRepository.findByCourseCode(courseCode).getCourseName();
        String message = "《"+courseCode+": "+courseName+"》新建作业”"+title+"“";
        members.forEach(member -> {
            HomeworkNotification homeworkNotification = new HomeworkNotification(courseCode,member.getUsername(), type, message, homework.getHomeworkID(), title);
            homeworkNotificationRepository.save(homeworkNotification);
        });

        return new SimpleSuccessfulPostResponse("作业发布成功");
    }

    public List<User> getCourseMember(String courseCode){
        Course course = courseRepository.findByCourseCode(courseCode);
        List<Take2> take2s = take2Repository.findAllByCourseID(course.getId());
        List<User> users = new ArrayList<>();

        take2s.forEach(take2 -> {
            User user = userRepository.findById(take2.getUserID()).orElse(null);
            if (user != null) {
                users.add(user);
            }
        });

        return users;
    }

    @EnsureUserExists
    @EnsureSectionExists
    @EnsureUserHasSectionAccess
    public List<HomeworkBriefDTO> getAllHomeworkBriefing(@Username String username, @SectionID String sectionID) {
        List<Homework> homeworks = homeworkRepository.findAllBySectionID(sectionID);
        return homeworks.stream().map(HomeworkBriefDTO::from).toList();
    }

    @EnsureUserExists
    @EnsureHomeworkExists
    @EnsureUserHasHomeworkAccess
    public Homework getHomeworkDetails(@Username String username, @HomeworkID String homeworkID) {
        return homeworkRepository.findByHomeworkID(homeworkID);
    }

    @EnsureUserExists
    @EnsureHomeworkExists
    @EnsureUserHasHomeworkAccess
    public AllVersionBriefDTO getAllVersionBriefing(@Username String username, @HomeworkID String homeworkID) {
        HomeworkVersion latestVersion = homeworkVersionRepository.findByHomeworkIDAndExpireTimeNull(homeworkID);
        List<HomeworkVersion> oldVersions = homeworkVersionRepository.findAllByHomeworkIDAndExpireTimeNotNull(homeworkID);

        VersionBriefDTO latest = VersionBriefDTO.from(latestVersion);
        List<VersionBriefDTO> old = oldVersions.stream()
                .map(VersionBriefDTO::from).toList();

        return new AllVersionBriefDTO(latest, old);
    }

    @EnsureUserExists
    public HomeworkVersion getVersionDetails(@Username String username, String homeworkVersionID) {
        return homeworkVersionRepository.findByHomeworkVersionID(homeworkVersionID);
    }

    @EnsureUserExists
    @EnsureHomeworkExists
    @EnsureUserHasHomeworkAccess
    public SimpleSuccessfulPostResponse editHomework(@Username String username,
                                                     @HomeworkID String homeworkID,
                                                     String content) {
        /*处理流程
假设⽤户在T时刻正在做⼀个homeworkID为123的homework
1. ⽤户打开homeworkID为123的作业，编辑以后尝试提交作业（添加新版本）
2. 后台根据homeworkID，找到所有旧版的HomeworkVersion（expireTime不为null的）
3. 如果旧版的HomeworkVersion数量快要超出oldMax了，则删掉expireTime最近的那个旧版HomeworkVersion
4. 后台根据homeworkID，找到在T时之前的，最新版的HomeworkVersion（expireTime为null的），给它标上expireTime（expireTime=T+oldTTL，也就是这个version在T时刻由最新版变成旧版，这个version在T时刻后再经过oldTTL的时间后⾃动消失）
5. 插⼊⼀个新的HomeworkVersion，expireTime为null，表示它是最新版
这样，再次读取homework的内容时，根据expireTime=null的条件，就能读取到最新版的了

6. 通知所有修这门课的用户，type为HOMEWORK_UPDATED
         */
        Homework homework = homeworkRepository.findByHomeworkID(homeworkID);

        List<HomeworkVersion> homeworkVersions = homeworkVersionRepository.findAllByHomeworkIDAndExpireTimeNotNull(homeworkID);
        Integer oldMax = homework.getOldMax();
        int index = 0;
        //如果version数量即将超过oldMax
        if (homeworkVersions.size() == oldMax) {
            Date closetExpireTime = homeworkVersions.get(0).getExpireTime();
            int i = 0;
            for (HomeworkVersion homeworkVersion : homeworkVersions) {
                //如果找到了比目前closetExpireTime更早的version
                if (homeworkVersion.getExpireTime().before(closetExpireTime)) {
                    index = i;
                    closetExpireTime = homeworkVersion.getExpireTime();
                }
                i++;
            }
            HomeworkVersion homeworkVersion = homeworkVersions.get(index);
            homeworkVersionRepository.delete(homeworkVersion);
        }

        //给原来的最新版标上expireTime
        HomeworkVersion newestHV = homeworkVersionRepository.findByHomeworkIDAndExpireTimeNull(homeworkID);
        Integer oldTTL = homework.getOldTTL();
        Date date = new Date();
        Date afterDate = new Date(date.getTime() + oldTTL * 1000);
        if (newestHV!=null){
            newestHV.setExpireTime(afterDate);
        }

        //保存最新版本
        HomeworkVersion newHomeworkVersion = new HomeworkVersion(homeworkID, content, username, date, null);
        homeworkVersionRepository.save(newHomeworkVersion);

        //通知所有修这门课的用户，type为HOMEWORK_UPDATED
        String courseCode = homework.getCourseCode();
        List<User> members = getCourseMember(courseCode);
        String type = HomeworkNotification.HOMEWORK_UPDATED;
        String courseName = courseRepository.findByCourseCode(courseCode).getCourseName();
        String message = "《"+courseCode+": "+courseName+"》作业变更”"+homework.getTitle()+"“";
        members.forEach(member -> {
            HomeworkNotification homeworkNotification = new HomeworkNotification(courseCode, member.getUsername(), type, message, homeworkID, homework.getTitle());
            homeworkNotificationRepository.save(homeworkNotification);
        });

        return new SimpleSuccessfulPostResponse("编辑作业成功");
    }

    @EnsureUserExists
    @EnsureCourseExists
    @EnsureUserHasCourseAccess
    public List<HomeworkNotification> getAllHomeworkNotifications(@Username String username,
                                                                  @CourseCode String courseCode) {
        return homeworkNotificationRepository.findAllByCourseCodeAndUsername(courseCode, username);
    }

    @EnsureUserExists
    public SimpleSuccessfulPostResponse dismissNotification(@Username String username, String homeworkNotificationID) {
        HomeworkNotification homeworkNotification = homeworkNotificationRepository.findByHomeworkNotificationID(homeworkNotificationID);
        homeworkNotificationRepository.delete(homeworkNotification);
        return new SimpleSuccessfulPostResponse("忽略该通知成功");
    }
}
