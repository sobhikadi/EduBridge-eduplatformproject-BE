package individualassignment.edubridge.business.users.teacher.impl;

import individualassignment.edubridge.business.users.teacher.DeleteTeacherUseCase;
import individualassignment.edubridge.persistence.users.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTeacherUseCaseImpl implements DeleteTeacherUseCase{

    private final TeacherRepository teacherRepository;

    @Override
    public void deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }
}
