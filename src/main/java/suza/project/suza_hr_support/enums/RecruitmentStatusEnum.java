package suza.project.suza_hr_support.enums;

public enum RecruitmentStatusEnum {
    APPLIED, // Applicant has submitted their application; HR has received it.
    SHORTLISTED, // HR has reviewed applications and selected this applicant for an interview.
    INTERVIEWED, // Applicant has completed the interview; HR may still be deciding.
    HIRED, // Applicant successfully passed all steps/ Applicant accepted → new Employee should be created
    REJECTED   // Applicant not selected
}
// Process tracker => Updated as the application moves through steps: 
    // APPLIED → SHORTLISTED → INTERVIEWED → HIRED/REJECTED