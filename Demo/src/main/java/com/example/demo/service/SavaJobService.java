package com.example.demo.service;

import com.example.demo.model.Job;
import com.example.demo.model.SavedJob;
import com.example.demo.model.User;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.SaveJobRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SavaJobService {
    private final JobRepository jobRepository;
    private final SaveJobRepository saveJobRepository;
    private final UserRepository userRepository;
    public SavaJobService(JobRepository jobRepository, SaveJobRepository saveJobRepository, UserRepository userRepository)
    {
        this.jobRepository=jobRepository;
        this.saveJobRepository=saveJobRepository;
        this.userRepository=userRepository;
    }
    public SavedJob savedJob(UUID userId, UUID jobId)
    {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công việc"));
        if(!"Đã duyệt" . equalsIgnoreCase(job.getStatus()))
            throw new RuntimeException("Công việc chưa được duyệt, không thể lưu công việc");
        if(saveJobRepository.existsByUserIdAndJobId(userId,jobId))
            throw new RuntimeException("Bạn đã lưu công việc này rồi");
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng"));
        SavedJob savedJob = new SavedJob();
        savedJob.setJob(job);
        savedJob.setUser(user);
        return saveJobRepository.save(savedJob);
    }
    public List<SavedJob> getSaveJob(UUID userId)
    {
        return  saveJobRepository.findByUserId(userId);
    }

    public void  unSave(UUID userId, UUID jobId)
    {
        if(!saveJobRepository.existsByUserIdAndJobId(userId,jobId))
        {
            throw  new RuntimeException("Bạn chưa lưu công việc nào");
        }
        saveJobRepository.deleteByUserIdAndJobId(userId,jobId);

    }
}

