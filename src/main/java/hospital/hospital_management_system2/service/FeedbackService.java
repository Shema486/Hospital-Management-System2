package hospital.hospital_management_system2.service;

import hospital.hospital_management_system2.dao.FeedbackDao;
import hospital.hospital_management_system2.model.PatientFeedback;

import java.util.*;


public class FeedbackService {

    private final FeedbackDao feedbackDAO = new FeedbackDao();
    private final Map<Long, PatientFeedback> feedbackCache = new HashMap<>();

    public void addFeedback(PatientFeedback feedback) {
        feedbackDAO.addFeedback(feedback);
        clearCache();
    }

    public List<PatientFeedback> getAllFeedback() {
        List<PatientFeedback> feedbackList = feedbackDAO.findAll();
        for (PatientFeedback f : feedbackList) {
            feedbackCache.put(f.getFeedbackId(), f);
        }
        return feedbackList;
    }

    public void deleteFeedback(Long id) {
        feedbackDAO.deleteFeedback(id);
        feedbackCache.remove(id);
    }

    // Clear cache
    public void clearCache() {
        feedbackCache.clear();
    }


}
