package com.example.demo.service;

import com.example.demo.dto.request.AdminEmployerDTO;
import com.example.demo.dto.request.EmployerDTO;
import com.example.demo.dto.request.MyEmployerDTO;
import com.example.demo.model.Application;
import com.example.demo.model.Employer;
import com.example.demo.model.Job;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.EmployerRepository;
import com.example.demo.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class EmployerService {

    private final EmployerRepository employerRepository;
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    // ROOT PATH: src/main/resources/static/uploads/
    private static final String STATIC_UPLOAD_ROOT =
            new File("src/main/resources/static/uploads").getAbsolutePath();

    private static final String LOGO_DIR = STATIC_UPLOAD_ROOT + "/logos/";
    private static final String LICENSE_DIR = STATIC_UPLOAD_ROOT + "/licenses/";

    public EmployerService(EmployerRepository employerRepository,
                           ApplicationRepository applicationRepository,
                           JobRepository jobRepository) {
        this.employerRepository = employerRepository;
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
    }

    public List<EmployerDTO> getEmployers() {
        return employerRepository.findAll()
                .stream()
                .map(e -> new EmployerDTO(
                        e.getId(),
                        e.getCompanyName(),
                        e.getCompanyEmail(),
                        e.getStatus(),
                        e.getCompanySize(),
                        e.getCompanyAddress()
                ))
                .toList();
    }

    public Employer create(Employer employer, MultipartFile logo, MultipartFile license)
            throws IOException {

        if (employerRepository.existsByCompanyName(employer.getCompanyName()))
            throw new RuntimeException("Công ty này đã tồn tại");

        // Lưu logo
        if (logo != null && !logo.isEmpty()) {

            // Tạo thư mục static/uploads/logos nếu chưa có
            Files.createDirectories(Path.of(LOGO_DIR));

            // Đường dẫn file lưu trên server
            String filePath = LOGO_DIR + logo.getOriginalFilename();
            logo.transferTo(new File(filePath));

            // Đường dẫn trả ra client
            employer.setCompanyLogo("/uploads/logos/" + logo.getOriginalFilename());
        }

        // Lưu license
        if (license != null && !license.isEmpty()) {

            Files.createDirectories(Path.of(LICENSE_DIR));

            String filePath = LICENSE_DIR + license.getOriginalFilename();
            license.transferTo(new File(filePath));

            employer.setLicenseDocument("/uploads/licenses/" + license.getOriginalFilename());
        }

        return employerRepository.save(employer);
    }

    public Employer getEmployerById(UUID id) {
        return employerRepository.findById(id).orElse(null);
    }
    public AdminEmployerDTO getEmployerAdminById(UUID id) {
        Employer e = employerRepository.findById(id).orElse(null);
        if (e == null) return null;

        return new AdminEmployerDTO(
                e.getId(),
                e.getCompanyName(),
                e.getCompanyEmail(),
                e.getCompanyLogo(),
                e.getCompanySize(),
                e.getCompanyDescription(),
                e.getCompanyAddress(),
                e.getStatus(),
                e.getLatitude(),
                e.getLongitude(),
                e.getLicenseDocument()
        );
    }

    public Employer updateEmployer(UUID id, Employer employer, MultipartFile logo, MultipartFile license)
            throws IOException {

        Employer existing = getEmployerById(id);
        if (existing == null) throw new RuntimeException("Không tìm thấy công ty");

        if (employer.getCompanyName() != null)
            existing.setCompanyName(employer.getCompanyName());

        if (employer.getCompanyEmail() != null)
            existing.setCompanyEmail(employer.getCompanyEmail());

        if (employer.getCompanySize() != null)
            existing.setCompanySize(employer.getCompanySize());

        if (employer.getCompanyDescription() != null)
            existing.setCompanyDescription(employer.getCompanyDescription());

        if (employer.getCompanyAddress() != null)
            existing.setCompanyAddress(employer.getCompanyAddress());

        if (employer.getLatitude() != null)
            existing.setLatitude(employer.getLatitude());

        if (employer.getLongitude() != null)
            existing.setLongitude(employer.getLongitude());


        // Update logo
        if (logo != null && !logo.isEmpty()) {
            Files.createDirectories(Path.of(LOGO_DIR));
            String filePath = LOGO_DIR + logo.getOriginalFilename();
            logo.transferTo(new File(filePath));
            existing.setCompanyLogo("/uploads/logos/" + logo.getOriginalFilename());
        }

        // Update license
        if (license != null && !license.isEmpty()) {
            Files.createDirectories(Path.of(LICENSE_DIR));
            String filePath = LICENSE_DIR + license.getOriginalFilename();
            license.transferTo(new File(filePath));
            existing.setLicenseDocument("/uploads/licenses/" + license.getOriginalFilename());
        }

        return employerRepository.save(existing);
    }
    public Employer updateEmployerWithoutFiles(UUID id, Employer employer) {
        Employer existing = getEmployerById(id);
        if (existing == null) throw new RuntimeException("Không tìm thấy công ty");

        if (employer.getCompanyName() != null)
            existing.setCompanyName(employer.getCompanyName());
        if (employer.getCompanyEmail() != null)
            existing.setCompanyEmail(employer.getCompanyEmail());
        if (employer.getCompanySize() != null)
            existing.setCompanySize(employer.getCompanySize());
        if (employer.getCompanyDescription() != null)
            existing.setCompanyDescription(employer.getCompanyDescription());
        if (employer.getCompanyAddress() != null)
            existing.setCompanyAddress(employer.getCompanyAddress());
        if (employer.getLatitude() != null)
            existing.setLatitude(employer.getLatitude());
        if (employer.getLongitude() != null)
            existing.setLongitude(employer.getLongitude());

        // Không thao tác logo & license
        return employerRepository.save(existing);
    }


    public void deleteEmployer(UUID id) {
        employerRepository.deleteById(id);
    }
    public void deleteMyEmployer(UUID userId)
    {
        Employer employer = employerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa tạo công ty"));

        employerRepository.delete(employer);
    }

    public Employer approvedEmpployer(UUID id)
    {
        Employer existingEmployer = getEmployerById(id);
        existingEmployer.setStatus("Đã duyệt");
        return employerRepository.save(existingEmployer);
    }
    public Employer rejectEmployer(UUID id)
    {
        Employer existingEmployer = getEmployerById(id);
        existingEmployer.setStatus("Từ chối");
        return employerRepository.save(existingEmployer);
    }
    public Employer getMyEmployer(UUID userId) {
        return employerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa tạo công ty nào!"));
    }
    public MyEmployerDTO getMyEmployerDTO(UUID userId) {
        Employer employer = employerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bạn chưa tạo công ty nào!"));
        return new MyEmployerDTO(
                employer.getId(),
                employer.getCompanyName(),
                employer.getCompanyEmail(),
                employer.getCompanyLogo(),
                employer.getCompanySize(),
                employer.getCompanyDescription(),
                employer.getCompanyAddress(),
                employer.getStatus(),
                employer.getLatitude(),
                employer.getLongitude(),
                employer.getLicenseDocument()
        );
    }
}
