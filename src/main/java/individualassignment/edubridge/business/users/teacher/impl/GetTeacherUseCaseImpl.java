package individualassignment.edubridge.business.users.teacher.impl;

import individualassignment.edubridge.business.users.exceptions.UnauthorizedDataAccessException;
import individualassignment.edubridge.business.users.impl.TeacherConverter;
import individualassignment.edubridge.business.users.teacher.GetTeacherUseCase;
import individualassignment.edubridge.domain.users.AccessToken;
import individualassignment.edubridge.domain.users.Teacher;
import individualassignment.edubridge.persistence.users.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetTeacherUseCaseImpl implements GetTeacherUseCase {

    private final TeacherRepository teacherRepository;

    private final AccessToken requestAccessToken;

    @Override
    @Transactional
    public Optional<Teacher> getTeacher(long teacherId) {
        if (!Objects.equals(requestAccessToken.getTeacherId(), teacherId)) {
            throw new UnauthorizedDataAccessException();
        }
        return teacherRepository.findById(teacherId).map(TeacherConverter::convert);
    }
}
