package individualassignment.edubridge.business.users.student.impl;

import individualassignment.edubridge.business.users.impl.StudentConverter;
import individualassignment.edubridge.business.users.student.GetAllStudentsUseCase;
import individualassignment.edubridge.domain.users.Student;
import individualassignment.edubridge.domain.users.requests.GetAllStudentsRequest;
import individualassignment.edubridge.domain.users.responses.GetAllStudentsResponse;
import individualassignment.edubridge.persistence.users.StudentRepository;
import individualassignment.edubridge.persistence.users.entities.StudentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllStudentsUseCaseImpl implements GetAllStudentsUseCase {

    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public GetAllStudentsResponse getAllStudents(GetAllStudentsRequest request) {
        List<StudentEntity> results;
        if (StringUtils.hasText(request.getCountryCode())) {
            results = studentRepository.findAllByCountryCodeOrderByIdAsc(request.getCountryCode());
        } else {
            results = studentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }

        final GetAllStudentsResponse response = new GetAllStudentsResponse();
        List<Student> students = results
                .stream()
                .map(StudentConverter::convert)
                .toList();

        response.setStudents(students);

        return response;
    }
}
