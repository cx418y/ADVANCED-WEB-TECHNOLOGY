package org.adweb.learningcommunityback.business.essayproblemmanagement.service;

import org.adweb.learningcommunityback.business.coursemanagement.dto.CourseConfig;
import org.adweb.learningcommunityback.business.essayproblemmanagement.dto.*;
import org.adweb.learningcommunityback.business.essayproblemmanagement.request.HandOutRequest;
import org.adweb.learningcommunityback.entity.db.*;
import org.adweb.learningcommunityback.entity.response.SimpleSuccessfulPostResponse;
import org.adweb.learningcommunityback.exception.AdWebBaseException;
import org.adweb.learningcommunityback.page.PageQueryResult;
import org.adweb.learningcommunityback.preensure.access.EnsureStudentHasCourseAccess;
import org.adweb.learningcommunityback.preensure.access.EnsureTeacherHasCourseAccess;
import org.adweb.learningcommunityback.preensure.access.EnsureUserHasCourseAccess;
import org.adweb.learningcommunityback.preensure.course.CourseCode;
import org.adweb.learningcommunityback.preensure.course.EnsureCourseExists;
import org.adweb.learningcommunityback.preensure.user.*;
import org.adweb.learningcommunityback.repository.*;
import org.adweb.learningcommunityback.utils.EntityDTOMapper;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EssayProblemManagementService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private CourseRepository courseRepository;

    @Resource
    private EssayProblemRepository essayProblemRepository;

    @Resource
    private EssayProblemToBeDoneRepository essayProblemToBeDoneRepository;

    @Resource
    private EssayProblemToBePeerGradedRepository essayProblemToBePeerGradedRepository;

    @Resource
    private EssayProblemToBeTeacherTAGradedRepository essayProblemToBeTeacherTAGradedRepository;

    @Resource
    private EssayProblemFinishedRepository essayProblemFinishedRepository;

    @Resource
    private PeerGradingRecordRepository peerGradingRecordRepository;

    @Resource
    private Take2Repository take2Repository;

    @EnsureTeacherExists
    @EnsureCourseExists
    @EnsureTeacherHasCourseAccess
    public CourseConfig courseConfig(@CourseCode String courseCode,
                                     @TeacherUsername String username) {
        Course course = courseRepository.findByCourseCode(courseCode);

        CourseConfig courseConfig = new CourseConfig();
        courseConfig.setAllowStudentAddProblem(course.getAllowStudentAddProblem());
        return courseConfig;
    }

    @EnsureTeacherExists
    @EnsureCourseExists
    @EnsureTeacherHasCourseAccess
    public SimpleSuccessfulPostResponse configMyCourse(@TeacherUsername String username,
                                                       @CourseCode String courseCode,
                                                       boolean allowStudentAddProblem) {
        Course course = courseRepository.findByCourseCode(courseCode);
        course.setAllowStudentAddProblem(allowStudentAddProblem);
        courseRepository.save(course);
        return new SimpleSuccessfulPostResponse("修改课程设置成功");
    }

    @EnsureCourseExists
    @EnsureStudentExists
    @EnsureStudentHasCourseAccess
    public SimpleSuccessfulPostResponse studentAddEssayProblem(@StudentUsername String username,
                                                               @CourseCode String courseCode,
                                                               String title,
                                                               String referenceAnswer) {
        Course course = courseRepository.findByCourseCode(courseCode);
        if (course.getAllowStudentAddProblem()) {
            EssayProblem essayProblem = new EssayProblem(title, referenceAnswer, username, courseCode);
            essayProblemRepository.save(essayProblem);
            return new SimpleSuccessfulPostResponse("出题成功");
        } else {
            throw new AdWebBaseException(IllegalArgumentException.class, "STUDENT_PERMISSION_DENIED", String.format("学生%s没有权限出题", username));
        }
    }

    @EnsureTeacherExists
    @EnsureCourseExists
    @EnsureTeacherHasCourseAccess
    public SimpleSuccessfulPostResponse teacherAddEssayProblem(@TeacherUsername String username,
                                                               @CourseCode String courseCode,
                                                               String title,
                                                               String referenceAnswer) {
        EssayProblem essayProblem = new EssayProblem(title, referenceAnswer, username, courseCode);
        essayProblemRepository.save(essayProblem);
        return new SimpleSuccessfulPostResponse("出题成功");
    }

    @EnsureTeacherExists
    @EnsureCourseExists
    @EnsureTeacherHasCourseAccess
    public List<EssayProblem> getCourseEssayProblems(@CourseCode String courseCode,
                                                     @TeacherUsername String username) {
        return essayProblemRepository.findAllByCourseCode(courseCode);
    }

    @EnsureStudentExists
    @EnsureCourseExists
    @EnsureStudentHasCourseAccess
    public PageQueryResult<EssayProblemToBeDoneDTO> getCourseEssayProblemsToBeDone(@CourseCode String courseCode,
                                                                                   @StudentUsername String username,
                                                                                   int pageNum,
                                                                                   int pageSize) {
        pageNum--;

        Page<EssayProblemToBeDone> essayProblemsToBeDone = essayProblemToBeDoneRepository.findAllByCourseCodeAndUsernameOrderByDateDesc(courseCode, username, PageRequest.of(pageNum, pageSize));

        int totalPage = essayProblemsToBeDone.getTotalPages();

        var data = essayProblemsToBeDone
                .stream()
                .map(essayProblemToBeDone -> {
                    EssayProblem essayProblem = essayProblemRepository.findByEssayProblemID(essayProblemToBeDone.getEssayProblemID());
                    return EssayProblemToBeDoneDTO.from(essayProblemToBeDone, essayProblem);
                }).toList();

        return PageQueryResult.of(totalPage, data);
    }

    //学生答题，后端判断是老师/同学评价并加入到对应仓库中
    @EnsureStudentExists
    public SimpleSuccessfulPostResponse studentDoEssayProblem(@StudentUsername String username,
                                                              String essayProblemToBeDoneID,
                                                              String answer) {
        EssayProblemToBeDone essayProblemToBeDone = essayProblemToBeDoneRepository.findByEssayProblemToBeDoneID(essayProblemToBeDoneID);

        if (essayProblemToBeDone == null) {
            throw new AdWebBaseException(IllegalArgumentException.class, "ESSAY_PROBLEM_NOT_EXISTS", String.format("题目ID：%s不存在", essayProblemToBeDoneID));
        }

        //如果是同学互评的话，则加入EssayProblemToBePeerGradedRepository仓库中
        String essayProblemID = essayProblemToBeDone.getEssayProblemID();
        String courseCode = essayProblemToBeDone.getCourseCode();
        double maxPoint = essayProblemToBeDone.getPoint();

        if (essayProblemToBeDone.getPeerGrading()) {
            EssayProblemToBePeerGraded essayProblemToBePeerGraded =
                    new EssayProblemToBePeerGraded(
                            essayProblemToBeDoneID,
                            essayProblemID,
                            maxPoint,
                            new ArrayList<>(),
                            username,
                            answer,
                            courseCode,
                            new Date());
            essayProblemToBePeerGradedRepository.save(essayProblemToBePeerGraded);
        } else {
            EssayProblemToBeTeacherTAGraded essayProblemToBeTeacherTAGraded =
                    new EssayProblemToBeTeacherTAGraded(
                            essayProblemToBeDoneID,
                            essayProblemID,
                            maxPoint,
                            username,
                            answer,
                            courseCode,
                            new Date());
            essayProblemToBeTeacherTAGradedRepository.save(essayProblemToBeTeacherTAGraded);
        }

        //删掉EssayProblemToBeDone
        essayProblemToBeDoneRepository.delete(essayProblemToBeDone);

        return new SimpleSuccessfulPostResponse("答题成功");
    }

    @EnsureTeacherExists
    public SimpleSuccessfulPostResponse handoutEssayProblems(@TeacherUsername String username,
                                                             List<HandOutRequest> request) {
        for (HandOutRequest handOutRequest : request) {
            String essayProblemID = handOutRequest.getEssayProblemID();
            boolean peerGrading = handOutRequest.isPeerGrading();
            double point = handOutRequest.getPoint();

            EssayProblem essayProblem = essayProblemRepository.findByEssayProblemID(essayProblemID);
            String courseCode = essayProblem.getCourseCode();
            String fromUsername = essayProblem.getFromUsername();

            Course course = courseRepository.findByCourseCode(courseCode);
            List<Take2> take2s = take2Repository.findAllByCourseID(course.getId());

            //分发给选这门课的所有学生（除出题人）
            for (Take2 t : take2s) {
                String userID = t.getUserID();
                User user = userRepository.findById(userID).orElse(null);

                /*
                todo 注意：只有学生需要答题
                 */

                if (user != null && !Objects.equals(user.getUsername(), fromUsername) && Objects.equals(user.getRole(), User.ROLE_STUDENT)) {
                    String studentName = user.getUsername();

                    EssayProblemToBeDone essayProblemToBeDone = new EssayProblemToBeDone(essayProblemID, studentName, courseCode, peerGrading, point, new Date());
                    essayProblemToBeDoneRepository.save(essayProblemToBeDone);
                }
            }
        }
        return new SimpleSuccessfulPostResponse("分发成功");
    }

    @EnsureStudentExists
    @EnsureCourseExists
    @EnsureStudentHasCourseAccess
    public PageQueryResult<EssayProblemToBePeerGradedDTO> studentGetEssayProblemToBePeerGraded(@CourseCode String courseCode,
                                                                                               @StudentUsername String username,
                                                                                               int pageSize,
                                                                                               int pageNum) {
        pageNum--;

        //由于需要在应用层过滤，因此这里不直接在竹居裤用分页查询
        //而是在应用层过滤以后再分页
        List<EssayProblemToBePeerGraded> allEssayProblemsToBePeerGraded =
                essayProblemToBePeerGradedRepository.findAllByCourseCodeOrderByDateDesc(courseCode);

        var allDTOs = allEssayProblemsToBePeerGraded
                .stream()
                .filter(epToBePeerGraded -> {
                    //过滤掉用户已经评过的题
                    boolean hasGraded = null != peerGradingRecordRepository
                            .findByEssayProblemToBeDoneIDAndUsername(epToBePeerGraded.getEssayProblemToBeDoneID(), username);

                    boolean selfAnswered = Objects.equals(username, epToBePeerGraded.getAnswererUsername());

                    return !hasGraded && !selfAnswered;

                }).map(epToBePeerGraded -> {
                    var essayProblem = essayProblemRepository.findByEssayProblemID(epToBePeerGraded.getEssayProblemID());
                    return EssayProblemToBePeerGradedDTO.from(epToBePeerGraded, essayProblem);
                }).toList();

        //分页
        PagedListHolder<EssayProblemToBePeerGradedDTO> page = new PagedListHolder<>(allDTOs);
        page.setPage(pageNum);
        page.setPageSize(pageSize);

        return PageQueryResult.of(page.getPageCount(), page.getPageList());

//        for (EssayProblemToBePeerGraded essayProblemToBePeerGraded : allEssayProblemsToBePeerGraded) {
//            String answerUsername = essayProblemToBePeerGraded.getAnswerUsername();
//            String essayProblemToBeDoneID = essayProblemToBePeerGraded.getEssayProblemToBeDoneID();
//            //判断是否互评过
//            PeerGradingRecord peerGradingRecord = peerGradingRecordRepository.findByEssayProblemToBeDoneIDAndUsername(essayProblemToBeDoneID, username);
//            if (peerGradingRecord == null) {
//                String essayProblemToBePeerGradedID = essayProblemToBePeerGraded.getEssayProblemToBePeerGradedID();
//                Double maxPoint = essayProblemToBePeerGraded.getMaxPoint();
//                List<Double> gradedPointList = essayProblemToBePeerGraded.getGradedPointList();
//                String answer = essayProblemToBePeerGraded.getAnswer();
//                String essayProblemID = essayProblemToBePeerGraded.getEssayProblemID();
//                EssayProblem essayProblem = essayProblemRepository.findByEssayProblemID(essayProblemID);
//                String title = essayProblem.getTitle();
//                String referenceAnswer = essayProblem.getReferenceAnswer();
//
//                EssayProblemFinishedDTO result = new EssayProblemFinishedDTO(essayProblemToBePeerGradedID, title, maxPoint, gradedPointList,
//                        answerUsername, answer, referenceAnswer, courseCode);
//                results.add(result);
//            }
//        }

//        //进行分页
//        int count = results.size(); // 记录总数
//        int pageCount; // 页数
//        if (count % pageSize == 0) {
//            pageCount = count / pageSize;
//        } else {
//            pageCount = count / pageSize + 1;
//        }
//        int fromIndex; // 开始索引
//        int toIndex; // 结束索引
//        if (pageCount != pageNum + 1) {
//            fromIndex = pageNum * pageSize;
//            toIndex = fromIndex + pageSize;
//            if (toIndex > count) {
//                fromIndex = (pageNum - 1) * pageSize;
//                toIndex = count;
//            }
//        } else {
//            fromIndex = pageNum * pageSize;
//            toIndex = count;
//        }
        //return results.subList(fromIndex, toIndex);
    }

    @EnsureCourseExists
    @EnsureUserExists
    @EnsureUserHasCourseAccess
    public PageQueryResult<EssayProblemToBeTeacherTAGradedDTO> teacherTAGetEssayProblemToBeTeacherTAGraded(@CourseCode String courseCode,
                                                                                                           @Username String username,
                                                                                                           int pageSize,
                                                                                                           int pageNum) {
        pageNum--;

        Page<EssayProblemToBeTeacherTAGraded>
                essayProblemsToBeTeacherTAGraded = essayProblemToBeTeacherTAGradedRepository.findAllByCourseCodeOrderByDateDesc(courseCode, PageRequest.of(pageNum, pageSize));

        int totalPage = essayProblemsToBeTeacherTAGraded.getTotalPages();

        EntityDTOMapper mapper = EntityDTOMapper
                .builder()
                .setDtoClass(EssayProblemToBeTeacherTAGradedDTO.class)
                .setEntityClass(EssayProblemToBeTeacherTAGraded.class)
                .build();

        var data = essayProblemsToBeTeacherTAGraded
                .stream()
                .map(essayProblemToBeTeacherTAGraded -> {
                    var essayProblem = essayProblemRepository.findByEssayProblemID(essayProblemToBeTeacherTAGraded.getEssayProblemID());
                    return EssayProblemToBeTeacherTAGradedDTO.from(essayProblemToBeTeacherTAGraded, essayProblem);
                }).toList();

        return PageQueryResult.of(totalPage, data);

//        List<EssayProblemToBeTeacherTAGradedDTO> results = new ArrayList<>();
//        for (EssayProblemToBeTeacherTAGraded essayProblemToBeTeacherTAGraded : essayProblemsToBeTeacherTAGraded) {
//            String essayProblemToBeTeacherTAGradedID = essayProblemToBeTeacherTAGraded.getEssayProblemToBeTeacherTAGradedID();
//            Double maxPoint = essayProblemToBeTeacherTAGraded.getMaxPoint();
//            String answerUsername = essayProblemToBeTeacherTAGraded.getAnswererUsername();
//            String answer = essayProblemToBeTeacherTAGraded.getAnswer();
//            String essayProblemID = essayProblemToBeTeacherTAGraded.getEssayProblemID();
//            EssayProblem essayProblem = essayProblemRepository.findByEssayProblemID(essayProblemID);
//            String title = essayProblem.getTitle();
//            String referenceAnswer = essayProblem.getReferenceAnswer();
//            EssayProblemToBeTeacherTAGradedDTO result = new EssayProblemToBeTeacherTAGradedDTO(essayProblemToBeTeacherTAGradedID, title, maxPoint,
//                    answerUsername, answer, referenceAnswer, courseCode);
//            results.add(result);
//        }
//
//        return results;
    }

    @EnsureStudentExists
    @Transactional
    public synchronized SimpleSuccessfulPostResponse peerGradeEssayProblem(@StudentUsername String username,
                                                                           String essayProblemToBePeerGradedID,
                                                                           Double point) {
        EssayProblemToBePeerGraded essayProblemToBePeerGraded = essayProblemToBePeerGradedRepository.findByEssayProblemToBePeerGradedID(essayProblemToBePeerGradedID);
        if (essayProblemToBePeerGraded == null) {
            throw new AdWebBaseException(IllegalArgumentException.class, "ESSAY_PROBLEM_TO_BE_PEER_GRADED_NOT_EXISTS", String.format("待互评题目ID：%s不存在", essayProblemToBePeerGradedID));
        }

        String msg = "评分成功";

        //如果这道题没有被评过，即gradedPointList为空，则加上这个互评分数
        List<Double> gradedPointList = essayProblemToBePeerGraded.getGradedPointList();
        if (gradedPointList.size() == 0) {
            gradedPointList.add(point);
            essayProblemToBePeerGradedRepository.save(essayProblemToBePeerGraded);
        }
        //如果被评过，即gradedPointList的size是1，则进行进一步判断
        else if (gradedPointList.size() == 1) {
            Double point1 = gradedPointList.get(0);
            Double maxPoint = essayProblemToBePeerGraded.getMaxPoint();
            String essayProblemID = essayProblemToBePeerGraded.getEssayProblemID();
            String answerUsername = essayProblemToBePeerGraded.getAnswererUsername();
            String answer = essayProblemToBePeerGraded.getAnswer();
            String courseCode = essayProblemToBePeerGraded.getCourseCode();
            //两次分数差的绝对值超过了这题满分的20%，交给教师/助教评阅，同时删掉这个
            if (Math.abs(point - point1) > 0.2 * maxPoint) {
                String essayProblemToBeDoneID = essayProblemToBePeerGraded.getEssayProblemToBeDoneID();
                EssayProblemToBeTeacherTAGraded essayProblemToBeTeacherTAGraded =
                        new EssayProblemToBeTeacherTAGraded(
                                essayProblemToBeDoneID,
                                essayProblemID, maxPoint,
                                answerUsername,
                                answer,
                                courseCode,
                                new Date());
                essayProblemToBeTeacherTAGradedRepository.save(essayProblemToBeTeacherTAGraded);
                essayProblemToBePeerGradedRepository.delete(essayProblemToBePeerGraded);
                msg = "你的评分和另一个同学的评分差距过大，已经交给教师和助教调停";
            }
            //反之，两次分数取平均，生成一个EssayProblemFinished，表示评判完成。同时删掉这个 EssayProblemToBePeerGraded
            else {
                Double gottenPoint = (point + point1) / 2;
                EssayProblemFinished essayProblemFinished =
                        new EssayProblemFinished(
                                essayProblemID,
                                maxPoint,
                                gottenPoint,
                                answer,
                                answerUsername,
                                courseCode,
                                new Date());
                essayProblemFinishedRepository.save(essayProblemFinished);
                essayProblemToBePeerGradedRepository.delete(essayProblemToBePeerGraded);
            }
        }

        String essayProblemToBeDoneID = essayProblemToBePeerGraded.getEssayProblemToBeDoneID();
        PeerGradingRecord peerGradingRecord = new PeerGradingRecord(username, essayProblemToBeDoneID);
        peerGradingRecordRepository.save(peerGradingRecord);
        return new SimpleSuccessfulPostResponse(msg);
    }

    @EnsureUserExists
    @EnsureUserHasCourseAccess
    public SimpleSuccessfulPostResponse teacherTAGradeEssayProblem(@Username String username,
                                                                   String essayProblemToBeTeacherTAGradedID,
                                                                   Double point) {
        EssayProblemToBeTeacherTAGraded essayProblemToBeTeacherTAGraded = essayProblemToBeTeacherTAGradedRepository.findByEssayProblemToBeTeacherTAGradedID(essayProblemToBeTeacherTAGradedID);

        String essayProblemID = essayProblemToBeTeacherTAGraded.getEssayProblemID();
        Double maxPoint = essayProblemToBeTeacherTAGraded.getMaxPoint();
        String answer = essayProblemToBeTeacherTAGraded.getAnswer();
        String answerUsername = essayProblemToBeTeacherTAGraded.getAnswererUsername();
        String courseCode = essayProblemToBeTeacherTAGraded.getCourseCode();

        EssayProblemFinished essayProblemFinished = new EssayProblemFinished(
                essayProblemID,
                maxPoint,
                point,
                answer,
                answerUsername,
                courseCode,
                new Date());
        essayProblemFinishedRepository.save(essayProblemFinished);

        essayProblemToBeTeacherTAGradedRepository.delete(essayProblemToBeTeacherTAGraded);

        return new SimpleSuccessfulPostResponse("评分成功");
    }


    @EnsureStudentExists
    @EnsureCourseExists
    @EnsureStudentHasCourseAccess
    public PageQueryResult<StudentEssayProblemFinishedDTO> studentGetEssayProblemFinished(@CourseCode String courseCode,
                                                                                          @StudentUsername String username,
                                                                                          int pageSize,
                                                                                          int pageNum) {
        pageNum--;

        Page<EssayProblemFinished> essayProblemsFinished = essayProblemFinishedRepository.findAllByCourseCodeAndAnswerUsernameOrderByDateDesc(courseCode, username, PageRequest.of(pageNum, pageSize));


        EntityDTOMapper mapper = EntityDTOMapper
                .builder()
                .setEntityClass(EssayProblemFinished.class)
                .setDtoClass(StudentEssayProblemFinishedDTO.class)
                .build();


        List<StudentEssayProblemFinishedDTO> data = essayProblemsFinished
                .stream()
                .map(essayProblemFinished -> {
                    EssayProblem essayProblem = essayProblemRepository.findByEssayProblemID(essayProblemFinished.getEssayProblemID());
                    return StudentEssayProblemFinishedDTO.from(essayProblemFinished, essayProblem);
                })
                .toList();

        int totalPage = essayProblemsFinished.getTotalPages();

        return PageQueryResult.of(totalPage, data);


//        List<StudentEssayProblemFinishedDTO> results = new ArrayList<>();
//        for (EssayProblemFinished essayProblemFinished : essayProblemsFinished) {
//            String essayProblemFinishedID = essayProblemFinished.getEssayProblemFinishedID();
//            Double maxPoint = essayProblemFinished.getMaxPoint();
//            Double gottenPoint = essayProblemFinished.getGottenPoint();
//            String answer = essayProblemFinished.getAnswer();
//            String essayProblemID = essayProblemFinished.getEssayProblemID();
//            EssayProblem essayProblem = essayProblemRepository.findByEssayProblemID(essayProblemID);
//            String referenceAnswer = essayProblem.getReferenceAnswer();
//            String title = essayProblem.getTitle();
//            StudentEssayProblemFinishedDTO result = new StudentEssayProblemFinishedDTO(essayProblemFinishedID, maxPoint, gottenPoint, title,
//                    answer, referenceAnswer, username, courseCode);
//            results.add(result);
//        }
//        return results;
    }
}
