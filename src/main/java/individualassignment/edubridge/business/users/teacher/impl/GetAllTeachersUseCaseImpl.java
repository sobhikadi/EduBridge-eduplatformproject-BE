package individualassignment.edubridge.business.users.teacher.impl;

import individualassignment.edubridge.business.users.impl.TeacherConverter;
import individualassignment.edubridge.business.users.teacher.GetAllTeachersUseCase;
import individualassignment.edubridge.domain.users.Teacher;
import individualassignment.edubridge.domain.users.requests.GetAllTeachersRequest;
import individualassignment.edubridge.domain.users.responses.GetAllTeachersResponse;
import individualassignment.edubridge.persistence.users.TeacherRepository;
import individualassignment.edubridge.persistence.users.entities.TeacherEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllTeachersUseCaseImpl implements GetAllTeachersUseCase {

    private final TeacherRepository teacherRepository;

    @Override
    @Transactional
    public GetAllTeachersResponse getAllTeachers(GetAllTeachersRequest request) {
        List<TeacherEntity> results;
        if (StringUtils.hasText(request.getCountryCode())) {
            results = teacherRepository.findAllByAddress_CountryCodeOrderByIdAsc(request.getCountryCode());
        } else {
            results = teacherRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }

        final GetAllTeachersResponse response = new GetAllTeachersResponse();
        List<Teacher> teachers = results
                .stream()
                .map(TeacherConverter::convert)
                .toList();

        response.setTeachers(teachers);

        return response;
    }
}
